package es.uc3m.labda.memetracker.tracker.newstracker;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

//import es.uc3m.labda.memetracker.tracker.Post;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * This code was originally written for
 * Erik's Lucene intro java.net article
 */
public class Indexer {

//  public static void main(String[] args) throws Exception {
    //if (args.length != 2) {
    //  throw new Exception("Usage: java " + Indexer.class.getName()
    //    + " <index dir> <data dir>");
    //}
    
	//String ruta = "C://Documents and Settings/Mamá y Papá/Escritorio/PFC/lucene_tutorial/docs/";
	//String  indice = "C://Documents and Settings/Mamá y Papá/Escritorio/PFC/lucene_tutorial/docs/";  
	
	//File indexDir = new File(indice);
    //File dataDir = new File(ruta);
	
	//File indexDir = new File(args[0]);
//	String Id_post = String.valueOf(Post.getIdPost());
    //File dataDir = new File(args[1]);

    //long start = new Date().getTime();
    //int numIndexed = index(indexDir, Id_post);
    //long end = new Date().getTime();

//    System.out.println("Indexing " + numIndexed + " files took "
//      + (end - start) + " milliseconds");
//  }

	
	// 1. creando el indice
//    Directory index = new RAMDirectory();
//    IndexWriter w = new IndexWriter(index, new StandardAnalyzer(), true);
    //addDoc(w, Id_post);
//  }
	

//	 private static void addDoc(IndexWriter w, Integer id) throws IOException {
//		    Document doc = new Document();
//		    doc.add(new Field("Id del Post", id, Field.Store.YES, Field.Index.TOKENIZED));
//		    //doc.add(new Field("contents", new InputStreamReader(new FileInputStream(f),"UTF-8")));
//		    doc.add(new Field("Texto", Post.getTexto(),Field.Store.YES,Field.Index.TOKENIZED));
//		    w.addDocument(doc);
//		  }
//		}
	
	
	public static void addDoc(String id_post) throws CorruptIndexException, IOException, ParseException {
		
		Directory dir;
//		String indexDir = System.getProperty("java.io.tmpdir", "tmp") + System.getProperty("file.separator") + "index-dir";
		String indexDir = "C:\\indexdir";
		dir = FSDirectory.getDirectory(indexDir); 
		
		try{
//		Directory index = new RAMDirectory();
//		FSDirectory indexDir = null;
//		File index = new File("C:\\Documents and Settings\\Mamá y Papá\\Escritorio\\PFC\\indexdir");
	    IndexWriter w = new IndexWriter(indexDir, new StandardAnalyzer(), true);
		
		
		Document doc = new Document();
	    doc.add(new Field("Id del Post", id_post, Field.Store.YES, Field.Index.TOKENIZED));
	    //doc.add(new Field("contents", new InputStreamReader(new FileInputStream(f),"UTF-8")));
	    doc.add(new Field("Titulo", Post.getTitulo(),Field.Store.YES,Field.Index.TOKENIZED));
	    doc.add(new Field("Texto", Post.getTexto(),Field.Store.YES,Field.Index.TOKENIZED));
	    doc.add(new Field("Texto", Post.getFecha(),Field.Store.YES,Field.Index.TOKENIZED));
	    w.addDocument(doc);
	    
	    
	    // 2. query
	    //String querystr = args.length > 0 ? args[0] : "managing USA, experience";
	    String querystr = "Texto";
	    Query q = new QueryParser("Id del Post", new StandardAnalyzer()).parse(querystr);

	    // 3. search
	    IndexSearcher s = new IndexSearcher(indexDir);
	    Hits hits = s.search(q);

	    // 4. display results
	    System.out.println("Found " + hits.length() + " hits.");
	    for(int i=0;i<hits.length();++i) {
	      System.out.println((i + 1) + ". " + hits.doc(i).get("Texto"));
	    }
	    s.close();
		
//	    System.out.println(id_post);
//	    System.out.println(Post.getTexto());
	    
	    System.out.println(indexDir);
		}
		catch(Exception e) {
			e.printStackTrace();
			}
	    	    
	}
}

//	public static int index(File indexDir, Integer id)
//    throws IOException {

//    if (id == 0) {
//      throw new IOException(id
//        + " el post con ese ID no existe");
//    }

    // Creamos una instancia de escritura de índices
    // 		indexDir es el directorio del índice
    // 		El analizador de texto que usaremos será StandardAnalyzer
    // 		true indica que el índice se creará, si existe se reemplaza
//    IndexWriter writer = new IndexWriter(indexDir,new StandardAnalyzer(), true);
    // Usa el formato de índice con un solo fichero (true) o no (false)
//    writer.setUseCompoundFile(true);

    // Indexa los ficheros en el directorio de datos dataDir
//    indexDirectory(writer, dataDir);

    // Cuenta, optimiza y cierra el indice
//    int numIndexed = writer.docCount();
//    writer.optimize();
//    writer.close();

//    return numIndexed;
//  }

//  private static void indexDirectory(IndexWriter writer, File dir)
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
   * Método para indexar un fichero
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
    // La información se analiza (tokeniza, filtran palabras de parada, etc... ) usando el analizador del IndexWriter ---> Standard Analyzer

    //    doc.add(new Field("contents", new FileReader(f)));

    // Cuando hay problemas con la codificación
//     doc.add(new Field("contents", new InputStreamReader(new FileInputStream(f),"UTF-8")));

    // El campo filename incluye el nombre del fichero
    // Se define que se quiere almacenar pero que no se quiere analizar
//    doc.add(new Field("filename", f.getCanonicalPath(),Field.Store.YES,Field.Index.UN_TOKENIZED));
    // Añadimos el documento al IndexWriter
//    writer.addDocument(doc);
//  }
