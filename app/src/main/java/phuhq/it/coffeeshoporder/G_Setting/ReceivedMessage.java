package phuhq.it.coffeeshoporder.G_Setting;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import phuhq.it.coffeeshoporder.R;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class ReceivedMessage extends FirebaseMessagingService {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(123, notification);

//        String link  = "https://firebasestorage.googleapis.com/v0/b/coffeeshoporder-ed93d.appspot.com/o/ic_check.png?alt=media&token=8502f896-168e-43b8-84a6-d992f1344e64";
//        sendNotification("T02","First time of send",link, null,null);
    }


//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private void sendNotification(final String txtTitle, final String txtBody, final String customLink, final String txtRefrigUID, final String txtCode) {
//
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("title", txtTitle);
//        intent.putExtra("body", txtBody);
//        intent.putExtra("link", customLink);
//        intent.putExtra("RefrigUID", txtRefrigUID);
//        intent.putExtra("code", txtCode);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        long[] pattern = {500, 500, 500, 500, 500};
//        Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        @SuppressLint("WrongConstant") NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_check)
//                .setBadgeIconType(R.drawable.ic_favorite_black_24dp)
//                .setContentTitle(txtTitle)
//                .setContentText(txtBody)
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
//                .setColor(Color.RED)
//                .setVibrate(pattern)
//                .setSound(defaultUri)
//                .setLights(Color.BLUE, 1, 1)
//                .setPriority(NotificationManager.IMPORTANCE_HIGH);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }
}
