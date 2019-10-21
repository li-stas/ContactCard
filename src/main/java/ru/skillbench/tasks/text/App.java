package ru.skillbench.tasks.text;
import java.util.Calendar;
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
        Scanner scan = new Scanner("BEGIN:VCARD\r\n"
                +"FN:Forrest Gump\r\n"
                +"ORG:Bubba Gump Shrimp Co.\r\n"
                +"BDAY:06-06-1944\r\n"
                +"TEL;TYPE=WORK,VOICE:4951234567\r\n"
                +"TEL;TYPE=CELL,VOICE:9150123456\r\n"
                +"END:VCARD\r\n" );
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
            if (lContains == false) { //!lContains
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

        if (lContains) {

            boolean Woman = data.contains("GENDER");
            String FullName = "";
            String Organization = "";
            String cBirthday = "";
            Calendar Birthday;

            for (String cField : data.split("\n")) {
                System.out.println("split " + cField); // + " " + cField.length() +" "+ "BEGIN:VCARD".length());

                if (cField.startsWith("BEGIN:VCARD") || cField.endsWith("END:VCARD")) {
                    System.out.println("  ?????:VCARD");
                } else {
                    //System.out.println(cField.startsWith("FN") +" "+"FN");
                    if (cField.startsWith("GENDER")) {

                        if ( cField.split(":")[0].length() != "GENDER".length() ) {
                            System.out.println("//   InputMismatchException  - завершить программу");
                        }
                        Woman = cField.split(":")[1].contains("F");

                    } else if (cField.startsWith("FN")) {
                        if (cField.split(":")[0].length() != "FN".length() ) {
                            System.out.println("//   InputMismatchException  - завершить программу");
                        }
                        FullName = cField.split(":")[1];
                    } else if (cField.startsWith("ORG")) {
                        if ( cField.split(":")[0].length() !=  "ORG".length() ) {
                            System.out.println("//   InputMismatchException  - завершить программу");
                        }
                        Organization = cField.split(":")[1];
                    } else if (cField.startsWith("BDAY")) {
                        if (cField.split(":")[0].length() != "BDAY".length() ) {
                            System.out.println("//   InputMismatchException  - завершить программу");
                        }
                        cBirthday = cField.split(":")[1];
                        //"DD-MM-YYYY"

                    }  else if (cField.startsWith("TEL")) {

                    }//ENDIF
                } //ENDIF
            } //NEXT

            System.out.println(
             "App{" +
                    "Woman=" + Woman +
                    ", FullName='" + FullName + '\'' +
                    ", Organization='" + Organization + '\'' +
                    ", cBirthday='" + cBirthday + '\'' +
                    '}'
              );

        } else {
            // "NoSuchElementException"
        } //ENDIF

    }


}
