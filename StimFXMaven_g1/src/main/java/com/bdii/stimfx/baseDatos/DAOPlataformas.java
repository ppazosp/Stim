/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.baseDatos;
import com.bdii.stimfx.aplicacion.FachadaAplicacion;
import com.bdii.stimfx.aplicacion.Plataforma;

import java.sql.Connection;


import java.sql.*;

/**
 *
 * @author alumnogreibd
 */
public class DAOPlataformas extends AbstractDAO{


    public DAOPlataformas (Connection conexion, com.bdii.stimfx.aplicacion.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }



    public java.util.List<Plataforma> consultarPlataformasVideoJuego(int id){
        java.util.List<Plataforma> resultado = new java.util.ArrayList<>();
        Plataforma plataformaActual;
        Connection con;
        PreparedStatement stmPlataformas=null;
        ResultSet rsPlataformas;

        con=this.getConexion();

        String consulta = "SELECT ptv.nombre_plataforma, p.icono\n" +
                "FROM videojuego v\n" +
                "JOIN plataforma_tiene_videojuego ptv ON v.id = ptv.id_videojuego \n" +
                "JOIN plataforma p ON ptv.nombre_plataforma = p.nombre\n" +
                "WHERE v.id = ?;";

        try  {
            stmPlataformas=con.prepareStatement(consulta);
            stmPlataformas.setInt(1, id);
            rsPlataformas=stmPlataformas.executeQuery();
            while (rsPlataformas.next())
            {
                plataformaActual = new Plataforma(rsPlataformas.getString("nombre_plataforma"),
                        FachadaAplicacion.bytesToImage(rsPlataformas.getBytes("icono")));

                resultado.add(plataformaActual);
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmPlataformas != null;
                stmPlataformas.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }

}