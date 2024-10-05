package big.manopoly.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.utilities.CityName;
import big.manopoly.utilities.PropertyType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CITY")
public class City extends Property {
    // number of houses built on the property (5 if property has hotel).
    private int houses;

    public City() {
        super();
    }

    @JsonCreator
    public City(@JsonProperty("position") int position, @JsonProperty("type") PropertyType colour,
            @JsonProperty("name") CityName name) {
        super(position, colour, name.toString());
        houses = 0;
    }

    public int getHouseCost() {
        return this.type.houseCost;
    }

    @Override
    public int getRent() throws Exception {
        if (this.owner == null) {
            throw new Exception("cannot get rent because property is owned by the bank");
        }

        CityName name = CityName.valueOf(getName());

        List<Integer> rentPrices = name.rentPrices;

        if (houses > 0) {
            return rentPrices.get(houses);
        }

        if (this.owner.doesOwnSet(this.type)) {
            return rentPrices.get(0) * 2;
        } else {
            return rentPrices.get(0);
        }
    }

    // getters
    public int getHouses() {
        return houses;
    }

    public boolean addHouse() {
        // TODO money stuff
        if (houses == 5 || owner == null) {
            return false;
        }

        if (!owner.doesOwnSet(this.type)) {
            return false;
        }

        houses++;

        List<Property> citySet = owner.getSet(this.type);

        for (Property property : citySet) {
            City city = (City) property;
            if (houses > (city.houses + 1)) {
                houses--;
                return false;
            }
        }

        return true;
    }

    @Override
    public int getCost() {
        CityName name = CityName.valueOf(getName());

        return name.propertyPrice;
    }

    @Override
    public int getMortgageCost() {
        CityName name = CityName.valueOf(getName());

        return name.getMortgageCost();
    }

    @Override
    public int getMortgagePayout() {
        CityName name = CityName.valueOf(getName());

        return name.getMortgagePayout();
    }

}
