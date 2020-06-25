package phuhq.it.coffeeshoporder.A4_OrderOverView.Model;

import java.util.List;

import phuhq.it.coffeeshoporder.A8_Chef.Model.A8_Cls_Chef;

public class A4_Cls_Invoice {
    private String tableID, table, status, date;
    private List<A8_Cls_Chef> drinksList;
    private int total;

    public A4_Cls_Invoice() {
    }

    public A4_Cls_Invoice(String tableID, String table, String status, String date, List<A8_Cls_Chef> drinksList, int total) {
        this.tableID = tableID;
        this.table = table;
        this.status = status;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<A8_Cls_Chef> getDrinksList() {
        return drinksList;
    }

    public void setDrinksList(List<A8_Cls_Chef> drinksList) {
        this.drinksList = drinksList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
