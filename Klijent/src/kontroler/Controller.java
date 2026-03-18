/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import komunikacija.KlijentskiZahtev;
import komunikacija.OdgovorInterfejs;
import komunikacija.Operacije;
import komunikacija.ServerskiOdgovor;
import komunikacija.ZahtevniInterfejs;
import model.Korisnik;
import model.Putovanje;

public class Controller {

    private static Controller instance;
    private Socket s;
    private OdgovorInterfejs odg;
    private ZahtevniInterfejs zahtev;

    public static Controller getInstance() throws IOException {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    private Controller() throws IOException {
        s = new Socket("localhost", 9000);
        zahtev = new ZahtevniInterfejs(s);
        odg = new OdgovorInterfejs(s);
    }

    public ServerskiOdgovor primiOdgovor() {
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            return (ServerskiOdgovor) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void posaljiZahtev(KlijentskiZahtev kz) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(kz);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Korisnik login(Korisnik k) throws IOException, Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.LOGIN);
        kz.setParametar(k);

        Controller.getInstance().posaljiZahtev(kz); // slanje serveru

        ServerskiOdgovor so = Controller.getInstance().primiOdgovor();

        if (so.getException() != null) {
            throw so.getException();
        }

        return (Korisnik) so.getOdgovor(); // vracanje ulogovanog korisnika
    }

    public boolean dodaj_putovanje(Putovanje p) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.DODAJ_PUTOVANJE, p);
        try {
            zahtev.posalji(kz);
            System.out.println("Šaljem zahtev za dodavanje putovanja: " + p);

        } catch (Exception e) {
            throw e;
        }
        ServerskiOdgovor so = (ServerskiOdgovor) odg.primi();
        if (so.getOdgovor().equals(true)) {
            sacuvajPutovanjeUTekstualniFajl(p);
            return true;
        }
        return false;

    }

    private void sacuvajPutovanjeUTekstualniFajl(Putovanje p) {
        String imeFajla = "Putovanje_" + p.getIme() + "_" + p.getPrezime() + ".txt";
        File fajl = new File(imeFajla);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fajl))) {
            bw.write("===== Podaci o putovanju =====");
            bw.newLine();
            bw.write("Ime i prezime: " + p.getIme() + " " + p.getPrezime());
            bw.newLine();
            bw.write("JMBG: " + p.getJmbg());
            bw.newLine();
            bw.write("Broj pasoša: " + p.getPasos());
            bw.newLine();
            bw.write("Destinacija: " + p.getZemlje());
            bw.newLine();
            bw.write("Datum prijave: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
            bw.newLine();
            bw.write("Datum ulaska u EU: " + p.getDatumUlaska().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
            bw.newLine();
            bw.write("Datum izlaska iz EU: " + p.getDatumIzlaska().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
            bw.newLine();

            long brojDana = ChronoUnit.DAYS.between(p.getDatumUlaska(), p.getDatumIzlaska()) + 1;
            bw.write("Broj dana boravka u EU: " + brojDana);
            bw.newLine();

            bw.write("Prevoz: " + p.getNacinPrevoza().toString());
            bw.newLine();

            boolean placanje = trebaDaPlati(p.getJmbg());
            bw.write("Da li korisnik treba da plaća prijavu: " + (placanje ? "DA" : "NE"));
            bw.newLine();

            bw.write("==============================");
            bw.newLine();

            bw.flush();
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Greška pri čuvanju fajla", e);
        }
    }

    public boolean trebaDaPlati(String jmbg) {
        try {
            // iz jmbga indeksi 4 do 6 su godina rodjenaj
            int godinaInt = Integer.parseInt(jmbg.substring(4, 7));
            int trenutnaGodina = LocalDate.now().getYear();
            int prag = trenutnaGodina - 2000;

            int punaGodina;
            if (godinaInt <= prag) {
                punaGodina = 2000 + godinaInt;
            } else {
                punaGodina = 1900 + godinaInt;
            }

            //  dan i mesec iz jmbga
            int dan = Integer.parseInt(jmbg.substring(0, 2));
            int mesec = Integer.parseInt(jmbg.substring(2, 4));
            System.out.println("mesec je:" + mesec);
            LocalDate datumRodjenja = LocalDate.of(punaGodina, mesec, dan);
            LocalDate danas = LocalDate.now();

            int starost = Period.between(datumRodjenja, danas).getYears();

            // 18 do 70 godina true
            return starost >= 18 && starost <= 70;

        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {

            return true;
        }
    }

    public boolean dodaj_korisnika(Korisnik novi) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.DODAJ_KORISNIKA, novi);
        try {
            zahtev.posalji(kz);
        } catch (Exception e) {
            throw e;
        }
        ServerskiOdgovor so = (ServerskiOdgovor) odg.primi();
        if (so.getOdgovor().equals(true)) {
            return true;
        }
        return false;
    }

    public List<Putovanje> getPutovanje(Korisnik k) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.PRIKAZI_PUTOVANJA, k);
        try {
            zahtev.posalji(kz);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        ServerskiOdgovor so = (ServerskiOdgovor) odg.primi();
        if (so.getException() == null) {
            return (List<Putovanje>) so.getOdgovor();
        } else {
            throw so.getException();
        }

    }

    public List<Putovanje> getPutovanjePasosJmbg(String s) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.PRIKAZI_PUTOVANJA_JMBG_PASOS, s);
        try {
            zahtev.posalji(kz);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        ServerskiOdgovor so = (ServerskiOdgovor) odg.primi();
        if (so.getException() == null) {
            return (List<Putovanje>) so.getOdgovor();
        } else {
            throw so.getException();
        }
    }

    
    public void izmeniPutovanje(Putovanje p) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev(Operacije.IZMENI_PUTOVANJE, p);
        try {
            zahtev.posalji(kz);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        ServerskiOdgovor so = (ServerskiOdgovor) odg.primi();
        if (so.getException() != null) {
            throw so.getException();
        }
    }
}
