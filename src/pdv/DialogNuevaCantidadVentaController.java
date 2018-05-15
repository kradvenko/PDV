/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdv;

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
 * @author Kradvenko
 */
public class DialogNuevaCantidadVentaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML TextField tfCantidad;
    @FXML TextField tfDescuentoEfectivo;
    @FXML TextField tfDescuentoPorcentaje;
    
    private NuevaVentaController parent;
    private int index;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setParent(NuevaVentaController parent) {
        this.parent = parent;
    }
    
    public void setCantidad(int cantidad, float descuentoEfectivo, float descuentoPorcentaje, int index) {
        tfCantidad.setText(String.valueOf(cantidad));
        tfDescuentoEfectivo.setText(String.valueOf(descuentoEfectivo));
        tfDescuentoPorcentaje.setText(String.valueOf(descuentoPorcentaje));
        this.index = index;
    }
    
    public void tfCantidad_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                int cantidad = Integer.parseInt(tfCantidad.getText());
                float descuentoEfectivo = Float.parseFloat(tfDescuentoEfectivo.getText());
                float descuentoPorcentaje = Float.parseFloat(tfDescuentoPorcentaje.getText());
                parent.setCantidadArticuloVenta(cantidad, descuentoEfectivo, descuentoPorcentaje, index);
                Stage stage = (Stage) tfCantidad.getScene().getWindow();
                stage.close();
            } catch (NumberFormatException exc) {
                
            }
        } else if (event.getCode() == KeyCode.ESCAPE) {
            Stage stage = (Stage) tfCantidad.getScene().getWindow();
            stage.close();
        }
    }
}