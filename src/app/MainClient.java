package app;

// 맨처음 로그인 화면, 
// 1. 로그인하면 >>> AdminClient.java
// 2. 로그인 안하면 >>> UserClient.java

//  1 or 2  이동하면,  MainClient 는 setVisible(false);

import javax.swing.*;
import java.awt.event.*;

public class MainClient extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 6026359005066173137L;
    // 변수
    JTextField tf_id, tf_pw;
    JButton b_login, b_user;
    JLabel l_id, l_pw, title;

    UserClient uc;
    AdminClient ac;

    // 생성자
    public MainClient() {
        System.out.println("MainClient start");

        title = new JLabel("구내식당 관리");

        tf_id = new JTextField();
        tf_pw = new JTextField();

        b_login = new JButton("관리자 로그인");
        b_user = new JButton("사용자 로그인");

        l_id = new JLabel("ID", JLabel.RIGHT);
        l_pw = new JLabel("PWD", JLabel.RIGHT);

        getContentPane().setLayout(null); // 직접 디자인.....

        title.setBounds(50, 50, 100, 30);
        l_id.setBounds(50, 130, 70, 30);
        l_pw.setBounds(50, 170, 70, 30);
        tf_id.setBounds(130, 130, 120, 30);
        tf_pw.setBounds(130, 170, 120, 30);
        b_login.setBounds(130, 220, 90, 50);
        b_user.setBounds(230, 220, 90, 50);

        getContentPane().add(title);
        getContentPane().add(l_id);
        getContentPane().add(tf_id);
        getContentPane().add(l_pw);
        getContentPane().add(tf_pw);
        getContentPane().add(b_login);
        getContentPane().add(b_user);

        b_login.addActionListener(this);
        b_user.addActionListener(this);

        setTitle("경영기술개발원 구내식당 프로그램");
        setVisible(true);
        setResizable(false);
        setSize(500, 500);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // 메소드
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b_login) {
            System.out.println("b_login click");
            // 아이디 비번 검사하고
            // 맞으면?
            boolean check = true;

            if (check) {
                tf_id.setText("");
                tf_pw.setText("");
                ac = new AdminClient();// 관리자클라이언트 ㄱㄱ
            } else {
                JOptionPane.showMessageDialog(this, "계정과 비밀번호를 확인하십시오");
                tf_id.setText("");
                tf_pw.setText("");
            }
        }
        if (e.getSource() == b_user) {
            System.out.println("b_user click");
            tf_id.setText("");
            tf_pw.setText("");
            uc = new UserClient(); // 유저 클라이언트 ㄱㄱ
        }

    }

    // main
    public static void main(String[] args) {
        new MainClient();
    }

}