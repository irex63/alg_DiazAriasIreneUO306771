package p7;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AlmacenajeContenedoresTiemposRyP {
    public static void main(String[] args) {

        String ruta = "casosPrueba/test0";
        String extension = ".txt";
        long t1, t2, t;

        System.out.println("Archivo;Tiempo;NumLlamadas");
        for (int i = 0; i < 10; i++) {
            String archivo = ruta + i + extension;
            try (Scanner sc = new Scanner(new File(archivo))) {
                int C = sc.nextInt();
                List<Integer> listaObjetos = new ArrayList<>();
                while (sc.hasNextInt()) {
                    listaObjetos.add(sc.nextInt());
                }

                int[] objetos = new int[listaObjetos.size()];
                for (int j = 0; j < listaObjetos.size(); j++) {
                    objetos[j] = listaObjetos.get(j);
                }
                AlmacenajeContenedoresTiemposRyP alg = new AlmacenajeContenedoresTiemposRyP(C, objetos);
                numLlamadas = 0;
                t1 = System.currentTimeMillis();
                alg.run();
                t2 = System.currentTimeMillis();
                t = t2 - t1;
                System.out.println(archivo +
                        ";" + t + ";" + numLlamadas);

            } catch (Exception e) {
                System.err.println("Error al leer el archivo: " + e.getMessage());
            }
        }
    }

    // Capacidad máxima de cada contenedor
    private int capacidadMaxima;
    // Objectos que se busca almacenar
    private int[] objetos;

    // Carga total de cada contenedor
    private int[] cargaActual;
    // Número de objetos actuales
    private int[] numeroActual;
    // Matriz para guardar estados: [cont][obj]
    private int[][] asignacionActual;

    // Soluciones encontrada
    private int mejorK;
    private int[][] mejorAsignacion;
    private int[] mejorContadores;
    private static int numLlamadas = 0;

    public AlmacenajeContenedoresTiemposRyP(int capacidad, int[] objetos) {
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
        numLlamadas = 0;
        solve(0, 0, sumatorioObjetos());
    }

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

       
        for (int cont = 0; cont < contedoresUsados; cont++) {
           
            int objectoActual = objetos[objIdx];
            if (cargaActual[cont] + objectoActual <= capacidadMaxima) {
                
                cargaActual[cont] += objectoActual;
        
                asignacionActual[cont][numeroActual[cont]] = objectoActual;
                
                numeroActual[cont]++;

                
                solve(objIdx + 1, contedoresUsados, sumaRestante - objectoActual);

               
                numeroActual[cont]--;
                
                cargaActual[cont] -= objetos[objIdx];
            }
        }

        if (contedoresUsados < mejorK - 1) {
            
            cargaActual[contedoresUsados] = objetos[objIdx];
            asignacionActual[contedoresUsados][numeroActual[contedoresUsados]] = objetos[objIdx];
            numeroActual[contedoresUsados]++;

            
            solve(objIdx + 1, contedoresUsados + 1, sumaRestante - objetos[objIdx]);

            
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

}