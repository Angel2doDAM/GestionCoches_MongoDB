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

//    Array para meter los Strings en el combobox
    private String[] Tipos = {"Mono Bolumen", "Deportivo", "Descapotable", "Furgoneta", "Carabana", "4x4", "Sub"};

    ObservableList<Coche> data;

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗

//    Esta función se ejecura al pulsar el botón "Eliminar"
    @FXML
    void OnEliminarClic(ActionEvent event) {
//        Compruebo si hay algún campo vacío
        if (!camposVacios()) {
//            Compruebo si el coche seleccionado es null (es decir, si no hay coche seleccionado)
            if (selecCoche == null) {
                AlertUtils.mostrarError("Debe haber un coche seleccionado con anterioridad");
            } else {
//                Elimina el coche de la base de datos
                cocheCRUD.eliminarCoche(selecCoche);
                cargarTabla();
                vaciarCampos();
                AlertUtils.mostrarAcierto("Coche eliminado");
            }
        }
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

//    Esta función se ejecura al pulsar el botón "Guardar"
    @FXML
    void OnGuardarClic(ActionEvent event) {
//        Compruebo si hay algún campo vacío
        if (!camposVacios()) {
            datosCoche1(MatriculaTXT.getText(), MarcaTXT.getText(), ModeloTXT.getText(), TipoComboBox.getValue().toString());
//            Compruebo si el coche se ha guardado bien para identificar si se deven vaciar los campos o no, a su vez guarda el coche
            if (cocheCRUD.guardarCoche(coche1)) {
                cargarTabla();
                vaciarCampos();
            }
        }
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

//    Esta función se ejecura al pulsar el botón "Modificar"
    @FXML
    void OnModificarClic(ActionEvent event) {
//        Compruebo si hay algún campo vacío
        if (!camposVacios()) {
            Coche cocheViejo = selecCoche;
            Coche cocheNuevo = new Coche(MatriculaTXT.getText(), MarcaTXT.getText(), ModeloTXT.getText(), TipoComboBox.getValue().toString());
//            Compruebo si el coche se ha editado bien para identificar si se deven vaciar los campos o no, a su vez modifica el coche
            if (cocheCRUD.editarCoche(cocheViejo, cocheNuevo)) {
                vaciarCampos();
                cargarTabla();
            }
        }
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

//    Esta función se ejecura al pulsar sobre uno de los coches de la tabla
    public void OnMouseClic(MouseEvent mouseEvent) {
        selecCoche = (Coche) LaTabla.getSelectionModel().getSelectedItem();
        camposLlenos();
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

//    Esta función se ejecura al pulsar el botón "Salir"
    @FXML
    void OnSalirClic(ActionEvent event) {
//        Cierra la conexión y sale del programa
        cocheCRUD.desconectar();
        System.exit(0);
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

//    Esta función se ejecuta al inicio del programa automaticamente
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        coches = cocheCRUD.obtenerCoches();

        ColMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        ColMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        ColModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        ColTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        data = FXCollections.observableArrayList();

//        Añade a la tabla la info de "data"
        LaTabla.setItems(FXCollections.observableList(coches));

        TipoComboBox.getItems().addAll(Tipos);
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

//    Función para meter los coches en la tabla
    public void cargarTabla() {
        LaTabla.getItems().clear();

        coches = cocheCRUD.obtenerCoches();

//        Añade a la tabla los objetos guardados en la lista "coches"
        LaTabla.setItems(FXCollections.observableList(coches));
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

//    Función para comprobar si por lo menos un campo del formulario está vacio
    public boolean camposVacios() {
        Boolean vacio = false;
//        Muestra un error si cualquiera de los campos del formulario están vacíos
        if (MatriculaTXT.getText().isEmpty() || MarcaTXT.getText().isEmpty() || ModeloTXT.getText().isEmpty() || TipoComboBox.getValue() == null) {
            AlertUtils.mostrarError("Todos los campos deben estar rellenos");
            vacio = true;
        }
        return vacio;
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

//    Función para vaciar todos los campos del formulario
    public void vaciarCampos() {
        MatriculaTXT.setText("");
        MarcaTXT.setText("");
        ModeloTXT.setText("");
        TipoComboBox.setValue("");
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

//    Función para auto completar los campos del formulario usando la información del coche seleccionado en la tabla
    public void camposLlenos() {
        MatriculaTXT.setText(selecCoche.getMatricula());
        MarcaTXT.setText(selecCoche.getMarca());
        ModeloTXT.setText(selecCoche.getModelo());
        TipoComboBox.setValue(selecCoche.getTipo());
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

//    Función para meter datos en coche1
    public void datosCoche1(String Matricula, String Marca, String Modelo, String Tipo) {
        coche1 = new Coche(Matricula, Marca, Modelo, Tipo);
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝
}