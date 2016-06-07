
package robot2;

import static robot2.Robot2.*;

/**
 *
 * @author przem0
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
    
    public void ustawRuch(int x)
    {
        if(x<8)
            this.przyciski[x]=true;
    }
    
}
