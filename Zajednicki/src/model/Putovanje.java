/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author Milica
 */
public class Putovanje implements Serializable {

    private int id_putovanje;

    private String ime;
    private String prezime;

    private String jmbg;
    private String pasos;
    private String zemlje;
    private LocalDate datumUlaska;
    private LocalDate datumIzlaska;
    private Prevoz nacinPrevoza;
    private String status; // završena, u obradi, zaključana

    public int getId_putovanje() {
        return id_putovanje;
    }

    public void setId_putovanje(int id_putovanje) {
        this.id_putovanje = id_putovanje;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getBrojPasosa() {
        return pasos;
    }

    public void setBrojPasosa(String pasos) {
        this.pasos = pasos;
    }

    public String getZemlje() {
        return zemlje;
    }

    public void setZemlje(String zemlje) {
        this.zemlje = zemlje;
    }

    public LocalDate getDatumUlaska() {
        return datumUlaska;
    }

    public void setDatumUlaska(LocalDate datumUlaska) {
        this.datumUlaska = datumUlaska;
    }

    public LocalDate getDatumIzlaska() {
        return datumIzlaska;
    }

    public void setDatumIzlaska(LocalDate datumIzlaska) {
        this.datumIzlaska = datumIzlaska;
    }

    public Prevoz getNacinPrevoza() {
        return nacinPrevoza;
    }

    public void setNacinPrevoza(Prevoz nacinPrevoza) {
        this.nacinPrevoza = nacinPrevoza;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus() {
        if (datumUlaska.isBefore(LocalDate.now())) {
            status = "zavrseno";
        } else if ((ChronoUnit.DAYS.between(LocalDate.now(), datumUlaska)) >= 2) {
            status = "u procesu";
        } else {
            status = "zakljuceno";
        }
    }

    public Putovanje(String ime, String prezime, String jmbg, String pasos, String zemlje, LocalDate datumUlaska, LocalDate datumIzlaska, Prevoz nacinPrevoza) {
        this.ime = ime;
        this.prezime = prezime;
        this.jmbg = jmbg;
        this.pasos = pasos;
        this.zemlje = zemlje;
        this.datumUlaska = datumUlaska;
        this.datumIzlaska = datumIzlaska;
        this.nacinPrevoza = nacinPrevoza;
    }
    
    

    public Putovanje(String jmbg, String pasos, String zemlje, LocalDate datumUlaska, LocalDate datumIzlaska, Prevoz nacinPrevoza) {
        this.jmbg = jmbg;
        this.pasos = pasos;
        this.zemlje = zemlje;
        this.datumUlaska = datumUlaska;
        this.datumIzlaska = datumIzlaska;
        this.nacinPrevoza = nacinPrevoza;
    }

    public Putovanje() {
    }

    public boolean isEditable() {
        return (ChronoUnit.DAYS.between(LocalDate.now(), datumUlaska) > 2);

    }

}
