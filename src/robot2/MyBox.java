
package robot2;

import com.sun.j3d.utils.geometry.Box;
import javax.media.j3d.Appearance;

/**
 * This class extends com.sun.j3d.utils.geometry.Box and has implemented methods useful especially for this project
 * @author PrzemysławPrzybyt
 */

public class MyBox extends Box {
    
  private float xPos = .0f, yPos = .0f, zPos = .0f;
    private double yAngle = .0f;
    private float height;
    private float lenght;
    private float width;
    
    /**
     * Creates new default Box
     */
    public MyBox(){
        super();
    }
    /**
     * Creates Box with specified length, height, width and appearance
     * @param length Length of Box
     * @param height Height of Box
     * @param width Width of Box
     * @param ap Appearance
     */
    public MyBox(float length,float height,  float width, Appearance ap){
        super(length,height, width, ap);
        this.lenght = length;
        this.width = width;
        this.height = height;
    }
    
//    public MyCylinder(float radius, float height, float x, float y, float z){
//        super(radius, height);
//        this.xPos = x;
//        this.yPos = y;
//        this.zPos = z;
//    }
    /**
     * Creates Box with specified length, height, width, appearance and Primitive flags customizing Box
    * @param lenght Length of Box
     * @param height Height of Box
     * @param width Width of Box
     * @param prim Flags that customize Object
     * @param ap Appearance
     */
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
    /**
     * 
     * @return float x coordinate of this object
     */
    public float getXPos(){
        return this.xPos;
    }
    
    /**
     * 
     * @param param 
     */
    public void setXPos(float param){
        this.xPos = param;
    }
    
    /**
     * 
     * @return float y coordinate of this object
     */
    public float getYPos(){
        return this.yPos;
    }
    /**
     * 
     * @param param 
     */
    public void setYPos(float param){
        this.yPos = param;
    }
    
    /**
     * 
     * @return  float z coordinate of this object
     */
    public float getZPos(){
        return this.zPos;
    }
    
    /**
     * 
     * @param param 
     */
    public void setZPos(float param){
        this.zPos = param;
    }
    
    /**
     * 
     * @return  float y angle of this object
     */
    public double getYAngle(){
        return this.yAngle;
    }
    
    /**
     * 
     * @param param 
     */
    public void setYAngle(double param){
        this.yAngle =     param ;
    }
    
    /**
     * 
     * @return  float height of this object
     */
    public float getHeight()
    {
        return this.height;
    }
    
    /**
     * 
     * @return  float width of this object
     */
    public float getWidtht()
    {
        return this.width;
    }
    
    /**
     * 
     * @return  float length of this object
     */
    public float getLenght()
    {
        return this.lenght;
    }
    
    /**
     * 
     * @param height 
     */
    public void setHeight(float height)
    {
        this.height = height;
    }
    
    /**
     * 
     * @param width 
     */
    public void setWidtht(float width )
    {
        this.width = width;
    }
    
    /**
     * 
     * @param lenght 
     */
    public void setLenght(float lenght)
    {
        this.lenght= lenght;
    }
    
    
}
