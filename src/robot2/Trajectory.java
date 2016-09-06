
package robot2;

import static robot2.Robot2.*;

/**
 * Trajectory that Robot will follow automatically
 * @author PrzemysławPrzybyt
 */
public class Trajectory {
    
    public boolean [] buttons;
    public static int ID;
    public float height;
    public double yAngle;
    public float armRadius;
    
    public float primitiveX;
    public float primitiveZ;
    
    public boolean catched;
    /**
     * Creates new trajectory that Robot will follow automatically
     */
    public Trajectory()
    {
        this.buttons = new boolean[8];
        ID++;
        if(ID==1)
        {
            this.height = ring.getYPos();
            this.yAngle = ring.getYAngle();
            this.armRadius = arm.getXPos();
            this.primitiveX = primitiveXPos;
            this.primitiveZ = primitiveZPos;
            this.catched = isCatched;
        } 

    }
    /**
     * Chooses which part of robot must be moved to follow specified trajectory
     * @param x this parameter specifies which "virtual" button must be clicked to create desired move of robot
     */
    public void SetMovement(int x)
    {
        if(x<8)
            this.buttons[x]=true;
    }
    
}
