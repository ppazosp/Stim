/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.baseDatos;

import com.bdii.stimfx.aplicacion.Editor;
import com.bdii.stimfx.aplicacion.FachadaAplicacion;
import com.bdii.stimfx.aplicacion.Videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alumnogreibd
 */
public class DAOVideojuegos extends AbstractDAO{


    public DAOVideojuegos (Connection conexion, FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }



    public void insertarVideojuego(Videojuego v){
        Connection con;
        PreparedStatement stmVideojuego=null;
        
        con=super.getConexion();
        
        try {
            stmVideojuego = con.prepareStatement("insert into videojuego(nombre, fechasubida, id_usreditor, precio, descripcion, imagen, banner, trailer) " +
                    "values (?,?,?,?,?,?,?,?)");

            stmVideojuego.setString(1, v.getNombre());
            stmVideojuego.setDate(2, v.getFechaSubida());
            stmVideojuego.setString(3, v.getEditor().getId());
            stmVideojuego.setDouble(4, v.getPrecio());
            stmVideojuego.setString(5, v.getDescripcion());
            stmVideojuego.setBytes(6, FachadaAplicacion.imageToBytes(v.getImagen()));
            stmVideojuego.setBytes(7, FachadaAplicacion.imageToBytes(v.getBanner()));
            stmVideojuego.setString(8, v.getTrailer());

            stmVideojuego.executeUpdate();
        } catch (SQLException e){
          System.out.println(e.getMessage());
          FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
          try {
              assert stmVideojuego != null;
              stmVideojuego.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
    }

    public void updateVideojuego(Videojuego v) {
        Connection con;
        PreparedStatement stmVideojuego = null;

        con = super.getConexion();

        try {
            stmVideojuego = con.prepareStatement("update videojuego set" +
                    " nombre = ?," +
                    " fechasubida = ?," +
                    " id_usreditor = ?," +
                    " precio = ?," +
                    " descripcion = ?," +
                    " imagen = ?," +
                    " banner = ?," +
                    " trailer = ?" +
                    "where id = ?;");

            stmVideojuego.setInt(9, v.getId());
            stmVideojuego.setString(1, v.getNombre());
            stmVideojuego.setDate(2, v.getFechaSubida());
            stmVideojuego.setString(3, v.getEditor().getId());
            stmVideojuego.setDouble(4, v.getPrecio());
            stmVideojuego.setString(5, v.getDescripcion());
            stmVideojuego.setBytes(6, FachadaAplicacion.imageToBytes(v.getImagen()));
            stmVideojuego.setBytes(7, FachadaAplicacion.imageToBytes(v.getBanner()));
            stmVideojuego.setString(8, v.getTrailer());

            stmVideojuego.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        } finally {
            try {
                assert stmVideojuego != null;
                stmVideojuego.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    public Videojuego consultarVideojuego(Integer idVideojuego){  // Sirve para la transaccion de obtener el videojuego asociado a un dlc y tmbn para obtener videojuegos asociados a una cartegoria
        Videojuego videojuego=null;
        Connection con;
        PreparedStatement stmVideojuego=null;
        ResultSet rsVideojuego;
        
        con=this.getConexion();
        
        String consulta = "select * "+
                            "from videojuego "+
                            "where id = ?";
        
        
        try{
            stmVideojuego=con.prepareStatement(consulta);
            stmVideojuego.setInt(1, idVideojuego);
            rsVideojuego=stmVideojuego.executeQuery();
            if (rsVideojuego.next()){
                videojuego = new Videojuego(rsVideojuego.getInt("id"), rsVideojuego.getString("nombre"),
                                                rsVideojuego.getDate("fechaSubida"), rsVideojuego.getString("descripcion"), rsVideojuego.getDouble("precio"));
            }
        } catch (SQLException e){
          System.out.println(e.getMessage());
          FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
          try {
              assert stmVideojuego != null;
              stmVideojuego.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        
        return videojuego;
    }

    public Videojuego consultarVideojuego(String nombreVideojuego) {
        Videojuego videojuego=null;
        Connection con;
        PreparedStatement stmVideojuego=null;
        ResultSet rsVideojuego;
        ResultSet rsEditor;

        con=this.getConexion();

        String consulta = "SELECT v.id, v.nombre, v.descripcion, v.id_usreditor, v.fechasubida, v.precio, v.imagen, v.banner, v.trailer, COUNT(c.id_videojuego) AS totalCompras\n" +
                "FROM videojuego AS v\n" +
                "LEFT JOIN comprar AS c ON v.id = c.id_videojuego\n" +
                "WHERE v.nombre = ?\n" +
                "GROUP BY v.id, v.nombre, v.descripcion, v.id_usreditor, v.fechasubida, v.precio, v.imagen, v.banner, v.trailer;";

        try{
            stmVideojuego=con.prepareStatement(consulta);
            stmVideojuego.setString(1, nombreVideojuego);
            rsVideojuego=stmVideojuego.executeQuery();
            if(rsVideojuego.next())
            {
                videojuego = new Videojuego(rsVideojuego.getInt("id"), rsVideojuego.getString("nombre"),
                        rsVideojuego.getDate("fechasubida"), rsVideojuego.getString("descripcion"),
                        rsVideojuego.getDouble("precio"), FachadaAplicacion.bytesToImage(rsVideojuego.getBytes("imagen")),
                        FachadaAplicacion.bytesToImage(rsVideojuego.getBytes("banner")), rsVideojuego.getString("trailer"));

                String consulta1= "  select * from usuario u " +
                        "  where id= ?;";
                try {
                    stmVideojuego = con.prepareStatement(consulta1);
                    stmVideojuego.setString(1, rsVideojuego.getString("id_usreditor"));
                    rsEditor = stmVideojuego.executeQuery();
                    while (rsEditor.next()) {
                        Editor editor = new Editor(rsEditor.getString("id"), rsEditor.getString("nombre"), rsEditor.getString("contraseña"),
                                null, rsEditor.getString("email"));
                        videojuego.setEditor(editor);
                    }
                }catch (SQLException e){
                    System.out.println(e.getMessage());
                    FachadaAplicacion.muestraExcepcion(e.getMessage());
                }
            }
        }catch (SQLException e){
                System.out.println(e.getMessage());
                FachadaAplicacion.muestraExcepcion(e.getMessage());
        }finally{
            try {
                assert stmVideojuego != null;
                stmVideojuego.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }

        return videojuego;
    }

    public List<String> consultarVideojuegos(){
        Connection con;
        PreparedStatement stmVideojuego=null;
        ResultSet rsVideojuego;
        List<String> resultado = new ArrayList<>();
        con=this.getConexion();

        //Cambiar consulta para q devulva tb nombre de usuario
        String consulta = "select v.nombre " +
                "from videojuego as v ";

        try  {
            stmVideojuego= con.prepareStatement(consulta);
            rsVideojuego=stmVideojuego.executeQuery();
            while (rsVideojuego.next()) {

                resultado.add(rsVideojuego.getString("nombre"));
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            //Mostar excepcion
            //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{// este esta bien pq es al final
            try {
                assert stmVideojuego != null;
                stmVideojuego.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }//cierra cursosr haya o no excepcion de sql
        //VA cargate todo

        return resultado;
    }

    public List<Videojuego> consultarVideojuegosEditor(String id_editor) {
        List<Videojuego> resultado = new ArrayList<>();
        Connection con;
        PreparedStatement stmVideojuego = null;
        ResultSet rsVideojuegos;

        con = this.getConexion();

        try {
            stmVideojuego = con.prepareStatement("   select v.id, v.nombre, v.fechasubida, v.id_usreditor, v.precio, v.descripcion, v.imagen, v.banner, v.trailer " +
                    "   from videojuego v " +
                    "   where v.id_usreditor = ? ");

            stmVideojuego.setString(1, id_editor);
            rsVideojuegos = stmVideojuego.executeQuery();
            while (rsVideojuegos.next()) {
                Editor e = new Editor(rsVideojuegos.getString("id_usreditor"));
                Videojuego videojuego = new Videojuego(rsVideojuegos.getInt("id"), rsVideojuegos.getString("nombre"),
                        rsVideojuegos.getDate("fechasubida"), e,
                        rsVideojuegos.getString("descripcion"), rsVideojuegos.getDouble("precio"),
                        FachadaAplicacion.bytesToImage(rsVideojuegos.getBytes("imagen")),
                        FachadaAplicacion.bytesToImage(rsVideojuegos.getBytes("banner")),
                        rsVideojuegos.getString("trailer"));

                resultado.add(videojuego);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            FachadaAplicacion.muestraExcepcion(e.getMessage());
        } finally {
            try {
                assert stmVideojuego != null;
                stmVideojuego.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

}