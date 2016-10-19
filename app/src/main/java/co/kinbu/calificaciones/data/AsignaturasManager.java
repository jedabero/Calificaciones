package co.kinbu.calificaciones.data;

/**
 * AsignaturasManager
 * Created by jedabero on 6/03/16.
 */
public class AsignaturasManager {

    public AsignaturasManager() {

    }

    public static void actualizarDefinitiva(Asignatura asignatura) {
        double definitiva = 0;
        int pesoTotal = 0;
        for (Nota nota : asignatura.getNotas()) {
            definitiva += nota.getValor()*nota.getPeso();
            pesoTotal += nota.getPeso();
        }
        definitiva /= pesoTotal;
        asignatura.setDefinitiva(definitiva);
    }

}
