package app;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import util.Db;
import util.Cal_info;

public class AdminClient extends JFrame implements ActionListener {

    // ### 변수
    Db db_call;

    JPanel cal_grid;// 달력 담길 그리드패널
    JPanel pan_day[] = new JPanel[31]; // 날짜 패널
    JLabel la_day[] = new JLabel[31]; // 날짜 라벨
    JLabel la_menu[] = new JLabel[31]; // 메뉴 라벨
    JLabel la_sum[] = new JLabel[31]; // 총수량 라벨
    JPanel pan_day_sum[] = new JPanel[31]; // add 날짜라벨 + 총수량 라벨
    JPanel void_pan_day[]; // 빈칸 패널
    JLabel void_la_day[];// 빈칸 패널's 라벨
    // 요일 패널 + 라벨
    JPanel dday;
    JLabel laSun, laMon, laTue, laWed, laThu, laFri, laSat;
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
    // 우측 하단 메뉴패널////////////////
    JPanel jMain;

    JTextField tfDate, tfSu, tfName;
    JTextField tfMenu[] = new JTextField[5];
    JLabel laDate, laSu, laName, laImg;
    JLabel laMenu[] = new JLabel[5];
    JButton button1, button2;
    ImageIcon img;
    ///////////
    Calendar today_cal = Calendar.getInstance(); // 오늘날짜
    int today_year = today_cal.get(Calendar.YEAR);
    int today_month = today_cal.get(Calendar.MONTH);
    int today_day = today_cal.get(Calendar.DATE);
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    // 테이블모델 >> 테이블 >> 스크롤
    // DefaultTableModel model = new DefaultTableModel(data, cols);
    Object data[][] = new Object[0][3]; // 5개 데이터가 담길 오브젝트
    String cols[] = { "no", "주문자", "수량" };
    DefaultTableModel model = new DefaultTableModel(data, cols) {
        // 테이블모델...편집 못하게 메소드 오버라이딩
        @Override
        public boolean isCellEditable(int i, int c) {
            return false;
        }
    };
    JTable ordertable = new JTable(model);
    JScrollPane tableSP = new JScrollPane(ordertable);

    // ### 생성자----------------------------------
    public AdminClient() {

        // System.setProperty("sun.java2d.uiScale", "1.0");

        // 달력 layout
        cal_grid = new JPanel(new GridLayout(6, 7, 2, 2));
        // 우측 메뉴 생성
        cal_menu_generate();
        // 달력 패널 생성
        cal_generate(today_year, today_month + 1);
        // 데이터 refresh
        refresh(today_year, today_month, today_day);
        // 오늘날짜로 시작
        tfDate.setText(df.format(today_cal.getTime()));
        // 주문목록 보여주기
        disp();
        // System.out.println(today_year + "..." + today_month + "..." + today_day);
        // System.out.println("생성 ok");
    }

