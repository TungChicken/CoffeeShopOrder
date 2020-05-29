package phuhq.it.coffeeshoporder.A4_OrderOverView.Presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import phuhq.it.coffeeshoporder.A3_OrderDetails.Model.A3_Cls_Drinks;
import phuhq.it.coffeeshoporder.R;

import static phuhq.it.coffeeshoporder.G_Common.G_Common.getDecimalFormattedString;

public class A4_OverViewAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<A3_Cls_Drinks> drinkList;

    public A4_OverViewAdapter(Context context, int layout, List<A3_Cls_Drinks> drinkList) {
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
        private TextView tvQty, tvTitle, tvTotalPrice;
        private ImageView imgDrink;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        try {
            A4_OverViewAdapter.ViewHolders viewHolders = null;
            if (view == null) {
                // Khai báo màn hình
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert layoutInflater != null;
                view = layoutInflater.inflate(layout, null);

                // Ánh xạ đối tượng
                viewHolders = new A4_OverViewAdapter.ViewHolders();
                viewHolders.tvQty = view.findViewById(R.id.a4_item_orderQty);
                viewHolders.tvTitle = view.findViewById(R.id.a4_item_tvTitle);
                viewHolders.tvTotalPrice = view.findViewById(R.id.a4_item_tvTotalPrice);
                viewHolders.imgDrink = view.findViewById(R.id.a4_item_img);

                view.setTag(viewHolders);
                view.setTag(R.id.a4_item_orderQty, viewHolders.tvQty);
                view.setTag(R.id.a4_item_tvTitle, viewHolders.tvTitle);
                view.setTag(R.id.a4_item_tvTotalPrice, viewHolders.tvTotalPrice);
                view.setTag(R.id.a4_item_img, viewHolders.imgDrink);
            } else {
                viewHolders = (A4_OverViewAdapter.ViewHolders) view.getTag();
            }
            // Hiển thị thông tin
            viewHolders.tvTitle.setText(String.valueOf(drinkList.get(position).getDrinkName()));
            viewHolders.tvQty.setText(String.valueOf(drinkList.get(position).getNowQty()));
            Picasso.with(context).load(drinkList.get(position).getImage())
                    .placeholder(R.drawable.c1)
                    .error(R.drawable.c1).into(viewHolders.imgDrink);
            int totalPrice = drinkList.get(position).getPrice() * drinkList.get(position).getNowQty();
            viewHolders.tvTotalPrice.setText(getDecimalFormattedString(String.valueOf(totalPrice)));

            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
