package big.manopoly.utilities;

public enum Colour {
    BROWN(2, 50),
    LIGHT_BLUE(3, 50),
    PINK(3, 100),
    ORANGE(3, 100),
    RED(3, 150),
    YELLOW(3, 150),
    GREEN(3, 200),
    DARK_BLUE(2, 200);

    public final int propertyCount;
    public final int houseCost;

    Colour(int propertyCount, int houseCost) {
        this.propertyCount = propertyCount;
        this.houseCost = houseCost;
    }
}
