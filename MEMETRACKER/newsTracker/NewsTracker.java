package es.uc3m.labda.memetracker.tracker.newstracker;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.StaleReaderException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Hits;
import org.apache.lucene.store.LockObtainFailedException;

public class NewsTracker {
	static List<Post> list = new ArrayList<Post>();
	static Post post = new Post();
	static PostSimilar ps = new PostSimilar();
	static NoticiaNueva n = new NoticiaNueva();
	
	
	
	
	public static void trackRecentNews() throws CorruptIndexException, ParseException, IOException, SQLException{
		Date fechaHora = new java.util.Date();
		fechaHora.setYear(108);
		fechaHora.setMonth(7);
		fechaHora.setDate(6);
		fechaHora.setHours(12);
		fechaHora.setMinutes(00);
		fechaHora.setSeconds(0);
		
		
		
		DBManager.openConnection();
//		list = DBManager.descargarPostRecientes("2008-07-31 14:15:00");
		list = DBManager.descargarPostRecientes(fechaHora);
		
		
		Date date = new java.util.Date();
		List<Hits> listaHits = new ArrayList<Hits>();		
		System.out.println("TAMAÑO DE LA LISTA DESCARGADA (Nº DE POSTS): " + list.size());		
		System.out.println("*************************************************************");
		System.out.println();


//		Indizador.crearIndice();	//se crea el indice ya que este no existe en principio
//		Indizador.cerrarIndice();    

		Iterator iter = list.iterator();	//variable para recorrer la lista de post descargados

		
		while (iter.hasNext()){
			String id = null;
			post = (Post) iter.next();
			System.out.println("POST --> " + post.getIdPost() + " en tratamiento");
			
			
			listaHits = ClasificaPost.buscaSimilares(post);  

			if (listaHits != null) System.out.println("Nº POSTS SIMILARES AL POST " + post.getIdPost() + " : " + listaHits.size());			
			else System.out.println("NO HAY POST SIMILARES AL POST " + post.getIdPost());
			
			id = ClasificaPost.asocia(post);   //para cada post descargado se determina el id de su post similar
			Indizador.addDoc(post);   //se incluye en el indice el post original 
//			Indizador.cerrarIndice();   
			if (id == null){							//se meten los datos del post en un objeto noticianueva
				n.setIdPost(post.getIdPost());
				n.setNumero("1");
//				n.setFechaHora(String.valueOf(java.sql.Date));
				SimpleDateFormat fechaHoraSistema = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				n.setFechaHora("'" + fechaHoraSistema.format(date) + "'");
				DBManager.cargarNoticiaNueva(n);    // Se modifica la tabla NOTICIA
			}
			else{										//se meten los datos del post en un objeto post postsimilar
				ps.setIdPost(post.getIdPost());       
				ps.setIdSimilar(id);
				ps.setNumero("1");
				DBManager.cargarPostSimilar(ps);    // Se modifica la tabla AGRUPA
			}
		
			System.out.println();
			System.out.println();
			
		}
		
		DBManager.closeConnection();
	}
	
	
	
	
	
	public static void deleteVeryOldNews() throws StaleReaderException, CorruptIndexException, LockObtainFailedException, IOException, ParseException{
		Indizador.removeDoc();		
	}

	
	public static void main(String args[]) throws CorruptIndexException, ParseException, IOException, SQLException{
		trackRecentNews();
		System.out.println("EL TRACKER HA TERMINADO SU TRABAJO");
		deleteVeryOldNews();
		System.out.println("Proceso finalizado");
	}
	
	
}


	