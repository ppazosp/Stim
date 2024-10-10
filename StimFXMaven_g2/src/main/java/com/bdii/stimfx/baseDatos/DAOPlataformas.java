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


    public java.util.List<Plataforma> consultarPlataformas(){
        java.util.List<Plataforma> resultado = new java.util.ArrayList<>();
        Plataforma plataformaActual;
        Connection con;
        PreparedStatement stmPlataformas=null;
        ResultSet rsPlataformas;

        con=this.getConexion();
        
        String consulta = "select * from plataforma ";

        try  {
        stmPlataformas=con.prepareStatement(consulta);
        rsPlataformas=stmPlataformas.executeQuery();
        while (rsPlataformas.next())
        {
            plataformaActual = new Plataforma(rsPlataformas.getString("nombre"));
            
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

    public boolean hasPlataforma(int id_v, String nombre) {
        Connection con;
        PreparedStatement stmPlataformas=null;
        ResultSet rsPlataformas;

        con=this.getConexion();

        String consulta = "SELECT ptv.nombre_plataforma " +
                "FROM videojuego v\n" +
                "JOIN plataforma_tiene_videojuego ptv ON v.id = ptv.id_videojuego \n" +
                "JOIN plataforma p ON ptv.nombre_plataforma = p.nombre\n" +
                "WHERE v.id = ? and p.nombre = ?;";

        try  {
            stmPlataformas=con.prepareStatement(consulta);
            stmPlataformas.setInt(1, id_v);
            stmPlataformas.setString(2, nombre);
            rsPlataformas=stmPlataformas.executeQuery();
            if (rsPlataformas.next())
            {
                return true;
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmPlataformas != null;
                stmPlataformas.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }

        return  false;
    }

    public void insertarPlataformaVideojuego(String nombre, int id_videojuego){
        Connection con;
        PreparedStatement stmPlataforma=null;
        
        con=super.getConexion();
        
        try {
            stmPlataforma=con.prepareStatement("insert into plataforma_tiene_videojuego(nombre_plataforma, id_videojuego) "+
                                            "values (?,?)");          
            stmPlataforma.setString(1, nombre);
            stmPlataforma.setInt(2, id_videojuego);
            stmPlataforma.executeUpdate();
        } catch (SQLException e){
          System.out.println(e.getMessage());
          FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
          try {
              assert stmPlataforma != null;
              stmPlataforma.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }
    
    public void borrarPlataformaVideojuego(String nombre, int id_videojuego){
        Connection con;
        PreparedStatement stmPlataforma=null;
        
        con=super.getConexion();
        
        try{
            stmPlataforma=con.prepareStatement("delete from plataforma_tiene_videojuego where nombre_plataforma = ? and id_videojuego = ?");
            stmPlataforma.setString(1, nombre);
            stmPlataforma.setInt(2, id_videojuego);
            stmPlataforma.executeUpdate();
        }catch (SQLException e){
          System.out.println(e.getMessage());
          FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
          try {
              assert stmPlataforma != null;
              stmPlataforma.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }  
    }

}