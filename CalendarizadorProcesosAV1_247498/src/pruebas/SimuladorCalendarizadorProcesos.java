/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import calendarizador.Estado;
import calendarizador.ParticionFija;
import calendarizador.Proceso;
import java.util.Scanner;

/**
 *
 * @author Saul Neri
 */
public class SimuladorCalendarizadorProcesos {
       
    public static final int MAX_PROCESOS    = 25;
    public static final int MAX_PARTICIONES = 10; // (bloques de memoria)
    
    public Proceso[] tablaTrabajos;
    public ParticionFija[] tablaMemoria;
    
    public SimuladorCalendarizadorProcesos() {
        this.tablaTrabajos  = new Proceso[25];
        this.tablaMemoria   = new ParticionFija[10];
        
        this.reiniciarSimulacion();
    }
    
    /**
     * Vuelve a asignar todos los procesos y particiones a su estado original
     */
    private void reiniciarSimulacion() {
        
        // se inicializan los trabajos (procesos) de la tabla de trabajo
        this.tablaTrabajos[0]   = new Proceso(1,    5,  5760,   Estado.INACTIVO);
        this.tablaTrabajos[1]   = new Proceso(2,    4,  4190,   Estado.INACTIVO);
        this.tablaTrabajos[2]   = new Proceso(3,    8,  3290,   Estado.INACTIVO);
        this.tablaTrabajos[3]   = new Proceso(4,    2,  2030,   Estado.INACTIVO);
        this.tablaTrabajos[4]   = new Proceso(5,    2,  2550,   Estado.INACTIVO);
        this.tablaTrabajos[5]   = new Proceso(6,    6,  6990,   Estado.INACTIVO);
        this.tablaTrabajos[6]   = new Proceso(7,    8,  8940,   Estado.INACTIVO);
        this.tablaTrabajos[7]   = new Proceso(8,    10, 740,    Estado.INACTIVO);
        this.tablaTrabajos[8]   = new Proceso(9,    7,  3930,   Estado.INACTIVO);
        this.tablaTrabajos[9]   = new Proceso(10,   6,  6890,   Estado.INACTIVO);
        this.tablaTrabajos[10]  = new Proceso(11,   5,  6580,   Estado.INACTIVO);
        this.tablaTrabajos[11]  = new Proceso(12,   8,  3820,   Estado.INACTIVO);
        this.tablaTrabajos[12]  = new Proceso(13,   9,  9140,   Estado.INACTIVO);
        this.tablaTrabajos[13]  = new Proceso(14,   10, 420,    Estado.INACTIVO);
        this.tablaTrabajos[14]  = new Proceso(15,   10, 220,    Estado.INACTIVO);
        this.tablaTrabajos[15]  = new Proceso(16,   7,  7540,   Estado.INACTIVO);
        this.tablaTrabajos[16]  = new Proceso(17,   3,  3210,   Estado.INACTIVO);
        this.tablaTrabajos[17]  = new Proceso(18,   1,  1380,   Estado.INACTIVO);
        this.tablaTrabajos[18]  = new Proceso(19,   9,  9350,   Estado.INACTIVO);
        this.tablaTrabajos[19]  = new Proceso(20,   3,  3610,   Estado.INACTIVO);
        this.tablaTrabajos[20]  = new Proceso(21,   7,  7540,   Estado.INACTIVO);
        this.tablaTrabajos[21]  = new Proceso(22,   2,  2710,   Estado.INACTIVO);
        this.tablaTrabajos[22]  = new Proceso(23,   8,  8390,   Estado.INACTIVO);
        this.tablaTrabajos[23]  = new Proceso(24,   5,  5950,   Estado.INACTIVO);
        this.tablaTrabajos[24]  = new Proceso(25,   10, 760,    Estado.INACTIVO);
        
        // se inicializan las particiones fijas (bloques de memoria)
        this.tablaMemoria[0] = new ParticionFija(1,     0,  9500);
        this.tablaMemoria[1] = new ParticionFija(2,     0,  7000);
        this.tablaMemoria[2] = new ParticionFija(3,     0,  4500);
        this.tablaMemoria[3] = new ParticionFija(4,     0,  8500);
        this.tablaMemoria[4] = new ParticionFija(5,     0,  3000);
        this.tablaMemoria[5] = new ParticionFija(6,     0,  9000);
        this.tablaMemoria[6] = new ParticionFija(7,     0,  1000);
        this.tablaMemoria[7] = new ParticionFija(8,     0,  5500);
        this.tablaMemoria[8] = new ParticionFija(9,     0,  1500);
        this.tablaMemoria[9] = new ParticionFija(10,    0,  500);
    }
    
