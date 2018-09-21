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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Carlos Contreras
 */
public class DialogWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML Button btnCerrar;
    
    @FXML Label lblMessage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setMessage(String message) {
        lblMessage.setText(message);
    }
    
    public void cerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
    
}
