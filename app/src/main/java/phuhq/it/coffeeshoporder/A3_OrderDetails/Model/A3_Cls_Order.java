package phuhq.it.coffeeshoporder.A3_OrderDetails.Model;

import java.util.List;

public class A3_Cls_Order {
    private String tableID, table, status;
    private List<A3_Cls_Drinks> drinksList;
    private int total;

    public A3_Cls_Order(String tableID, String table, String status, List<A3_Cls_Drinks> drinksList, int total) {
        this.tableID = tableID;
        this.table = table;
        this.status = status;
        this.drinksList = drinksList;
        this.total = total;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
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
