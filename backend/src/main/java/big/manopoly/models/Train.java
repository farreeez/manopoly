package big.manopoly.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.utils.PropertyType;
import big.manopoly.utils.TrainName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TRAIN")
public class Train extends Property {

    public Train() {
        super();
    }

    @JsonCreator
    public Train(@JsonProperty("board") Board board, @JsonProperty("position") int position,
            @JsonProperty("name") TrainName name) {
        super(board, position, PropertyType.TRAIN, name.toString());
    }

    @Override
    public int getRent() throws Exception {
        if (owner == null) {
            throw new Exception("this card has no owner (at getRent function in big.manopoly.models.Train.java)");
        }

        // get the number of trains owned by the user.
        int trainCount = this.owner.getSet(type).size() - 1;

        if (trainCount < 0) {
            throw new Exception("owner doesnt have trains (at getRent function in big.manopoly.models.Train.java)");
        }

        return (int) (25 * Math.pow(2, trainCount));
    }

    @Override
    public int getCost() {
        TrainName name = TrainName.valueOf(getName());

        return name.propertyPrice;
    }

    @Override
    public int getMortgageCost() {
        TrainName name = TrainName.valueOf(getName());

        return name.getMortgageCost();
    }

    @Override
    public int getMortgagePayout() {
        TrainName name = TrainName.valueOf(getName());

        return name.getMortgagePayout();
    }

}
