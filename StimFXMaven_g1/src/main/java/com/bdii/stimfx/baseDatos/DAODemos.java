package com.bdii.stimfx.baseDatos;
import java.sql.Connection;

import com.bdii.stimfx.aplicacion.*;


import java.sql.*;

/**
 *
 * @author alumnogreibd
 */
public class DAODemos extends AbstractDAO {


    public DAODemos(Connection conexion, FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }



    public Demo consultarDemo(int mes, int ano) {
        Connection con;
        PreparedStatement stmDemo=null;
        ResultSet rsDemos;
        Demo resultado = null;

        con=this.getConexion();

        String consulta = "select nombre, mes, ano, imagen, id_usradmin, url" +
                "   from demo  \n" +
                "   where mes = ? and ano = ?;";

        try{
            stmDemo=con.prepareStatement(consulta);
            stmDemo.setInt(1, mes);
            stmDemo.setInt(2, ano);
            rsDemos=stmDemo.executeQuery();
            while (rsDemos.next())
            {
                resultado = new Demo(rsDemos.getString("nombre"),
                        rsDemos.getInt("mes"), rsDemos.getInt("ano"),
                        FachadaAplicacion.bytesToImage(rsDemos.getBytes("imagen")),
                        rsDemos.getString("id_usradmin"), rsDemos.getString("url"));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmDemo != null;
                stmDemo.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }

}


