
package pruebas;

import calendarizador.Estado;
import calendarizador.ParticionFija;
import calendarizador.Procesador;
import calendarizador.Proceso;
import java.util.Scanner;

/**
 *
 * @author Saul Neri
 */
public class SimuladorCalendarizadorProcesos {
       
    public static final int MAX_PROCESOS    = 25;
    public static final int MAX_PARTICIONES = 10; // (bloques de memoria)
    public static final int QUANTUM         = 5;
    
    public static final int CABECERA_ARRIBA_TABLA_CPU       = 1;
    public static final int CABECERA_CENTRO_TABLA_CPU       = 2;
    public static final int CABECERA_ABAJO_TABLA_CPU        = 3;
    public static final int ATENDIENDO_TABLA_CPU            = 4;
    public static final int SIGUIENTE_TABLA_CPU             = 5;
    public static final int GUARDANDO_CONTEXTO_TABLA_CPU    = 6;
    public static final int CARGANDO_CONTEXTO_TABLA_CPU     = 7;
    
    private int apuntadorTabla = 0; 
    private int memoriaTotal = 0;
    private int fragInternaTotal = 0;
    
    public Proceso[] tablaTrabajos;
    public ParticionFija[] tablaMemoria;
    public Procesador procesador;
    
    public SimuladorCalendarizadorProcesos() {
        this.tablaTrabajos  = new Proceso[25];
        this.tablaMemoria   = new ParticionFija[10];
        this.procesador = new Procesador();
        
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
        
        // se les anade 20 de tiempo a cada proceso
        for (int i=0; i < this.tablaTrabajos.length; i++) {
            this.tablaTrabajos[i].setTiempo(tablaTrabajos[i].getTiempo() + 20);
        }        
        
        this.calculaMemoriaTotal();
    }
    
    /**
     * Ejecuta una vez el flujo del simulador. 
     * El simulador consiste en que el usuario vaya presionando la tecla ENTER
     * para ejecutar un paso a la vez del simulador, cada vez que el usuario 
     * presiona ENTER el calendarizador de procesos va avanzando para ver 
     * que proceso mete a la memoria, despues de asignarle un bloque de memoria
     * a un proceso el simulador avanza al siguiente bloque y verifica si esta 
     * vacio para asignarle un bloque de memoria, sigue verificando los procesos
     * hasta que ninguno tenga estado de espera o inactivo.
     */
    private void ejecutarPaso() {
        
        int ptr = apuntadorTabla;
        
        Scanner in = new Scanner(System.in);
        String s;
        
        
        if (tablaMemoria[ptr].getProceso() != null) {
            // obtiene el id del proceso que se le dara el Quantum
            int procId = this.tablaMemoria[ptr].getProceso().getId() - 1;
            
            procesador.cargaContexto(procId + 1);
            procesador.guardarContexto(0);
            
            this.mostrarTablas();
            
            s = in.nextLine();
            
            procesador.cargaContexto(0); // restablece el campo
            procesador.atiendeProceso(procId + 1);
            procesador.setProcesoSiguiente(this.obtenerSiguienteProceso());
            
            for (int i=0; i < QUANTUM; i++) {
                
                // cursor...
                System.out.printf(">>> ");
                s = in.nextLine(); 
                
                int tiempo = this.tablaTrabajos[procId].getTiempo();
                
                // se le resta una unidad de tiempo del Quantum...
                this.tablaTrabajos[procId].setTiempo(tiempo - 1);
                
                boolean tiempoAcabo = (tiempo - 1) <= 0; 
                
                if (tiempoAcabo) {
                    this.mostrarTablas();
                    // se quita el proceso del bloque
                    this.tablaMemoria[ptr].setProceso(null);
                    // se cambia el estado del proceso a terminado...
                    this.tablaTrabajos[procId].setEstado(Estado.TERMINADO);
                    break;
                }
                
                
                this.mostrarTablas();
            }
            
            procesador.guardarContexto(procId+1);
            //this.mostrarTablas();
            procesador.cargaContexto(0); // se restablece el campo
            this.mostrarTablas();
            procesador.atiendeProceso(0); // se restablece el campo
            
            // busca un nuevo proceso para asignarle su Quantum...
        } else {
            asignarBloqueParticion();
        }

        this.calculaFragInternaTotal();
        
        // apunta a la siguiente particion de memoria (siguiente bloque de memoria)
        if (++apuntadorTabla == MAX_PARTICIONES) {
            apuntadorTabla = 0;
        }
    }
    
