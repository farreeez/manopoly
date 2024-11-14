package big.manopoly.utils;

public enum PropertyType {
    // city colours
    BROWN(2, 50),
    LIGHT_BLUE(3, 50),
    PINK(3, 100),
    ORANGE(3, 100),
    RED(3, 150),
    YELLOW(3, 150),
    GREEN(3, 200),
    DARK_BLUE(2, 200),

    UTILITY(2),

    TRAIN(4);

    public final int propertyCount;
    public final int houseCost;

    PropertyType(int propertyCount) {
        houseCost = 0;
        this.propertyCount = propertyCount;
    }

    PropertyType(int propertyCount, int houseCost) {
        this.propertyCount = propertyCount;
        this.houseCost = houseCost;
    }
}
