/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdv;

import Data.Articulo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Carlos Contreras
 */
public class CantidadCompraController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    public ComprasController parent;
    
    @FXML TextField tfCantidad;
    @FXML TextField tfCosto;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void tfCantidad_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            parent.ActualizarCantidadArticuloCompra(Float.parseFloat(tfCantidad.getText()), Float.parseFloat(tfCosto.getText()));
            cerrar();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            cerrar();
        }
    }
    
    public void setCantidad(float cantidad, float costo) {
        tfCantidad.setText(String.valueOf(cantidad));
        tfCosto.setText(String.valueOf(costo));
    }
    
    public void cerrar() {
        Stage stage = (Stage) tfCantidad.getScene().getWindow();
        stage.close();
    }
    
}
