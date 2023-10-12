/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

/**
 * Declara la funcionalidad de un proceso y bloque (particion)
 * @author Saul Neri
 */
public interface ControlProceso {
    /**
     * Devuelve el ID del proceso
     * @return 
     */
    public int getId();
    
    /**
     * Devuelve el tiempo requerido por el proceso
     * @return 
     */
    public int getTiempo();
    
    /**
     * Devuelve el tamano requerido por el proceso
     * @return 
     */
    public int getTamanho();
    
    /**
     * Devuelve el estado del proceso
     * @return 
     */
    public int getEstado();
    
    /**
     * Asigna el ID del proceso
     * @param id 
     */
    public void setId(int id);
    
    /**
     * Asigna el tiempo que requiere el proceso
     * @param tiempo 
     */
    public void setTiempo(int tiempo);
    
    /**
     * Asigna el tamano en bytes requerido por el proceso
     * @param tamanho 
     */
    public void setTamanho(int tamanho);
    
    /**
     * Asigna el estado al proceso
     * @param estado 
     */
    public void setEstado(int estado);
}
