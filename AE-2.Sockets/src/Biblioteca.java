
import java.util.ArrayList;

public class Biblioteca {

	private ArrayList<Libro> libros;

	public Biblioteca() {
		libros = new ArrayList<Libro>();

		// Añadimos los 5 libros a la base de datos de la biblioteca

		libros.add(new Libro("9788445000663", "EL SEÑOR DE LOS ANILLOS", "J.R.R. Tolkien", 10.95));
		libros.add(new Libro("9788401337208", "EL NOMBRE DEL VIENTO", "PATRICK ROTHFUSS", 21.75));
		libros.add(new Libro("9788498726138", "EL IMPERIO FINAL", "BRANDON SANDERSON", 12.30));
		libros.add(new Libro("9788499899619", "EL TEMOR DE UN HOMBRE SABIO", "PATRICK ROTHFUSS", 10.40));
		libros.add(new Libro("9789563251951", "JUEGO DE TRONOS", "GEROGE R.R. MARTIN", 20.03));
	}

	public ArrayList<Libro> getLibros() {
		return libros;
	}

	public void setLibros(ArrayList<Libro> libros) {
		this.libros = libros;
	}

	@Override
	public String toString() {
		return "Biblioteca [libros=" + libros + "]";
	}

	public String buscarIsbn(String isbn) {
		String resultado = "";
		for (Libro l : libros) { // Realizamos un for para recorrer todo el Array de la biblioteca
			if (l.getIsbn().contains(isbn)) { // Si el libro contiene ese ISBN se añade al resultado para enviarlo al
												// cliente.
				resultado = resultado + l; // Aunque el ISBN sea unico y no sea necesario realizar un acumulador, pero
											// pensamos que es una buena herramienta si en algún momento se duplica
											// algún dato por error.
			}
		}

		if ("".equalsIgnoreCase(resultado)) { // Si no se ha modificado el String resultado es porque no existe ningún
												// libro. También se podría utilizar isEmpty();
			resultado = "No existe ningún libro con ese ISBN";
		}

		return resultado;
	}

	public String buscarTitulo(String titulo) {
		String resultado = "";
		for (Libro l : libros) {
			if (l.getTitulo().contains(titulo)) {
				resultado = resultado + l;
			}
		}
		if ("".equalsIgnoreCase(resultado)) {
			resultado = "No existe ningún libro con ese titulo";
		}
		return resultado;
	}

	public String buscarAutor(String autor) {
		String resultado = "";
		for (Libro l : libros) {
			if (l.getAutor().contains(autor)) {
				resultado = resultado + l;
			}
		}
		if ("".equalsIgnoreCase(resultado)) {
			resultado = "No existe ningún libro con ese autor";
		}
		return resultado;
	}

	public void agregarLibro(String isbn, String titulo, String autor, double precio) { // Método para agregar libros a
																						// la biblioteca con sus
																						// atributos.

		this.libros.add(new Libro(isbn, titulo, autor, precio));
	}

}