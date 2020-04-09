package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class App extends JFrame implements ActionListener {

    JPanel cal_grid = new JPanel();

    JPanel pan_day[] = new JPanel[31];
    JPanel void_pan_day[] = new JPanel[6];
    JLabel la_day[] = new JLabel[31];

    JTextField tfYear, tfMonth;

    Date today = new Date();
    Calendar today_cal = Calendar.getInstance();
    int today_year, today_month, today_day, today_weekday;

    public App() {

        today_year = today_cal.get(Calendar.YEAR);
        today_month = today_cal.get(Calendar.MONTH);
        today_day = today_cal.get(Calendar.DAY_OF_MONTH);
        today_weekday = today_cal.get(Calendar.DAY_OF_WEEK);

        for (int i = 0; i < 6; i++) {
            void_pan_day[i] = new JPanel();
        }

        for (int i = 0; i < la_day.length; i++) {
            String temp = (i + 1) + "";
            la_day[i] = new JLabel(temp, JLabel.RIGHT);
        }

        for (int i = 0; i < la_day.length; i++) {
            pan_day[i] = new JPanel();
            pan_day[i].add(la_day[i]);
        }

        int void_day = 3;
        int max_day = 30;
        for (int i = 0; i < void_day; i++) {
            cal_grid.add(void_pan_day[i]);
        }

        for (int i = 0; i < max_day; i++) {
            cal_grid.add(pan_day[i]);
        }

        tfYear = new JTextField();
        tfMonth = new JTextField();

        setLayout(null);// 기본 레이아웃 0
        cal_grid.setLayout(new GridLayout(6, 7));
        cal_grid.setBackground(Color.cyan);

        cal_grid.setBounds(10, 10, 500, 500);

        getContentPane().add(cal_grid);
        setTitle("달력테스트");
        setVisible(true);
        setSize(700, 700);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void disp() {

    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello Java");
        new App();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}