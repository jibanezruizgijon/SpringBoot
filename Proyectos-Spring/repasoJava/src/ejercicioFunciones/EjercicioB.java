package ejercicioFunciones;

import java.util.Scanner;

public class EjercicioB {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int n = 0;


		// Pide introducir los dos números
		System.out.println("Introduce un número:");
		n = s.nextInt();
		
		// Llama a la función
		impares(n);
		
		s.close();
	}
	
	// Función para sumar los numeros impares desde el 1 
	// hasta el que se indique
	public static void impares (int n) {
		int suma = 0;
		for(int i = 1; i<=n; i++) {
			if(i%2 != 0) {
				suma += i;
			}
		}
		System.out.println("La suma de los números impares hasta " + n + " son " + suma);
			
	}
	
}
