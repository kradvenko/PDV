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
 * @author Carlos Contreras
 */
public class Turno {
    private int idTurno;
    private String nombre;
    private String horaInicio;
    private String horaFin;
    
    public Turno() {
        
    }
    
    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }
    
    public int getIdTurno() {
        return this.idTurno;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
    
    public String getHoraInicio() {
        return this.horaInicio;
    }
    
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
    
    public String getHoraFin() {
        return this.horaFin;
    }
    
    public static ObservableList<Turno> obtenerTurnos() {
        ObservableList<Turno> turnos = FXCollections.observableArrayList();        
        try {
            Conexion con = new Conexion();
            String query = "SELECT * FROM turnos";
            
            ResultSet res = con.executeQueryResultSet(query);
            
            if (res != null) {
                while (res.next()) {
                    Turno t = new Turno();
                    t.setIdTurno(res.getInt("id_turno"));
                    t.setNombre(res.getString("nombre"));
                    t.setHoraInicio(res.getString("hora_inicio"));
                    t.setHoraFin(res.getString("hora_fin"));
                    turnos.add(t);
                }
            }
        } catch (Exception exc) {
            
        }
        
        return turnos;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }
    
}
