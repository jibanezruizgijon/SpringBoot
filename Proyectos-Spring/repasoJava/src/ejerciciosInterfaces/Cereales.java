package ejerciciosInterfaces;

import java.time.LocalDate;

public class Cereales extends Producto implements EsAlimento {

	private tipoCereal tipoCereal;
	private LocalDate caducidad;

	public Cereales(String marca,ejerciciosInterfaces.tipoCereal tipoCereal, double precio, LocalDate caducidad ) {
		super(marca, precio);
		this.tipoCereal = tipoCereal;
		this.caducidad = caducidad;
	}

	
	
	public tipoCereal getTipoCereal() {
		return tipoCereal;
	}

	public void setTipoCereal(tipoCereal tipoCereal) {
		this.tipoCereal = tipoCereal;
	}

	@Override
	public void setCaducidad(LocalDate fc) {
		this.caducidad = fc;
	}

	@Override
	public LocalDate getCaducidad() {
		return caducidad;
	}

	@Override
	public int getCalorias() {
		switch (tipoCereal) {
		case avena:
			return 5;
		case maiz:
			return 8;
		case trigo:
			return 12;
		default:
			return 15;
		}
	}

	@Override
	public String toString() {
		return "Cereales [marca=" + marca + ", tipoCereal=" + tipoCereal + ", precio=" + precio + ", caducidad= " + caducidad + ", calorias="
				+ getCalorias() + "]";
	}

}
