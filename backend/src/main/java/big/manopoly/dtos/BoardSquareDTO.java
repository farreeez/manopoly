package big.manopoly.dtos;

public class BoardSquareDTO {
    protected String id;
    protected int position;
    protected String name;
    protected Long boardId;
    protected int price;
    protected boolean isProperty;

    // Default constructor
    public BoardSquareDTO() {
    }

    // Constructor to populate the DTO from a BoardSquare entity
    public BoardSquareDTO(String id, int position, String name, Long boardId, int price, boolean isProperty) {
        this.id = id;
        this.position = position;
        this.name = name;
        this.boardId = boardId;
        this.price = price;
        this.isProperty = isProperty; 
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

    public boolean isProperty() {
        return isProperty;
    }

    public void setIsProperty(boolean isProperty) {
        this.isProperty = isProperty;
    }
}
