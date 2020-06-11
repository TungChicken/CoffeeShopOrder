package phuhq.it.coffeeshoporder.G_Common;

import java.util.StringTokenizer;

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
}
