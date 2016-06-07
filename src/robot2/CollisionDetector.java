/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot2;

import com.sun.j3d.utils.geometry.Sphere;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnCollisionEntry;
import javax.media.j3d.WakeupOnCollisionExit;
import javax.vecmath.Point3d;
import static robot2.Robot2.isCatched;

/**
 *
 * @author przem0
 */

public class CollisionDetector extends Behavior {
    public static boolean inCollision = false;
    private WakeupOnCollisionEntry wEnter;
    private WakeupOnCollisionExit wExit;

      TransformGroup prym;

    
    public CollisionDetector(TransformGroup tg) {
        inCollision = false;
        prym = tg;
        prym.setCollisionBounds(new BoundingSphere(new Point3d(), 0.1d));
    }

    @Override
    public void initialize() {
        wEnter = new WakeupOnCollisionEntry(prym);
        wExit = new WakeupOnCollisionExit(prym);
        wakeupOn(wEnter);
    }

    @Override
    public void processStimulus(Enumeration criteria) {
      
           inCollision = !inCollision;
       
        if (inCollision) 
        {
            System.out.println("in");
            wakeupOn(wExit);  
          
        }
        else 
        {
            System.out.println("out");
            
            wakeupOn(wEnter);
        }
      
  
    }
    
    
    
}
