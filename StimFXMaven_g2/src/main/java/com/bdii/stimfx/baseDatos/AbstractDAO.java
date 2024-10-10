/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bdii.stimfx.baseDatos;

/**
 *
 * @author basesdatos
 */
public abstract class AbstractDAO {
   private com.bdii.stimfx.aplicacion.FachadaAplicacion fa;
   private java.sql.Connection conexion;

   
    protected java.sql.Connection getConexion(){
        return this.conexion;
    }
    
    protected void setConexion(java.sql.Connection conexion){
        this.conexion=conexion;
    }
   
   protected void setFachadaAplicacion(com.bdii.stimfx.aplicacion.FachadaAplicacion fa){
       this.fa=fa;
   }

}
