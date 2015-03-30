package projet_SAR_2014;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SiteT extends Thread {
	
Tampon tampon ;
private PrintWriter out;
private BufferedReader in;
private Socket socket = null;
boolean connecte = true;

   public SiteT ( Socket socket , Tampon t ){ 
     super("ThreadClient");
     this.socket = socket;
     tampon = t;
   }
   
   public void run() {
	   try {
			InetAddress ip=socket.getInetAddress();
			int port=socket.getPort();
			System.out.println("voila mon adresse ip : "+ ip.getHostAddress() + ", et voila mon numero de port : " + port);
			
			out = new PrintWriter(socket.getOutputStream(),true);	
			in =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    	
		while (connecte) {
			// on teste si c'est une demande de production de la part d'un producteur
			String lire=null;
			try{
			 lire= in.readLine();
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			if(lire.equals("PROD") && lire!=null){
		    	// on verifie si le nbre de message present dans le tampon est inferieur a la taille du tampon
		    	if(tampon.getProduction()){
		    		out.println("ACK");
		    		out.flush();
		    		sur_reception_du(in.readLine());
		    		connecte=false;
		    	}else{
		    		out.println("KO");
		    		out.flush();
		    	}
		    }
			// on teste si c'est une demande de consommation de la part d'un consommateur
			if(lire.equals("CONS")&& lire!=null){
				// on verifie si le nbre de message dans le tampon n est pas egal a 0
		    	if(tampon.getConsommation()){
		    		out.println("ACK");
		    		out.flush();
		    		String conso = tampon.consommer();
		    		out.println(conso);
		    		out.flush();
		    		connecte=false;
		    	}else{
		    		out.println("KO");
		    		out.flush();
		    	}
		    }	      	
		}   
		 
		} catch (IOException e) {
			e.printStackTrace();
		}catch (NullPointerException e) {
			e.printStackTrace();
		}
	   
	   finally {
			try {
				
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
   public void sur_reception_du(String mess){
		tampon.produire(mess);
   }
}