/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author Carlos Contreras
 */
public class Bitacora {
    
    public Bitacora() {
        
    }
    
    public static void guardarBitacoraGeneral(String movimiento, int idUsuario) {
        try {
            Conexion con = new Conexion();
            String query = "INSERT INTO bitacora_general (bitacora, fecha, id_usuario) "
                    + "VALUES ('" + movimiento + "', datetime(strftime('%s','now'), 'unixepoch', 'localtime'), " + idUsuario + ")";
            con.executeQueryString(query);
            con.closeCon();
        } catch (Exception exc) {
            
        }
    }
}
