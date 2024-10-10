/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.baseDatos;

import com.bdii.stimfx.aplicacion.*;

import java.sql.Date;
import java.sql.DriverManager;
import java.util.List;

/**
 *
 * @author alumnogreibd
 */
public class FachadaBaseDatos {
    private java.sql.Connection conexion;
    private final DAOVideojuegos daoV;
    private final DAODLCs daoD;
    private final DAOResenhas daoR;
    private final DAOUsuarios daoU;
    private final DAOTorneos daoT;
    private final DAOCompras daoCompras;
    private final DAOPlataformas daoP;
    private final DAODemos daoDemos;


    public FachadaBaseDatos (com.bdii.stimfx.aplicacion.FachadaAplicacion fa){

        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        String url = "jdbc:postgresql://surus.db.elephantsql.com:5432/vzgfiqrg";
        String username = "vzgfiqrg";
        String password = "VguSZP9OqTMKB_gk-05FtRIK-OmTPTLF";

        try {
            conexion = DriverManager.getConnection(url, username, password);
        }
        catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }

        daoV = new DAOVideojuegos(conexion, fa);
        daoU = new DAOUsuarios(conexion, fa);
        daoD = new DAODLCs(conexion, fa);
        daoCompras = new DAOCompras(conexion, fa);
        daoR = new DAOResenhas(conexion, fa);
        daoT = new DAOTorneos(conexion, fa);
        daoP = new DAOPlataformas(conexion, fa);
        daoDemos = new DAODemos(conexion, fa);
    }



    public void devolverCompra(int id_v, String id_usr)
    {
        daoCompras.devolverCompra(id_v, id_usr);
    }

    public Demo consultarDemo(int mes, int ano) {
        return daoDemos.consultarDemo(mes, ano);
    }


    public List<Videojuego> consultarVideojuegos(String nombre){  // Abajo hay una con id, aunq con nombre hace falta
        return daoV.consultarVideojuegos(nombre);
    }

    public void insertarResenha(Resenha r){
        daoR.insertarResenha(r);
    }

    public void insertarMeGusta( String id_usr, int id_v, int i_res)
    {
        daoR.insertarMeGusta(id_usr, id_v, i_res);
    }

    public void borrarMeGusta(String id_usr, int id_v, int i_res)
    {
        daoR.borrarMeGusta(id_usr, id_v, i_res);
    }

    public boolean isLiked(String id_usr, int id_v, int i_res)
    {
        return daoR.isLiked(id_usr, id_v, i_res);
    }

    public void updateLikes(Resenha r)
    {
        daoR.updateLikes(r);
    }
    
    public void insertarUsuario(Usuario u){
        daoU.insertarUsuario(u);
    }

    public void hacerJugadorCompetitivo(String u_id)
    {
        daoU.hacerJugadorCompetitivo(u_id);
    }

    public void hacerEditor(String u_id)
    {
        daoU.hacerEditor(u_id);
    }

    public Usuario consultarUsuario(String id_usr)
    {
        return daoU.consultarUsuario(id_usr);
    }

    public java.util.List<Usuario> consultarUsuariosNoSeguidos(String id, String busq){
        return daoU.consultarUsuariosNoSeguidos(id, busq);
    }
    
    public void insertarCompra(int id_videojuego, double precio,  String id_usuario){
        daoCompras.insertarCompra(id_videojuego, precio,  id_usuario);
    }

    public void insertarFondos(String u_id, double valor)
    {
        daoU.insertarFondos(u_id, valor);
    }

    public java.util.List<DLC> consultarDLCsVideojuego(Videojuego v){
        return daoD.consultarDLCsVideojuego(v);
    }
    
    public void seguir(String idU1, String idU2){
        daoU.seguir(idU1, idU2);
    }
    
    public void dejarSeguir(String idU1, String idU2){
        daoU.dejarSeguir(idU1, idU2);
    }

    public java.util.List<Usuario> consultarSeguidos(String idU1){
        return daoU.consultarSeguidos(idU1);
    }

    public int contarJuegosUsuario(String id_usuario){
        return daoCompras.contarJuegosUsuario(id_usuario);
    }

    public List<Videojuego> consultaVideoJuegosInicio(){
        return daoV.consultaVideoJuegosInicio();
    }

    public Usuario validarUsuario(String id, String clave){
        return daoU.validarUsuario(id, clave);
    }

    public Videojuego proximoVideojuego(){
        return  daoV.proximoVideojuego();
    }

    public int torneosGanados(String id){
        return daoT.torneosGanados(id);
    }

    public List<Plataforma> consultarPlataformasVideoJuego(int id){
        return daoP.consultarPlataformasVideoJuego(id);
    }

    public Usuario modificarUsuario(Usuario u){
        return daoU.modificarUsuario(u);
    }

    public List<DLC> consultarDLCsVideojuegoUsuario(int id_v, String id_u){
        return daoD.consultarDLCsVideojuegoUsuario(id_v, id_u);
    }

    public void comprarDLC(DLC d, String id_u, Date date){
        daoD.comprarDLC(d, id_u, date);
    }

    public boolean existeUsuario(String id){
        return daoU.existeUsuario(id);
    }

    public java.util.List<Videojuego> consultarVideojuegosUsuario(String id){
        return daoU.consultarVideojuegos(id);
    }

    public void consultarResenhas(Videojuego v)
    {
        daoR.consultarResenhas(v);
    }

    public float consultarMediaResenhas(Videojuego v)
    {
        return daoR.consultarMediaResenhas(v);
    }
}
