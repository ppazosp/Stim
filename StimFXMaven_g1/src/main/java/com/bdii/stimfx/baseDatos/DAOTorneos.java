/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.baseDatos;

import com.bdii.stimfx.aplicacion.FachadaAplicacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alumnogreibd
 */
public class DAOTorneos extends AbstractDAO{


    public DAOTorneos (Connection conexion, com.bdii.stimfx.aplicacion.FachadaAplicacion fa){  //MIRAR TEMA FECHAS, POR AHORA AUTOMATICO
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
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

}