package entity;

/**
 * Created by bubof on 27.08.2017.
 */
public class Vector2 {
    private double x,y;

    public Vector2(double x,double y){
        this.x = x;
        this.y = y;
    }

    public void addVector(Vector2 another){
        this.x = another.getX();
        this.y = another.getY();
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }
}
