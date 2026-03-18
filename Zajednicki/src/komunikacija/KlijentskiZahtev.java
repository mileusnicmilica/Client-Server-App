
package komunikacija;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class KlijentskiZahtev implements Serializable {

    private Operacije operacija;
    private Object parametar;

    public KlijentskiZahtev() {
    }

    public KlijentskiZahtev(Operacije operacija, Object parametar) {
        this.operacija = operacija;
        this.parametar = parametar;
    }

    public Object getParametar() {
        return parametar;
    }

    public void setParametar(Object parametar) {
        this.parametar = parametar;
    }

    public Operacije getOperacija() {
        return operacija;
    }

    public void setOperacija(Operacije operacija) {
        this.operacija = operacija;
    }

   
}
