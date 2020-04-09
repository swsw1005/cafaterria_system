package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Test0409 extends JFrame implements ActionListener {
    // 변수
    JPanel jMain;
    JTextField tfDate, tfSu, tfName;
    JTextArea taInfo;
    JLabel laDate, laSu, laName;
    JButton button1;

    String DRIVER = "com.mysql.jdbc.Driver";
    String URL = "jdbc:mysql://localhost:3306/mydb";
    String USER = "root";
    String PWD = "12345";

    Connection con = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    JPanel cal_main = new JPanel();
    JPanel topPane = new JPanel();
    JButton prevBtn = new JButton("◀");
    JButton nextBtn = new JButton("▶");

    JLabel yearLbl = new JLabel("년");
    JLabel monthLbl = new JLabel("월");

    JComboBox<Integer> yearCombo = new JComboBox<Integer>();
    DefaultComboBoxModel<Integer> yearModel = new DefaultComboBoxModel<Integer>();
    JComboBox<Integer> monthCombo = new JComboBox<Integer>();
    DefaultComboBoxModel<Integer> monthModel = new DefaultComboBoxModel<Integer>();

    // Center
    JPanel centerPane = new JPanel(new BorderLayout());
    JPanel titlePane = new JPanel(new GridLayout(1, 7));
    String titleStr[] = { "일", "월", "화", "수", "목", "금", "토" };
    JPanel datePane = new JPanel(new GridLayout(0, 7));

    Calendar now;
    int year, month, date;

    // Object data[][]=new Object[0][5];
    // String rows[]={"메뉴1","메뉴2","메뉴3","메뉴4","메뉴5"};

    // DefaultTableModel model=new DefaultTableModel(data,rows);
    // JTable table=new JTable(model);
    // JScrollPane scrollPane=new JScrollPane(table);

    // 생성자
    public Test0409() {

        cal_main = new JPanel();
        cal_main.setLayout(null);
        cal_main.setBounds(30, 150, 500, 500);
        cal_main.setBackground(Color.white);

        jMain = new JPanel();
        jMain.setLayout(null);
        jMain.setBounds(550, 150, 320, 500);
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
        // --
        getContentPane().setLayout(null);

        laDate.setBounds(50, 50, 50, 30);
        laSu.setBounds(50, 80, 50, 30);
        laName.setBounds(50, 110, 50, 30);

        tfDate.setBounds(110, 50, 160, 30);
        tfSu.setBounds(110, 80, 160, 30);
        tfName.setBounds(110, 110, 160, 30);

        button1.setBounds(50, 150, 220, 30);

        taInfo.setBounds(50, 200, 220, 250);
        // scrollPane.setBounds(50,200,220,100);

        topPane.setBounds(0, 0, 500, 50);
        centerPane.setBounds(0, 100, 500, 450);
        // titlePane.setBounds(0,100,500,50);\
        // datePane.setBounds(0,150,500,100);

        getContentPane().add(jMain);

        jMain.add(laDate);
        jMain.add(laSu);
        jMain.add(laName);
        jMain.add(tfDate);
        jMain.add(tfSu);
        jMain.add(tfName);
        jMain.add(button1);
        // jMain.add(scrollPane);
        jMain.add(taInfo);

        getContentPane().add(cal_main);
        // cal_main.add(topPane);
        // cal_main.add(centerPane);
        // cal_main.add(titlePane);
        // cal_main.add(datePane);
        cal_main.add(topPane);
        cal_main.add(centerPane);

        // 이벤트등록
        tfDate.addActionListener(this);
        tfSu.addActionListener(this);
        tfName.addActionListener(this);
        button1.addActionListener(this);

        // 캘린더이벤트
        prevBtn.addActionListener(this);
        nextBtn.addActionListener(this);
        yearCombo.addActionListener(this);
        monthCombo.addActionListener(this);

        // 캘린더
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 자원 해제 후 종료
        now = Calendar.getInstance(); // 현재 날짜
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        date = now.get(Calendar.DATE);

        topPane.add(prevBtn);

        for (int i = year - 100; i <= year + 50; i++) {
            yearModel.addElement(i);
        }

        yearCombo.setModel(yearModel);
        yearCombo.setSelectedItem(year + ""); // 현재 년도 선택
        topPane.add(yearCombo);
        topPane.add(yearLbl);

        for (int i = 1; i <= 12; i++) {
            monthModel.addElement(i);
        }

        monthCombo.setModel(monthModel);
        monthCombo.setSelectedItem(month); // 현재 월 선택
        topPane.add(monthCombo);
        topPane.add(monthLbl);
        topPane.add(nextBtn);

        topPane.setBackground(new Color(100, 200, 200));

        titlePane.setBackground(Color.white);
        for (int i = 0; i < titleStr.length; i++) {
            JLabel lbl = new JLabel(titleStr[i], JLabel.CENTER);
            if (i == 0) {
                lbl.setForeground(Color.red);
            } else if (i == 6) {
                lbl.setForeground(Color.blue);
            }
            titlePane.add(lbl);
        }
        centerPane.add(titlePane, "North");

        dayPrint(year, month);
        centerPane.add(datePane, "Center");
        // add(centerPane, "Center");

        // 창
        setSize(900, 800);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 드라이버 로딩
        try {
            Class.forName(DRIVER);
            System.out.println("드라이버 로딩 성공");
        } catch (ClassNotFoundException ex1) {
            System.out.println("드라이버 로딩 실패:" + ex1);
        } // catch end

        // DB연결
        try {
            con = DriverManager.getConnection(URL, USER, PWD); // DB연결
            System.out.println("DB 연결 성공");
            stmt = con.createStatement(); // Statemend 생성

        } catch (SQLException ex2) {
            System.out.println("DB 연결 실패:" + ex2);
        } // catch end

    }// cons end

    class MyMouse extends MouseAdapter {
        public void mousePressed(MouseEvent me) {

        }// mousePressed end
    }// MyMouse end

    // 메서드
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tfSu) {
            tfName.requestFocus();
        }

        // 수량 미입력시
        if (e.getSource() == button1) {
            String su = tfSu.getText().trim();
            if (su.equals("") || su.length() < 1) {
                JOptionPane.showMessageDialog(this, "수량을 입력하세요.");
                tfSu.requestFocus();
                return;
            } // if
        } // if
          // 주문자 미입력시
        if (e.getSource() == button1) {
            String name = tfName.getText().trim();
            if (name.equals("") || name.length() < 1) {
                JOptionPane.showMessageDialog(this, "주문자를 입력하세요.");
                tfName.requestFocus();
                return;
            } // if
        } // if
          // insert

        if (e.getSource() == button1)
            JOptionPane.showMessageDialog(this, "주문이 완료되었습니다.");

        Object obj = e.getSource();
        if (obj instanceof JButton) {
            JButton eventBtn = (JButton) obj;
            int yy = (Integer) yearCombo.getSelectedItem();
            int mm = (Integer) monthCombo.getSelectedItem();
            if (eventBtn.equals(prevBtn)) { // 전달
                if (mm == 1) {
                    yy--;
                    mm = 12;
                } else {
                    mm--;
                }
            } else if (eventBtn.equals(nextBtn)) { // 다음달
                if (mm == 12) {
                    yy++;
                    mm = 1;
                } else {
                    mm++;
                }
            }
            yearCombo.setSelectedItem(yy);
            monthCombo.setSelectedItem(mm);
        } else if (obj instanceof JComboBox) { // 콤보박스 이벤트 발생시
            createDayStart();
        }

    }// actionPerformed() end

    public void disp() {
        System.out.println("주문내용");
    }

    public void createDayStart() {
        datePane.setVisible(false); // 패널 숨기기
        datePane.removeAll(); // 날짜 출력한 라벨 지우기
        dayPrint((Integer) yearCombo.getSelectedItem(), (Integer) monthCombo.getSelectedItem());
        datePane.setVisible(true); // 패널 재출력
    }

    public void dayPrint(int y, int m) {

        Calendar cal = Calendar.getInstance();
        cal.set(y, m - 1, 1); // 출력할 첫날의 객체 만든다.
        int week = cal.get(Calendar.DAY_OF_WEEK); // 1일에 대한 요일 일요일 : 0
        int lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 그 달의 마지막 날
        for (int i = 1; i < week; i++) { // 날짜 출력 전까지의 공백 출력
            datePane.add(new JLabel(" "));
        }

        for (int i = 1; i <= lastDate; i++) {
            JLabel lbl = new JLabel(String.valueOf(i), JLabel.CENTER);
            cal.set(y, m - 1, i);
            int outWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (outWeek == 1) {
                lbl.setForeground(Color.red);
            } else if (outWeek == 7) {
                lbl.setForeground(Color.BLUE);
            }

            datePane.add(lbl);
        }
    }

    // main
    public static void main(String[] args) {
        new Test0409();
    }// main end

}// class end