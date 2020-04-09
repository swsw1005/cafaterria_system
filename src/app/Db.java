package app;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Db {

    // 변수
    int pNum, y, m, d; // 수행할 프로토콜, 년, 월, 일

    // 100|startDate|endDate // select menu (1 month)
    // 130|startDate|endDate // select count(*) orderList( 1month)
    // 150|orderDate // select orderlist 1day (AdminClient ONLY)
    // 160|startDate|endDate|orderDate // 한달치 주문count + 특정일 주문내역
    // 200|orderDate|count|name // insert orderlist (UserClient ONLY)
    // 300|orderDate|name // delete orderlist (AdminClient ONLY)
    // 400|id|pwd // select adminlist

    // HashTable<Integer, Object> ht = new Hashtable<>()

    String queryString; // 실행할 쿼리

    String driver, url, user, pwd; // DB연결 기본 변수

    Connection con = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    String sql = "";
    ResultSet rs;
    // 값 담아서 나갈 변수
    Hashtable<Integer, String> ht100 = new Hashtable<>();// 100
    int[] temp_orderCnt = new int[31]; // 130
    Vector<Integer> vec150 = new Vector<>(); // 150
    Vector<String> vec151 = new Vector<>();// 150
    Hashtable<Integer, Object[]> ht1 = new Hashtable<>();
    String hwal;// 400

    // 메소드
    public void queryString(String protocol) {
        // ex) protocol = "100|2020|4|30|select * from member"

        // properties 에서 db 정보 읽어오기
        Properties pp = new Properties();

        try {
            pp.load(new FileInputStream("properties//DB.properties"));
        } catch (Exception ex) {
            System.out.println("!!!! properties 읽기 예외  " + ex);
        }

        driver = pp.getProperty("driver");
        url = pp.getProperty("url");
        user = pp.getProperty("user");
        pwd = pp.getProperty("pwd");

        StringTokenizer token = new StringTokenizer(protocol, "|");
        int pNum = Integer.parseInt(token.nextToken());

        // 1. 드라이버 확인
        try {
            Class.forName(driver);
            System.out.println("드라이버 OK");
        } catch (ClassNotFoundException ex) {
            System.out.println("!!! driver check fail");
        }

        try {
            // 2. 연결
            con = DriverManager.getConnection(url, user, pwd);
            System.out.println("연결  OK");

            // 3. sql 문 작성 : String으로 쿼리문 넣어준다.
            stmt = con.createStatement();

            System.out.println("pnum protocol..." + pNum);

            switch (pNum) {

                case 100: // select menu (1 month) // 100|startDate|endDate
                    System.out.println("100----");
                    String date100 = token.nextToken();
                    String date101 = token.nextToken();

                    date100 = date100.substring(0, 4) + "-" + date100.substring(4, 6) + "-" + date100.substring(6, 8);
                    date101 = date101.substring(0, 4) + "-" + date101.substring(4, 6) + "-" + date101.substring(6, 8);

                    sql = "select to_char(orderDate, 'dd') orderDate, menu1, menu2, menu3, menu4, menu5 from menu WHERE orderDate BETWEEN ' "
                            + date100 + "' AND '" + date101 + "'";

                    // select to_char(orderDate, 'yyyymmdd') orderDate,
                    // menu1, menu2, menu3,
                    // menu4, menu5 from menu
                    // WHERE orderDate BETWEEN TO_DATE('20200127') AND
                    // TO_DATE('20200201');

                    rs = stmt.executeQuery(sql);

                    if (rs != null) {
                        int i = 0;
                        while (rs.next()) {
                            // String temp = rs.getString("menu1");
                            // temp = temp + "\n" + rs.getString("menu2");
                            // temp = temp + "\n" + rs.getString("menu3");
                            // temp = temp + "\n" + rs.getString("menu4");
                            // temp = temp + "\n" + rs.getString("menu5");
                            String temp = "<html><span>" + rs.getString("menu1").trim();
                            temp = temp + "<br>" + rs.getString("menu2").trim();
                            temp = temp + "<br>" + rs.getString("menu3").trim();
                            temp = temp + "<br>" + rs.getString("menu4").trim();
                            temp = temp + "<br>" + rs.getString("menu5").trim();
                            temp = temp + "</span></html>";

                            ht100.put(i, temp);
                            i++;
                        }
                    }
                    break;

                case 130: // select count(*) orderlist 1month (AdminClient ONLY)
                    // 130|startDate|endDate

                    System.out.println("130----");
                    int date130 = Integer.parseInt(token.nextToken());
                    int date131 = Integer.parseInt(token.nextToken());
                    int d130131 = date131 - date130;

                    for (int i = 0; i < d130131; i++) {

                        String date_temp = (date130 + i) + "";

                        date_temp = date_temp.substring(0, 4) + "-" + date_temp.substring(4, 6) + "-"
                                + date_temp.substring(6, 8);

                        sql = "select sum(count) from orderlist where orderdate='" + date_temp + "'";
                        rs = stmt.executeQuery(sql);
                        if (rs != null) {
                            while (rs.next()) {
                                temp_orderCnt[i] = rs.getInt(1);
                                System.out.print(temp_orderCnt[i] + " ");
                            }
                        }
                    }

                    break;

                case 150: // select orderlist 1day (AdminClient ONLY)
                    // 150|orderDate
                    System.out.println("150----");

                    String date150 = token.nextToken();
                    date150 = date150.substring(0, 4) + "-" + date150.substring(4, 6) + "-" + date150.substring(6, 8);

                    sql = "select * from orderlist where orderDate = '" + date150 + "'";
                    System.out.println(sql);
                    rs = stmt.executeQuery(sql);
                    if (rs != null) {
                        while (rs.next()) {
                            // temp = rs.getString("menu1");
                            vec150.add(rs.getInt("count"));
                            vec151.add(rs.getString("name"));
                            System.out.printf("%d(%d) ", vec150.size(), vec151.size());
                        }
                    }
                    break;

                case 160: // 130 + 150
                    // 160|startDate|endDate|orderDate
                    // 한달치 주문count + 특정일 주문내역
                    System.out.println("160----");

                    int date160 = Integer.parseInt(token.nextToken());// startdate
                    int date161 = Integer.parseInt(token.nextToken());// enddate
                    String date162 = token.nextToken();// orderdate

                    System.out.println(date160);
                    System.out.println(date161);
                    System.out.println(date162);

                    int d160161 = date161 - date160;

                    for (int i = 0; i < d160161; i++) {

                        String date_temp = (date160 + i) + "";

                        date_temp = date_temp.substring(0, 4) + "-" + date_temp.substring(4, 6) + "-"
                                + date_temp.substring(6, 8);

                        sql = "select sum(count) from orderlist where orderdate='" + date_temp + "'";
                        rs = stmt.executeQuery(sql);
                        if (rs != null) {
                            while (rs.next()) {
                                temp_orderCnt[i] = rs.getInt(1);
                                System.out.print(temp_orderCnt[i] + " ");
                            }
                        }
                    }

                    date162 = date162.substring(0, 4) + "-" + date162.substring(4, 6) + "-" + date162.substring(6, 8);

                    sql = "select * from orderlist where orderDate = '" + date162 + "'";
                    System.out.println(sql);
                    rs = stmt.executeQuery(sql);
                    if (rs != null) {
                        while (rs.next()) {
                            // temp = rs.getString("menu1");
                            vec150.add(rs.getInt("count"));
                            vec151.add(rs.getString("name"));
                            System.out.printf("%d(%d) ", vec150.size(), vec151.size());
                        }
                    }

                    break;

                case 200: // insert orderlist
                    // 200|orderDate|count|name

                    String date200 = token.nextToken();
                    date200 = date200.substring(0, 4) + "-" + date200.substring(4, 6) + "-" + date200.substring(6, 8);
                    int count200 = Integer.parseInt(token.nextToken());
                    String text200 = token.nextToken();

                    // insert into orderlist values('2020-02-12',4,'박범수');
                    // stmt.executeUpdate(sql);

                    // sql = "insert into orderlist values(?,?,?)";
                    sql = " insert into orderlist values((select nvl(max(no), 0) + 1 from orderlist), '" + date200
                            + "', " + count200 + " ,'" + text200 + "')";

                    // sql = "insert into orderlist values( '" + date200 + "', " + count200 + " ,'"
                    // + text200 + "')";
                    System.out.println();
                    System.out.println(sql);
                    System.out.println();

                    stmt.executeUpdate(sql);

                    break;

                case 300: // delete orderlist (AdminClient ONLY)
                    // 300|no

                    int no300 = Integer.parseInt(token.nextToken());

                    sql = "delete orderlist where no='" + no300 + "'";

                    System.out.println();
                    System.out.println(sql);
                    System.out.println();

                    stmt.executeUpdate(sql);

                    break;

                case 400: // select state from adminlist where id = id and pw = pw;
                    // 400|id|pwd

                    String adminId = token.nextToken();
                    String adminPw = token.nextToken();

                    sql = "select state from adminlist where id='" + adminId + "' and pw='" + adminPw + "'";

                    System.out.println(sql);

                    rs = stmt.executeQuery(sql);

                    if (rs != null) {
                        while (rs.next()) {
                            String status = rs.getString("state");
                            System.out.println("status : " + status);
                        }
                    }

                    break;
                default:
                    break;
            }

            // 6. 실행된 결과 표시 --> HTML, CSS , JS
            if (rs != null) {
                while (rs.next()) {
                    String temp_id = rs.getString("id");
                    String temp_pwd = rs.getString("pw");
                    String temp_name = rs.getString("name");

                    System.out.println(temp_id + "\t" + temp_pwd + "\t" + temp_name);
                }
            }

        } catch (SQLException ex2) {
            // 예외처리
            System.out.println("실패");
            ex2.printStackTrace(); // 에러 스택 보여준다.
        } finally {
            // 예외가 발생 하든말든 무조건 실행되는 부분
            // 7. 닫기 -->> 오류가 나든 안나든 닫아야 한다.
            //// 자원절약
            if (con != null) {

                try {
                    con.close();
                    stmt.close();

                } catch (SQLException eSqlException) {
                    // sql 예외처리
                    System.out.println("7.DB 닫는 중 에러");
                    eSqlException.printStackTrace();// 에러스택
                } // end try catch

            } // end if
        } // end try cateh

    }// cons end

    // 메소드

}
// class end