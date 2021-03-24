public abstract class Column implements Cloneable, Comparable {

    public String getName(){return this.getClass().getName();}

    @Override
    public Object clone(){
        try{
            return super.clone();
        }catch (CloneNotSupportedException e){
            throw new InternalError();
        }
    }

    @Override
    public abstract boolean equals(Object object2);

    public abstract Column fromStringToObject(String str);

    public abstract String toString();

    public abstract Column addition(Column col2);
}
