/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.aplicacion;

import com.bdii.stimfx.baseDatos.FachadaBaseDatos;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import com.bdii.stimfx.gui.FachadaGUI;
import javafx.scene.image.Image;


public class GestionUsuarios {
    FachadaGUI fgui;
    FachadaBaseDatos fbd;


    public GestionUsuarios(FachadaGUI fgui, FachadaBaseDatos fbd){
        this.fgui=fgui;
        this.fbd=fbd;
    }



    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            md.update(password.getBytes());
            
            // bytes hasheados
            byte[] hashedBytes = md.digest();
            
            // Converison a base 64
            String hashedPassword = Base64.getEncoder().encodeToString(hashedBytes);
            //le quitamos el '=' que esta al final del base64 ya que no aporta nada
            return hashedPassword.substring(0, hashedPassword.length() - 1);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public Usuario comprobarAutentificacion(String idUsuario, String clave){
        Usuario u;
        //hashear en el futuro
        String hashedPassword= hashPassword(clave);
        u=fbd.validarUsuario(idUsuario, hashedPassword);
        return u;
    }

    public Usuario registrarUsuario(String id, String clave, String nombre, String email){
        Usuario usuario;
        if (fbd.existeUsuario(id)){
            System.out.println("El usuario ya existe");
            return null;
        }
        else{
            String hashedPassword= hashPassword(clave);
            usuario = new Usuario(id, nombre, hashedPassword, email);
            fbd.insertarUsuario(usuario);
        }
        return usuario;
    }

    public void hacerAdmin(Usuario u)
    {
        fbd.hacerAdmin(u.getId());
    }

    public void hacerJugadorCompetitivo(Usuario u)
    {
        fbd.hacerJugadorCompetitivo(u.getId());
    }

    public void hacerEditor(Usuario u)
    {
        fbd.hacerEditor(u.getId());
    }

    public Usuario modificarUsuario(String id, String nombre, String claveAntigua, String clave, String email, Image imagen){
        Usuario usuario;
        if (claveAntigua.equals(clave)){
            usuario = new Usuario(id, nombre, clave, email, imagen);}
        //Si cambio contraseña rehshear
        else{
            usuario = new Usuario(id, nombre, hashPassword(clave), email, imagen);
        }
        return fbd.modificarUsuario(usuario);
    }

    public java.util.List<Usuario> consultarUsuariosNoAdmins(){
        return fbd.consultarUsuariosNoAdmins();
    }

}
