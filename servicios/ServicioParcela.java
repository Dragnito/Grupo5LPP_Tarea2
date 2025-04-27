package servicios;

import modelos.Parcela;
import modelos.Cultivo;
import java.util.ArrayList;
import java.util.List;

public class ServicioParcela {
    private List<Parcela> parcelas = new ArrayList<>();

    // Listar parcelas con sus cultivos
    public List<Parcela> listarParcelas() {
        return parcelas;
    }

    // Agregar una parcela
    public void agregarParcela(Parcela parcela) {
        parcelas.add(parcela);
    }

    // Eliminar una parcela (solo si no tiene cultivos activos)
    public boolean eliminarParcela(Parcela parcela) {
        if (parcela.getCultivos().stream().noneMatch(c -> c.getEstado().equals("ACTIVO"))) {
            parcelas.remove(parcela);
            return true;
        }
        return false;
    }

    // Editar una parcela
    public void editarParcela(Parcela parcela, double tamano) {
        parcela.setTamano(tamano);
    }

    // Asignar un cultivo a una parcela
    public void asignarCultivoAParcela(Parcela parcela, Cultivo cultivo) {
        parcela.agregarCultivo(cultivo);
        cultivo.setParcela(parcela);
    }
}