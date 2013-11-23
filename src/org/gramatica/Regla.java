package org.gramatica;

/**
 * @author Jhon Garavito
 * Organización Uis-Grupo de investigacion teoria Caos
 * Año 2008 2009
 */
public class Regla
{
    private char simbIzq[];
    private char simbDer[];
    private char varEnv[];
    private float prob;
    public Regla()
    {
        simbIzq=new char[0];
        simbDer=new char[0];
        varEnv=new char[0];
        prob=0.0f;
    }
    public Regla(char simIzqArg[],char simDerArg[],char varEnvArg[],float probArg)
    {
        simbIzq=simIzqArg;
        simbDer=simDerArg;
        varEnv=varEnvArg;
        prob=probArg;
    }
    public void modSimbIzq(char simIzqArg[])
    {
        simbIzq=simIzqArg;
    }
    public void modSimbDer(char simDerArg[])
    {
        simbDer=simDerArg;
    }
    public void modVarEnv(char varEnvArg[])
    {
        varEnv=varEnvArg;
    }
    public void modProb(float probArg)
    {
        prob=probArg;
    }
    public char obtCarIzq(int ubi)
    {
        return simbIzq[ubi];
    }
    public char obtCarDer(int ubi)
    {
        return simbDer[ubi];
    }
    public char[] obtSimbIzq()
    {
        return simbIzq;
    }
    public char[] obtSimbDer()
    {
        return simbDer;
    }
    public int obtCantSimbIzq()
    {
        return simbIzq.length;
    }
    public int obtCantSimbDer()
    {
        return simbDer.length;
    }
    public char obtVarEnv()
    {
        return varEnv[0];
    }
    public float obtProb()
    {
        return prob;
    }
}
