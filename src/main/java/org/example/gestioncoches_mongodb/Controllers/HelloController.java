package org.example.gestioncoches_mongodb.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import com.mongodb.MongoClient;
import org.example.gestioncoches_mongodb.CRUD.CocheCRUD;
import org.example.gestioncoches_mongodb.Clases.Coche;
import org.example.gestioncoches_mongodb.DAO.CocheDAO;
import org.example.gestioncoches_mongodb.Util.AlertUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    CocheCRUD cocheCRUD = new CocheCRUD();

    @FXML
    private TableColumn ColMarca;

    @FXML
    private TableColumn ColMatricula;

    @FXML
    private TableColumn ColModelo;

    @FXML
    private TableColumn ColTipo;

    @FXML
    private TableView<Coche> LaTabla;

    @FXML
    private TextField MarcaTXT;

    @FXML
    private TextField MatriculaTXT;

    @FXML
    private TextField ModeloTXT;

    @FXML
    private ComboBox TipoComboBox;

    Coche coche1;
    Coche selecCoche = null;

    List<Coche> coches = null;

    private String[] Tipos = {"Mono Bolumen", "Deportivo", "Descapotable", "Furgoneta", "Carabana", "4x4", "Sub"};

    ObservableList<Coche> data;

    @FXML
    void OnEliminarClic(ActionEvent event) {
        if (!camposVacios()) {
            if (selecCoche == null) {
                AlertUtils.mostrarError("Debe haber un coche seleccionado con anterioridad");
            } else {
                cocheCRUD.eliminarCoche(selecCoche);
                cargarTabla();
                vaciarCampos();
                AlertUtils.mostrarAcierto("Coche eliminado");
            }
        }
    }

    @FXML
    void OnGuardarClic(ActionEvent event) {
        if (!camposVacios()) {
            datosCoche1(MatriculaTXT.getText(), MarcaTXT.getText(), ModeloTXT.getText(), TipoComboBox.getValue().toString());
            cocheCRUD.guardarCoche(coche1);
            cargarTabla();
            vaciarCampos();
        }
    }

    @FXML
    void OnModificarClic(ActionEvent event) {
        if (!camposVacios()) {
            Coche cocheViejo = selecCoche;
            Coche cocheNuevo = new Coche(MatriculaTXT.getText(), MarcaTXT.getText(), ModeloTXT.getText(), TipoComboBox.getValue().toString());
            if (cocheCRUD.editarCoche(cocheViejo, cocheNuevo)){
                vaciarCampos();
                cargarTabla();
            }
        }
    }

    public void OnMouseClic(MouseEvent mouseEvent) {
        selecCoche = (Coche) LaTabla.getSelectionModel().getSelectedItem();
        camposLlenos();
    }

    @FXML
    void OnSalirClic(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        coches = cocheCRUD.obtenerCoches();

        ColMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        ColMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        ColModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        ColTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        data = FXCollections.observableArrayList();

        LaTabla.setItems(FXCollections.observableList(coches)); //añade a la tabla la info de "data"

        TipoComboBox.getItems().addAll(Tipos);
    }

    public void cargarTabla() {
        LaTabla.getItems().clear();

        coches = cocheCRUD.obtenerCoches();

        LaTabla.setItems(FXCollections.observableList(coches)); //añade a la tabla la info de "data"
    }

    public boolean camposVacios() {
        Boolean vacio = false;
        if (MatriculaTXT.getText().isEmpty() || MarcaTXT.getText().isEmpty() || ModeloTXT.getText().isEmpty() || TipoComboBox.getValue() == null) {
            AlertUtils.mostrarError("Todos los campos deben estar rellenos");
            vacio = true;
        }
        return vacio;
    }

    public void vaciarCampos() {
        MatriculaTXT.setText("");
        MarcaTXT.setText("");
        ModeloTXT.setText("");
        TipoComboBox.setValue("");
    }

    public void camposLlenos() {
        MatriculaTXT.setText(selecCoche.getMatricula());
        MarcaTXT.setText(selecCoche.getMarca());
        ModeloTXT.setText(selecCoche.getModelo());
        TipoComboBox.setValue(selecCoche.getTipo());
    }

    public void datosCoche1(String Matricula, String Marca, String Modelo, String Tipo) {
        coche1 = new Coche(Matricula, Marca, Modelo, Tipo);
    }
}
