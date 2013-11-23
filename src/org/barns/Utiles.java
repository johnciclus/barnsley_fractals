package org.barns;


import java.awt.Color;
import java.text.DecimalFormat;
import org.gramatica.Regla;

/**
 * @author Jhon Garavito
 * Organización Uis-Grupo de investigacion teoria Caos
 * Año 2008 2009
 */

public class Utiles
{
    private static char alfaMin[]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private static char alfaMay[]={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    
    public static float[] invColor(float col1[])
    {
        float col[]=new float[3];
        col[0]=1.0f-col1[0];
        col[1]=1.0f-col1[1];
        col[2]=1.0f-col1[2];
        return col;
    }
    public static float[] comColor(float col1[],float col2[])
    {
        float col[]=new float[3];
        col[0]=(col1[0]+col2[0])/2;
        col[1]=(col1[1]+col2[1])/2;
        col[2]=(col1[2]+col2[2])/2;
        return col;
    }
    public static Color obtColor(float col1[])
    {
        return new java.awt.Color(col1[0],col1[1],col1[2]);
    }
    public static float[] obtColor(Color col1)
    {
        float colVect[]={(float)col1.getRed()/255,(float)col1.getGreen() /255,(float)col1.getBlue()/255};
        return colVect;
    }
    public static float compDispPor(float prob[])
    {
        float suma=0;
        for(int i=0;i<prob.length;i++)
            suma+=prob[i];
        return (1-suma);
    }
    public static float[] adqPorcentajes(float Trans[][])
    {
        float probAux[]=new float[Trans.length];
        for(int i=0;i<Trans.length;i++)
                probAux[i]=Trans[i][6];
        return probAux;
    }
    public static String[] detControlGrama(float TransProb[],DecimalFormat formateador)
    {
        String reg[]=new String[1];
        reg[0]="";
        for(int i=0;i<TransProb.length-1;i++)
                reg[0]+=String.valueOf(alfaMin[i])+"S"+"["+formateador.format(TransProb[i])+"]|";
        reg[0]+=String.valueOf(alfaMin[TransProb.length-1])+"S"+"["+formateador.format(TransProb[TransProb.length-1])+"]";
        return reg;
    }
    public static Regla convRegla(String reg)
    {   
        char simbIzq[];
        char simbDer[];
        char varEnv[];
        float prob;
        int ubi=0;
        boolean stop;

        prob=Float.valueOf(reg.substring(reg.indexOf("[")+1,reg.indexOf("]")).replace(',', '.'));
        reg=reg.substring(0,reg.indexOf("["));
        
        for(int i=0;i<reg.length();i++)
        {
            stop=false;
            for(int j=0;j<alfaMay.length;j++)
            {
                if(reg.charAt(i)==alfaMay[j])
                {
                    stop=true;
                    j=alfaMay.length;
                }
            }
            if(stop)
            {
                ubi=i;
                i=reg.length();
            }
            else if(!stop&&i==reg.length()-1)
            {
                ubi=i+1;
            }
        }

        simbIzq=reg.substring(0,ubi).toCharArray();
        reg=reg.substring(ubi,reg.length());
        ubi=0;

        for(int i=0;i<reg.length();i++)
        {
            stop=false;
            for(int j=0;j<alfaMin.length;j++)
            {
                if(reg.charAt(i)==alfaMin[j])
                {
                    stop=true;
                    j=alfaMin.length;
                }
            }
            if(stop)
            {
                ubi=i;
                i=reg.length();
            }
            else if(!stop&&i==reg.length()-1)
            {
                ubi=i+1;
            }
        }

        varEnv=reg.substring(0,ubi).toCharArray();
        reg=reg.substring(ubi,reg.length());
        ubi=0;

        for(int i=0;i<reg.length();i++)
        {
            stop=false;
            for(int j=0;j<alfaMay.length;j++)
            {
                if(reg.charAt(i)==alfaMay[j])
                {
                    stop=true;
                    j=alfaMay.length;
                }
            }
            if(stop)
            {
                ubi=i;
                i=reg.length();
            }
            else if(!stop&&i==reg.length()-1)
            {
                ubi=i+1;
            }
        }
        simbDer=reg.substring(0,ubi).toCharArray();
        return new Regla(simbIzq,simbDer,varEnv,prob);
    }
}
