package ejercicio3g;

import java.util.ArrayList;
import java.util.Scanner;

public class Main_Persona {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String nombre = "";
		int edad = 0;
		char sexo = ' ';
		double peso = 0;
		double altura = 0;
		boolean esMayor;
		int pesoIdeal = 0;
		ArrayList<Persona> personas = new ArrayList<>();

		// Se piden los datos
		System.out.println("Introduce el nombre:");
		nombre = s.nextLine();

		System.out.println("Introduce la edad");
		edad = s.nextInt();

		System.out.println("Introduce el sexo ('H' hombre, 'M' mujer)");
		sexo = s.next().charAt(0);

		// Funcina con la coma "," y no con el punto "."
		System.out.println("Introduce el peso");
		peso = s.nextDouble();

		System.out.println("Introduce la altura");
		altura = s.nextDouble();

		// Se introducen los datos
		Persona persona1 = new Persona(nombre, edad, sexo, peso, altura);
		Persona persona2 = new Persona(nombre, edad, sexo);
		Persona persona3 = new Persona();

		persona3.setNombre("Laura");
		persona3.setEdad(24);
		persona3.setSexo('M');
		persona3.setPeso(67);
		persona3.setAltura(1.56);

		// Cada persona se al array personas
		personas.add(persona1);
		personas.add(persona2);
		personas.add(persona3);

		// Recorre cada persona añadida al array para decir su peso y si es mayor de edad
		for (Persona p : personas) {
			esMayor = p.esMayorEdad();
			pesoIdeal = p.calcularIMC();
			if (esMayor) {
				System.out.println(p.getNombre() +" es mayor de edad");
			} else {
				System.out.println(p.getNombre() +" no es mayor de edad");
			}
			if (pesoIdeal == 1) {
				System.out.println(p.getNombre() +" tiene sobrepeso");
			} else if (pesoIdeal == 0) {
				System.out.println(p.getNombre() +" está por debajo de su peso ideal");
			} else {
				System.out.println(p.getNombre() +" está en su peso ideal");
			}
		}

		// Muestra todos los datos de cada persona
		System.out.println(persona1);
		System.out.println(persona2);
		System.out.println(persona3);
	}

}
