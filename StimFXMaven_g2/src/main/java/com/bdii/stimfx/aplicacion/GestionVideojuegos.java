/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.aplicacion;

import com.bdii.stimfx.baseDatos.FachadaBaseDatos;
import com.bdii.stimfx.gui.FachadaGUI;


public class GestionVideojuegos {
    
    FachadaGUI fgui;
    FachadaBaseDatos fbd;
    
   
    public GestionVideojuegos(FachadaGUI fgui, FachadaBaseDatos fbd){
     this.fgui=fgui;
     this.fbd=fbd;
    }



    public void insertarVideojuego(Videojuego v){
        fbd.insertarVideojuego(v);
    }

    public void updateVideojuego(Videojuego v) {
        fbd.updateVideojuego(v);
    }

    public void publicarVideojuego(Videojuego v) {
        if (existsVideojuego(v)) updateVideojuego(v);
        else insertarVideojuego(v);
    }

    public boolean existsVideojuego(Videojuego v) {
        return fbd.consultarVideojuego(v.getId()) != null;
    }

    public Videojuego consultarVideojuego(String n){
        return fbd.consultarVideojuego(n);
    }

}
