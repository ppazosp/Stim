package com.bdii.stimfx.aplicacion;

import javafx.scene.image.Image;

public class Demo {
    private final String nombre;
    private final int mes;
    private final int ano;
    private final Image imagen;
    private final String id_usreditor;
    private final String url;


    public Demo(String nombre, int mes, int ano, Image imagen, String id_usreditor, String url)
    {
        this.nombre = nombre;
        this.mes = mes;
        this.ano = ano;
        this.imagen = imagen;
        this.id_usreditor = id_usreditor;
        this.url = url;
    }



    public String getNombre() {
        return nombre;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public Image getImagen() {
        return imagen;
    }

    public String getId_usreditor() {
        return id_usreditor;
    }

    public String getUrl() {
        return url;
    }

}
