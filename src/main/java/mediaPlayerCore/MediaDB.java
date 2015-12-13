package mediaPlayerCore;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class MediaDB {
	public static void main(String[] args) throws SQLException, UnsupportedTagException, InvalidDataException, IOException{
		
		createDatabase();
	}
	public static void createDatabase() throws SQLException{
		Connection conn = DriverManager.getConnection(Environment.DB_URL);
		
		System.out.println("Connected to database!");
		//REQ #7
		Statement stmt = conn.createStatement();
		String dropTable = "drop table Media";
		try{
	
		stmt.execute(dropTable);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		System.out.println("Media table dropped.");
		String createTable = "create table Media("
				+ "id int not null primary key, " 
				+ "name varchar(255), "
				+ "length double, " 
				+ "genre varchar(90), "
				+ "artist varchar(90), "
				+ "album varchar(90), "
				+ "filename varchar(255) "
				+ ")";
		
		stmt.execute(createTable);
		System.out.println("Media table created.");
	}
	public static ArrayList<MediaFile> createDatabase(String filepath) throws SQLException, UnsupportedTagException, InvalidDataException, IOException{
		Connection conn = DriverManager.getConnection(Environment.DB_URL);
	
		System.out.println("Connected to database!");
		//REQ #7
		Statement stmt = conn.createStatement();
		String dropTable = "drop table Media";
		try{
		stmt.execute(dropTable);
		System.out.println("Media table dropped.");
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		String createTable = "create table Media("
				+ "id int not null primary key, " 
				+ "name varchar(1055), "
				+ "length double, " 
				+ "genre varchar(255), "
				+ "artist varchar(255), "
				+ "album varchar(255), "
				+ "filename varchar(555) "
				+ ")";
		
		stmt.execute(createTable);
		System.out.println("Media table created.");
		MediaUtility m = new MediaUtility();
		
		m.pullTags(m.pullMediaFromFolder(filepath));
		ArrayList<MediaFile> mediafiles = m.getMediafiles();
		for(int i = 0 ; i < mediafiles.size() ; i ++){
			MediaFile media = (MediaFile) mediafiles.get(i);
			//try{
			addMediaStmt(conn, i, MediaDB.removedPunctuation(media.getName()), 
					media.getLength(), 
					MediaDB.removedPunctuation(media.getGenre()), 
					MediaDB.removedPunctuation(media.getArtist()),
					MediaDB.removedPunctuation(((Mp3media)media).getAlbum()),
					MediaDB.removedPunctuation(media.getFilename()));
			//}catch(Exception e){
				//System.out.println("A Database exception happened there may be"
					//	+ " invalid files in the folder...skipping: "+ media.getFilename());
			//}
			
		}
		return mediafiles;
	}
	public static String removePunctuationDB(String string){
		if(string.contains("'")){
			return string.replace("'", "");
		}else{
			return string;
		}
		
	}
	public static String removedPunctuation(String string){
		if(string == null){
			string = "empty";
		}
		char[] a = string.toCharArray();
		char[] b = new char[a.length];
		int n = 0;
		for(int i = 0 ; i < a.length; i++){
			if(Character.isLetter(a[i])){
				b[n] = a[i];
				n++;
			}
		}
		char[] c = new char[n];
		for(int i = 0 ; i < n; i++){
			c[i] = b[i];
		}
		return new String(c);
	}
	public static void addMediaStmt(Connection conn, int id, String name, double length, 
		String genre, String artist, String album, String filename) throws SQLException{
		Statement stmt = conn.createStatement();
		String insert = String.format("insert into Media (id, name, length, genre, artist, album, filename) "
				+ "values (%d, '%s', %f, '%s', '%s', '%s', '%s')", id, name, length, genre, artist, album, filename);
		stmt.executeUpdate(insert);
		System.out.println("Mp3: "+id+ " added!");
		
	}
	public static ArrayList<String> getMedia(){

		ArrayList<String> mediaInfo = new ArrayList<String>();
		
		try {
			Connection conn = DriverManager.getConnection(Environment.DB_URL);
			
			Statement stmt = conn.createStatement();
			
			ResultSet results = stmt.executeQuery("select id, name, length, genre, artist, album, filename from Media");
			
			
			while(results.next()){
				double duration = Math.abs(results.getDouble("length"));
				double minutes =  (duration/60);
				double seconds =(duration%60);
				 
				mediaInfo.add(String.format("%d \t| \t%s \t\t| \t%.0f:%.0f \t| \t%s \t| \t%s \t| \t%s\t", 
						 results.getInt("id"), results.getString("name"),minutes,seconds,results.getString("genre"),
						 results.getString("artist"),results.getString("album")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mediaInfo;
	}
}
