package phuhq.it.coffeeshoporder.A3_OrderDetails.Model;

import java.io.Serializable;

public class A3_Cls_Drinks implements Serializable {
    private String DrinkName, Image, DrinkID;
    private int NowQty;
    private int Price;


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

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getDrinkID() {
        return DrinkID;
    }

    public void setDrinkID(String drinkID) {
        DrinkID = drinkID;
    }
}
