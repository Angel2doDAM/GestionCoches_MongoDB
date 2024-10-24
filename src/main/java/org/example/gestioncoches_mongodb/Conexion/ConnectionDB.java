/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.gestioncoches_mongodb.Conexion;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class ConnectionDB {

//═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗

    public static MongoClient conectar() {
        try {
//            Conexión con la base de datos de mongoDB
            final MongoClient conexion = new MongoClient(new MongoClientURI("mongodb://admin:1234@localhost:27017/?authSource=admin"));
            System.out.println("Conectada correctamente a la BD");
            return conexion;
        } catch (Exception e) {
//            Si falla la conexión muestra un mensaje y el error
            System.out.println("Conexion Fallida");
            System.out.println(e);
            return null;
        }
    }

//═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣

    public static void desconectar(MongoClient con) {
//        Cierra/Termina la conexión con la base de datos
        con.close();
    }

//═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝
}