    /**
     * Asigna la memoria fragmentada total del simulador
     */
    private void calculaFragInternaTotal() {
        int suma = 0;
        
        for (ParticionFija p: tablaMemoria) {
            suma += p.getFragmentacionInterna();
        }
        
        this.fragInternaTotal = suma;
    }
    
    /**
     * Asigna la memoria total del simulador (de la tabla de memoria)
     */
    private void calculaMemoriaTotal() {
        int suma = 0;
        
        for (int i=0; i < MAX_PARTICIONES; i++) {
            suma += this.tablaMemoria[i].getTamanho();
        }
        
        this.memoriaTotal = suma;
    }
    
    /**
     * Le asigna un bloque de memoria a un proceso en el cual alojarse.
     */
    private void asignarBloqueParticion() {
        
        int ptr = apuntadorTabla;
        boolean terminarBusqueda = false;
        
        // se busca un proceso en espera (PRIORITARIO)
        for (int pid=0; pid < MAX_PROCESOS; pid++) {
            Proceso p = this.tablaTrabajos[pid];
            if (p.getEstado() == Estado.ESPERA) {
                if (p.getTamanho() < this.tablaMemoria[ptr].getTamanho()) {
                    this.tablaTrabajos[pid].setEstado(Estado.ASIGNADO);
                    this.tablaMemoria[ptr].setProceso(this.tablaTrabajos[pid]);
                    terminarBusqueda = true;
                    break;
                }
            }
        }
        
        // se busca un proceso inactivo si no se encontraron en espera...
        if (terminarBusqueda == false) {
            for (int pid=0; pid < MAX_PROCESOS; pid++) {
                Proceso p = this.tablaTrabajos[pid];
                if (p.getEstado() == Estado.INACTIVO) {
                    if (p.getTamanho() < this.tablaMemoria[ptr].getTamanho()) {
                        p.setEstado(Estado.ASIGNADO);
                        this.tablaMemoria[ptr].setProceso(p);
                        break;
                    }
                    
                    this.tablaTrabajos[pid].setEstado(Estado.ESPERA);
                }
            }
        }
        
        // fin busqueda de proceso...
    }
    
    /**
     * Devuele los procesos completados por el simulador
     * @return 
     */
    private int procesosCompletados() {
        int procCompletados = 0;
        
        for (Proceso p: tablaTrabajos) {
            if (p.getEstado() == Estado.TERMINADO) procCompletados++;
        }
        
        return procCompletados;
    }
    
    /**
     * Ejecuta la simulacion del calendarizador de procesos...
     */
    public void empezarSimulacion() {
        boolean simulacionCorriendo = true;
        
        int procRestantes = MAX_PROCESOS;
        
        Scanner in = new Scanner(System.in);
        
        while ((procRestantes = MAX_PROCESOS - this.procesosCompletados()) != 0) {
            mostrarTablas();
            System.out.print("> ");
            this.ejecutarPaso();
            String s = in.nextLine();   
        }
        
        // se limpian los campos...
        procesador.atiendeProceso(0);
        procesador.setProcesoSiguiente(0);
        procesador.cargaContexto(0);
        procesador.guardarContexto(0);
        
        this.mostrarTablas(); // ultimo...
    }
    
