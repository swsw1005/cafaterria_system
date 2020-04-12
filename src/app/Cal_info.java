package app;

import java.util.Calendar;

public class Cal_info {
    // 년, 월 입력받아서, void_day, max_day 내보내주는 클래스

    public int void_day; // 앞에 며칠 비냐?
    public int max_day; // 이번달 며칠이냐?

    public Cal_info(int year, int month) {

        Calendar temp_cal = Calendar.getInstance();
        temp_cal.set(year, month - 1, 1);

        void_day = temp_cal.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println("void_day " + void_day);

        temp_cal.set(year, month, 0);
        max_day = temp_cal.get(Calendar.DATE);
        System.out.println("max_day " + max_day);
    }
}