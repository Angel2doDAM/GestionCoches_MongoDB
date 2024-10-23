package org.example.gestioncoches_mongodb.DAO;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.gestioncoches_mongodb.Clases.Coche;
import org.example.gestioncoches_mongodb.Conexion.ConnectionDB;
import org.example.gestioncoches_mongodb.Util.AlertUtils;

import java.util.ArrayList;
import java.util.List;

//Clase con todos los métodos
public class CocheDAO {

    MongoClient con;
    MongoCollection<Document> collection = null;
    String json;
    Document doc;

    List<Coche> coches = new ArrayList<>();

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗

//    Función para conectar con la base de datos y añadir 4 coches prefab para así poder cargarlos en el initialice
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

//        Creación de los coches prefab
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

//        Inserción de los coches prefab en la base de datos
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
//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

//    Función para guardar un coche nuevo en la base de datos
    public boolean GuardarCoche(Coche cocheNuevo) {
//        Compruebo si la matricula a introducir ya existe, pues hay muchos coches con las mismas especificaciones pero nunca la misma matrícula
        if (!comprobacionMatricula(cocheNuevo)) {
            Gson gson = new Gson();

//            inserto el coche en la base de datos
            json = gson.toJson(cocheNuevo);
            doc = Document.parse(json);
            collection.insertOne(doc);
            AlertUtils.mostrarAcierto("Coche guardado");
            return true;
        } else {
//            Si ya existe devuelvo false para no vaciar los campos
            return false;
        }
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

//    Función para eliminar un coche de la base de datos
    public void ElimicarCoche(Coche cocheEliminar) {

//        Elimino el coche de la base de datos teniendo en cuanta la matrícula, ya que es un atributo único
        collection.deleteOne(new Document("Matricula", cocheEliminar.getMatricula()));
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

//    Función para editar un coche existente en la base de datos
    public boolean EditarCoche(Coche cocheViejo, Coche cocheNuevo) {
        boolean completo = false;
//        Compruebo si el coche existe dentro de la base de datos
        if (comprobarCoche(cocheViejo)) {
//            Compruebo si todos los campos son iguales a los anteriores, pues sería una tonteria actualizarlo sin modificar nada
            if (cocheViejo.getMatricula().equals(cocheNuevo.getMatricula()) && cocheViejo.getMarca().equals(cocheNuevo.getMarca()) &&
            cocheViejo.getModelo().equals(cocheNuevo.getModelo()) && cocheViejo.getTipo().equals(cocheNuevo.getTipo())) {
                AlertUtils.mostrarError("No has cambiado nada, prueba a variar un campo");
            } else {
//                Actualizo el coche en la base de datos
                collection.updateOne(new Document("Matricula", cocheViejo.getMatricula()),
                new Document("$set", new Document("Matricula", cocheNuevo.getMatricula()).append("Marca", cocheNuevo.getMarca()).append("Modelo", cocheNuevo.getModelo()).append("Tipo", cocheNuevo.getTipo())));
                AlertUtils.mostrarAcierto("Coche editado");
                completo = true;
            }
        } else {
//            Si el coche no existe en la base de datos
            AlertUtils.mostrarError("No puedes editar un coche inexistente");
        }
        return completo;
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

//    Función para obtener todos los coches de la tabla de datos
    public List<Coche> obtenerCoches() {

        Gson gson = new Gson();
        MongoCursor<Document> cursor = collection.find().iterator();

        while (cursor.hasNext()) {
            Coche coche = gson.fromJson(cursor.next().toJson(), Coche.class);
            coches.add(coche);
        }
        cursor.close();
        return coches;
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

//    Función para comprobar si una matrícula ya existe en la base de datos
    public boolean comprobacionMatricula(Coche coche1) {
        boolean existente = false;
//        Compara el atributo matrícula de todos los coches para ver si alguno coincide
        if (coches.stream().anyMatch(coche -> coche.getMatricula().equalsIgnoreCase(coche1.getMatricula()))) {
            AlertUtils.mostrarError("Esa matrícula ya existe");
            existente = true;
        }
        return existente;
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

//    Función para comprobar si un coche estero
    public boolean comprobarCoche(Coche coche1) {
        boolean existe = false;
//        Recorre coches y va comparando 1 a 1 el coche pasado
        if (coches.contains(coche1)) {
            existe = true;
        }
        return existe;
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

//    Función para conectar a la base de datos
    public void desconectar(){
        ConnectionDB.desconectar(con);
    }

//════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝

}