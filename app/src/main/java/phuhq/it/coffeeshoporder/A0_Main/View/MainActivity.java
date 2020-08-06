package phuhq.it.coffeeshoporder.A0_Main.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

import phuhq.it.coffeeshoporder.A3_OrderDetails.View.A3_OrderDialog;
import phuhq.it.coffeeshoporder.A3_OrderDetails.View.A3_OrderDialogMore;
import phuhq.it.coffeeshoporder.A6_UserInfo.View.A6_UserInfo;
import phuhq.it.coffeeshoporder.A7_TableList.View.A7_TableList;
import phuhq.it.coffeeshoporder.R;

import static phuhq.it.coffeeshoporder.G_Common.G_Common.STATUS_FINISH;
import static phuhq.it.coffeeshoporder.G_Common.G_Common.flagTableList;

public class MainActivity extends AppCompatActivity {
    //region AVAILABLE
    //public A2_Cls_User clsUser;
    //endregion

    //region FROM EVENT
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLoad();
        onListeningDataChange();
    }

    @Override
    protected void onResume() {
        super.onResume();



    }

    //endregion

    //region ADD CONTROLS AND LOAD
    public void addControls() {
    }

    public void mainLoad() {
        addControls();
        //clsUser = (A2_Cls_User) getIntent().getSerializableExtra("USER");
    }
    //endregion

    //region ON TOUCH
    public void onSmoothieClick(View view) {
        showDialogOrder(1);
    }

    public void onCoffeeClick(View view) {
        showDialogOrder(2);
    }

    public void onOrder(View view) {
        flagTableList = false; // order, pending
        Intent intent = new Intent(MainActivity.this, A7_TableList.class);
        startActivity(intent);
    }

    public void onInvoice(View view) {
        flagTableList = true; // invoice
        Intent intent = new Intent(MainActivity.this, A7_TableList.class);
        startActivity(intent);
    }

    public void onUserAccount(View view) {
        Intent intent = new Intent(MainActivity.this, A6_UserInfo.class);
        //intent.putExtra("USER", clsUser);
        startActivity(intent);
    }
    //endregion

    //region ON LISTENING STATUS CHANGE
    public void onListeningDataChange() {
        try {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference reference = database.getReference("CSO").child("TBT_Notifications");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        String tableName = dataSnapshot.child("TableName").getValue().toString();
                        playBeep();
                        onMessageAlertInfo(tableName);
                        reference.child("TableName").removeValue();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region BOTTOM DIALOG
    private void showDialogOrder(int screen) {
        try {
            // Only call xml file
//            View dialogView = getLayoutInflater().inflate(R.layout.a3_order_dialog, null);
//            BottomSheetDialog dialog = new BottomSheetDialog(this);
//            dialog.setContentView(dialogView);
//            dialog.show();

            // Call fragment
            if (screen == 1) {
                A3_OrderDialogMore orderDialogMore = new A3_OrderDialogMore();
                orderDialogMore.show(getSupportFragmentManager(), orderDialogMore.getTag());
            } else {
                A3_OrderDialog orderDialog = new A3_OrderDialog();
                orderDialog.show(getSupportFragmentManager(), orderDialog.getTag());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region LISTENING NOTIFICATIONS
    public void playBeep() {
        // Cách 1: Sử dụng audio có sẵn của android
//        ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 500);
//        toneGenerator.startTone(ToneGenerator.TONE_PROP_PROMPT);

        // Cách 2: Sử dụng audio của mình
        Uri beepSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + File.pathSeparator
                + File.separator + getPackageName() + "/raw/notifications.mp3");
        Ringtone ringtone = RingtoneManager.getRingtone(this, beepSound);
        ringtone.play();
    }

    public void onMessageAlertInfo(String msgKey) {
        try {
            String title = msgKey + STATUS_FINISH;
            String body = "Finish. Please give for customer";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(title);
            builder.setMessage(body);

            DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case DialogInterface.BUTTON_POSITIVE:
                            break;
                    }
                }
            };

            builder.setPositiveButton("OK", clickListener);
            builder.setIcon(R.drawable.ic_check);
            //builder.show();

            Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}
