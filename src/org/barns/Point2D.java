package org.barns;

/**
 * @author Jhon Garavito
 * Año 2011
 */

public class Point2D
{
    private float x,y;
    private float color[]=new float[3];
    public Point2D()
    {
        x=0;
        y=0;
        color[0]=0.0f;
        color[1]=0.0f;
        color[2]=0.0f;
    }
    public Point2D(float xArg,float yArg,float col[])
    {
        x=xArg;
        y=yArg;
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
