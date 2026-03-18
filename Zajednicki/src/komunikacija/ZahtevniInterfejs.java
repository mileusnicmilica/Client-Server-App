/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package komunikacija;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
/**
 *
 * @author Milica
 */
public class ZahtevniInterfejs implements Serializable {
    private final Socket soket;

    public ZahtevniInterfejs(Socket soket) {
        this.soket = soket;
    }
    // za slanje objekata preko mreže ka serveru    sender
    public void posalji(Object objekat) throws Exception {
        try {
            ObjectOutputStream izlaz = new ObjectOutputStream(soket.getOutputStream());
            izlaz.writeObject(objekat);
            izlaz.flush();
        } catch (SocketException ex) {
            throw new SocketException("Greška prilikom slanja objekta: " + ex.getMessage());
        }
    }
}


