package org.barns;

import java.util.Random;
/**
 * @author Jhon Garavito
 * Organización Uis-Grupo de investigacion teoria Caos
 * Año 2008 2009
 */
public class Fractal3D
{
    private boolean colDeg=true,puntosGen=false;
    private float trans[][];
    private int maxInter,transAct;
    private float colTrans[][];
    public Point3D vectPunt[];
    private float colFon[];

    public Fractal3D(int cantTrans, int cantInter)
    {
        transAct=cantTrans;
        maxInter=cantInter;
        trans=new float[cantTrans][13];
        colTrans=new float[cantTrans][3];

        for(int i=0;i<cantTrans;i++)
            for(int j=0;j<13;j++)
                trans[i][j]=0.0f;
        for(int i=0;i<cantTrans;i++)
            for(int j=0;j<3;j++)
            colTrans[i][j]=0.0f;
        vectPunt=new Point3D[cantInter];
    }
    public Fractal3D(int cantTrans, int cantInter,float transArg[][],float trasCol[][],float colFonArg[])
    {
        transAct=cantTrans;
        maxInter=cantInter;
        colTrans=new float[cantTrans][3];
        trans=transArg;
        colTrans=trasCol;
        vectPunt=new Point3D[cantInter];
        colFon=colFonArg;
    }
    public void genPuntos(char cadCaracteres[])
    {
        char alfaMin[]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        maxInter=cadCaracteres.length;
        int i=0,j=0,k=0;
        float x=0,y=0,z=0;
        float nx,ny,nz;
        vectPunt=new Point3D[maxInter];
        float colAux[]=colTrans[0];

        for(i=0;i<maxInter;i++)
        {
            for(j=0;j<alfaMin.length;j++)
                if(alfaMin[j]==cadCaracteres[i])
                {
                    k=j;
                    j=alfaMin.length;
                }
            nx=trans[k][0]*x+trans[k][1]*y+trans[k][2]*z+trans[k][9];
            ny=trans[k][3]*x+trans[k][4]*y+trans[k][5]*z+trans[k][10];
            nz=trans[k][6]*x+trans[k][7]*y+trans[k][8]*z+trans[k][11];
            x=nx;
            y=ny;
            z=nz;
            colAux=(colDeg)?Utiles.comColor(colTrans[k],colAux):colTrans[k];
            vectPunt[i]=new Point3D(nx,ny,nz,colAux);
        }
        puntosGen=true;
    }
    public void genPuntos(boolean actColDeg)
    {
        colDeg=actColDeg;
        int k=0,j=0;
        float x=0,y=0,z=0;
        float nx,ny,nz;
        float suma;
        float aux;
        Random rd1=new Random();
        vectPunt=new Point3D[maxInter];
        
        float colAux[]=colTrans[0];

        for(int i=0;i<maxInter;i++)
        {
            suma=0;
            aux=rd1.nextFloat();
            for(j=0;j<transAct;j++)
            {
                suma+=trans[j][12];
                if(aux<suma)
                {
                   k=j;
                   j=transAct;
                }
            }
            nx=trans[k][0]*x+trans[k][1]*y+trans[k][2]*z+trans[k][9];
            ny=trans[k][3]*x+trans[k][4]*y+trans[k][5]*z+trans[k][10];
            nz=trans[k][6]*x+trans[k][7]*y+trans[k][8]*z+trans[k][11];
            x=nx;
            y=ny;
            z=nz;
            colAux=(colDeg)?Utiles.comColor(colTrans[k],colAux):colTrans[k];
            vectPunt[i]=new Point3D(nx,ny,nz,colAux);
        }
        puntosGen=true;
    }
    public void libPuntos()
    {
        vectPunt=new Point3D[0];
        puntosGen=false;
    }
    public void modDatoTrans(int i,int j,float cant)
    {
      if(i<26&&j<13)
      {
        trans[i][j]=cant;
        genPuntos(colDeg);
      }
    }
    public void modDatFractal(int cantTrans,int cantInter,float transArg[][],float trasCol[][])
    {
      maxInter=cantInter;
      transAct=cantTrans;
      trans=transArg;
      colTrans=trasCol;
      genPuntos(colDeg);
    }
    public void modCantTrans(int cantTrans)
    {
        transAct=cantTrans;
        float transAux[][]=new float[cantTrans][13];
        float colTransAux[][]=new float[cantTrans][3];

        if(cantTrans<26)
        {
          if(trans.length>=transAux.length)
          {
          for(int i=0;i<transAux.length;i++)
          {
                for(int j=0;j<13;j++)
                {
                    transAux[i][j]=trans[i][j];
                }
                colTransAux[i]=colTrans[i];
          }
          }
          else
          {
            for(int i=0;i<trans.length;i++)
            {
                for(int j=0;j<13;j++)
                {
                    transAux[i][j]=trans[i][j];
                }
                colTransAux[i]=colTrans[i];
            }
            for(int i=0;i<(transAux.length-trans.length);i++)
            {
                for(int j=0;j<13;j++)
                {
                    transAux[trans.length+i][j]=0.0f;
                }
                for(int j=0;j<3;j++)
                    colTransAux[trans.length+i][j]=0.0f;
            }
          }
          trans=transAux;
          colTrans=colTransAux;
          genPuntos(colDeg);
          }
      }
    public void modVecColTrans(float vectColArg[][])
    {
      if(vectColArg.length==colTrans.length)
      {
          colTrans=vectColArg;
          genPuntos(colDeg);
      }
    }
    public void modColTrans(float colArg[],int i)
    {
        if(i<colTrans.length)
        {
            colTrans[i]=colArg;
            genPuntos(colDeg);
        }
    }
    public void modMaxInter(int cantInter)
    {
        if(cantInter<=500000)
        {
            maxInter=cantInter;
            vectPunt=new Point3D[cantInter];
            genPuntos(colDeg);
        }
    }
    public void modColFon(float[] colArg)
    {
      colFon=colArg;
    }
    public float[][] obtTransMat()
    {
        return trans;
    }
    public float[][] obtColTrans()
    {
        return colTrans;
    }
    public float[] obtColFon()
    {
      return colFon;
    }
    public int obtMaxInter()
    {
        return maxInter;
    }
    public int obtCantTrans()
    {
        return transAct;
    }
    public float[] obtProbabilidad()
    {
        float prob[]=new float[transAct];
        for(int i=0;i<transAct;i++)
            prob[i]=trans[i][12];
        return prob;
    }
    public Point3D[] obtPuntos()
    {
        return vectPunt;
    }
    public Point3D[] obtPuntosOpt(boolean actColDeg)
    {
        if(puntosGen)
            return vectPunt;
        else
        {
            genPuntos(actColDeg);
            return vectPunt;
        }
    }

}
