/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.aplicacion;
import java.util.Objects;


public class DLC {
    private final int idVideojuego;
    private final int idDLC;
    private final String nombre;
    private final double precio;


    public DLC(int idVideojuego, int idDLC, String nombre, double precio) {
        this.idVideojuego = idVideojuego;
        this.idDLC = idDLC;
        this.nombre = nombre;
        this.precio= precio;
    }



    public int getIdVideojuego() {
        return idVideojuego;
    }

    public int getIdDLC() {
        return idDLC;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DLC dlc = (DLC) o;
        return idVideojuego == dlc.idVideojuego && idDLC == dlc.idDLC;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVideojuego, idDLC);
    }
}
