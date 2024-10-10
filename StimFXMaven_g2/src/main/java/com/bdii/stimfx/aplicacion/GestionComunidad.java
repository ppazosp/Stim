package com.bdii.stimfx.aplicacion;

import com.bdii.stimfx.baseDatos.FachadaBaseDatos;
import com.bdii.stimfx.gui.FachadaGUI;

public class GestionComunidad {
    FachadaGUI fgui;
    FachadaBaseDatos fbd;


    public GestionComunidad(FachadaGUI fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }



    public java.util.List<Comunidad> consultarComunidades(String nombre){
        return fbd.consultarComunidades(nombre);
    }

    public void insertarJugadorEquipo(String id_usuario, Comunidad c){
        fbd.insertarJugadorEquipo(id_usuario, c);
    }

    public void salirJugadorEquipo(String id_usuario){
        fbd.salirJugadorEquipo(id_usuario);
    }

    public Comunidad consultarEquipoJugador(String id_usuario){
        return fbd.consultarEquipoJugador(id_usuario);
    }

    public int contarMiembrosEquipo(Comunidad c) {
        return fbd.contarMiembrosEquipo(c);
    }

    public boolean tieneComunidad(Usuario u){
        return fbd.tieneComunidad(u.getId());
    }

}
