package ejercicioFunciones;

import java.util.Scanner;

public class EjercicioA {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n1 = 0;
		int n2 = 0;

		// Pide introducir los dos números
		System.out.println("Introduce un primer número:");
		n1 = s.nextInt();

		System.out.println("Introduce un segundo número:");
		n2 = s.nextInt();

		mostrar(n1,n2);
		s.close();
	}

	public static void mostrar(int n1, int n2) {
		// El if tiene en cuenta cual de los dos números es el pequeño
		// para empezar a recorrer el for con el número más pequeño hasta el más grande
		if (n1 <= n2) {
			for (int i = n1; i <= n2; i++) {
				System.out.println(i);
			}
		} else {
			for (int i = n2; i <= n1; i++) {
				System.out.println(i);
			}
		}
	}
}
