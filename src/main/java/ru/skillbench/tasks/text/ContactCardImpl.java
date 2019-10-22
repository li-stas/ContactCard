package ru.skillbench.tasks.text;
import java.time.LocalDate;
import java.time.Period;
//import java.time.format.DateTimeFormatter;
import java.util.*;


/**
     * ЦЕЛИ ЗАДАЧИ:<ul>
     * <li>Научиться разбирать (parse) потоки InputStreams с помощью java.util.Scanner.</li>
     * <li>Узнать о работе с датами в java.time (рекомендуется) и java.util.Calendar.</li>
     * <li>Попрактиковаться в выбрасывании исключений пакета java.util</li>
     * </ul>
     * <p/>
     * ЗАДАНИЕ<br/>
     * Создавать экземпляры своего класса визитной карточки (класса, реализующего данный интерфейс)
     *  методами {@link #getInstance(Scanner)} и {@link #getInstance(String)}.<br/>
     * Большинство других методов должны просто возвращать считанные через {@link Scanner} значения,
     *   однако хранить ли их в отдельных полях класса или иным способом - на усмотрение разработчика.<br/>
     * Расчет возраста по дате рождения может проводиться в методе {@link #getAge()}
     *  - необязательно это делать сразу в методе getInstance().<br/>
     * У экземпляра класса {@link ContactCard}, созданного конструктором (а не методом getInstance()),
     *   не будут вызываться никакие методы, кроме getInstance()<br/>
     * <p/>
     * ПРИМЕЧАНИЕ:<br/>
     * Предложенный в задании формат электронной визитки ({@link #getInstance(Scanner)}) - это упрощенная версия
     * <a href="https://en.wikipedia.org/wiki/VCard">стандартного формата vCard</a>.<br/>
     * ДОПОЛНИТЕЛЬНОЕ ТРЕБОВАНИЕ: попробуйте написать парсинг так, чтобы метод getInstance() завершался без ошибки
     *   с любыми данными, удовлетворяющими формату vCard 3.0 или 4.0, за исключением требований к номеру телефона
     *   (в vCard могут быть не только указанные в задании поля, причем они могут идти в любом порядке).<br/>
     *
     * @author Alexey Evdokimov
     */
    public class ContactCardImpl implements ContactCard {

        private String FullName;
        private String Organization;
        private boolean Woman;
        private Calendar Birthday;
        private String Phone;

        public ContactCardImpl() {
        }

        /**
         * @param scanner Источник данных
         * @return {@link ContactCard}, созданный из этих данных
         * @throws InputMismatchException Возникает, если структура или значения данных не соответствуют формату,
         *   описанному выше; например, если после названия поля нет двоеточия или дата указана в ином формате
         *   или номер телефона содержит неверное число цифр.
         * @throws NoSuchElementException Возникает, если данные не содержат обязательных полей
         *   (FN, ORG, BEGIN:VCARD, END:VCARD)
        */
        public ContactCard getInstance(Scanner scanner) throws InputMismatchException, NoSuchElementException {
            String data = "";
            String cPhone = "";
            String cBirthday = "";
            String[] aContains = {"FN", "ORG", "BEGIN:VCARD", "END:VCARD"}; // проверочные слова
            boolean lContains = true;

            while (scanner.hasNext()) {
                data += scanner.nextLine() + "\n";
            }

            // проверка на входение обязательный слов
            for (String cContains: aContains) {
                lContains = data.contains(cContains);
                if (!lContains) {
                    break;
                }
            }
            if (lContains) {
                // первой строкой всегда идет BEGIN:VCARD, последней - END:VCARD
                if (!(data.startsWith("BEGIN:VCARD") & data.endsWith("END:VCARD\n"))) {
                    //System.out.println("//InputMismatchException");
                    lContains = false;
                    throw new InputMismatchException();
                }
            } else {
                //System.out.println( "NoSuchElementException" );
                throw new NoSuchElementException();
            }
            if (lContains) {
                Woman = data.contains("GENDER");

                for (String cField : data.split("\n")) {
                    //System.out.println("split " + cField); // + " " + cField.length() +" "+ "BEGIN:VCARD".length());
                    if (cField.startsWith("GENDER")) {

                        if (cField.split(":")[0].length() != "GENDER".length()) {
                            //System.out.println("//   InputMismatchException  - QUIT - после Ключа нет :");
                            throw new InputMismatchException();
                            //Woman = false;
                            //continue;
                        }
                        if (cField.split(":").length != 2) { // должно быть два элемнта
                             throw new InputMismatchException();
                            //Woman = false;
                            //continue;
                        }
                        String cM_or_F = cField.split(":")[1];
                        if (cM_or_F.length() > 1) { // длина вторго =1
                            throw new InputMismatchException();
                        }
                        if (cM_or_F.contains("M") || cM_or_F.contains("F")) { // вторго = M || F
                            Woman = cM_or_F.contains("F");
                        } else {
                            throw new InputMismatchException();
                        }

                    } else if (cField.startsWith("FN")) {
                        if (cField.split(":")[0].length() != "FN".length()) {
                            //System.out.println("//   InputMismatchException  - QUIT- после Ключа нет :");
                            throw new InputMismatchException();
                            //continue;
                        }
                        FullName = cField.split(":")[1];
                    } else if (cField.startsWith("ORG")) {
                        if (cField.split(":")[0].length() !=  "ORG".length()) {
                            //System.out.println("//   InputMismatchException  - QUIT- после Ключа нет :");
                            throw new InputMismatchException();
                            //continue;
                        }
                        Organization = cField.split(":")[1];
                    } else if (cField.startsWith("BDAY")) {
                        if (cField.split(":")[0].length() != "BDAY".length()) {
                            //System.out.println("//   InputMismatchException  - QUIT- после Ключа нет :");
                            throw new InputMismatchException();
                            //continue;
                        }
                        cBirthday = cField.split(":")[1];
                        if (cBirthday.length() == 10) { // "DD-MM-YYYY" -> len=10
                            String[] aBirthday  = cBirthday.split("-");
                            if (aBirthday.length == 3)  {
                                int nYYYY;
                                try {
                                    nYYYY = Integer.parseInt(aBirthday[3 - 1]);
                                } catch (NumberFormatException e) {
                                    nYYYY = 0;
                                }
                                int nMM;
                                try {
                                    nMM = Integer.parseInt(aBirthday[2 -  1]);
                                } catch (NumberFormatException e) {
                                    nMM = 0;
                                }
                                int nDD;
                                try {
                                    nDD = Integer.parseInt(aBirthday[1 - 1]);
                                } catch (NumberFormatException e) {
                                    nDD = 0;
                                }
                                if (nYYYY != 0 & nMM != 0 & nDD != 0) {
                                    Birthday = new GregorianCalendar(nYYYY, nMM - 1, nDD);
                                } else {
                                    //System.out.println("//   InputMismatchException  - QUIT- nYYYY != 0 & nMM != 0 & nDD != 0");
                                    throw new InputMismatchException();
                                }
                                //Birthday = new GregorianCalendar( nYYYY, nMM, nDD );
                            } else {
                                //System.out.println("//   InputMismatchException  - QUIT- к-во элем # 3");
                                throw new InputMismatchException();
                            }

                        } else {
                            //System.out.println("//   InputMismatchException  - QUIT- форммат даты len # 10");
                            throw new InputMismatchException();
                        }
                        // "DD-MM-YYYY"

                    }  else if (cField.startsWith("TEL")) {
                        //TEL;TYPE=WORK,VOICE:4951234567 cField
                        //                    0123456789
                        // "(123) 456-7890
                        String cType = cField.split("=")[2 - 1];
                        //System.out.println(cType);
                        cType = cType.split(":")[1 - 1]; // тип телефона
                        //System.out.println(cType);

                        String cNumTel = cField.split(":")[2 - 1];

                        long nNumTel; // все ли цифры
                        try {
                            nNumTel = Long.parseLong(cNumTel);
                        } catch (NumberFormatException e) {
                            nNumTel = -1;
                        }
                        //System.out.println("nNumTel "+nNumTel+" "+cNumTel);

                        if (cNumTel.length() == 10 & nNumTel != -1) {
                            String cNumTel_01_03 = cNumTel.substring(0, (0 + 3)); // c какой п-ции 0 и (0+3)->сколько знак
                            String cNumTel_04_05 = cNumTel.substring(3, (3 + 3));
                            String cNumTel_06_10 = cNumTel.substring(6, (6 + 4)); //String cNumTel_06_10 = cNumTel.substring(6));
                            //System.out.println(cType +" "+cNumTel_01_03+" "+cNumTel_04_03+" "+cNumTel_06_04);
                            cNumTel = "(" + cNumTel_01_03 + ") " + cNumTel_04_05 + "-" +  cNumTel_06_10;

                        } else {
                            //System.out.println("//   InputMismatchException  - QUIT- NumTel len # 10");
                            throw new InputMismatchException();
                        }
                        if (cPhone.length() != 0) {
                            cPhone += ";";
                        }
                        cPhone += cType + ":" + cNumTel;
                        //System.out.println(cPhone);
                        Phone = cPhone;
                    } //ENDIF

                } //NEXT

                /* System.out.println(
                        "App{" +
                                "Woman=" + Woman +
                                ", FullName='" + FullName + '\'' +
                                ", Organization='" + Organization + '\'' +
                                ", cBirthday='" + Birthday + '\'' +
                                '}'
                ); */

            } else {
                // "NoSuchElementException"
                throw new NoSuchElementException();
            } //ENDIF
            return this;
        }
        /**
         * Метод создает {@link Scanner} и вызывает {@link #getInstance(Scanner)}
         * @param data Данные для разбора, имеющие формат, описанный в {@link #getInstance(Scanner)}
         * @return {@link ContactCard}, созданный из этих данных
         */
        public ContactCard getInstance(String data) {
            Scanner scan = new Scanner(data);
            return getInstance(scan);
        }

        /**
         * @return Полное имя - значение vCard-поля FN: например, "Forrest Gump"
         */
        public String getFullName() {
            return FullName;
        }

        /**
         * @return Организация - значение vCard-поля ORG: например, "Bubba Gump Shrimp Co."
         */
        public String getOrganization() {
            return Organization;
        }

        /**
         * Если поле GENDER отсутствует в данных или равно "M", этот метод возвращает false
         * @return true если этот человек женского пола (GENDER:F)
         */
        public boolean isWoman() {
            return Woman;
        }

        /**
         * ПРИМЕЧАНИЕ: в современных приложениях рекомендуется для работы с датой применять java.time.LocalDate,
         *  однако такие классы как java.util.Calendar или java.util.Date необходимо знать.
         * @return День рождения человека в виде {@link Calendar}
         * @throws NoSuchElementException Если поле BDAY отсутствует в данных
         */
        public Calendar getBirthday() throws NoSuchElementException {
            if (Birthday == null) {
                throw new NoSuchElementException();
            }
            return Birthday;
        }

        /**
         * ПРИМЕЧАНИЕ: В реализации этого метода рекомендуется использовать {@link DateTimeFormatter}
         * @return Возраст человека на данный момент в виде {@link Period}
         * @throws NoSuchElementException Если поле BDAY отсутствует в данных
         */
        public Period getAge() throws NoSuchElementException {
            if (Birthday == null) {
                throw new NoSuchElementException();
            }
            LocalDate dBDay = LocalDate.of(
                     Birthday.get(Calendar.YEAR)
                    , Birthday.get(Calendar.MONTH)
                    , Birthday.get(Calendar.DAY_OF_MONTH));
            LocalDate dCurDay = LocalDate.of(
                     Calendar.getInstance().get(Calendar.YEAR)
                    , Calendar.getInstance().get(Calendar.MONTH)
                    , Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            return Period.between(dBDay, dCurDay);
        }

        /**
         * @return Возраст человека в годах: например, 74
         * @throws NoSuchElementException Если поле BDAY отсутствует в данных
         */
        public int getAgeYears() throws NoSuchElementException {
            if (Birthday == null) {
                throw new NoSuchElementException();
            }
            return this.getAge().getYears();
        }

        /**
         * Возвращает номер телефона в зависимости от типа.
         * @param type Тип телефона, который содержится в данных между строкой "TEL;TYPE=" и двоеточием
         * @return Номер телефона - значение vCard-поля TEL, приведенное к следующему виду: "(123) 456-7890"
         * @throws NoSuchElementException если в данных нет телефона указанного типа
         */
        public String getPhone(String type) throws NoSuchElementException {
            if (Phone.length() == 0) { // нет номеров
                throw new NoSuchElementException();
            }
            if (!Phone.contains(type)) { // нет того типа телефона
                throw new NoSuchElementException();
            }
            String[] aPhone = Phone.split(";");
            String cPhone = "";
            for (String cElem  : aPhone) {
                if (cElem.contains(type)) {
                    cPhone = cElem.split(":")[1];
                    break;
                }
            }
            return cPhone;
        }

    @Override
    public String toString() {
        return "ContactCardImpl{" +
                "FullName='" + FullName + '\'' +
                ", Organization='" + Organization + '\'' +
                ", Woman=" + Woman +
                ", Phone='" + Phone + '\'' +
                ", Birthday=" + Birthday +
                '}';
    }
}


