
package robot2;

import com.sun.j3d.utils.geometry.Box;
import javax.media.j3d.Appearance;


public class MyBox extends Box {
    
  private float xPos = .0f, yPos = .0f, zPos = .0f;
    private double yAngle = .0f;
    private float height;
    private float lenght;
    private float width;
    
    public MyBox(){
        super();
    }
    
    public MyBox(float lenght,float height,  float width, Appearance ap){
        super(lenght,height, width, ap);
        this.lenght = lenght;
        this.width = width;
        this.height = height;
    }
    
//    public MyCylinder(float radius, float height, float x, float y, float z){
//        super(radius, height);
//        this.xPos = x;
//        this.yPos = y;
//        this.zPos = z;
//    }
    
     public MyBox(float lenght,float height,  float width, int prim, Appearance ap){
        super(lenght,height, width,  prim, ap);
        this.lenght = lenght;
        this.width = width;
        this.height = height;
    }
//    public MyCylinder(float radius, float height,int prim, Appearance ap, float x, float y, float z){
//        super(radius, height, prim, ap);
//        this.xPos = x;
//        this.yPos = y;
//        this.zPos = z;
//    }
    
    public float getXPos(){
        return this.xPos;
    }
    
    public void setXPos(float param){
        this.xPos = param;
    }
    
    public float getYPos(){
        return this.yPos;
    }
    
    public void setYPos(float param){
        this.yPos = param;
    }
    
    public float getZPos(){
        return this.zPos;
    }
    
    public void setZPos(float param){
        this.zPos = param;
    }
    
    public double getYAngle(){
        return this.yAngle;
    }
    
    public void setYAngle(double param){
        this.yAngle =     param ;
    }
    
    public float getHeight()
    {
        return this.height;
    }
    public float getWidtht()
    {
        return this.width;
    }
    public float getLenght()
    {
        return this.lenght;
    }
    
    
    public void setHeight(float height)
    {
        this.height = height;
    }
    public void setWidtht(float width )
    {
        this.width = width;
    }
    public void setLenght(float lenght)
    {
        this.lenght= lenght;
    }
    
    
}
