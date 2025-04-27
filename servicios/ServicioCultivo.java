package servicios;

import modelos.Cultivo;
import modelos.Parcela;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioCultivo {
    private List<Cultivo> cultivos = new ArrayList<>();

    // Listar cultivos existentes
    public List<Cultivo> listarCultivos() {
        return cultivos;
    }

    // Crear un nuevo cultivo
    public void crearCultivo(Cultivo cultivo) {
        cultivos.add(cultivo);
    }

    // Eliminar un cultivo (solo si no tiene actividades pendientes)
    public boolean eliminarCultivo(Cultivo cultivo) {
        if (cultivo.getActividades().stream().noneMatch(a -> !a.isCompletada())) {
            cultivos.remove(cultivo);
            return true;
        }
        return false;
    }

    // Editar la información básica de un cultivo
    public void editarCultivo(Cultivo cultivo, String nombre, String variedad, double superficie, Parcela parcela) {
        cultivo.setNombre(nombre);
        cultivo.setVariedad(variedad);
        cultivo.setSuperficie(superficie);
        cultivo.setParcela(parcela);
    }

    // Buscar cultivos por nombre o variedad
    public List<Cultivo> buscarCultivos(String criterio) {
        return cultivos.stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(criterio) || c.getVariedad().equalsIgnoreCase(criterio))
                .collect(Collectors.toList());
    }
}
