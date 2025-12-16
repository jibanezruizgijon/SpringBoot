package ejercicioPOO;

public class Boligrafos implements Comparable<Boligrafos>{
	private String modelo;
	private double precio;

	
	public Boligrafos() {
		super();
	}
	public Boligrafos(String modelo, double precio) {
		super();
		this.modelo = modelo;
		this.precio = precio;
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
	public int compareTo(Boligrafos otro) {
		return this.modelo.compareToIgnoreCase(otro.modelo);
	}
	
	@Override
	public String toString() {
		return "Boligrafos [modelo=" + modelo + ", precio=" + precio + "]";
	}
	
}
