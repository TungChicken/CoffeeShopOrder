package phuhq.it.coffeeshoporder.A3_OrderDetails.Model;

import java.io.Serializable;

public class A3_Cls_Drinks implements Serializable {
    private String drinkName, Image ;
    private int nowQty, drinkID;
    private int price;

    public A3_Cls_Drinks() {
    }

    public A3_Cls_Drinks(String drinkName, String image, int nowQty, int drinkID, int price) {
        this.drinkName = drinkName;
        Image = image;
        this.nowQty = nowQty;
        this.drinkID = drinkID;
        this.price = price;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getNowQty() {
        return nowQty;
    }

    public void setNowQty(int nowQty) {
        this.nowQty = nowQty;
    }

    public int getDrinkID() {
        return drinkID;
    }

    public void setDrinkID(int drinkID) {
        this.drinkID = drinkID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
