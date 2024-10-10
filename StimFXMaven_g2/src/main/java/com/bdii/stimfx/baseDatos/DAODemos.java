package com.bdii.stimfx.baseDatos;
import java.util.ArrayList;
import java.sql.Connection;

import com.bdii.stimfx.aplicacion.*;


import java.sql.*;
import java.util.List;

/**
 *
 * @author alumnogreibd
 */
public class DAODemos extends AbstractDAO {

    public DAODemos(Connection conexion, FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }



    public void insertarDemo(Demo d) {
        Connection con;
        PreparedStatement stmDemo = null;

        con = this.getConexion();

        try {
            stmDemo = con.prepareStatement("insert into demo(nombre, mes, ano, imagen, id_usradmin, url) " +
                    "values (?,?,?,?,?,?)");

            stmDemo.setString(1, d.getNombre());
            stmDemo.setInt(2, d.getMes());
            stmDemo.setInt(3, d.getAno());
            stmDemo.setBytes(4, FachadaAplicacion.imageToBytes(d.getImagen()));
            stmDemo.setString(5, d.getId_usreditor());
            stmDemo.setString(6, d.getUrl());
            stmDemo.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        } finally {
            try {
                assert stmDemo != null;
                stmDemo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    public void updateDemo(Demo d) {
        Connection con;
        PreparedStatement stmDemo = null;

        con = this.getConexion();

        try {
            stmDemo = con.prepareStatement("update demo set" +
                    " nombre = ?," +
                    " imagen = ?, " +
                    " url = ? " +
                    " where mes = ? and ano = ? ;");

            stmDemo.setString(1, d.getNombre());
            stmDemo.setInt(4, d.getMes());
            stmDemo.setInt(5, d.getAno());
            stmDemo.setString(3, d.getUrl());
            stmDemo.setBytes(2, FachadaAplicacion.imageToBytes(d.getImagen()));
            stmDemo.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        } finally {
            try {
                assert stmDemo != null;
                stmDemo.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
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

    public List<Demo> consultarDemoAdmin(String a_id) {
        List<Demo> resultado = new ArrayList<>();
        Demo demoActual;
        Connection con;
        PreparedStatement stmDemos=null;
        ResultSet rsDemos;

        con=this.getConexion();

        try{
            stmDemos=con.prepareStatement(" select nombre, mes, ano, imagen, url from demo where id_usradmin = ? order by ano, mes desc ");

            stmDemos.setString(1, a_id);
            rsDemos=stmDemos.executeQuery();
            while (rsDemos.next())
            {
                demoActual = new Demo(rsDemos.getString("nombre"), rsDemos.getInt("mes"),
                        rsDemos.getInt("ano"), FachadaAplicacion.bytesToImage(rsDemos.getBytes("imagen")),
                        a_id, rsDemos.getString("url"));

                resultado.add(demoActual);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmDemos != null;
                stmDemos.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }

        return resultado;
    }

}


