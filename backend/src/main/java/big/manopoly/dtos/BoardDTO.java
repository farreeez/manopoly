package big.manopoly.dtos;

import big.manopoly.models.Colour;
import java.util.List;

public class BoardDTO {
    private Long id;
    private List<Long> playerIds;
    private List<String> squareIds;
    private List<Colour> possibleColours;
    private List<Colour> takenColours;
    private boolean diceRolled;
    private PlayerDTO currentPlayerTurn;
    private int[] diceRolls;
    private boolean rollDiceAction;
    private int lastBoughtPosition;

    public BoardDTO() {
    }

    public BoardDTO(Long id, List<Long> playerIds, List<String> squareIds, List<Colour> takenColours,
            List<Colour> possibleColours, PlayerDTO currentPlayerTurn, boolean diceRolled, int[] diceRolls,
            boolean rollDiceAction, int lastBoughtPosition) {
        this.id = id;
        this.playerIds = playerIds;
        this.squareIds = squareIds;
        this.takenColours = takenColours;
        this.possibleColours = possibleColours;
        this.currentPlayerTurn = currentPlayerTurn;
        this.diceRolled = diceRolled;
        this.diceRolls = diceRolls;
        this.rollDiceAction = rollDiceAction;
        this.lastBoughtPosition = lastBoughtPosition;
    }

    public boolean isRollDiceAction() {
        return rollDiceAction;
    }

    public boolean isDiceRolled() {
        return diceRolled;
    }

    public PlayerDTO getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public Long getId() {
        return id;
    }

    public int[] getDiceRolls() {
        return diceRolls;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Colour> getPossibleColours() {
        return possibleColours;
    }

    public int getLastBoughtPosition() {
        return lastBoughtPosition;
    }

    public void setLastBoughtPosition(int lastBoughtPosition) {
        this.lastBoughtPosition = lastBoughtPosition;
    }

    public void setPossibleColours(List<Colour> possibleColours) {
        this.possibleColours = possibleColours;
    }

    public List<Colour> getTakenColours() {
        return takenColours;
    }

    public void setTakenColours(List<Colour> takenColours) {
        this.takenColours = takenColours;
    }

    public List<Long> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(List<Long> playerIds) {
        this.playerIds = playerIds;
    }

    public List<String> getSquareIds() {
        return squareIds;
    }

    public void setRollDiceAction(boolean rollDiceAction) {
        this.rollDiceAction = rollDiceAction;
    }

    public void setSquareIds(List<String> squareIds) {
        this.squareIds = squareIds;
    }

    public void setDiceRolled(boolean diceRolled) {
        this.diceRolled = diceRolled;
    }

    public void setCurrentPlayerTurn(PlayerDTO currentPlayerTurn) {
        this.currentPlayerTurn = currentPlayerTurn;
    }

    public void setDiceRolls(int[] diceRolls) {
        this.diceRolls = diceRolls;
    }
}