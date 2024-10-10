/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.baseDatos;

import com.bdii.stimfx.aplicacion.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alumnogreibd
 * */
public class DAOUsuarios extends AbstractDAO{


    public DAOUsuarios (Connection conexion, com.bdii.stimfx.aplicacion.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }



    public void insertarUsuario(Usuario u){
        Connection con;
        PreparedStatement stmUsuario=null;

        con=super.getConexion();

        try {
            stmUsuario=con.prepareStatement("insert into usuario(id, nombre, contraseña, email) "+
                                            "values (?,?,?,?)");
            stmUsuario.setString(1, u.getId());
            stmUsuario.setString(2, u.getNombre());
            stmUsuario.setString(3, u.getContrasena());
            stmUsuario.setString(4, u.getEmail());
            stmUsuario.executeUpdate();
        } catch (SQLException e){
          System.out.println(e.getMessage());
          FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
          try {
              assert stmUsuario != null;
              stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }

    public void insertarFondos(String u_id, double valor) {
        Connection con;
        PreparedStatement stmUsuario=null;

        con=super.getConexion();

        try {
            stmUsuario=con.prepareStatement("update monedero set dinero = dinero + ? where id_comun = ? ");
            stmUsuario.setDouble(1, valor);
            stmUsuario.setString(2, u_id);

            stmUsuario.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmUsuario != null;
                stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }

    public Usuario modificarUsuario(Usuario u){
        Connection con;
        PreparedStatement stmUsuario=null;

        con=super.getConexion();
        String consulta = "update usuario "+
                " set nombre=?, "+
                "contraseña=?, "+
                "email=?, "+
                "foto=? "+
                " where id = ?";

        try{
            stmUsuario=con.prepareStatement(consulta);

            stmUsuario.setString(1, u.getNombre());
            stmUsuario.setString(2, u.getContrasena());
            stmUsuario.setString(3, u.getEmail());
            stmUsuario.setBytes(4, FachadaAplicacion.imageToBytes(u.getFotoPerfil()));
            stmUsuario.setString(5, u.getId());
            stmUsuario.executeUpdate();

            return u;

        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmUsuario != null;
                stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return null;
    }

    public List<Usuario> consultarUsuariosNoAdmins(){
        List<Usuario> resultado = new ArrayList<>();
        Usuario usuarioActual;
        Connection con;
        PreparedStatement stmUsuarios=null;
        ResultSet rsUsuarios;

        con=this.getConexion();

        String consulta = "select * from usuario u where u.id not in " +
                " (select user_id from tipo_usuario where tipo = ?)" +
                "order by u.id";

        try  {
            stmUsuarios=con.prepareStatement(consulta);
            stmUsuarios.setString(1, TipoUsuario.ADMINISTRADOR);
            rsUsuarios=stmUsuarios.executeQuery();

            while (rsUsuarios.next())
            {
                usuarioActual = new Usuario(rsUsuarios.getString("id"), rsUsuarios.getString("nombre"),
                        rsUsuarios.getString("contraseña"),
                        rsUsuarios.getString("email"), FachadaAplicacion.bytesToImage(rsUsuarios.getBytes("foto")));

                resultado.add(usuarioActual);
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

    public Usuario validarUsuario(String id, String clave){
        Usuario resultado = null;
        Connection con;
        PreparedStatement stmUsuarios=null, stmnTipoUsuarios;
        ResultSet rsUsuarios, rsTipoUsuarios;

        con=this.getConexion();

        String consulta = "select * from usuario where id like ? and contraseña like ?";



        try  {
            stmUsuarios=con.prepareStatement(consulta);
            stmUsuarios.setString(1, id);
            stmUsuarios.setString(2, clave);
            rsUsuarios=stmUsuarios.executeQuery();

            if (rsUsuarios.next())
            {
                resultado = new Usuario(rsUsuarios.getString("id"), rsUsuarios.getString("nombre"),
                        rsUsuarios.getString("contraseña"),
                        rsUsuarios.getString("email"), FachadaAplicacion.bytesToImage(rsUsuarios.getBytes("foto")));
                // Consultamos tipos de usuarios
                consulta = "select user_id, tipo from tipo_usuario where user_id like ?";
                    try  {
                        stmnTipoUsuarios=con.prepareStatement(consulta);
                        stmnTipoUsuarios.setString(1, id);
                        rsTipoUsuarios=stmnTipoUsuarios.executeQuery();
                    while (rsTipoUsuarios.next())
                    {
                        if (rsTipoUsuarios.getString("tipo").equals("administrador")){
                            resultado.setAdmin(true);
                        }
                        if (rsTipoUsuarios.getString("tipo").equals("editor")){
                            resultado.setEditor(true);
                        }
                        if (rsTipoUsuarios.getString("tipo").equals("jugador_competitivo")){
                            resultado.setCompetitivePlayer(true);
                        }
                    }

                } catch (SQLException e){
                    System.out.println(e.getMessage());
                    FachadaAplicacion.muestraExcepcion(e.getMessage());
                }finally{
                    try {
                        stmUsuarios.close();
                    } catch (SQLException e){System.out.println("Imposible cerrar cursores");}
                }
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

    public boolean existeUsuario(String id){
        boolean resultado = false;
        Connection con;
        PreparedStatement stmUsuarios=null;
        ResultSet rsUsuarios;

        con=this.getConexion();

        String consulta = "select * from usuario where id like ?";



        try  {
            stmUsuarios=con.prepareStatement(consulta);
            stmUsuarios.setString(1, id);
            rsUsuarios=stmUsuarios.executeQuery();

            if (rsUsuarios.next())
            {
                resultado=true;

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

    public void hacerAdmin(String u_id) {
        Connection con;
        PreparedStatement stmUsuario=null;

        con=super.getConexion();

        try {
            stmUsuario=con.prepareStatement("insert into tipo_usuario(user_id, tipo) "+
                    "values (?,?)");

            stmUsuario.setString(1, u_id);
            stmUsuario .setString(2, TipoUsuario.ADMINISTRADOR);
            stmUsuario.executeUpdate();

        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmUsuario != null;
                stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }

    public void hacerJugadorCompetitivo(String u_id) {
        Connection con;
        PreparedStatement stmUsuario=null;

        con=super.getConexion();

        try {
            stmUsuario=con.prepareStatement("insert into tipo_usuario(user_id, tipo) "+
                    "values (?,?)");

            stmUsuario.setString(1, u_id);
            stmUsuario .setString(2, TipoUsuario.JUGADOR_COMPETITIVO);
            stmUsuario.executeUpdate();

        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmUsuario != null;
                stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }

    public void hacerEditor(String u_id) {
        Connection con;
        PreparedStatement stmUsuario=null;

        con=super.getConexion();

        try {
            stmUsuario=con.prepareStatement("insert into tipo_usuario(user_id, tipo) "+
                    "values (?,?)");

            stmUsuario.setString(1, u_id);
            stmUsuario .setString(2, TipoUsuario.EDITOR);
            stmUsuario.executeUpdate();

        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmUsuario != null;
                stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }

}

