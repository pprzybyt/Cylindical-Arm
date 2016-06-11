
package robot2;

import static robot2.Robot2.*;

/**
 * Trajectory that Robot will follow automatically
 * @author PrzemysławPrzybyt
 */
public class Trajektoria {
    
    public boolean [] przyciski;
    public static int ID;
    public float height;
    public double yAngle;
    public float armRadius;
    
    public float prymX;
    public float prymZ;
    
    public boolean catched;
    /**
     * Creates new trajectory that Robot will follow automatically
     */
    public Trajektoria()
    {
        this.przyciski = new boolean[8];
        ID++;
        if(ID==1)
        {
            this.height = pierscien.getYPos();
            this.yAngle = pierscien.getYAngle();
            this.armRadius = ramie.getXPos();
            this.prymX = prymXPos;
            this.prymZ = prymZPos;
            this.catched = isCatched;
        } 

    }
    /**
     * Chooses which part of robot must be moved to follow specified trajectory
     * @param x this parameter specifies which "virtual" button must be clicked to create desired move of robot
     */
    public void ustawRuch(int x)
    {
        if(x<8)
            this.przyciski[x]=true;
    }
    
}
