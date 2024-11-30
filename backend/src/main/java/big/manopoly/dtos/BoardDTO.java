package big.manopoly.dtos;

import java.util.List;
import java.util.Set;

public class BoardDTO {
    private Long id;
    private Set<Long> playerIds;
    private List<String> squareIds;

    public BoardDTO() {
    }

    public BoardDTO(Long id, Set<Long> playerIds, List<String> squareIds) {
        this.id = id;
        this.playerIds = playerIds;
        this.squareIds = squareIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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