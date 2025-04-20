package big.manopoly.utils;

public enum TrainName {
    Berlin_Hauptbahnhof(),
    Tokyo_Station(),
    Grand_Central_Terminal(),
    Gare_du_Nord();

    public final int propertyPrice;

    TrainName() {
        this.propertyPrice = 200;
    }

    public int getMortgagePayout() {
        return propertyPrice / 2;
    }

    public int getMortgageCost() {
        return (int) (getMortgagePayout() + getMortgagePayout() * 0.1);
    }
}
