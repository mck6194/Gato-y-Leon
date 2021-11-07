package es.uc3m.labda.memetracker.tracker.newstracker;

public class Noticia {
	String IdPost = null;
	String Numero = null;
	String FechaHora = null;
	
	public void setIdPost(String Id){
	    if (Id != ""){
	    	IdPost = Id;
	    }
	}
	public String getIdPost(){
	    return IdPost;
	}
	
	public void setNumero(String Num){
		if (Num != ""){
			Numero= Num;
		}
//		Numero = Num;
	}
	
	public String getNumero(){
	    return Numero;
	}
	
	public void setFechaHora(String Fec){
	    if (Fec != ""){
	    	FechaHora = Fec;
	    }
	}
	public String getFechaHora(){
	    return FechaHora;
	}

}
