package projet_SAR_2014;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Producteur extends Thread {

	private PrintWriter out;
	private BufferedReader in;
	private Socket socket = null;
	private String message =null;
	String reponse = null;
	boolean connecte = true;
	public Producteur(Socket soc){
		socket=soc;
	}
	
	public void run(){
	
		try {
			out = new PrintWriter(socket.getOutputStream(),true);
			in =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
			message = new Message().getMessage();
			
			while(connecte){
				// envoie une demande de production
				out.println("PROD");
				out.flush();
				reponse =in.readLine(); 	
				if(sur_reception_de(reponse)){
					// envoie du message au serveur
					out.println(message);
					out.flush();
					System.out.println( "Thread Producteur : "+ this.getName() +", je produis : "+message +".");
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
			
			System.out.println( "Thread Producteur : "+ this.getName() +", je ne peux pas  produire pour l'instant car le tampon est plein");
		}
		return false;
	}

}
