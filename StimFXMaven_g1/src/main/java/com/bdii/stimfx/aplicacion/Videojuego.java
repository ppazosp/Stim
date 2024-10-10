/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.aplicacion;

import javafx.scene.image.Image;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 *
 * @author alumnogreibd
 */
public class Videojuego {
    private final int id;
    private String nombre;
    private Date fechaSubida;
    private Editor editor;
    private String descripcion;
    private List<DLC> DLCs;
    private double precio;
    private int numDescargas;
    private List<Resenha> resenhas;
    private Image imagen;
    private Image banner;
    private String trailer;


    public Videojuego(int id, String nombre, Date fechaSubida, String descripcion, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.fechaSubida = fechaSubida;
        this.descripcion = descripcion;
        this.DLCs = new ArrayList<>();
        this.precio= precio;
        this.numDescargas=0;
    }


    public Videojuego(int id, String nombre, Date fechaSubida, String descripcion, double precio, Image imagen, Image banner, String trailer) {
        this.id = id;
        this.nombre = nombre;
        this.fechaSubida = fechaSubida;
        this.descripcion = descripcion;
        this.precio= precio;
        this.numDescargas=0;
        this.imagen = imagen;
        this.banner = banner;
        this.trailer = trailer;
        this.DLCs = new ArrayList<>();
        this.resenhas = new ArrayList<>();
    }


    public Videojuego(int id, String nombre, Date fechaSubida, Editor editor, String descripcion, double precio, Image imagen, Image banner, String trailer) {
        this.id = id;
        this.nombre = nombre;
        this.fechaSubida = fechaSubida;
        this.editor = editor;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.banner = banner;
        this.trailer = trailer;
        this.DLCs = new ArrayList<>();
        this.resenhas = new ArrayList<>();
    }


    public Videojuego(int id, String nombre, Date fechaSubida, String descripcion, double precio,List<DLC> DLCs) {
        this.id = id;
        this.nombre = nombre;
        this.fechaSubida = fechaSubida;
        this.descripcion = descripcion;
        this.precio= precio;
        this.DLCs = DLCs;
        this.numDescargas=0;
    }


    public Videojuego(int id, String nombre, String descripcion, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.DLCs = new ArrayList<>();
        this.precio= precio;
        this.numDescargas=0;
    }


    public Videojuego(int id, String nombre, Image imagen) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
    }


    public Videojuego(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }


    public Videojuego(int id)
    {
        this.id = id;
    }



    public void setNumDescargas(int numDescargas) {
        this.numDescargas = numDescargas;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }
    
    public void addDLC(DLC dlc){
        this.DLCs.add(dlc);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFechaSubida() {
        return fechaSubida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public List<Resenha> getResenhas() {
        return resenhas;
    }

    public void setResenhas(List<Resenha> resenhas) {
        this.resenhas = resenhas;
    }

    public void addResenha(Resenha rs){
        this.resenhas.add(rs);
    }

    public Image getImagen() {
        return imagen;
    }

    public Image getBanner() {
        return banner;
    }

    public int getNumDescargas() {
        return numDescargas;
    }

    public String getTrailer() {
        return trailer;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Videojuego that = (Videojuego) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
