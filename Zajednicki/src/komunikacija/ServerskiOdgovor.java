/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komunikacija;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class ServerskiOdgovor implements Serializable {

    private Object odgovor;
    private Exception exception;

    public ServerskiOdgovor() {
    }

    public ServerskiOdgovor(Object odgovor, Exception exception) {
        this.odgovor = odgovor;
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Object getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(Object odgovor) {
        this.odgovor = odgovor;
    }

}
