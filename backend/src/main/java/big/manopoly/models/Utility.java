package big.manopoly.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.utilities.PropertyType;
import big.manopoly.utilities.UtilityName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("UTILITY")
public class Utility extends Property {

    @JsonCreator
    public Utility(@JsonProperty("position") int position, @JsonProperty("name") UtilityName name) {
        super(position, PropertyType.UTILITY, name.toString());
    }

    @Override
    public int getRent() throws Exception {
        // TODO Auto-generated method stub
        if (owner == null) {
            System.out.println("this card has no owner (at getRent function in big.manopoly.models.Utility.java)");
            throw new Exception("this card has no owner (at getRent function in big.manopoly.models.Utility.java)");
        }

        // Implement this once the board implementation is finished
        int roll = 4;

        int utilityCount = this.owner.getSet(type).size();

        UtilityName name = UtilityName.valueOf(getName());

        return name.utilityMultiplier.get(utilityCount - 1) * roll;
    }
}