    /**
     * Muestra las 2 tablas (de procesos y de memoria) una al lado de otra
     */
    public void mostrarTablas() {
        String filaProceso, filaParticion, filaTabla;
       
        
        String separador = "\t";
        
        String headerTabla = String.format(
                "|%10s |%10s |%10s |%10s  |%s|%10s |%10s |%10s  |%10s  | %10s |",
                "ID", "Tiempo", "Tamano", "Estado", separador, "ID", "Tamano", "Proceso", "Tiempo", "F. Interna"
        );
        
             
        String tituloTablaTrabajo   = "                 Tabla de trabajo                 ";
        String tituloTablaMemoria   = String.format("Tabla de memoria [Frag. Interna: %8d] [Total Mem: %10d]", this.fragInternaTotal, this.memoriaTotal);
        String marcoTablaTrabajo    = "+------------------------------------------------+";
        String marcoTablaMemoria    = "+--------------------------------------------------------------+";
        String marcoTablaProcesador = "+--------------------------------------------+";
        
        int contadorFilaCPU         = 0;
        
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
                
                String filaCPU = "";
                
                
                int pid = 0;
                String texto = "";
                
                if (i > MAX_PARTICIONES && i <= MAX_PARTICIONES + 7) {
                    int punteroFilaTabla = i - (MAX_PARTICIONES);
                    
                    switch (punteroFilaTabla) {
                        case CABECERA_ARRIBA_TABLA_CPU:
                            filaCPU = marcoTablaProcesador;
                            break;
                        case CABECERA_CENTRO_TABLA_CPU:
                            filaCPU = "|                 PROCESADOR                 |";
                            break;
                        case CABECERA_ABAJO_TABLA_CPU:
                            filaCPU = marcoTablaProcesador;
                            break;
                        case ATENDIENDO_TABLA_CPU:
                            pid = procesador.procesoAtendiendose();
                            texto = (pid > 0) ? String.valueOf(pid) : "";
                            filaCPU = String.format("|      Atendiendo      |%20s |", texto);
                            break;
                        case SIGUIENTE_TABLA_CPU:
                            pid = procesador.procesoSiguiente();
                            texto = (pid > 0) ? String.valueOf(pid) : "";
                            filaCPU = String.format("|      Siguiente       |%20s |", texto);
                            break;
                        case GUARDANDO_CONTEXTO_TABLA_CPU:
                            pid = procesador.contextoGuardando();
                            texto = (pid > 0) ? String.valueOf(pid) : "";
                            filaCPU = String.format("|  Guardando Contexto  |%20s |", texto);
                            break;
                        case CARGANDO_CONTEXTO_TABLA_CPU:
                            pid = procesador.contextoCargando();
                            texto = (pid > 0) ? String.valueOf(pid) : "";
                            filaCPU = String.format("|  Cargando Contexto   |%20s |", texto);
                            break;
                    }
                    
                    filaTabla = filaProceso + separador + filaCPU;
                    
                    // resetea el contador puntero para dibujarla tabla del procesador
                    if (++punteroFilaTabla >= 7) {
                        punteroFilaTabla = 0;
                    }
                }
            }
                      
            System.out.println(filaTabla);
        }
    }

    /**
     * Obtiene el PID del siguiente proceso que sigue del actual apuntado por el apuntador
     * de tabla de memoria
     * @return Devuelve el PId del siguiente proceso
     */
    public int obtenerSiguienteProceso() {
        int ptr = apuntadorTabla;
        
        int siguiente = 0;
        
        int i=ptr;
        int busquedas = 0;
        
        while (true) {
            if (++i >= this.tablaMemoria.length) {
                i = 0;
            }

            if (this.tablaMemoria[i].getProceso() != null) {
                siguiente = this.tablaMemoria[i].getProceso().getId();
                return siguiente;
            }

            // detecta si ya se dio una vuelta completa a la tabla y no se encontro 
            // ningun proceso siguiente
            if (++busquedas >= this.tablaMemoria.length) {
                return 0;
            }
        }
    }
    
    public static void main(String args[]) {
        SimuladorCalendarizadorProcesos simulador = new SimuladorCalendarizadorProcesos();
        simulador.empezarSimulacion();
    }
}
