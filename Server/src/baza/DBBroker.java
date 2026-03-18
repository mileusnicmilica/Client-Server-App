/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baza;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Korisnik;
import model.Prevoz;
import model.Putovanje;

/**
 *
 * @author Milica
 */
public class DBBroker {

    private Connection connection;

    public DBBroker() {
        getConnection();
    }

    private void getConnection() {
        if (connection == null) {
            String url = "jdbc:mysql://localhost:3306/rmt_baza";
            String user = "root";
            String pass = "";

            try {
                connection = DriverManager.getConnection(url, user, pass);
                System.out.println("Pronadjena konekcija sa bazom");

            } catch (SQLException ex) {
                Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public Korisnik getKorisnik(Korisnik korisnik) throws SQLException {

        getConnection();
        String query = "SELECT jmbg, ime, prezime, email, username, password, pasos FROM user WHERE username=? AND password=?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, korisnik.getUsername());
            statement.setString(2, korisnik.getPassword());

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Korisnik(rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("ime"),
                            rs.getString("prezime"),
                            rs.getString("email"),
                            rs.getString("jmbg"),
                            rs.getString("pasos"));
                } else {
                    throw new SQLException("Korisnik ne postoji!");
                }

            }

        } catch (SQLException ex) {
            System.out.println("Neuspešno učitavanje korisnika iz baze!");
            throw ex;
        }
    }

    public void dodajKorisnika(Korisnik korisnik) throws SQLException {
        getConnection();

        String query = "INSERT INTO user (jmbg, ime, prezime, email, username, password, pasos) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, korisnik.getJmbg());
            statement.setString(2, korisnik.getIme());
            statement.setString(3, korisnik.getPrezime());
            statement.setString(4, korisnik.getEmail());
            statement.setString(5, korisnik.getUsername());
            statement.setString(6, korisnik.getPassword());
            statement.setString(7, korisnik.getPasos());

            statement.executeUpdate();
            statement.close();
        }
    }

    public void dodajPutovanje(Putovanje putovanje) throws SQLException {
        getConnection();
        String query = "INSERT INTO putovanje (ime, prezime, jmbg, pasos, zemlja, datum_ulaska, datum_izlaska, tip_prevoza) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, putovanje.getIme());
            statement.setString(2, putovanje.getPrezime());
            statement.setString(3, putovanje.getJmbg());
            statement.setString(4, putovanje.getPasos());
            statement.setString(5, putovanje.getZemlje());
            statement.setDate(6, Date.valueOf(putovanje.getDatumUlaska()));
            statement.setDate(7, Date.valueOf(putovanje.getDatumIzlaska()));
            statement.setString(8, putovanje.getNacinPrevoza().toString());

            statement.executeUpdate();
        }

    }

    public boolean validacijaKorisnika(Korisnik k) throws SQLException {

        String querry = "SELECT * FROM user where jmbg= '" + k.getJmbg() + "' OR username= '" + k.getUsername() + "' OR pasos= '" + k.getPasos() + "'";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(querry);

        return (!rs.next());

    }

    public List<Putovanje> prikazPutovanja(Korisnik k) throws SQLException {
        List<Putovanje> putovanja = new ArrayList<>();
        String querry = "SELECT * FROM putovanje WHERE jmbg= '" + k.getJmbg() + " ' OR pasos= '" + k.getPasos() + "'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(querry);

        while (rs.next()) {
            Putovanje p = new Putovanje();
            p.setIme(rs.getString("ime"));
            p.setPrezime(rs.getString("prezime"));
            p.setJmbg(rs.getString("jmbg"));
            p.setPasos(rs.getString("pasos"));
            p.setZemlje(rs.getString("zemlja"));
            p.setDatumUlaska(rs.getDate("datum_ulaska").toLocalDate());
            p.setDatumIzlaska(rs.getDate("datum_izlaska").toLocalDate());
            p.setId_putovanje(rs.getInt("id_putovanje"));
            p.setNacinPrevoza(Prevoz.valueOf(rs.getString("tip_prevoza")));
            p.setStatus();
            putovanja.add(p);

        }
        return putovanja;

    }

    public List<Putovanje> prikazPutovanjaPasos(String s) throws SQLException {
        List<Putovanje> putovanja = new ArrayList<>();
        String querry = "SELECT * FROM putovanje WHERE jmbg= '" + s + "' OR pasos= '" + s + "'";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(querry);

        while (rs.next()) {
            Putovanje p = new Putovanje();
            p.setIme(rs.getString("ime"));
            p.setPrezime(rs.getString("prezime"));
            p.setJmbg(rs.getString("jmbg"));
            p.setPasos(rs.getString("pasos"));
            p.setZemlje(rs.getString("zemlja"));
            p.setDatumUlaska(rs.getDate("datum_ulaska").toLocalDate());
            p.setDatumIzlaska(rs.getDate("datum_izlaska").toLocalDate());
            p.setId_putovanje(rs.getInt("id_putovanje"));
            p.setNacinPrevoza(Prevoz.valueOf(rs.getString("tip_prevoza")));
            p.setStatus();
            putovanja.add(p);

        }
        return putovanja;

    }

    public void izmeniPutovanje(Putovanje p) throws SQLException {

        String query = "UPDATE putovanje SET  zemlja=?, datum_ulaska=?, datum_izlaska=?, tip_prevoza=? WHERE id_putovanje=?";

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, p.getZemlje());
        statement.setDate(2, Date.valueOf(p.getDatumUlaska()));
        statement.setDate(3, Date.valueOf(p.getDatumIzlaska()));
        statement.setString(4, p.getNacinPrevoza().toString());
        statement.setInt(5, p.getId_putovanje());
        System.out.println();
        statement.execute();

    }

    public boolean validacijaPutovanja(Putovanje p) throws SQLException {
        String query = "SELECT * FROM putovanje WHERE jmbg= ? AND zemlja=?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, p.getJmbg());
        statement.setString(2, p.getZemlje());

        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            System.out.println("rs je : " + rs.getString("zemlja") + " "
                    + rs.getDate("datum_ulaska") + " " + rs.getDate("datum_izlaska"));
            LocalDate datumUlaska = rs.getDate("datum_ulaska").toLocalDate();
            LocalDate datumIzlaska = rs.getDate("datum_izlaska").toLocalDate();
            if ((datumUlaska.isBefore(p.getDatumIzlaska())
                    && datumUlaska.isAfter(p.getDatumUlaska()))
                    || (datumIzlaska.isBefore(p.getDatumIzlaska())
                    && datumIzlaska.isAfter(p.getDatumUlaska()))
                    || (datumIzlaska.isAfter(p.getDatumUlaska()) && datumUlaska.isBefore(p.getDatumUlaska()))) {
                return false;
            }

        }
        return true;

    }
}
