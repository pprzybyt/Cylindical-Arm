
package robot2;

import com.sun.j3d.utils.geometry.Cylinder;
import javax.media.j3d.Appearance;

/**
 * This class extends com.sun.j3d.utils.geometry.Cylinder 
 * @author PawełKuźmicki
 */

public class MyCylinder extends Cylinder{
    
    private float xPos = .0f, yPos = .0f, zPos = .0f;
    private double yAngle = .0f;
    /**
     * Creates default Cylinder
     */
    public MyCylinder(){
        super();
    }
    /**
     * Creates new Cylinder with specified radius and height
     * @param radius radius of base
     * @param height height of Cylinder
     */
    public MyCylinder(float radius, float height){
        super(radius, height);
    }
    
    /**
     * Specifies size and starting position of Cylinder
     * @param radius radius of base
     * @param height height of Cylinder
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     */
    public MyCylinder(float radius, float height, float x, float y, float z){
        super(radius, height);
        this.xPos = x;
        this.yPos = y;
        this.zPos = z;
    }
    /**
     * Specifies size and appearance of Cylinder
     * @param radius radius of base
     * @param height height of Cylinder
     * @param prim Primitive flags customizing Cylinder
     * @param ap Appearance
     */ 
     public MyCylinder(float radius, float height,int prim, Appearance ap){
        super(radius, height, prim, ap);
    }
     
     /**
      * Specifies size, appearance and starting position of Cylinder
      * @param radius radius of base
      * @param height height of Cylinder
      * @param prim Primitive flags customizing Cylinder
      * @param ap Appearance
      * @param x X coordinate
      * @param y Y coordinate
      * @param z Z coordinate
      */
    public MyCylinder(float radius, float height,int prim, Appearance ap, float x, float y, float z){
        super(radius, height, prim, ap);
        this.xPos = x;
        this.yPos = y;
        this.zPos = z;
    }
    /**
     * 
     * @return float x coordinate of Cylinder
     */
    public float getXPos(){
        return this.xPos;
    }
    
    public void setXPos(float param){
        this.xPos = param;
    }
    
    /**
     * 
     * @return float y coordinate of Cylinder
     */
    public float getYPos(){
        return this.yPos;
    }
    
    public void setYPos(float param){
        this.yPos = param;
    }
    /**
     * 
     * @return float z coordinate of Cylinder
     */
    public float getZPos(){
        return this.zPos;
    }
    
    public void setZPos(float param){
        this.zPos = param;
    }
    /**
     * 
     * @return float angle of Cylinder in regard to Y axis
     */
    public double getYAngle(){
        return this.yAngle;
    }
    /**
     * sets y angle in regard to Y axis
     * @param param 
     */
    public void setYAngle(double param){
        this.yAngle = param ;
    }
    
}
