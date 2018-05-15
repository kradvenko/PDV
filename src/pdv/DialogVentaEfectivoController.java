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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kradvenko
 */
public class DialogVentaEfectivoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML Label lblTotal;
    @FXML Label lblCambio;
    
    @FXML TextField tfEfectivo;
    
    private NuevaVentaController parent;
    private Float total;
    private Float efectivo;
    private Float cambio;
    
    private boolean cobro;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cobro = false;
    }    
    
    public void setParent(NuevaVentaController parent, Float total) {
        this.parent = parent;
        this.total = total;
        lblTotal.setText("$ " + String.format("%.2f", total));
    }
    
    public void tfEfectivo_KeyPressed(KeyEvent event) {        
        if (event.getCode() == KeyCode.ENTER) {
            if (cobro == true) {
                cobrar();
            } else {
                try {
                    efectivo = Float.parseFloat(tfEfectivo.getText());
                    if (efectivo < total) {
                        efectivo = 0f;
                        tfEfectivo.setText("0");
                    }
                    cobro = true;
                } catch (NumberFormatException exc) {
                    efectivo = 0f;
                    tfEfectivo.setText("0");
                } finally {
                    calcularCambio();
                }
            }
        } else {
            cobro = false;
        }
    }
    
    public void calcularCambio() {
        if (efectivo == 0f) {            
            return;
        }
        cambio = efectivo - total;
        lblCambio.setText("$ " + String.format("%.2f", cambio));
    }
    
    public void cobrar() {
        if (efectivo > 0f) {
            parent.guardarVenta("EFECTIVO", cambio);
            Stage stage = (Stage) tfEfectivo.getScene().getWindow();
            stage.close();
        }
    }
}
