package com.bdii.stimfx.aplicacion;

import com.bdii.stimfx.baseDatos.FachadaBaseDatos;
import com.bdii.stimfx.gui.FachadaGUI;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class FachadaAplicacion {
    private final FachadaBaseDatos fbd;
    private final GestionUsuarios gu;
    private final GestionVideojuegos gv;
    private final GestionDemo gdem;
    private final GestionDLC gd;

    private final GestionComunidad gc;
    private final GestionTorneos gt;
    private final GestionCompra gcom;
    private final GestionPlataforma gpl;

    public Usuario usuario;

    public static Image pathToImage(String path) {
        try {
            File imageFile = new File(path);
            FileInputStream inputStream = new FileInputStream(imageFile);
            return new Image(inputStream);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + path);
            e.printStackTrace();
            return null;
        }
    }
    public static byte[] pathToBytes(String path) {
        try {

            File imageFile = new File(path);
            FileInputStream fis = new FileInputStream(imageFile);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();

        }catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
    public static byte[] imageToBytes(Image image) {
        if (image == null) return null;

        try {
            
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            
            ImageIO.write(bufferedImage, "png", outputStream);

            
            byte[] byteArray = outputStream.toByteArray();

            
            outputStream.close();

            return byteArray;
        } catch (IOException e) {
            
            e.printStackTrace();
            return null;
        }
    }
    public static Image bytesToImage(byte[] imageData) {
        if (imageData == null) return null;

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
            Image image = new Image(bis);
            bis.close();

            return image;
        } catch (IOException e) {
            System.out.println("Error converting byte array to image: " + e.getMessage());
            return null;
        }
    }


    public FachadaAplicacion(FachadaGUI fg) {
        fbd = new FachadaBaseDatos(this);
        gu = new GestionUsuarios(fg, fbd);
        gv = new GestionVideojuegos(fg, fbd);
        gd = new GestionDLC(fg, fbd);
        gdem = new GestionDemo(fg, fbd);
        gc = new GestionComunidad(fg, fbd);
        gt = new GestionTorneos(fg, fbd);
        gcom = new GestionCompra(fg, fbd);
        gpl = new GestionPlataforma(fg, fbd);

    }

    public static void main(String[] args) {
        Application.launch(FachadaGUI.class, args);
    }

    public static void muestraExcepcion(String e) {
        // fg.muestraExcepcion(e);
        System.out.println("Excepcion: "+ e);
    }



    // FUNCIONES RELACIONADAS CON GESTIONVIDEOJUEGOS
    // -----------------------------------------------------------------------

    public List<Videojuego> consultarVideosjuegosEditor(String id_editor) {
        return fbd.consultarVideosjuegosEditor(id_editor);
    }

    // Funcion para buscar un unico videojuego a partir de un nombre
    public Videojuego consultarVideojuego(String n){
        return gv.consultarVideojuego(n);
    }

    public List<String> consultarVideojuegos(){  // Abajo hay una con id, aunq con nombre hace falta
        return fbd.consultarVideojuegos();
    }

    // Funcion para publicar un videojuego
    public void publicarVideojuego(Videojuego v) {
        gv.publicarVideojuego(v);
    }


    // -----------------------------------------------------------------------






    // FUNCIONES RELACIONADAS CON GESTIONUSUARIOS
    // -----------------------------------------------------------------------

    // Funcion para registrar un usuario en la base
    public boolean registrar(String id, String clave, String nombre, String email){
        this.usuario  = gu.registrarUsuario(id, clave, nombre, email);
        return usuario!=null;
    }

    public void modificarUsuario(String nombre, String clave, String email, Image imagen){
        Usuario u = gu.modificarUsuario(this.usuario.getId(), nombre, this.usuario.getContrasena(), clave, email, imagen);
        if (u != null) {
            this.usuario = u;
        }
    }

    public void hacerAdmin(Usuario u)
    {
        gu.hacerAdmin(u);
    }

    public void hacerJugadorCompetitivo(Usuario u)
    {
        gu.hacerJugadorCompetitivo(u);
    }

    public void hacerEditor(Usuario u)
    {
        gu.hacerEditor(u);
    }

    public java.util.List<Usuario> consultarUsuariosNoAdmins(){
        return gu.consultarUsuariosNoAdmins();
    }

    //Lo hice para q si null->false, si true te pasa el usuario, asi puedes ir a tu perfil y eso
    public boolean checkCredentials(String username, String password) {
        this.usuario = gu.comprobarAutentificacion(username, password);
        return usuario != null;
    }

    // Funcion para mirar comunidades en el buscador, encontrar una comunidad especifica, a partir de algo o todas si la barra esta vacía
    public java.util.List<Comunidad> consultarComunidades(String nombre){
        return gc.consultarComunidades(nombre);
    }

    // Funcion para insertar a un usuario en una comunidad.
    // QUE SEA COMPETITIVO EL USUARIO NO ESTA IMPLEMENTADO A NIVEL BAJO (por lo menos por ahora) !!!!!!!!!!!!!!!!
    public void insertarJugadorEquipo(String id_usuario, Comunidad c){
        gc.insertarJugadorEquipo(id_usuario, c);
    }

    // Funcion para hacer salir de una comunidad a un usuario
    public void salirJugadorEquipo(String id_usuario){
        gc.salirJugadorEquipo(id_usuario);
    }

    // Funcion para consultar el equipo en el que esta un usuario
    public Comunidad consultarEquipoJugador(String id_usuario){
        return gc.consultarEquipoJugador(id_usuario);
    }

    // Funcion para contar los miembros de un equipo
    public int contarMiembrosEquipo(Comunidad c) {
        return gc.contarMiembrosEquipo(c);
    }

    // Comprueba que un usuario pertenece a alguna comunidad y devuelve un boolean
    public boolean tieneComunidad(Usuario u){
        return gc.tieneComunidad(u);
    }

    // -----------------------------------------------------------------------






    // FUNCIONES RELACIONADAS CON GESTIONDEMO
    // -----------------------------------------------------------------------

    // Funcion que permite consultar una demo a partir de un mes y/o ano
    public List<Demo> consultarDemoAdmin(Usuario u)
    {
        return gdem.consultarDemoAdmin(u);
    }
    public void publicarDemo(Demo d)
    {
        gdem.publicarDemo(d);
    }

    // -----------------------------------------------------------------------






    // FUNCIONES RELACIONADAS CON GESTIONCOMPRA
    // -----------------------------------------------------------------------
    // Funcion para contar la cantidad de juegos que un usario tiene en propiedad
    public Integer contarJuegosUsuario(String id_usuario){
        return gcom.contarJuegosUsuario(id_usuario);
    }

    // -----------------------------------------------------------------------






    // FUNCIONES RELACIONADAS CON GESTIONDLC
    // -----------------------------------------------------------------------

    public java.util.List<DLC> consultarDLCsVideojuego(Videojuego v){
        return gd.consultarDLCsVideojuego(v);
    }

    public void publicarDLC(DLC d)
    {
        gd.publicarDLC(d);
    }


    // -----------------------------------------------------------------------






    // FUNCIONES RELACIONADAS CON GESTIONTORNEOS
    // -----------------------------------------------------------------------

    public int torneosGanados(Usuario u){
        return gt.torneosGanados(u);
    }
    // Funcion para consultar los torneos existentes ¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿ACTUALES O HISTORICOS????????????????????
    public List<Torneo> consultarTorneos(String nombre)
    {
        return gt.consultarTorneos(nombre);
    }
    //Funcion para agregar a un usuario a un torneo
    public void participarTorneo(Usuario u, Torneo t) {
        gt.participarTorneo(u, t);
    }
    // Funcion para comprobar si un usuario forma parte de un torneo determinado
    public boolean isParticipante(Usuario u, Torneo t) {
        return gt.isParticipante(u, t);
    }
    // Funcion para comprobar si un usuario puede retirarse de un torneo
    public boolean puedeRetirarse(Torneo t) {
        return gt.puedeRetirarse(t);
    }
    // Funion para hacer que un usuario se retire de un torneo
    public void retirarseTorneo(Usuario u, Torneo t) {
        gt.retirarseTorneo(u, t);
    }
    // Funcion para actualizar el ganador de un torneo
    public void setGanador(Torneo t) {
        gt.setGanador(t);
    }

    public List<Torneo> consultarTorneosAdmin(Usuario u)
    {
        return gt.consultarTorneosAdmin(u);
    }

    public void publicarTorneo(Torneo t)
    {
        gt.publicarTorneo(t);
    }
    // -----------------------------------------------------------------------





    // FUNCIONES RELACIONADAS CON GESTIOPLATADFORMA

    // -----------------------------------------------------------------------

    // Funcion para consultar plataformas a partir de un nombre, que puede estar vacio
    public java.util.List<Plataforma> consultarPlataformas(){
        return gpl.consultarPlataformas();
    }
    // Funcion para añadir una plataforma a un videojuego
    public void insertarPlataformaVideojuego(Plataforma p, Videojuego v){
        gpl.insertarPlataformaVideojuego(p, v);
    }
    // Funcion para borrar a un videojuego una plataforma
    public void borrarPlataformaVideojuego(Plataforma p, Videojuego v){
        gpl.borrarPlataformaVideojuego(p, v);
    }

    public boolean hasPlataforma(Videojuego v, Plataforma p){
        return gpl.hasPlataforma(v, p);
    }

    // -----------------------------------------------------------------------









    // -----------------------------------------------------------------------
    // -----------------------------------------------------------------------
    // -----------------------------------------------------------------------

}
