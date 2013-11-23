package org.gramatica;

/**
 * @author Jhon Garavito
 * Organización Uis-Grupo de investigacion teoria Caos
 * Año 2008 2009
 */
public class Variable
{
    private char varAct;
    private Regla reglas[];
    public Variable(char varArg,Regla reglasArg[])
    {
        varAct=varArg;
        reglas=reglasArg;
    }
    public void modVarAct(char varActArg)
    {
        varAct=varActArg;
    }
    public void modReglas(Regla reglasArg[])
    {
        reglas=reglasArg;
    }
    public char obtVarAct()
    {
        return varAct;
    }
    public Regla[] obtReglas()
    {
        return reglas;
    }
    public int cantReglas()
    {
        return reglas.length;
    }
    public boolean compSumaProb()
    {
        float suma=0;
        for(int i=0;i<reglas.length;i++)
            suma+=reglas[i].obtProb();
        if(suma==1.0f)
            return true;
        else
            return false;
    }
    public float obtSumaProb()
    {
        float suma=0;
        for(int i=0;i<reglas.length;i++)
            suma+=reglas[i].obtProb();
        return suma;
    }
}
