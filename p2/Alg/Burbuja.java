package p2;

public class Burbuja {

	static int[] v;

	/**
	 * Recorre el vector de izquierda a derecha sin llegar al último (el último
	 * elemento ya queda ordenado de forma automática) . Luego recorremos todos los
	 * elementos desde el final hasta la posición actual en la que nos encontramos.
	 * Si el valor anterior es menor que el actual intercambiamos.
	 * 
	 * Complejidad: O(n^2)
	 * 
	 * Comportamiento con distintas disposiciones del vector:
	 * Ordenado : nunca va a realizar el intercambio de valores
	 * Aleatorio : Caso medio
	 * Orden Inverso : siempre se va a realizar el intercambio de valores
	 * 
	 * @param a vector a ordenar
	 */
	public static void burbuja(int[] a) {
		int n = a.length;
		int x;
		for (int i = 0; i <= n - 2; i++) // Recorremos de 0-Penúltimo
			for (int j = n - 1; j > i; j--) // Recorremos desde último-posActual
				if (a[j - 1] > a[j]) { // Si anterior > actual intercambiamos
					x = a[j - 1];
					a[j - 1] = a[j];
					a[j] = x;
				}
	}

	public static void main(String arg[]) {
		int n = Integer.parseInt(arg[0]); // tamanno del problema
		v = new int[n];

		Vector.ordenDirecto(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		burbuja(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);

		Vector.ordenInverso(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		burbuja(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);

		Vector.ordenAleatorio(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		burbuja(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);
	} // fin de main

}
