/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdv;

import Data.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Carlos Contreras
 */
public class DialogGuardarCompraController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML TextField tfCantidad;
    @FXML TextField tfNumeroNota;
    @FXML TextArea taNotas;
    @FXML TextField tfCantidadCaja;
    
    @FXML Button btnCerrar;
    
    @FXML ComboBox cbUsuarios;
    @FXML ComboBox cbPagarCaja;
    
    ObservableList<Usuario> usuarios;
    ObservableList<String> respuestas;
    
    public ComprasController parent;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cargarRespuestas();
        cargarUsuarios();
    }    
    
    public void cerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
    
    public void guardar() {
        Usuario u = (Usuario)cbUsuarios.getValue();
        String s = (String)cbPagarCaja.getValue();
        String total = tfCantidad.getText();
        String notas = taNotas.getText();
        String numeroNota = tfNumeroNota.getText();
        float cantidadCajaVenta = Float.parseFloat(tfCantidadCaja.getText());
        this.parent.guardarCompraDialog(total, notas, numeroNota, u.getIdUsuario(), s, cantidadCajaVenta);
        cerrar();
    }
    
    public void setTotal(float total) {
        tfCantidad.setText(String.valueOf(total));
    }
    
    public void cargarUsuarios() {
        usuarios = Usuario.obtenerUsuarios();
        cbUsuarios.setItems(usuarios);
        cbUsuarios.getSelectionModel().selectFirst();
    }
    
    public void cargarRespuestas() {
        respuestas = FXCollections.observableArrayList();
        respuestas.add("SI");
        respuestas.add("NO");
        cbPagarCaja.setItems(respuestas);
        cbPagarCaja.getSelectionModel().selectFirst();
    }
}
