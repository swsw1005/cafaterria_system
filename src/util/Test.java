package util;

import java.util.Calendar;
import java.util.StringTokenizer;

// 100|startDate|endDate // select menu (1 month)
// 130|startDate|endDate // select count(*) orderList( 1month)
// 150|orderDate // select orderlist 1day (AdminClient ONLY)
// 160|startDate|endDate|orderDate
// 200|orderDate|count|name // insert orderlist (UserClient ONLY)
// 300|orderDate|name // delete orderlist (AdminClient ONLY)

public class Test {
    public static void main(String[] args) {

        // String str = "100|20200301|20200331";
        // Db db = new Db();
        // db.queryString(str);
        // for (int i = 0; i < db.temp_menu.length; i++) {
        // for (int j = 0; j < db.temp_menu[i].length; j++) {
        // System.out.println(db.temp_menu[i][j]);
        // }
        // System.out.println();
        // }
        // for (int i = 0; i < db.ht100.size(); i++) {
        // System.out.println(i);
        // System.out.println(db.ht100.get(i));
        // }

        // 11
        // String str = "130|20200301|20200310";
        // str = "130|20200301|20200310";
        // Db db = new Db();
        // db.queryString(str);

        // 111
        // String str3 = "150|20200301";
        // Db db3 = new Db();
        // db3.queryString(str3);
        // for (int i = 0; i < db3.vec1.size(); i++) {
        // System.out.println(db3.vec1.get(i) + " " + db3.vec2.get(i));
        // }

        // 111
        String str = "200|20200301|10|김철수";
        Db db = new Db();
        db.queryString(str);

        // String str2 = "150|20200301";
        // Db db2 = new Db();
        // db2.queryString(str2);
        // for (int i = 0; i < db2.vec1.size(); i++) {
        // System.out.println(db2.vec1.get(i) + " " + db2.vec2.get(i));
        // }

        // String str2 = "160|20200301|20200310|20200303";
        // Db db2 = new Db();
        // db2.queryString(str2);
        // // 150검증
        // for (int i = 0; i < db2.vec1.size(); i++) {
        // System.out.println(db2.vec1.get(i) + " " + db2.vec2.get(i));
        // }

        // String str3 = "400|admin|12345";
        // Db db = new Db();
        // db.queryString(str3);

        // String str4 = "300|1";
        // Db db = new Db();
        // db.queryString(str4);

        // Calendar cc = Calendar.getInstance();
        // cc.set(2020, 4, 1);
        // // uc.cal_generate(cc);
        // UserClient uc = new UserClient(cc);

        // Cal_info ci = new Cal_info(2020, 2);

        // String str3 = "500|20191101|밥알|김치|계란|묵| ";
        // Db db = new Db();
        // db.queryString(str3);

    }
}
