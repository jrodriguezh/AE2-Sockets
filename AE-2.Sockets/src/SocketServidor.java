import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServidor {

	public static final int PUERTO = 2040;  // Establecemos el puerto 2040
	
	
	
	
	public static void main(String[] args){
		System.out.println("    ENCENDIENDO LA BIBLIOTECA ");
		System.out.println("----------------------------------");
		Biblioteca biblio = new Biblioteca(); // Generamos la biblioteca en el servidor para que mientras se mantenga encendido, 
											  //para que los diferentes clientes tengan acceso a una misma biblioteca.
		
		int peticion = 0;		
		
		
		try (ServerSocket servidor = new ServerSocket()){
			InetSocketAddress direccion= new InetSocketAddress(PUERTO); 
			servidor.bind(direccion);  //Establecemos la conexi�n del socket con el puerto designado
			
			System.out.println("SERVIDOR: Esperando peticion por el puerto " + PUERTO);
			
			while(true) {
				Socket socketAlCliente = servidor.accept(); // Dejamos en espera el socket del servidor hasta que se le haga una petici�n.
				System.out.println("SERVIDOR: peticion numero "+ ++peticion);
				new ServidorHilo(socketAlCliente, biblio); //Referenciamos el socket y la biblioteca al hilo de cada cliente. 
				
			}
			
			
		} catch(IOException e) {
			System.out.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("SERVIDOR: Error");
			e.printStackTrace();
		}
		
		
		
	}
	
	
}
