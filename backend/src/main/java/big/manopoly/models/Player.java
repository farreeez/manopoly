package big.manopoly.models;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    // TODO relationship + collection element + polymorphism stuff
    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<Property> properties;
    
    private Boolean free;

    @JsonCreator
    public Player(@JsonProperty("position") Position position) {
        this.position = position;
    }

    public Player () {}

    public Long getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    // public Board getBoard() {
    //     return board;
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
    //     this.board = board;
    // }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

}
