package ejercicioPOO;

import java.util.ArrayList;
import java.util.Collections;

public class Main_boligrafos {

	public static void main(String[] args) {
		ArrayList<Boligrafos> boli = new ArrayList <>() ;
		
		boli.add(new Boligrafos("PilotSuperGrip", 1));
		boli.add(new Boligrafos("Pilot G2", 1.3));
		boli.add(new Boligrafos("Bic Cristal", 0.5));
		boli.add(new Boligrafos("Pilot G2", 1.3));
		
		// Como comparar dos boligrafos en concreto
		
		Collections.sort(boli);
		
		for (Boligrafos b : boli) {
            System.out.println(b);
        }

	}

}
