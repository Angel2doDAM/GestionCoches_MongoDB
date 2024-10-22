package org.example.gestioncoches_mongodb.CRUD;

import org.example.gestioncoches_mongodb.Clases.Coche;
import org.example.gestioncoches_mongodb.DAO.CocheDAO;

import java.util.List;

public class CocheCRUD {

    CocheDAO cocheDAO;

    public CocheCRUD() {
        cocheDAO = new CocheDAO();
        cocheDAO.conectar();
    }

    public List<Coche> obtenerCoches() {
        return cocheDAO.obtenerCoches();
    }

    public boolean guardarCoche(Coche coche1) {
        return cocheDAO.GuardarCoche(coche1);
    }

    public void eliminarCoche(Coche coche1) {
        cocheDAO.ElimicarCoche(coche1);
    }

    public boolean editarCoche(Coche cocheViejo, Coche cocheNuevo) {
        return cocheDAO.EditarCoche(cocheViejo, cocheNuevo);
    }

    public void desconectar(){
        cocheDAO.desconectar();
    }

}