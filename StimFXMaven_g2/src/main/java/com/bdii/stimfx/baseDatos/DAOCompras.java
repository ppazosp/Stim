/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.baseDatos;

import java.sql.Connection;
import com.bdii.stimfx.aplicacion.FachadaAplicacion;
import java.sql.*;


public class DAOCompras extends AbstractDAO{


    public DAOCompras (Connection conexion, com.bdii.stimfx.aplicacion.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }



    public Integer contarJuegosUsuario(String id_usuario){
        int juegosUsuario=0;
        Connection con;
        PreparedStatement stmCompras=null;
        ResultSet rsCompras;

        con=this.getConexion();

        String consulta = "select count(*) "+
                "from comprar "+
                "where id_usr::text like ? ";

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

}