    /**
     * Ejecuta la simulacion del calendarizador de procesos...
     */
    public void ejecutarSimulacion() {
        boolean simulacionCorriendo = true;
        
        this.mostrarTablas();
        
        //Scanner in = new Scanner(System.in);
    }
    
    /**
     * Muestra las 2 tablas (de procesos y de memoria) una al lado de otra
     */
    public void mostrarTablas() {
        String filaProceso, filaParticion, filaTabla;
        
        String separador = "\t\t";
        
        String headerTabla = String.format(
                "|%10s |%10s |%10s |%10s  |%s|%10s |%10s |%10s  |%10s  | %10s |",
                "ID", "Tiempo", "Tamano", "Estado", separador, "ID", "Tamano", "Proceso", "Tiempo", "F. Interna"
        );
        
             
        String tituloTablaTrabajo   = "                 Tabla de trabajo                 ";
        String tituloTablaMemoria   = "                       Tabla de memoria                      ";
        String marcoTablaTrabajo    = "+------------------------------------------------+";
        String marcoTablaMemoria    = "+--------------------------------------------------------------+";
        
        // se imprime la cabecera de las tablas
        System.out.println(tituloTablaTrabajo + separador + tituloTablaMemoria);
        System.out.println(marcoTablaTrabajo + separador + marcoTablaMemoria);
        System.out.println(headerTabla);
        System.out.println(marcoTablaTrabajo + separador + marcoTablaMemoria); 
        
        // se imprimen las filas de la tabla de trabajos y la tabla de memoria...
        for (int i=0; i < MAX_PROCESOS; i++) {
            
            filaProceso = String.format("|% 10d |% 10d |% 10d | %10s |", 
                    this.tablaTrabajos[i].getId(),
                    this.tablaTrabajos[i].getTiempo(),
                    this.tablaTrabajos[i].getTamanho(),
                    Estado.getEstadoTexto(this.tablaTrabajos[i].getEstado()) 
            );
            
            if (i < MAX_PARTICIONES) { 
                
                Proceso procParticion = this.tablaMemoria[i].getProceso();
                
                if (procParticion != null) {
                    filaParticion = String.format("|% 10d |% 10d |% 10d | %10d | %10d |", 
                        this.tablaMemoria[i].getId(),
                        this.tablaMemoria[i].getTamanho(),
                        procParticion.getId(),
                        procParticion.getTiempo(),
                        this.tablaMemoria[i].getFragmentacionInterna()
                    );
                } else { 
                    // en caso de que la particion no tenga un proceso cargado, los campos se dejan en blanco...
                    filaParticion = String.format("|% 10d |% 10d | %10s | %10s | %10s |", 
                        this.tablaMemoria[i].getId(),
                        this.tablaMemoria[i].getTamanho(),
                        " ",
                        " ",
                        " "
                    );
                }
                
                filaTabla = filaProceso + separador + filaParticion;
            } else {
                filaTabla = filaProceso; // se omiten las filas de tabla de memoria INEXISTENTES
            }
            
                            
            System.out.println(filaTabla);
        }
    }
    
    public static void main(String args[]) {
        SimuladorCalendarizadorProcesos simulador = new SimuladorCalendarizadorProcesos();
        simulador.ejecutarSimulacion();
    }
}
