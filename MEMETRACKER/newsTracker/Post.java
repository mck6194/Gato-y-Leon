package es.uc3m.labda.memetracker.tracker.newstracker;

public class Post {
	String IdPost;
	String Titulo;
	String Texto;
	String Fecha;
	
	public void setIdPost(String Id){
	    if (Id != ""){
	    	IdPost = Id;
	    }
	}
	public String getIdPost(){
	    return IdPost;
	}
	
	public void setTitulo(String Tit){
		if (Tit != ""){
			Titulo = Tit;
		}
	}
	
	public String getTitulo(){
	    return Titulo;
	}
	
	public void setTexto(String Tex){
		if (Tex != ""){
			Texto = Tex;
		}
	}
	
	public String getTexto(){
	    return Texto;
	}
	
	public void setFecha(String Fec){
		if (Fec != ""){
			Fecha = Fec;
		}
	}
	
	public String getFecha(){
	    return Fecha;
	}

}

