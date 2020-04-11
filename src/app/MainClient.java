package app;

// 맨처음 로그인 화면, 
// 1. 로그인하면 >>> AdminClient.java
// 2. 로그인 안하면 >>> UserClient.java

//  1 or 2  이동하면,  MainClient 는 setVisible(false);
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

public class MainClient extends JFrame implements ActionListener {

    private static final long serialVersionUID = 6026359005066173137L;

    // 변수
    JLabel laTitle, laId, laPwd, laComment;
    JTextField tfId;
    JPasswordField pfPwd;
    JButton buSys, buUser;
    JPanel panel;

    // java에서 제공한 인터페이스
    Connection con = null; // Db연결
    Statement stmt = null; // SQL문 실행
    PreparedStatement pstmt = null; // SQL문 실행
    ResultSet rs = null; // select 결과를 받아올곳

    // 생성자
    public MainClient() {

        // DB연동작업
        Properties pp = new Properties();

        try {
            pp.load(new FileInputStream("properties//DB.properties"));
        } catch (Exception ex) {
            System.out.println("!!!! properties 읽기 예외  " + ex);
        }

        String driver = pp.getProperty("driver");
        String url = pp.getProperty("url");
        String user = pp.getProperty("user");
        String pwd = pp.getProperty("pwd");

        setTitle("Login");
        getContentPane().setBackground(Color.white);

        laTitle = new JLabel("로그인", JLabel.CENTER);
        laTitle.setFont(new Font("Dialog", Font.BOLD, 30));
        laTitle.setForeground(Color.black);
        laId = new JLabel("관리자 ID", JLabel.RIGHT);
        laId.setFont(new Font("Dialog", Font.BOLD, 16));
        laId.setForeground(Color.black);
        laPwd = new JLabel("비밀번호", JLabel.RIGHT);
        laPwd.setFont(new Font("Dialog", Font.BOLD, 16));
        laPwd.setForeground(Color.black);
        // laComment = new JLabel("일반회원은 클릭!", JLabel.CENTER);
        // laComment.setFont(new Font("Dialog", Font.BOLD, 16));
        // laComment.setForeground(Color.red);

        tfId = new JTextField();
        tfId.setFont(new Font("Dialog", Font.BOLD, 18));
        pfPwd = new JPasswordField();
        pfPwd.setFont(new Font("Dialog", Font.BOLD, 18));

        buSys = new JButton("관리자 로그인");
        buSys.setFont(new Font("Dialog", Font.BOLD, 16));
        buSys.setBackground(Color.blue);
        buSys.setForeground(Color.white);
        buUser = new JButton("비회원 로그인");
        buUser.setFont(new Font("Dialog", Font.BOLD, 16));
        buUser.setBackground(Color.blue);
        buUser.setForeground(Color.white);

        panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);

        getContentPane().setLayout(null);

        laTitle.setBounds(220, 10, 100, 50);
        laId.setBounds(40, 70, 80, 50);
        laPwd.setBounds(40, 130, 80, 50);
        // laComment.setBounds(140, 300, 300, 40);

        tfId.setBounds(140, 70, 300, 50);
        pfPwd.setBounds(140, 130, 300, 50);

        buSys.setBounds(140, 190, 300, 50);
        buUser.setBounds(140, 250, 300, 50);

        getContentPane().add(laTitle);
        getContentPane().add(laId);
        getContentPane().add(laPwd);
        // getContentPane().add(laComment);

        getContentPane().add(tfId);
        getContentPane().add(pfPwd);

        getContentPane().add(buSys);
        getContentPane().add(buUser);

        // 이벤트 등록
        buUser.addActionListener(this);
        buSys.addActionListener(this);

        tfId.addActionListener(this);
        tfId.addActionListener(this);

        setBounds(800, 300, 550, 380);
        setVisible(true);

        // 프로그램종료
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 드라이버 로딩
        try {
            Class.forName(driver);
            System.out.println("드라이버 로딩 성공");
        } catch (ClassNotFoundException ex1) {
            System.out.println("드라이버 로딩 실패: " + ex1);
        } // catch end

        // DB연결
        try {
            con = DriverManager.getConnection(url, user, pwd); // DB연결
            System.out.println("DB연결 성공");
            stmt = con.createStatement();// Statement 생성
        } catch (SQLException ex2) {
            System.out.println("DB연결 실패: " + ex2);
        } // catch end

    }

    public void actionPerformed(ActionEvent e) {
        //
        if (e.getSource() == buSys) {
            String id = tfId.getText().trim();
            String pwd = pfPwd.getText().trim();
            if (id.equals("") || id.length() < 1) {
                JOptionPane.showMessageDialog(this, "ID를 입력하세요");
                tfId.requestFocus();// 포커스 설정
                return;
            } // inner if end

            if (pwd.equals("") || pwd.length() < 1) {
                JOptionPane.showMessageDialog(this, "암호를 입력하세요");
                pfPwd.requestFocus();// 포커스 설정
                return;
            } // inner if end

            String adminId = id;
            String adminPw = pwd;
            String status = "";// "활동" 쓰여질곳

            String sql = "select state from adminlist where id='" + adminId + "' and pw='" + adminPw + "'";

            System.out.println(sql);

            System.out.println("status : " + status);

            try {
                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    status = rs.getString("state").trim();
                    System.out.println("status : " + status);

                } // while end
                rs.close();

                if (status.equals("활동")) {
                    JOptionPane.showMessageDialog(this, "관리자 로그인 성공 !");
                    setVisible(false);
                    tfId.setText("");
                    pfPwd.setText("");
                    new AdminClient();
                } else {
                    JOptionPane.showMessageDialog(this, "아이디/비밀번호를 확인하세요 !");
                    // tf 2개 지우기
                    tfId.setText("");
                    pfPwd.setText("");
                    return;
                }

            } catch (Exception ex) {
                System.out.println("disp() 예외: " + ex);
            } // catch end

        }

        if (e.getSource() == buUser) {
            JOptionPane.showMessageDialog(this, "환영합니다 !");
            setVisible(false);
            new UserClient();

        }

    }

    public static void main(String[] args) {
        new MainClient();
    }
}