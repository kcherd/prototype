import java.time.LocalTime;

public class TimeColumn extends Column{
    int hours;
    int minutes;
    int seconds;

    TimeColumn(){
        hours = 0;
        minutes = 0;
        seconds = 0;
    }

    TimeColumn(int hours, int minutes, int seconds){
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    TimeColumn(String data){
        int [] arrData = fromString(data);
        hours = arrData[0];
        minutes = arrData[1];
        seconds = arrData[2];
    }

    public int getHours(){
        return hours;
    }

    public int getMinutes(){
        return minutes;
    }

    public int getSeconds(){
        return seconds;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public TimeColumn clone() {
        return (TimeColumn) super.clone();
    }

    @Override
    public boolean equals(Object object2) {
        if (!(object2 instanceof TimeColumn)) {
            return false;
        } else{
            TimeColumn col2 = (TimeColumn) object2;
            return hours == col2.hours && minutes == col2.minutes && seconds == col2.seconds;
        }
    }

    @Override
    public Column fromStringToObject(String str) {
        int [] data = fromString(str);
        TimeColumn col = new TimeColumn(data[0], data[1], data[2]);
        return col.clone();
    }

    private int[] fromString(String str){
        if(str.matches("\\d\\d?:\\d\\d?:\\d\\d?")){
            String [] splitString = str.split(":");
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
        return hours + ":" + minutes + ":" + seconds;
    }

    @Override
    public Column addition(Column col2) {
        if (!(col2 instanceof TimeColumn)) {
            return null;
        } else{
            TimeColumn timeColumn = (TimeColumn) col2;
            LocalTime time = LocalTime.of(hours, minutes, seconds);
            time = time.plusHours(timeColumn.hours);
            time = time.plusMinutes(timeColumn.minutes);
            time = time.plusSeconds(timeColumn.seconds);

            TimeColumn sum = new TimeColumn(time.getHour(), time.getMinute(), time.getSecond());
            return sum.clone();
        }
    }

    @Override
    public int compareTo(Object o) {
        TimeColumn obj2 = (TimeColumn) o;
        int compHours = Integer.compare(hours, obj2.hours);
        int compMinutes = Integer.compare(minutes, obj2.minutes);
        int compSeconds = Integer.compare(seconds, obj2.seconds);

        if(compHours > 0){
            return 1;
        } else if(compHours < 0){
            return -1;
        } else {
            if(compMinutes > 0){
                return 1;
            } else if(compMinutes < 0){
                return -1;
            } else {
                return compSeconds;
            }
        }
    }
}
