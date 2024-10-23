package org.example.gestioncoches_mongodb.CRUD;

import org.example.gestioncoches_mongodb.Clases.Coche;
import org.example.gestioncoches_mongodb.DAO.CocheDAO;

import java.util.List;

//Esta clase es un "paso intermedio" sirve para que no se llame al DAO directamente desde el controlador
public class CocheCRUD {

    CocheDAO cocheDAO;

//═══════════════════════════════════════════════════════════════════════════════════════════╗

//    Función para conectar a la base de datos
    public CocheCRUD() {
        cocheDAO = new CocheDAO();
        cocheDAO.conectar();
    }

//═══════════════════════════════════════════════════════════════════════════════════════════╣

//    Función para Guardar todos los coches en una lista
    public List<Coche> obtenerCoches() {
        return cocheDAO.obtenerCoches();
    }

//═══════════════════════════════════════════════════════════════════════════════════════════╣

//    Función para guardar un nuevo coche
    public boolean guardarCoche(Coche coche1) {
        return cocheDAO.GuardarCoche(coche1);
    }

//═══════════════════════════════════════════════════════════════════════════════════════════╣

//    Función para eliminar un coche de la base de datos
    public void eliminarCoche(Coche coche1) {
        cocheDAO.ElimicarCoche(coche1);
    }

//═══════════════════════════════════════════════════════════════════════════════════════════╣

//    Función para modificar un coche ya existente en la base de datos
    public boolean editarCoche(Coche cocheViejo, Coche cocheNuevo) {
        return cocheDAO.EditarCoche(cocheViejo, cocheNuevo);
    }

//═══════════════════════════════════════════════════════════════════════════════════════════╣

//    Función para cerrar la conexión con la base de datos
    public void desconectar(){
        cocheDAO.desconectar();
    }

//═══════════════════════════════════════════════════════════════════════════════════════════╝
}