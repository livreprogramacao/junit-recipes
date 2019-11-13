package junit.cookbook.common;

public class Song {
    private String name;
    private String artistName;
    private int duration;
    private double durationInSeconds;

    public Song(String name, String artistName, int duration) {
        this.name = name;
        this.artistName = artistName;
        this.duration = duration;
        this.durationInSeconds = (double) duration / 60.0d;
    }

    public double getDurationInMinutes() {
        return durationInSeconds;
    }
}
