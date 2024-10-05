package big.manopoly.utilities;

import java.util.*;

public enum UtilityName {
    // first input represents multipliers
    ELECTRIC(150),
    WATER(150);

    public final int propertyPrice;
    public final List<Integer> utilityMultiplier;

    // constructor for utilities
    UtilityName(int propertyPrice) {
        this.propertyPrice = propertyPrice;
        this.utilityMultiplier = Arrays.asList(4, 10);
    }

    public int getMortgagePayout() {
        return propertyPrice / 2;
    }

    public int getMortgageCost() {
        return (int) (getMortgagePayout() + getMortgagePayout() * 0.1);
    }
}
