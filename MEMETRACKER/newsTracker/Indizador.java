package es.uc3m.labda.memetracker.tracker.newstracker;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.StaleReaderException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
//import org.apache.lucene.queryParser.QueryParser;
//import org.apache.lucene.search.Hits;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
//import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import java.io.File;
//import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.io.FileReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.Date;



/**
 * This code was originally written for
 * Erik's Lucene intro java.net article
 */
public class Indizador {

	static IndexWriter w;

	
	
	/* En la primera iteracion de nuestrop programa se llama a este metodo para crear el indice 
	 * (a lo mejor no es necesario llamarlo siempre que se ejecute el tracker ya que el indice debe permanecer, no crearse uno nuevo cada dia) */
    public static void crearIndice() throws IOException{
		Directory dir;
//		FSDirectory index = null;
	    String index = "C:\\Documents and Settings\\Mamá y Papá\\Escritorio\\PFC\\indexdir";
		dir = FSDirectory.getDirectory(index);      //Variable para crear el indice en un directorio, si quisiéramos crearlo en RAM sería:
//	    Directory index = new RAMDirectory();
		IndexWriter iw = new IndexWriter(index, new StandardAnalyzer(), true);
    	w = iw;
    }
    
    
    /* Este es un método que debería ser llamado cuando ya no se van a introducir 
     * más documentos en el índice durante un tiempo.
     * Optinmiza el índice para realizar búsquedas de manera más rápida y eficiente */
    public static void cerrarIndice() throws CorruptIndexException, IOException{
    	w.optimize();
    	w.close();
    }
	
	public static void addDoc(Post p) throws CorruptIndexException, IOException, ParseException {
	    String index = "C:\\Documents and Settings\\Mamá y Papá\\Escritorio\\PFC\\indexdir";
	    File file = new File(index);    //ruta en la que se encuentra el indice
	    
	    if(file.exists() == false){		
            IndexWriter w = new IndexWriter(file,new StandardAnalyzer(),true);  //si el índice no existe se indica como tercer parámetro del IndexWriter para empezar a introducir documentos
            add(w, p);
              }
            else
            {
           	IndexWriter w = new IndexWriter(file,new StandardAnalyzer(),false);	//cuando ya existe el índice, también se indica como tercer parámetro para sobreescribir elindice añadiendo más documentos
           	add(w, p);
            }
	    

	    System.out.println(index);
	    System.out.println("Se añadió el post " + p.getIdPost() + " al índice");	    
	    	    
	}  
  
	
	
	/*
	 * Metodo que anyade los documentos al indice que especifica el objeto IndexWriter
	 */
  private static void add(IndexWriter w, Post p) throws IOException {
	    Document doc = new Document();
	    doc.add(new Field("Id del post", p.getIdPost(), Field.Store.YES, Field.Index.TOKENIZED));     //ojo, luego para borrar docs del indice he leido que estos deben ser de tipo Field.keyword
	    doc.add(new Field("Titulo", p.getTitulo(),Field.Store.YES,Field.Index.TOKENIZED));
	    doc.add(new Field("Texto", p.getTexto(),Field.Store.YES,Field.Index.TOKENIZED));
	    doc.add(new Field("Fecha", p.getFecha(),Field.Store.YES,Field.Index.TOKENIZED));
	    w.addDocument(doc);
	    
	    w.close();      //Se cierra el IndexWriter despues de introducir en el indice cada documento!!!  (sin esto no funcionaba bien) 
	  
  }
  
  
  public static void removeDoc() throws StaleReaderException, CorruptIndexException, LockObtainFailedException, IOException, ParseException{
	  String index = "C:\\Documents and Settings\\Mamá y Papá\\Escritorio\\PFC\\indexdir";
	  File file = new File(index); 

//	  IndexReader fsReader = IndexReader.open(index);
  
	  Query q = new QueryParser("Texto", new StandardAnalyzer()).parse("El");  //con esta búsqueda saldran todos los post del indice
	  
	  // BUSQUEDA SOBRE EL INDICE
	    IndexSearcher s = new IndexSearcher(index);
	    Hits hits = s.search(q);

	    for(int i=0;i<hits.length();++i) {
    
	      String fechaIndice = hits.doc(i).getField("Fecha").stringValue();
	      String fechaIndice2 = fechaIndice.substring(0, 4) + fechaIndice.substring(5, 7) + fechaIndice.substring(8, 10);
	      Integer fechaPost = Integer.parseInt(fechaIndice2);
	      
	      Date fechaHoy = new Date();
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	      String formato = sdf.format(fechaHoy);
	      Integer fechaActual = Integer.parseInt(formato);
	      
	      Integer idDocActual = hits.id(i);
	      
	      if ( (fechaActual - fechaPost) > 30 ){  //Si el post lleva mas de 30 días en el indice...
	    	  IndexReader fsReader = IndexReader.open(index);
	    	  fsReader.deleteDocument(idDocActual);     //...se borra el documento correspondiente al post del indice
	    	  fsReader.close();
	      }
	    }
	    IndexWriter w = new IndexWriter(file,new StandardAnalyzer(),false);		// Se abre el indice para modificarlo (en este caso optimizarlo)
	    w.optimize();		// Despues de borrar documentos del indice hay que optimizarlo para que no queden huecos y las busquedas sean mas eficientes
	    w.close();		// Se cierra el indice
//	    w.optimize();
 
  }
	    
	  //try{

//		  IndexReader fsReader;

		  //if (file.exists()) {
		  //fsReader =IndexReader.open(index);
//		  fsReader.deleteDocuments(new Term("pk",message.getId()+""));
		  //fsReader.close();
		  //}
	  //}

		  //}
	      //catch(IOException e){
		  //e.printStackTrace();
	    
  
  
}  
  
  
//  private static void indexDirectory(IndexWriter writer, File dir)
//  private static void indexDirectory(IndexWriter writer)
//    throws IOException {

//    File[] files = dir.listFiles();

    // Recorre el directorio de manera recursiva
//    for (int i = 0; i < files.length; i++) {
//      File f = files[i];
//      if (f.isDirectory()) {
//        indexDirectory(writer, f);  // recurse
//      } else if (f.isFile()) {
//        indexFile(writer, f);
//      }
//    }
//  }


  /**
   * Metodo para indexar un fichero
   *
   *
   * @param writer
   * @param f
   * @throws IOException
   */
//  private static void indexFile(IndexWriter writer, File f)
//    throws IOException {

//    if (f.isHidden() || !f.exists() || !f.canRead()) {
//      return;
//    }

//    System.out.println("Indexing " + f.getCanonicalPath());

    // Creamos un documento
//    Document doc = new Document();

    // Añadimos campos al documento
    // El primer campo es de tipo contenido textual, se pasa directamente un objeto Reader para leer el contenido del Fichero
    // La informacion se analiza (tokeniza, filtran palabras de parada, etc... ) usando el analizador del IndexWriter ---> Standard Analyzer

    //    doc.add(new Field("contents", new FileReader(f)));

    // Cuando hay problemas con la codificacion
//     doc.add(new Field("contents", new InputStreamReader(new FileInputStream(f),UTF_8)));

    // El campo filename incluye el nombre del fichero
    // Se define que se quiere almacenar pero que no se quiere analizar
//    doc.add(new Field("filename", f.getCanonicalPath(),Field.Store.YES,Field.Index.UN_TOKENIZED));
    // Añadimos el documento al IndexWriter
//    writer.addDocument(doc);
//  }
//}
