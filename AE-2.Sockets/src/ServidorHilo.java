import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ServidorHilo implements Runnable {

	private Thread hilo;
	private static int numCliente = 0;
	private Socket socketAlCliente;
	public Biblioteca biblio;

	public ServidorHilo(Socket socketAlCliente, Biblioteca biblio) {
		numCliente++;
		hilo = new Thread(this, "Cliente_" + numCliente);
		this.socketAlCliente = socketAlCliente;
		this.biblio = biblio;
		hilo.start();

	}

	@Override
	public void run() {
		System.out.println("Estableciendo comunicacion con " + hilo.getName());
		PrintStream salida = null; // Inicializamos los objetos de comunicaci�n entre sockets
		InputStreamReader entrada = null;
		BufferedReader entradaBuffer = null;

		try (Scanner sc = new Scanner(System.in)) {

			salida = new PrintStream(socketAlCliente.getOutputStream()); // Salida del servidor al cliente
			entrada = new InputStreamReader(socketAlCliente.getInputStream()); // Entrada del servidor al cliente
			entradaBuffer = new BufferedReader(entrada);

			String texto = "";
			boolean continuar = true;

			while (continuar) { // Creamos un bucle que se parar� solo cuando nos llegue del cliente la opci�n 5

				texto = entradaBuffer.readLine().toUpperCase(); // Cambiamos el texto introducido por el cliente a
																// mayusculas
				String[] consulta = texto.split("-"); // Separamos la entrada al servidor para diferenciar la opcion
														// elegida de la consulta.
				int opcion = Integer.parseInt(consulta[0]);
				if (opcion == 5) {
					salida.println("0"); // Enviamos al cliente un 0 para que sepa que queremos terminar el proceso.

					continuar = false;
				} else {

					if (opcion == 1) {
						System.out.println("Buscando el ISBN: " + consulta[1]);
						salida.println(biblio.buscarIsbn(consulta[1]));

					} else {
						if (opcion == 2) {
							System.out.println("Buscando el Titulo: " + consulta[1]);
							salida.println(biblio.buscarTitulo(consulta[1]));
						} else {
							if (opcion == 3) {
								System.out.println("Buscando el Autor: " + consulta[1]);
								salida.println(biblio.buscarAutor(consulta[1]));
							} else {
								if (opcion == 4) {
									System.out.println("Agregando libro: " + consulta[1]);
									String[] agregandoLibro = consulta[1].split("@@"); // Utilizamos el split para
																						// separar los datos del libro a
																						// crear con el m�todo
																						// agregarLibro();
									biblio.agregarLibro(agregandoLibro[0], agregandoLibro[1], agregandoLibro[2],
											Double.parseDouble(agregandoLibro[3]));
									salida.println("Libro a�adido: [ISBN=" + agregandoLibro[0] + ", titulo="
											+ agregandoLibro[1] + ", autor=" + agregandoLibro[2] + ", precio="
											+ (agregandoLibro[3])
											+ "La biblioteca actual consta de los siguientes libros: "
											+ biblio.toString()); // Hemos decidido imprimir el libro a�adido y despu�s toda la 
									                              //biblioteca para comprobar que el
																  // libro introducido es correcto.

								}
							}
						}

					}
				}
			}

			socketAlCliente.close();

		} catch (IOException e) {
			System.err.println("ClienteHilo: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("ClienteHilo: Error");
			e.printStackTrace();
		}

	}
}
