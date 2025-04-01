package big.manopoly.dtos;

public class CardPurchaseActionDTO extends TileActionDTO {

    private int price;

    public CardPurchaseActionDTO(int[] diceRolls, int price) {
        super(diceRolls);
        this.price = price;
        this.displayableAction = true;
        this.propertyPurchaseAction = true;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
