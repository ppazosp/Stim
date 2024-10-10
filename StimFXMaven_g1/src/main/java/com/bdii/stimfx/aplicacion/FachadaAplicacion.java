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

    private final GestionUsuarios gu;
    private final GestionVideojuegos gv;
    private final GestionDemo gdem;
    private final GestionDLC gd;
    private final GestionCompra gcom;
    private final GestionResenhas gr;
    private final GestionTorneos gt;

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
        FachadaBaseDatos fbd = new FachadaBaseDatos(this);
        gu = new GestionUsuarios(fg, fbd);
        gv = new GestionVideojuegos(fg, fbd);
        gd = new GestionDLC(fg, fbd);
        gdem = new GestionDemo(fg, fbd);
        gcom = new GestionCompra(fg, fbd);
        gr = new GestionResenhas(fg, fbd);
        gt = new GestionTorneos(fg, fbd);
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

    // Funcion para consultar videojuegos a partir de un nombre
    public java.util.List<Videojuego> consultarVideojuegos(String n){
        return gv.consultarVideojuegos(n);
    }

    // Funcion para obtener las plataformas asociadas a un videojuego.
    public List<Plataforma> consultarPlataformasVideojuego(Videojuego v){
        return gv.consultarPlataformasVideoJuego(v);
    }
    // Funcion para obtener el proximo videojuego que saldra a la venta
    public Videojuego proximoVideojuego(){
        return gv.proximoVideojuego();
    }

    // Funcion para obtener los 3 videojuegos mas vendidos del momento
    public List<Videojuego> consultaVideoJuegosInicio(){
        return gv.consultaVideoJuegosInicio();
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


    // Funcion para buscar a los usuarios que no seguimos
    public java.util.List<Usuario> consultarUsuariosNoSeguidos(Usuario usuario, String busq){
        return gu.consultarUsuariosNoSeguidos(usuario, busq);
    }


    // Funcion para empezar a seguir a un usuario
    public void seguir(Usuario u1, Usuario u2){
        gu.seguir(u1, u2);
    }

    // Funcion para dejar de seguir a un usuario
    public void dejarSeguir(Usuario u1, Usuario u2){
        gu.dejarSeguir(u1, u2);
    }

    // Funcion para consultar a las personas que sigue un usuario
    public java.util.List<Usuario> consultarSeguidos(Usuario u1){
        return gu.consultarSeguidos(u1);
    }

    //Lo hice para q si null->false, si true te pasa el usuario, asi puedes ir a tu perfil y eso
    public boolean checkCredentials(String username, String password)
    {
        this.usuario = gu.comprobarAutentificacion(username, password);
        return usuario != null;
    }


    // Funcion para consultar los videojuegos de un usuario
    public java.util.List<Videojuego> consultarVideojuegosUsuario(String id){
        return gu.consultarVideojuegosUsuario(id);
    }


    public void insertarFondos(Usuario u, Double valor)
    {
        gu.insertarFondos(u, valor);
    }

    public Usuario consultarUsuario(String id_usr)
    {
        return gu.consultarUsuario(id_usr);
    }
    // -----------------------------------------------------------------------





    // FUNCIONES RELACIONADAS CON GESTIONDEMO
    // -----------------------------------------------------------------------

    // Funcion que permite consultar una demo a partir de un mes y/o ano
    public Demo consultarDemo(int mes, int ano)
    {
        return gdem.consultarDemo(mes, ano);
    }


    // -----------------------------------------------------------------------



    // FUNCIONES RELACIONADAS CON GESTIONTORNEOS
    // -----------------------------------------------------------------------

    // Funcion para consultar la cantidad de torneos ganados por un usuario
    public int torneosGanados(Usuario u){
        return gt.torneosGanados(u);
    }
    // -----------------------------------------------------------------------




    // FUNCIONES RELACIONADAS CON GESTIONRESEÑA
    // -----------------------------------------------------------------------

    public void consultarResenhas(Videojuego v)
    {
        gr.consultarResenhas(v);
    }

    public float consultarMediaResenhas(Videojuego v)
    {
        return gr.consultarMediaResenhas(v);
    }

    public void publicarResenha(Resenha r)
    {
        gr.publicarResenha(r);
    }

    public void insertarMeGusta( String id_usr, int id_v, int i_res)
    {
        gr.insertarMeGusta(id_usr, id_v, i_res);
    }

    public void borrarMeGusta(String id_usr, int id_v, int i_res)
    {
        gr.borrarMeGusta(id_usr, id_v, i_res);
    }

    public boolean isLiked(String id_usr, int id_v, int i_res)
    {
        return gr.isLiked(id_usr, id_v, i_res);
    }

    public void updateLikes(Resenha r)
    {
        gr.updateLikes(r);
    }

    // -----------------------------------------------------------------------






    // FUNCIONES RELACIONADAS CON GESTIONCOMPRA
    // -----------------------------------------------------------------------

    public void compraVideojuego(Videojuego v){
        gcom.compraVideojuego(v,usuario);
    }

    // Funcion para contar la cantidad de juegos que un usario tiene en propiedad
    public int contarJuegosUsuario(String id_usuario){
        return gcom.contarJuegosUsuario(id_usuario);
    }

    // Comprueba si un usuario tiene un juego en especifico
    public boolean tieneVideojeugo(Usuario usuario, Videojuego videojuego){
        return gcom.tieneVideojuego(usuario, videojuego);
    }

    public void devolverVideojuego(Videojuego v, Usuario u)
    {
        gcom.devolverVideojuego(v, u);
    }

    // -----------------------------------------------------------------------




    // FUNCIONES RELACIONADAS CON GESTIONDLC
    // -----------------------------------------------------------------------

    // Funcion para consultar los DLCs en propiedad de un usuario relacionados con un videojuego especifico
    public List<DLC> consultarDLCsVideojuegoUsuario(Videojuego v, Usuario u){
        return gd.consultarDLCsVideojuegoUsuario(v, u);
    }

    // Funcion para comprobar si un usuario tiene un DLC en especifico
    public boolean tieneDLC(Usuario usuario, DLC dlc){
        return gd.tieneDLC(usuario, dlc);
    }

    // Funcion para comprar un DLC especifico
    public void comprarDLC(DLC d, Usuario u){
        gd.comprarDLC(d, u);
    }

    // Funcion para consultar los DLCs de un videojuego
    public java.util.List<DLC> consultarDLCsVideojuego(Videojuego v){
        return gd.consultarDLCsVideojuego(v);
    }

    // -----------------------------------------------------------------------




    // -----------------------------------------------------------------------
    // -----------------------------------------------------------------------
    // -----------------------------------------------------------------------
}
