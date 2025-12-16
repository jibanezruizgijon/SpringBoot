package estructurasDinamicas;

import java.util.ArrayList;
import java.util.Iterator;

public class Ejercicio4f {

	public static void main(String[] args) {
		ArrayList<String> fruta = new ArrayList<>();
		
		fruta.add("manzana");
		fruta.add("platano");
		fruta.add("cereza");
		fruta.add("pera");
		
		for(int i = 0; i< fruta.size(); i++) {
			System.out.println(fruta.get(i));
		}
	}

}
