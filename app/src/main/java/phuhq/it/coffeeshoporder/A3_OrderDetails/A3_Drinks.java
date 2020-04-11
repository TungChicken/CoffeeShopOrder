package phuhq.it.coffeeshoporder.A3_OrderDetails;

public class A3_Drinks {
    private String DrinkName, Image, DrinkID;
    private int NowQty;
    private double Price;


    public String getDrinkName() {
        return DrinkName;
    }

    public void setDrinkName(String drinkName) {
        DrinkName = drinkName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getNowQty() {
        return NowQty;
    }

    public void setNowQty(int nowQty) {
        NowQty = nowQty;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getDrinkID() {
        return DrinkID;
    }

    public void setDrinkID(String drinkID) {
        DrinkID = drinkID;
    }
}
