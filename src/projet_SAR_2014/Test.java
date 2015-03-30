package projet_SAR_2014;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class Test {
	public static void main(String[] args) {
			int port=1290;	
			String taille=null;
			System.out.println("veuillez saisir la taille du tampon. merci");
			BufferedReader taille_tampon = new BufferedReader(new InputStreamReader(System.in));
			try {
				taille = taille_tampon.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Tampon tampon = new Tampon(Integer.parseInt(taille));
			ServerSocket SocketServer = null;
			Socket socket=null;
			try{
			
				SocketServer=new ServerSocket(port);
				while(true){	
				 socket=SocketServer.accept();
				//pour chque client, la prise en charge est assuree par un thread serveur, 
				 //principe "surveillant-executatnt", serveur concurrent
				SiteT thread=new SiteT(socket, tampon);
				thread.start();
				}	
			}catch(IOException e){
				System.err.println("Erreur : "+e);
			}finally{
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				SocketServer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}	
}
