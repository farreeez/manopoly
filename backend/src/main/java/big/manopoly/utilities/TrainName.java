package big.manopoly.utilities;

public enum TrainName {
    TRAIN1(),
    TRAIN2(),
    TRAIN3(),
    TRAIN4();

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
