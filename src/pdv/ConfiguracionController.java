/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdv;

import Data.Categoria;
import Data.Tienda;
import Data.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Kradvenko
 */
public class ConfiguracionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private TableView<Usuario> tvUsuarios;
    @FXML private TableColumn<Usuario, String> tcUsuario;
    @FXML private TableColumn<Usuario, String> tcPassword;
    @FXML private TableColumn<Usuario, String> tcTipo;
    
    @FXML private TextField tfTienda;
    @FXML private TextField tfNuevoUsuario;
    @FXML private TextField tfNuevoPassword;
    @FXML private TextField tfActualizarUsuario;
    @FXML private TextField tfActualizarPassword;
    
    @FXML private ComboBox cbNuevoTipos;
    @FXML private ComboBox cbActualizarTipos;
    
    private Tienda tiendaActual;
    ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
    ObservableList<String> tipos = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcUsuario.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usuario"));
        tcPassword.setCellValueFactory(new PropertyValueFactory<Usuario, String>("contraseña"));
        tcTipo.setCellValueFactory(new PropertyValueFactory<Usuario, String>("tipo"));
        
        tipos.add("ADMINISTRADOR");
        tipos.add("USUARIO");
        
        cbNuevoTipos.setItems(tipos);
        cbActualizarTipos.setItems(tipos);
        
        cbNuevoTipos.getSelectionModel().selectFirst();
        cbActualizarTipos.getSelectionModel().selectFirst();
        
        cargarInformacion();
    }
    
    public void cargarInformacion() {
        Tienda t = Tienda.obtenerDatosTienda();
        tiendaActual = t;
        tfTienda.setText(t.getNombre());
        usuarios = FXCollections.observableArrayList();
        usuarios = Usuario.obtenerUsuarios();
        tvUsuarios.setItems(usuarios);
    }
    
    public void agregarUsuario() {
        String nombre = tfNuevoUsuario.getText();
        String contrasena = tfNuevoPassword.getText();
        String tipo = (String)cbNuevoTipos.getValue();
        
        Usuario.agregarUsuario(nombre, contrasena, tipo);
        
        tvUsuarios.getItems().clear();
        usuarios = Usuario.obtenerUsuarios();
        tvUsuarios.setItems(usuarios);
    }
    
    public void actualizarUsuario() {
        String nombre = tfActualizarUsuario.getText();
        String contrasena = tfActualizarPassword.getText();
        String tipo = (String)cbActualizarTipos.getValue();
        
        Usuario.actualizarUsuario(tvUsuarios.getSelectionModel().getSelectedItem().getIdUsuario(), nombre, contrasena, tipo);
        
        tvUsuarios.getItems().clear();
        usuarios = Usuario.obtenerUsuarios();
        tvUsuarios.setItems(usuarios);
        
        tfActualizarUsuario.setText("");
        tfActualizarPassword.setText("");        
    }
    
    public void tvUsuarios_Click(MouseEvent event) {
        tfActualizarUsuario.setText(tvUsuarios.getSelectionModel().getSelectedItem().getUsuario());
        tfActualizarPassword.setText(tvUsuarios.getSelectionModel().getSelectedItem().getContraseña());
        cbActualizarTipos.getSelectionModel().select(tvUsuarios.getSelectionModel().getSelectedItem().getTipo());
    }
    
    public void guardarInformacion() {
        Tienda.guardarDatosTienda(tiendaActual.getIdTienda(), tfTienda.getText());
        //Usuario.actualizarUsuario(usuarios.get(0).getIdUsuario(), tfAdministradorUsuario.getText(), tfAdministradorPass.getText(), usuarios.get(0).getTipo());
        //Usuario.actualizarUsuario(usuarios.get(1).getIdUsuario(), tfEncargadoUsuario.getText(), tfEncargadoPass.getText(), usuarios.get(1).getTipo());
    }
    
}
