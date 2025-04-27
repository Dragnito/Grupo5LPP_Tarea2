package modelos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cultivo extends ElementoAgricola {
    private String variedad;
    private double superficie;
    private Parcela parcela;
    private List<Actividad> actividades;

    public Cultivo(String nombre, String variedad, double superficie, Parcela parcela, LocalDate fechaSiembra, String estado, List<Actividad> actividades) {
        super(nombre, fechaSiembra, estado);
        this.variedad = variedad;
        this.superficie = superficie;
        this.parcela = parcela;

        // Si el cultivo no tiene actividades, se inicializa la lista vacía

        this.actividades = (actividades != null) ? actividades : new ArrayList<>();
    }

    // Getters y Setters (definir y obtener atributos)

    public String getVariedad() {
        return variedad;
    }

    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public Parcela getParcela() {
        return parcela;
    }

    public void setParcela(Parcela parcela) {
        this.parcela = parcela;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public void agregarActividad(Actividad actividad) {
        this.actividades.add(actividad);
    }

    @Override
    public String obtenerDescripcion() {
        return "Cultivo de " + nombre + " (" + variedad + "), sembrado el " + fechaSiembra + ", en parcela " + (parcela != null ? parcela.getCodigo() : "Sin asignar");
    }    
}