package big.manopoly.dtos;

import java.util.List;
import java.util.Set;

import big.manopoly.models.Colour;

public class BoardDTO {
    private Long id;
    private Set<Long> playerIds;
    private List<String> squareIds;
    private List<Colour> possibleColours;
    private List<Colour> takenColours;

    public BoardDTO() {
    }

    public BoardDTO(Long id, Set<Long> playerIds, List<String> squareIds, List<Colour> takenColours, List<Colour> possibleColours) {
        this.id = id;
        this.playerIds = playerIds;
        this.squareIds = squareIds;
        this.takenColours = takenColours;
        this.possibleColours = possibleColours;
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

    public Set<Long> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(Set<Long> playerIds) {
        this.playerIds = playerIds;
    }

    public List<String> getSquareIds() {
        return squareIds;
    }

    public void setSquareIds(List<String> squareIds) {
        this.squareIds = squareIds;
    }
}