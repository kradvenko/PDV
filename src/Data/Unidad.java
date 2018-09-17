/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author kradv
 */
public class Unidad {
    private int idUnidad;
    private String nombre;
    
    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }
    
    public int getIdUnidad() {
        return this.idUnidad;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public Unidad() {
        
    }
    
    public static ObservableList<Unidad> obtenerUnidades() {
        ObservableList<Unidad> unidades = FXCollections.observableArrayList();
        try {
            Conexion con = new Conexion();
            String query = "SELECT * FROM unidades";
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Unidad u = new Unidad();
                    u.setIdUnidad(res.getInt("id_unidad"));
                    u.setNombre(res.getString("unidad"));
                    unidades.add(u);
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            
        }
        return unidades;
    }
    
    public static Unidad obtenerUnidad(int idUnidad) {
        Unidad u = new Unidad();
        try {
            Conexion con = new Conexion();
            String query = "SELECT * FROM unidades WHERE id_unidad = " + idUnidad;
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    u = new Unidad();
                    u.setIdUnidad(res.getInt("id_unidad"));
                    u.setNombre(res.getString("unidad"));
                }
            }
        } catch (Exception exc) {
            
        }
        return u;
    }
    
    public static Unidad obtenerUnidad(String unidad) {
        Unidad u = new Unidad();
        try {
            Conexion con = new Conexion();
            String query = "SELECT * FROM unidades WHERE unidad = '" + unidad + "'";
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    u = new Unidad();
                    u.setIdUnidad(res.getInt("id_unidad"));
                    u.setNombre(res.getString("unidad"));
                }
            }
        } catch (Exception exc) {
            
        }
        return u;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }
}
