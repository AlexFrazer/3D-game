package derp;
 
import derp.Point3D;
import derp.ThreeDimensionalKDTree;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
 
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JApplet;
import javax.swing.JOptionPane;
 
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.fixedfunc.GLLightingFunc;
 
 
public class ThreeDeeGame extends JApplet implements GLEventListener, KeyListener
{
    int winWidth=800, winHeight=600;
    Animator animator;
    GLU glu;
    GLUT glut;
    ArrayList<Point3D> lol = new ArrayList<Point3D>();
    //working.
    Camera cam = new Camera(2,2,0, 0,0,1, 0,1,0);
    public synchronized void keyPressed(final KeyEvent e) {
        if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W') {
          System.out.println("moved foward");
            cam.forward();
        }
 
        if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
          System.out.println("moved left");
            cam.left();
        }
 
        if (e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
        	System.out.println("moved back");
            cam.back();
        }
 
        if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
        	System.out.println("moved right");
            cam.right();
        }
    }
    public void keyReleased(KeyEvent e) {
       }
    public void keyTyped(KeyEvent e) {
       }
    public void display (GLAutoDrawable gld)
    {
        final GL2 gl = gld.getGL().getGL2();
 
        // Clear the buffer, need to do both the color and the depth buffers
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Load the identity into the Modelview matrix
        gl.glLoadIdentity();
        // Setup the camera. The camera is located at the origin, looking along the positive z-axis, with y-up
        cam.setLookAt(glu);
        // set the position and diffuse/ambient terms of the light
        float [] pos = {1, 1, -1, 0};
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_POSITION, pos, 0);
        float [] diffuse = {0.7f, 0.7f, 0.7f, 1.0f};
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_DIFFUSE, diffuse, 0);
        float [] ambient = {0.2f, 0.2f, 0.2f, 1.0f};
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_AMBIENT, ambient, 0);
        float [] specular = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_SPECULAR, specular, 0);
        
        // set the color
        float [] color1 = {0.0f, 1.0f, 0.0f, 1.0f};
        float [] color3 = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_AMBIENT_AND_DIFFUSE, color1, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_SPECULAR, color3, 0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_SHININESS, 64.0f);
        
        //upper left corner, clockwise.
        gl.glBegin(GL2.GL_QUADS); //floor A D C B
        	gl.glNormal3d(0.0, 1.0, 0.0);
        	gl.glVertex3d(-50.0, 0.0, -50.0); //A
            gl.glVertex3d(-50.0, 0.0, 50.0); //D
            gl.glVertex3d(50.0, 0.0, 50.0); //C
            gl.glVertex3d(50.0, 0.0, -50.0); //B
        gl.glEnd();
        gl.glBegin(GL2.GL_QUADS); //top A D C B
	    	gl.glNormal3d(0.0, -1.0, 0.0);
	    	gl.glVertex3d(-50.0, 100.0, -50.0); //A
	        gl.glVertex3d(-50.0, 100.0, 50.0); //D
	        gl.glVertex3d(50.0, 100.0, 50.0); //C
	        gl.glVertex3d(50.0, 100.0, -50.0); //B
        gl.glEnd();
        gl.glBegin(GL2.GL_QUADS); 
	        gl.glNormal3d(-1.0, 0.0, 0.0);
	    	gl.glVertex3d(-50.0, 100.0, 50.0);  //F
	    	gl.glVertex3d(-50.0, 0.0, 50.0);   //D
	    	gl.glVertex3d(50.0, 100.0, 50.0); //E
	    	gl.glVertex3d(50.0, 0.0, 50.0);  //C
        gl.glEnd();
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
    public void PlayMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    	AudioInputStream ais = AudioSystem.getAudioInputStream(new File("C:\\Test\\Assignment11\\Nevermore.wav"));
    	Clip clip = AudioSystem.getClip();
    	clip.open(ais);
    	clip.start();
    }
 
    public void init (GLAutoDrawable gld)
    {
    	JOptionPane.showMessageDialog(null, 
    								"Please turn on sound and put this java assignment in C:\\Test\\Assignment11",
    								"Artistic expression",
    								JOptionPane.OK_OPTION);
    	try {
			PlayMusic();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        glu = new GLU();
        glut = new GLUT();
        final GL2 gl = gld.getGL().getGL2();
        gl.glClearColor(0.8f, 0.8f, 0.8f, 1.0f);
        // setup the projection matrix
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60.0, 1.33, 0.0001, 2);
        
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glEnable(GLLightingFunc.GL_NORMALIZE); // automatically normalizes stuff
        gl.glEnable(GL.GL_CULL_FACE); // cull back faces
        gl.glEnable(GL.GL_DEPTH_TEST); // turn on z-buffer
        gl.glEnable(GLLightingFunc.GL_LIGHTING); // turn on lighting
        gl.glEnable(GLLightingFunc.GL_LIGHT0); // turn on light
        gl.glShadeModel(GLLightingFunc.GL_SMOOTH); // smooth normals
        
    }
    public void init() {
        setLayout(new FlowLayout());
        // create a gl drawing canvas
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);
        canvas.setPreferredSize(new Dimension(winWidth, winHeight));
        //animator = new Animator(canvas);
        Thread updateThread = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    try{
                        update();
                        Thread.sleep(20);
                    } catch(Exception e) {}
                }
            }
        });
        canvas.addGLEventListener(this);
        canvas.addKeyListener(this);
        add(canvas);
        setSize(winWidth, winHeight);
        // add the canvas to the frame
        animator = new Animator(canvas);
        canvas.requestFocusInWindow(); 
    }
    public synchronized void update() {
     ThreeDimensionalKDTree tree = new ThreeDimensionalKDTree();
     tree.buildTree(lol, 0);
    }
    public void start() {
        animator.setRunAsFastAsPossible(false);
        animator.start();
    }
    public void stop() {
        animator.stop();
    }
    public void dispose (GLAutoDrawable arg0)
    {
    }
    public class sphere {
    long lastTime;
     int radius=100;
     double x,y,z;
     double vx,vy,vz;
     double frcx, frcy, frcz;
     public void update()
     {
    	long dt = System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        // limit forces
        if (frcx > 0.1) frcx = 0.1;
        if (frcx < -0.1) frcx = -0.1;
        if (frcy > 0.1) frcy = 0.1;
        if (frcy < -0.1) frcy = -0.1;
        if (frcz < 0.1) frcz = 0.1;
        if (frcz > -0.1) frcz = -0.1;
        
        // integrate forces and velocities
        x = x + frcx*dt;
        y = y + frcy*dt;
        z = z + frcz*dt;
        
		if (x < radius) {
			x = radius;
			vx = -vx;
		}
		if (x > winWidth-radius) {
			x = winWidth-radius;
			vx = -vx;
		}
		if (y < radius) {
			y = radius;
			y = -vy;
		}
		if (y > winHeight-radius) {
			y = winHeight-radius;
			y = -vy;
		}
		if(z < radius) {
 
		}
		if(z > radius) {
 
		}
     }
    }
    public class Camera
    {
        double ex, ey, ez, cx, cy, cz, ux, uy, uz;
        double theta = 0.087266463;
            
        Camera(double ex, double ey, double ez, double cx, double cy, double cz, double ux, double uy, double uz) {
			this.ex = ex;
			this.ey = ey;
			this.ez = ez;
			this.cx = cx;
			this.cy = cy;
			this.cz = cz;
			this.ux = ux;
			this.uy = uy;
			this.uz = uz;
        }
        
        void setLookAt(GLU glu) {
         glu.gluLookAt(ex, ey, ez, cx, cy, cz, ux, uy, uz);
        }
            
        void forward() {
	     double dx = cx - ex, dy = cy - ey, dz = cz - ez;
	     double m = Math.sqrt((dx*dx)+(dy*dy)+(dz*dz));
		dx /= m/2;
		dy /= m/2;
		dz /= m/2;
 
		cx += dx;
		cy += dy;
		cz += dz;
		ex += dx;
		ey += dy;
		ez += dz;
        }
            
        void back() {
     double dx = cx - ex, dy = cy - ey, dz = cz - ez;
     double m = Math.sqrt((dx*dx)+(dy*dy)+(dz*dz));
			dx /= m/2;
			dy /= m/2;
			dz /= m/2;
 
			cx -= dx;
			cy -= dy;
			cz -= dz;
			ex -= dx;
			ey -= dy;
			ez -= dz;
        }
            
        void left() {
     double dx = cx - ex, dy = cy - ey, dz = cz - ez;
     double m = Math.sqrt((dx*dx)+(dy*dy)+(dz*dz));
			dx /= m;
			dy /= m;
			dz /= m;
			double nx = Math.cos(theta)*dx + Math.sin(theta)*dz;
			double ny = dy;
			double nz = -Math.sin(theta)*dx + Math.cos(theta)*dz;
			cx = ex+nx;
			cy = ey+ny;
			cz = ez+nz;
        }
 
        void right() {
     double dx = cx - ex, dy = cy - ey, dz = cz - ez;
     double m = Math.sqrt((dx*dx)+(dy*dy)+(dz*dz));
			dx /= m;
			dy /= m;
			dz /= m;
			double nx = Math.cos(theta)*dx - Math.sin(theta)*dz;
			double ny = dy;
			double nz = Math.sin(theta)*dx + Math.cos(theta)*dz;
			cx = ex+nx;
			cy = ey+ny;
			cz = ez+nz;
        }
    }
}
