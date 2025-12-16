package ejercicioPOO;

public class Movil implements Comparable<Movil> {

	private String modelo;
	private double precio;
	
	public Movil(String modelo, double precio) {
		super();
		this.modelo = modelo;
		this.precio = precio;
	}
	

	public Movil() {
		super();
	}


	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	 @Override
	public int compareTo(Movil otro) {
		 int resultado = Double.compare(this.precio, otro.precio);

	        // Si el precio es igual, comparamos alfabéticamente por modelo
	        if (resultado == 0) {
	            resultado = this.modelo.compareToIgnoreCase(otro.modelo);
	        }

	        return resultado;
	    }


	 @Override
	 public String toString() {
		return "Movil [modelo=" + modelo + ", precio=" + precio + "]";
	 }
	 
	
	
}
