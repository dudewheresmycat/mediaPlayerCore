package mediaPlayerCore;

import java.util.StringTokenizer;

public class InvalidFileTypeException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// REQ #12
	public InvalidFileTypeException(String filename){
		
		super(String.format("; is not a valid file type for the Media Organizer, skipping file %s\n", filename));
	}
	public String getExtension(String filename){
		StringTokenizer st = new StringTokenizer(filename, ".");// REQ#2
		if(st.countTokens()==2){
			st.nextToken();
			String fileExtension = st.nextToken();
			return fileExtension;
		}else{
			return "folder";
		}
		
		
	}
}
// comment