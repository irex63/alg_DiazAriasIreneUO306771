package p7;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AlmacenajeContenedoresRyP {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Entrada mal introducida");
            return;
        }

        String ruta = "CasosPrueba/" + args[0];
        try (Scanner sc = new Scanner(new File(ruta))) {
            int C = sc.nextInt();
            List<Integer> listaObjetos = new ArrayList<>();
            while (sc.hasNextInt()) {
                listaObjetos.add(sc.nextInt());
            }

            int[] objetos = new int[listaObjetos.size()];
            for (int i = 0; i < listaObjetos.size(); i++) {
                objetos[i] = listaObjetos.get(i);
            }

            AlmacenajeContenedoresRyP alg = new AlmacenajeContenedoresRyP(C, objetos);
            alg.run();

        } catch (Exception e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Capacidad máxima de cada contenedor
    private int capacidadMaxima;
    // Objectos que se busca almacenar
    private int[] objetos;

    // Carga total de cada contenedor
    private int[] cargaActual;
    // Número de objetos actuales en cada contenedor
    private int[] numeroActual;
    // Matriz para guardar estados: [contenedor][obj]
    private int[][] asignacionActual;

    // Soluciones encontrada
    private int mejorK;
    private int[][] mejorAsignacion;
    private int[] mejorContadores;
    private long numLlamadas = 0;

    public AlmacenajeContenedoresRyP(int capacidad, int[] objetos) {
        this.capacidadMaxima = capacidad;
        this.objetos = objetos;
        this.mejorK = objetos.length + 1;

        // Inicializamos los valores sobre los que vamos a trabajar
        int n = objetos.length;
        this.cargaActual = new int[n];
        this.asignacionActual = new int[n][n];
        this.numeroActual = new int[n];

    }

    private int sumatorioObjetos() {
        int res = 0;
        for (int i = 0; i < objetos.length; i++) {
            res += objetos[i];
        }
        return res;
    }

    public void run() {
        solve(0, 0, sumatorioObjetos());
        imprimirResultado();
    }

    /**
     * ALTURA DEL ÁRBOL DE LLAMDAS : Número de objetos
     * EXTENSIÓN DE CADA LLAMADA : Dónde colocamos cada objeto en cada llamada.
     * Si tenemos dos contendores libres existen dos posibilidades de dónde colocar
     * el objeto
     * 
     * @param objIdx           objeto que vamos a introducir
     * @param contedoresUsados contenedores que se están usando
     */
    private void solve(int objIdx, int contedoresUsados, int sumaRestante) {
        // LowerBound
        // Calcular el número mínimo teórico de contenedores adicionales necesarios
        int cotaMinima = (sumaRestante + capacidadMaxima - 1) / capacidadMaxima;
        // SumatorioObjetos(objeIndex) => La suma es de aquellos objetos que quedan por
        // alamacenar

        if (contedoresUsados + cotaMinima >= mejorK) {
            return;
        }

        // Sumamos el número de llamadas
        numLlamadas++;

        // Si ya superamos el mejor número de contenedores, no seguimos
        // Recalcular el mejork

        if (contedoresUsados >= mejorK) {
            return;
        }

        // Todos los objetos están dentro de los contenedores
        if (objIdx == objetos.length) {
            // Si la solución usa menos cont que la actual mejor
            if (contedoresUsados < mejorK) {
                // Actualizamos la solución previa
                mejorK = contedoresUsados;
                guardarSol(contedoresUsados);
            }
            return;
        }

        // Intentamos colocar en contenedores ya usados
        for (int cont = 0; cont < contedoresUsados; cont++) {
            // Para cada contenedor
            int objectoActual = objetos[objIdx];
            if (cargaActual[cont] + objectoActual <= capacidadMaxima) {
                // Si el objeto actual cabe dentro del contenedor
                // Pasamos de estado y hacemos llamda recursiva
                cargaActual[cont] += objectoActual;
                // Para el contenedor actual y el objecto actual guardamos el peso
                asignacionActual[cont][numeroActual[cont]] = objectoActual;
                // Incrementamos el número de objetos
                numeroActual[cont]++;

                // Hacemos la llamada recursiva con el siguiente objeto
                solve(objIdx + 1, contedoresUsados, sumaRestante - objectoActual);

                // Restauramos el estado previo
                // Decrementar el número de objectos de ese contenedor
                numeroActual[cont]--;
                // Disminuir la carga de ese contenedor
                cargaActual[cont] -= objetos[objIdx];
            }
        }

        // Intentar colocar en nuevo contenedor
        // Solo exploramos la rama si puede haber mejora
        if (contedoresUsados < mejorK - 1) {
            // Solo si puede existir mejora

            // Cambiamos de estado
            cargaActual[contedoresUsados] = objetos[objIdx];
            asignacionActual[contedoresUsados][numeroActual[contedoresUsados]] = objetos[objIdx];
            numeroActual[contedoresUsados]++;

            // Llamada recursiva
            solve(objIdx + 1, contedoresUsados + 1, sumaRestante - objetos[objIdx]);

            // Restauramos el estado
            numeroActual[contedoresUsados]--;
            cargaActual[contedoresUsados] = 0;
        }
    }

    private void guardarSol(int k) {
        mejorAsignacion = new int[k][objetos.length];
        mejorContadores = new int[k];
        for (int i = 0; i < k; i++) {
            mejorContadores[i] = numeroActual[i];
            for (int j = 0; j < numeroActual[i]; j++) {
                mejorAsignacion[i][j] = asignacionActual[i][j];
            }
        }
    }

    private void imprimirResultado() {
        System.out.println("Lista de contenedores y objetos contenidos:");
        for (int i = 0; i < mejorK; i++) {
            System.out.print("Contenedor " + (i + 1) + ": ");
            for (int j = 0; j < mejorContadores[i]; j++) {
                System.out.print(mejorAsignacion[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("El número de contenedores necesario es " + mejorK + ".");
        System.out.println("Número de llamadas recursivas: " + numLlamadas);
    }

}