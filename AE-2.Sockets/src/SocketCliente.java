import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketCliente {

	public static final int PUERTO = 2040; // Establecemos el puerto 2040
	public static final String IP_SERVER = "localhost"; // El cliente tiene que conectarse a la IP del servidor.

	public static void main(String[] args) {

		System.out.println("    APLICACIÓN CLIENTE   ");
		System.out.println("----------------------------------");

		InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);

		try (Scanner sc = new Scanner(System.in)) {

			System.out.println("CLIENTE: Esperando a que el servidor acepte la conexión");
			Socket socketAlCliente = new Socket();
			socketAlCliente.connect(direccionServidor); // Establecemos la conexión con el socket del servidor
			System.out.println("CLIENTE: Conexion establecida... a " + IP_SERVER + " por el puerto " + PUERTO);

			InputStreamReader entrada = new InputStreamReader(socketAlCliente.getInputStream());
			PrintStream salida = new PrintStream(socketAlCliente.getOutputStream());
			BufferedReader entradaBuffer = new BufferedReader(entrada);

			String consulta = "";
			boolean continuar = true;

			do {

				escribirMenu(); // Invocamos al método que escribirá las opciones del menú

				int opcion = sc.nextInt();

				switch (opcion) { // Creamos un switch para cada una de las opciones del cliente.

				case 1:
					System.out.println("Introduce el ISBN a buscar:");
					sc.nextLine(); // No sabemos porque el programa omite una entrada y ejecuta la segunda.
									// Pensamos que es porque nextInt(), consume solo la entrada de número y deja en
									// búfer el salto de línea, que se consume automáticamente en el siguiente
									// nextLine();
					consulta = sc.nextLine();
					salida.println(opcion + "-" + consulta);  // Enviamos la consulta al servidor
					System.out.println(entradaBuffer.readLine()); // Recibimos el resultado del servidor y lo imprimimos
					break;

				case 2:
					System.out.println("Introduce el titulo del libro a buscar:");
					sc.nextLine();
					consulta = sc.nextLine();
					salida.println(opcion + "-" + consulta);
					System.out.println(entradaBuffer.readLine());
					break;
				case 3:
					System.out.println("Introduce el autor a buscar:");
					sc.nextLine();
					consulta = sc.nextLine();
					salida.println(opcion + "-" + consulta);
					System.out.println(entradaBuffer.readLine());
					break;
				case 4:
					System.out.println("Introduce los datos del libro:");
					sc.nextLine();
					System.out.println("ISBN:");

					String isbn = sc.nextLine();
					System.out.println("Titulo:");

					String titulo = sc.nextLine();
					System.out.println("Autor:");

					String autor = sc.nextLine();
					System.out.println("Precio:");

					String precio = sc.nextLine();
					consulta = isbn + "@@" + titulo + "@@" + autor + "@@" + precio; // Enviamos cada dato separado por @@ para poder separarlo en el servidor
					salida.println(opcion + "-" + consulta);
					System.out.println(entradaBuffer.readLine());
					break;

				case 5:
					salida.println(opcion);
					if ("0".equalsIgnoreCase(entradaBuffer.readLine())) { // Establecemos 0 como la respuesta del servidor para cerrar el proceso.
						continuar = false;
					}
					System.out.println("Saliendo del programa...");
					break;

				default:
					System.out.println("Opción no valida");

				}

			} while (continuar);
			socketAlCliente.close();
		} catch (UnknownHostException e) {
			System.err.println("CLIENTE: No encuentro el servidor en la dirección" + IP_SERVER);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("CLIENTE: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("CLIENTE: Error -> " + e);
			e.printStackTrace();
		}

	}

	public static void escribirMenu() {
		System.out.println("Elija una de las siguientes opciones:");
		System.out.println("1. Consulta de libro por ISBN");
		System.out.println("2. Consulta de libro por titulo");
		System.out.println("3. Consulta de libro por autor");
		System.out.println("4. Añadir un libro a la biblioteca");
		System.out.println("5. Salir del programa");
	}

}
