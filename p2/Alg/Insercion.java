package p2;

public class Insercion {
	static int[] v;

	/**
	 * Similiar a ordenar una baraja de cartas. Recorres el vector saltandote el
	 * primer elemento y vas añadiendo en los elementos ya ordenados el elemento
	 * actual sobre el que se este iterando.
	 * s
	 * Complejidad : O(n^2)
	 * 
	 * Comportamiento con distintas disposiciones del vector:
	 * Ordenado : Solo se recorre el vector una vez y no se mueve nigún elemento
	 * Aleatorio : Caso medio
	 * Orden Inverso : El elemento actual siempre es más pequeño que los ya
	 * ordenados entonces hay que moverlo a la primera pos.
	 */
	public static void insercion(int[] a) {
		int n = a.length;
		for (int i = 1; i <= n - 1; i++) {
			int x = a[i];
			int j = i - 1;
			while (j >= 0 && x < a[j]) {
				a[j + 1] = a[j];
				j = j - 1;
			}
			a[j + 1] = x;
		} // for
	}

	public static void main(String arg[]) {
		int n = Integer.parseInt(arg[0]); // tamanno del problema
		v = new int[n];

		Vector.ordenDirecto(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		insercion(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);

		Vector.ordenInverso(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		insercion(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);

		Vector.ordenAleatorio(v);
		System.out.println("VECTOR A ORDENAR ES");
		Vector.escribe(v);
		insercion(v);
		System.out.println("VECTOR ORDENADO ES");
		Vector.escribe(v);
	} // fin de main

}
