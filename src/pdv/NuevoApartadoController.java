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
 * @author carloscui
 */
public class NuevoApartadoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML Label lblTotal;
    @FXML Label lblRestan;
    @FXML TextField tfAbono;
    @FXML TextField tfCliente;
    
    private NuevaVentaController parent;
    private Float total;
    private Float abono;
    private Float restan;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setParent(NuevaVentaController parent, Float total) {
        this.parent = parent;
        this.total = total;
        lblTotal.setText("$ " + String.format("%.2f", total));
    }
    
    public void tfAbono_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                abono = Float.parseFloat(tfAbono.getText());
                if (abono >= total) {
                    abono = 0f;
                    tfAbono.setText("0");
                }
            } catch (NumberFormatException exc) {
                abono = 0f;
            } finally {
                calcularRestan();
            }
        }
    }
    
    public void calcularRestan() {
        if (abono == 0f) {            
            return;
        }
        restan = total - abono;
        lblRestan.setText("$ " + String.format("%.2f", restan));
    }
    
    public void abono_efectivo() {
        guardarApartado("EFECTIVO");
    }
    
    public void abono_tarjeta() {
        guardarApartado("TARJETA");
    }
    
    public void guardarApartado(String tipo) {
        try {
            parent.guardarApartado(tipo, restan, tfCliente.getText(), abono);
            cancelarApartado();
        } catch (Exception exc) {
            
        }
    }
    
    public void cancelarApartado() {
        Stage stage = (Stage) tfAbono.getScene().getWindow();
        stage.close();
    }
}
