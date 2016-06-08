
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
import static robot2.Robot2.przyciski;

/**
 *
 * @author przem0
 */

public class Robot2 extends JFrame implements ActionListener, KeyListener{

    
    public static BranchGroup scena;
  
    
    // up, down, left, roght, long, short, take, put
    public static boolean [] przyciski = new boolean[10];
    
    private Cylinder podloga;
    private Cylinder trzon;
    public static MyBox pierscien;
    public static MyCylinder ramie;
    public static MyCylinder kisc;
    
    public static MySphere prymityw;
    
    private TransformGroup glownaTrans;
    
    public static ArrayList <Trajektoria> trajektoria = new ArrayList<>() ;
    public static int index = 0;
    public static float robotHeight = 0.8f;
    public static float robotRadius = 0.08f;
    public static float groundHeight = 0.02f;
    
    private final float pierscienLenght = 0.3f ;
    private final float pierscienHeight = 0.1f;
    private final float pierscienWidth = 0.1f;
    
    public static Transform3D transPierscien;
    public static Transform3D rotPierscien;
    
    public static TransformGroup przesunPierscien;
    public static TransformGroup obrotPierscien;
    
    public static TransformGroup przesunRamie;
    public static Transform3D transRamie;
    
    public static TransformGroup przesunKisc;
    
    public static TransformGroup przesunPrym;
    public static TransformGroup obrotPrym;
    
    public static Transform3D rotPrym;
    public static Transform3D transPrym;
    
    public static boolean isCatched = false;
    
    public static float prymHeight;
    public static float prymXPos;
    public static float prymZPos;
     public static float prymRadius;
     
    public static boolean isFallingDown = false;
    
    public static boolean allowMoveDown = true;
    public static boolean allowMoveThrough = false;
    public static boolean allowMoveLeft = false;
    public static boolean allowMoveRight = false;
    public static boolean allowMoveBack = false;
    
    public static boolean allowCatch = false;
    
    public static BranchGroup prym;
    public static Sphere s;
    
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

        scena = utworzScene();
        
        scena.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        scena.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
        scena.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
          
        scena.setCapability(BranchGroup.ALLOW_COLLISION_BOUNDS_READ);
        scena.setCapability(BranchGroup.ALLOW_COLLISION_BOUNDS_WRITE);
  
	scena.compile();
        
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

