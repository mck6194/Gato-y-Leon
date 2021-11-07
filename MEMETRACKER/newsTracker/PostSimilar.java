package es.uc3m.labda.memetracker.tracker.newstracker;

public class PostSimilar extends Noticia{
	private String IdSimilar;
	
	
	public void setIdSimilar(String Id){
	    if (Id != ""){
	    	IdSimilar = Id;
	    }
	}
	public String getIdSimilar(){
	    return IdSimilar;
	}
	
	public static void main(String args[]){
	PostSimilar post = null;
	post.IdPost = "12";
	post.setNumero("1");
	}
	
	
	
}
