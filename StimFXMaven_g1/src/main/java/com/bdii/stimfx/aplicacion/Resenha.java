/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.aplicacion;
//import java.time.LocalDate;

/**
 *
 * @author alumnogreibd
 */
public class Resenha {
    private final int id_videojuego;
    private final String id_usuario;
    private final int idResenha;
    private final String comentario;
    private final int valoracion;
    private int likes;


    public Resenha(int id_videojuego, int idResenha, String id_usuario, String comentario, int valoracion) {
        this.id_videojuego = id_videojuego;
        this.id_usuario = id_usuario;
        this.idResenha = idResenha;
        this.comentario = comentario;
        this.valoracion = valoracion;
    }


    public Resenha(String texto, int valoracion, String id_usr, int id_juego) {
        this.id_videojuego = id_juego;
        this.idResenha = -1;
        this.comentario = texto;
        this.valoracion = valoracion;
        this.id_usuario = id_usr;
    }




    public int getIdResenha() {
        return idResenha;
    }

    public String getComentario() {
        return comentario;
    }

    public int getValoracion() {
        return valoracion;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public int getId_videojuego() {
        return id_videojuego;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
