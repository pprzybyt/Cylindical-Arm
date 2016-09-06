
package robot2;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Sphere;
import java.util.TimerTask;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;
import static robot2.Robot2.*;

/**
 * Allows robot to move acording active button specified
 * in Array buttons
 * @author PrzemysławPrzybyt
 */

public class Task extends TimerTask {

    /**
     * Every time, button from Array buttons is active, moves appropiate part of robot 
     * 
     */
    @Override
    public void run() 
    {
         if(button[8])
        {
            trajectory.add(new Trajectory()); 
        }
         
         if(button[9])
             SetPosition();
            
        if(button[0])
        {
            if(ring.getYPos()<=robotHeight/2 - ring.getHeight()-0.02f)
            {   
                ring.setYPos(ring.getYPos()+0.01f);
                transRing.set(new Vector3f(ring.getXPos(),ring.getYPos(),0.0f));
                translateRing.setTransform(transRing);
                if(button[8])  
                    trajectory.get(index).SetMovement(0);
            }    
        }
        if(button[1])
        {
             if(allowMoveDown && ring.getYPos()>= -robotHeight/2 + ring.getHeight()+ floorHeight +0.01f)
            {   
                ring.setYPos(ring.getYPos()-0.01f);
                transRing.set(new Vector3f(ring.getXPos(),ring.getYPos(),0.0f));
                translateRing.setTransform(transRing);
                if(button[8])  
                    trajectory.get(index).SetMovement(1);
                
            }
             
             if(CollisionDetector.inCollision && !isCatched)
                 allowMoveDown = false;
             else
                 allowMoveDown = true;
            
        }
       
        if(button[2] && ring.getYAngle() <= (double)Math.PI)
        {
       
            if(allowMoveLeft)
            {
                Transform3D rot = new Transform3D();
            
            rot.rotY(0.03);
            
            ring.setYAngle(ring.getYAngle()+0.03);
            
            rotRing.mul(rot);
            rotateRing.setTransform(rotRing);
            if(button[8])  
                trajectory.get(index).SetMovement(2);
            }
       
                 if(CollisionDetector.inCollision && allowMoveRight && !isCatched)
                allowMoveLeft = false;
            else
                allowMoveLeft = true;
            
            
        }
       
             if(button[3] && ring.getYAngle() >= -(double)Math.PI)
        {
            
         
            if(allowMoveRight)
            {
            Transform3D rot = new Transform3D();
            rot.rotY(-0.03);
            
            ring.setYAngle(ring.getYAngle()-0.03);
            
            rotRing.mul(rot);
            rotateRing.setTransform(rotRing);
            if(button[8])  
                trajectory.get(index).SetMovement(3);
            }
            
            if(CollisionDetector.inCollision && allowMoveLeft && !isCatched)
                allowMoveRight = false;
            else
                allowMoveRight = true;

            
        }
        
        if(button[4])
        {
           if(allowMoveBack && arm.getXPos() >= ring.getXPos())
           {
               arm.setXPos(arm.getXPos() - 0.01f);
               transArm.set(new Vector3f(arm.getXPos(),0.0f,0.0f));
               translateArm.setTransform(transArm);
               if(button[8])  
                    trajectory.get(index).SetMovement(4);
           }
           
           if(CollisionDetector.inCollision && allowMoveThrough && !isCatched)
               allowMoveBack =false;
           else
               allowMoveBack = true;
           
        }
        if(button[5])
        {
     
           if(arm.getXPos() <= ring.getXPos() + arm.getHeight()/2 + 0.12f)
           {
               if(allowMoveThrough)
               {
               arm.setXPos(arm.getXPos() + 0.01f);
               transArm.set(new Vector3f(arm.getXPos(),0.0f,0.0f));
               translateArm.setTransform(transArm);
                 if(button[8])  
                    trajectory.get(index).SetMovement(5);
               }
               
           }
            if(CollisionDetector.inCollision && !isCatched && primitiveRadius>= arm.getXPos()+arm.getHeight()/2  )
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
        
        if(button[6] && allowCatch &&!isCatched && !isFallingDown)
        {
           isCatched = true;
           System.err.println("catched");
           
           stage .removeChild(primitiveBranch);
            translateWrist.addChild(primitiveBranch);
                    
           transPrimitive.set(new Vector3f(primitive.getRadius(),0.0f,0.0f));
           translatePrimitive.setTransform(transPrimitive);
           
             if(button[8])  
                    trajectory.get(index).SetMovement(6);
          
             
        }
      
        if(button[7] && isCatched)
        {
            isCatched = false;
            translateWrist.removeChild(primitiveBranch);
            stage.addChild(primitiveBranch);
                       
            primitiveHeight = robotHeight/2 + ring.getYPos()+ 0.01f;
            primitiveXPos = (arm.getXPos()+  ring.getLenght()/2 + arm.getHeight()/2 + primitive.getRadius()+wrist.getHeight())*(float)Math.cos(-ring.getYAngle());
            primitiveZPos = (arm.getXPos()+ ring.getLenght()/2 + arm.getHeight()/2 + primitive.getRadius()+ wrist.getHeight())*(float)Math.sin(-ring.getYAngle());
           
            primitiveRadius = arm.getXPos()+ ring.getLenght()/2 + arm.getHeight()/2 + primitive.getRadius()+wrist.getHeight();
            isFallingDown = true;
              if(button[8])  
                    trajectory.get(index).SetMovement(7);
            
            
        }
        
        if(isFallingDown)
        {
             if(primitiveHeight >= floorHeight/2 + primitive.getRadius() + 0.03f)
             {
                 primitiveHeight -= 0.02f;
                 transPrimitive.set(new Vector3f(primitiveXPos,primitiveHeight,primitiveZPos));
                 translatePrimitive.setTransform(transPrimitive);
             }
             else if (primitiveHeight >= floorHeight/2 + primitive.getRadius())
             {
                 transPrimitive.set(new Vector3f(primitiveXPos ,floorHeight/2 + primitive.getRadius() + 0.01f,primitiveZPos ));
                 translatePrimitive.setTransform(transPrimitive);
                 primitiveHeight = 0.0f;
             }
             else {

                 isFallingDown = false;        
             }
        }
       
        
        if(!CollisionDetector.inCollision)
            allowCatch = false;
        
        if(button[8])
            index++;
        
        if(button[9])
            if(trajectory.size() - 1 > index)
                index++;
            else 
               index=0;
        
        
    }

    private void SetPosition() {
         if(index == 0)
             {
                 
            Transform3D rot = new Transform3D();
            rot.rotY(trajectory.get(0).yAngle - ring.getYAngle());           
            rotRing.mul(rot);
            rotateRing.setTransform(rotRing);
            ring.setYAngle(trajectory.get(0).yAngle);
            
            ring.setYPos(trajectory.get(0).height);
            transRing.set(new Vector3f(ring.getXPos(),ring.getYPos(),0.0f));
            translateRing.setTransform(transRing);
            
            arm.setXPos(trajectory.get(0).armRadius);
            transArm.set(new Vector3f(arm.getXPos(),0.0f,0.0f));
            translateArm.setTransform(transArm);
            
            
            transPrimitive.set(new Vector3f(trajectory.get(0).primitiveX ,floorHeight/2 + primitive.getRadius() + 0.01f,trajectory.get(0).primitiveZ ));
            translatePrimitive.setTransform(transPrimitive);
            
            if(trajectory.get(0).catched && primitiveBranch.getParent().equals(stage))
            {
             isCatched = true;
           System.err.println("catched");
           
           stage .removeChild(primitiveBranch);
           translateWrist.addChild(primitiveBranch);  
           
           transPrimitive.set(new Vector3f(primitive.getRadius(),0.0f,0.0f));
           translatePrimitive.setTransform(transPrimitive);   
         
           }
            else if (!trajectory.get(0).catched && primitiveBranch.getParent().equals(translateWrist))
            {
                isCatched = false;
            translateWrist.removeChild(primitiveBranch);
            stage.addChild(primitiveBranch);
         
               transPrimitive.set(new Vector3f(trajectory.get(0).primitiveX ,floorHeight/2 + primitive.getRadius() + 0.01f,trajectory.get(0).primitiveZ ));
                
            }
            else if(trajectory.get(0).catched && primitiveBranch.getParent().equals(translateWrist))
            {
                transPrimitive.set(new Vector3f(primitive.getRadius(),0.0f,0.0f));
                translatePrimitive.setTransform(transPrimitive);   
            }
            
            isRecorded = true;
         }
             
                    for(int i = 0 ; i<8 ; i++)
                 button[i] = trajectory.get(index).buttons[i];
            
           
             
         }
    
    
}
        