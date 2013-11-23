package org.barns;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
/**
 * @author Jhon Garavito
 * Organización Uis-Grupo de investigacion teoria Caos
 * Año 2008 2009 2010
 */

public class BarnsGL3D implements GLEventListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private boolean verEjes=false,clickDer=false,clickIzq=false,clickCen=false;
    private float ColFon[]=new float[3],ColEje[],xPos=0.0f,yPos=0.0f,zPos=0.0f,x=0,y=0,rotX=0.0f,rotY=0.0f,rotZ=0.0f;
    private Point3D datFrac[]=new Point3D[0];

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

        gl.glRotatef(rotX, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotY, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(rotZ, 0.0f, 0.0f, 1.0f);
        
        if(verEjes==true)
            verEjes(gl);

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
        else if(e.getButton()==MouseEvent.BUTTON2)
        {
            clickCen=true;
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
        clickCen=false;
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
        else if(clickCen)
        {
            rotZ+=(float)(x-e.getX())+(float)(y-e.getY());
            if(rotZ%45<=3.0f&&rotZ%45>=0.0f)
                rotZ=rotZ-rotZ%45;
            else if((45-rotZ%45)<=3.0f&&(45-rotZ%45)>=0.0f)
                rotZ=rotZ+(45-rotZ%45);
            x=e.getX();
            y=e.getY();
        }
        else if(clickIzq)
        {
            rotX+=(float)(e.getY()-y);
            if(rotX%45<=3.0f&&rotX%45>=0.0f)
                rotX=rotX-rotX%45;
            else if((45-rotX%45)<=3.0f&&(45-rotX%45)>=0.0f)
                rotX=rotX+(45-rotX%45);
            rotY+=(float)(e.getX()-x);
            if(rotY%45<=3.0f&&rotY%45>=0.0f)
                rotY=rotY-rotY%45;
            else if((45-rotY%45)<=3.0f&&(45-rotY%45)>=0.0f)
                rotY=rotY+(45-rotY%45);
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
    public void modPuntos(Point3D puntArg[])
    {
        if(puntArg!=null)
            datFrac=puntArg;
    }
    private void verEjes(GL gl)
    {
        gl.glBegin(GL.GL_LINES);
        gl.glColor4f(ColEje[0], ColEje[1], ColEje[2], 1.0f);

        gl.glVertex3f(-5.0f, 0.0f, 0.0f);
        gl.glVertex3f(5.0f, 0.0f, 0.0f);
        for(int i=-5;i<5;i++)
        {
            gl.glVertex3f(i, -0.05f, 0.0f);
            gl.glVertex3f(i, 0.05f, 0.0f);
        }
        for(int i=-5;i<5;i++)
        {
            gl.glVertex3f(i, 0.0f, -0.05f);
            gl.glVertex3f(i, 0.0f, 0.05f);
        }
        for(float i=-5;i<5;i+=0.1)
        {
            gl.glVertex3f(i, -0.02f, 0.0f);
            gl.glVertex3f(i, 0.02f, 0.0f);
        }
        for(float i=-5;i<5;i+=0.1)
        {
            gl.glVertex3f(i, 0.0f, -0.02f);
            gl.glVertex3f(i, 0.0f, 0.02f);
        }

        gl.glVertex3f(0.0f, -5.0f, 0.0f);
        gl.glVertex3f(0.0f, 5.0f, 0.0f);
        for(int i=-5;i<5;i++)
        {
            gl.glVertex3f(-0.05f, i, 0.0f);
            gl.glVertex3f(0.05f, i, 0.0f);
        }
        for(int i=-5;i<5;i++)
        {
            gl.glVertex3f(0.0f, i, -0.05f);
            gl.glVertex3f(0.0f, i, 0.05f);
        }
        for(float i=-5;i<5;i+=0.1)
        {
            gl.glVertex3f( -0.02f, i, 0.0f);
            gl.glVertex3f( 0.02f, i, 0.0f);
        }
        for(float i=-5;i<5;i+=0.1)
        {
            gl.glVertex3f( 0.0f, i, -0.02f);
            gl.glVertex3f( 0.0f, i, 0.02f);
        }
        
        gl.glVertex3f(0.0f, 0.0f, -5.0f);
        gl.glVertex3f(0.0f, 0.0f, 5.0f);
        for(int i=-5;i<5;i++)
        {
            gl.glVertex3f(-0.05f,0.0f,i);
            gl.glVertex3f(0.05f,0.0f,i);
        }
        for(int i=-5;i<5;i++)
        {
            gl.glVertex3f(0.0f,-0.05f,i);
            gl.glVertex3f(0.0f,0.05f,i);
        }
        for(float i=-5;i<5;i+=0.1)
        {
            gl.glVertex3f( -0.02f, 0.0f,i);
            gl.glVertex3f( 0.02f, 0.0f,i);
        }
        for(float i=-5;i<5;i+=0.1)
        {
            gl.glVertex3f( 0.0f,-0.02f,i);
            gl.glVertex3f( 0.0f,0.02f,i);
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
            gl.glVertex3f(datFrac[i].obtX(), datFrac[i].obtY(),datFrac[i].obtZ());
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
        xPos=0.0f;
        yPos=0.0f;
        zPos=-4.0f;
    }


}