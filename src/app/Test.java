package app;

public class Test {
    public static void main(String[] args) {

        // String str = "100|20200301|20200331";
        // Db db = new Db();
        // db.queryString(str);
        // for (int i = 0; i < db.temp_menu.length; i++) {
        // for (int j = 0; j < db.temp_menu[i].length; j++) {
        // System.out.print(db.temp_menu[i][j]);
        // System.out.print(" ");
        // }
        // System.out.println();
        // }

        // 11
        // String str = "130|20200301|20200331";
        // Db db = new Db();
        // db.queryString(str);

        // 111
        // String str = "150|20200301";
        // Db db = new Db();
        // db.queryString(str);
        // for (int i = 0; i < db.vec1.size(); i++) {
        // System.out.println(db.vec1.get(i) + " " + db.vec2.get(i));
        // }

        // 111
        // String str = "200|20200301|10|김철수";
        // Db db = new Db();
        // db.queryString(str);

        String str2 = "150|20200301";
        Db db2 = new Db();
        db2.queryString(str2);
        for (int i = 0; i < db2.vec1.size(); i++) {
            System.out.println(db2.vec1.get(i) + " " + db2.vec2.get(i));
        }

    }
}