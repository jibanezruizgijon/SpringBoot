package estructurasDinamicas;

import java.util.ArrayList;

public class Ejercicio4d {

	public static void main(String[] args) {
		ArrayList <String> coche = new ArrayList<>(6);
		
		coche.add("ferrari");
		coche.add("ford");
		coche.add("renault");
		coche.add("bmw");
		
		System.out.println("Lista de coches: " + coche.size());
		
		coche.trimToSize();

		System.out.println("Lista de coches: " + coche.size());
	}

}
