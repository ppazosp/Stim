package com.bdii.stimfx.aplicacion;

import com.bdii.stimfx.baseDatos.FachadaBaseDatos;
import com.bdii.stimfx.gui.FachadaGUI;

import java.util.List;

public class GestionDemo {
    FachadaGUI fgui;
    FachadaBaseDatos fbd;


    public GestionDemo(FachadaGUI fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }



    public void insertarDemo(Demo d)
    {
        fbd.insertarDemo(d);
    }

    public void updateDemo(Demo d)
    {
        fbd.updateDemo(d);
    }

    public List<Demo> consultarDemoAdmin(Usuario u)
    {
        return fbd.consultarDemoAdmin(u.getId());
    }

    public boolean existsDemo(Demo d) {
        return fbd.consultarDemo(d.getMes(), d.getAno()) != null;
    }

    public void publicarDemo(Demo d) {
        if (existsDemo(d)) updateDemo(d);
        else insertarDemo(d);
    }
}
