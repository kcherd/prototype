public class IntColumn extends Column{
    int data;

    IntColumn(){
        data = 0;
    }

    IntColumn(int data){
        this.data = data;
    }

    IntColumn(String data){
        this.data = fromString(data);
    }

    public int getData(){
        return data;
    }

    public void setData(int data){
        this.data = data;
    }

    @Override
    public IntColumn clone() {
        return (IntColumn) super.clone();
    }

    @Override
    public boolean equals(Object object2) {
        if (!(object2 instanceof IntColumn)) {
            return false;
        } else{
            IntColumn col2 = (IntColumn) object2;
            return data == col2.data;
        }
    }

    @Override
    public Column fromStringToObject(String str) {
        IntColumn col = new IntColumn(fromString(str));
        return col.clone();
    }

    private int fromString(String str){
        if (str.matches("\\d+")){
            return Integer.parseInt(str);
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
        if (!(col2 instanceof IntColumn)) {
            return null;
        } else{
            IntColumn intColumn = (IntColumn) col2;
            IntColumn sum = new IntColumn(data + intColumn.data);
            return sum.clone();
        }
    }

    @Override
    public int compareTo(Object o) {
        IntColumn obj2 = (IntColumn) o;
        return Integer.compare(data, obj2.data);
    }
}
