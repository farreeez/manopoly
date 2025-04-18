package big.manopoly.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.utils.PropertyType;
import big.manopoly.utils.RentDisplay;
import big.manopoly.utils.UtilityName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("UTILITY")
public class Utility extends Property {

    public Utility() {
        super();
    }

    @JsonCreator
    public Utility(@JsonProperty("board") Board board, @JsonProperty("position") int position,
            @JsonProperty("name") UtilityName name) {
        super(board, position, PropertyType.UTILITY, name.toString());
    }

    @Override
    public int getRent() throws Exception {
        if (owner == null) {
            throw new Exception("this card has no owner (at getRent function in big.manopoly.models.Utility.java)");
        }

        // Implement this once the board implementation is finished
        int roll = 4;

        int utilityCount = this.owner.getSet(type).size();

        UtilityName name = UtilityName.valueOf(getName());

        return name.utilityMultiplier.get(utilityCount - 1) * roll;
    }

    @Override
    public int getPrice() {
        UtilityName name = UtilityName.valueOf(getName());

        return name.propertyPrice;
    }

    @Override
    public int getMortgageCost() {
        UtilityName name = UtilityName.valueOf(getName());

        return name.getMortgageCost();
    }

    @Override
    public int getMortgagePayout() {
        UtilityName name = UtilityName.valueOf(getName());

        return name.getMortgagePayout();
    }

    @Override
    public List<RentDisplay> getPossibleRents() {
        List<RentDisplay> rentList = new ArrayList<>();

        RentDisplay rentDisplay = new RentDisplay("If one utility is owned rent is 4 times the dice roll.", 0);
        rentList.add(rentDisplay);

        rentDisplay = new RentDisplay("If both utilities are owned rent is 10 times the dice roll.", 0);
        rentList.add(rentDisplay);

        return rentList;
    }
}
