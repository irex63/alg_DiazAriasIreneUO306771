package p6Opcional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LaberintoTodas {

    public static void main(String[] args) {
        // if (args.length < 1) {
        // System.out.println("Entrada mal introducida");
        // return;
        // }
        int lado = 7;
        int[][] tablero = new int[lado][lado];
        String ruta = "CasosPrueba/caso1.txt";

        try (Scanner lector = new Scanner(new File(ruta))) {
            for (int i = 0; i < tablero.length; i++) {
                for (int j = 0; j < tablero[0].length; j++) {
                    if (lector.hasNextInt()) {
                        tablero[i][j] = lector.nextInt();
                    }
                }
            }
            // Pintamos el tablero oriniginal
            System.out.println("Laberinto :");
            for (int i = 0; i < lado; i++) {
                for (int j = 0; j < lado; j++) {
                    System.out.print(tablero[i][j] + " ");
                }
                System.out.println();
            }

            LaberintoTodas alg = new LaberintoTodas(tablero, lado);
            List<List<String>> sols = alg.run();

            pintarTablero(sols);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public LaberintoTodas(int[][] tablero, int lado) {
        this.tablero = tablero;
        this.lado = lado;

        run();
    }

    private List<List<String>> soluciones;
    private int[][] tablero;
    private int lado;

    private List<List<String>> run() {
        soluciones = new ArrayList<>();
        solve(0, 0);
        return soluciones;
    }

    private void solve(int fila, int colum) {
        // CASO SOLUCIÓN
        if (fila == (lado - 1) && colum == (lado - 1)) {
            soluciones.add(construirSolucion(tablero));
            return;
        }

        // MOVERSE A LA SIGUIENTE POS
        int nuevaFila = 0;
        int nuevaColum = 0;
        // MOVERSE IZQ
        nuevaFila = fila;
        nuevaColum = colum - 1;
        if (nuevaColum >= 0 && tablero[nuevaFila][nuevaColum] == 0) {
            tablero[nuevaFila][nuevaColum] = 2;
            solve(nuevaFila, nuevaColum);
            tablero[nuevaFila][nuevaColum] = 0;
        }
        // MOVERSE DER
        nuevaFila = fila;
        nuevaColum = colum + 1;
        if (nuevaColum < lado && tablero[nuevaFila][nuevaColum] == 0) {
            tablero[nuevaFila][nuevaColum] = 2;
            solve(nuevaFila, nuevaColum);
            tablero[nuevaFila][nuevaColum] = 0;
        }
        // MOVERSE ABAJO
        nuevaFila = fila - 1;
        nuevaColum = colum;
        if (nuevaFila >= 0 && tablero[nuevaFila][nuevaColum] == 0) {
            tablero[nuevaFila][nuevaColum] = 2;
            solve(nuevaFila, nuevaColum);
            tablero[nuevaFila][nuevaColum] = 0;
        }
        // MOVERSE ARRIBA
        nuevaFila = fila + 1;
        nuevaColum = colum;
        if (nuevaFila < lado && tablero[nuevaFila][nuevaColum] == 0) {
            tablero[nuevaFila][nuevaColum] = 2;
            solve(nuevaFila, nuevaColum);
            tablero[nuevaFila][nuevaColum] = 0;
        }
    }

    private List<String> construirSolucion(int[][] tablero) {
        List<String> solucion = new ArrayList<>();
        for (int i = 0; i < tablero.length; i++) {
            String fila = "";
            for (int j = 0; j < tablero[0].length; j++) {
                int valor = tablero[i][j];
                if (valor == 0) {
                    fila += ". ";
                } else if (valor == 1) {
                    fila += "H ";
                } else if (valor == 2) {
                    fila += "* ";
                }
            }
            solucion.add(fila);
        }
        return solucion;
    }

    private static void pintarTablero(List<List<String>> resultado) {
        for (int i = 0; i < resultado.size(); i++) {
            System.out.println("Solución " + (i + 1) + ":");
            for (String fila : resultado.get(i)) {
                System.out.println(fila);
            }
            System.out.println();
        }
    }

}
