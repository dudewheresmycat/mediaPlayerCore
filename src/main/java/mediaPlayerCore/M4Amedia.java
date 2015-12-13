package mediaPlayerCore;

public class M4Amedia extends MediaFile{
	//REQ #6
	private String extension;
	public M4Amedia(String name, double length, String genre,
			String artist, String filename) {
		super(name, length, genre, artist, filename);
		// TODO Auto-generated constructor stub
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
}
