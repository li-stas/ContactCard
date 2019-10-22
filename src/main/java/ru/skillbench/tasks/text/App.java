package ru.skillbench.tasks.text;
import java.time.Period;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String cScan =  "BEGIN:VCARD\r\n"
                        +"FN:Forrest Gump\r\n"
                        +"ORG:Bubba Gump Shrimp Co.\r\n"
                        +"BDAY:24-11-1991\r\n"
                        +"GENDER:M\r\n"
                        +"TEL;TYPE=WORK,VOICE:4951234567\r\n"
                        +"TEL;TYPE=CELL,VOICE:9150123456\r\n"
                        +"END:VCARD\r\n" ;

        Scanner scan = new Scanner( cScan );

        ContactCard oVCard = new ContactCardImpl();
        // oVCard.getInstance(scan);
        oVCard.getInstance(cScan);
        System.out.println(oVCard.getPhone("WORK,VOICE"));
        System.out.println(oVCard.getAgeYears());
        System.out.println(oVCard.isWoman());
        System.out.println( oVCard );

        Period period = oVCard.getAge();
        System.out.println(period);
        System.out.print(period.getYears() + " years,");
        System.out.print(period.getMonths() + " months,");
        System.out.print(period.getDays() + " days");
        //expected:<P27Y10M28D> but was:<P27Y9M29D>
        /*
        java.lang.AssertionError: getBirthday() failed with this text:
FN:Андрей Петров
ORG:Фриланс
BDAY:24-11-1991
 expected:<java.util.GregorianCalendar[time=690930000000, areFieldsSet=true, areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="GMT+03:00",offset=10800000,dstSavings=0,useDaylight=false,transitions=0,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=1991,MONTH=11,WEEK_OF_YEAR=48,WEEK_OF_MONTH=5,DAY_OF_MONTH=24,DAY_OF_YEAR=328,DAY_OF_WEEK=1,DAY_OF_WEEK_IN_MONTH=4,AM_PM=0,HOUR=0,HOUR_OF_DAY=0,MINUTE=0,SECOND=0,MILLISECOND=0,ZONE_OFFSET=10800000,DST_OFFSET=0]>
  but was:<java.util.GregorianCalendar[time=           ?,areFieldsSet=false,areAllFieldsSet=false,lenient=true,zone=sun.util.calendar.ZoneInfo[id="GMT+03:00",offset=10800000,dstSavings=0,useDaylight=false,transitions=0,lastRule=null],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=?,YEAR=1991,MONTH=11, WEEK_OF_YEAR=?,WEEK_OF_MONTH=?,DAY_OF_MONTH=24,DAY_OF_YEAR=?,DAY_OF_WEEK=?,DAY_OF_WEEK_IN_MONTH=?,AM_PM=0,HOUR=0,HOUR_OF_DAY=0,MINUTE=0,SECOND=0,MILLISECOND=?,ZONE_OFFSET=?,DST_OFFSET=?]>
           java.util.GregorianCalendar[time=690933600000,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="Europe/Kiev",offset=7200000,dstSavings=3600000,useDaylight=true,transitions=121,lastRule=java.util.SimpleTimeZone[id=Europe/Kiev,offset=7200000,dstSavings=3600000,useDaylight=true,startYear=0,startMode=2,startMonth=2,startDay=-1,startDayOfWeek=1,startTime=3600000,startTimeMode=2,endMode=2,endMonth=9,endDay=-1,endDayOfWeek=1,endTime=3600000,endTimeMode=2]],firstDayOfWeek=2,minimalDaysInFirstWeek=4,ERA=1,YEAR=1991,MONTH=10,WEEK_OF_YEAR=47,WEEK_OF_MONTH=3,DAY_OF_MONTH=24,DAY_OF_YEAR=328,DAY_OF_WEEK=1,DAY_OF_WEEK_IN_MONTH=4,AM_PM=0,HOUR=0,HOUR_OF_DAY=0,MINUTE=0,SECOND=0,MILLISECOND=0,ZONE_OFFSET=7200000,DST_OFFSET=0]}

         */

        /*

        String data = "";
        String [] aContains = {"FN", "ORG", "BEGIN:VCARD", "END:VCARD"}; // проверочные слова
        boolean lContains = true;

        //String cField ="";
        // считали со СКАНА
        while (scan.hasNext()) {
            data += scan.nextLine() + "\n";
        }
        //System.out.println( data );

        // проверка на входение слов
        for (String cContains: aContains ) {
            lContains = data.contains(cContains);
            if (! lContains) {
                break;
            }
        }
        if ( lContains) {
            // первой строкой всегда идет BEGIN:VCARD, последней - END:VCARD
            if ( !(data.startsWith("BEGIN:VCARD") & data.endsWith("END:VCARD\n")) ) {
                System.out.println("//InputMismatchException");
                lContains = false;
            }
        } else {
            System.out.println( "NoSuchElementException" );
        }

        System.out.println( lContains ? "ok": "NoSuchElementException" );

        boolean Woman;
        String FullName = "";
        String Organization = "";
        Calendar Birthday = new GregorianCalendar();
        String cPhone = "";
        String cBirthday = "";

        if (lContains) {
            Woman = data.contains("GENDER");

            for (String cField : data.split("\n")) {
                System.out.println("split " + cField); // + " " + cField.length() +" "+ "BEGIN:VCARD".length());

                                    //System.out.println(cField.startsWith("FN") +" "+"FN");
                    if (cField.startsWith("GENDER")) {

                        if ( cField.split(":")[0].length() != "GENDER".length() ) {
                            System.out.println("//   InputMismatchException  - QUIT - после Ключа нет :");
                            continue;
                        }
                        Woman = cField.split(":")[1].contains("F");

                    } else if (cField.startsWith("FN")) {
                        if (cField.split(":")[0].length() != "FN".length() ) {
                            System.out.println("//   InputMismatchException  - QUIT- после Ключа нет :");
                            continue;
                        }
                        FullName = cField.split(":")[1];
                    } else if (cField.startsWith("ORG")) {
                        if ( cField.split(":")[0].length() !=  "ORG".length() ) {
                            System.out.println("//   InputMismatchException  - QUIT- после Ключа нет :");
                            continue;
                        }
                        Organization = cField.split(":")[1];
                    } else if (cField.startsWith("BDAY")) {
                        if (cField.split(":")[0].length() != "BDAY".length() ) {
                            System.out.println("//   InputMismatchException  - QUIT- после Ключа нет :");
                            continue;
                        }
                        cBirthday = cField.split(":")[1];
                        if (cBirthday.length() == 10 ) { // "DD-MM-YYYY" -> len=10  
                            String aBirthday[] = cBirthday.split("-");
                            if ( aBirthday.length == 3)  {
                                int nYYYY ;
                                try { nYYYY = Integer.parseInt(aBirthday[3-1]); }
                                catch ( NumberFormatException e) { nYYYY = 0; }
                                int nMM ;
                                try { nMM = Integer.parseInt(aBirthday[2-1]); }
                                catch ( NumberFormatException e) { nMM = 0; }
                                int nDD ;
                                try { nDD = Integer.parseInt(aBirthday[1-1]); }
                                catch ( NumberFormatException e) { nDD = 0; }
                                if ( nYYYY != 0 & nMM != 0 & nDD != 0 ) {
                                    Birthday = new GregorianCalendar( nYYYY, nMM - 1, nDD );
                                } else {
                                    System.out.println("//   InputMismatchException  - QUIT- nYYYY != 0 & nMM != 0 & nDD != 0");
                                }
                                    Birthday = new GregorianCalendar( nYYYY, nMM, nDD );
                            } else {
                                System.out.println("//   InputMismatchException  - QUIT- к-во элем # 3");
                            }

                        } else {
                            System.out.println("//   InputMismatchException  - QUIT- форммат даты len # 10");
                        }
                        // "DD-MM-YYYY"

                    }  else if (cField.startsWith("TEL")) {
                        //TEL;TYPE=WORK,VOICE:4951234567 cField
                        //                    0123456789
                        // "(123) 456-7890
                        String cType = cField.split("=")[2-1];
                        //System.out.println(cType);
                        cType = cType.split(":")[1-1]; // тип телефона
                        //System.out.println(cType);

                        String cNumTel = cField.split(":")[2-1];
                        if ( cNumTel.length() == 10) {
                            String cNumTel_01_03 = cNumTel.substring(0,(0+3)); // c какой п-ции 0 и (0+3)->сколько знак
                            String cNumTel_04_05 = cNumTel.substring(3,(3+3));
                            String cNumTel_06_10 = cNumTel.substring(6,(6+4)); //String cNumTel_06_10 = cNumTel.substring(6));
                            //System.out.println(cType +" "+cNumTel_01_03+" "+cNumTel_04_03+" "+cNumTel_06_04);
                            cNumTel = "(" + cNumTel_01_03 + ") " + cNumTel_04_05 + "-" +  cNumTel_06_10;

                        } else {
                            System.out.println("//   InputMismatchException  - QUIT- NumTel len # 10");
                        }
                        if (cPhone.length() != 0  ) {
                            cPhone += ";";
                        }
                        cPhone += cType +":"+cNumTel;
                        System.out.println(cPhone);
                    }//ENDIF

            } //NEXT

            System.out.println(
             "App{" +
                    "Woman=" + Woman +
                    ", FullName='" + FullName + '\'' +
                    ", Organization='" + Organization + '\'' +
                    ", cBirthday='" + Birthday + '\'' +
                    '}'
              );

        } else {
            // "NoSuchElementException"
        } //ENDIF
      */
    }


}
