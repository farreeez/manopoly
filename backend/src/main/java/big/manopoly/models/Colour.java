package big.manopoly.models;

public class Colour {
    private int red;
    private int green;
    private int blue;
    private int identifier;

    // Constructor
    public Colour(int red, int green, int blue, int identifier) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.identifier = identifier;
    }

    // Getters and setters
    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }
}
