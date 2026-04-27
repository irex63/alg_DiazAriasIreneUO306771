package p6Opcional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LaberintoTodasRefactorizado {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Entrada mal introducida");
            return;
        }
        int lado = 7;
        int[][] tablero = new int[lado][lado];
        String ruta = "CasosPrueba/" + args[0];

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

            LaberintoTodasRefactorizado alg = new LaberintoTodasRefactorizado(tablero, lado);
            List<List<String>> sols = alg.run();

            pintarTablero(sols);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public LaberintoTodasRefactorizado(int[][] tablero, int lado) {
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

    // Para represnetar el movimiento en 2D
    private final int[] movFila = { 0, 0, -1, 1 }; // Cambio en fila
    private final int[] movColum = { -1, 1, 0, 0 }; // Cambio en columna

    private void solve(int fila, int colum) {
        // CASO SOLUCIÓN
        if (fila == (lado - 1) && colum == (lado - 1)) {
            soluciones.add(construirSolucion(tablero));
            return;
        }

        // MOVIMIENTOS
        for (int i = 0; i < 4; i++) {
            int nuevaFila = fila + movFila[i];
            int nuevaColum = colum + movColum[i];

            if (esValido(nuevaFila, nuevaColum)) {
                // Avanzamos estado
                tablero[nuevaFila][nuevaColum] = 2;

                solve(nuevaFila, nuevaColum);

                // Volvemos atrás
                tablero[nuevaFila][nuevaColum] = 0;
            }
        }
    }

    private boolean esValido(int f, int c) {
        return f >= 0 && f < lado &&
                c >= 0 && c < lado &&
                tablero[f][c] == 0;
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
