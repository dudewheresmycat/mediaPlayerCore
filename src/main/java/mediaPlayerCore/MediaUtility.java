package mediaPlayerCore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class MediaUtility {
	
	//REQ #10
	private ArrayList<MediaFile> mediafiles = new ArrayList<>();
	
	
	
	public File[] pullMediaFromFolder(String filepath){
		File file = new File(filepath);
		 File[] a = file.listFiles();
		 for(int i = 0; i < a.length; i++){
			 System.out.println("added file:"+i);
		 }
		 
		return a;
	}
	
	public void pullTags(File[] filepaths) throws UnsupportedTagException, InvalidDataException, IOException{
		for(int i = 0 ; i < filepaths.length;i++){
			String filename = filepaths[i].getPath();
			try{
			if(!(filename.endsWith(".mp3")||filename.endsWith(".m4a"))){
				throw new InvalidFileTypeException(filename);
			}
				// REQ #3 & REQ #4  
				// possible to get half credit for implementing a library instead of a custom interface?
				Mp3File mp3file = new Mp3File(filename);
				System.out.println("added: "+ i + ": "+filename);
				String name="empty";
				double length=0; 
				String genre="empty"; 
				String artist="empty"; 
				String album="empty";
				
				if(mp3file.hasId3v2Tag()){
					try{
					genre = mp3file.getId3v2Tag().getGenreDescription();
					album = mp3file.getId3v2Tag().getAlbum(); 
					name = mp3file.getId3v2Tag().getTitle();
					artist = mp3file.getId3v2Tag().getArtist();
					length = (double)mp3file.getLengthInSeconds();
					}catch(Exception e){
						System.out.println("An Array exception happened"
								+ " invalid files in the folder or empty tags...skipping: "+filename);
						name=filename.substring((filename.length()/2), filename.length());
					}
				//REQ #10
				}
				if(filename.endsWith(".mp3")){
					Mp3media media = new Mp3media(name,length,genre,artist,album,filename);
					media.setExtension(".mp3");
					mediafiles.add(media);
				}else if(filename.endsWith(".m4a")){
					M4Amedia media = new M4Amedia(name,length,genre,artist,filename);
					media.setExtension(".m4a");
					mediafiles.add(media);
				}
			}catch(InvalidFileTypeException e){
				// REQ #11
				System.out.print(e.getExtension(filename));
				System.out.print(e.getLocalizedMessage());
				
			}
			
		}
		
	}

	public ArrayList<MediaFile> getMediafiles() {
		return mediafiles;
	}

	public void setMediafiles(ArrayList<MediaFile> mediafiles) {
		this.mediafiles = mediafiles;
	}

}
