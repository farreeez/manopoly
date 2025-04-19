package big.manopoly.utils;

// this is a class used for serialsing String, int pairs to display a set of possbile rent prices for each property.
public class RentDisplay {
    private String rentPrompt;
    private int rentPrice;

    public RentDisplay(String rentPrompt, int rentPrice) {
        this.rentPrice = rentPrice;
        this.rentPrompt = rentPrompt;
    }

    public String getRentPrompt() {
        return rentPrompt;
    }

    public int getRentPrice() {
        return rentPrice;
    }

    public void setRentPrompt(String prompt) {
        rentPrompt = prompt;
    }

    public void setRentprice(int price) {
        rentPrice = price;
    }
}
