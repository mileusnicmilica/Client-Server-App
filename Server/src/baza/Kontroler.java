/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baza;

import baza.DBBroker;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Korisnik;
import model.Putovanje;

/**
 *
 * @author USER
 */
public class Kontroler {

    private static Kontroler instance;
    private DBBroker dbb;

    private Kontroler() {
        dbb = new DBBroker();
    }

    public static Kontroler getInstance() {
        if (instance == null) {
            instance = new Kontroler();
        }
        return instance;
    }

    public Korisnik login(Korisnik korisnik) throws SQLException {
        return dbb.getKorisnik(korisnik);
    }

    public boolean dodajKorisnika(Korisnik novi) throws SQLException {
        if (dbb.validacijaKorisnika(novi)) {
            dbb.dodajKorisnika(novi);
            System.out.println("dodat korisnik");
            return true;
        } else {
            System.out.println("validacija nije prosla");
            return false;
        }

    }

    public boolean dodajPutovanje(Putovanje novo) {
        try {
            Korisnik k = new Korisnik(novo.getIme(), novo.getPrezime(), novo.getJmbg(), novo.getBrojPasosa());
            if (dbb.validacijaPutovanja(novo) && !dbb.validacijaKorisnika(k)) {
                dbb.dodajPutovanje(novo);
                System.out.println("Ima jmbg!");
                return true;
            } else {
                System.out.println("Putovanje već postoji u bazi, ili ne postoji korisnik sa tim podacima!");
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Putovanje> prikazPutovanja(Korisnik k) throws SQLException {
        return dbb.prikazPutovanja(k);
    }

    public List<Putovanje> prikazPutovanjaPasos(String s) throws SQLException {
        return dbb.prikazPutovanjaPasos(s);
    }

    public void izmeniPutovanje(Putovanje p) throws SQLException {
        dbb.izmeniPutovanje(p);
    }

}
