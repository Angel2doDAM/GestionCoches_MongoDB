package org.example.gestioncoches_mongodb.DAO;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.gestioncoches_mongodb.Clases.Coche;
import org.example.gestioncoches_mongodb.Conexion.ConnectionDB;

import java.util.ArrayList;
import java.util.List;

public class CocheDAO {

    MongoClient con;
    MongoCollection<Document> collection = null;
    String json;
    Document doc;

    public void conectar() {
        try {
            con = ConnectionDB.conectar();

            //La clase MongoDatabase nos ofrece el métod0 getDatabase() que nos permite seleccionar la base de datos
            //con la que queremos trabajar
            // Me conecto a la BD "taller" si NO existe la crea.

            MongoDatabase database = con.getDatabase("tallerAngel");


            //creando una coleccion
            database.createCollection("coches");


            //Inserto un documento en la coleccion coches
            collection = database.getCollection("coches");

            // Eliminar la colección y empezar de nuevo
            collection.drop();
            System.out.println("La coleccion se ha borrado Correctamente.\n");
            //creo una nueva coleccion
            database.createCollection("coches");
            System.out.println("Coleccion creada Satisfactoriamente.\n");

            collection = database.getCollection("coches");


        } catch (Exception exception) {
            System.err.println(exception.getClass().getName() + ": " + exception.getMessage());
        }

        Coche coche1 = new Coche();

        coche1.setMatricula("0168JKL");
        coche1.setMarca("Renault");
        coche1.setModelo("Clio");
        coche1.setTipo("Mono Bolumen");

        Coche coche2 = new Coche();

        coche2.setMatricula("6801LFJ");
        coche2.setMarca("Renault");
        coche2.setModelo("Scenic");
        coche2.setTipo("Mono Bolumen");

        Coche coche3 = new Coche();

        coche3.setMatricula("0123KFC");
        coche3.setMarca("KIA");
        coche3.setModelo("EV3");
        coche3.setTipo("Sub");

        Coche coche4 = new Coche();

        coche4.setMatricula("4567KLK");
        coche4.setMarca("DACIA");
        coche4.setModelo("Duster");
        coche4.setTipo("4x4");

        Gson gson = new Gson();

        json = gson.toJson(coche1);
        doc = Document.parse(json);
        collection.insertOne(doc);

        json = gson.toJson(coche2);
        doc = Document.parse(json);
        collection.insertOne(doc);

        json = gson.toJson(coche3);
        doc = Document.parse(json);
        collection.insertOne(doc);

        json = gson.toJson(coche4);
        doc = Document.parse(json);
        collection.insertOne(doc);

    }

    public void GuardarCoche(Coche cocheNuevo) {
        Gson gson = new Gson();

        json = gson.toJson(cocheNuevo);
        doc = Document.parse(json);
        collection.insertOne(doc);
    }

    public void ElimicarCoche(Coche cocheEliminar) {

        collection.deleteOne(new Document("Matricula", cocheEliminar.getMatricula()));
    }

    public List<Coche> obtenerCoches() {

        List<Coche> coches = new ArrayList<>();

        Gson gson = new Gson();
        MongoCursor<Document> cursor = collection.find().iterator();

        while (cursor.hasNext()) {
            Coche coche = gson.fromJson(cursor.next().toJson(), Coche.class);
            coches.add(coche);
        }
        cursor.close();
        return coches;
    }

}