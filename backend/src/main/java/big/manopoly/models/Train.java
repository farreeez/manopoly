package big.manopoly.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import big.manopoly.utils.PropertyType;
import big.manopoly.utils.RentDisplay;
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
        int trainCount = this.owner.getList(type).size() - 1;

        if (trainCount < 0) {
            throw new Exception("owner doesnt have trains (at getRent function in big.manopoly.models.Train.java)");
        }

        return (int) (25 * Math.pow(2, trainCount));
    }

    @Override
    public List<RentDisplay> getPossibleRents() {
        List<RentDisplay> rentList = new ArrayList<>();

        for(int i = 0; i < 4; i++) {
           RentDisplay display = new RentDisplay("Rent if " + (i+1) + " train stations are owned.", (int) (25 * Math.pow(2, i)));
           rentList.add(display); 
        }

        return rentList;
    }

    @Override
    public int getPrice() {
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
