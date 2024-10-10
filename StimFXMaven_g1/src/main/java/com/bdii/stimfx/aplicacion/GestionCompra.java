package com.bdii.stimfx.aplicacion;

import com.bdii.stimfx.baseDatos.FachadaBaseDatos;
import com.bdii.stimfx.gui.FachadaGUI;

import java.util.List;

public class GestionCompra {
    FachadaGUI fgui;
    FachadaBaseDatos fbd;


    public GestionCompra(FachadaGUI fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }



    public void compraVideojuego(Videojuego v, Usuario u){
        insertarCompra(v, u.getId());
        u.modificarDinero(-v.getPrecio());
    }

    public void insertarCompra(Videojuego v, String id_usr) {
        fbd.insertarCompra(v.getId(), v.getPrecio(), id_usr);
    }

    public void devolverVideojuego(Videojuego v, Usuario u) {
        devolverCompra(v, u);
        u.modificarDinero(v.getPrecio());
    }

    public void devolverCompra(Videojuego v, Usuario u)
    {
        fbd.devolverCompra(v.getId(), u.getId());
    }

    public int contarJuegosUsuario(String id_usuario){
        return fbd.contarJuegosUsuario(id_usuario);
    }

    public boolean tieneVideojuego(Usuario usuario, Videojuego videojuego){
        List<Videojuego> videojuegoUsuario = fbd.consultarVideojuegosUsuario(usuario.getId());
        return  videojuegoUsuario.contains(videojuego);
    }


}