        Transform3D przesuniecie_obserwatora = new Transform3D();
        przesuniecie_obserwatora.set(new Vector3f(0.0f,(robotHeight+groundHeight)/2,3.5f));
        simpleU.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);
        simpleU.addBranchGraph(scena);
        
        // obrot kamery
        OrbitBehavior orbitBeh = new OrbitBehavior(canvas3D, OrbitBehavior.REVERSE_ROTATE);
        orbitBeh.setSchedulingBounds(new BoundingSphere());
        simpleU.getViewingPlatform().setViewPlatformBehavior(orbitBeh);
              glownaTrans = simpleU.getViewingPlatform().getViewPlatformTransform();
  
        // 40 razy na sekunde wykonuje to co w klasie zadanie
        timer = new Timer();
        timer.scheduleAtFixedRate(new Task(),0,25);
        
        
        
    }

    public static void main(String[] args) {
        
        Robot2 robo = new Robot2();
 
    }

    
     private BranchGroup utworzScene() 
     {
            BranchGroup Scena = new BranchGroup();
            
            BoundingSphere bounds = new BoundingSphere();
            bounds.setRadius(1.5);
            Lights(Scena,bounds);
            
           
        // PODŁOGA
         
      Appearance aPodloga = new Appearance();
      Material mPodloga = new Material();
      
      Texture2D tPodloga = TextureMaker("pafcy.jpg");
      
      aPodloga.setMaterial(mPodloga);
      aPodloga.setTexture(tPodloga);
      
      podloga = new Cylinder(1.2f, groundHeight,Cylinder.GENERATE_NORMALS | Cylinder.GENERATE_TEXTURE_COORDS,aPodloga);
      
      glownaTrans = new TransformGroup();
      AllowBlock(glownaTrans, true);
      glownaTrans.addChild(podloga);
      
      //   TRZON
      
      Appearance aRamie = new Appearance();
      Material mRamie = new Material();
      
      
      Texture2D tRamie = TextureMaker("robot4.jpg");
      
       aRamie.setMaterial(mRamie);
      aRamie.setTexture(tRamie);
      
      
      Appearance aRobot = new Appearance();
      Material mRobot = new Material();
      
      
      Texture2D tRobot = TextureMaker("robot3.jpg");
      
      aRobot.setMaterial(mRobot);
      aRobot.setTexture(tRobot);
      
      trzon = new Cylinder(robotRadius, robotHeight,Cylinder.GENERATE_NORMALS | Cylinder.GENERATE_TEXTURE_COORDS,aRamie);
      
      Transform3D transTrzon = new Transform3D();
      transTrzon.set(new Vector3f(0.0f,(robotHeight+groundHeight)/2,0.0f));
      
      TransformGroup przesunTrzon = new TransformGroup(transTrzon);
      przesunTrzon.addChild(trzon);
      
      glownaTrans.addChild(przesunTrzon);
     
    //  PODSTAWA
       MyBox podstawa = new MyBox(0.3f,0.01f,0.3f,Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS,aRobot);
       Transform3D transPodst = new Transform3D();
       transPodst.set(new Vector3f(0.0f,groundHeight,0.0f));
       TransformGroup przesunPodst = new TransformGroup(transPodst);
       przesunPodst.addChild(podstawa);
       glownaTrans.addChild(przesunPodst);
    
     
    //   PIERSCIEN
    
      pierscien = new MyBox (pierscienLenght, pierscienHeight, pierscienWidth,Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS,aRobot);
      transPierscien = new Transform3D();
      transPierscien.set(new Vector3f(pierscien.getLenght()/2,0.0f,0.0f));
      pierscien.setXPos(pierscien.getLenght()/2);
      przesunPierscien = new TransformGroup(transPierscien);
         AllowBlock(przesunPierscien, true);
      przesunPierscien.addChild(pierscien);
      
      
      rotPierscien = new Transform3D();
      obrotPierscien = new TransformGroup(rotPierscien);
         AllowBlock(obrotPierscien, true);
      obrotPierscien.addChild(przesunPierscien);
      
      przesunTrzon.addChild(obrotPierscien);
      
      //  RAMIE
      

      ramie = new MyCylinder(0.07f, pierscien.getLenght()*2 - robotRadius*2 - 0.05f,Cylinder.GENERATE_NORMALS | Cylinder.GENERATE_TEXTURE_COORDS,aRamie);
      
      Transform3D tempRot = new Transform3D();
      tempRot.rotZ((double)Math.PI/2);
      
      Transform3D poczRot = new Transform3D();
      poczRot.mul(tempRot);
      
      TransformGroup obrotPocz = new TransformGroup(poczRot);
      obrotPocz.addChild(ramie);
      
      transRamie = new Transform3D();
      transRamie.set(new Vector3f(pierscien.getXPos(),0.0f,0.0f));
      
      ramie.setXPos(pierscien.getXPos());
      
      przesunRamie = new TransformGroup(transRamie);
         AllowBlock(przesunRamie, true);
      przesunRamie.addChild(obrotPocz);
      
      przesunPierscien.addChild(przesunRamie);
      
      //   KISC
      
      kisc = new MyCylinder(0.1f, 0.01f,Cylinder.GENERATE_NORMALS | Cylinder.GENERATE_TEXTURE_COORDS,aRobot);
        
      TransformGroup obrotPocz2 = new TransformGroup(poczRot);
      obrotPocz2.addChild(kisc);
      
      Transform3D transKisc = new Transform3D();
      transKisc.set(new Vector3f(ramie.getHeight()/2,0.0f,0.0f));
      
      przesunKisc = new TransformGroup(transKisc);
         AllowBlock(przesunKisc, true);
      przesunKisc.addChild(obrotPocz2);
      
      przesunRamie.addChild(przesunKisc);
  
      // PRYMITYW
      
      Appearance aPilka = new Appearance();
      Material mPilka = new Material();
      
      Texture2D tPilka = TextureMaker("pilka.jpg");
      
      aPilka.setMaterial(mPilka);
      aPilka.setTexture(tPilka);
      
      prymityw =  new MySphere(0.1f,Sphere.GENERATE_TEXTURE_COORDS|Sphere.GENERATE_NORMALS ,aPilka);
      
      
      
      transPrym = new Transform3D();
         
      transPrym.set(new Vector3f(pierscien.getLenght() + 0.3f ,prymityw.getRadius() + groundHeight/2 + 0.01f,0.0f));
      
      prymXPos = pierscien.getLenght() + 0.3f;
      // po to by nie mozna było lapac kiscia ktora jest za prymitywem
      prymRadius = pierscien.getLenght() + 0.3f;

      przesunPrym = new TransformGroup(transPrym);
           AllowBlock(przesunPrym, true);    
      przesunPrym.addChild(prymityw);
      
       prym = new BranchGroup();
       
       prym.setCapability(BranchGroup.ALLOW_DETACH);
       prym.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
       prym.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
       
       prym.addChild(przesunPrym);
       
                                
       // kolizja
        CollisionDetector cd = new CollisionDetector(prymityw);
        cd.setSchedulingBounds(bounds);
        
      
     
        Scena.addChild(glownaTrans);
        Scena.addChild(prym);

        glownaTrans.addChild(cd);
  
            return Scena;
            
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
       
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==start)
        {
             przyciski[8] = true;

             isRecorded = true;

             this.requestFocus();

            
        }
             else if (e.getSource() == stop)
        {
            if(isRecorded)
            {
            przyciski[8] = false;
            index = 0 ;
            przyciski[9] = true;

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

    @Override
    public void keyPressed(KeyEvent e) {
        
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_UP : przyciski[0] = true; break;
            case KeyEvent.VK_DOWN : przyciski[1] = true; break;
            case KeyEvent.VK_LEFT : przyciski[2] = true; break;
            case KeyEvent.VK_RIGHT : przyciski[3] = true; break;
            case KeyEvent.VK_Z : przyciski[4] = true; break;       // wsuwanie
            case KeyEvent.VK_X : przyciski[5] = true; break;   // wysuwanie
            case KeyEvent.VK_C : przyciski[6] = true; break;        // lapanie
            case KeyEvent.VK_SPACE : przyciski[7] = true; break;  // puszczanie
            case KeyEvent.VK_S : przyciski[8] = true; break;   // start
            case KeyEvent.VK_Q : if(isRecorded) przyciski[8] = false ; break;   // stop
            case KeyEvent.VK_W : RobotReset(); break;     // reset
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
      switch(e.getKeyCode())
        {
            case KeyEvent.VK_UP : przyciski[0] = false; break;
            case KeyEvent.VK_DOWN : przyciski[1] = false; break;
            case KeyEvent.VK_LEFT : przyciski[2] = false; break;
            case KeyEvent.VK_RIGHT : przyciski[3] = false; break;
            case KeyEvent.VK_Z : przyciski[4] = false; break;
            case KeyEvent.VK_X : przyciski[5] = false; break;
            case KeyEvent.VK_C : przyciski[6] = false; break; 
            case KeyEvent.VK_SPACE : przyciski[7] = false; break;  
            case KeyEvent.VK_Q :  index = 0 ; przyciski[9] = true; break;  
        }
    }
    
    public void RobotReset()
    {
        isRecorded = false;
        przyciski[9] = false; 
        trajektoria.removeAll(trajektoria);
        Trajektoria.ID = 0;
        index = 0 ;
        for(int i = 0; i< przyciski.length ; i++)
            przyciski[i] = false;
    }
}
