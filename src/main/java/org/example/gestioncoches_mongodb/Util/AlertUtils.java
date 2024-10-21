package org.example.gestioncoches_mongodb.Util;

import javafx.scene.control.Alert;

//Clase ara no mostrar alertas, creada para no repetir tanto código
public class AlertUtils {

    //    Función de alerta erronea
    public static void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setContentText(mensaje);
        alerta.show();
    }

    //    Funcion de alerta acertada
    public static void mostrarAcierto(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setContentText(mensaje);
        alerta.show();
    }
}
