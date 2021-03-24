public class GPSColumn extends Column{
    double latitude;
    double longitude;

    GPSColumn(){
        latitude = 0;
        longitude = 0;
    }

    GPSColumn(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    GPSColumn(String data){
        double [] arrData = fromString(data);
        latitude = arrData[0];
        longitude = arrData[1];
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public GPSColumn clone() {
        return (GPSColumn) super.clone();
    }

    @Override
    public boolean equals(Object object2) {
        if (!(object2 instanceof GPSColumn)) {
            return false;
        } else{
            GPSColumn col2 = (GPSColumn) object2;
            return latitude == col2.latitude && longitude == col2.longitude;
        }
    }

    @Override
    public Column fromStringToObject(String str) {
        double [] data = fromString(str);
        GPSColumn col = new GPSColumn(data[0], data[1]);
        return col.clone();
    }

    private double[] fromString(String str){
        if(str.matches("\\d+\\.\\d+;\\d+\\.\\d+")){
            String [] splitString = str.split(";");
            double [] result = new double[2];
            result[0] = Double.parseDouble(splitString[0]);
            result[1] = Double.parseDouble(splitString[1]);
            return result;
        } else{
            throw new IllegalArgumentException("Неверные параметры для создания объекта!");
        }
    }

    @Override
    public String toString() {
        return latitude + ";" + longitude;
    }

    @Override
    public Column addition(Column col2) {
        if (!(col2 instanceof GPSColumn)) {
            return null;
        } else{
            GPSColumn gpsColumn = (GPSColumn) col2;
            GPSColumn sum = new GPSColumn(latitude + gpsColumn.latitude, longitude + gpsColumn.longitude);
            return sum.clone();
        }
    }

    @Override
    public int compareTo(Object o) {
        GPSColumn obj2 = (GPSColumn) o;
        int compLat = Double.compare(latitude, obj2.latitude);
        int compLong = Double.compare(longitude, obj2.longitude);

        if(compLat > 0){
            return 1;
        } else if(compLat < 0){
            return -1;
        } else{
            return compLong;
        }
    }
}
