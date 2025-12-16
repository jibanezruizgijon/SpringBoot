package ejercicioPOO;

import java.util.ArrayList;
import java.util.Collections;

public class EjercicioE {

	public static  void main(String[] args) {
		
		ArrayList <Movil> movil = new ArrayList <>() ;
		
		movil.add(new Movil("iPhone 12 Pro Max", 1259));
		movil.add(new Movil("Xiaomi Mi 10 Pro", 999));
		movil.add(new Movil("Huawei P40 Pro+", 1399));
		movil.add(new Movil("Samsug Z Flip 5G", 1550));
		movil.add(new Movil("Samsung S20", 1500));
		movil.add(new Movil("LG V50", 899));
		movil.add(new Movil("Xiaomi Mi 10 Pro", 999));
		movil.add(new Movil("Huawei P40 Pro+", 1399));
		movil.add(new Movil("Samsung Z Flip 5G", 1550));
		movil.add(new Movil("Samsung S30", 1300));
		movil.add(new Movil("Huawei P50 Pro+", 1399));
		movil.add(new Movil("Samsung Z Flip 5G", 1550));
		
		Collections.sort(movil);
		
		for (Movil m : movil) {
            System.out.println(m);
        }
	}

}
