package org.barns;

/**
 * @author Jhon Garavito
 * Organización Uis-Grupo de investigacion teoria Caos
 * Año 2008 2009
 */
public class Point3D
{
    private float x,y,z;
    private float color[]=new float[3];
    public Point3D()
    {
        x=0;
        y=0;
        z=0;
        color[0]=0.0f;
        color[1]=0.0f;
        color[2]=0.0f;
    }
    public Point3D(float xArg,float yArg,float zArg,float col[])
    {
        x=xArg;
        y=yArg;
        z=zArg;
        color=col;
    }
    public void modX(float xArg)
    {
        x=xArg;
    }
    public void modY(float yArg)
    {
        y=yArg;
    }
    public void modZ(float zArg)
    {
        z=zArg;
    }
    public void modColor(float col[])
    {
        color=col;
    }
    public float obtX()
    {
        return x;
    }
    public float obtY()
    {
        return y;
    }
    public float obtZ()
    {
        return z;
    }
    public float[] obtColor()
    {
        return color;
    }
    public float obtColR()
    {
        return color[0];
    }
    public float obtColG()
    {
        return color[1];
    }
    public float obtColB()
    {
        return color[2];
    }
}
