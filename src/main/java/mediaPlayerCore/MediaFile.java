package mediaPlayerCore;

public class MediaFile{
	//REQ #5
	//id, name, length, genre, artist, filename
	private String name;
	private double length;
	private String genre;
	private String artist;
	private String filename;
	
	public MediaFile(String name, double length, String genre, String artist, String filename){
		this.name = name;
		this.length = length;
		this.genre = genre;
		this.artist = artist;
		this.filename = filename;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}