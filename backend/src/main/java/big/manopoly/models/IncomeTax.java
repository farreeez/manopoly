package big.manopoly.models;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Income Tax")
public class IncomeTax extends BoardSquare {
    private boolean percentageBasedTax;

    public IncomeTax() {
        super();
    }

    @JsonCreator
    public IncomeTax(Board board, int position, String name, boolean isPercentageBased) {
        super(board, position, name);
        this.percentageBasedTax = isPercentageBased;
    }

    public int getTax(int playerMoney) {
        if (percentageBasedTax) {
            if(playerMoney <= 0) {
                return 0;
            }

            return (int) (0.1 * playerMoney);
        } else {
            return 75;
        }
    }
}
