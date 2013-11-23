package org.gramatica;

import java.text.DecimalFormat;
import java.util.Random;
import org.barns.Utiles;

/**
 * @author Jhon Garavito
 * Organización Uis-Grupo de investigacion teoria Caos
 * Año 2008 2009
 */
public class Gramatica
{
    private Variable varGrama[];
    //private List<String> Errores;
    private char cadCaracteres[];
    private char alfaMay[]={'S','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','T','U','V','W','X','Y','Z'};

    public Gramatica(String regVar[],DecimalFormat formateador)
    {    
        varGrama=new Variable[regVar.length];
        String reg[];
        Regla reglas[];

        for(int i=0;i<varGrama.length;i++)
        {
            regVar[i]=regVar[i].replace('|', '/');            
            reg=regVar[i].split(String.valueOf('/'));

            reglas=new Regla[reg.length];
            for(int j=0;j<reg.length;j++)
            {
                reglas[j]=Utiles.convRegla(reg[j]);
            }
            varGrama[i]=new Variable(alfaMay[i],reglas);
        
            if(!varGrama[i].compSumaProb())
            {
                //Errores.add("Error en comprobación de Probabilidad, Probabilidad = "+varGrama[i].obtSumaProb());
            }
        }
    }
    public void modGramatica(Variable varGraArg[])
    {
        varGrama=varGraArg;
    }
    public Variable[] obtGramatica()
    {
        return varGrama;
    }
    public char[] genCad(int cantIter)
    {
        cadCaracteres=new char[cantIter];
        char cadCaracIzq[]=new char[cadCaracteres.length];
        char cadCaracDer[]=new char[cadCaracteres.length];
        
        char var='S';
        int cantDatIzq=0,cantDatDer=0,i=0,j=0,k=0;
        float suma=0.0f,aux=0.0f;
        boolean cadLista=false;
        Regla reg[];
        Random rd=new Random();
        while((cadCaracteres.length>(cantDatIzq+cantDatDer))&&!cadLista)
        {
            for(i=0;i<varGrama.length;i++)
            {
                if(var==varGrama[i].obtVarAct())
                {
                    suma=0.0f;
                    aux=rd.nextFloat();
                    reg=varGrama[i].obtReglas();
                    for(j=0;j<reg.length;j++)
                    {
                        suma+=reg[j].obtProb();
                        if(aux<suma)
                        {
                            if((cadCaracteres.length-(cantDatIzq+cantDatDer))>=(reg[j].obtCantSimbIzq()+reg[j].obtCantSimbDer()))
                            {
                                var=reg[j].obtVarEnv();
                                for(k=0;k<reg[j].obtCantSimbIzq();k++)
                                {
                                    cadCaracIzq[cantDatIzq]=reg[j].obtCarIzq(k);
                                    cantDatIzq++;
                                }
                                for(k=0;k<reg[j].obtCantSimbDer();k++)
                                {
                                    cadCaracDer[cantDatDer]=reg[j].obtCarDer(k);
                                    cantDatDer++;
                                }
                            }
                            else
                            {
                                cadLista=true;
                            }
                            j=reg.length;
                        }
                    }
                    i=varGrama.length;
                }
            }
        }
        cadCaracteres=new char[cantDatIzq+cantDatDer];
        for(i=0;i<cantDatIzq;i++)
            cadCaracteres[i]=cadCaracIzq[i];
        for(i=0;i<cantDatDer;i++)
            cadCaracteres[cantDatIzq+i]=cadCaracDer[cantDatDer-i-1];
        return cadCaracteres;
    }
}
