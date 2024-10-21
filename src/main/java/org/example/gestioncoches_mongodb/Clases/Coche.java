package org.example.gestioncoches_mongodb.Clases;

public class Coche {

    private String Matricula;
    private String Marca;
    private String Modelo;
    private String Tipo;

    public Coche() {
    }

    public Coche(String matricula, String marca, String modelo, String tipo) {
        Matricula = matricula;
        Marca = marca;
        Modelo = modelo;
        Tipo = tipo;
    }

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