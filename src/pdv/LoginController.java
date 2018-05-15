/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdv;

import Data.Conexion;
import Data.Usuario;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Kradvenko
 */
public class LoginController implements Initializable {
    
    private MainController parent;
    
    @FXML private Text actiontarget;
    @FXML private TextField tfUsuario;
    @FXML private TextField tfContrasena;    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        Usuario u = new Usuario();
        if (tfUsuario.getText().length() <= 0) {
            actiontarget.setText("No ha escrito el nombre de usuario.");
            return;
        }
        if (tfContrasena.getText().length() <= 0) {
            actiontarget.setText("No ha escrito la contraseña de usuario.");
            return;
        }
        u = Usuario.iniciarSesion(tfUsuario.getText(), tfContrasena.getText());
        if (u != null) {
            if (u.getEstado().contains("ACTIVO")) {
                parent.setUsuarioActual(u);
                Stage stage = (Stage) tfUsuario.getScene().getWindow();
                stage.close();
            } else {
                actiontarget.setText("El usuario no se encuentra activo");
            }
        } else {
            actiontarget.setText("Error");
        }
    }
    
    public void setParent(MainController parent) {
        this.parent = parent;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}
