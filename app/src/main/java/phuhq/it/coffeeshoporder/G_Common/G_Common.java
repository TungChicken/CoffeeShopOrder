package phuhq.it.coffeeshoporder.G_Common;

import android.app.Notification;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.StringTokenizer;

import phuhq.it.coffeeshoporder.R;

public class G_Common {
    public static String userLogin;
    public static boolean flagTableList = false; //false: order;   true:invoice
    public static String tableOrder;
    public static String tableNameOrder;
    public static final String STATUS_ORDER = "order";
    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_FREE = "free";
    public static final String PER_ADMIN = "admin";
    public static final String PER_CHEF = "chef";
    public static final String PER_EMP = "employee";
    public static final String PASS_DEFAULT = "123";
    public static final String STATUS_FINISH = " READY !!!";

    public static String getDecimalFormattedString(String value) {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1) {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        String str3 = "";
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt(-1 + str1.length()) == '.') {
            j--;
            str3 = ".";
        }
        for (int k = j; ; k--) {
            if (k < 0) {
                if (str2.length() > 0)
                    str3 = str3 + "." + str2;
                return str3;
            }
            if (i == 3) {
                str3 = "," + str3;
                i = 0;
            }
            str3 = str1.charAt(k) + str3;
            i++;
        }
    }

    public static void showNotifications(Context context, String message) {
        try {
            String title = message + STATUS_FINISH;
            String body = "Finish for build";
            Notification notification = new NotificationCompat.Builder(context)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.drawable.ic_check)
                    .build();

            NotificationManagerCompat manager = NotificationManagerCompat.from(context);
            manager.notify(123, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
