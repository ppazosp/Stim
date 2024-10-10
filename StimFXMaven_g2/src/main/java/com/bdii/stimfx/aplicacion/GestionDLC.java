package com.bdii.stimfx.aplicacion;

import com.bdii.stimfx.baseDatos.FachadaBaseDatos;
import com.bdii.stimfx.gui.FachadaGUI;

public class GestionDLC {
    FachadaGUI fgui;
    FachadaBaseDatos fbd;


    public GestionDLC(FachadaGUI fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }



    public void insertarDLC(DLC d){
        fbd.insertarDLC(d);
    }

    public void updateDLC(DLC d)
    {
        fbd.updateDLC(d);
    }

    public java.util.List<DLC> consultarDLCsVideojuego(Videojuego v){
        return fbd.consultarDLCsVideojuego(v);
    }

    public void publicarDLC(DLC d) {
        if (existsDLC(d)) updateDLC(d);
        else insertarDLC(d);
    }

    public boolean existsDLC(DLC d) {
        return fbd.consultarDLC(d.getIdVideojuego(), d.getIdDLC()) != null;
    }
}
