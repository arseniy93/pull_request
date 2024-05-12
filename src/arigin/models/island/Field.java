package arigin.models.island;

import java.util.Objects;

public class Field implements Comparable<Field>  {
    //cords of filed
    String f;
    String v;

    public Field(int x, int y) {

        this.x = x;
        this.y = y;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

//    @Override
//    public int compare(Field o1, Field o2) {
//        if(o1.getX()>o2.getX())
//            return 1;
//        else if(o1.getX()<o2.getX())
//        return -1;
//        else return 0;
//    }
//    public static Comparator<Field> createComp(){
//        return Comparator.comparing(Field::getX)
//                .thenComparing(Field::getY);
//    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Field field = (Field) object;
        return x == field.x && y == field.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }



    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int x;
    private int y;




    @Override
    public int compareTo(Field o) {
        int a1=o.getX();
        int a2=getX();
        int b1=o.getY();
        int b2=getX();
        if(-a1+a2>0) {
            return 1;
        }
        else if(-a1+a2<0) return -1;
        else return 0;
    }
//    @Override
//    public int compareTo(Field o) {
//        int a1=o.getX();
//        int a2=getX();
//        int b1=o.getY();
//        int b2=getX();
//        if(-a1+a2>0) {
//            return 1;
//        }
//        else if(-a1+a2<0) return -1;
//        else return 0;
//    }
}
//if(-b1+b2>0){
//        return 1;
//        }
//        else if((-b1+b2<0)){
//        return -1;
//        }
//        else return 0;