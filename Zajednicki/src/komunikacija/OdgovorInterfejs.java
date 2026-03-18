/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package komunikacija;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
/**
 *
 * @author Milica Klasa zadužena za prijem objekata sa servera
 */
public class OdgovorInterfejs {

    private final Socket soket;

    public OdgovorInterfejs(Socket soket) {
        this.soket = soket;
    }

    public Object primi() throws Exception {
        try {
            ObjectInputStream ulaz = new ObjectInputStream(soket.getInputStream());
            return ulaz.readObject();
        } catch (IOException ex) {
            throw new Exception("Greška prilikom čitanja objekta: " + ex.getMessage());
        }
    }
}


