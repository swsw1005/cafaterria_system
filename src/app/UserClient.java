package app;

import javax.swing.JFrame;

public class UserClient extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -1381514279549167823L;

    public UserClient() {
        System.out.println("UserClient() start");

        String str = "100|20200301|20200331";

        Db db = new Db();
        // db.queryString(str);

        // for (int i = 0; i < db.temp_menu.length; i++) {
        // for (int j = 0; j < db.temp_menu[i].length; j++) {
        // System.out.print(db.temp_menu[i][j]);
        // System.out.print(" ");
        // }
        // System.out.println();
        // }

        str = "150|20200205";
        new Db().queryString(str);

        setSize(300, 300);
        setVisible(true);
        setTitle("UserClient");

    }

}