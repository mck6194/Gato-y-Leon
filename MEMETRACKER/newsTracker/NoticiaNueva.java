package es.uc3m.labda.memetracker.tracker.newstracker;

public class NoticiaNueva extends Noticia{
	
	public static NoticiaNueva copiarNoticiaNueva(Post p){
		NoticiaNueva n = null;
		n.setIdPost(p.getIdPost());
		n.setNumero("1");
				
		return n;
	}

}
