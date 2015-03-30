package projet_SAR_2014;

public class Message {
	public String tab[]= {"tomatte", "poivron", "courgette", "patate","banane", "batavia", "framboise", "cerise","fraise","chocolat","carotte","betrave","viande","vanille"};
	String message;
	public Message(){
		message = tab[(int) (Math.random()* tab.length)];
		
	}
	public String getMessage(){
		return message;
	}
}
