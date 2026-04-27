package p2;

/* Este programa sirve para ordenar n elementos con un algoritmo cuadratico
( SELECCION ) */

public class Seleccion {
	static int[] v;

	/**
	 * Coge el valor más pequeño del vector y lo mueve al principio.
	 * 
	 * Complejidad : O(n^2)
	 * 
	 * Siempre tarda lo mismo independientemente de la disposición inicial del
	 * vector
	 */
	public static void seleccion(int[] a) { // O(n^2)
		int n = a.length;
		int x;
		int posmin;
		// Buscar la posicion del mas pequeño de los que quedan
		for (int i = 0; i < n - 1; i++) {
			posmin = i;
			for (int j = i + 1; j < n; j++)
				if (a[j] < a[posmin])
					posmin = j;
			// Intercambia el que toca con el mas pequeño
			x = a[posmin];
			a[posmin] = a[i];
			a[i] = x;

		} // for
	}

	public static void main(String arg[]) {
		int n = Integer.parseInt(arg[0]); // tamanno del problema
		v = new int[n];

		Vector.ordenDirecto(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		seleccion(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);

		Vector.ordenInverso(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		seleccion(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);

		Vector.ordenAleatorio(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		seleccion(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);
	} // fin de main

}
