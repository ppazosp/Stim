package com.bdii.stimfx.aplicacion;

import com.bdii.stimfx.baseDatos.FachadaBaseDatos;
import com.bdii.stimfx.gui.FachadaGUI;

public class GestionPlataforma {
    FachadaGUI fgui;
    FachadaBaseDatos fbd;


    public GestionPlataforma(FachadaGUI fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }



    public java.util.List<Plataforma> consultarPlataformas(){
        return fbd.consultarPlataformas();
    }

    public boolean hasPlataforma(Videojuego v, Plataforma p){
        return fbd.hasPlataforma(v.getId(), p.getNombre());
    }

    public void insertarPlataformaVideojuego(Plataforma p, Videojuego v){
        fbd.insertarPlataformaVideojuego(p.getNombre(), v.getId());
    }

    public void borrarPlataformaVideojuego(Plataforma p, Videojuego v){
        fbd.borrarPlataformaVideojuego(p.getNombre(), v.getId());
    }
}
