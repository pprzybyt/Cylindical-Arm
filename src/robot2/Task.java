
package robot2;

import java.util.TimerTask;
import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;
import static robot2.Robot2.*;

/**
 *
 * @author przem0
 */

public class Task extends TimerTask {

    @Override
    public void run() 
    {
        
        // Tu beda animacje nawet 3 na raz
        if(przyciski[0])
        {
            if(pierscien.getYPos()<=robotHeight/2 - pierscien.getHeight()/2-0.02f)
            {   
                pierscien.setYPos(pierscien.getYPos()+0.01f);
                transPierscien.set(new Vector3f(0.0f,pierscien.getYPos(),0.0f));
                przesunPierscien.setTransform(transPierscien);
            }    
        }
        if(przyciski[1])
        {
             if(pierscien.getYPos()>= -robotHeight/2 + pierscien.getHeight()/2+ groundHeight/2 +0.01f)
            {   
                pierscien.setYPos(pierscien.getYPos()-0.01f);
                transPierscien.set(new Vector3f(0.0f,pierscien.getYPos(),0.0f));
                przesunPierscien.setTransform(transPierscien);
            }
            
        }
       
        if(przyciski[2] && pierscien.getYAngle() <= (double)Math.PI)
        {
            Transform3D rot = new Transform3D();
            rot.rotY(0.03);
            
            pierscien.setYAngle(pierscien.getYAngle()+0.03);
            
            rotPierscien.mul(rot);
            obrotPierscien.setTransform(rotPierscien);
            
        }
       
             if(przyciski[3] && pierscien.getYAngle() >= -(double)Math.PI)
        {
            Transform3D rot = new Transform3D();
            rot.rotY(-0.03);
            
            pierscien.setYAngle(pierscien.getYAngle()-0.03);
            
            rotPierscien.mul(rot);
            obrotPierscien.setTransform(rotPierscien);
            
        }
        
        if(przyciski[4])
        {
           if(ramie.getXPos()-ramie.getHeight()/2 >= - pierscien.getRadius() + 0.1f)
           {
               ramie.setXPos(ramie.getXPos() - 0.01f);
               transRamie.set(new Vector3f(ramie.getXPos(),0.0f,0.0f));
               przesunRamie.setTransform(transRamie);
           }
           
        }
        if(przyciski[5])
        {
            if(ramie.getXPos()+ramie.getHeight()/2 <= pierscien.getRadius() + ramie.getHeight() - 0.1f)
           {
               ramie.setXPos(ramie.getXPos() + 0.01f);
               transRamie.set(new Vector3f(ramie.getXPos(),0.0f,0.0f));
               przesunRamie.setTransform(transRamie);
           }
            
        }
        
        //  CollisionDetector.inCollision 
        if(przyciski[6] && !isCatched && !fallingDown)
        {
           isCatched = true;
           
           objRotate.removeChild(prym);
            przesunKisc.addChild(prym);
           
              Transform3D tmp = new Transform3D();
             tmp.rotY(-prymLastAngle);
             rotPrym.mul(tmp);
             obrotPrym.setTransform(rotPrym);
           
           transPrym.set(new Vector3f(prymityw.getRadius(),0.0f,0.0f));
           przesunPrym.setTransform(transPrym);
           
          
             
        }
      
        if(przyciski[7] && isCatched)
        {
            isCatched = false;
            przesunKisc.removeChild(prym);
            objRotate.addChild(prym);
            
            Transform3D tmp = new Transform3D();
            tmp.rotY(pierscien.getYAngle());
            rotPrym.mul(tmp);
            obrotPrym.setTransform(rotPrym);
             
            prymLastAngle = pierscien.getYAngle();
            
            prymHeight = robotHeight/2 + pierscien.getYPos()+ 0.01f;
            prymXPos = ramie.getXPos()+ ramie.getHeight()/2 + prymityw.getRadius();
            fallingDown = true;
            
            
            
        }
        
        if(fallingDown)
        {
             if(prymHeight >= groundHeight/2 + prymityw.getRadius() + 0.03f)
             {
                 prymHeight -= 0.02f;
                 transPrym.set(new Vector3f(prymXPos,prymHeight,0.0f));
                 przesunPrym.setTransform(transPrym);
             }
             else if (prymHeight >= groundHeight/2 + prymityw.getRadius())
             {
                 transPrym.set(new Vector3f(prymXPos,groundHeight/2 + prymityw.getRadius() + 0.01f,0.0f));
                 przesunPrym.setTransform(transPrym);
                 prymHeight = 0.0f;
             }
             else {

                 fallingDown = false;
             }
        }
        
        
        
    }
    
}
