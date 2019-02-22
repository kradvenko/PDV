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
public class Usuario {
    private int idUsuario;
    private String usuario;
    private String contraseña;
    private String tipo;
    private String estado;
    
    public Usuario() {
        
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public int getIdUsuario() {
        return this.idUsuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String getUsuario() {
        return this.usuario;
    }
    
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    public String getContraseña() {
        return this.contraseña;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getTipo() {
        return this.tipo;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getEstado() {
        return this.estado;
    }
    
    public static Usuario iniciarSesion(String usuario, String contraseña) {
        Usuario u = new Usuario();
        try {
            Conexion con = new Conexion();
            ResultSet res = con.executeQueryResultSet("SELECT * FROM usuarios WHERE usuario = '" + usuario + "' AND contrasena = '" + contraseña + "'");
            if (res != null) {
                while (res.next()) {
                    u.setIdUsuario(res.getInt("id_usuario"));
                    u.setUsuario(res.getString("usuario"));
                    u.setTipo(res.getString("tipo"));
                    u.setContraseña(res.getString("contrasena"));
                    u.setEstado(res.getString("estado"));
                }
                con.closeCon();
            }
        } catch (Exception exc) {
            u = null;
        }
        return u;
    }
    
    public static ObservableList<Usuario> obtenerUsuarios() {
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
        try {
            Conexion con = new Conexion();
            String query = "SELECT * FROM usuarios";
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Usuario u = new Usuario();
                    u.setContraseña(res.getString("contrasena"));
                    u.setEstado(res.getString("estado"));
                    u.setIdUsuario(res.getInt("id_usuario"));
                    u.setTipo(res.getString("tipo"));
                    u.setUsuario(res.getString("usuario"));
                    usuarios.add(u);
                }
            }
            con.closeCon();
        } catch (Exception exc) {
            
        }
        return usuarios;
    }
    
    public static void actualizarUsuario(int idUsuario, String nombre, String contrasena, String tipo) {
        try {
            Conexion con = new Conexion();
            String query = "UPDATE usuarios SET usuario = '" + nombre + "', contrasena = '" + contrasena + "', tipo = '" + tipo + "' WHERE id_usuario = " + idUsuario + "";
            con.executeQueryString(query);
            con.closeCon();
        } catch (Exception exc) {
            
        }
    }
    
    public static void agregarUsuario(String nombre, String contrasena, String tipo) {
        try {
            Conexion con = new Conexion();
            String query = "INSERT INTO usuarios (usuario, contrasena, tipo, estado) VALUES ('" + nombre + "', '" + contrasena + "', '" + tipo + "', 'ACTIVO')";
            con.executeQueryString(query);
            con.closeCon();
        } catch (Exception exc) {
            
        }
    }
    
    @Override
    public String toString() {
        return this.usuario;
    }
}