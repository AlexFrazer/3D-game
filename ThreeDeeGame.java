package Gaem;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
import javax.swing.JApplet;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;


public class ThreeDeeGame extends JApplet implements GLEventListener, MouseListener, MouseMotionListener, KeyListener
{
	private static final long serialVersionUID = 1L;

	double sqr(double x) {
		return x*x;
	}
    
    int winWidth=700, winHeight=525;
    Thread updateThread;
    Animator animator;
    GLU glu;
    GLUT glut;
    Gaem.Camera camera;
    
    public ThreeDeeGame() {
        camera = new Camera(0,25,0, 0,25,1, 0,1,0);
    }
    
    public synchronized void update() {
    }
    
    public synchronized void display (GLAutoDrawable gld)
    {
        final GL2 gl = gld.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        camera.setLookAt(glu);
        float [] pos = {0, 75, 0, 1.0f};
        gl.glLightfv(GLLightingFunc.GL_LIGHT1, GLLightingFunc.GL_POSITION, pos, 0);
        float [] diffuse = {0.7f, 0.7f, 0.7f, 1.0f};
        gl.glLightfv(GLLightingFunc.GL_LIGHT1, GLLightingFunc.GL_DIFFUSE, diffuse, 0);
        float [] ambient = {0.2f, 0.2f, 0.2f, 1.0f};
        gl.glLightfv(GLLightingFunc.GL_LIGHT1, GLLightingFunc.GL_AMBIENT, ambient, 0);
        
        gl.glBegin(GL2.GL_QUADS);
        float [] color1 = {0.5f, 0.8f, 1.0f, 1.0f};
        float [] color2 = {0.6f, 0.8f, 0.7f, 1.0f};
        float [] color3 = {1.0f, 1.0f, 0.8f, 1.0f};

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_AMBIENT_AND_DIFFUSE, color1, 0);
        gl.glNormal3d(0,0,1);
        gl.glVertex3d(-100.0, 0.0, -100.0);
        gl.glVertex3d(100.0, 0.0, -100.0);
        gl.glVertex3d(100.0, 100.0, -100.0);
        gl.glVertex3d(-100.0, 100.0, -100.0);

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_AMBIENT_AND_DIFFUSE, color3, 0);
        gl.glNormal3d(0,1,0);
        gl.glVertex3d(-100.0, 0.0, -100.0);
        gl.glVertex3d(-100.0, 0.0, 100.0);
        gl.glVertex3d(100.0, 0.0, 100.0);
        gl.glVertex3d(100.0, 0.0, -100.0);

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_AMBIENT_AND_DIFFUSE, color2, 0);
        gl.glNormal3d(1,0,0);
        gl.glVertex3d(-100.0, 0.0, -100.0);
        gl.glVertex3d(-100.0, 100.0, -100.0);
        gl.glVertex3d(-100.0, 100.0, 100.0);
        gl.glVertex3d(-100.0, 0.0, 100.0);

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_AMBIENT_AND_DIFFUSE, color1, 0);
        gl.glNormal3d(0,0,-1);
        gl.glVertex3d(-100.0, 0.0, 100.0);
        gl.glVertex3d(-100.0, 100.0, 100.0);
        gl.glVertex3d(100.0, 100.0, 100.0);
        gl.glVertex3d(100.0, 0.0, 100.0);

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_AMBIENT_AND_DIFFUSE, color3, 0);
        gl.glNormal3d(0,-1,0);
        gl.glVertex3d(-100.0, 100.0, -100.0);
        gl.glVertex3d(100.0, 100.0, -100.0);
        gl.glVertex3d(100.0, 100.0, 100.0);
        gl.glVertex3d(-100.0, 100.0, 100.0);

        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_AMBIENT_AND_DIFFUSE, color2, 0);
        gl.glNormal3d(-1,0,0);
        gl.glVertex3d(100.0, 0.0, -100.0);
        gl.glVertex3d(100.0, 0.0, 100.0);
        gl.glVertex3d(100.0, 100.0, 100.0);
        gl.glVertex3d(100.0, 100.0, -100.0);

        gl.glEnd();

        gl.glFlush();
        gld.swapBuffers();
        try {
            Thread.sleep(1);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
    
    public void displayChanged (GLAutoDrawable gld, boolean arg1, boolean arg2)
    {
    }
    
    public void reshape (GLAutoDrawable gld, int x, int y, int width, int height)
    {
        GL gl = gld.getGL();
        winWidth = width;
        winHeight = height;
        gl.glViewport(0,0, width, height);
    }
    
    public void mouseClicked (MouseEvent e) {}
    public void mouseEntered (MouseEvent e) {}
    public void mouseExited (MouseEvent e) {}
    public void mousePressed (MouseEvent e) {}
    public void mouseReleased (MouseEvent e) {}
    public void mouseDragged (MouseEvent e){}
    public void keyReleased (KeyEvent e){}
    public void keyTyped (KeyEvent e) {}
    
    public void mouseMoved (MouseEvent e)
    {
    }
    
    public synchronized void keyPressed (KeyEvent e)
    {
    	if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W') {
            System.out.println("moved foward");
              camera.forward();
          }
   
          if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
          	System.out.println("moved left");
              camera.left();
          }
   
          if (e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
          	System.out.println("moved back");
              camera.back();
          }
   
          if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
          	System.out.println("moved right");
              camera.right();
          }
    }
    
    public void init (GLAutoDrawable gld)
    {
        glu = new GLU();
        glut = new GLUT();
        final GL2 gl = gld.getGL().getGL2();
        gl.glClearColor(0.8f, 0.8f, 0.8f, 1.0f);
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60.0, 1.33, 0.01, 1000);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glEnable(GLLightingFunc.GL_NORMALIZE);
        gl.glEnable(GL.GL_CULL_FACE);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GLLightingFunc.GL_LIGHTING);
        gl.glEnable(GLLightingFunc.GL_LIGHT1);
        gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
        
        gld.setAutoSwapBufferMode(false);
    }
    public void init() {
        setLayout(new FlowLayout());
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);
        canvas.setPreferredSize(new Dimension(winWidth, winHeight));
       
        canvas.addGLEventListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addKeyListener(this);
        add(canvas);
        setSize(winWidth, winHeight);
        animator = new Animator(canvas);
        updateThread = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    try{
                        update();
                        Thread.sleep(2);
                    } catch(Exception e) {}
                }
            }
        });
    }
    public void start() {
        animator.setRunAsFastAsPossible(false);
        animator.start();
        updateThread.setPriority(Thread.MAX_PRIORITY);
        updateThread.start();
    } 
    
    public void stop() {
        animator.stop();
        updateThread.interrupt();
    }
    public void dispose (GLAutoDrawable arg0)
    {
    }
}
