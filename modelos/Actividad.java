package modelos;

import java.time.LocalDate;

public class Actividad {
    private String tipo;
    private LocalDate fecha;
    private boolean completada;

    public Actividad(String tipo, LocalDate fecha) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.completada = false; // Cuando se crea, siempre está pendiente
    }

    // Getters y Setters (definir y obtener atributos)
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void completar() {
        this.completada = true;
    }

    @Override
    public String toString() {
        return tipo + ":" + fecha + (completada ? " (completada)" : " (pendiente)");
    }
}