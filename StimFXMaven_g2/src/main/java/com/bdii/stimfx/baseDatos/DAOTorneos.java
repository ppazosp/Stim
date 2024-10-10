/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.baseDatos;

import com.bdii.stimfx.aplicacion.FachadaAplicacion;
import com.bdii.stimfx.aplicacion.Torneo;
import com.bdii.stimfx.aplicacion.Usuario;
import com.bdii.stimfx.aplicacion.Videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alumnogreibd
 */
public class DAOTorneos extends AbstractDAO{


    public DAOTorneos (Connection conexion, com.bdii.stimfx.aplicacion.FachadaAplicacion fa){  //MIRAR TEMA FECHAS, POR AHORA AUTOMATICO
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }



    public void insertarTorneo(Torneo t){
        Connection con;
        PreparedStatement stmTorneo=null;

        con=super.getConexion();

        try {
            stmTorneo=con.prepareStatement("insert into torneo(nombre, fecha_inicio, fecha_fin, premio, id_videojuego, id_usradmin) "+
                                            "values (?,?,?,?,?,?)");

            stmTorneo.setString(1, t.getNombre());
            stmTorneo.setDate(2, t.getFecha_inicio());
            stmTorneo.setDate(3, t.getFecha_final());
            stmTorneo.setInt(4, t.getPremio());
            stmTorneo.setInt(5, t.getVideojuego().getId());
            stmTorneo.setString(6, t.getAdministrador().getId());
            stmTorneo.executeUpdate();
        } catch (SQLException e){
          System.out.println(e.getMessage());
          FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
          try {
              assert stmTorneo != null;
              stmTorneo.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }

    public void updateTorneo(Torneo t) {
        Connection con;
        PreparedStatement stmVideojuego = null;

        con = super.getConexion();

        try {
            stmVideojuego = con.prepareStatement("update torneo set" +
                    " nombre = ?," +
                    " fecha_inicio = ?," +
                    " fecha_fin = ?," +
                    " premio = ?," +
                    " id_videojuego = ? " +
                    " where id = ? ");

            stmVideojuego.setInt(6, t.getId());
            stmVideojuego.setString(1, t.getNombre());
            stmVideojuego.setDate(2, t.getFecha_inicio());
            stmVideojuego.setDate(3, t.getFecha_final());
            stmVideojuego.setInt(4, t.getPremio());
            stmVideojuego.setInt(5, t.getVideojuego().getId());

            stmVideojuego.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        } finally {
            try {
                assert stmVideojuego != null;
                stmVideojuego.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    public Torneo consultarTorneo(Integer t_id){  // Sirve para la transaccion de obtener el videojuego asociado a un dlc y tmbn para obtener videojuegos asociados a una cartegoria
        Torneo torneo=null;
        Connection con;
        PreparedStatement stmTorneo=null;
        ResultSet rsTorneo;

        con=this.getConexion();

        String consulta = "select * "+
                "from torneo "+
                "where id = ?";


        try{
            stmTorneo=con.prepareStatement(consulta);
            stmTorneo.setInt(1, t_id);
            rsTorneo=stmTorneo.executeQuery();

            if (rsTorneo.next()){
                Videojuego v = new Videojuego(rsTorneo.getInt("id_videojuego"));
                Usuario u = new Usuario(rsTorneo.getString("id_usradmin"));

                torneo = new Torneo(rsTorneo.getInt("id"), rsTorneo.getString("nombre"), rsTorneo.getDate("fecha_inicio"),
                        rsTorneo.getDate("fecha_fin"), rsTorneo.getInt("premio"), rsTorneo.getString("ganador"),
                        v, u);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmTorneo != null;
                stmTorneo.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }

        return torneo;
    }

    public int torneosGanados(String id){
        int resultado = 0;
        Connection con;
        PreparedStatement stmUsuarios=null;
        ResultSet rsUsuarios;

        con=this.getConexion();

        String consulta = "select count(*) as torneos_ganados\n" +
                "from torneo \n" +
                "where ganador like ?";



        try  {
            stmUsuarios=con.prepareStatement(consulta);
            stmUsuarios.setString(1, id);
            rsUsuarios=stmUsuarios.executeQuery();

            if (rsUsuarios.next())
            {
                resultado = rsUsuarios.getInt("torneos_ganados");

            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                if (stmUsuarios != null) {
                    stmUsuarios.close();
                }
            } catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }

    public List<Torneo> consultarTorneos(String nombre) {
        List<Torneo> resultado = new ArrayList<>();
        Torneo torneoActual;
        Connection con;
        PreparedStatement stmTorneos=null;
        ResultSet rsTorneos;

        con=this.getConexion();

        try{
            stmTorneos=con.prepareStatement(" select t.id, t.nombre, fecha_inicio, fecha_fin, premio, ganador, id_videojuego, v.nombre as v_nombre, id_usradmin, imagen " +
                                                "from torneo t join videojuego v on id_videojuego = v.id " +
                    " where LOWER(t.nombre) like LOWER(?)" +
                    "order by t.nombre");
            stmTorneos.setString(1, "%"+nombre+"%");
            rsTorneos=stmTorneos.executeQuery();
            while (rsTorneos.next())
            {
                Videojuego v = new Videojuego(rsTorneos.getInt("id_videojuego"), rsTorneos.getString("v_nombre"),
                        FachadaAplicacion.bytesToImage(rsTorneos.getBytes("imagen")));
                Usuario u = new Usuario(rsTorneos.getString("id_usradmin"));
                torneoActual = new Torneo(rsTorneos.getInt("id"), rsTorneos.getString("nombre"), rsTorneos.getDate("fecha_inicio"),
                rsTorneos.getDate("fecha_fin"), rsTorneos.getInt("premio"), rsTorneos.getString("ganador"), v, u);
                resultado.add(torneoActual);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmTorneos != null;
                stmTorneos.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }

        return resultado;
    }

    public List<Torneo> consultarTorneosAdmin(String a_id) {
        List<Torneo> resultado = new ArrayList<>();
        Torneo torneoActual;
        Connection con;
        PreparedStatement stmTorneos=null;
        ResultSet rsTorneos;

        con=this.getConexion();

        try{
            stmTorneos=con.prepareStatement(" select t.id, t.nombre, fecha_inicio, fecha_fin, premio, ganador, id_videojuego, v.nombre as v_nombre, id_usradmin, imagen " +
                    "from torneo t join videojuego v on id_videojuego = v.id " +
                    " where t.id_usradmin like ? " +
                    "order by t.fecha_inicio desc");

            stmTorneos.setString(1, "%"+a_id+"%");
            rsTorneos=stmTorneos.executeQuery();
            while (rsTorneos.next())
            {
                Videojuego v = new Videojuego(rsTorneos.getInt("id_videojuego"), rsTorneos.getString("v_nombre"),
                        FachadaAplicacion.bytesToImage(rsTorneos.getBytes("imagen")));
                Usuario u = new Usuario(rsTorneos.getString("id_usradmin"));
                torneoActual = new Torneo(rsTorneos.getInt("id"), rsTorneos.getString("nombre"), rsTorneos.getDate("fecha_inicio"),
                        rsTorneos.getDate("fecha_fin"), rsTorneos.getInt("premio"), rsTorneos.getString("ganador"), v, u);
                resultado.add(torneoActual);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmTorneos != null;
                stmTorneos.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }

        return resultado;
    }

    public void participarTorneo(String u_id, int t_id) {
        Connection con;
        PreparedStatement stmTorneo=null;

        con=super.getConexion();

        try {
            stmTorneo=con.prepareStatement("insert into jug_participa_torneo(id_jugador, id_torneo) "+
                    "values (?,?)");

            stmTorneo.setString(1, u_id);
            stmTorneo.setInt(2, t_id);

            stmTorneo.executeUpdate();

        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmTorneo != null;
                stmTorneo.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }

    }

    public void retirarseTorneo(String u_id, int t_id) {
        Connection con;
        PreparedStatement stmTorneo=null;

        con=super.getConexion();

        try {
            stmTorneo=con.prepareStatement("delete from jug_participa_torneo " +
                    "where id_jugador = ? and id_torneo = ? ");

            stmTorneo.setString(1, u_id);
            stmTorneo.setInt(2, t_id);

            stmTorneo.executeUpdate();

        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmTorneo != null;
                stmTorneo.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }

    public List<Usuario> consultarParticipantes(int t_id) {
        List<Usuario> resultado = new ArrayList<>();
        Connection con;
        PreparedStatement stmParticipantes=null;
        ResultSet rsTorneos;

        con=this.getConexion();

        try{
            stmParticipantes=con.prepareStatement(" select u.id from usuario u " +
                    "where u.id in " +
                    "(select id_jugador from jug_participa_torneo where id_torneo = ?)  ");

            stmParticipantes.setInt(1, t_id);
            rsTorneos=stmParticipantes.executeQuery();
            while (rsTorneos.next())
            {
                Usuario u = new Usuario(rsTorneos.getString("id"));

                resultado.add(u);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmParticipantes != null;
                stmParticipantes.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }

        return resultado;
    }

    public void setGanador(String u_id, int t_id) {
        Connection con;
        PreparedStatement stmTorneo = null;

        con = super.getConexion();

        try {
            stmTorneo = con.prepareStatement("update torneo " +
                    "set ganador = ? " +
                    "where id = ? ");

            stmTorneo.setString(1, u_id);
            stmTorneo.setInt(2, t_id);

            stmTorneo.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        } finally {
            try {
                assert stmTorneo != null;
                stmTorneo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

}