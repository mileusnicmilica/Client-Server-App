/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import baza.DBBroker;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Milica
 */
public class PokreniServer extends Thread {
    private DBBroker db;
    private static List<ObradaKlijentskihZahteva> korisnici = new ArrayList<>();
    public PokreniServer() {
    }

    @Override
    public void run() {
        try {
            ServerSocket serverskiSoket = new ServerSocket(9000);
            while (true) {
                System.out.println("Cekanje klijenta...");
                Socket s = serverskiSoket.accept();
                System.out.println("Klijent se povezao!");
                ObradaKlijentskihZahteva nit = new ObradaKlijentskihZahteva(s);
                
                korisnici.add(nit);
                
                nit.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(PokreniServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
