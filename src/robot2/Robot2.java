
package robot2;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Matrix3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import static robot2.Robot2.button;

/**
 * Main class, Creates main BranchGroup and initiates all components of scene
 * @author PrzemyslawPrzybyt
 */

public class Robot2 extends JFrame implements ActionListener, KeyListener{

    
    public static BranchGroup stage;
  
    
    // up, down, left, roght, long, short, take, put
    public static boolean [] button = new boolean[10];
    
    private Cylinder floor;
    private Cylinder core;
    public static MyBox ring;
    public static MyCylinder arm;
    public static MyCylinder wrist;
    
    public static MySphere primitive;
    
    private TransformGroup mainTransform;
    
    public static ArrayList <Trajectory> trajectory = new ArrayList<>() ;
    public static int index = 0;
    public static float robotHeight = 0.8f;
    public static float robotRadius = 0.08f;
    public static float floorHeight = 0.02f;
    
    private final float ringLenght = 0.3f ;
    private final float ringHeight = 0.1f;
    private final float ringWidth = 0.1f;
    
    public static Transform3D transRing;
    public static Transform3D rotRing;
    
    public static TransformGroup translateRing;
    public static TransformGroup rotateRing;
    
    public static TransformGroup translateArm;
    public static Transform3D transArm;
    
    public static TransformGroup translateWrist;
    
    public static TransformGroup translatePrimitive;
    public static TransformGroup rotatePrimitive;
    
    public static Transform3D rotPrimitive;
    public static Transform3D transPrimitive;
    
    public static boolean isCatched = false;
    
    public static float primitiveHeight;
    public static float primitiveXPos;
    public static float primitiveZPos;
     public static float primitiveRadius;
     
    public static boolean isFallingDown = false;
    
    public static boolean allowMoveDown = true;
    public static boolean allowMoveThrough = false;
    public static boolean allowMoveLeft = false;
    public static boolean allowMoveRight = false;
    public static boolean allowMoveBack = false;
    
    public static boolean allowCatch = false;
    
    public static BranchGroup primitiveBranch;
    public static Sphere sphere;
    
    private final Timer timer;
    
    private final JButton start, stop, reset;
    private final JPanel panel;
    
    public static boolean isRecorded;
    
    public Robot2()
    {
        super("Robot 3D");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
    
        this.addKeyListener(this);
        
        start = new JButton("START");
        stop = new JButton("STOP");
        reset = new JButton("RESET");
       
        panel = new JPanel();
        panel.add(start);
        start.addActionListener(this);
        panel.add(stop);
        stop.addActionListener(this);
        panel.add(reset);
        reset.addActionListener(this);
        
        GraphicsConfiguration config =
        SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas3D = new Canvas3D(config);
        canvas3D.setPreferredSize(new Dimension(800,600));
        canvas3D.addKeyListener(this);

            add(BorderLayout.CENTER, canvas3D);
            add(BorderLayout.NORTH, panel);

        pack();
        setVisible(true);
        setFocusable(true);
        setLocationRelativeTo(null);
        requestFocusInWindow();

        stage = utworzScene();
        
        stage.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        stage.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        stage.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
          
        stage.setCapability(BranchGroup.ALLOW_COLLISION_BOUNDS_READ);
        stage.setCapability(BranchGroup.ALLOW_COLLISION_BOUNDS_WRITE);
  
	stage.compile();
        
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

        Transform3D przesuniecie_obserwatora = new Transform3D();
        przesuniecie_obserwatora.set(new Vector3f(0.0f,(robotHeight+floorHeight)/2,3.5f));
        simpleU.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);
        simpleU.addBranchGraph(stage);
        
        OrbitBehavior orbitBeh = new OrbitBehavior(canvas3D, OrbitBehavior.REVERSE_ROTATE);
        orbitBeh.setSchedulingBounds(new BoundingSphere());
        simpleU.getViewingPlatform().setViewPlatformBehavior(orbitBeh);
              mainTransform = simpleU.getViewingPlatform().getViewPlatformTransform();
  
