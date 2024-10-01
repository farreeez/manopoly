package big.manopoly.utilities;

import java.util.Arrays;
import java.util.List;

public enum CityName {
    BROWN1(Arrays.asList(2, 10, 30, 90, 160, 250), 60), // Property 1 of Brown
    BROWN2(Arrays.asList(4, 20, 60, 180, 320, 450), 60), // Property 2 of Brown

    LIGHT_BLUE1(Arrays.asList(6, 30, 90, 270, 400, 550), 100), // Property 1 of Light Blue
    LIGHT_BLUE2(Arrays.asList(6, 30, 90, 270, 400, 550), 100), // Property 2 of Light Blue
    LIGHT_BLUE3(Arrays.asList(8, 40, 100, 300, 450, 600), 120), // Property 3 of Light Blue

    PINK1(Arrays.asList(10, 50, 150, 450, 625, 750), 140), // Property 1 of Pink
    PINK2(Arrays.asList(10, 50, 150, 450, 625, 750), 140), // Property 2 of Pink
    PINK3(Arrays.asList(12, 60, 180, 500, 700, 900), 160), // Property 3 of Pink

    ORANGE1(Arrays.asList(14, 70, 200, 550, 750, 950), 180), // Property 1 of Orange
    ORANGE2(Arrays.asList(14, 70, 200, 550, 750, 950), 180), // Property 2 of Orange
    ORANGE3(Arrays.asList(16, 80, 220, 600, 800, 1000), 200), // Property 3 of Orange

    RED1(Arrays.asList(18, 90, 250, 700, 875, 1050), 220), // Property 1 of Red
    RED2(Arrays.asList(18, 90, 250, 700, 875, 1050), 220), // Property 2 of Red
    RED3(Arrays.asList(20, 100, 300, 750, 925, 1100), 240), // Property 3 of Red

    YELLOW1(Arrays.asList(22, 110, 330, 800, 975, 1150), 260), // Property 1 of Yellow
    YELLOW2(Arrays.asList(22, 110, 330, 800, 975, 1150), 260), // Property 2 of Yellow
    YELLOW3(Arrays.asList(24, 120, 360, 850, 1025, 1200), 280), // Property 3 of Yellow

    GREEN1(Arrays.asList(26, 130, 390, 900, 1100, 1275), 300), // Property 1 of Green
    GREEN2(Arrays.asList(26, 130, 390, 900, 1100, 1275), 300), // Property 2 of Green
    GREEN3(Arrays.asList(28, 150, 450, 1000, 1200, 1400), 320), // Property 3 of Green

    DARK_BLUE1(Arrays.asList(35, 175, 500, 1100, 1300, 1500), 350), // Property 1 of Dark Blue
    DARK_BLUE2(Arrays.asList(50, 200, 600, 1400, 1700, 2000), 400); // Property 2 of Dark Blue

    public final List<Integer> rentPrices;
    public final int propertyPrice;

    CityName(List<Integer> rentPrices, int propertyPrice) {
        this.rentPrices = rentPrices;
        this.propertyPrice = propertyPrice;
    }

    public int getMortgagePayout() {
        return propertyPrice / 2;
    }

    public int getMortgageCost() {
        return (int) (getMortgagePayout() + getMortgagePayout() * 0.1);
    }
}
