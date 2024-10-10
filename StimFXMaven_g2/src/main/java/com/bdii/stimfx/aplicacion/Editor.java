/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.aplicacion;

/**
 *
 * @author alumnogreibd
 */
public class Editor extends Usuario {

    public Editor(String id, String nombre, String contrasena, String TipoUsuario, String email) {
        super(id, nombre, contrasena, email);
    }

    public Editor(String idUsr) {
        super(idUsr);
    }
}
