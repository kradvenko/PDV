/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author kradv
 */
public class Proveedor {
    private int idProveedor;
    private String nombre;
    private String notas;
    
    public Proveedor() {
        
    }
    
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
    
    public int getIdProveedor() {
        return this.idProveedor;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNotas(String notas) {
        this.notas = notas;
    }
    
    public String getNotas() {
        return this.notas;
    }
    
    public static ObservableList<Proveedor> obtenerListaProveedores() {
        ObservableList<Proveedor> proveedores = FXCollections.observableArrayList();
        
        try {
            Conexion con = new Conexion();
            String query = "SELECT * FROM proveedores ";
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Proveedor p = new Proveedor();
                    p.setIdProveedor(res.getInt("id_proveedor"));
                    p.setNombre(res.getString("nombre"));
                    p.setNotas(res.getString("notas"));
                    proveedores.add(p);
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            
        }
        
        return proveedores;
    }
    
    public static void agregarProveedor(String nombre, String notas) {
        try {
            Conexion con = new Conexion();
            String query = "INSERT INTO proveedores(nombre, notas) VALUES ('" + nombre + "', '" + notas + "')";
            con.executeQueryString(query);
            con.closeCon();
        } catch (Exception exc) {
            
        }
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }
}
