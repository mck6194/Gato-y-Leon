package es.uc3m.labda.memetracker.tracker.newstracker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
//import java.sql.Date;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Hits;

import es.uc3m.labda.memetracker.tracker.Filtra;

public class DBManager {
	
	 private static final String DB = "politiktracker",

     TABLE_NAME = "post",

     HOST = "jdbc:mysql://163.117.55.26:3306/",

     ACCOUNT = "memetrackeradmin",

     PASSWORD = "memetracker",

     DRIVER = "org.gjt.mm.mysql.Driver";
	 
	 
// Definimos una ArrayList de Posts
static List<Post> list = new ArrayList<Post>();
static List<String> listaID = new ArrayList<String>();
static List<Hits> listaHits = new ArrayList<Hits>();		

//variable para realizar la conexión sobre la BD 
static Connection cn;
//variable statement para ejecutar las querys sobre la BD
static Statement stm;
	 



	/*
	 * Metodo que recibe una Fecha (con la hora) como parametro y descarga todos los post
	 * de una BD (en nuestro caso los post estan en la BD--> MEMETRACKER descargados por un crawler
	 */
	public static List<Post> descargarPostRecientes(Date fecha){
 
		 List<Post> listaPost = new ArrayList<Post>();
		 String cadenaLimpia;
		 	

//LO SIGUIENTE PODRIA SER NECESARIO  (de momento no)
		 //**** Se convierte la fecha recibida (un String) al formato sql.Date ****//
//		 java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
		
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 String fechaBien = sdf.format(fecha);
//		 System.out.println(fechaBien);

//		 java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());   //con esta conversion se pierden las horas, minutos y segundos
		 
		 System.out.println(fechaBien);
		 
//		 java.util.Date fechaConv = null;
//		try {
			//primero se pasa al formato util.Date
//			fechaConv = sdf.parse(fecha);
//		} catch (ParseException e1) {
			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		 System.out.println(fechaConv);
		 
//		 java.sql.Date fechaSQL = new java.sql.Date(fechaConv.getTime());
		 
		 
		 
		 
		 
		 
		 //CONSULTA SOBRE LA BD
         //**** Condición de descarga de posts de la BD ****

		 String query = "SELECT Id_Post,Titulo,Texto,FechaHora FROM " + TABLE_NAME + " WHERE FechaHora > '" + fechaBien + "'";
		 
         
         try{
         ResultSet table = stm.executeQuery(query);

         while(table.next()) {
         	 Post p = new Post();
         	 cadenaLimpia = Filtra.filtrarEtiquetasGData(table.getString("Texto"));
	         p.setIdPost(table.getString("Id_Post"));
	         p.setTitulo(table.getString("Titulo"));
	         p.setTexto(cadenaLimpia);
	         p.setFecha(table.getString("FechaHora"));
	        	   
	         String Id_post = String.valueOf(p.getIdPost());


				listaPost.add(p);    //Se anyade cada post descargado a la lista de post
       	   	   
	           	   //textOut.write(Post.getTexto() + "\r\n" + "\r\n" + "\r\n");
	        	   //textOut.write(table.getString(x)+ "\t");
	        	   //textOut.close();
	        	   //} catch (IOException e) {
	        	  // e.printStackTrace();
	        	  // }
	        	   System.out.println("");

         }
         
         }
         catch(Exception e) {
	         e.printStackTrace();
	         
	         }
      return listaPost;
      }
		 
		
	 /*
	  * Metodo que recibe un objeto de tipo NoticiaNueva y lo introduce en la BD en la tabla noticia 
	  */
	 public static boolean cargarNoticiaNueva(NoticiaNueva n){
		 boolean cargado;
		 String TABLE_NAME = "noticia";
		 
         // Insertando los datos de la noticia nueva en la tabla noticia
         String query = "INSERT INTO " + TABLE_NAME;
         query += " VALUES(" + n.getIdPost() + ", ";
         query += 1 + ", ";
         query += n.getIdPost() + ",";
         query += n.getFechaHora() + ");";
         try {
			stm.executeUpdate(query);
			cargado = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			cargado = false;
		}
		 return cargado;
	 }
	 
	   
	 // protect data with quotes
//	   private static String quote(String include) {
//	      return("\"" + include + "\"");
//	   }
	   

	 
	 /*
	  * Metodo que recibe un objeto PostSimilar e introduce sus datos en la BD
	  * en la tabla agrupa
	  */
	   public static boolean cargarPostSimilar(PostSimilar p){
		   boolean cargado;
		   String TABLE_NAME = "agrupa";
		   
		// Insertando los datos del post similar. En este caso se modifica la tabla AGRUPA y la tabla ¿¿REPRESENTA??
	         String query = "INSERT INTO " + TABLE_NAME;
	         query += " VALUES(" + p.getIdPost() + ", ";
	         query += p.getIdSimilar() + ");";
	         try {
				stm.executeUpdate(query);
				cargado = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				cargado = false;
			}
			return cargado;
	   }
		  	

	   
	   
