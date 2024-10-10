/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.aplicacion;

import com.bdii.stimfx.baseDatos.FachadaBaseDatos;
import com.bdii.stimfx.gui.FachadaGUI;


public class GestionTorneos {
    FachadaGUI fgui;
    FachadaBaseDatos fbd;


    public GestionTorneos(FachadaGUI fgui, FachadaBaseDatos fbd){
        this.fgui=fgui;
        this.fbd=fbd;
    }



    public int torneosGanados(Usuario u){
        return fbd.torneosGanados(u.getId());
    }

}
