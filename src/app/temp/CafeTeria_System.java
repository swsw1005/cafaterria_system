package app.temp;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class CafeTeria_System extends JFrame {
    // 변수
    JPanel pDate = new JPanel();
    JPanel pUp = new JPanel();
    JPanel pText = new JPanel();

    JButton btnPrevMon = new JButton("◀");
    JLabel lblYearMon = new JLabel();
    JButton btnNextMon = new JButton("▶");

    JButton[] btnArr = new JButton[42];

    Calendar curMon = Calendar.getInstance();

    // 생성자
    CafeTeria_System() {
        setTitle("Menu Calendar");

        pUp.setBackground(Color.yellow);
        pUp.setLayout(new FlowLayout(FlowLayout.CENTER));
        pUp.add(btnPrevMon);
        pUp.add(lblYearMon);
        pUp.add(btnNextMon);

        pDate.setLayout(new GridLayout(6, 7)); // 6행 7열
        for (int i = 0; i < btnArr.length; i++) {
            btnArr[i] = new JButton("");
            pDate.add(btnArr[i]);
        }

        pDate.setBackground(Color.red);
        pText.setBackground(Color.CYAN);
        // pText.setLayout(new BorderLayout(BorderLayout.EAST));

        btnPrevMon.addActionListener(new BtnEventHandler());
        btnNextMon.addActionListener(new BtnEventHandler());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent we) {
                we.getWindow().setVisible(false);
                we.getWindow().dispose();
                System.exit(0);
            }// windowClosing() end
        }); // addWindowListener() end

        add(pUp, "North");
        add(pDate, "Center");
        add(pText, "East");
        setBounds(80, 80, 1800, 900);
        pDate.setBounds(0, 80, 200, 150);
        setDays(curMon);
        setVisible(true);
    }// cons end

    // 메서드
    void setDays(Calendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);

        lblYearMon.setText(year + "년" + (month + 1) + "월");

        Calendar sDay = Calendar.getInstance(); // 시작일

        sDay.set(year, month, 1);
        sDay.add(Calendar.DATE, -sDay.get(Calendar.DAY_OF_WEEK) + 1);

        for (int i = 0; i < btnArr.length; i++, sDay.add(Calendar.DATE, 1)) {
            int day = sDay.get(Calendar.DATE);
            btnArr[i].setText(day + "");

            if (sDay.get(Calendar.MONTH) != month) {
                btnArr[i].setForeground(Color.lightGray);
            } else {
                btnArr[i].setBackground(Color.white);
            } // else end
        } // for end
    }// setDays() end

    // inner class
    class BtnEventHandler implements ActionListener {
        public void actionPerformed(final ActionEvent e) {
            final JButton src = (JButton) e.getSource();

            if (src == btnPrevMon) {
                curMon.add(Calendar.MONDAY, -1);
            } else if (src == btnNextMon) {
                curMon.add(Calendar.MONTH, 1);
            } // if end
            setDays(curMon);
            repaint();
        }// actionPerformed() end
    }// inner class end

    // main
    public static void main(final String[] args) {
        final CafeTeria_System cs = new CafeTeria_System();
    }

}// class end