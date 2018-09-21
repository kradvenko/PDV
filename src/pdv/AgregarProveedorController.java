/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdv;

import Data.Proveedor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kradv
 */
public class AgregarProveedorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML TextField tfNombre;
    @FXML TextArea taNotas;
    
    @FXML Button btnCerrar;
    
    public ComprasController parent;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void cerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
    
    public void guardar() {
        if (tfNombre.getText().length() > 0) {
            Proveedor.agregarProveedor(tfNombre.getText(), taNotas.getText());
            parent.cargarProveedores();
            cerrar();
        } else {
            
        }
    }
}
