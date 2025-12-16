package ejerciciosClaseString;

import java.util.Scanner;

public class EjercicioA {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String str1 = "";
		String str2 = "";

		// Se piden las dos cadenas de texto
		System.out.println("Introduce la primera cadena de texto:");
		str1 = s.nextLine();

		System.out.println("Introduce la primera cadena de texto:");
		str2 = s.nextLine();
		
		// Se llama a la función
		 recorte(str1, str2);

	        s.close();
	}

	public static void recorte(String c1, String c2) {
		String out1 = "";
		String out2 = "";
		
		// Carácteres en c1 que no estén en c2
		for (int i = 0; i < c1.length(); i++) {
			char caracter = c1.charAt(i);
			if (c2.indexOf(caracter) == -1) { 
				out1 += caracter;
			}
		}
		
		// Carácteres en c2 que no estén en c1
				for (int i = 0; i < c2.length(); i++) {
					char caracter = c2.charAt(i);
					if (c1.indexOf(caracter) == -1) { 
						out2 += caracter;
					}
				}
				
				//Muestra el resultado de las cadenas recortadas
		System.out.println("out1 = " + out1);
		System.out.println("out2 = " + out2);
	}
}
