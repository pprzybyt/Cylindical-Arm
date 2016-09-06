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
 * This class detects whether robot's arm meets primitive
 * @author PrzemyslawPrzybyt
 */

public class CollisionDetector extends Behavior {
    public static boolean inCollision = false;
    private WakeupOnCollisionEntry wEnter;
    private WakeupOnCollisionExit wExit;

      Sphere primitive;

    /**
     * 
     * @param s Sphere that will be catched by robot
     */
    public CollisionDetector(Sphere s) {
        inCollision = false;
        primitive = s;
        primitive.setCollisionBounds(new BoundingSphere(new Point3d(), 0.1d));
    }

    @Override
    public void initialize() {
        wEnter = new WakeupOnCollisionEntry(primitive);
        wExit = new WakeupOnCollisionExit(primitive);
        wakeupOn(wEnter);
    }
    
    /**
     * 
     * @param criteria 
     */
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
