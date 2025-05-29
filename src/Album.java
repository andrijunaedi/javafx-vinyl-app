import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Album {
    private final StringProperty name;
    private final StringProperty artist;

    public Album(String name, String artist) {
        this.name = new SimpleStringProperty(name);
        this.artist = new SimpleStringProperty(artist);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getArtist() {
        return artist.get();
    }

    public StringProperty artistProperty() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist.set(artist);
    }
}