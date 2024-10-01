package big.manopoly.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import big.manopoly.utilities.Colour;
import big.manopoly.utilities.PropertyName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@DiscriminatorValue("CITY")
public class City extends Property {

    // number of houses built on the property (5 if property has hotel).
    private int houses;

    @Enumerated(EnumType.STRING)
    private Colour colour;

    public City() {
        super();
    }

    @JsonCreator
    public City(int position, PropertyName name, Colour colour) {
        super(position, name);
        this.colour = colour;
        houses = 0;
    }

    @Override
    public int getHouseCost() {
        return colour.houseCost;
    }

    @Override
    public int getRent() throws Exception {
        if (this.owner == null) {
            throw new Exception("cannot get rent because property is owned by the bank");
        }

        List<Integer> rentPrices = name.rentPrices;

        if (houses > 0) {
            return rentPrices.get(houses);
        }
        
        if (this.owner.doesOwnSet(colour)) {
            return rentPrices.get(0) * 2;
        } else {
            return rentPrices.get(0);
        }
    }

    // getters
    public int getHouses() {
        return houses;
    }

    public Colour getColour() {
        return colour;
    }

    public boolean addHouse() {
        //TODO money stuff
        if(houses == 5 || owner == null) {
            return false;
        }

        if(!owner.doesOwnSet(colour)) {
            return false;
        }

        houses++;

        List<Property> citySet = owner.getSet(colour);

        for (Property property : citySet) {
            City city = (City) property;
            if(houses > (city.houses + 1)) {
                houses--;
                return false;
            }
        }

        return true;
    }

}
