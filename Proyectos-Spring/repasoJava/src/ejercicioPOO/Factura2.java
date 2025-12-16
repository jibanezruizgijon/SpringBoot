package ejercicioPOO;

public class Factura2 {
	private String c_articulo;
	private int litro_vendidos;
	
	
	public Factura2(String c_articulo, int litro_vendidos) {
		super();
		this.c_articulo = c_articulo;
		this.litro_vendidos = litro_vendidos;
	}
	public double calcularImporte() {
	    return litro_vendidos * obtenerPrecio();
	}
	public double obtenerPrecio() {
	    switch (c_articulo) {
	        case "1":
	            return 0.6;
	        case "2":
	            return 3.0;
	        case "3":
	            return 1.25;
	        default:
	            return 0; 
	    }
	}

}
