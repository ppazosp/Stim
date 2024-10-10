/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.aplicacion;

import com.bdii.stimfx.baseDatos.FachadaBaseDatos;
import com.bdii.stimfx.gui.FachadaGUI;

import java.util.List;

/**
 *
 * @author alumnogreibd
 */
public class GestionVideojuegos {
    
    FachadaGUI fgui;
    FachadaBaseDatos fbd;
    
   
    public GestionVideojuegos(FachadaGUI fgui, FachadaBaseDatos fbd){
     this.fgui=fgui;
     this.fbd=fbd;
    }



    public java.util.List<Videojuego> consultarVideojuegos(String n){
        return fbd.consultarVideojuegos(n);
    }

    public List<Videojuego> consultaVideoJuegosInicio(){
        return fbd.consultaVideoJuegosInicio();
    }

    public Videojuego proximoVideojuego(){
        return fbd.proximoVideojuego();
    }

    public List<Plataforma> consultarPlataformasVideoJuego(Videojuego v){
        return fbd.consultarPlataformasVideoJuego(v.getId());
    }

}
