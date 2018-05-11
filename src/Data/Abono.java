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
 * @author carloscui
 */
public class Abono {
    private int idAbono;
    private int idApartado;
    private String fecha;
    private String tipo;
    private Float abono;
    
    public Abono() {
        
    }
    
    public void setIdAbono(int idAbono) {
        this.idAbono = idAbono;        
    }
    
    public int getIdAbono() {
        return this.idAbono;
    }
    
    public void setIdApartado(int idApartado) {
        this.idApartado = idApartado;        
    }
    
    public int getIdApartado() {
        return this.idApartado;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String getFecha() {
        return this.fecha;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getTipo() {
        return this.tipo;
    }
    
    public void setAbono(Float abono) {
        this.abono = abono;
    }
    
    public Float getAbono() {
        return this.abono;
    }
    
    public static ObservableList<Abono> obtenerAbonosApartado(int idApartado) {
        ObservableList<Abono> abonos = FXCollections.observableArrayList();
        try {
            Conexion con = new Conexion();
            String query = "SELECT * FROM abonos WHERE id_apartado = " + idApartado;
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Abono a = new Abono();
                    a.setIdAbono(res.getInt("id_abono"));
                    a.setIdApartado(res.getInt("id_apartado"));
                    a.setAbono(res.getFloat("abono"));
                    a.setFecha(res.getString("fecha"));
                    a.setTipo(res.getString("tipo"));
                    abonos.add(a);
                }
            }
        } catch (Exception exc) {
            
        }
        return abonos;
    }
    
    public static ObservableList<Abono> obtenerAbonosFecha(String fecha) {
        ObservableList<Abono> abonos = FXCollections.observableArrayList();
        try {
            Conexion con = new Conexion();
            String query = "SELECT * FROM abonos WHERE fecha LIKE '" + fecha + "%'";
            ResultSet res = con.executeQueryResultSet(query);
            if (res != null) {
                while (res.next()) {
                    Abono a = new Abono();
                    a.setIdAbono(res.getInt("id_abono"));
                    a.setIdApartado(res.getInt("id_apartado"));
                    a.setAbono(res.getFloat("abono"));
                    a.setFecha(res.getString("fecha"));
                    a.setTipo(res.getString("tipo"));
                    abonos.add(a);
                }
            }
        } catch (Exception exc) {
            
        }
        return abonos;
    }
}