    // ### 달력 패널 배치하는 메소드
    public void cal_generate(int y, int m) {

        Cal_info ci = new Cal_info(y, m);

        void_pan_day = new JPanel[42 - ci.max_day];// ex) 42-30 = 12
        void_la_day = new JLabel[42 - ci.max_day];

        // 빈패널 생성 + 라벨
        for (int i = 0; i < void_pan_day.length; i++) {
            void_pan_day[i] = new JPanel(); // 패널생성
            void_la_day[i] = new JLabel(); // 라벨생성
            void_pan_day[i].add(void_la_day[i]); // 패널 + 라벨
            void_pan_day[i].setBackground(new Color(254, 242, 242));
        } // for end
        for (int i = 0; i < la_day.length; i++) {// 날째 , 메뉴 라벨
            String temp = (i + 1) + "";
            la_day[i] = new JLabel(temp, JLabel.LEFT);// 날짜표시 라벨
            la_menu[i] = new JLabel(temp, JLabel.LEFT); // 메뉴표시 라벨
            la_sum[i] = new JLabel("", JLabel.RIGHT); // 1일주문합 표시 라벨
        } // for end
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
            la_sum[i].setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 7));
            la_sum[i].setForeground(new Color(254, 136, 127));
            la_sum[i].setBackground(new Color(251, 216, 219));

            la_menu[i].setFont(new Font("굴림체", Font.PLAIN, 16));
            la_day[i].setFont(new Font("굴림체", Font.BOLD, 16));
            la_sum[i].setFont(new Font("굴림체", Font.BOLD, 20));
        } // for end

        // 달력 패널 배치
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

    // ### 우측 메뉴 생성메소드
    public void cal_menu_generate() {
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

        /// 우측 데이터 jMain 패널
        jMain = new JPanel();
        jMain.setLayout(null);
        jMain.setBackground(new Color(252, 165, 174));
        // 라벨 생성 - 이름 지정, 글자 정렬
        laDate = new JLabel("날짜", JLabel.RIGHT);
        for (int i = 0; i < 5; i++) {
            laMenu[i] = new JLabel("Menu" + (i + 1));
            laMenu[i].setFont(new Font("Dialog", Font.BOLD, 14));
            laMenu[i].setForeground(Color.white);
        }
        laSu = new JLabel("수량", JLabel.RIGHT);
        laName = new JLabel("주문자", JLabel.RIGHT);
        // 라벨 글씨체,스타일,글자크기 지정
        laDate.setFont(new Font("Dialog", Font.BOLD, 14));
        laSu.setFont(new Font("Dialog", Font.BOLD, 14));
        laName.setFont(new Font("Dialog", Font.BOLD, 14));
        // 라벨 글자색 지정
        laDate.setForeground(Color.white);
        laSu.setForeground(Color.white);
        laName.setForeground(Color.white);
        // 로고미지 생성 및 라벨에 넣기
        img = new ImageIcon("image/Adminlogo.png");
        laImg = new JLabel(img);
        // TextField 생성
        tfDate = new JTextField();
        tfName = new JTextField();
        tfSu = new JTextField();

        // TextField Menu[] 생성 및 속성 지정
        for (int i = 0; i < 5; i++) {
            tfMenu[i] = new JTextField(); // tfMenu[i] 생성
            tfMenu[i].setFont(new Font("Dialog", Font.PLAIN, 15)); // 메뉴 폰트 설정
            tfMenu[i].setHorizontalAlignment(JTextField.CENTER); // 메뉴칸 가운데 정렬
            tfMenu[i].setBounds(110, 40 + 30 * i, 160, 30); // 메뉴 위치 및 사이즈 지정
            jMain.add(tfMenu[i]); // JMain에 Menu[] 붙이기
        }

        // TextField 글씨체,스타일,글자크기 지정
        tfDate.setFont(new Font("Dialog", Font.PLAIN, 15));
        tfName.setFont(new Font("Dialog", Font.PLAIN, 15));
        tfSu.setFont(new Font("Dialog", Font.PLAIN, 15));
        // TextField 내용 정렬
        tfDate.setHorizontalAlignment(JTextField.CENTER); // 날짜칸 가운데 정렬
        tfName.setHorizontalAlignment(JTextField.CENTER);
        tfSu.setHorizontalAlignment(JTextField.CENTER);
        // TextField 내용 편집 제한하기
        tfDate.setEditable(false);// 날짜칸 편집 금지
        tfName.setEditable(false);
        tfSu.setEditable(false);

        getContentPane().setLayout(null);
        jMain.setBounds(1215, 80, 320, 860);
        jMain.setBorder(new LineBorder(Color.black));
        // Label 위치 및 크기 지정
        laDate.setBounds(50, 10, 50, 30);
        for (int i = 0; i < 5; i++) {
            laMenu[i].setBounds(50, (40 + 30 * i), 80, 30);
        }
        laSu.setBounds(50, 190, 50, 30);
        laName.setBounds(50, 220, 50, 30);
        laImg.setBounds(0, 610, 320, 300);
        // TextField 위치 및 크기 지정
        tfDate.setBounds(110, 10, 160, 30);
        tfSu.setBounds(110, 190, 160, 30);
        tfName.setBounds(110, 220, 160, 30);

        // Button 생성 및 속성 지정
        button1 = new JButton("주문 취소");
        button2 = new JButton("메뉴 수정");
        button1.setBounds(50, 300, 220, 30);
        button2.setBounds(50, 260, 220, 30);
        button1.setBackground(new Color(250, 42, 110));
        button2.setBackground(new Color(250, 42, 110));
        button1.setForeground(Color.white);
        button2.setForeground(Color.white);
        button1.setFont(new Font("Dialog", Font.PLAIN, 15));
        button2.setFont(new Font("Dialog", Font.PLAIN, 15));

        tableSP.setBounds(50, 340, 220, 300);
        getContentPane().add(jMain); // 프레임에 jMain Panel 붙이기
        // jMain 패널에 Label,TextFiled,TextArea 붙이기
        jMain.add(laDate);
        jMain.add(laSu);
        jMain.add(laName);
        for (int i = 0; i < 5; i++) {
            jMain.add(laMenu[i]);
        }
        jMain.add(laImg);
        jMain.add(tfDate);
        jMain.add(tfSu);
        jMain.add(tfName);
        jMain.add(button1);
        jMain.add(button2);
        jMain.add(tableSP); ///// 고객용페이지 // 테이블 안보여준다

        // 테이블 너비 설정
        ordertable.getTableHeader().setReorderingAllowed(false); // 이동 불가
        ordertable.getTableHeader().setResizingAllowed(false); // 크기 조절 불가
        TableColumn tc = ordertable.getColumnModel().getColumn(0);
        tc.setMaxWidth(50);
        tc.setMinWidth(50);
        tc = ordertable.getColumnModel().getColumn(1);
        tc.setMaxWidth(200);
        tc.setMinWidth(50);
        tc = ordertable.getColumnModel().getColumn(2);
        tc.setMaxWidth(50);
        tc.setMinWidth(50);

        // 이벤트등록
        ordertable.addMouseListener(new MyMouse());
        tfDate.addActionListener(this);
        tfSu.addActionListener(this);
        tfName.addActionListener(this);
        button1.addActionListener(this);
        button2.addActionListener(this);
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
        setTitle("관리자용 페이지");
        setVisible(true);
        setSize(1555, 980);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // refresh(today_year, today_month, today_day);
        System.out.printf("%d - %d - %d", today_year, today_month, today_day);

    }// cal_menu_generate() end

    // ### 달력 패널 삭제 메소드
    public void remove_cal() {
        cal_grid.removeAll();
    } // remove_cal() end

    // ### 데이터만 다시 불러오는 메소드(패널 재배치X)
    public void refresh(int year, int month, int date) {

        // 100|startDate|endDate // select menu (1 month)
        // 130|startDate|endDate // select count(*) orderList( 1month)
        // 150|orderDate // select orderlist 1day (AdminClient ONLY)
        // 160|startDate|endDate|orderDate // 130+150

        try {
            String sstr = "";
            db_call = new Db(); // DB연결
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            Calendar startCal = Calendar.getInstance();
            startCal.set(year, month, date);
            String orderDate = sdf.format(startCal.getTime()); // ex) 20200105
            startCal.set(year, month, 1);
            String startDate = sdf.format(startCal.getTime()); // ex) 20200101
            startCal.set(year, month + 1, 0);
            String endDate = sdf.format(startCal.getTime()); // ex) 20200200 => 20200131

            System.out.println(startDate + " " + orderDate + " " + endDate);

            sstr = "100|" + startDate + "|" + endDate; // 100|20200101|20200131
            db_call.queryString(sstr);
            sstr = "160|" + startDate + "|" + endDate + "|" + orderDate;
            db_call.queryString(sstr);

            for (int i = 0; i < la_day.length; i++) {
                la_menu[i].setText(db_call.ht100.get(i));
                la_sum[i].setText(db_call.orderCnt130[i] + "");
            }

            // 오늘날짜 서식 적용
            boolean b1 = yearCombo.getSelectedItem().toString().trim().equals(today_year + "");
            boolean b2 = monthCombo.getSelectedItem().toString().trim().equals((today_month + 1) + "");
            if (b1 && b2) {
                pan_day[today_day - 1].setBorder(new LineBorder(Color.red, 2));
            } // 오늘날짜 서식 end

        } catch (Exception e) {
            System.out.println("데이터 오염");
        }
    }// refresh() end

    // ### order테이블에 값 뿌리는 메소드
    private void disp() {
        model.setRowCount(0); // 초기화
        System.out.println("db_call.vec150.size()");
        System.out.println(db_call.vec150.size());

        for (int j = 0; j < db_call.vec150.size(); j++) {
            String temp[] = { (db_call.vec150.get(j)), db_call.vec151.get(j), db_call.vec152.get(j) };
            model.addRow(temp);
        }
    }// disp() end

    // ### 주문 넣는 메소드
    private void order() {
        // 200|orderDate|count|name // insert orderlist (UserClient ONLY)
        String o_date = tfDate.getText().replaceAll("-", "");
        String o_count = tfSu.getText().trim();
        String o_name = tfName.getText().trim();

        db_call.queryString("200|" + o_date + "|" + o_count + "|" + o_name);
    }// order() end

    // ### 주문 취소 메소드
    private void order_cancel(int k) {// 주문 취소하는 메소드
        // 300|no // delete orderlist (AdminClient ONLY)
        db_call.queryString("300|" + k);
    } // order_cancel() end

    @Override
    public void actionPerformed(ActionEvent e) {
        // 현재 선택된 날짜 // 년 월 일 get
        int yy = (Integer) yearCombo.getSelectedItem();
        int mm = (Integer) monthCombo.getSelectedItem();
        int dd = Integer.parseInt(tfDate.getText().substring(8, 10));
        // 지난달로 이동 + 콤보박스 set
        if (e.getSource() == prevBtn) {
            System.out.println("!!! 지난달go");
            if (mm == 1) {
                yy--;
                mm = 12;
            } else {
                mm--;
            }
            // System.out.println(yy + " " + mm);
            yearCombo.setSelectedItem(yy);
            monthCombo.setSelectedItem(mm);
        }
        // 다음달로 이동 + 콤보박스 set
        if (e.getSource() == nextBtn) {
            System.out.println("!!!다음달go");
            if (mm == 12) {
                yy++;
                mm = 1;
            } else {
                mm++;
            }
            // System.out.println(yy + " " + mm);
            yearCombo.setSelectedItem(yy);
            monthCombo.setSelectedItem(mm);
        }
        // 콤보박스 변경 되면 >> 달력 패널 재배치
        if (e.getSource() == yearCombo || e.getSource() == monthCombo) {
            remove_cal();// 기존꺼 지우고
            cal_generate(yy, mm);
            refresh(yy, mm - 1, 1);// 데이터 다시 부르고
        }
        // Admin > 주문삭제 버튼, User > 주문입력 버튼
        if (e.getSource() == button1) {

            int a1 = tfName.getText().trim().length();
            int a2 = tfSu.getText().trim().length();
            System.out.println(a1 + " " + a2);
            // 이름 수량 둘다 값이 존재하는 경우에만 실행
            if (a1 > 0 && a2 > 0) {
                String confirmMsg = "주문일자 " + tfDate.getText() + "\n이름       " + tfName.getText() + "\n수량       "
                        + tfSu.getText() + "\n\n 취소하시겠습니까?";
                int yes_or_no = JOptionPane.showConfirmDialog(this, confirmMsg, "주문 취소 확인", JOptionPane.YES_NO_OPTION);
                if (yes_or_no == JOptionPane.YES_OPTION) {
                    int temp = Integer.parseInt((String) model.getValueAt(ordertable.getSelectedRow(), 0));
                    order_cancel(temp);
                    refresh(yy, mm - 1, dd);// 데이터 refresh
                    disp();
                    tfSu.setText("");
                    tfName.setText("");
                } else {
                    refresh(yy, mm - 1, dd);// 데이터 refresh
                    disp();
                    tfSu.setText("");
                    tfName.setText("");
                } // inner if else end
            } // if end
        }
        // Admin > 메뉴수정 버튼
        if (e.getSource() == button2) {
            // 500|insertDate|menu1|menu2|menu3|menu4|menu5 (AdminMenuClient ONLY)

            int yes_or_no = JOptionPane.showConfirmDialog(this, "메뉴를 수정하시겠습니까?", "메뉴 수정", JOptionPane.YES_NO_OPTION);

            if (yes_or_no == JOptionPane.YES_OPTION) {
                // menu1~5중에 중간이 비는 경우 바로잡기위해
                // vector를 거쳐간다
                Vector<String> temp_vec = new Vector<>();

                // int i2 = 0;
                for (int i = 0; i < 5; i++) {
                    String temp = tfMenu[i].getText().trim();

                    if (!tfMenu[i].getText().trim().isEmpty()) {
                        temp_vec.add(tfMenu[i].getText());
                    }
                }
                for (int i = 0; i < 5; i++) {
                    temp_vec.add(" ");
                }
                String tempDate = tfDate.getText().replaceAll("-", "");
                System.out.println(tempDate);

                String tempMenuString = "500|" + tempDate;

                for (int i = 0; i < 5; i++) {
                    tempMenuString = tempMenuString + "|" + temp_vec.get(i);
                }
                System.out.println(tempMenuString);

                db_call.queryString(tempMenuString);

                refresh(yy, mm - 1, dd);// 데이터 다시 부르고
            } else {
                refresh(yy, mm - 1, dd);// 데이터 다시 부르고
            }
        } // if (e.getSource() == button2) end
    } // actionPerformed end

    class MyMouse extends MouseAdapter {
        // extends MouseAdapter : 프레스 하나만 쓰려면 이쪽이 편하다
        // implements MouseListener : 모든 마우스이벤트 오버라이딩....코드가 길다
        public void mousePressed(MouseEvent me) {

            for (int i = 0; i < 31; i++) {
                if (me.getSource() == pan_day[i]) {

                    for (int k = 0; k < 5; k++) {
                        tfMenu[k].setText("");// 기존값 지우기
                    }
                    // 클릭한 날짜 서식변경
                    la_day[i].setBackground(new Color(254, 242, 242));
                    la_sum[i].setBackground(new Color(254, 242, 242));
                    la_menu[i].setBackground(new Color(250, 170, 170));
                    la_menu[i].setForeground(Color.white);

                    int cb_year = (Integer) yearCombo.getSelectedItem();
                    int cb_month = (Integer) monthCombo.getSelectedItem();
                    // System.out.println("니가 클릭한건 " + i);
                    refresh(cb_year, cb_month - 1, i + 1);

                    // tfDate에 날짜 넣기
                    String clickDate2 = ((cb_month + 100) + "").substring(1, 3);
                    String clickDate3 = ((i + 101) + "").substring(1, 3);
                    String clickDate = cb_year + "-" + clickDate2 + "-" + clickDate3;
                    tfDate.setText(clickDate);
                    tfName.setText("");
                    tfSu.setText("");

                    // tfMenu 1,2,3,4,5 >> 메뉴 넣어주기
                    String temp = "";

                    try {
                        temp = db_call.ht100.get(i);
                        // temp = db_call.arr100[i];
                        temp = temp.replaceAll("<html>", "");
                        temp = temp.replaceAll("</html>", "");
                        temp = temp.replaceAll("<span>", "");
                        temp = temp.replaceAll("</span>", "");
                        temp = temp.replaceAll("<br>", "|");
                        // System.out.println(temp);
                        StringTokenizer st = new StringTokenizer(temp, "|");
                        for (int j = 0; j < 5; j++) {
                            // tfMenu[j].setText("");// 기존값 지워주고
                            try { // 메뉴 5개 미만인경우 오류...
                                tfMenu[j].setText(st.nextToken());
                            } catch (Exception e) {
                                tfMenu[j].setText("");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("db_call.ht100.get(i); error");
                    }

                    disp();// 주문목록 새로고침
                } else {
                    la_day[i].setBackground(new Color(251, 216, 219));
                    la_sum[i].setBackground(new Color(251, 216, 219));
                    la_menu[i].setBackground(new Color(254, 242, 242));
                    la_menu[i].setForeground(Color.black);
                } // if end

            }
            // 주문목록 클릭 >> 위 textfield에 값 보내기
            if (me.getSource() == ordertable) {
                int row = ordertable.getSelectedRow();
                // System.out.println(">>>>>>>row : " + row);
                tfName.setText((String) ordertable.getValueAt(row, 1));
                tfSu.setText((String) ordertable.getValueAt(row, 2));
            }
        }// mousePressed end
    } // inner class MyMouse end

    // main
    public static void main(String[] args) {
        new AdminClient();
    }

}