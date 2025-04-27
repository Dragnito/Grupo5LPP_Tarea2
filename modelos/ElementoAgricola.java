package modelos;

import java.time.LocalDate;

public abstract class ElementoAgricola {
    protected String nombre;
    protected LocalDate fechaSiembra;
    protected String estado; // ACTIVO, EN_RIESGO, COSECHADO

    public ElementoAgricola(String nombre, LocalDate fechaSiembra, String estado) {
        this.nombre = nombre;
        this.fechaSiembra = fechaSiembra;
        this.estado = estado;
    }

    // Getters y setters (definir y obtener atributos)

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaSiembra() {
        return fechaSiembra;
    }

    public void setFechaSiembra(LocalDate fechaSiembra) {
        this.fechaSiembra = fechaSiembra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Método abstracto que las subclases deben implementar (por enunciado de la tarea)

    public abstract String obtenerDescripcion();
}