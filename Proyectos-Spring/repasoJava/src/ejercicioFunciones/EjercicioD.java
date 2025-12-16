package ejercicioFunciones;

import java.util.Scanner;

public class EjercicioD {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int decimal = 0;
		
		System.out.println("Introduce un número");
		decimal = s.nextInt();

		String numeroBinario = PasarBinario(decimal);
		
		System.out.println("El número " + decimal + " a binario " + " es " + numeroBinario);
		
		s.close();
	}
	
	// Función que pasa un número decimal a binario mediante un bucle
	public static String PasarBinario(int decimal){
		String binario=""; 
		
		if(decimal==0){
			return "0";
		} else {
			while(decimal>0){
				// Calcula el resto de dividir entre 2
				
				// Añade el resto al principio para crear el binario
				binario =  (decimal % 2) + binario;
				// Divide el número entre 2 para seguir calculando el siguiente resto
				decimal /=2;
			}
			
			// binario(num)
			return binario;
		}
		
	}

}
