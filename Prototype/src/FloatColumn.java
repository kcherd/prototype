public class FloatColumn extends Column {
    float data;

    FloatColumn(){
        data = 0;
    }

    FloatColumn(float data){
        this.data = data;
    }

    FloatColumn(String data){
        this.data = fromString(data);
    }

    public float getData() {
        return data;
    }

    public void setData(float data){
        this.data = data;
    }

    @Override
    public FloatColumn clone() {
        return (FloatColumn) super.clone();
    }

    @Override
    public boolean equals(Object object2) {
        if (!(object2 instanceof FloatColumn)) {
            return false;
        } else{
            FloatColumn col2 = (FloatColumn) object2;
            return data == col2.data;
        }
    }

    @Override
    public Column fromStringToObject(String str) {
        FloatColumn col = new FloatColumn(fromString(str));
        return col.clone();
    }

    private float fromString(String str){
        if (str.matches("\\d+\\.\\d+")){
            return Float.parseFloat(str);
        } else{
            throw new IllegalArgumentException("Неверные параметры для создания объекта!");
        }
    }

    @Override
    public String toString() {
        return data + "";
    }

    @Override
    public Column addition(Column col2) {
        if (!(col2 instanceof FloatColumn)) {
            return null;
        } else{
            FloatColumn floatColumn = (FloatColumn) col2;
            FloatColumn sum = new FloatColumn(data + floatColumn.data);
            return sum.clone();
        }
    }

    @Override
    public int compareTo(Object o) {
        FloatColumn obj2 = (FloatColumn) o;
        return Float.compare(data, obj2.data);
    }
}
