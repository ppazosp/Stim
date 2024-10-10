/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.baseDatos;

import com.bdii.stimfx.aplicacion.DLC;
import com.bdii.stimfx.aplicacion.FachadaAplicacion;
import com.bdii.stimfx.aplicacion.Videojuego;

import java.sql.*;

/**
 *
 * @author alumnogreibd
 */
public class DAODLCs extends AbstractDAO {


    public DAODLCs(Connection conexion, com.bdii.stimfx.aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }



    public java.util.List<DLC> consultarDLCsVideojuego(Videojuego v) {
        java.util.List<DLC> resultado = new java.util.ArrayList<>();
        DLC dlcActual;
        Connection con;
        PreparedStatement stmDLC = null;
        ResultSet rsDLC;

        con = this.getConexion();

        String consulta = "select * " +
                "from dlc " +
                "where id_videojuego = ?";


        try {
            stmDLC = con.prepareStatement(consulta);
            stmDLC.setInt(1, v.getId());
            rsDLC = stmDLC.executeQuery();
            while (rsDLC.next()) {
                dlcActual = new DLC(v.getId(), rsDLC.getInt("id_dlc"), rsDLC.getString("nombre"),
                         rsDLC.getInt("precio"));
                resultado.add(dlcActual);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        } finally {
            try {
                assert stmDLC != null;
                stmDLC.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return resultado;
    }

    public java.util.List<DLC> consultarDLCsVideojuegoUsuario(int id_v, String id_u) {
        java.util.List<DLC> resultado = new java.util.ArrayList<>();
        DLC dlcActual;
        Connection con;
        PreparedStatement stmDLC = null;
        ResultSet rsDLC;

        con = this.getConexion();

        String consulta = "select * " +
                "from dlc  d inner join comprardlc cd on d.id_dlc = cd.id_dlc" +//tabla comprar dlc
                " where d.id_videojuego = ? and id_usr = ?";


        try {
            stmDLC = con.prepareStatement(consulta);
            stmDLC.setInt(1, id_v);
            stmDLC.setString(2, id_u);
            rsDLC = stmDLC.executeQuery();
            while (rsDLC.next()) {
                dlcActual = new DLC(id_v, rsDLC.getInt("id_dlc"), rsDLC.getString("nombre"),
                        rsDLC.getInt("precio"));
                resultado.add(dlcActual);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        } finally {
            try {
                assert stmDLC != null;
                stmDLC.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return resultado;
    }

    public void comprarDLC(DLC d, String id_u, Date date) {
        Connection con;
        PreparedStatement stmDLC = null, stmMonedero = null;

        con = super.getConexion();

        try {
            stmDLC = con.prepareStatement("insert into comprardlc(id_videojuego, id_dlc, id_usr, fechacompra) " +
                    "values (?,?,?,?)");

            stmDLC.setInt(1, d.getIdVideojuego());
            stmDLC.setInt(2, d.getIdDLC());
            stmDLC.setString(3, id_u);
            stmDLC.setDate(4, date);
            stmDLC.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        } finally {
            try {
                assert stmDLC != null;
                stmDLC.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        try {
            stmMonedero = con.prepareStatement("UPDATE monedero\n" +
                    "SET dinero = dinero - ?\n" +
                    "WHERE id_comun = ?;");//Cambiar insert

            stmMonedero.setDouble(1, d.getPrecio());
            stmMonedero.setString(2, id_u);
            stmMonedero.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        } finally {
            try {
                assert stmMonedero != null;
                stmMonedero.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

}
