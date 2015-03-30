package projet_SAR_2014;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Consommateur extends Thread {

	private PrintWriter out;
	private BufferedReader in;
	private Socket socket = null;
	private String message =null;
	String reponse = null;
	boolean connecte = true;
	
	public Consommateur(Socket soc){
		socket=soc;
	}
	public void run(){
		try {
			out = new PrintWriter(socket.getOutputStream(),true);
			in =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(connecte){
				// envoie d'une demande de consommation
				out.println("CONS");
				out.flush();
				reponse =in.readLine(); 		
				if(sur_reception_de(reponse)){
				// le serveur m'envoie un message a consommer apres reception d'un ack  
					message = in.readLine();
					System.out.println( " Thread consommateur :  "+ this.getName() +", je consomme : "+message +".");
					connecte = false;
				break;
				}else{
					connecte = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	// sur reception de la reponse du serveur
	public boolean sur_reception_de(String reponse_serveur){
		if(reponse_serveur.equals("ACK")){
			return true;
		}else{
			System.out.println( " Thread consommateur :  "+ this.getName() +", je ne peux pas consommer pour l'instant car le tampon est vide");
	
		}
		return false;
	}
	public String getMessage(){
		return message;
	}
}
