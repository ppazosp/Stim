package com.bdii.stimfx.aplicacion;

import com.bdii.stimfx.baseDatos.FachadaBaseDatos;
import com.bdii.stimfx.gui.FachadaGUI;

public class GestionDemo {
    FachadaGUI fgui;
    FachadaBaseDatos fbd;


    public GestionDemo(FachadaGUI fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }




    public Demo consultarDemo(int mes, int ano)
    {
        return fbd.consultarDemo(mes, ano);
    }

}
