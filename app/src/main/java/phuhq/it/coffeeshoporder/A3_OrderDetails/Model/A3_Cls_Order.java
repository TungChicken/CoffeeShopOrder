package phuhq.it.coffeeshoporder.A3_OrderDetails.Model;

import java.util.List;

public class A3_Cls_Order {
    private String table, status;
    private List<A3_Cls_Drinks> drinksList;
    private int total;

    public A3_Cls_Order(String table, String status, List<A3_Cls_Drinks> clsDrinks, int total) {
        this.table = table;
        this.status = status;
        this.drinksList = clsDrinks;
        this.total = total;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<A3_Cls_Drinks> getDrinksList() {
        return drinksList;
    }

    public void setDrinksList(List<A3_Cls_Drinks> drinksList) {
        this.drinksList = drinksList;
    }
}
