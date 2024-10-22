package org.example.gestioncoches_mongodb.Clases;

//Clase para crear objetos "coche" con sus variables
public class Coche {

//    Variables de los coches
    private String Matricula;
    private String Marca;
    private String Modelo;
    private String Tipo;

//    constructor vacío
    public Coche() {
    }

//    Constructor con todas las variables
    public Coche(String matricula, String marca, String modelo, String tipo) {
        Matricula = matricula;
        Marca = marca;
        Modelo = modelo;
        Tipo = tipo;
    }

//    Getter y Setter de todas las variables
    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String matrícula) {
        Matricula = matrícula;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

//    ToString de la clase (no se usa en este programa pero es util tenerlo por si acaso
    @Override
    public String toString() {
        return "Coche{" +
                "Matrícula='" + Matricula + '\'' +
                ", Marca='" + Marca + '\'' +
                ", Modelo='" + Modelo + '\'' +
                ", Tipo='" + Tipo + '\'' +
                '}';
    }
}