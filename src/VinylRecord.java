public class VinylRecord {
    private Album album; 
    private int available;
    private int total;

    public VinylRecord(Album album, int available, int total) {
        this.album = album;
        this.available = available;
        this.total = total;
    }

    // Getters
    public Album getAlbum() {
        return album;
    }

    public String getAlbumName() {
        return album != null ? album.getName() : null;
    }

    public String getArtist() {
        return album != null ? album.getArtist() : null;
    }

    public int getAvailable() {
        return available;
    }

    public int getTotal() {
        return total;
    }

    // Setters
    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setAlbumName(String albumName) {
        if (album != null) {
            album.setName(albumName);
        } else {
            throw new IllegalArgumentException("Album cannot be null");
        }
    }

    public void setArtist(String artist) {
        if (album != null) {
            album.setArtist(artist);
        } else {
            throw new IllegalArgumentException("Album cannot be null");
        }
    }
}