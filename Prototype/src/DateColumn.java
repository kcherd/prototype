import java.time.LocalDate;

public class DateColumn extends Column{
    int year;
    int month;
    int day;

    DateColumn(){
        year = 1;
        month = 1;
        day = 1;
    }

    DateColumn(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    DateColumn(String date){
        int [] arrDate = fromString(date);
        year = arrDate[2];
        month = arrDate[1];
        day = arrDate[0];

    }

    public int getYear(){
        return year;
    }

    public int getMonth(){
        return month;
    }

    public int getDay(){
        return day;
    }

    public void setYear(int year){
        this.year = year;
    }

    public void setMonth(int month){
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public DateColumn clone() {
        return (DateColumn) super.clone();
    }

    @Override
    public boolean equals(Object object2) {
        if (!(object2 instanceof DateColumn)) {
            return false;
        } else{
            DateColumn col2 = (DateColumn) object2;
            return year == col2.year && month == col2.month && day == col2.day;
        }
    }

    @Override
    public Column fromStringToObject(String str) {
        int [] data = fromString(str);
        DateColumn col = new DateColumn(data[2], data[1], data[0]);
        return col.clone();
    }

    private int[] fromString(String str){
        if(str.matches("\\d\\d?\\.\\d\\d?\\.\\d+")){
            String [] splitString = str.split("\\.");
            int [] result = new int[3];
            result[0] = Integer.parseInt(splitString[0]);
            result[1] = Integer.parseInt(splitString[1]);
            result[2] = Integer.parseInt(splitString[2]);
            return result;
        } else{
            throw new IllegalArgumentException("Неверные параметры для создания объекта!");
        }
    }

    @Override
    public String toString() {
        return day + "." + month + "." + year;
    }

    @Override
    public Column addition(Column col2) {
        if (!(col2 instanceof DateColumn)) {
            return null;
        } else{
            DateColumn dateColumn = (DateColumn) col2;
            LocalDate date = LocalDate.of(year, month, day);
            date = date.plusYears(dateColumn.year);
            date = date.plusMonths(dateColumn.month);
            date = date.plusDays(dateColumn.day);

            DateColumn sum = new DateColumn(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
            return sum.clone();
        }
    }

    @Override
    public int compareTo(Object o) {
        DateColumn obj2 = (DateColumn) o;
        int compYear = Integer.compare(year, obj2.year);
        int compMonth = Integer.compare(month, obj2.month);
        int compDay = Integer.compare(day, obj2.day);

        if(compYear > 0){
            return 1;
        } else if(compYear < 0){
            return -1;
        } else {
            if(compMonth > 0){
                return 1;
            } else if(compMonth < 0){
                return -1;
            } else {
                return compDay;
            }
        }
    }
}
