package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserClient extends JFrame implements ActionListener {

    JPanel cal_grid;

    JPanel pan_day[] = new JPanel[31];
    JPanel void_pan_day[] = new JPanel[10];
    JLabel la_day[] = new JLabel[31];
    JLabel la_menu[] = new JLabel[31];

    JLabel void_la_day[] = new JLabel[10];

    JTextField tfYear, tfMonth;

    // 우측 메뉴패널////////////////
    JPanel jMain;
    JTextField tfDate, tfSu, tfName;
    JTextArea taInfo;
    JLabel laDate, laSu, laName;
    JButton button1;
    // 우측 상단 날짜조절////////////////
    JPanel datePane;
    JButton prevBtn = new JButton("◀");
    JButton nextBtn = new JButton("▶");

    JLabel yearLbl = new JLabel("년");
    JLabel monthLbl = new JLabel("월");

    Date today = new Date();
    Calendar today_cal = Calendar.getInstance(); // 오늘날짜
    Calendar this_month_weekday = Calendar.getInstance(); // 첫달 구하는...
    Calendar view_cal = Calendar.getInstance();// 이 값으로 달력출력
    int today_year, today_month, today_day, today_weekday;

    JComboBox<Integer> yearCombo = new JComboBox<Integer>();
    DefaultComboBoxModel<Integer> yearModel = new DefaultComboBoxModel<Integer>();
    JComboBox<Integer> monthCombo = new JComboBox<Integer>();
    DefaultComboBoxModel<Integer> monthModel = new DefaultComboBoxModel<Integer>();

    // 생성자----------------------------------
    public UserClient(Calendar calendar) {
        cal_generate(calendar);
    }

    // 달력 재생성
    public void cal_generate(Calendar cal) {

        cal_grid = new JPanel();

        // today_year = cal.get(Calendar.YEAR);
        // today_month = cal.get(Calendar.MONTH);
        // today_day = cal.get(Calendar.DAY_OF_MONTH);
        // from view cal
        int year = cal.get(Calendar.YEAR);
        int month_index = cal.get(Calendar.MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);

        this_month_weekday.set(year, month_index, 1);
        // System.out.println(new SimpleDateFormat("yyyy MM
        // dd").format(this_month_weekday.getTime()));
        today_weekday = this_month_weekday.get(Calendar.DAY_OF_WEEK) - 1;

        int max_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        System.out.println(year + " " + month + " " + month_index + " " + date);
        System.out.println(today_weekday);
        System.out.println(max_day);

        for (int i = 0; i < void_pan_day.length; i++) {
            void_pan_day[i] = new JPanel();
            void_la_day[i] = new JLabel(i + "");
        }

        for (int i = 0; i < la_day.length; i++) {
            String temp = (i + 1) + "";
            la_day[i] = new JLabel(temp, JLabel.LEFT);// 날짜표시 라벨
            la_menu[i] = new JLabel(temp); // 메뉴표시 라벨
        }

        for (int i = 0; i < la_day.length; i++) {
            pan_day[i] = new JPanel();

            pan_day[i].setLayout(new BorderLayout());
            pan_day[i].add(la_day[i], "North");
            pan_day[i].add(la_menu[i], "Center");

            la_day[i].setOpaque(true);
            la_day[i].setForeground(Color.yellow);
            la_day[i].setBackground(Color.black);

            la_menu[i].setOpaque(true);
            la_menu[i].setForeground(Color.black);
            la_menu[i].setBackground(Color.yellow);

            la_menu[i].setFont(new Font("굴림체", Font.PLAIN, 18));
            la_day[i].setFont(new Font("굴림체", Font.PLAIN, 20));
        }

        // void pan day 확인용
        for (int i = 0; i < void_pan_day.length; i++) {
            void_pan_day[i].add(void_la_day[i]);
        }

        for (int i = 0; i < today_weekday; i++) {
            cal_grid.add(void_pan_day[i]);
        }
        for (int i = 0; i < max_day; i++) {
            cal_grid.add(pan_day[i]);
            pan_day[i].addMouseListener(new MyMouse());
        }
        for (int i = today_weekday; i < void_pan_day.length; i++) {
            cal_grid.add(void_pan_day[i]);
        }

        tfYear = new JTextField();
        tfMonth = new JTextField();

        setLayout(null);// 기본 레이아웃 0
        cal_grid.setLayout(new GridLayout(6, 7));
        cal_grid.setBackground(Color.cyan);

        cal_grid.setBounds(10, 10, 1200, 880);

        ////////////////////////
        jMain = new JPanel();
        jMain.setLayout(null);
        jMain.setBounds(1220, 250, 320, 500);
        jMain.setBackground(Color.white);

        laDate = new JLabel("날짜", JLabel.RIGHT);
        laDate.setFont(new Font("Diolog", Font.BOLD, 14));
        laDate.setForeground(Color.blue);

        laSu = new JLabel("수량", JLabel.RIGHT);
        laSu.setFont(new Font("Diolog", Font.BOLD, 14));
        laSu.setForeground(Color.blue);

        laName = new JLabel("주문자", JLabel.RIGHT);
        laName.setFont(new Font("Diolog", Font.BOLD, 14));
        laName.setForeground(Color.blue);
        // --
        tfDate = new JTextField();
        tfSu = new JTextField();
        tfName = new JTextField();

        taInfo = new JTextArea();
        taInfo.setBackground(Color.yellow);
        taInfo.setEditable(false);

        button1 = new JButton("주문");
        getContentPane().setLayout(null);

        laDate.setBounds(50, 50, 50, 30);
        laSu.setBounds(50, 80, 50, 30);
        laName.setBounds(50, 110, 50, 30);

        tfDate.setBounds(110, 50, 160, 30);
        tfSu.setBounds(110, 80, 160, 30);
        tfName.setBounds(110, 110, 160, 30);

        button1.setBounds(50, 150, 220, 30);

        taInfo.setBounds(50, 200, 220, 250);
        getContentPane().add(jMain);

        jMain.add(laDate);
        jMain.add(laSu);
        jMain.add(laName);
        jMain.add(tfDate);
        jMain.add(tfSu);
        jMain.add(tfName);
        jMain.add(button1);
        jMain.add(taInfo);

        // 이벤트등록
        tfDate.addActionListener(this);
        tfSu.addActionListener(this);
        tfName.addActionListener(this);
        button1.addActionListener(this);
        /////////////////////////
        // 연도 2020-1 ~ 2020+1
        for (int i = year - 1; i <= year + 1; i++) {
            yearModel.addElement(i);
        } // for end
        yearCombo.setModel(yearModel);
        yearCombo.setSelectedItem(year); // 현재 년도 선택

        // 1~12월
        for (int i = 1; i <= 12; i++) {
            monthModel.addElement(i);
        } // for end

        monthCombo.setModel(monthModel);
        monthCombo.setSelectedItem(month); // 현재 월 선택

        datePane = new JPanel();
        datePane.setLayout(null);
        datePane.setBounds(1220, 50, 350, 100);
        // datePane.setBackground(Color.white); //////

        prevBtn.setFont(new Font("Diolog", Font.BOLD, 20));
        prevBtn.setBounds(5, 5, 70, 70);

        datePane.add(prevBtn);

        JPanel comboGrid = new JPanel();
        comboGrid.setLayout(new GridLayout(2, 2));
        comboGrid.setBounds(100, 10, 150, 60);
        comboGrid.setBackground(null);
        datePane.add(comboGrid);

        yearCombo.setBounds(80, 5, 70, 30);
        comboGrid.add(yearCombo);

        yearLbl.setBounds(165, 5, 70, 30);
        comboGrid.add(yearLbl);

        monthCombo.setBounds(190, 5, 40, 30);
        comboGrid.add(monthCombo);

        monthLbl.setBounds(240, 5, 30, 30);
        comboGrid.add(monthLbl);

        nextBtn.setBounds(275, 5, 70, 70);
        datePane.add(nextBtn);

        prevBtn.addActionListener(this);
        nextBtn.addActionListener(this);
        yearCombo.addActionListener(this);
        monthCombo.addActionListener(this);

        getContentPane().add(datePane);

        /////////////////////////

        getContentPane().add(cal_grid);
        setTitle("달력테스트");
        setVisible(true);
        setSize(1600, 980);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        refresh(today_year, today_month, today_day);
        System.out.printf("%d - %d - %d", today_year, today_month, today_day);

        datePane.revalidate();
        datePane.repaint();

    }// cal_generate end

    // inner class-----------------------------------
    class MyMouse extends MouseAdapter {
        // extends MouseAdapter : 프레스 하나만 쓰려면 이쪽이 편하다
        // implements MouseListener : 모든 마우스이벤트 오버라이딩....코드가 길다
        public void mousePressed(MouseEvent me) {

            for (int i = 0; i < 31; i++) {
                if (me.getSource() == pan_day[i]) {
                    System.out.println("니가 클릭한건 " + i);
                    refresh(view_cal.get(Calendar.YEAR), view_cal.get(Calendar.MONTH), i + 1);
                    cal_generate(view_cal);
                }
            }
        }
    } // inner class end

    // 달력 삭제만...
    public void remove_cal() {
        getContentPane().remove(cal_grid);
        getContentPane().revalidate();
        getContentPane().repaint();

        datePane.revalidate();
        datePane.repaint();

        // cal_grid.removeAll();
    }

    // 데이터만 재작업
    public void refresh(int year, int month, int date) {

        // 100|startDate|endDate // select menu (1 month)
        // 130|startDate|endDate // select count(*) orderList( 1month)
        // 150|orderDate // select orderlist 1day (AdminClient ONLY)
        // 160|startDate|endDate|orderDate // 한달치 주문count + 특정일 주문내역
        // 200|orderDate|count|name // insert orderlist (UserClient ONLY)
        // 300|orderDate|name // delete orderlist (AdminClient ONLY)
        // 400|id|pwd // select adminlist

        String sstr = "";
        Db db_call = new Db(); // DB연결
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Calendar startCal = Calendar.getInstance();
        startCal.set(year, month, date);
        String orderDate = sdf.format(startCal.getTime()); // 20200105
        startCal.set(year, month, 1);
        String startDate = sdf.format(startCal.getTime()); // 20200101
        startCal.set(year, month + 1, 0);
        String endDate = sdf.format(startCal.getTime()); // 20200200 > 20200131

        System.out.println(startDate + " " + orderDate + " " + endDate);

        sstr = "100|" + startDate + "|" + endDate; // 100|20200101|20200131
        db_call.queryString(sstr);
        sstr = "160|" + startDate + "|" + endDate + "|" + orderDate;
        db_call.queryString(sstr);

        for (int i = 0; i < la_day.length; i++) {
            la_menu[i].setText(db_call.ht100.get(i));
        }

    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello Java");
        new UserClient(Calendar.getInstance());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int yy = (Integer) yearCombo.getSelectedItem();
        int mm = (Integer) monthCombo.getSelectedItem();
        if (e.getSource() == prevBtn) {
            System.out.println("!!! 지난달go");
            if (mm == 1) {
                yy--;
                mm = 12;
            } else {
                mm--;
            }
            System.out.println(yy + " " + mm);
            remove_cal();// 기존꺼 지우고
            view_cal.set(yy, mm - 1, 1);// 뷰칼 설정
            refresh(yy, mm - 1, 1);// 데이터 다시 부르고
            cal_generate(view_cal); // 달력 생성

        } // prevBtn if
        if (e.getSource() == nextBtn) {
            System.out.println("!!!다음달go");
            if (mm == 12) {
                yy++;
                mm = 1;
            } else {
                mm++;
            }
            System.out.println(yy + " " + mm);
            remove_cal();// 기존꺼 지우고
            view_cal.set(yy, mm - 1, 1);// 뷰칼 설정
            refresh(yy, mm - 1, 1);// 데이터 다시 부르고
            cal_generate(view_cal); // 달력 생성
        } // nextBtn if

        yearCombo.setSelectedItem(yy);
        monthCombo.setSelectedItem(mm);

        System.out.println(yy + "--- " + mm);

    }
}