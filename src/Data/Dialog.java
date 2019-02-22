/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pdv.DialogWindowController;

/**
 *
 * @author Carlos Contreras
 */
public class Dialog {
    public String mensaje;
    
    public Dialog() {
        
    }
    
    public void mostrarMensaje() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DialogWindow.fxml"));
            Parent root = (Parent)loader.load();
            DialogWindowController controller = loader.<DialogWindowController>getController();
            controller.setMessage(mensaje);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {

        }
    }
}
