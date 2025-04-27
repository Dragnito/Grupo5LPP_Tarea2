package modelos;

import java.util.ArrayList;
import java.util.List;

public class Parcela {
    private String codigo;
    private double tamano;
    private List<Cultivo> cultivos;

    public Parcela(String codigo, double tamano) {
        this.codigo = codigo;
        this.tamano = tamano;
        this.cultivos = new ArrayList<>();
    }

    // Getters y Setters (definir y obtener atributos)

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getTamano() {
        return tamano;
    }

    public void setTamano(double tamano) {
        this.tamano = tamano;
    }

    public List<Cultivo> getCultivos() {
        return cultivos;
    }

    public void setCultivos(List<Cultivo> cultivos) {
        this.cultivos = cultivos;
    }

    // Métodos para agregar y eliminar cultivos

    public void agregarCultivo(Cultivo cultivo) {
        this.cultivos.add(cultivo);
    }

    public void eliminarCultivo(Cultivo cultivo) {
        this.cultivos.remove(cultivo);
    }
}