package com.example.veterinaria2_lucas;

import kotlin.text.UStringsKt;

public class Mascota {

    /**
     * Atributos de clase (inaccesibles)
     */
    private int idmascota;

    private String nombre;

    private String tipo;
    private String raza;
    private Double peso;
    private String color;

    /**
     * Encapsulamiento (GET/SET)
     */

    public int getIdmascota(){
        return idmascota;
    }

    public void setIdmascota(int idmascota) {
        this.idmascota = idmascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
