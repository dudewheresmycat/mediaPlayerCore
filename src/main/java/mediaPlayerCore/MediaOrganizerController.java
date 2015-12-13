package mediaPlayerCore;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MediaOrganizerController {

	public ObservableList<Songs> data;
	@FXML
	private ListView<String> listView;
	@FXML
	private TableColumn<Songs, String> artistColumn;

	@FXML
	private TableColumn<Songs, Duration> timeColumn;

	@FXML
	private TableColumn<Songs, String> nameColumn;

	@FXML
	private TableColumn<Songs, String> genreColumn;

	@FXML
	private TableColumn<Songs, String> albumColumn;

	@FXML
	private TableView<Songs> tableView;

	private MediaPlayer mediaPlayer;

	@FXML
	private Button pauseTrack;

	@FXML
	private Button previousTrack;

	@FXML
	private Button nextTrack;

	@FXML
	private Button playTrack;

	@FXML
	private Button refresh;
	@FXML
	private TextField folderToUse;
	private ArrayList<MediaFile> mediafiles;
	private int playIndex = 0;
	public void refreshButtonListener(ActionEvent event)
			throws UnsupportedTagException, InvalidDataException, SQLException,
			IOException {
		String filepath = folderToUse.getText();
		if(filepath.contains("\\")){
			filepath.replace("\\", "/");
		}
				
		ArrayList<MediaFile> media =  MediaDB.createDatabase(filepath);
		initialize(media);
	}

	public void initialize(ArrayList<MediaFile> media) {
		mediafiles = media;
		
		ObservableList<String> list = FXCollections.observableArrayList(MediaDB
				.getMedia());
		listView.setItems(list);
		playIndex=0;
	}


	public void playButtonListener(ActionEvent event) {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
		
		File source = new File(mediafiles.get(playIndex).getFilename());
		System.out.println(source.toURI().toString());
		Media media = new Media(source.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();

	}

	public void pauseButtonListener(ActionEvent event) {
		if (mediaPlayer != null) {
			if(mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED){
				mediaPlayer.play();
			}else{
				mediaPlayer.pause();
			}
		}
	}

	public void nextTrackListener(ActionEvent event) {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
		if(playIndex == mediafiles.size()-1){
			playIndex = 0;
		}else{
			playIndex++;
		}
		File source = new File(mediafiles.get(playIndex).getFilename());
		System.out.println(source.toURI().toString());
		Media media = new Media(source.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}

	public void previousTrackListener(ActionEvent event) {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
		if(playIndex == 0){
			playIndex = mediafiles.size()-1;
		}else{
			playIndex--;
		}
		
		File source = new File(mediafiles.get(playIndex).getFilename());
		System.out.println(source.toURI().toString());
		Media media = new Media(source.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}
	

}