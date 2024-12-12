package big.manopoly.dtos;

import java.util.List;

import big.manopoly.models.Colour;

public class BoardDTO {
    private Long id;
    private List<Long> playerIds;
    private List<String> squareIds;
    private List<Colour> possibleColours;
    private List<Colour> takenColours;
    private boolean diceRolled;
    private PlayerDTO currentPlayerTurn;

    public BoardDTO() {
    }

    public BoardDTO(Long id, List<Long> playerIds, List<String> squareIds, List<Colour> takenColours, List<Colour> possibleColours, PlayerDTO currentPlayerTurn, boolean diceRolled) {
        this.id = id;
        this.playerIds = playerIds;
        this.squareIds = squareIds;
        this.takenColours = takenColours;
        this.possibleColours = possibleColours;
        this.currentPlayerTurn = currentPlayerTurn;
        this.diceRolled = diceRolled;
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

    public void setId(Long id) {
        this.id = id;
    }

    public List<Colour> getPossibleColours() {
        return possibleColours;
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

    public void setSquareIds(List<String> squareIds) {
        this.squareIds = squareIds;
    }

    public void setDiceRolled(boolean diceRolled) {
        this.diceRolled = diceRolled;
    }

    public void setCurrentPlayerTurn(PlayerDTO currentPlayerTurn) {
        this.currentPlayerTurn = currentPlayerTurn;
    }
}