package ru.kenguru.driverassistant.others;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateController {

    private DateFormat dateFormat;

    public DateController() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("ru"));
    }

    public String format(Date date) {
        if (date != null) return dateFormat.format(date);
        else return "";
    }

    public Date parseString(String string) {
        Date date = null;
        if (string != null) {
            try {
                date = dateFormat.parse(string);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public long getDifDate(Date date1, Date date2) {
        return date1.getTime() - date2.getTime();
    }

    public String parseDifTime(long dif) {
        String strTime = "";
        long days = dif / 1000 / 60 / 60 / 24;
        if (days > 0) {
            dif -= days * 24 * 60 * 60 * 1000;
        }
        long hours = dif / 1000 / 60 / 60;
        if (hours > 0) {
            dif -= hours * 60 * 60 * 1000;
        }
        long minutes = dif / 1000 / 60;

        if (days > 0) strTime += days + " Дней ";
        strTime += hours + " Часов ";
        strTime += minutes + " Минут";

        return strTime;
    }

    public String parseNumberDate(long number) {
        if (number < 10) return "0" + number;
        else return "" + number;
    }
}
