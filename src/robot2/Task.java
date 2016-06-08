
package robot2;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Sphere;
import java.util.TimerTask;
import javax.media.j3d.BranchGroup;
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
         if(przyciski[8])
        {
            trajektoria.add(new Trajektoria()); 
        }
         
         if(przyciski[9])
             UstawPozycje();
            
        // Tu beda animacje nawet 3 na raz
        if(przyciski[0])
        {
            if(pierscien.getYPos()<=robotHeight/2 - pierscien.getHeight()-0.02f)
            {   
                pierscien.setYPos(pierscien.getYPos()+0.01f);
                transPierscien.set(new Vector3f(pierscien.getXPos(),pierscien.getYPos(),0.0f));
                przesunPierscien.setTransform(transPierscien);
                if(przyciski[8])  
                    trajektoria.get(index).ustawRuch(0);
            }    
        }
        if(przyciski[1])
        {
             if(allowMoveDown && pierscien.getYPos()>= -robotHeight/2 + pierscien.getHeight()+ groundHeight +0.01f)
            {   
                pierscien.setYPos(pierscien.getYPos()-0.01f);
                transPierscien.set(new Vector3f(pierscien.getXPos(),pierscien.getYPos(),0.0f));
                przesunPierscien.setTransform(transPierscien);
                if(przyciski[8])  
                    trajektoria.get(index).ustawRuch(1);
            }
             
             if(CollisionDetector.inCollision && !isCatched)
                 allowMoveDown = false;
             else
                 allowMoveDown = true;
            
        }
       
        if(przyciski[2] && pierscien.getYAngle() <= (double)Math.PI)
        {
       
            if(allowMoveLeft)
            {
                Transform3D rot = new Transform3D();
            
            rot.rotY(0.03);
            
            pierscien.setYAngle(pierscien.getYAngle()+0.03);
            
            rotPierscien.mul(rot);
            obrotPierscien.setTransform(rotPierscien);
            if(przyciski[8])  
                trajektoria.get(index).ustawRuch(2);
            }
       
                 if(CollisionDetector.inCollision && allowMoveRight && !isCatched)
                allowMoveLeft = false;
            else
                allowMoveLeft = true;
            
            
        }
       
             if(przyciski[3] && pierscien.getYAngle() >= -(double)Math.PI)
        {
            
         
            if(allowMoveRight)
            {
            Transform3D rot = new Transform3D();
            rot.rotY(-0.03);
            
            pierscien.setYAngle(pierscien.getYAngle()-0.03);
            
            rotPierscien.mul(rot);
            obrotPierscien.setTransform(rotPierscien);
            if(przyciski[8])  
                trajektoria.get(index).ustawRuch(3);
            }
            
            if(CollisionDetector.inCollision && allowMoveLeft && !isCatched)
                allowMoveRight = false;
            else
                allowMoveRight = true;

            
        }
        
        if(przyciski[4])
        {
           if(allowMoveBack && ramie.getXPos() >= pierscien.getXPos())
           {
               ramie.setXPos(ramie.getXPos() - 0.01f);
               transRamie.set(new Vector3f(ramie.getXPos(),0.0f,0.0f));
               przesunRamie.setTransform(transRamie);
               if(przyciski[8])  
                    trajektoria.get(index).ustawRuch(4);
           }
           
           if(CollisionDetector.inCollision && allowMoveThrough && !isCatched)
               allowMoveBack =false;
           else
               allowMoveBack = true;
           
        }
        if(przyciski[5])
        {
     
           if(ramie.getXPos() <= pierscien.getXPos() + ramie.getHeight()/2 + 0.12f)
           {
               if(allowMoveThrough)
               {
               ramie.setXPos(ramie.getXPos() + 0.01f);
               transRamie.set(new Vector3f(ramie.getXPos(),0.0f,0.0f));
               przesunRamie.setTransform(transRamie);
                 if(przyciski[8])  
                    trajektoria.get(index).ustawRuch(5);
               }
               
           }
            if(CollisionDetector.inCollision && !isCatched && prymRadius>= ramie.getXPos()+ramie.getHeight()/2  )
               {
                   allowCatch =true;
                   allowMoveThrough = false;
               }
               else
               {
                    allowMoveThrough = true;
                    allowCatch = false;
               }
            
        }
        
        if(przyciski[6] && allowCatch &&!isCatched && !isFallingDown)
        {
           isCatched = true;
           System.err.println("catched");
           
           scena .removeChild(prym);
            przesunKisc.addChild(prym);
                    
           transPrym.set(new Vector3f(prymityw.getRadius(),0.0f,0.0f));
           przesunPrym.setTransform(transPrym);
           
             if(przyciski[8])  
                    trajektoria.get(index).ustawRuch(6);
          
             
        }
      
        if(przyciski[7] && isCatched)
        {
            isCatched = false;
            przesunKisc.removeChild(prym);
            scena.addChild(prym);
                       
            prymHeight = robotHeight/2 + pierscien.getYPos()+ 0.01f;
            prymXPos = (ramie.getXPos()+  pierscien.getLenght()/2 + ramie.getHeight()/2 + prymityw.getRadius()+kisc.getHeight())*(float)Math.cos(-pierscien.getYAngle());
            prymZPos = (ramie.getXPos()+ pierscien.getLenght()/2 + ramie.getHeight()/2 + prymityw.getRadius()+ kisc.getHeight())*(float)Math.sin(-pierscien.getYAngle());
           
            prymRadius = ramie.getXPos()+ pierscien.getLenght()/2 + ramie.getHeight()/2 + prymityw.getRadius()+kisc.getHeight();
            isFallingDown = true;
              if(przyciski[8])  
                    trajektoria.get(index).ustawRuch(7);
            
            
        }
        
        if(isFallingDown)
        {
             if(prymHeight >= groundHeight/2 + prymityw.getRadius() + 0.03f)
             {
                 prymHeight -= 0.02f;
                 transPrym.set(new Vector3f(prymXPos,prymHeight,prymZPos));
                 przesunPrym.setTransform(transPrym);
             }
             else if (prymHeight >= groundHeight/2 + prymityw.getRadius())
             {
                 transPrym.set(new Vector3f(prymXPos ,groundHeight/2 + prymityw.getRadius() + 0.01f,prymZPos ));
                 przesunPrym.setTransform(transPrym);
                 prymHeight = 0.0f;
             }
             else {

                 isFallingDown = false;        
             }
        }
       
        
        if(!CollisionDetector.inCollision)
            allowCatch = false;
        
        if(przyciski[8])
            index++;
        
        if(przyciski[9])
            if(trajektoria.size() - 1 > index)
                index++;
            else 
               index=0;
        
        
    }

    private void UstawPozycje() {
         if(index == 0)
             {
                 
            Transform3D rot = new Transform3D();
            rot.rotY(trajektoria.get(0).yAngle - pierscien.getYAngle());           
            rotPierscien.mul(rot);
            obrotPierscien.setTransform(rotPierscien);
            pierscien.setYAngle(trajektoria.get(0).yAngle);
            
            pierscien.setYPos(trajektoria.get(0).height);
            transPierscien.set(new Vector3f(pierscien.getXPos(),pierscien.getYPos(),0.0f));
            przesunPierscien.setTransform(transPierscien);
            
            ramie.setXPos(trajektoria.get(0).armRadius);
            transRamie.set(new Vector3f(ramie.getXPos(),0.0f,0.0f));
            przesunRamie.setTransform(transRamie);
            
            
            transPrym.set(new Vector3f(trajektoria.get(0).prymX ,groundHeight/2 + prymityw.getRadius() + 0.01f,trajektoria.get(0).prymZ ));
            przesunPrym.setTransform(transPrym);
            
            if(trajektoria.get(0).catched && prym.getParent().equals(scena))
            {
             isCatched = true;
           System.err.println("catched");
           
           scena .removeChild(prym);
           przesunKisc.addChild(prym);  
           
           transPrym.set(new Vector3f(prymityw.getRadius(),0.0f,0.0f));
           przesunPrym.setTransform(transPrym);   
         
           }
            else if (!trajektoria.get(0).catched && prym.getParent().equals(przesunKisc))
            {
                isCatched = false;
            przesunKisc.removeChild(prym);
            scena.addChild(prym);
         
               transPrym.set(new Vector3f(trajektoria.get(0).prymX ,groundHeight/2 + prymityw.getRadius() + 0.01f,trajektoria.get(0).prymZ ));
                
            }
            else if(trajektoria.get(0).catched && prym.getParent().equals(przesunKisc))
            {
                transPrym.set(new Vector3f(prymityw.getRadius(),0.0f,0.0f));
                przesunPrym.setTransform(transPrym);   
            }
            
            isRecorded = true;
         }
             
                    for(int i = 0 ; i<8 ; i++)
                 przyciski[i] = trajektoria.get(index).przyciski[i];
            
           
             
         }
    
    
}
        