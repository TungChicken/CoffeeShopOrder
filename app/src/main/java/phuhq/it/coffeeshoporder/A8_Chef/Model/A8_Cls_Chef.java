package phuhq.it.coffeeshoporder.A8_Chef.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class A8_Cls_Chef implements Serializable {
    private List<A8_Cls_Chef_Details> drinksList;
    private String status, tableID, table;
    private int total;

    public List<A8_Cls_Chef_Details> getDrinksList() {
        return drinksList;
    }

    public void setDrinksList(List<A8_Cls_Chef_Details> drinksList) {
        this.drinksList = drinksList;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
