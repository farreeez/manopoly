package big.manopoly.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.utilities.PropertyType;
import big.manopoly.utilities.UtilityName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Utility extends Property {
    @Id
    private UtilityName name;

    @JsonCreator
    public Utility(@JsonProperty("position") int position, @JsonProperty("name") UtilityName name) {
        super(position, PropertyType.UTILITY);

        this.name = name;
    }

    @Override
    public int getRent() throws Exception {
        // TODO Auto-generated method stub
        if (owner == null) {
            throw new Exception("this card has no owner (at getRent function in big.manopoly.models.Utility.java)");
        }

        // Implement this once the board implementation is finished
        int roll = 4;

        int utilityCount = this.owner.getSet(type).size();

        return name.utilityMultiplier.get(utilityCount - 1) * roll;
    }

    public UtilityName getName() {
        return name;
    }

}
