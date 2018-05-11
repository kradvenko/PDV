/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Data;

import java.sql.ResultSet;

/**
 *
 * @author Kradvenko
 */
public class Tienda {
    private int idTienda;
    private String nombre;
    
    public Tienda() {
        
    }
    
    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }
    
    public int getIdTienda() {
        return this.idTienda;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public static Tienda obtenerDatosTienda() {
        Tienda t = new Tienda();
        try {
            Conexion con = new Conexion();
            String query = "SELECT * FROM tienda";
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    t = new Tienda();
                    t.setIdTienda(res.getInt("id_tienda"));
                    t.setNombre(res.getString("nombre"));                    
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            
        }
        return t;
    }
    
    public static void guardarDatosTienda(int idTienda, String tienda) {
        try {
            Conexion con = new Conexion();
            String query = "UPDATE tienda SET nombre = '" + tienda + "' WHERE id_tienda = " + idTienda;
            con.executeQueryString(query);
            con.closeCon();
        } catch (Exception exc) {
            
        }
    }
}
