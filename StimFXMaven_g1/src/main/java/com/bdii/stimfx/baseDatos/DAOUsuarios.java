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
        String consulta = " update usuario "+
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

    public java.util.List<Usuario> consultarUsuariosNoSeguidos(String id, String busq){
        java.util.List<Usuario> resultado = new java.util.ArrayList<>();
        Usuario usuarioActual;
        Connection con;
        PreparedStatement stmUsuarios=null;
        ResultSet rsUsuarios;

        con=this.getConexion();

        try  {
            stmUsuarios=con.prepareStatement("select * from usuario u " +
                    " where (LOWER(u.id) like LOWER(?) or LOWER(u.nombre) like LOWER(?)) and u.id not in " +
                    "(select id_usr2 from ser_amigo " +
                    "where id_usr1 like ?) and u.id not like ?");

            stmUsuarios.setString(1, "%"+busq+"%");
            stmUsuarios.setString(2, "%"+busq+"%");
            stmUsuarios.setString(3, "%"+id+"%");
            stmUsuarios.setString(4, "%"+id+"%");
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

    public Usuario consultarUsuario(String id){
        Usuario usuario=null;
        Connection con;
        PreparedStatement stmUsuario=null;
        ResultSet rsUsuario;

        con=this.getConexion();

        String consulta = "select * "+
                "from usuario "+
                "where id = ?";


        try{
            stmUsuario=con.prepareStatement(consulta);
            stmUsuario.setString(1, id);
            rsUsuario=stmUsuario.executeQuery();
            if (rsUsuario.next()){
                usuario = new Usuario(rsUsuario.getString("id"), rsUsuario.getString("nombre"),
                        rsUsuario.getString("contraseña"), rsUsuario.getString("email"),
                        FachadaAplicacion.bytesToImage(rsUsuario.getBytes("foto")));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmUsuario != null;
                stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }

        return usuario;
    }
    
    public void seguir(String idU1, String idU2){
        Connection con;
        PreparedStatement stmSeguidos=null;
        
        con=super.getConexion();
        
        try {
            stmSeguidos=con.prepareStatement("insert into ser_amigo(id_usr1, id_usr2) "+
                                            "values (?,?)");       
            stmSeguidos.setString(1, idU1);
            stmSeguidos.setString(2, idU2);
            stmSeguidos.executeUpdate();
        } catch (SQLException e){
          System.out.println(e.getMessage());
          FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
          try {
              assert stmSeguidos != null;
              stmSeguidos.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }
    
    public void dejarSeguir(String idU1, String idU2){
        Connection con;
        PreparedStatement stmSeguidos=null;
        
        con=super.getConexion();
        
        try{
            stmSeguidos=con.prepareStatement("delete from ser_amigo where id_usr1 = ? and id_usr2 = ?");
            stmSeguidos.setString(1, idU1);
            stmSeguidos.setString(2, idU2);
            stmSeguidos.executeUpdate();
        
        }catch (SQLException e){
          System.out.println(e.getMessage());
          FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
          try {
              assert stmSeguidos != null;
              stmSeguidos.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }

    public java.util.List<Usuario> consultarSeguidos(String idU1){
        java.util.List<Usuario> resultado = new java.util.ArrayList<>();
        Connection con;
        PreparedStatement stmSeguidos=null;
        Usuario usuarioActual;
        ResultSet rsSeguidos;
        
        con=this.getConexion();
        
        String consulta = "select * from usuario u " +
                        "where u.id in " +
                        "(select id_usr2 from ser_amigo where id_usr1 = ?)";
        
        try{
            stmSeguidos=con.prepareStatement(consulta);
            stmSeguidos.setString(1, idU1);
            rsSeguidos=stmSeguidos.executeQuery();
            while (rsSeguidos.next())
            {
                usuarioActual = new Usuario(rsSeguidos.getString("id"), rsSeguidos.getString("nombre"),
                        rsSeguidos.getString("contraseña"),
                        rsSeguidos.getString("email"), FachadaAplicacion.bytesToImage(rsSeguidos.getBytes("foto")));
                resultado.add(usuarioActual);
            }
        } catch (SQLException e){
          System.out.println(e.getMessage());
          FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
          try {
              assert stmSeguidos != null;
              stmSeguidos.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;  
    }

    public Usuario validarUsuario(String id, String clave){
        Usuario resultado = null;
        Connection con;
        PreparedStatement stmUsuarios=null, stmDinero;
        ResultSet rsUsuarios, rsDinero;

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

                // Consultamos monedero de usuario
                consulta = "select * from monedero where monedero.id_comun like ?";
                try  {
                    stmDinero=con.prepareStatement(consulta);
                    stmDinero.setString(1, id);
                    rsDinero=stmDinero.executeQuery();
                    if (rsDinero.next())
                    {
                       resultado.setDinero(rsDinero.getFloat("dinero"));


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

    public java.util.List<Videojuego> consultarVideojuegos(String id){
        java.util.List<Videojuego> resultado = new java.util.ArrayList<>();
        Connection con;
        PreparedStatement stmVideojuego=null;
        ResultSet rs;

        con=this.getConexion();

        String consulta = "select  v.id, v.nombre , v.fechasubida , v.id_usreditor , v.precio , v.descripcion, v.imagen, v.banner, v.trailer, c.id_usr\n" +
                "from comprar as c join videojuego as v on c.id_videojuego = v.id " +
                "where c.id_usr = ? and c.fecha_devolucion is null;";
        //un segundo porfa
        try{
            stmVideojuego=con.prepareStatement(consulta);
            stmVideojuego.setString(1, id);
            rs=stmVideojuego.executeQuery();
            while (rs.next())
            {
                Videojuego videojuego = new Videojuego(rs.getInt("id"),rs.getString("nombre"),
                        rs.getDate("fechasubida"), rs.getString("descripcion"), rs.getDouble("precio"),
                        FachadaAplicacion.bytesToImage(rs.getBytes("imagen")), FachadaAplicacion.bytesToImage(rs.getBytes("banner")), rs.getString("trailer"));
                // SOlo tiene nombre este usuario
                Editor usuario = new Editor(rs.getString("id_usr"));
                videojuego.setEditor(usuario);
                resultado.add(videojuego);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmVideojuego != null;
                stmVideojuego.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
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

