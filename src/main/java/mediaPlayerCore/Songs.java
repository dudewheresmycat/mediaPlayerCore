package mediaPlayerCore;

import java.io.Serializable;
import java.net.URI;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.util.Duration;

public class Songs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7557647281157394793L;
	private ReadOnlyObjectProperty<Media> media;
	private ReadOnlyStringProperty artist;
	private ReadOnlyStringProperty album;
	private ReadOnlyStringProperty genre;
	private ReadOnlyStringProperty title;
	private ReadOnlyStringProperty duration;
	private ReadOnlyStringProperty filename;

	public Songs(URI uri) {
        if (uri == null) {
            throw new NullPointerException("Can not create the song with a null URI");
        }
        this.media = new SimpleObjectProperty<>(new Media(uri.toString()));
        this.artist = new SimpleStringProperty("Unknown");
        this.album = new SimpleStringProperty("Unknown");
        this.title = new SimpleStringProperty("Unknow");
        this.duration = new SimpleStringProperty("--:--:--");
        this.media.get().getMetadata().addListener(new MapChangeListener<String, Object>() {

            @Override
            public void onChanged(Change<? extends String, ? extends Object> change) {
                if (change.getKey().equals("title")) {
                    if (change.wasAdded()) {
                        ((SimpleStringProperty) Songs.this.titleProperty()).set(change.getValueAdded().toString());
                    } else {
                        ((SimpleStringProperty) Songs.this.titleProperty()).set("Unknown");
                    }
                } else if (change.getKey().equals("genre")){
                	if (change.wasAdded()) {
                		((SimpleStringProperty) Songs.this.titleProperty()).set(change.getValueAdded().toString());
                	} else {
                		((SimpleStringProperty) Songs.this.titleProperty()).set("Unknown");
                	}
                } else if (change.getKey().equals("artist")) {
                    if (change.wasAdded()) {
                        ((SimpleStringProperty) Songs.this.artistProperty()).set(change.getValueAdded().toString());
                    } else {
                        ((SimpleStringProperty) Songs.this.artistProperty()).set("Unknown");
                    }
                } else if (change.getKey().equals("album")) {
                    if (change.wasAdded()) {
                        ((SimpleStringProperty) Songs.this.albumProperty()).set(change.getValueAdded().toString());
                    } else {
                        ((SimpleStringProperty) Songs.this.albumProperty()).set("Unknown");
                    }
                }
            }
        });
        this.media.get().durationProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable o) {
                if(media.get().durationProperty().get() == Duration.UNKNOWN) {
                    ((SimpleStringProperty) duration).set("--:--:--");
                } else {
                    double initialTime = media.get().durationProperty().get().toSeconds();

                    int numberOfHour = (int) initialTime / 3600;
                    int numberOfMinutes;
                    int numberOfSeconds;

                    double remainingTime = initialTime % 3600;
                    if (remainingTime == 0) {
                        numberOfMinutes = 0;
                        numberOfSeconds = 0;
                    } else {
                        numberOfMinutes = (int) remainingTime / 60;
                        numberOfSeconds = (int) remainingTime % 60;
                    }

                    ((SimpleStringProperty) duration).set(String.format("%1$02d:%2$02d:%3$02d", numberOfHour, numberOfMinutes, numberOfSeconds));
                }
            }
        });
    }

	public final String getArtist() {
		return this.artistProperty().get();
	}

	public ReadOnlyStringProperty artistProperty() {
		return this.artist;
	}

	public final String getAlbum() {
		return this.albumProperty().get();
	}

	public ReadOnlyStringProperty albumProperty() {
		return this.album;
	}
	public final String getGenre() {
		return this.genreProperty().get();
	}
	public ReadOnlyStringProperty genreProperty() {
		return this.genre;
	}

	public final String getTitle() {
		return this.titleProperty().get();
	}

	public ReadOnlyStringProperty titleProperty() {
		return this.title;
	}

	public final String getDuration() {
		return this.durationProperty().get();
	}

	public ReadOnlyStringProperty durationProperty() {
		return this.duration;
	}

	public final Media getMedia() {
		return this.mediaProperty().get();
	}

	public ReadOnlyObjectProperty<Media> mediaProperty() {
		return this.media;
	}

	public ReadOnlyStringProperty getFilename() {
		return filename;
	}

	public void setFilename(ReadOnlyStringProperty filename) {
		this.filename = filename;
	}
}