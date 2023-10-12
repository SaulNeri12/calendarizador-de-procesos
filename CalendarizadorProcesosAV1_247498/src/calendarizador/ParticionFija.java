package calendarizador;

/**
 * @author Saul Neri
 */
public class ParticionFija extends Particion {
    protected int fragInterna;
    
    public ParticionFija() {
        super();
        
        this.fragInterna = 0; // por defecto
    }
    
    public ParticionFija(int numId, int tiempo, int tamanho) {
        super(numId, tiempo, tamanho);
        
        this.fragInterna = 0; // por defecto
    }
    
    /**
     * Devuelve la fragmentacion interna total de la particion.
     * @return 
     */
    public int getFragmentacionInterna() {
        return this.fragInterna;
    }
    
    /**
     * Asigna la fragmentacion interna de la particion por la dada como parametro.
     * @param fragInterna 
     */
    public void setFragmentacionInterna(int fragInterna) {
        this.fragInterna = fragInterna;
    }
    
    /**
     * Le suma o resta (dependiendo si tamanho tiene signo negativo) a la fragmentacion
     * interna total de la particion.
     * @param tamanho 
     */
    public void actualizarFragmentacionInterna(int tamanho) {
        this.fragInterna += tamanho;
    }
    
    @Override
    public String toString() {
        return String.format(
                "ParticionFija {numId: %d, tiempo: %d, tamanho: %d, proceso: %s}", 
                this.getId(),
                this.getTiempo(),
                this.getTamanho(),
                this.getProceso()
        );
    }
}
