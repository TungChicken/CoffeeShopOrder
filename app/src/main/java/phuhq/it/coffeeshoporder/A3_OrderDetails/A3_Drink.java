package phuhq.it.coffeeshoporder.A3_OrderDetails;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class A3_Drink implements Serializable {
    private String drinkName;
    private int Image;
    private int  qty;
    private double price;

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
