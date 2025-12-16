package ejercicio3g;



public class Persona {
	private String nombre;
	private int edad;
	private String DNI;
	private char sexo;
	private double peso;
	private double altura;
	private final char SexoPorDefecto= 'H';

	
	// Constructor vacío con los datos por defecto
	public Persona() {
		this.nombre = "";
        this.edad = 0;
        this.DNI = generaDNI(); 
        this.sexo = SexoPorDefecto; 
        this.peso = 0.0;
        this.altura = 0.0;
	}
	
	// Constructor con algunos datos
	public Persona(String nombre, int edad, char sexo) {
		super();
		this.nombre = nombre;
		this.edad = edad;
		this.sexo = sexo;
		this.DNI = generaDNI();
	}

	// Constructor con todos los datos
	public Persona(String nombre, int edad, char sexo, double peso, double altura) {
		super();
		this.nombre = nombre;
		this.edad = edad;
		this.DNI = generaDNI();
		this.sexo = sexo;
		this.peso = peso;
		this.altura = altura;
	}
	
	// Método que te indice de masa corporal
	public int calcularIMC() {
		int imc = 0;
		double resultado = 0;
		resultado = peso / (altura*altura);
		if (resultado < 20) {
			imc = -1;
		}
		if(resultado >= 20 && resultado <=25) {
			imc = 0;
		}
		if(resultado > 25) {
			imc =  1;
		}
		return imc;
	}
	
	// Método que te dice si eres mayor de edad
	public boolean esMayorEdad() {
		boolean esMayor ;
		
		if(edad < 18) {
			esMayor = false;
		} else {
			esMayor = true;
		}
		return esMayor;
	}
	
	public void comprobarSexo(char sexo) {
		if (sexo != 'H' || sexo != 'M') {
			sexo = 'H';
		} 
	}

	
	// Método que te genera un DNI a partir de números aleatorios
	public String generaDNI() {
		String[] letras = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
		int dni [] = new int[8];;
		int resto = 0;
		String DNI = "";
		
		int numero = (int) (Math.random() * 100000000);
		
		resto = numero % 23;
		
		String letra = letras[resto];
		
		DNI += Integer.toString(numero) + letra ;
		return DNI;
	}
	
	
	// Getters y Setters
	public String getNombre() {
		return nombre;
	}

	public int getEdad() {
		return edad;
	}

	public String getDNI() {
		return DNI;
	}

	public char getSexo() {
		return sexo;
	}

	public double getPeso() {
		return peso;
	}

	public double getAltura() {
		return altura;
	}

	public char getSexoPorDefecto() {
		return SexoPorDefecto;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	// Método para mostrar todos los datos de la persona
	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", edad=" + edad + ", DNI=" + DNI + ", sexo=" + sexo + ", peso=" + peso
				+ ", altura=" + altura + "]";
	}

}
