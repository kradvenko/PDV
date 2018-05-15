/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdv;

import Data.Tienda;
import Data.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Kradvenko
 */
public class ConfiguracionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TextField tfTienda;
    @FXML private TextField tfAdministradorUsuario;
    @FXML private TextField tfAdministradorPass;
    @FXML private TextField tfEncargadoUsuario;
    @FXML private TextField tfEncargadoPass;
    
    private Tienda tiendaActual;
    ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarInformacion();
    }
    
    public void cargarInformacion() {
        Tienda t = Tienda.obtenerDatosTienda();
        tiendaActual = t;
        tfTienda.setText(t.getNombre());
        usuarios = FXCollections.observableArrayList();
        usuarios = Usuario.obtenerUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getTipo().contains("ADMINISTRADOR")) {
                tfAdministradorUsuario.setText(usuarios.get(i).getUsuario());
                tfAdministradorPass.setText(usuarios.get(i).getContraseña());
            } else if (usuarios.get(i).getTipo().contains("USUARIO")) {
                tfEncargadoUsuario.setText(usuarios.get(i).getUsuario());
                tfEncargadoPass.setText(usuarios.get(i).getContraseña());
            }
        }
    }
    
    public void guardarInformacion() {
        Tienda.guardarDatosTienda(tiendaActual.getIdTienda(), tfTienda.getText());
        Usuario.actualizarUsuario(usuarios.get(0).getIdUsuario(), tfAdministradorUsuario.getText(), tfAdministradorPass.getText(), usuarios.get(0).getTipo());
        Usuario.actualizarUsuario(usuarios.get(1).getIdUsuario(), tfEncargadoUsuario.getText(), tfEncargadoPass.getText(), usuarios.get(1).getTipo());
    }
    
}
