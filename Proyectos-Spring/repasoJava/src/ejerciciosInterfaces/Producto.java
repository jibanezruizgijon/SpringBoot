package ejerciciosInterfaces;

public class Producto {
	protected String marca;
	protected double precio;

	public Producto(String marca, double precio) {
		this.marca = marca;
		this.precio = precio;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "marca=" + marca + ", precio=" + precio;
	}
}