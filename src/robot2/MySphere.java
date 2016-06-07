
package robot2;

import com.sun.j3d.utils.geometry.Sphere;
import javax.media.j3d.Appearance;

/**
 *
 * @author przem0
 */
public class MySphere extends Sphere {
   
    private float xPos = .0f, yPos = .0f, zPos = .0f;
    private double yAngle = 0.0;
    
    public MySphere()
    {
        super();
    }
    
    public MySphere(float radius) 
    {
        super(radius);
    }
    
    public MySphere(float radius, float x, float y)
    {
        super(radius);
        this.xPos = x;
        this.yPos = y;  
    }
    
     public MySphere(float radius,int param , Appearance a)
    {
        super(radius, param , a);
    }
    public MySphere(float radius, float x, float y , double a)
    {
        super(radius);
        this.xPos = x;
        this.yPos = y;  
        this.yAngle = a;
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
    
     public double getYAngle(){
        return this.yAngle;
    }
    
    public void setYAngle(double param){
        this.yAngle = param ;
    }

}
