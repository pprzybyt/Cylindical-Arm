
package robot2;

import com.sun.j3d.utils.geometry.Sphere;
import javax.media.j3d.Appearance;

/**
 * Class that extends com.sun.j3d.utils.geometry.Sphere
 * @author PrzemysławPrzybyt
 */
public class MySphere extends Sphere {
   
    private float xPos = .0f, yPos = .0f, zPos = .0f;
    private double yAngle = 0.0;
    
    /**
     * Creates new Sphere of basic radius of 1.0
     */
    public MySphere()
    {
        super();
    }
    /**
     * Creates new Sphere with specified size
     * 
     * @param radius radius of Sphere
     */
    public MySphere(float radius) 
    {
        super(radius);
    }
    /**
     * Creates new Sphere with specified size
     * @param radius radius of Sphere
     * @param x coordinate
     * @param y coordinate
     */
    public MySphere(float radius, float x, float y)
    {
        super(radius);
        this.xPos = x;
        this.yPos = y;  
    }
    /**
     * Creates new Sphere with specified size and appearance
     * @param radius radius of Sphere
     * @param param Primitive flags customizing Sphere
     * @param a appearance
     */
     public MySphere(float radius,int param , Appearance a)
    {
        super(radius, param , a);
    }
     /**
      * Creates new Sphere with specified size and angle to Y axis
      * @param radius radius of Sphere
      * @param x coordinate
      * @param y coordinate
      * @param a angle
      */
    public MySphere(float radius, float x, float y , double a)
    {
        super(radius);
        this.xPos = x;
        this.yPos = y;  
        this.yAngle = a;
    }
    /**
     * 
     * @return float X coordinate of Sphere
     */
     public float getXPos(){
        return this.xPos;
    }
    /**
     * Sets X coord of Sphere to 
     * @param param 
     */
    public void setXPos(float param){
        this.xPos = param;
    }
    /**
     * 
     * @return float Y coordinate of Sphere 
     */
    public float getYPos(){
        return this.yPos;
    }
    
    /**
     * Sets Y coord of Sphere to param
     * @param param 
     */
    public void setYPos(float param){
        this.yPos = param;
    }
    
    /**
     * 
     * @return float Z coordinate of Sphere
     */
    public float getZPos(){
        return this.zPos;
    }
    /**
     * Sets Z coord of Sphere to param
     * @param param 
     */
    public void setZPos(float param){
        this.zPos = param;
    }
    /**
     * 
     * @return Y angle of Sphere
     */
     public double getYAngle(){
        return this.yAngle;
    }
    /**
     * Sets angle in regard to Y axis of Sphere to 
     * @param param 
     */
    public void setYAngle(double param){
        this.yAngle = param ;
    }

}