        timer = new Timer();
        timer.scheduleAtFixedRate(new Task(),0,25);
        
        
        
    }

    public static void main(String[] args) {
        
        Robot2 robo = new Robot2();
 
    }
    
    /**
     * Creates main BranchGroup with every Java3D component
     * 
     * @return main Branchgroup of program
     */
     private BranchGroup utworzScene() 
     {
            BranchGroup Stage = new BranchGroup();
            
            BoundingSphere bounds = new BoundingSphere();
            bounds.setRadius(1.5);
            Lights(Stage,bounds);
            
           
        //  FLOOR
         
      Appearance aFloor = new Appearance();
      Material mFloor = new Material();
      
      Texture2D tFloor = TextureMaker("pafcy.jpg");
      
      aFloor.setMaterial(mFloor);
      aFloor.setTexture(tFloor);
      
      floor = new Cylinder(1.2f, floorHeight,Cylinder.GENERATE_NORMALS | Cylinder.GENERATE_TEXTURE_COORDS,aFloor);
      
      mainTransform = new TransformGroup();
      AllowBlock(mainTransform, true);
      mainTransform.addChild(floor);
      
      //   CORE
      
      Appearance aArm = new Appearance();
      Material mArm = new Material();
      
      
      Texture2D tArm = TextureMaker("robot4.jpg");
      
      aArm.setMaterial(mArm);
      aArm.setTexture(tArm);
      
      
      Appearance aRobot = new Appearance();
      Material mRobot = new Material();
      
      
      Texture2D tRobot = TextureMaker("robot3.jpg");
      
      aRobot.setMaterial(mRobot);
      aRobot.setTexture(tRobot);
      
      core = new Cylinder(robotRadius, robotHeight,Cylinder.GENERATE_NORMALS | Cylinder.GENERATE_TEXTURE_COORDS,aArm);
      
      Transform3D transCore = new Transform3D();
      transCore.set(new Vector3f(0.0f,(robotHeight+floorHeight)/2,0.0f));
      
      TransformGroup translateCore = new TransformGroup(transCore);
      translateCore.addChild(core);
      
      mainTransform.addChild(translateCore);
     
    //  BASE
    
       MyBox base = new MyBox(0.3f,0.01f,0.3f,Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS,aRobot);
       Transform3D transBase = new Transform3D();
       transBase.set(new Vector3f(0.0f,floorHeight,0.0f));
       TransformGroup translateBase = new TransformGroup(transBase);
       translateBase.addChild(base);
       mainTransform.addChild(translateBase);
       
     
    //   RING
    
      ring = new MyBox (ringLenght, ringHeight, ringWidth,Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS,aRobot);
      transRing = new Transform3D();
      transRing.set(new Vector3f(ring.getLenght()/2,0.0f,0.0f));
      ring.setXPos(ring.getLenght()/2);
      translateRing = new TransformGroup(transRing);
         AllowBlock(translateRing, true);
      translateRing.addChild(ring);
      
      
      rotRing = new Transform3D();
      rotateRing = new TransformGroup(rotRing);
         AllowBlock(rotateRing, true);
      rotateRing.addChild(translateRing);
      
      translateCore.addChild(rotateRing);
      
      //  ARM
      
      arm = new MyCylinder(0.07f, ring.getLenght()*2 - robotRadius*2 - 0.05f,Cylinder.GENERATE_NORMALS | Cylinder.GENERATE_TEXTURE_COORDS,aArm);
      
      Transform3D rotTemplate = new Transform3D();
      rotTemplate.rotZ((double)Math.PI/2);
      
      Transform3D rot = new Transform3D();
      rot.mul(rotTemplate);
      
      TransformGroup rotation1 = new TransformGroup(rot);
      rotation1.addChild(arm);
      
      transArm = new Transform3D();
      transArm.set(new Vector3f(ring.getXPos(),0.0f,0.0f));
      
      arm.setXPos(ring.getXPos());
      
      translateArm = new TransformGroup(transArm);
         AllowBlock(translateArm, true);
      translateArm.addChild(rotation1);
      
      translateRing.addChild(translateArm);
      
      //   WRIST
      
      wrist = new MyCylinder(0.1f, 0.01f,Cylinder.GENERATE_NORMALS | Cylinder.GENERATE_TEXTURE_COORDS,aRobot);
        
      TransformGroup rotation2 = new TransformGroup(rot);
      rotation2.addChild(wrist);
      
      Transform3D transWrist = new Transform3D();
      transWrist.set(new Vector3f(arm.getHeight()/2,0.0f,0.0f));
      
      translateWrist = new TransformGroup(transWrist);
         AllowBlock(translateWrist, true);
      translateWrist.addChild(rotation2);
      
      translateArm.addChild(translateWrist);
  
      // PRYMITYW
      
      Appearance aBall = new Appearance();
      Material mBall = new Material();
      
      Texture2D tBall = TextureMaker("pilka.jpg");
      
      aBall.setMaterial(mBall);
      aBall.setTexture(tBall);
      
      primitive =  new MySphere(0.1f,Sphere.GENERATE_TEXTURE_COORDS|Sphere.GENERATE_NORMALS ,aBall);
      
      
      transPrimitive = new Transform3D();
         
      transPrimitive.set(new Vector3f(ring.getLenght() + 0.3f ,primitive.getRadius() + floorHeight/2 + 0.01f,0.0f));
      
      primitiveXPos = ring.getLenght() + 0.3f;
      primitiveRadius = ring.getLenght() + 0.3f;

      translatePrimitive = new TransformGroup(transPrimitive);
           AllowBlock(translatePrimitive, true);    
      translatePrimitive.addChild(primitive);
      
       primitiveBranch = new BranchGroup();
       
       primitiveBranch.setCapability(BranchGroup.ALLOW_DETACH);
       primitiveBranch.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
       primitiveBranch.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
       
       primitiveBranch.addChild(translatePrimitive);
       
                                
       // kolizja
        CollisionDetector cd = new CollisionDetector(primitive);
        cd.setSchedulingBounds(bounds);
        
      
     
        Stage.addChild(mainTransform);
        Stage.addChild(primitiveBranch);

        mainTransform.addChild(cd);
  
            return Stage;
            
     }
     
    private void Lights( BranchGroup bg, BoundingSphere bounds) {
            
      DirectionalLight lightD = new DirectionalLight();
      lightD.setInfluencingBounds(bounds);
      lightD.setDirection(new Vector3f(-0.1f, -0.3f,-1.0f));
      lightD.setColor(new Color3f(1.0f, 1.0f, 1.0f));
      bg.addChild(lightD);
      
      AmbientLight lightA = new AmbientLight();
      lightA.setInfluencingBounds(bounds);
      lightA.setColor(new Color3f(0.02f,0.7f,0.5f));
      bg.addChild(lightA);
    }
    
    private void AllowBlock( TransformGroup tg , boolean allowChildren) {
        
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        
        if(allowChildren)
        {
            tg.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
            tg.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
            tg.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        }            
    }
    
    private Texture2D TextureMaker(String source)
    {
        TextureLoader loader = new TextureLoader(source,this);
        ImageComponent2D image = loader.getImage();

        Texture2D t2D = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());

        t2D.setImage(0, image);
        t2D.setBoundaryModeS(Texture.WRAP);
        t2D.setBoundaryModeT(Texture.WRAP);
        
        return t2D;   
    }
       
    /**
     * Handles the upper buttons
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==start)
        {
             button[8] = true;

             isRecorded = true;

             this.requestFocus();

            
        }
             else if (e.getSource() == stop)
        {
            if(isRecorded)
            {
            button[8] = false;
            index = 0 ;
            button[9] = true;

            }

            this.requestFocus();

        }
        else if(e.getSource() == reset)
        {
             RobotReset();

            isRecorded = false; 

             this.requestFocus();

        }
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {

    }

    /**
     * Detects which button was pressed and activates this button
     * @param e KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_UP : button[0] = true; break;
            case KeyEvent.VK_DOWN : button[1] = true; break;
            case KeyEvent.VK_LEFT : button[2] = true; break;
            case KeyEvent.VK_RIGHT : button[3] = true; break;
            case KeyEvent.VK_Z : button[4] = true; break;       // slipping in
            case KeyEvent.VK_X : button[5] = true; break;   // ejecting
            case KeyEvent.VK_C : button[6] = true; break;        // catching
            case KeyEvent.VK_SPACE : button[7] = true; break;  // releasing
            case KeyEvent.VK_S : button[8] = true; break;   // start
            case KeyEvent.VK_Q : if(isRecorded) button[8] = false ; break;   // stop
            case KeyEvent.VK_W : RobotReset(); break;     // reset
        }
        
    }

    /**
     * Detects which button was released and deactivates this button
     * @param e KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
      switch(e.getKeyCode())
        {
            case KeyEvent.VK_UP : button[0] = false; break;
            case KeyEvent.VK_DOWN : button[1] = false; break;
            case KeyEvent.VK_LEFT : button[2] = false; break;
            case KeyEvent.VK_RIGHT : button[3] = false; break;
            case KeyEvent.VK_Z : button[4] = false; break;
            case KeyEvent.VK_X : button[5] = false; break;
            case KeyEvent.VK_C : button[6] = false; break; 
            case KeyEvent.VK_SPACE : button[7] = false; break;  
            case KeyEvent.VK_Q :  index = 0 ; button[9] = true; break;  
        }
    }
    
    /**
     * Resets recorded trajectory and allows to regain control over the robot
     */
    
    public void RobotReset()
    {
        isRecorded = false;
        button[9] = false; 
        trajectory.removeAll(trajectory);
        Trajectory.ID = 0;
        index = 0 ;
        for(int i = 0; i< button.length ; i++)
            button[i] = false;
    }
}
