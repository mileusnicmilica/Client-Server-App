/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Milica
 */
public class Korisnik implements Serializable {

    private String username;
    private String password;
    private String ime;
    private String prezime;
    private String email;
    private String jmbg;
    private String pasos;

    public Korisnik(String username, String password, String ime, String prezime, String email, String jmbg, String pasos) {
        this.username = username;
        this.password = password;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.jmbg = jmbg;
        this.pasos = pasos;
    }

    public Korisnik() {
    }

    public Korisnik(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Korisnik(String ime, String prezime, String jmbg, String pasos) {
        this.ime = ime;
        this.prezime = prezime;
        this.jmbg = jmbg;
        this.pasos = pasos;
    }

    
    
    

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getEmail() {
        return email;
    }

    public String getJmbg() {
        return jmbg;
    }

    public String getPasos() {
        return pasos;
    }
}
