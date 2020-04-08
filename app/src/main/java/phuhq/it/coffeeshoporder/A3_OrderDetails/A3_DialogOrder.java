package phuhq.it.coffeeshoporder.A3_OrderDetails;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import phuhq.it.coffeeshoporder.R;

public class A3_DialogOrder extends Dialog implements View.OnClickListener {
    //region AVAILABLE
    private SeekBar seekBar;
    private TextView tvOrderCount;

    public Activity activity;
    public Dialog dialog;
    //endregion

    //region FORM EVENTS
    public A3_DialogOrder(@NonNull Context context) {
        super(context);
    }

    public A3_DialogOrder(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected A3_DialogOrder(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3_dialog_order);
        mainLoad();
        setDataSeekBar();
    }

    private void addControls() {
        seekBar = findViewById(R.id.seekBar);
        tvOrderCount = findViewById(R.id.textView26);
    }

    private void mainLoad() {
        addControls();
    }

    //endregion

    //region SEEKBAR
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDataSeekBar() {
        try {
            this.seekBar.setMax(10);
            this.seekBar.setMin(1);
            tvOrderCount.setText(String.valueOf(seekBar.getProgress()));

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                int progress = 0;

                @Override
                public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                    progress = progressValue;
                    tvOrderCount.setText(String.valueOf(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    tvOrderCount.setText(String.valueOf(progress));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

    }
    //endregion
}
