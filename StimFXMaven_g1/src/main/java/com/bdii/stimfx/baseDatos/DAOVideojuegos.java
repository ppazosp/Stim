/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.baseDatos;

import com.bdii.stimfx.aplicacion.DLC;
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



    public List<Videojuego> consultarVideojuegos(String nombre){
        Connection con;
        PreparedStatement stmCatalogo=null;
        ResultSet rsCatalogo;
        PreparedStatement stmDLC;
        ResultSet rsDLC;
        List<Videojuego> resultado = new ArrayList<>();
        con=this.getConexion();
        //Cambiar consulta para q devulva tb nombre de usuario
        String consulta = "select * " +
                                         "from videojuego as v join editor e on e.id= v.id_usreditor join usuario u on u.id = e.id and "+
                                         " LOWER(v.nombre) like LOWER(?)";

        try  {
        stmCatalogo= con.prepareStatement(consulta);
        stmCatalogo.setString(1, "%"+nombre+"%");
        rsCatalogo=stmCatalogo.executeQuery();
        while (rsCatalogo.next())
        {
            int id=rsCatalogo.getInt("id");
            Videojuego videojuego = new Videojuego(id, rsCatalogo.getString("nombre"),
                        rsCatalogo.getDate("fechaSubida"),
                    rsCatalogo.getString("descripcion"), rsCatalogo.getDouble("precio"),
                    FachadaAplicacion.bytesToImage(rsCatalogo.getBytes("imagen")), FachadaAplicacion.bytesToImage(rsCatalogo.getBytes("banner")),
                    rsCatalogo.getString("trailer"));
            Editor editor = new Editor(rsCatalogo.getString("id_usreditor"), rsCatalogo.getString("nombre"),
                    rsCatalogo.getString("contraseña"), rsCatalogo.getString("email"));
            videojuego.setEditor(editor);
            resultado.add(videojuego);


            consulta= "select * from DLC where id_videojuego = ?;";
            try{
            stmDLC=con.prepareStatement(consulta);
            stmDLC.setInt(1, id);
            rsDLC= stmDLC.executeQuery();
            while(rsDLC.next()){
                DLC dlc= new DLC(id, rsDLC.getInt("id_dlc"),rsDLC.getString("nombre"),
                        rsDLC.getDouble("precio"));
                videojuego.addDLC(dlc);
            }
                   // (idVideojuego, idDLC, nombre, descripcion, precio, fechaLanzamiento)
            } catch (SQLException e){
              System.out.println(e.getMessage());
              //Mostar excepcion
              //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            }
        }
        } catch (SQLException e){
          System.out.println(e.getMessage());
          //Mostar excepcion
          //this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{// este esta bien pq es al final
          try {
              assert stmCatalogo != null;
              stmCatalogo.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }//cierra cursosr haya o no excepcion de sql
            //VA cargate todo
        return resultado;
    }

    public List<Videojuego> consultaVideoJuegosInicio(){
        List <Videojuego> resultado = new ArrayList<>(3);
        Connection con;
        PreparedStatement stmVideojuego=null;
        ResultSet rsVideojuegos, rsEditor;

        con=this.getConexion();

        String consulta = "   select v.id, v.nombre, v.fechasubida, v.id_usreditor, v.precio, v.descripcion, v.imagen, v.banner, v.trailer, count(*) as totalCompras\n" +
                "   from videojuego v \n" +
                "   join comprar c on v.id=c.id_videojuego \n" +
                "   group by v.id\n" +
                "   order by totalCompras desc\n" +
                "   limit 3;";

        try{
            stmVideojuego=con.prepareStatement(consulta);
            rsVideojuegos=stmVideojuego.executeQuery();
            while (rsVideojuegos.next())
            {
                Videojuego videojuego = new Videojuego(rsVideojuegos.getInt("id"), rsVideojuegos.getString("nombre"),
                                        rsVideojuegos.getDate("fechasubida"), rsVideojuegos.getString("descripcion"),
                                        rsVideojuegos.getDouble("precio"), FachadaAplicacion.bytesToImage(rsVideojuegos.getBytes("imagen")),
                                        FachadaAplicacion.bytesToImage(rsVideojuegos.getBytes("banner")), rsVideojuegos.getString("trailer"));
                videojuego.setNumDescargas(rsVideojuegos.getInt("totalCompras"));

                //Consulta para editor

                String consulta1= "  select * from usuario u " +
                        "  where id= ?;";
                try {
                    stmVideojuego = con.prepareStatement(consulta1);
                    stmVideojuego.setString(1, rsVideojuegos.getString("id_usreditor"));
                    rsEditor = stmVideojuego.executeQuery();
                    while (rsEditor.next()) {
                        Editor editor = new Editor(rsEditor.getString("id"), rsEditor.getString("nombre"), rsEditor.getString("contraseña"),
                                rsEditor.getString("email"));
                        videojuego.setEditor(editor);
                    }
                }
                catch (SQLException e){
                    System.out.println(e.getMessage());
                    FachadaAplicacion.muestraExcepcion(e.getMessage());
                }
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

    public Videojuego proximoVideojuego(){
        Connection con;
        PreparedStatement stmVideojuego=null;
        ResultSet rsVideojuegos, rsEditor;
        Videojuego resultado = null;

        con=this.getConexion();

        String consulta = "select v.id, v.nombre, v.fechasubida, v.id_usreditor, v.precio, v.descripcion, v.imagen, v.banner, v.trailer\n" +
                "   from videojuego v  \n" +
                "   where fechasubida  > current_date\n" +
                "   order by fechasubida\n" +
                "   limit 1;";

        try{
            stmVideojuego=con.prepareStatement(consulta);
            rsVideojuegos=stmVideojuego.executeQuery();
            while (rsVideojuegos.next())
            {
                resultado = new Videojuego(rsVideojuegos.getInt("id"), rsVideojuegos.getString("nombre"),
                        rsVideojuegos.getDate("fechasubida"), rsVideojuegos.getString("descripcion"),
                        rsVideojuegos.getDouble("precio"), FachadaAplicacion.bytesToImage(rsVideojuegos.getBytes("imagen")),
                        FachadaAplicacion.bytesToImage(rsVideojuegos.getBytes("banner")), rsVideojuegos.getString("trailer"));

                String consulta1= "  select * from usuario u " +
                        "  where id= ?;";
                try {
                    stmVideojuego = con.prepareStatement(consulta1);
                    stmVideojuego.setString(1, rsVideojuegos.getString("id_usreditor"));
                    rsEditor = stmVideojuego.executeQuery();
                    while (rsEditor.next()) {
                        Editor editor = new Editor(rsEditor.getString("id"), rsEditor.getString("nombre"), rsEditor.getString("contraseña"),
                                rsEditor.getString("email"));
                        resultado.setEditor(editor);
                    }
                }
                catch (SQLException e){
                    System.out.println(e.getMessage());
                    FachadaAplicacion.muestraExcepcion(e.getMessage());
                }
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

}