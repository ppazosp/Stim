/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.baseDatos;

import com.bdii.stimfx.aplicacion.Comunidad;
import com.bdii.stimfx.aplicacion.FachadaAplicacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alumnogreibd
 */
public class DAOComunidades extends AbstractDAO{

    public DAOComunidades(Connection conexion, FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }


    public java.util.List<Comunidad> consultarComunidades(String nombre){
        java.util.List<Comunidad> resultado = new java.util.ArrayList<>();
        Comunidad comunidadActual;
        Connection con;
        PreparedStatement stmEquipos=null;
        ResultSet rsEquipos;

        con=this.getConexion();

        String consulta = "select * from comunidad ";

        if (nombre != null) consulta += "where LOWER(nombre) like LOWER(?) ";


        try  {
            stmEquipos=con.prepareStatement(consulta);
            if (nombre != null) stmEquipos.setString(1, "%"+nombre+"%");
            rsEquipos=stmEquipos.executeQuery();
            while (rsEquipos.next())
            {
                comunidadActual = new Comunidad(rsEquipos.getString("nombre"),FachadaAplicacion.bytesToImage(rsEquipos.getBytes("escudo")));

                resultado.add(comunidadActual);
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                if (stmEquipos != null) {
                    stmEquipos.close();
                }
            } catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }

    public void insertarJugadorEquipo(String id_usuario, Comunidad c){
        Connection con;
        PreparedStatement stmEquipo=null;

        con=super.getConexion();

        try {
            stmEquipo=con.prepareStatement("insert into forma_parte_equipo(id_jugador, nombre_equipo, fecha_inicio) "+
                    "values (?,?, current_timestamp)");

            stmEquipo.setString(1, id_usuario);
            stmEquipo.setString(2, c.getNombre());
            stmEquipo.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmEquipo != null;
                stmEquipo.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }

    public void salirJugadorEquipo(String id_usuario){
        Connection con;
        PreparedStatement stmEquipo=null;

        con=super.getConexion();

        try {
            stmEquipo=con.prepareStatement("update forma_parte_equipo " +
                                                "set fecha_fin = current_timestamp " +
                                                "where id_jugador = ? " +
                                                "and fecha_fin is null");

            stmEquipo.setString(1, id_usuario);
            stmEquipo.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmEquipo != null;
                stmEquipo.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }

    public Comunidad consultarEquipoJugador(String id_usuario){
        Comunidad resultado=null;
        Connection con;
        PreparedStatement stmEquipos=null;
        ResultSet rsEquipos;

        con=this.getConexion();

        try  {
            stmEquipos=con.prepareStatement("select nombre, escudo from comunidad " +
                                                "where nombre in " +
                                                "(select nombre_equipo from forma_parte_equipo " +
                                                "where id_jugador like ? and fecha_fin is null)");
            stmEquipos.setString(1, id_usuario);
            rsEquipos=stmEquipos.executeQuery();
            while (rsEquipos.next())
            {
                resultado = new Comunidad(rsEquipos.getString("nombre"), FachadaAplicacion.bytesToImage(rsEquipos.getBytes("escudo")));
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                if (stmEquipos != null) {
                    stmEquipos.close();
                }
            } catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }

    public int contarMiembrosEquipo(Comunidad c){
        int miembrosEquipo=0;
        Connection con;
        PreparedStatement stmEquipos=null;
        ResultSet rsEquipos;

        con=this.getConexion();

        String consulta = "select count(*) "+
                "from forma_parte_equipo "+
                "where nombre_equipo like ? "+
                "and fecha_fin is null";

        try {
            stmEquipos=con.prepareStatement(consulta);
            stmEquipos.setString(1, c.getNombre());
            rsEquipos=stmEquipos.executeQuery();

            if (rsEquipos.next()) {
                miembrosEquipo = rsEquipos.getInt(1);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmEquipos != null;
                stmEquipos.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return miembrosEquipo;
    }

    public boolean tieneComunidad(String id_usr){
        boolean tieneComunidad=false;
        Connection con;
        PreparedStatement stmEquipos=null;
        ResultSet rsEquipos;

        con=this.getConexion();

        String consulta = "select * from forma_parte_equipo " +
                "where  id_jugador = ? and fecha_fin is null";

        try {
            stmEquipos=con.prepareStatement(consulta);
            stmEquipos.setString(1, id_usr);
            rsEquipos=stmEquipos.executeQuery();

            if (rsEquipos.next()) {
               tieneComunidad= true;
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmEquipos != null;
                stmEquipos.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return tieneComunidad;
    }

}

