package app;

import java.awt.Color;
import java.awt.Font;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class T2 extends JFrame implements ActionListener {
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

    // 생성자
    public T2() {
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

        // 이벤트등록
        tfDate.addActionListener(this);
        tfSu.addActionListener(this);
        tfName.addActionListener(this);
        button1.addActionListener(this);
        // b1.addMouseListener(new MyMouse());

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

    }// actionPerformed() end

    public void disp() {
        System.out.println("주문내용");
    }

    // main
    public static void main(String[] args) {
        new T2();
    }// main end

}// class end