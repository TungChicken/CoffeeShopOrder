package phuhq.it.coffeeshoporder.A7_TableList.Presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import phuhq.it.coffeeshoporder.A7_TableList.Model.A7_Cls_Table;
import phuhq.it.coffeeshoporder.R;

import static phuhq.it.coffeeshoporder.G_Common.G_Common.STATUS_ORDER;
import static phuhq.it.coffeeshoporder.G_Common.G_Common.STATUS_PENDING;
import static phuhq.it.coffeeshoporder.G_Common.G_Common.flagTableList;

public class A7_Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<A7_Cls_Table> listTable;

    public A7_Adapter(Context context, int layout, List<A7_Cls_Table> listTable) {
        this.context = context;
        this.layout = layout;
        this.listTable = listTable;
    }

    @Override
    public int getCount() {
        return listTable.size();
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
        private TextView tvTableName, tvStatus;
        private ImageView img;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        try {
            A7_Adapter.ViewHolders viewHolders = null;
            if (view == null) {
                // Khai báo màn hình
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert layoutInflater != null;
                view = layoutInflater.inflate(layout, null);

                // Ánh xạ đối tượng
                viewHolders = new A7_Adapter.ViewHolders();
                viewHolders.tvTableName = view.findViewById(R.id.a7_tablelist_item_tablename);
                viewHolders.tvStatus = view.findViewById(R.id.a7_tablelist_item_status);
                viewHolders.img = view.findViewById(R.id.a7_tablelist_item_icon);

                view.setTag(viewHolders);
                view.setTag(R.id.a7_tablelist_item_tablename, viewHolders.tvTableName);
                view.setTag(R.id.a7_tablelist_item_status, viewHolders.tvStatus);
                view.setTag(R.id.a7_tablelist_item_icon, viewHolders.img);
            } else {
                viewHolders = (A7_Adapter.ViewHolders) view.getTag();
            }
            // Hiển thị thông tin
            viewHolders.tvTableName.setText(String.valueOf(listTable.get(position).getName()));
            viewHolders.tvStatus.setText(String.valueOf(listTable.get(position).getStatus()));
            if (flagTableList) {
                viewHolders.img.setImageResource(R.drawable.a7_table_order);
            } else {
                if (listTable.get(position).getStatus().equals(STATUS_PENDING)) {
                    viewHolders.img.setImageResource(R.drawable.a7_table_pending);
                } else {
                    viewHolders.img.setImageResource(R.drawable.a7_table_free);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
