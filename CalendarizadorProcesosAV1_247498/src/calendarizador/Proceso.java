
package calendarizador;

import interfaces.ControlProceso;

/**
 * @author Saul Neri
 */
public class Proceso implements ControlProceso {

    protected int numId;
    protected int tiempo;
    protected int tamanho;
    protected int estado;
    
    public Proceso() {
        this.numId = 0;
        this.tiempo = 0;
        this.tamanho = 0;
        this.estado = Estado.INACTIVO;
    }
    
    public Proceso(int numId, int tiempo, int tamanho, int estado) {
        this.numId = Math.abs(numId);
        this.tiempo = Math.abs(tiempo);
        this.tamanho = Math.abs(tamanho);
        this.estado = Math.abs(estado);
    }
    
    @Override
    public int getId() {
        return this.numId;
    }

    @Override
    public int getTiempo() {
        return this.tiempo;
    }

    @Override
    public int getTamanho() {
        return this.tamanho;
    }

    @Override
    public int getEstado() {
        return this.estado;
    }

    @Override
    public void setId(int id) {
        this.numId = Math.abs(id);
    }

    @Override
    public void setTiempo(int tiempo) {
        this.tiempo = Math.abs(tiempo);
    }

    @Override
    public void setTamanho(int tamanho) {
        this.tamanho = Math.abs(tamanho);
    }

    @Override
    public void setEstado(int estado) {
        this.estado = Math.abs(estado);
    }
    
    @Override
    public String toString() {
        return String.format(
                "Proceso {numId: %d, tiempo: %d, tamanho: %d, estado: %s}",
                this.getId(),
                this.getTiempo(),
                this.getTamanho(),
                Estado.getEstadoTexto(this.getEstado())
        );
    }
}
