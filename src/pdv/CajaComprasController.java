/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdv;

import Data.Bitacora;
import Data.Compra;
import Data.Dialog;
import Data.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Carlos Contreras
 */
public class CajaComprasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML TextField tfCantidad;
    @FXML Button btnCerrar;
    
    private String cantidadOriginal;
    
    public Usuario currentUser;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfCantidad.setText(String.valueOf(Compra.obtenerCantidadCajaCompras()));
        cantidadOriginal = tfCantidad.getText();
    }
    
    public void cerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
    
    public void cambiarCantidad() {
        try {
            Compra c = new Compra();
            c.cambiarCantidadCajaCompras(Float.parseFloat(tfCantidad.getText()));
            Bitacora.guardarBitacoraGeneral("CAMBIO DE CANTIDAD CAJA COMPRAS - DE " + cantidadOriginal + " A " + tfCantidad.getText(), currentUser.getIdUsuario());
            cerrar();
        } catch (Exception exc) {
            Dialog d = new Dialog();
            d.mensaje = exc.getMessage();
            d.mostrarMensaje();
        }
    }
    
    
}
