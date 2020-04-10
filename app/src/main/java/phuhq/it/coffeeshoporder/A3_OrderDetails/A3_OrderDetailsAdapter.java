package phuhq.it.coffeeshoporder.A3_OrderDetails;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import phuhq.it.coffeeshoporder.R;

public class A3_OrderDetailsAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<A3_Drink> drinkList;

    public A3_OrderDetailsAdapter(Context context, int layout, List<A3_Drink> drinkList) {
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
        private TextView tvTotal, tvTitle, tvPrice;
        private ImageView imgDrink, btnIncrease, btnReduction;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        try {
            A3_OrderDetailsAdapter.ViewHolders viewHolders = null;
            if (view == null) {
                // Khai báo màn hình
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert layoutInflater != null;
                view = layoutInflater.inflate(layout, null);

                // Ánh xạ đối tượng
                viewHolders = new A3_OrderDetailsAdapter.ViewHolders();
                viewHolders.tvTotal = view.findViewById(R.id.a3_item_tvTotal);
                viewHolders.tvTitle = view.findViewById(R.id.a3_item_tvTitle);
                viewHolders.tvPrice = view.findViewById(R.id.a3_item_tvPrice);
                viewHolders.imgDrink = view.findViewById(R.id.a3_item_img);
                viewHolders.btnIncrease = view.findViewById(R.id.a3_item_imgIncrease);
                viewHolders.btnReduction = view.findViewById(R.id.a3_item_imgReduction);

                view.setTag(viewHolders);
                view.setTag(R.id.a3_item_tvTotal, viewHolders.tvTotal);
                view.setTag(R.id.a3_item_tvTitle, viewHolders.tvTitle);
                view.setTag(R.id.a3_item_tvPrice, viewHolders.tvPrice);
                view.setTag(R.id.a3_item_img, viewHolders.imgDrink);
                view.setTag(R.id.a3_item_imgIncrease, viewHolders.btnIncrease);
                view.setTag(R.id.a3_item_imgReduction, viewHolders.btnReduction);
            } else {
                viewHolders = (A3_OrderDetailsAdapter.ViewHolders) view.getTag();
            }
            // Hiển thị thông tin
            viewHolders.tvTitle.setText(String.valueOf(drinkList.get(position).getDrinkName()));
            String showPrice = "$ " + drinkList.get(position).getPrice();
            viewHolders.tvPrice.setText(showPrice);
            viewHolders.imgDrink.setImageResource(drinkList.get(position).getImage());
            // Sự kiện click
            final ViewHolders finalViewHolders = viewHolders;
            viewHolders.btnReduction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int orderNum = Integer.parseInt(finalViewHolders.tvTotal.getText().toString());
                    if (orderNum == 10) {
                        // Order = 0 -> không cho giảm nữa
                        finalViewHolders.btnReduction.setClickable(false);
                    } else {
                        orderNum += 1;
                        drinkList.get(position).setQty(orderNum);
                        finalViewHolders.tvTotal.setText(String.valueOf(orderNum));
                        finalViewHolders.tvTotal.setTextColor(context.getResources().getColor(R.color.primaryTextColor));
                        finalViewHolders.btnIncrease.setClickable(true);
                    }
                }
            });
            viewHolders.btnIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int orderNum = Integer.parseInt(finalViewHolders.tvTotal.getText().toString());
                    if (orderNum == 0) {
                        // Order = 0 -> không cho giảm nữa
                        finalViewHolders.btnIncrease.setClickable(false);
                    } else {
                        orderNum -= 1;
                        if (orderNum ==0){
                            finalViewHolders.tvTotal.setTextColor(context.getResources().getColor(R.color.black));
                        }
                        finalViewHolders.tvTotal.setText(String.valueOf(orderNum));
                        drinkList.get(position).setQty(orderNum);
                        finalViewHolders.btnReduction.setClickable(true);
                    }
                }
            });

            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
