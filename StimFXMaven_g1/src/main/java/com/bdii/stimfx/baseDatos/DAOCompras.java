/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.baseDatos;
import java.sql.Connection;

import com.bdii.stimfx.aplicacion.FachadaAplicacion;


import java.sql.*;

/**
 *
 * @author alumnogreibd
 */
public class DAOCompras extends AbstractDAO{


    public DAOCompras (Connection conexion, com.bdii.stimfx.aplicacion.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }



    public void insertarCompra(int id_videojuego, double precio, String id_usuario){
        Connection con;
        PreparedStatement stmCompra=null, stmMonedero = null;
        
        con=super.getConexion();
        
        try {
            stmCompra=con.prepareStatement("insert into comprar(id_videojuego, id_usr, fecha_compra) "+
                                            "values (?,?,current_timestamp)");
            
            stmCompra.setInt(1, id_videojuego);
            stmCompra.setString(2, id_usuario);
            stmCompra.executeUpdate();
        } catch (SQLException e){
          System.out.println(e.getMessage());
          FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
          try {
              assert stmCompra != null;
              stmCompra.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        try {
            stmMonedero = con.prepareStatement("UPDATE monedero\n" +
                    "SET dinero = dinero - ?\n" +
                    "WHERE id_comun = ?;");

            stmMonedero.setDouble(1, precio);
            stmMonedero.setString(2, id_usuario);
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

    public int contarJuegosUsuario(String id_usuario){
        int juegosUsuario=0;
        Connection con;
        PreparedStatement stmCompras=null;
        ResultSet rsCompras;

        con=this.getConexion();

        String consulta = "select count(*) "+
                "from comprar "+
                "where id_usr like ? and fecha_devolucion is null ";

        try {
            stmCompras=con.prepareStatement(consulta);
            stmCompras.setString(1, "%"+id_usuario+"%");
            rsCompras=stmCompras.executeQuery();

            if (rsCompras.next()) {
                juegosUsuario = rsCompras.getInt(1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmCompras != null;
                stmCompras.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return juegosUsuario;
    }

    public void devolverCompra(int id_v, String id_usr) {
        Connection con;
        PreparedStatement stmCompra = null;

        con = super.getConexion();

        try {
            stmCompra = con.prepareStatement("update comprar set fecha_devolucion = ? " +
                    "where id_videojuego = ? and id_usr  = ? and fecha_devolucion is null");

            java.sql.Date fechaActual = new java.sql.Date(System.currentTimeMillis());

            stmCompra.setInt(2, id_v);
            stmCompra.setString(3, id_usr);
            stmCompra.setDate(1, fechaActual);
            stmCompra.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        } finally {
            try {
                assert stmCompra != null;
                stmCompra.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

}