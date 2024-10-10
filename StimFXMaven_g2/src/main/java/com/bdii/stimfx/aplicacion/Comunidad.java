
package com.bdii.stimfx.aplicacion;


import javafx.scene.image.Image;

public class Comunidad {
    private final String nombre;
    private Image escudo;


    public Comunidad(String nombre, Image escudo) {
        this.nombre = nombre;
        this.escudo = escudo;
    }



    public Comunidad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Image getEscudo() {
        return escudo;
    }

}
