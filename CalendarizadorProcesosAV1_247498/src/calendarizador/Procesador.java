
package calendarizador;

/**
 * estructura que simula ser un manejador de procesos que usa Round-Robin como
 * algoritmo de calendarizacion de procesos
 * @author Saul Neri
 */
public class Procesador {
    private int atendiendo;
    private int siguiente;
    private int contextoGuardando;
    private int contextoCargando;
       
    /**
     * Crea una nueva instancia de una estructura que simula ser un manejador de
     * procesos que usa Round-Robin
     */
    public Procesador() {
        this.atendiendo = 0;
        this.siguiente = 0;
        this.contextoGuardando = 0;
        this.contextoCargando = 0;
    }
    
    /**
     * Indca el proceso que sera el siguiente en procesar.
     * @param pid ID del programa o trabajo
     */
    public void setProcesoSiguiente(int pid) {
        this.siguiente = pid;
    }
    
    /**
     * Atiende ese proceso y camba el estado de atendiendo del procesador
     * al PID dado como parametro
     * @param pid ID del programa o trabajo
     */
    public void atiendeProceso(int pid) {
        this.atendiendo = pid;
    }
    
    /**
     * Indica que se esta guardando el contexto del PID dado
     * @param pid ID del programa o trabajo
     */
    public void setGuardandoContexto(int pid) {
        this.contextoGuardando = pid;
    }
    
    /**
     * Indica que se esta cargando el proceso por el PID dado
     * @param pid ID del programa o trabajo
     */
    public void setContextoCargando(int pid) {
        this.contextoCargando = pid;
    }
    
    /**
     * Devuelve el PID del programa que se esta ejecutando
     * @return PID
     */
    public int procesoAtendiendose() {
        return this.atendiendo;
    }
    
    /**
     * Devuelve el PID del siguiente proceso a procesar
     * @return PID
     */
    public int procesoSiguiente() {
        return this.siguiente;
    }
    
    /**
     * Guarda el contexto del proceso por el PID dado
     * @param pid ID del programa o trabajo
     */
    public void guardarContexto(int pid) {
        this.contextoGuardando = pid;
    }
    
    /**
     * Carga el contexto del proceso por el PID dado
     * @param pid ID del programa o trabajo
     */
    public void cargaContexto(int pid) {
        this.contextoCargando = pid;
    }
    
    /**
     * Devuelve el PID del proceso el cual esta siendo guardado
     * @return PID
     */
    public int contextoGuardando() {
        return this.contextoGuardando;
    }
    
    /**
     * Devuelve el PID del proceso que se esta cargando para su posterior procesamiento
     * @return PID
     */
    public int contextoCargando() {
        return this.contextoCargando;
    }
    
    @Override
    public String toString() {
        return String.format(
                "Procesador {atendiendo: %d, siguiente: %d, guardandoContexto: %d, cargandoContexto: %d}", 
                this.atendiendo, this.siguiente,
                this.contextoGuardando, this.contextoCargando
        );
    }
}
