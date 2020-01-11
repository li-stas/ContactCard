package ru.skillbench.tasks.text;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class test_app {
    public static void main( String[] args ) {
        System.out.println("Hello World!");
        String cScan = "BEGIN:VCARD\r\n"
                + "FN:Forrest Gump\r\n"
                + "ORG:Bubba Gump Shrimp Co.\r\n"
                + "BDAY:24-11-1991\r\n"
                + "GENDER:M\r\n"
                + "TEL;TYPE=WORK,VOICE:4951234567\r\n"
                + "TEL;TYPE=CELL,VOICE:9150123456\r\n"
                + "END:VCARD\r\n";

        Scanner scan = new Scanner(cScan);

        ContactCard oVCard = new ContactCardImpl();
        // oVCard.getInstance(scan);
        oVCard.getInstance(cScan);
        System.out.println(oVCard.getPhone("WORK,VOICE"));
        System.out.println(oVCard.getAgeYears());
        System.out.println(oVCard.isWoman());
        System.out.println(oVCard);

        Period period = oVCard.getAge();
        System.out.println(period);
        System.out.print(period.getYears() + " years,");
        System.out.print(period.getMonths() + " months,");
        System.out.println(period.getDays() + " days");

        // 24-11-1991
        Calendar Birthday = new GregorianCalendar(1991, 11 - 1, 24);
        LocalDate dBDay = LocalDate.of(
                Birthday.get(Calendar.YEAR)
                , Birthday.get(Calendar.MONTH) + 1
                , Birthday.get(Calendar.DAY_OF_MONTH));
        LocalDate dCurDay = LocalDate.of(
                Calendar.getInstance().get(Calendar.YEAR)
                , Calendar.getInstance().get(Calendar.MONTH) + 1
                , Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        //LocalDate dCurDay = LocalDate.now();
        Period period1 = Period.between(dBDay, dCurDay);
        System.out.println(period1);
        System.out.print(period1.getYears() + " years,");
        System.out.print(period1.getMonths() + " months,");
        System.out.print(period1.getDays() + " days");
    }
}
