package projet_SAR_2014;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestProdCons {

	public static void main(String[] args) {
		InetAddress ip = null;
		int port;
		String nbreprod="";
		String nbrecons="";
		
		Socket s=null;
		Socket ss=null;
			if(args.length!=2){
				try {
					ip= InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				port=1290;
			}else{
				try {
					ip=InetAddress.getByName(args[0]);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				port = Integer.parseInt(args[1]);
			}
			System.out.println("veuillez saisir le nombre de producteur. merci");
			BufferedReader nbre_produ = new BufferedReader(new InputStreamReader(System.in));
			
			try {
				nbreprod = nbre_produ.readLine();				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		int np =Integer.parseInt(nbreprod);
		
		System.out.println("veuillez saisir le nombre de Consommateur. merci");
		BufferedReader nbre_cons = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			nbrecons = nbre_cons.readLine();				
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	int nc =	Integer.parseInt(nbrecons);
	
		/*for(int i=0; i<n;i++){
			try {
				s=new Socket(ip,port);
				ss=new Socket(ip,port);
				Producteur prod = new Producteur(s);
				Consommateur cons = new Consommateur(ss);
				prod.start(); cons.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		for(int i=0; i<np;i++){
			try {
				s=new Socket(ip,port);
				Producteur prod = new Producteur(s);
				prod.start(); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for(int i=0; i<nc;i++){
			try {
				ss=new Socket(ip,port);
				Consommateur cons = new Consommateur(ss);
				cons.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/*try {
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
