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
public class DialogNuevaCantidadVentaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML TextField tfCantidad;
    @FXML TextField tfDescuentoEfectivo;
    @FXML TextField tfDescuentoPorcentaje;
    @FXML TextField tfImporte;
    
    @FXML Label lblTipo;
    
    private NuevaVentaController parent;
    private int index;
    
    private Articulo articuloActual;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setParent(NuevaVentaController parent) {
        this.parent = parent;
        articuloActual = parent.articuloVentaActual;
        if ("GRAMOS".equals(articuloActual.getUnidad())) {
            lblTipo.setText("Peso (Gramos)");
            //calcularParaGramos();
        }
    }
    
    public void actualizarImporte() {
        try {
            if (tfCantidad.getText().length() > 0) {
                Float importe = 0f;
                Float cantidad = Float.parseFloat(tfCantidad.getText());
                importe = cantidad * parent.articuloVentaActual.getPrecio();
                tfImporte.setText(String.valueOf(importe));
                calcularParaGramos();
            }
        } catch (Exception exc) {
            tfCantidad.setText("0.0");            
        }
    }
    
    public void setCantidad(float cantidad, float descuentoEfectivo, float descuentoPorcentaje, float importe, int index) {
        tfCantidad.setText(String.valueOf(cantidad));
        tfDescuentoEfectivo.setText(String.valueOf(descuentoEfectivo));
        tfDescuentoPorcentaje.setText(String.valueOf(descuentoPorcentaje));
        tfImporte.setText(String.valueOf(importe));
        this.index = index;        
    }
    
    public void tfCantidad_KeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                float cantidad = Float.parseFloat(tfCantidad.getText());
                float descuentoEfectivo = Float.parseFloat(tfDescuentoEfectivo.getText());
                float descuentoPorcentaje = Float.parseFloat(tfDescuentoPorcentaje.getText());
                float importe = Float.parseFloat(tfImporte.getText());
                parent.setCantidadArticuloVenta(cantidad, descuentoEfectivo, descuentoPorcentaje, importe, index);
                Stage stage = (Stage) tfCantidad.getScene().getWindow();
                stage.close();
            } catch (NumberFormatException exc) {
                
            }
        } else if (event.getCode() == KeyCode.ESCAPE) {
            Stage stage = (Stage) tfCantidad.getScene().getWindow();
            stage.close();
        } else {
            actualizarImporte();
        }
    }
    
    public void calcularParaGramos() {
        if ("GRAMOS".equals(articuloActual.getUnidad())) {
            Float importe = 0f;
            importe = articuloActual.getPrecio() / 1000;
            importe = importe * Float.parseFloat(tfCantidad.getText());
            tfImporte.setText(String.valueOf(importe));
        }
    }
}