package phuhq.it.coffeeshoporder.A8_Chef.Model;

import java.io.Serializable;

public class A8_Cls_Chef_Details implements Serializable {
    private String drinkName;
    private int nowQty, price, drinkID;

    public int getDrinkID() {
        return drinkID;
    }

    public void setDrinkID(int drinkID) {
        this.drinkID = drinkID;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public int getNowQty() {
        return nowQty;
    }

    public void setNowQty(int nowQty) {
        this.nowQty = nowQty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
