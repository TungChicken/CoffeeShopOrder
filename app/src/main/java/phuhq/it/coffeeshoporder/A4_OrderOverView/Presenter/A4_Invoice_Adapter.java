package phuhq.it.coffeeshoporder.A4_OrderOverView.Presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import phuhq.it.coffeeshoporder.A8_Chef.Model.A8_Cls_Chef_Details;
import phuhq.it.coffeeshoporder.R;

import static phuhq.it.coffeeshoporder.G_Common.G_Common.getDecimalFormattedString;

public class A4_Invoice_Adapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<A8_Cls_Chef_Details> drinkList;

    public A4_Invoice_Adapter(Context context, int layout, List<A8_Cls_Chef_Details> drinkList) {
        this.context = context;
        this.layout = layout;
        this.drinkList = drinkList;
    }

    @Override
    public int getCount() {
        return drinkList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolders {
        private TextView tvQty, tvTitle, tvTotal;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        try {
            A4_Invoice_Adapter.ViewHolders viewHolders = null;
            if (view == null) {
                // Khai báo màn hình
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert layoutInflater != null;
                view = layoutInflater.inflate(layout, null);

                // Ánh xạ đối tượng
                viewHolders = new A4_Invoice_Adapter.ViewHolders();
                viewHolders.tvQty = view.findViewById(R.id.a8_item_tvQtyOrder);
                viewHolders.tvTitle = view.findViewById(R.id.a8_item_tvTitle);
                viewHolders.tvTotal = view.findViewById(R.id.a8_item_tvQtyOrder_total);

                view.setTag(viewHolders);
                view.setTag(R.id.a8_item_tvQtyOrder, viewHolders.tvQty);
                view.setTag(R.id.a8_item_tvTitle, viewHolders.tvTitle);
                view.setTag(R.id.a8_item_tvQtyOrder_total, viewHolders.tvTotal);
            } else {
                viewHolders = (A4_Invoice_Adapter.ViewHolders) view.getTag();
            }
            // Hiển thị thông tin
            viewHolders.tvTitle.setText(String.valueOf(drinkList.get(position).getDrinkName()));
            viewHolders.tvQty.setText(String.valueOf(drinkList.get(position).getNowQty()));
            int totalRow = drinkList.get(position).getNowQty() * drinkList.get(position).getPrice();
            viewHolders.tvTotal.setText(getDecimalFormattedString(String.valueOf(totalRow)));

            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
