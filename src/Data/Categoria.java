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
 * @author Kradvenko
 */
public class Categoria {
    private int idCategoria;
    private String nombre;
    
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
    
    public int getIdCategoria() {
        return this.idCategoria;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public Categoria() {
        
    }
    
    public static ObservableList<Categoria> obtenerListaCategorias() {
        ObservableList<Categoria> categorias = FXCollections.observableArrayList();
        try {
            Conexion con = new Conexion();
            String query = "SELECT * FROM categorias ORDER BY nombre";
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Categoria c = new Categoria();
                    c.setIdCategoria(res.getInt("id_categoria"));
                    c.setNombre(res.getString("nombre"));
                    categorias.add(c);
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            
        }
        return categorias;
    }
    
    public static void agregarCategoria(String categoria) {
        try {
            Conexion con = new Conexion();
            String query = "INSERT INTO categorias (nombre) VALUES ('" + categoria + "')";
            con.executeQueryResultSet(query);
        } catch (Exception exc) {
            
        }
    }
    
    public static void actualizarCategoria(int idCategoria, String nombre) {
        try {
            Conexion con = new Conexion();
            String query = "UPDATE categorias SET nombre = '" + nombre + "' WHERE id_categoria = " + idCategoria;
            con.executeQueryResultSet(query);
        } catch (Exception exc) {
            
        }
    }
    
    public static Categoria obtenerCategoria(int idCategoria) {
        Categoria c = new Categoria();
        try {
            Conexion con = new Conexion();
            String query = "SELECT * FROM categorias WHERE id_categoria = " + idCategoria;
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    c = new Categoria();
                    c.setIdCategoria(res.getInt("id_categoria"));
                    c.setNombre(res.getString("nombre"));
                }
            }
        } catch (Exception exc) {
            
        }
        return c;
    }
    
    public static Categoria obtenerCategoria(String categoria) {
        Categoria c = new Categoria();
        try {
            Conexion con = new Conexion();
            String query = "SELECT * FROM categorias WHERE nombre = '" + categoria + "'";
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    c = new Categoria();
                    c.setIdCategoria(res.getInt("id_categoria"));
                    c.setNombre(res.getString("nombre"));
                }
            }
        } catch (Exception exc) {
            
        }
        return c;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }
}