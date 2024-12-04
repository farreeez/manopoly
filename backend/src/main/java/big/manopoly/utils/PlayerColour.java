package big.manopoly.utils;

public enum PlayerColour {
    RED(255, 0, 0),
    BLUE(0, 0, 255),
    GREEN(0, 128, 0),
    YELLOW(255, 255, 0),
    ORANGE(255, 165, 0),
    PURPLE(128, 0, 128),
    PINK(255, 192, 203),
    CYAN(0, 255, 255),
    MAGENTA(255, 0, 255),
    BROWN(165, 42, 42),
    BLACK(0, 0, 0),
    WHITE(255, 255, 255);

    private final int red;
    private final int green;
    private final int blue;

    PlayerColour(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    @Override
    public String toString() {
        return name() + " (RGB: " + red + ", " + green + ", " + blue + ")";
    }
}
