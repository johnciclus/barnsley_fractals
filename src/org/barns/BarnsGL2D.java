package org.barns;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.nio.Buffer;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
/**
 * @author Jhon Garavito
 * Año 2011
 */

public class BarnsGL2D implements GLEventListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private boolean verEjes=false,clickDer=false,clickIzq=false,capImagen=false;
    private float ColFon[]=new float[3],ColEje[],xPos=0.0f,yPos=-1.0f,zPos=-4.0f,x=0,y=0,rotZ=0.0f;
    private Point2D datFrac[]=new Point2D[0];
    private Buffer pixels;
    public void init(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();
        gl.setSwapInterval(1);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH);
        
        initVar();
        drawable.addMouseListener(this);
        drawable.addMouseMotionListener(this);
        drawable.addMouseWheelListener(this);
    }
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
    {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { 
        
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 0.0f, 20.0f);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    public void display(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(ColFon[0], ColFon[1], ColFon[2], 1.0f);
        
        gl.glLoadIdentity();

        gl.glTranslatef(xPos, yPos, zPos);

        gl.glRotatef(rotZ, 0.0f, 0.0f, 1.0f);

        if(verEjes==true)
            verEjes(gl);
        if(capImagen==true)
        {
            gl.glDrawBuffer(gl.GL_BACK);
            dibFractal(gl);
            gl.glReadBuffer(gl.GL_BACK);
            gl.glReadPixels(0, 0, drawable.getWidth(), drawable.getHeight(), gl.GL_RGB, gl.GL_UNSIGNED_BYTE, pixels);
            capImagen=false;
        }
        else
            dibFractal(gl);
        gl.glFlush();
    }
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged)
    {
    }
    public void mouseClicked(MouseEvent e)
    {
       
    }
    public void mousePressed(MouseEvent e)
    {
        if(e.getButton()==MouseEvent.BUTTON1)
        {
            clickDer=true;
            x=e.getX();
            y=e.getY();
        }
        else if(e.getButton()==MouseEvent.BUTTON3)
        {
            clickIzq=true;
            x=e.getX();
            y=e.getY();
        }
    }
    public void mouseReleased(MouseEvent e)
    {
        clickDer=false;
        clickIzq=false;
    }
    public void mouseEntered(MouseEvent e)
    {
        
    }
    public void mouseExited(MouseEvent e)
    {
        
    }
    public void mouseDragged(MouseEvent e)
    {
        if(clickDer)
        {
            xPos+=(float)(e.getX()-x)/140;
            yPos+=(float)(y-e.getY())/140;
            x=e.getX();
            y=e.getY();
        }
        else if(clickIzq)
        {
            rotZ+=(float)(x-e.getX())+(float)(y-e.getY());
            if(rotZ%45<=3.0f&&rotZ%45>=0.0f)
                rotZ=rotZ-rotZ%45;
            else if((45-rotZ%45)<=3.0f&&(45-rotZ%45)>=0.0f)
                rotZ=rotZ+(45-rotZ%45);
            x=e.getX();
            y=e.getY();
        }
    }
    public void mouseMoved(MouseEvent e)
    {
        
    }
    public void mouseWheelMoved(MouseWheelEvent e)
    {
         if(zPos+0.1f*e.getWheelRotation()<=0.0f)
             zPos+=0.1f*e.getWheelRotation();
    }
    public void modFondo(float colfon[])
    {
        ColFon[0]=colfon[0];
        ColFon[1]=colfon[1];
        ColFon[2]=colfon[2];
    }
    public float obtXPos()
    {
        return xPos;
    }   
    public float obtYPos()
    {
      return yPos;
    }    
    public float obtZPos()
    {
      return zPos;
    }    
    public void modXPos(float x)
    {
      xPos=x;
    }    
    public void modYPos(float y)
    {
      yPos=y;
    }   
    public void modZPos(float z)
    {
      zPos=z;
    }
    public void modPuntos(Point2D puntArg[])
    {
        if(puntArg!=null)
            datFrac=puntArg;
    }
    public void obtImagen()
    {
        capImagen=true;
    }
    private void verEjes(GL gl)
    {
        gl.glBegin(GL.GL_LINES);
        gl.glColor4f(ColEje[0], ColEje[1], ColEje[2], 1.0f);

        gl.glVertex3f(-8.0f, 0.0f, 0.0f);
        gl.glVertex3f(8.0f, 0.0f, 0.0f);
        for(int i=-8;i<8;i++)
        {
            gl.glVertex3f(i, -0.05f, 0.0f);
            gl.glVertex3f(i, 0.05f, 0.0f);
        }
        for(float i=-8;i<8;i+=0.1)
        {
            gl.glVertex3f(i, -0.02f, 0.0f);
            gl.glVertex3f(i, 0.02f, 0.0f);
        }

        gl.glVertex3f(0.0f, -8.0f, 0.0f);
        gl.glVertex3f(0.0f, 8.0f, 0.0f);
        for(int i=-8;i<8;i++)
        {
            gl.glVertex3f(-0.05f, i, 0.0f);
            gl.glVertex3f(0.05f, i, 0.0f);
        }
        for(float i=-8;i<8;i+=0.1)
        {
            gl.glVertex3f( -0.02f, i, 0.0f);
            gl.glVertex3f( 0.02f, i, 0.0f);
        }
    gl.glEnd();
    }
    public void verEjes(boolean act,float colEje[])
    {
     verEjes=act;
     ColEje[0]=colEje[0];
     ColEje[1]=colEje[1];
     ColEje[2]=colEje[2];
    }
    private void dibFractal(GL gl)
    {
        gl.glBegin(GL.GL_POINTS);
        for(int i=0;i<datFrac.length;i++)
            {
            gl.glColor3f(datFrac[i].obtColR(), datFrac[i].obtColG(), datFrac[i].obtColB());
            gl.glVertex2f(datFrac[i].obtX(), datFrac[i].obtY());
            }
        gl.glEnd();
    }
    private void initVar()
    {
        ColFon=new float[3];
        ColEje=new float[3];
        for(int i=0;i<3;i++)
            ColFon[i]=0.0f;
        for(int i=0;i<3;i++)
            ColEje[i]=1.0f;

    }
}

