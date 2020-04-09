package app;

public class MenuOutPut {

    // 변수
    int y;
    int m;
    int d;
    String mString;
    String q; // 쿼리문

    // 생성자
    public MenuOutPut(int year, int month, int day, String menuString) {
        this.y = year;
        this.m = month;
        this.d = day;
        this.mString = menuString;

    }

    // 메소드
    public String mString() {
        return mString;
    }

}