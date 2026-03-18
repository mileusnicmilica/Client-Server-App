/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import komunikacija.KlijentskiZahtev;
import komunikacija.OdgovorInterfejs;
import komunikacija.ServerskiOdgovor;
import komunikacija.ZahtevniInterfejs;
import model.Korisnik;


/**
 *
 * @author USER
 */
import baza.Kontroler;
import model.Putovanje;

public class ObradaKlijentskihZahteva extends Thread {
    
    private Socket s;
    private OdgovorInterfejs odgovor;
    private ZahtevniInterfejs zahtev;
    
    public ObradaKlijentskihZahteva(Socket s) {
        this.s = s;
        zahtev = new ZahtevniInterfejs(s);
        odgovor = new OdgovorInterfejs(s);
    }
    
    @Override
    public void run() {
        while (true) {
            KlijentskiZahtev kz = primiZahtev();
            if (kz == null) {
                System.out.println("Klijent je zatvorio vezu ili je došlo do greške u prijemu zahteva.");
                break; // prekidamo rad niti
            }
            ServerskiOdgovor so = new ServerskiOdgovor();
            switch (kz.getOperacija()) {
                case LOGIN:
                    Korisnik korisnik = (Korisnik) kz.getParametar();
                    Korisnik ulogovani = null;
                    try {
                        ulogovani = Kontroler.getInstance().login(korisnik);
                    } catch (SQLException ex) {
                        Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    so.setOdgovor(ulogovani);
                    break;
                
                case DODAJ_KORISNIKA:
                    Korisnik novi = (Korisnik) kz.getParametar();
                    try {
                        if (Kontroler.getInstance().dodajKorisnika(novi)) {
                            so.setOdgovor(true);  // vraćamo true da klijent zna da jje uspesno
                        } else {
                            so.setOdgovor(false);
                        }
                    } catch (Exception e) {
                        so.setException(e);
                    }
                    break;
                case DODAJ_PUTOVANJE:
                    Putovanje putovanje = (Putovanje) kz.getParametar();
                    try {
                        if (Kontroler.getInstance().dodajPutovanje(putovanje)) {
                            so.setOdgovor(true);
                        } else {
                            so.setOdgovor(false);
                        }
                        System.out.println("Putovanje uspešno dodato: " + putovanje);
                    } catch (Exception e) {
                        so.setException(e);
                        System.err.println("Greška prilikom dodavanja putovanja:");
                        e.printStackTrace(); // PRIKAZUJE GREŠKU NA KONZOLI
                    }
                    break;
                case IZMENI_PUTOVANJE:
                    Putovanje izmena = (Putovanje) kz.getParametar();
                    try {
                        Kontroler.getInstance().izmeniPutovanje(izmena);
                        so.setOdgovor(true);
                    } catch (SQLException ex) {
                        so.setException(ex);
                    }
                    break;
                case PRIKAZI_PUTOVANJA:
                    Korisnik korisnikZaPutovanja = (Korisnik) kz.getParametar();
                    try {
                        List<Putovanje> lista = Kontroler.getInstance().prikazPutovanja(korisnikZaPutovanja);
                        so.setOdgovor(lista);
                    } catch (SQLException ex) {
                        so.setException(ex);
                    }
                    break;
                case PRIKAZI_PUTOVANJA_JMBG_PASOS:
                    String s = (String) kz.getParametar();
                    try {
                        List<Putovanje> lista = Kontroler.getInstance().prikazPutovanjaPasos(s);
                        so.setOdgovor(lista);
                    } catch (SQLException ex) {
                        so.setException(ex);
                    }
                    break;
            }
            posaljiOdgovor(so);
        }
    }
    
    private KlijentskiZahtev primiZahtev() {
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            return (KlijentskiZahtev) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("KLIJENT SE ODVEZAO (UGASIO/LA SI KLIJENTSKU APLIKACIJU),"
                    + " ZATO SE DESIO OVAJ EXCEPTION ");
            Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void posaljiOdgovor(ServerskiOdgovor so) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(so);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
