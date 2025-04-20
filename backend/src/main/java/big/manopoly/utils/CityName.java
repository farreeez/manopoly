package big.manopoly.utils;

import java.util.Arrays;
import java.util.List;

public enum CityName {
    Cairo(Arrays.asList(2, 10, 30, 90, 160, 250), 60), // Property 1 of Brown
    Alexandria(Arrays.asList(4, 20, 60, 180, 320, 450), 60), // Property 2 of Brown

    Athens(Arrays.asList(6, 30, 90, 270, 400, 550), 100), // Property 1 of Light Blue
    Mykonos(Arrays.asList(6, 30, 90, 270, 400, 550), 100), // Property 2 of Light Blue
    Olympia(Arrays.asList(8, 40, 100, 300, 450, 600), 120), // Property 3 of Light Blue

    Kyoto(Arrays.asList(10, 50, 150, 450, 625, 750), 140), // Property 1 of Pink
    Osaka(Arrays.asList(10, 50, 150, 450, 625, 750), 140), // Property 2 of Pink
    Tokyo(Arrays.asList(12, 60, 180, 500, 700, 900), 160), // Property 3 of Pink

    Texas(Arrays.asList(14, 70, 200, 550, 750, 950), 180), // Property 1 of Orange
    Washington(Arrays.asList(14, 70, 200, 550, 750, 950), 180), // Property 2 of Orange
    California(Arrays.asList(16, 80, 220, 600, 800, 1000), 200), // Property 3 of Orange

    Shenzhen(Arrays.asList(18, 90, 250, 700, 875, 1050), 220), // Property 1 of Red
    Beijing(Arrays.asList(18, 90, 250, 700, 875, 1050), 220), // Property 2 of Red
    Shanghai(Arrays.asList(20, 100, 300, 750, 925, 1100), 240), // Property 3 of Red

    Brisbane(Arrays.asList(22, 110, 330, 800, 975, 1150), 260), // Property 1 of Yellow
    Melbourne(Arrays.asList(22, 110, 330, 800, 975, 1150), 260), // Property 2 of Yellow
    Sydney(Arrays.asList(24, 120, 360, 850, 1025, 1200), 280), // Property 3 of Yellow

    Christchurch(Arrays.asList(26, 130, 390, 900, 1100, 1275), 300), // Property 1 of Green
    Wellington(Arrays.asList(26, 130, 390, 900, 1100, 1275), 300), // Property 2 of Green
    Auckland(Arrays.asList(28, 150, 450, 1000, 1200, 1400), 320), // Property 3 of Green

    Stockholm(Arrays.asList(35, 175, 500, 1100, 1300, 1500), 350), // Property 1 of Dark Blue
    Malmö(Arrays.asList(50, 200, 600, 1400, 1700, 2000), 400); // Property 2 of Dark Blue

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
