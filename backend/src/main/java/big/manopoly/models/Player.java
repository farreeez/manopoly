package big.manopoly.models;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.utilities.Colour;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Position position;

    // TODO create relationship with board once board is created
    // private Board board;

    private Integer money;

    // TODO polymorphism stuff
    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Property> properties = new HashSet<>();

    private Boolean free;

    @JsonCreator
    public Player(@JsonProperty("position") Position position) {
        this.position = position;
    }

    public Player() {
    }

    public boolean doesOwnSet(Colour colour) {
        // TODO Test
        List<Property> citySetCount = properties.stream().filter(p -> {
            if (p.getClass() == City.class) {
                City city = (City) p;
                return city.getColour() == colour;
            }

            return false;
        }).toList();

        return citySetCount.size() == colour.propertyCount;
    }

    public Long getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    // public Board getBoard() {
    // return board;
    // }

    public Integer getMoney() {
        return money;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public Boolean isFree() {
        return free;
    }

    // public void setBoard(Board board) {
    // this.board = board;
    // }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void addProperty(Property property) {
        if (property.getOwner() != null) {
            property.getOwner().removeProperty(property);
        }

        property.setOwner(this);
        properties.add(property);
    }

    public void removeProperty(Property property) {
        property.setOwner(null);

        properties.remove(property);
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

}
