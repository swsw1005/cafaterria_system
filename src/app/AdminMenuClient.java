package app;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdminMenuClient extends JFrame implements ActionListener {

    Db db_call;

    JPanel cal_grid;// 달력 담길 그리드패널
    JPanel pan_day[] = new JPanel[31]; // 날짜 패널
    JLabel la_day[] = new JLabel[31]; // 날짜 라벨
    JLabel la_menu[] = new JLabel[31]; // 메뉴 라벨
    JLabel la_sum[] = new JLabel[31]; // 총수량 라벨
    JPanel pan_day_sum[] = new JPanel[31]; // 날짜라벨 + 총수량 라벨

    JPanel void_pan_day[];
    JLabel void_la_day[];// 빈칸 패널's 라벨

    // 요일 메뉴
    JPanel dday;
    JLabel laSun, laMon, laTue, laWed, laThu, laFri, laSat;
    // 우측 하단 메뉴패널////////////////
    JPanel jMain;
    JTextField tfDate;
    JTextField tfMenu[] = new JTextField[5];;
    JLabel laDate, laMenu1, laMenu2, laMenu3, laMenu4, laMenu5, laImg;
    JButton button1;
    ImageIcon img;
    // 우측 상단 날짜조절////////////////
    JPanel datePane;
    JButton prevBtn = new JButton("◀");
    JButton nextBtn = new JButton("▶");
    JLabel yearLbl = new JLabel("년");
    JLabel monthLbl = new JLabel("월");
    JComboBox<Integer> yearCombo = new JComboBox<Integer>();
    DefaultComboBoxModel<Integer> yearModel = new DefaultComboBoxModel<Integer>();
    JComboBox<Integer> monthCombo = new JComboBox<Integer>();
    DefaultComboBoxModel<Integer> monthModel = new DefaultComboBoxModel<Integer>();

    ///////////
    Calendar today_cal = Calendar.getInstance(); // 오늘날짜
    int today_year = today_cal.get(Calendar.YEAR);
    int today_month = today_cal.get(Calendar.MONTH);
    int today_day = today_cal.get(Calendar.DATE);
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    // 생성자----------------------------------
    public AdminMenuClient() {
        cal_grid = new JPanel(new GridLayout(6, 7, 2, 2));
        cal_generate(today_year, today_month + 1);
        cal_menu_generate();
        refresh(today_year, today_month, today_day);
        tfDate.setText(df.format(today_cal.getTime()));
        // disp();
        System.out.println(today_year + "..." + today_month + "..." + today_day);
        System.out.println("생성 ok");
    }

    // 달력 패널 재배치
    public void cal_generate(int y, int m) {

        Cal_info ci = new Cal_info(y, m);

        void_pan_day = new JPanel[42 - ci.max_day];// ex) 42-30 = 12
        void_la_day = new JLabel[42 - ci.max_day];

        for (int i = 0; i < void_pan_day.length; i++) {// 빈패널 생성 + 라벨
            void_pan_day[i] = new JPanel(); // 패널생성
            void_la_day[i] = new JLabel(); // 라벨생성
            void_pan_day[i].add(void_la_day[i]); // 패널 + 라벨
            void_pan_day[i].setBackground(new Color(254, 242, 242));
            // void_pan_day[i].setBorder(new LineBorder(Color.black));

            // if (i + 1 == void_pan_day.length) {
            // void_la_day[i].setText("끗이다 요놈아");
            // }
        }

        for (int i = 0; i < la_day.length; i++) {// 날째 , 메뉴 라벨
            String temp = (i + 1) + "";
            la_day[i] = new JLabel(temp, JLabel.LEFT);// 날짜표시 라벨
            la_menu[i] = new JLabel(temp, JLabel.LEFT); // 메뉴표시 라벨
            la_sum[i] = new JLabel("", JLabel.CENTER); // 총갯수표시 라벨
        }

        for (int i = 0; i < la_day.length; i++) {
            // 날짜패널 생성 + 레이아웃 + 날짜,메뉴 라벨링
            pan_day[i] = new JPanel();
            pan_day_sum[i] = new JPanel();

            pan_day[i].setLayout(new BorderLayout());
            pan_day_sum[i].setLayout(new GridLayout(1, 2, 0, 2));

            pan_day[i].add(la_menu[i], "Center");
            pan_day[i].add(pan_day_sum[i], "North");

            pan_day_sum[i].add(la_day[i]);
            pan_day_sum[i].add(la_sum[i]);

            la_day[i].setOpaque(true);
            la_day[i].setBorder(BorderFactory.createEmptyBorder(3, 7, 3, 2));
            la_day[i].setForeground(new Color(250, 42, 110));
            la_day[i].setBackground(new Color(251, 216, 219));

            la_menu[i].setOpaque(true);
            la_menu[i].setBorder(BorderFactory.createEmptyBorder(2, 7, 2, 0));
            la_menu[i].setForeground(new Color(103, 65, 61));
            la_menu[i].setBackground(new Color(254, 242, 242));

            la_sum[i].setOpaque(true);
            la_sum[i].setForeground(new Color(254, 136, 127));
            la_sum[i].setBackground(new Color(251, 216, 219));

            la_menu[i].setFont(new Font("굴림체", Font.PLAIN, 16));
            la_day[i].setFont(new Font("굴림체", Font.BOLD, 16));
            la_sum[i].setFont(new Font("굴림체", Font.BOLD, 20));

        }

        for (int i = 0; i < ci.void_day; i++) {
            cal_grid.add(void_pan_day[i]);
        }
        for (int i = 0; i < ci.max_day; i++) {
            cal_grid.add(pan_day[i]);
            pan_day[i].addMouseListener(new MyMouse());
        }
        for (int i = ci.void_day; i < void_pan_day.length; i++) {
            cal_grid.add(void_pan_day[i]);
        }

        setLayout(null);// 기본 레이아웃 0

        cal_grid.setBounds(10, 60, 1200, 880);
        cal_grid.setBackground(Color.white);
        cal_grid.setBorder(new LineBorder(Color.black));

    }// cal_generate end

    // 우측 메뉴 배치
    private void cal_menu_generate() {
        // 요일 붙이기
        dday = new JPanel(new GridLayout(0, 7));
        dday.setBounds(10, 10, 1200, 45);
        dday.setBackground(new Color(252, 165, 174));

        laSun = new JLabel("Sun", JLabel.CENTER);
        laMon = new JLabel("Mon", JLabel.CENTER);
        laTue = new JLabel("Tue", JLabel.CENTER);
        laWed = new JLabel("Wed", JLabel.CENTER);
        laThu = new JLabel("Thu", JLabel.CENTER);
        laFri = new JLabel("Fri", JLabel.CENTER);
        laSat = new JLabel("Sat", JLabel.CENTER);

        dday.add(laSun).setFont(new Font("굴림체", Font.BOLD, 20));
        dday.add(laMon).setFont(new Font("굴림체", Font.BOLD, 20));
        dday.add(laTue).setFont(new Font("굴림체", Font.BOLD, 20));
        dday.add(laWed).setFont(new Font("굴림체", Font.BOLD, 20));
        dday.add(laThu).setFont(new Font("굴림체", Font.BOLD, 20));
        dday.add(laFri).setFont(new Font("굴림체", Font.BOLD, 20));
        dday.add(laSat).setFont(new Font("굴림체", Font.BOLD, 20));

        dday.setBorder(new LineBorder(Color.black));
        laSun.setForeground(Color.white);
        laMon.setForeground(Color.white);
        laTue.setForeground(Color.white);
        laWed.setForeground(Color.white);
        laThu.setForeground(Color.white);
        laFri.setForeground(Color.white);
        laSat.setForeground(Color.white);

        laSun.setBounds(0, 0, 200, 40);
        laMon.setBounds(210, 0, 200, 40);
        laTue.setBounds(420, 0, 200, 40);
        laWed.setBounds(630, 0, 200, 40);
        laThu.setBounds(740, 0, 200, 40);
        laFri.setBounds(850, 0, 200, 40);
        laSat.setBounds(960, 0, 200, 40);

        getContentPane().add(dday);

        jMain = new JPanel();
        jMain.setLayout(null);
        jMain.setBackground(new Color(252, 165, 174));

        laDate = new JLabel("날짜", JLabel.RIGHT);
        laDate.setFont(new Font("Dialog", Font.BOLD, 14));
        laDate.setForeground(Color.white);

        laMenu1 = new JLabel("Menu1", JLabel.RIGHT);
        laMenu2 = new JLabel("Menu2", JLabel.RIGHT);
        laMenu3 = new JLabel("Menu3", JLabel.RIGHT);
        laMenu4 = new JLabel("Menu4", JLabel.RIGHT);
        laMenu5 = new JLabel("Menu5", JLabel.RIGHT);
        laMenu1.setFont(new Font("Dialog", Font.BOLD, 14));
        laMenu2.setFont(new Font("Dialog", Font.BOLD, 14));
        laMenu3.setFont(new Font("Dialog", Font.BOLD, 14));
        laMenu4.setFont(new Font("Dialog", Font.BOLD, 14));
        laMenu5.setFont(new Font("Dialog", Font.BOLD, 14));
        laMenu1.setForeground(Color.white);
        laMenu2.setForeground(Color.white);
        laMenu3.setForeground(Color.white);
        laMenu4.setForeground(Color.white);
        laMenu5.setForeground(Color.white);

        img = new ImageIcon("image/logo.png");
        laImg = new JLabel(img);

        tfDate = new JTextField();
        tfDate.setFont(new Font("Dialog", Font.PLAIN, 15));
        tfDate.setHorizontalAlignment(JTextField.CENTER); // 날짜칸 가운데 정렬
        tfDate.setEditable(false);// 날짜칸 편집 금지

        for (int i = 0; i < 5; i++) {
            // 메뉴칸 생성
            tfMenu[i] = new JTextField();
        }
        for (int i = 0; i < 5; i++) {
            // +폰트 설정 + 가운데 정렬
            tfMenu[i].setFont(new Font("Dialog", Font.PLAIN, 15));
            tfMenu[i].setHorizontalAlignment(JTextField.CENTER);
        }

        button1 = new JButton("메뉴 수정&입력");
        getContentPane().setLayout(null);
        jMain.setBounds(1215, 80, 320, 860);
        jMain.setBorder(new LineBorder(Color.black));

        laDate.setBounds(50, 60, 50, 30);
        laMenu1.setBounds(50, 90, 50, 30);
        laMenu2.setBounds(50, 120, 50, 30);
        laMenu3.setBounds(50, 150, 50, 30);
        laMenu4.setBounds(50, 180, 50, 30);
        laMenu5.setBounds(50, 210, 50, 30);
        laImg.setBounds(0, 610, 320, 300);

        tfDate.setBounds(110, 60, 160, 30);

        for (int i = 0; i < 5; i++) {
            tfMenu[i].setBounds(110, 90 + 30 * i, 160, 30);
            jMain.add(tfMenu[i]);
        }

        button1.setBounds(50, 250, 220, 30);
        button1.setBackground(new Color(250, 42, 110));
        button1.setForeground(Color.white);
        button1.setFont(new Font("Dialog", Font.PLAIN, 15));

        getContentPane().add(jMain);

        jMain.add(laDate);
        jMain.add(laMenu1);
        jMain.add(laMenu2);
        jMain.add(laMenu3);
        jMain.add(laMenu4);
        jMain.add(laMenu5);
        jMain.add(laImg);
        jMain.add(tfDate);
        jMain.add(button1);

        // 이벤트등록
        tfDate.addActionListener(this);
        button1.addActionListener(this);
        /////////////////////////
        // 연도 2020-1 ~ 2020+1
        // for (int i = year - 1; i <= year + 1; i++) {
        for (int i = today_year - 1; i <= today_year + 1; i++) {
            yearModel.addElement(i);
        } // for end
        yearCombo.setModel(yearModel);
        yearCombo.setSelectedItem(today_year); // 현재 년도 선택

        // 1~12월
        for (int i = 1; i <= 12; i++) {
            monthModel.addElement(i);
        } // for end

        monthCombo.setModel(monthModel);
        monthCombo.setSelectedItem(today_month + 1); // 현재 월 선택

        // datePane
        datePane = new JPanel();
        datePane.setLayout(null);
        datePane.setBounds(1215, 10, 320, 72);
        datePane.setBackground(new Color(252, 165, 174)); //////
        datePane.setBorder(new LineBorder(Color.black));

        prevBtn.setBounds(30, 20, 50, 30);
        nextBtn.setBounds(240, 20, 50, 30);
        yearCombo.setBounds(90, 20, 60, 30);
        monthCombo.setBounds(175, 20, 40, 30);
        yearLbl.setBounds(155, 20, 30, 30);
        monthLbl.setBounds(220, 20, 30, 30);
        yearLbl.setForeground(Color.white);
        monthLbl.setForeground(Color.white);

        datePane.add(prevBtn);
        datePane.add(yearCombo);
        datePane.add(yearLbl);
        datePane.add(monthCombo);
        datePane.add(monthLbl);
        datePane.add(nextBtn);

        prevBtn.addActionListener(this);
        nextBtn.addActionListener(this);
        yearCombo.addActionListener(this);
        monthCombo.addActionListener(this);

        getContentPane().add(datePane);
        getContentPane().add(cal_grid);
        getContentPane().setBackground(Color.white);
        setTitle("관리자용  - 메뉴 관리");
        setVisible(true);
        setSize(1550, 980);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // refresh(today_year, today_month, today_day);
        System.out.printf("%d - %d - %d", today_year, today_month, today_day);

        datePane.revalidate();
        datePane.repaint();
    }

    private void remove_cal() {// 달력패널 삭제 메소드
        cal_grid.removeAll();
    }

    // 데이터만 다시 불러오는 메소드(패널 재배치X)
    private void refresh(int year, int month, int date) {

        // 100|startDate|endDate // select menu (1 month)
        // 130|startDate|endDate // select count(*) orderList( 1month)
        // 150|orderDate // select orderlist 1day (AdminClient ONLY)
        // 160|startDate|endDate|orderDate // 130+150

        String sstr = "";
        db_call = new Db(); // DB연결
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

        // 날짜별로 주문합계, 메뉴 다시 뿌려준다
        for (int i = 0; i < la_day.length; i++) {
            la_menu[i].setText(db_call.ht100.get(i));
            la_sum[i].setText(db_call.temp_orderCnt[i] + "");
        }

    }

    // 안쓰는 메소드
    // private void disp()
    // private void order()
    // private void order_cancel(int k)

    @Override
    public void actionPerformed(ActionEvent e) {

        int yy = (Integer) yearCombo.getSelectedItem();
        int mm = (Integer) monthCombo.getSelectedItem();
        int dd = Integer.parseInt(tfDate.getText().substring(8, 10));
        if (e.getSource() == prevBtn) {
            System.out.println("!!! 지난달go");
            if (mm == 1) {
                yy--;
                mm = 12;
            } else {
                mm--;
            }
            System.out.println(yy + " " + mm);
            yearCombo.setSelectedItem(yy);
            monthCombo.setSelectedItem(mm);
        }

        if (e.getSource() == nextBtn) {
            System.out.println("!!!다음달go");
            if (mm == 12) {
                yy++;
                mm = 1;
            } else {
                mm++;
            }
            System.out.println(yy + " " + mm);
            yearCombo.setSelectedItem(yy);
            monthCombo.setSelectedItem(mm);
        }

        if (e.getSource() == yearCombo || e.getSource() == monthCombo) {
            remove_cal();// 기존꺼 지우고
            cal_generate(yy, mm);
            refresh(yy, mm - 1, 1);// 데이터 다시 부르고
        }

        // Admin > 주문삭제 버튼
        // User > 주문입력 버튼
        // AdminMenu > 메뉴입력&수정 버튼
        if (e.getSource() == button1) {
            // 550|insertDate|menu1|menu2|menu3|menu4|menu5 (AdminMenuClient ONLY)
            Vector<String> temp_vec = new Vector<>();

            for (int i = 0; i < 5; i++) {
                // null or 빈칸 건너뛰고 vector.add
                String temp = tfMenu[i].getText();
                if (!(temp == null || temp.length() < 1))
                    temp_vec.add(tfMenu[i].getText());
            }
            for (int i = 0; i < 5; i++) {
                temp_vec.add(" ");
            }
            String tempDate = tfDate.getText().replaceAll("-", "");
            System.out.println(tempDate);

            String tempMenuString = "500|" + tempDate + "|";

            for (int i = 0; i < 5; i++) {
                tempMenuString = tempMenuString + temp_vec.get(i) + "|";
            }
            System.out.println(tempMenuString);

            db_call.queryString(tempMenuString);

            refresh(yy, mm - 1, dd);// 데이터 다시 부르고
        }
    }

    class MyMouse extends MouseAdapter {
        // extends MouseAdapter : 프레스 하나만 쓰려면 이쪽이 편하다
        // implements MouseListener : 모든 마우스이벤트 오버라이딩....코드가 길다
        public void mousePressed(MouseEvent me) {

            for (int i = 0; i < 31; i++) {
                if (me.getSource() == pan_day[i]) {
                    int cb_year = (Integer) yearCombo.getSelectedItem();
                    int cb_month = (Integer) monthCombo.getSelectedItem();

                    System.out.println("니가 클릭한건 " + i);
                    refresh(cb_year, cb_month - 1, i + 1);

                    // tfDate에 날짜 넣기
                    String clickDate2 = ((cb_month + 100) + "").substring(1, 3);
                    String clickDate3 = ((i + 101) + "").substring(1, 3);
                    String clickDate = cb_year + "-" + clickDate2 + "-" + clickDate3;
                    tfDate.setText(clickDate);
                    // tfMenu 1,2,3,4,5 >> 메뉴 넣어주기
                    String temp = db_call.ht100.get(i);
                    temp = temp.replaceAll("<html>", "");
                    temp = temp.replaceAll("</html>", "");
                    temp = temp.replaceAll("<span>", "");
                    temp = temp.replaceAll("</span>", "");
                    temp = temp.replaceAll("<br>", "|");

                    System.out.println(temp);
                    StringTokenizer st = new StringTokenizer(temp, "|");

                    for (int j = 0; j < 5; j++) {
                        tfMenu[j].setText(st.nextToken());
                    }

                    // disp();// 우측 하단 테이블에 넣어주기
                }
            }
        }
    }

    // main
    public static void main(String[] args) {
        new AdminMenuClient();
    }
}
// inner class end