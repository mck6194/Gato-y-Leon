package es.uc3m.labda.memetracker.tracker.newstracker;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;

public class ClasificaPost {
	static String index = "C:\\Documents and Settings\\Mamá y Papá\\Escritorio\\PFC\\indexdir";

	public static List<Hits> buscaSimilares(Post p) throws ParseException, CorruptIndexException, IOException{
		
//		String index = "C:\\Documents and Settings\\Mamá y Papá\\Escritorio\\PFC\\indexdir";
		
		// Se define un ArrayList de Hits
	   List<Hits> listaHits = new ArrayList<Hits>();
		
		
	    // ANALISIS SEGUN EL POST RECIBIDO. Se compara el texto del post recibido con los textos del resto de posts del índice
	    //String querystr = args.length > 0 ? args[0] : "managing USA, experience";
	    String querystr = p.getTexto();
	    Query q = new QueryParser("Texto", new StandardAnalyzer()).parse(querystr);   /// no se porque aquí se elimina el write.lock

	    
	File file = new File(index);    //ruta en la que se encuentra el indice
	if (file.exists()){                    //si existe el índice, se buscarán los hits correspondientes
	
	    // BUSQUEDA SOBRE EL INDICE
	    IndexSearcher s = new IndexSearcher(index);
	    Hits hits = s.search(q);

	    // SE MUESTRAN LOS RESULTADOS DE HITS ENCONTRADOS
//	    System.out.println("Found " + hits.length() + " hits.");

//	    System.out.println(hits.doc(0).getField("Titulo").stringValue());
	    
	    
	    System.out.println("LOS HIT COINCIDENTES SON EN NUMERO: " + hits.length());
		for(int i=0;i<hits.length();++i) {
//	      Document doc=hits.doc(i);
//	      System.out.println(doc.get("Titulo"));
	      //System.out.println(doc.getField("Titulo").stringValue());	
//	      System.out.println((i + 1) + ". " + hits.doc(i).get("Texto"));
//	      System.out.println(hits.score(i)+"  "+hits.id(i));
//	      System.out.println();
//	      System.out.println();
	      
			
//			System.out.println(hits.doc(i).getField("Fecha").stringValue());
			
	      if (hits.score(i)> 0.1){  //añado a la lista de hits los que tengan un cierto parecido al post recibido como argumento
	      listaHits.add(hits);     //mirar esto porque casi seguro no se están añadiendo los hits indicados a la lista
	      }
	    }
	    
	    for (int i=0; i<listaHits.size(); i++){
		  Document doc=hits.doc(i);
		  System.out.println("El post : " + doc.get("Id del post"));
	      System.out.println("   coincide en un :   " + listaHits.get(i).score(i) + "    con el post tratado.");
	    }
    
	    s.close();

	}
	else listaHits = null;   //si no se ha creado aún el indice, no hay post coincidentes

	return listaHits;
	}
	
	

	public static String asocia(Post p) throws CorruptIndexException, ParseException, IOException{
		String id = null;
		List<Hits> listaHits = new ArrayList<Hits>();
		int numHits = 0;
		float similitud = 0;
		
		listaHits = buscaSimilares(p);
		
	if (listaHits != null){
		Iterator it = listaHits.iterator();  //variable para saber el numero de HITS encontrados con el metodo buscaSimilares

		while (it.hasNext()){			
			it.next();
			numHits++;
		}
		

	    for(int i=0;i<listaHits.size();++i) {             // bucle para ligar (en la BD) el post similar al que más se le parezca
	      if (listaHits.get(i).score(i) > similitud)
	      {
	    	  similitud = listaHits.get(i).score(i);
	    	  id = listaHits.get(i).doc(i).getField("Id del post").stringValue();	    	  
	    	  
	      }
	    }

	}
	else id = null;
		return id;
	}
	
}
