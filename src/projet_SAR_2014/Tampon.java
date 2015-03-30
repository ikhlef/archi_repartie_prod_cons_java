package projet_SAR_2014;

public class Tampon {

	private String[] messages;
	private int tete = 0;
	private int queue = 0;
	private boolean productionPossible = true;
	private boolean consommationPossible = false;
	private static int TAILLE;
	
	public Tampon(int taille){
		
		messages = new String[taille];	
		TAILLE= taille;
	}
	
	public synchronized String consommer(){
		try{ 
			while( !consommationPossible ){ 
				wait();
			//System.out.println("je te met en attente, la consommation n est pas possible actuelement");	
			}
		}catch( InterruptedException e ){
			System.out.println( "interruption" );
			System.exit(1);
		}
	
		String message = messages[tete];
		tete = (tete+1)%TAILLE;
		if( queue==tete ){
			consommationPossible = false;
		}
		//System.out.println( "consommation de : "+message );
		notifyAll();
	return message;
	}
	
	public synchronized void produire( String message ){
	try{
		while( !productionPossible ){
			wait();
			//System.out.println("je te met en attente, la production n est pas possible actuelement");
		}
	}catch( InterruptedException e ){
		System.out.println(" interruption" );
		System.exit(1);
	}
	
	messages[queue] = message;
	consommationPossible = true;
	queue = (queue+1)%TAILLE;
		if ( queue==tete ){
			productionPossible = false;
		}
		//System.out.println( "dépot de : "+message );
		notifyAll();
	}
	public synchronized boolean getProduction(){
		return productionPossible;
	}
	public synchronized boolean getConsommation(){
		return consommationPossible;
	}
	
	public synchronized String [] getTompon(){
		return messages;
	} 
}
