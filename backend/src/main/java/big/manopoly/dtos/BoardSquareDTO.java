package big.manopoly.dtos;

public class BoardSquareDTO {
    private String id;
    private int position;
    private String name;
    private Long boardId;
    private int price;

    // Default constructor
    public BoardSquareDTO() {
    }

    // Constructor to populate the DTO from a BoardSquare entity
    public BoardSquareDTO(String id, int position, String name, Long boardId, int price) {
        this.id = id;
        this.position = position;
        this.name = name;
        this.boardId = boardId;
        this.price = price;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