	   /*
	    * Metodo que sirve para eliminar noticias de la BD (serán noticias antiguas en nuestro prograam)
	    */
	   public static boolean eliminarNoticia(String id){
		   boolean eliminada;
		   String TABLE_NAME = "noticia";
		   
			// Eliminando los datos de la noticia. En este caso se elimina de la tabla NOTICIA y la tabla ¿¿AGRUPA?? y ¿¿REPRESENTA??
	         String query = "DELETE FROM " + TABLE_NAME + " WHERE Id_Post = ";
	         query += id + ";";
	         try {
				stm.executeUpdate(query);
				eliminada = true;
	System.out.println("Noticia con Id_post = " + id + " eliminada");			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				eliminada = false;
			}
		   
		   return eliminada;
	   }
	   
	   
	   
	   
	   /*
	    * Este metodo obtiene el ID de la noticia asociada (o que representa) a un determinado post
	    */
	   public static String obtenerNoticiaAsociada(String IdPost){
		   String CodNoticia = null;
		   String IdNoticia = null;
		   String TABLE_NAME = "agrupa";
		   ResultSet resultadoQuerys; 
		   //Seleccionando los id's necesarios de la tabla AGRUPA de la BD
		   String query = "SELECT Cod_Noticia FROM " + TABLE_NAME + " WHERE Id_Post = " + IdPost + ";";
		   try {
			resultadoQuerys = stm.executeQuery(query);
			
			if (resultadoQuerys.next()){
				CodNoticia = resultadoQuerys.getString("Cod_Noticia");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//seleccionando el Id del post de la noticia de la tabla NOTICIA de la BD
		TABLE_NAME = "noticia";
		String query2 = "SELECT Id_Post FROM " + TABLE_NAME + " WHERE Cod_Noticia = " + CodNoticia + ";";
		try {
			resultadoQuerys = stm.executeQuery(query2);
			if (resultadoQuerys.next()){
				IdNoticia = resultadoQuerys.getString("Id_Post");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		   System.out.println("Se ha obtenido la noticia con Id = " + IdNoticia);
		return IdNoticia;
	   }
	   
	
	   
	   
	   
	public static Post obtenerPost(String id) throws SQLException{
		Post p = null;
		 String TABLE_NAME = "post";
		   ResultSet resultadoQuery; 
		   //Seleccionando los id's necesarios de la tabla AGRUPA de la BD
		   String query = "SELECT Id_Post,Titulo,Texto,FechaHora FROM " + TABLE_NAME + " WHERE Id_Post = " + id + ";";
		   resultadoQuery = stm.executeQuery(query);
		   p.setIdPost(resultadoQuery.getString("Id_Post"));
		   p.setTitulo(resultadoQuery.getString("Titulo"));
		   p.setTexto(resultadoQuery.getString("Texto"));
		   p.setFecha(resultadoQuery.getString("FechaHora"));
		return p;
	}

	
	
	
	
	
	/*
	 * Metodo que abre la conexion sobre la BD
	 */
	public static void openConnection() {
	   
	      try {
	         // autenticaacion
	         Properties props = new Properties();
	         props.setProperty("user", ACCOUNT);
	         props.setProperty("password", PASSWORD);

	               

	         // cargamos el driver y conectamos a la BD
	         Class.forName(DRIVER).newInstance();
	         Connection con = DriverManager.getConnection(HOST + DB, props);
	         Statement stmt = con.createStatement();
	         stm = stmt;
	         cn = con;
	         
	      }

	         catch(Exception e) {
	         e.printStackTrace();
	         
	         }
	      
	}
	
	
	/************************************************************************
	 * Metodo que cierra la conexion sobre la BD asegurandose de que no este cerrada ya
	 ************************************************************************/
	public static void closeConnection(){
		try {
		    if ( cn != null )
			if ( !cn.isClosed() )    // Si no está cerrada, la cierro
			    cn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
	      
