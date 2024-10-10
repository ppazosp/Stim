/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bdii.stimfx.baseDatos;

import com.bdii.stimfx.aplicacion.*;

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
    private final DAOUsuarios daoU;
    private final DAOTorneos daoT;
    private final DAOCompras daoCompras;
    private final DAOPlataformas daoP;
    private final DAOComunidades daoComunidades;
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
        daoComunidades = new DAOComunidades(conexion, fa);
        daoT = new DAOTorneos(conexion, fa);
        daoP = new DAOPlataformas(conexion, fa);
        daoDemos = new DAODemos(conexion, fa);
    }



    public void insertarDemo(Demo d) {daoDemos.insertarDemo(d);}

    public void updateDemo(Demo d) {daoDemos.updateDemo(d);}

    public Demo consultarDemo(int mes, int ano) {
        return daoDemos.consultarDemo(mes, ano);
    }

    public List<Demo> consultarDemoAdmin(String a_id)
    {
        return daoDemos.consultarDemoAdmin(a_id);
    }

    public List<String> consultarVideojuegos(){  // Abajo hay una con id, aunq con nombre hace falta
        return daoV.consultarVideojuegos();
    }
    
    public void insertarVideojuego(Videojuego v){
        daoV.insertarVideojuego(v);
    }

    public void updateVideojuego(Videojuego v) {
        daoV.updateVideojuego(v);
    }

    public Videojuego consultarVideojuego(Integer v){
        return daoV.consultarVideojuego(v);
    }

    public Videojuego consultarVideojuego(String v){
        return daoV.consultarVideojuego(v);
    }

    public void insertarUsuario(Usuario u){
        daoU.insertarUsuario(u);
    }

    public void hacerAdmin(String u_id)
    {
        daoU.hacerAdmin(u_id);
    }

    public void hacerJugadorCompetitivo(String u_id)
    {
        daoU.hacerJugadorCompetitivo(u_id);
    }

    public void hacerEditor(String u_id)
    {
        daoU.hacerEditor(u_id);
    }

    public void insertarTorneo(Torneo t){
        daoT.insertarTorneo(t);
    }

    public java.util.List<Usuario> consultarUsuariosNoAdmins(){
        return daoU.consultarUsuariosNoAdmins();
    }

    public void insertarFondos(String u_id, double valor)
    {
        daoU.insertarFondos(u_id, valor);
    }

    public java.util.List<DLC> consultarDLCsVideojuego(Videojuego v){
        return daoD.consultarDLCsVideojuego(v);
    }
    
    public void insertarDLC(DLC d){
        daoD.insertarDLC(d);
    }

    public DLC consultarDLC(int id_v, int id_dlc)
    {
        return daoD.consultarDLC(id_v, id_dlc);
    }

    public void updateDLC(DLC d)
    {
        daoD.updateDLC(d);
    }

    public java.util.List<Plataforma> consultarPlataformas(){
        return daoP.consultarPlataformas();
    }

    public boolean hasPlataforma(int id_v, String nombre){
        return daoP.hasPlataforma(id_v, nombre);
    }
    
    public void insertarPlataformaVideojuego(String nombre, int id_videojuego){
        daoP.insertarPlataformaVideojuego(nombre, id_videojuego);
    }
    
    public void borrarPlataformaVideojuego(String nombre, int id_videojuego){
        daoP.borrarPlataformaVideojuego(nombre, id_videojuego);
    }

    public Integer contarJuegosUsuario(String id_usuario){
        return daoCompras.contarJuegosUsuario(id_usuario);
    }

    public java.util.List<Comunidad> consultarComunidades(String nombre){
        return daoComunidades.consultarComunidades(nombre);
    }

    public void insertarJugadorEquipo(String id_usuario, Comunidad c){
        daoComunidades.insertarJugadorEquipo(id_usuario, c);
    }

    public void salirJugadorEquipo(String id_usuario){
        daoComunidades.salirJugadorEquipo(id_usuario);
    }

    public Comunidad consultarEquipoJugador(String id_usuario){
        return daoComunidades.consultarEquipoJugador(id_usuario);
    }

    public int contarMiembrosEquipo(Comunidad c) {
        return daoComunidades.contarMiembrosEquipo(c);
    }

    public Usuario validarUsuario(String id, String clave){
        return daoU.validarUsuario(id, clave);
    }

    public List<Torneo> consultarTorneos(String nombre)
    {
        return daoT.consultarTorneos(nombre);
    }

    public int torneosGanados(String id){
        return daoT.torneosGanados(id);
    }

    public void participarTorneo(String u_id, int t_id)
    {
        daoT.participarTorneo(u_id, t_id);
    }

    public void retirarseTorneo(String u_id, int t_id)
    {
        daoT.retirarseTorneo(u_id, t_id);
    }

    public List<Torneo> consultarTorneosAdmin(String a_id)
    {
        return daoT.consultarTorneosAdmin(a_id);
    }

    public void updateTorneo(Torneo t)
    {
        daoT.updateTorneo(t);
    }

    public Torneo consultarTorneo(int t_id)
    {
        return daoT.consultarTorneo(t_id);
    }

    public List<Usuario> consultarParticipantes(int t_id)
    {
        return daoT.consultarParticipantes(t_id);
    }

    public void setGanador(String u_id, int t_id) {
        daoT.setGanador(u_id, t_id);
    }

    public Usuario modificarUsuario(Usuario u){
        return daoU.modificarUsuario(u);
    }

    public boolean existeUsuario(String id){
        return daoU.existeUsuario(id);
    }

    public boolean tieneComunidad(String usr_id){
        return daoComunidades.tieneComunidad(usr_id);
    }

    public List<Videojuego> consultarVideosjuegosEditor(String id_editor) {
        return daoV.consultarVideojuegosEditor(id_editor);
    }
}
