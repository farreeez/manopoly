package big.manopoly.dtos;

public class TileActionDTO {
    protected boolean displayableAction;
    protected boolean propertyPurchaseAction;
    protected boolean cardAction;
    protected int[] diceRolls;

    public TileActionDTO(int[] diceRolls) {
        displayableAction = false;
        propertyPurchaseAction = false;
        cardAction = false;
        this.diceRolls = diceRolls;
    }

    public boolean isDisplayableAction() {
        return displayableAction;
    }

    public boolean isPropertyPurcahseAction() {
        return propertyPurchaseAction;
    }

    public boolean isCardAction() {
        return cardAction;
    }

    public void setDisplayableAction(boolean displayableAction) {
        this.displayableAction = displayableAction;
    }

    public void setPropertyPurchaseAction(boolean propertyPurchaseAction) {
        this.propertyPurchaseAction = propertyPurchaseAction;
    }

    public void setCardAction(boolean cardAction) {
        this.cardAction = cardAction;
    }

}
