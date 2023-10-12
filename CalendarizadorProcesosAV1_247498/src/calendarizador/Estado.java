
package calendarizador;

/**
 * Contiene los estados posibles de un proceso en un calendarizador de procesos.
 * @author Saul Neri
 */
public class Estado {
    public static final int INACTIVO    = 0x00;
    public static final int ASIGNADO    = 0x01;
    public static final int TERMINADO   = 0x02;
    public static final int ESPERA      = 0x03;
    
    public static String getEstadoTexto(int estado) {
        
        String texto = "";
        
        switch (estado) {
            case Estado.INACTIVO:
                texto = "INACTIVO";
                break;
            case Estado.ASIGNADO:
                texto = "ASIGNADO";
                break;
            case Estado.TERMINADO:
                texto = "TERMINADO";
                break;
            case Estado.ESPERA:
                texto = "ESPERA";
                break;
            default:
                texto = "NO DEFINIDO";
                break;
        }
        
        return texto;
    }
}
