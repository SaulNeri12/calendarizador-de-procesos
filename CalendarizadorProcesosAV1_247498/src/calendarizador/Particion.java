package calendarizador;

import interfaces.ControlProceso;

/**
 * @author Saul Neri
 */
public class Particion implements ControlProceso {
    
    protected int numId;
    protected int tiempo;
    protected int tamanho;
    protected int estado;
    protected Proceso proceso;
    
    public Particion() {
        this.proceso = null; // no asignado por defecto
    }
    
    public Particion(int numId, int tiempo, int tamanho) {
        this.numId = Math.abs(numId);
        this.tiempo = Math.abs(tiempo);
        this.tamanho = Math.abs(tamanho);
        
        this.proceso = null; // no asignado por defecto
    }
    
    /**
     * Devuelve el proceso que esta alojado en la particion
     * @return 
     */
    public Proceso getProceso() {
        return this.proceso;
    }
    
    /**
     * Le asigna un proceso a la particion
     * @param proceso 
     */
    public void setProceso(Proceso proceso) {
        if (null == proceso) {
            throw new Error("# [Proceso.setProceso(...)] El proceso dado es null");
        }
        
        this.proceso = proceso;
        this.estado = proceso.getEstado();
        this.tiempo = proceso.getTiempo();
    }
    
    /**
     * Devuelve el ID de la particion
     * @return 
     */
    @Override
    public int getId() {
        return this.numId;
    }
    
    /**
     * Devuelve el tiempo que requiere el proceso que se encuentra en la particion
     * @return
     */
    @Override
    public int getTiempo() {
        return this.tiempo;
    }
      
    /**
     * Devuelve el tamano de la particion
     * @return 
     */
    @Override
    public int getTamanho() {
        return this.tamanho;
    }
    
    /**
     * Devuelve el estado del proceso que se encuentra en la particion
     * @return 
     */
    @Override
    public int getEstado() {
        return this.proceso.getEstado();
    }
    
    /**
     * Asigna el ID de la particion
     * @param id
     */
    @Override
    public void setId(int id) {
        this.numId = Math.abs(id);
    }

    /**
     * Asigna el tiempo del proceso que se encuentra en la particion
     * @param tiempo 
     */
    @Override
    public void setTiempo(int tiempo) {
        this.tiempo = Math.abs(tiempo);
    }

    /**
     * Asigna el tamanho de la particion
     * @param tamanho
     */
    @Override
    public void setTamanho(int tamanho) {
        this.tamanho = Math.abs(tamanho);
    }

    /**
     * Asigna el estado del proceso que se encuentra en la particion
     * @param estado
     */
    @Override
    public void setEstado(int estado) {
        this.proceso.setEstado(estado);
    }
    
    @Override
    public String toString() {
        return String.format(
                "Particion {numId: %d, tiempo: %d, tamanho: %d, proceso: %s}", 
                this.getId(),
                this.getTiempo(),
                this.getTamanho(),
                this.getProceso()
        );
    }
}
