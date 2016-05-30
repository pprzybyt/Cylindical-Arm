
package robot2;

import com.sun.j3d.utils.geometry.Cylinder;
import javax.media.j3d.Appearance;

/**
 * @author Paweł Kuźmicki
 */

public class MyCylinder extends Cylinder{
    
    private float xPos = .0f, yPos = .0f, zPos = .0f;
    private double yAngle = .0f;
    
    public MyCylinder(){
        super();
    }
    
    public MyCylinder(float radius, float height){
        super(radius, height);
    }
    
    public MyCylinder(float radius, float height, float x, float y, float z){
        super(radius, height);
        this.xPos = x;
        this.yPos = y;
        this.zPos = z;
    }
    
     public MyCylinder(float radius, float height,int prim, Appearance ap){
        super(radius, height, prim, ap);
    }
    public MyCylinder(float radius, float height,int prim, Appearance ap, float x, float y, float z){
        super(radius, height, prim, ap);
        this.xPos = x;
        this.yPos = y;
        this.zPos = z;
    }
    
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
    
}
