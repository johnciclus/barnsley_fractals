package org.barns;

import com.sun.opengl.util.Animator;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.help.CSH;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import org.barns.AcercaDe;
import org.barns.BarnsGL2D;
import org.barns.BarnsGL3D;
import org.barns.Fractal2D;
import org.barns.Fractal3D;
import org.barns.SelecGrama;
import org.barns.Utiles;
import org.help.BarnsHelp;
import org.gramatica.Gramatica;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.skin.MistAquaSkin;
import org.jvnet.substance.watermark.SubstanceStripeWatermark;
/**
 * @author Jhon Garavito
 * Organizaci?n Uis-Grupo de investigacion teoria Caos
 * A?o 2008 2009
 */

public class BarnsForm extends JFrame {

    private GLCanvas canvas2D,canvas3D;
    private Animator animator;
    private BarnsGL2D barnsGL2D=new BarnsGL2D();
    private BarnsGL3D barnsGL3D=new BarnsGL3D();
    private float colEje2D[],colFon2D[]=new float[3];
    private float colEje3D[],colFon3D[]=new float[3];
    private BarnsHelp help;
    private DecimalFormatSymbols Format;
    private DecimalFormat formateador;

    private JSpinner jSpinButMat2D[][];
    private JLabel jLabTit2D[];
    private JButton jBtnCol2D[];
    private JSpinner jSpinButMat3D[][];
    private JLabel jLabTit3D[];
    private JButton jBtnCol3D[];
    private JLabel jLabGra[];
    private JTextField jText[];
    private ButtonGroup groupVisu;
    private ButtonGroup groupPant;

    private boolean modoInactivo2D=false;
    private boolean modoInactivo3D=false;
    private boolean verEjes2D=false;
    private boolean verEjes3D=false;
    private boolean colDeg2D=true;
    private boolean colDeg3D=true;
    private boolean actGramatica=false;
    private int valRecSelFract2D[]=new int[3];
    private int valRecSelFract3D[]=new int[3];
    private char alfaMin[]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private char alfaMay[]={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private String stgFiles2D[]=new String[0];
    private String stgFiles3D[]=new String[0];

    private Fractal2D listFract2D[];
    private Fractal3D listFract3D[];
    private Gramatica gramatica;
    
    public BarnsForm()
    {
        addGL();
        addProperty();
        initComponents();
        addSpinnersTrans2D();
        addSpinnersTrans3D();
        addControlGramatical();
        iniObjetos();  
    }
    public static void main(final String[] args)
    {
        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new Runnable() {
        public void run() {
        SubstanceLookAndFeel.setSkin(new MistAquaSkin()
           .withWatermark(new SubstanceStripeWatermark()));
        new BarnsForm().run(args);
        }
        });
    }
    public void run(final String[] args)
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new Dimension((int) (d.width * 0.8), (int) (d.height * 0.9)));
        setLocationRelativeTo(null);
        setVisible(true);
        validate();
        animator.start();
    }
    private GLCapabilities createGLCapabilites()
    {
        GLCapabilities capabilities = new GLCapabilities();
        capabilities.setHardwareAccelerated(true);
        capabilities.setNumSamples(2);
        capabilities.setSampleBuffers(true);
        return capabilities;
    }
    public void addGL()
    {
        canvas2D = new GLCanvas(createGLCapabilites());
        canvas2D.addGLEventListener(barnsGL2D);
        canvas2D.setMinimumSize(new Dimension());
        canvas3D = new GLCanvas(createGLCapabilites());
        canvas3D.addGLEventListener(barnsGL3D);
        canvas3D.setMinimumSize(new Dimension());
        animator = new Animator(canvas2D);
        animator.add(canvas3D);
    }
    public void addProperty()
    {
        setTitle("Barnsley Fractals - El explorador de fractales");
    }
    public void addSpinnersTrans2D()
    {
        jSpinButMat2D=new JSpinner[26][7];

        for(int i=0;i<26;i++)
        {
            for(int j=0;j<6;j++)
            {
                jSpinButMat2D[i][j]= new JSpinner();
                jSpinButMat2D[i][j].setName("SpinMat2D["+i+","+j+"]");
                jSpinButMat2D[i][j].setModel(new SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(-2.0f), Float.valueOf(2.0f), Float.valueOf(0.05f)));
                jSpinButMat2D[i][j].setEnabled(false);
                jSpinButMat2D[i][j].addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinButMat2DStateChanged(evt);
            }
        });
            }
            for(int j=6;j<7;j++)
            {
                jSpinButMat2D[i][j]= new JSpinner();
                jSpinButMat2D[i][j].setName("SpinMat2D["+i+","+j+"]");
                jSpinButMat2D[i][j].setModel(new SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.01f)));
                jSpinButMat2D[i][j].setEnabled(false);
                jSpinButMat2D[i][j].addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinButMat2DStateChanged(evt);
            }
        });
            }
        }
        jLabTit2D=new JLabel[34];
        for(int i=0;i<26;i++)
        {
            jLabTit2D[i]=new JLabel();
            jLabTit2D[i].setName("jLbTrans"+i);
            jLabTit2D[i].setText("Trans "+(i+1)+" ["+alfaMin[i]+"]");
        }
        for(int i=26;i<34;i++)
        {
            jLabTit2D[i]=new JLabel();
            jLabTit2D[i].setName("jLbTit"+i);
        }

        jLabTit2D[26].setText("Trans. X1");
        jLabTit2D[27].setText("Trans. Y1");
        jLabTit2D[28].setText("Trans. X2");
        jLabTit2D[29].setText("Trans. Y2");
        jLabTit2D[30].setText(" Desp. X ");
        jLabTit2D[31].setText(" Desp. Y ");
        jLabTit2D[32].setText(" Proba. ");
        jLabTit2D[33].setText(" Color. ");

        jBtnCol2D=new JButton[26];
        for(int i=0;i<26;i++)
        {
            jBtnCol2D[i]=new JButton();
            jBtnCol2D[i].setName("jBtn2D["+i+"]");
            jBtnCol2D[i].setText("Col "+alfaMin[i]);
            jBtnCol2D[i].setEnabled(false);
            jBtnCol2D[i].addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jBtnCol2DSelectionColor(evt);
                }
            });
        }

        javax.swing.GroupLayout jPanelTransLayout = new javax.swing.GroupLayout(jPanelTrans2D);
        jPanelTrans2D.setLayout(jPanelTransLayout);
        GroupLayout.ParallelGroup parallelHor[]=new GroupLayout.ParallelGroup[9];

        for(int i=0;i<9;i++)
        parallelHor[i]=jPanelTransLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false);

        for(int i=0;i<26;i++)
        parallelHor[0].addComponent(jLabTit2D[i], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);

        for(int i=0;i<26;i++)
            parallelHor[1].addComponent(jSpinButMat2D[i][0], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[1].addComponent(jLabTit2D[26]);

        for(int i=0;i<26;i++)
            parallelHor[2].addComponent(jSpinButMat2D[i][1], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[2].addComponent(jLabTit2D[27]);

        for(int i=0;i<26;i++)
            parallelHor[3].addComponent(jSpinButMat2D[i][2], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[3].addComponent(jLabTit2D[28]);

        for(int i=0;i<26;i++)
            parallelHor[4].addComponent(jSpinButMat2D[i][3], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[4].addComponent(jLabTit2D[29]);

        for(int i=0;i<26;i++)
            parallelHor[5].addComponent(jSpinButMat2D[i][4], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[5].addComponent(jLabTit2D[30]);

        for(int i=0;i<26;i++)
            parallelHor[6].addComponent(jSpinButMat2D[i][5], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[6].addComponent(jLabTit2D[31]);

        for(int i=0;i<26;i++)
            parallelHor[7].addComponent(jSpinButMat2D[i][6], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[7].addComponent(jLabTit2D[32]);

        for(int i=0;i<26;i++)
            parallelHor[8].addComponent(jBtnCol2D[i], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[8].addComponent(jLabTit2D[33]);

        jPanelTransLayout.setHorizontalGroup
                (
            jPanelTransLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTransLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parallelHor[0]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[1]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[2]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[3]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[4]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[5]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[6]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[7]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[8]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            )
        );

        GroupLayout.ParallelGroup parallelVer[]=new GroupLayout.ParallelGroup[27];

        for(int i=0;i<27;i++)
        parallelVer[i]=jPanelTransLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);

        for(int i=26;i<34;i++)
            parallelVer[0].addComponent(jLabTit2D[i]);

        for(int i=0;i<26;i++)
            {
                parallelVer[i+1].addComponent(jLabTit2D[i]);
                    for(int j=0;j<7;j++)
                        parallelVer[i+1].addComponent(jSpinButMat2D[i][j], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
                    parallelVer[i+1].addComponent(jBtnCol2D[i]);
            }

        jPanelTransLayout.setVerticalGroup
                (
            jPanelTransLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup
            (
            jPanelTransLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parallelVer[0]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[1]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[2]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[3]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[4]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[5]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[6]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[7]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[8]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[9]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[10]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[11]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[12]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[13]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[14]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[15]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[16]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[17]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[18]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[19]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[20]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[21]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[22]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[23]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[24]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[25]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[26]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGap(80, 80, 80)
            )
        );
    }
    public void addSpinnersTrans3D()
    {
        jSpinButMat3D=new JSpinner[26][13];

        for(int i=0;i<26;i++)
        {
            for(int j=0;j<12;j++)
            {
                jSpinButMat3D[i][j]= new JSpinner();
                jSpinButMat3D[i][j].setName("SpinMat3D["+i+","+j+"]");
                jSpinButMat3D[i][j].setModel(new SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(-2.0f), Float.valueOf(2.0f), Float.valueOf(0.05f)));
                jSpinButMat3D[i][j].setEnabled(false);
                jSpinButMat3D[i][j].addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinButMat3DStateChanged(evt);
            }
        });
            }
            for(int j=12;j<13;j++)
            {
                jSpinButMat3D[i][j]= new JSpinner();
                jSpinButMat3D[i][j].setName("SpinMat3D["+i+","+j+"]");
                jSpinButMat3D[i][j].setModel(new SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.01f)));
                jSpinButMat3D[i][j].setEnabled(false);
                jSpinButMat3D[i][j].addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinButMat3DStateChanged(evt);
            }
        });
            }
        }

        jLabTit3D=new JLabel[40];
        for(int i=0;i<26;i++)
        {
            jLabTit3D[i]=new JLabel();
            jLabTit3D[i].setName("jLbTrans"+i);
            jLabTit3D[i].setText("Trans "+(i+1)+" ["+alfaMin[i]+"]");
        }
        for(int i=26;i<40;i++)
        {
            jLabTit3D[i]=new JLabel();
            jLabTit3D[i].setName("jLbTit"+i);
        }
        jLabTit3D[26].setText("Trans. X1");
        jLabTit3D[27].setText("Trans. Y1");
        jLabTit3D[28].setText("Trans. Z1");
        jLabTit3D[29].setText("Trans. X2");
        jLabTit3D[30].setText("Trans. Y2");
        jLabTit3D[31].setText("Trans. Z2");
        jLabTit3D[32].setText("Trans. X3");
        jLabTit3D[33].setText("Trans. Y3");
        jLabTit3D[34].setText("Trans. Z3");
        jLabTit3D[35].setText(" Desp. X ");
        jLabTit3D[36].setText(" Desp. Y ");
        jLabTit3D[37].setText(" Desp. Z ");
        jLabTit3D[38].setText(" Proba. ");
        jLabTit3D[39].setText(" Color. ");

        jBtnCol3D=new JButton[26];
        for(int i=0;i<26;i++)
        {
            jBtnCol3D[i]=new JButton();
            jBtnCol3D[i].setName("jBtn3D["+i+"]");
            jBtnCol3D[i].setText("Col "+alfaMin[i]);
            jBtnCol3D[i].setEnabled(false);
            jBtnCol3D[i].addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    jBtnCol3DSelectionColor(evt);
                }
            });
        }

        javax.swing.GroupLayout jPanelTransLayout = new javax.swing.GroupLayout(jPanelTrans3D);
        jPanelTrans3D.setLayout(jPanelTransLayout);
        GroupLayout.ParallelGroup parallelHor[]=new GroupLayout.ParallelGroup[15];

        for(int i=0;i<15;i++)
        parallelHor[i]=jPanelTransLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false);

        for(int i=0;i<26;i++)
            parallelHor[0].addComponent(jLabTit3D[i], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);

        for(int i=0;i<26;i++)
            parallelHor[1].addComponent(jSpinButMat3D[i][0], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[1].addComponent(jLabTit3D[26]);

        for(int i=0;i<26;i++)
            parallelHor[2].addComponent(jSpinButMat3D[i][1], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[2].addComponent(jLabTit3D[27]);

        for(int i=0;i<26;i++)
            parallelHor[3].addComponent(jSpinButMat3D[i][2], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[3].addComponent(jLabTit3D[28]);

        for(int i=0;i<26;i++)
            parallelHor[4].addComponent(jSpinButMat3D[i][3], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[4].addComponent(jLabTit3D[29]);

        for(int i=0;i<26;i++)
            parallelHor[5].addComponent(jSpinButMat3D[i][4], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[5].addComponent(jLabTit3D[30]);

        for(int i=0;i<26;i++)
            parallelHor[6].addComponent(jSpinButMat3D[i][5], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[6].addComponent(jLabTit3D[31]);

        for(int i=0;i<26;i++)
            parallelHor[7].addComponent(jSpinButMat3D[i][6], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[7].addComponent(jLabTit3D[32]);

        for(int i=0;i<26;i++)
            parallelHor[8].addComponent(jSpinButMat3D[i][7], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[8].addComponent(jLabTit3D[33]);

        for(int i=0;i<26;i++)
            parallelHor[9].addComponent(jSpinButMat3D[i][8], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[9].addComponent(jLabTit3D[34]);

        for(int i=0;i<26;i++)
            parallelHor[10].addComponent(jSpinButMat3D[i][9], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[10].addComponent(jLabTit3D[35]);

        for(int i=0;i<26;i++)
            parallelHor[11].addComponent(jSpinButMat3D[i][10], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[11].addComponent(jLabTit3D[36]);

        for(int i=0;i<26;i++)
            parallelHor[12].addComponent(jSpinButMat3D[i][11], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[12].addComponent(jLabTit3D[37]);

        for(int i=0;i<26;i++)
            parallelHor[13].addComponent(jSpinButMat3D[i][12], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[13].addComponent(jLabTit3D[38]);

        for(int i=0;i<26;i++)
            parallelHor[14].addComponent(jBtnCol3D[i], GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE);
        parallelHor[14].addComponent(jLabTit3D[39]);

        jPanelTransLayout.setHorizontalGroup
                (
            jPanelTransLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTransLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parallelHor[0]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[1]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[2]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[3]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[4]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[5]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[6]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[7]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[8]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[9]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[10]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[11]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[12]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[13]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[14]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            )
        );

        GroupLayout.ParallelGroup parallelVer[]=new GroupLayout.ParallelGroup[27];

        for(int i=0;i<27;i++)
        parallelVer[i]=jPanelTransLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);

        for(int i=26;i<40;i++)
            parallelVer[0].addComponent(jLabTit3D[i]);

        for(int i=0;i<26;i++)
            {
                parallelVer[i+1].addComponent(jLabTit3D[i]);
                    for(int j=0;j<13;j++)
                        parallelVer[i+1].addComponent(jSpinButMat3D[i][j], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
                    parallelVer[i+1].addComponent(jBtnCol3D[i]);
            }

        jPanelTransLayout.setVerticalGroup
                (
            jPanelTransLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup
            (
            jPanelTransLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parallelVer[0]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[1]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[2]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[3]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[4]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[5]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[6]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[7]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[8]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[9]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[10]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[11]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[12]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[13]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[14]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[15]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[16]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[17]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[18]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[19]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[20]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[21]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[22]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[23]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[24]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[25]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[26]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGap(80, 80, 80)
            )
        );
    }
    public void addControlGramatical()
    {
        jLabGra=new JLabel[26];
        jText=new JTextField[26];

        jLabGra[0]=new JLabel();
        jLabGra[0].setText(String.valueOf(alfaMay[18]));
        for(int i=1;i<19;i++)
        {
            jLabGra[i]=new JLabel();
            jLabGra[i].setText(String.valueOf(alfaMay[i-1]));
        }
        for(int i=19;i<26;i++)
        {
            jLabGra[i]=new JLabel();
            jLabGra[i].setText(String.valueOf(alfaMay[i]));
        }

        for(int i=0;i<26;i++)
        {
            jText[i]=new JTextField();
            jText[i].setName("jForText["+i+"]");
           
            jText[i].setEnabled(false);

            jText[i].addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jForTextActionPerformed(evt);
            }
            });
            jText[i].addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jForTextPropertyChange(evt);
            }
            });
        }

        javax.swing.GroupLayout jPanelGramaticalLayout = new javax.swing.GroupLayout(jPanelGramatical);
        jPanelGramatical.setLayout(jPanelGramaticalLayout);
        GroupLayout.ParallelGroup parallelHor[]=new GroupLayout.ParallelGroup[2];

        for(int i=0;i<2;i++)
        parallelHor[i]=jPanelGramaticalLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false);

        for(int i=0;i<26;i++)
            parallelHor[0].addComponent(jLabGra[i], GroupLayout.DEFAULT_SIZE, 5, Short.MAX_VALUE);

        for(int i=0;i<26;i++)
            parallelHor[1].addComponent(jText[i], GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE);

        jPanelGramaticalLayout.setHorizontalGroup
                (
            jPanelGramaticalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGramaticalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parallelHor[0]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelHor[1]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            )
        );

        GroupLayout.ParallelGroup parallelVer[]=new GroupLayout.ParallelGroup[26];

        for(int i=0;i<26;i++)
            parallelVer[i]=jPanelGramaticalLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);

        for(int i=0;i<26;i++)
            {
                parallelVer[i].addComponent(jLabGra[i]);
                parallelVer[i].addComponent(jText[i]);
            }

        jPanelGramaticalLayout.setVerticalGroup
                (
            jPanelGramaticalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup
            (
            jPanelGramaticalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parallelVer[0]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[1]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[2]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[3]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[4]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[5]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[6]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[7]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[8]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[9]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[10]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[11]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[12]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[13]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[14]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[15]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[16]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[17]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[18]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[19]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[20]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[21]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[22]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[23]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[24]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(parallelVer[25]).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGap(80, 80, 80)
            )
        );
    }
    public void iniObjetos()
    {
        AcercaDe.setDefaultLookAndFeelDecorated(true);
        SelecGrama.setDefaultLookAndFeelDecorated(true);
        Format=new DecimalFormatSymbols();
        Format.setDecimalSeparator(',');
        formateador= new DecimalFormat("####.####",Format);

        colFon2D=new float[3];
        colFon2D[0]=0.0f;
        colFon2D[1]=0.0f;
        colFon2D[2]=0.0f;
        colFon3D=new float[3];
        colFon3D[0]=0.0f;
        colFon3D[1]=0.0f;
        colFon3D[2]=0.0f;
        
        valRecSelFract2D=new int[3];
        valRecSelFract3D=new int[3];
        for(int i=0;i<3;i++)
            valRecSelFract2D[i]=-1;
        for(int i=0;i<3;i++)
            valRecSelFract3D[i]=-1;
        
        String dir2D="Data"+File.separator+"Data2D";
        String dir3D="Data"+File.separator+"Data3D";
        String line;
        int inter=0;
        int cantTrans=0;
        float val;

        float ColorFon[]=new float[3];
        float transMat[][]=new float[26][7];
        float colTransFloat[][]=new float[26][3];
        
        File file;
        BufferedReader bufRead;
        
        File files2D=new File(dir2D);
        File files3D=new File(dir3D);

        if(files2D.isDirectory())
        {
            stgFiles2D=files2D.list();
            listFract2D=new Fractal2D[stgFiles2D.length];
            for(int i=0;i<stgFiles2D.length;i++)
            {
                file=new File(dir2D+File.separator+stgFiles2D[i]);
                if(file.isFile())
                {
                    try
                    {
                        bufRead = new BufferedReader(new FileReader(file));
                        line=bufRead.readLine();

                        inter=Integer.valueOf(line.substring(0,line.indexOf('\t')));
                        line=line.substring(line.indexOf('\t')+1,line.length());

                        cantTrans=Integer.valueOf(line.substring(0,line.indexOf('\t')));
                        line=line.substring(line.indexOf('\t')+1,line.length());
                        line=line.substring(line.indexOf('\t')+1,line.length());
                        line=line.substring(line.indexOf('\t')+1,line.length());
                        line=line.substring(line.indexOf('\t')+1,line.length());

                        transMat=new float[cantTrans][7];
                        colTransFloat=new float[cantTrans][3];

                        ColorFon=Utiles.obtColor(new Color(Integer.valueOf(line)));

                        for(int j=0;j<cantTrans;j++)
                        {
                            if(bufRead.ready())
                            {
                                line=bufRead.readLine();
                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][0]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][1]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][2]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][3]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][4]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][5]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][6]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                colTransFloat[j]=Utiles.obtColor(new Color(Integer.valueOf(line)));
                            }
                        }
                    }
                    catch (FileNotFoundException ex)
                    {

                    }
                    catch (IOException ex)
                    {

                    }
                }
                
                listFract2D[i]=new Fractal2D(cantTrans,inter,transMat,colTransFloat,ColorFon);
                stgFiles2D[i]=stgFiles2D[i].substring(0, stgFiles2D[i].length()-4);
            }        
        jListFrac.setModel(new javax.swing.AbstractListModel() {
            String[] strings = stgFiles2D;
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        }
        if(files3D.isDirectory())
        {
            stgFiles3D=files3D.list();
            listFract3D=new Fractal3D[stgFiles3D.length];
            for(int i=0;i<stgFiles3D.length;i++)
            {
                file=new File(dir3D+File.separator+stgFiles3D[i]);
                if(file.isFile())
                {
                    try
                    {
                        bufRead = new BufferedReader(new FileReader(file));
                        line=bufRead.readLine();

                        inter=Integer.valueOf(line.substring(0,line.indexOf('\t')));
                        line=line.substring(line.indexOf('\t')+1,line.length());

                        cantTrans=Integer.valueOf(line.substring(0,line.indexOf('\t')));
                        line=line.substring(line.indexOf('\t')+1,line.length());
                        line=line.substring(line.indexOf('\t')+1,line.length());
                        line=line.substring(line.indexOf('\t')+1,line.length());
                        line=line.substring(line.indexOf('\t')+1,line.length());

                        transMat=new float[cantTrans][13];
                        colTransFloat=new float[cantTrans][3];

                        ColorFon=Utiles.obtColor(new Color(Integer.valueOf(line)));

                        for(int j=0;j<cantTrans;j++)
                        {
                            if(bufRead.ready())
                            {
                                line=bufRead.readLine();
                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][0]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][1]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][2]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][3]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][4]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][5]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][6]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][7]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][8]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][9]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][10]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][11]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][12]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                colTransFloat[j]=Utiles.obtColor(new Color(Integer.valueOf(line)));
                            }
                        }
                    }
                    catch (FileNotFoundException ex)
                    {

                    }
                    catch (IOException ex)
                    {

                    }
                }
                listFract3D[i]=new Fractal3D(cantTrans,inter,transMat,colTransFloat,ColorFon);
                stgFiles3D[i]=stgFiles3D[i].substring(0, stgFiles3D[i].length()-4);
            }
        }
        help=new BarnsHelp("");
        jMenuItemHelp.addActionListener(new CSH.DisplayHelpFromSource(help.obtHelpBroker()));

        groupVisu= new ButtonGroup();
        groupVisu.add(jRadBtnGeo);
        groupVisu.add(jRadBtnCar);
        groupPant=new ButtonGroup();
        groupPant.add(jRadioButtonMenuItemPantalla);
        groupPant.add(jRadioButtonMenuItemVentana);
    }
    public void habFilasTrans2D(int cant)
    {
        if(cant>=0&&cant<=26)
        {
            for(int i=0;i<cant;i++)
                for(int j=0;j<7;j++)
                {
                    jSpinButMat2D[i][j].setEnabled(true);
                }
            
            for(int i=cant;i<26;i++)
                for(int j=0;j<7;j++)
                {
                    jSpinButMat2D[i][j].setEnabled(false);
                    jSpinButMat2D[i][j].setValue(0.0f);
                }
            
            for(int i=0;i<cant;i++)
            {
                jBtnCol2D[i].setEnabled(true);
            }

            for(int i=cant;i<26;i++)
            {
                jBtnCol2D[i].setEnabled(false);
                jBtnCol2D[i].setBackground(Color.white);
            }
        }
    }
    public void habFilasTrans3D(int cant)
    {
        if(cant>=0&&cant<=26)
        {
            for(int i=0;i<cant;i++)
                for(int j=0;j<13;j++)
                {
                    jSpinButMat3D[i][j].setEnabled(true);
                }

            for(int i=cant;i<26;i++)
                for(int j=0;j<13;j++)
                {
                    jSpinButMat3D[i][j].setEnabled(false);
                    jSpinButMat3D[i][j].setValue(0.0f);
                }

            for(int i=0;i<cant;i++)
            {
                jBtnCol3D[i].setEnabled(true);
            }

            for(int i=cant;i<26;i++)
            {
                jBtnCol3D[i].setEnabled(false);
                jBtnCol3D[i].setBackground(Color.white);
            }
        }
    }
    public void habFilasGrama(int cant)
    {
        if(cant>=0&&cant<=26)
        {
            for(int i=0;i<cant;i++)
                jText[i].setEnabled(true);

            for(int i=cant;i<26;i++)
            {
                jText[i].setEnabled(false);
                jText[i].setText("");
            }
        }
    }
    private void verificarEjes2D()
    {
        colEje2D=Utiles.invColor(colFon2D);
        barnsGL2D.verEjes(verEjes2D,colEje2D);
    }
    private void verificarEjes3D()
    {
        colEje3D=Utiles.invColor(colFon3D);
        barnsGL3D.verEjes(verEjes3D,colEje3D);
    }
    private void asigDatTrans2D(float transArg[][],float colTransArg[][])
    {
        for(int i=0;i<transArg.length;i++)
            for(int j=0;j<transArg[i].length;j++)
            {
                jSpinButMat2D[i][j].setValue(transArg[i][j]);
            }
        for(int i=0;i<colTransArg.length;i++)
            jBtnCol2D[i].setBackground(Utiles.obtColor(colTransArg[i]));
    }
    private void asigDatTrans3D(float transArg[][],float colTransArg[][])
    {
        for(int i=0;i<transArg.length;i++)
            for(int j=0;j<transArg[i].length;j++)
            {
                jSpinButMat3D[i][j].setValue(transArg[i][j]);
            }
        for(int i=0;i<colTransArg.length;i++)
            jBtnCol3D[i].setBackground(Utiles.obtColor(colTransArg[i]));
    }
    private void asigGrama(String reglas[])
    {
        jSpinnerGrama.setValue(reglas.length);
        for(int i=0;i<reglas.length;i++)
            jText[i].setText(reglas[i]);
    }
    private void asigFondo2D(Color colAux)
    {
        colFon2D=Utiles.obtColor(colAux);
        jBtnColFon.setBackground(Utiles.obtColor(colFon2D));
        barnsGL2D.modFondo(colFon2D);
    }
    private void asigFondo2D(float[] colAux)
    {
        colFon2D=colAux;
        jBtnColFon.setBackground(Utiles.obtColor(colFon2D));
        barnsGL2D.modFondo(colFon2D);
    }
    private void asigFondo3D(Color colAux)
    {
        colFon3D=Utiles.obtColor(colAux);
        jBtnColFon.setBackground(Utiles.obtColor(colFon3D));
        barnsGL3D.modFondo(colFon3D);
    }
    private void asigFondo3D(float[] colAux)
    {
        colFon3D=colAux;
        jBtnColFon.setBackground(Utiles.obtColor(colFon3D));
        barnsGL3D.modFondo(colFon3D);
    }
    private void asigDatos2D()
    {
        modoInactivo2D=true;
        barnsGL2D.modPuntos(listFract2D[valRecSelFract2D[0]].obtPuntosOpt(colDeg2D));
        jSpinnerIter.setValue(listFract2D[valRecSelFract2D[0]].obtMaxInter());
        jSpinnerCantTrans2D.setValue(listFract2D[valRecSelFract2D[0]].obtCantTrans());
        asigDatTrans2D(listFract2D[valRecSelFract2D[0]].obtTransMat(),listFract2D[valRecSelFract2D[0]].obtColTrans());
        asigFondo2D(listFract2D[valRecSelFract2D[0]].obtColFon());
        asigGrama(Utiles.detControlGrama(listFract2D[valRecSelFract2D[0]].obtProbabilidad(),formateador));
        modoInactivo2D=false;
    }
    private void asigDatos3D()
    {
        modoInactivo3D=true;
        barnsGL3D.modPuntos(listFract3D[valRecSelFract3D[0]].obtPuntosOpt(colDeg3D));
        jSpinnerIter.setValue(listFract3D[valRecSelFract3D[0]].obtMaxInter());
        jSpinnerCantTrans3D.setValue(listFract3D[valRecSelFract3D[0]].obtCantTrans());
        asigDatTrans3D(listFract3D[valRecSelFract3D[0]].obtTransMat(),listFract3D[valRecSelFract3D[0]].obtColTrans());
        asigFondo3D(listFract3D[valRecSelFract3D[0]].obtColFon());
        asigGrama(Utiles.detControlGrama(listFract3D[valRecSelFract3D[0]].obtProbabilidad(),formateador));
        modoInactivo3D=false;
    }
    private void crearGramatica2D()
    {
        if(actGramatica)
        {
            String reg[]=new String[Integer.valueOf(jSpinnerGrama.getValue().toString())];
            for(int i=0;i<reg.length;i++)
                reg[i]=jText[i].getText();
            gramatica=new Gramatica(reg,formateador);
            listFract2D[valRecSelFract2D[0]].genPuntos(gramatica.genCad(listFract2D[valRecSelFract2D[0]].obtMaxInter()));
            barnsGL2D.modPuntos(listFract2D[valRecSelFract2D[0]].obtPuntos());
        }
    }
    private void crearGramatica3D()
    {
        if(actGramatica)
        {
            String reg[]=new String[Integer.valueOf(jSpinnerGrama.getValue().toString())];
            for(int i=0;i<reg.length;i++)
                reg[i]=jText[i].getText();
            gramatica=new Gramatica(reg,formateador);
            listFract3D[valRecSelFract3D[0]].genPuntos(gramatica.genCad(listFract3D[valRecSelFract3D[0]].obtMaxInter()));
            barnsGL3D.modPuntos(listFract3D[valRecSelFract3D[0]].obtPuntos());
        }
    }
    private void jBtnCol2DSelectionColor(ActionEvent evt)
    {
        if(!modoInactivo2D)
        {
            String nomBtn=evt.toString().substring(evt.toString().indexOf("jBtn2D")+6,evt.toString().lastIndexOf("]")+1);
            int ubiCol=Integer.valueOf(nomBtn.substring(nomBtn.indexOf("[")+1,nomBtn.indexOf("]")));
            Color colAux=JColorChooser.showDialog(this, "Selector de color de transformaci?n", jBtnCol2D[ubiCol].getBackground());
            if(colAux!=null)
            {
                jBtnCol2D[ubiCol].setBackground(colAux);
                if(valRecSelFract2D[0]>=0)
                {
                    listFract2D[valRecSelFract2D[0]].modColTrans(Utiles.obtColor(colAux), ubiCol);
                    barnsGL2D.modPuntos(listFract2D[valRecSelFract2D[0]].obtPuntos());
                }
            }
        }
    }
    private void jBtnCol3DSelectionColor(ActionEvent evt)
    {
        if(!modoInactivo3D)
        {
            String nomBtn=evt.toString().substring(evt.toString().indexOf("jBtn3D")+6,evt.toString().lastIndexOf("]")+1);
            int ubiCol=Integer.valueOf(nomBtn.substring(nomBtn.indexOf("[")+1,nomBtn.indexOf("]")));
            Color colAux=JColorChooser.showDialog(this, "Selector de color de transformaci?n", jBtnCol3D[ubiCol].getBackground());
            if(colAux!=null)
            {
                jBtnCol3D[ubiCol].setBackground(colAux);
                if(valRecSelFract3D[0]>=0)
                {
                    listFract3D[valRecSelFract3D[0]].modColTrans(Utiles.obtColor(colAux), ubiCol);
                    barnsGL3D.modPuntos(listFract3D[valRecSelFract3D[0]].obtPuntos());
                }
            }
        }
    }
    private void jSpinButMat2DStateChanged(ChangeEvent evt)
    {
        if(!modoInactivo2D)
        {
            String nomBtn=evt.toString().substring(evt.toString().indexOf("SpinMat2D")+9,evt.toString().indexOf("]")+1);
            int iIdx=Integer.valueOf(nomBtn.substring(nomBtn.indexOf("[")+1,nomBtn.indexOf(",")));
            int jIdx=Integer.valueOf(nomBtn.substring(nomBtn.indexOf(",")+1,nomBtn.indexOf("]")));
            float val=Float.parseFloat(jSpinButMat2D[iIdx][jIdx].getValue().toString());
            if(valRecSelFract2D[0]>=0)
            {
                if(jIdx==6)
                {
                    /*
                    //float dispPor=Utiles.compDispPor(Utiles.adqPorcentajes(listFract2D[valRecSelFract2D[0]].obtTransMat()));
                    if(dispPor==0)
                    {
                        for(int i=0;i<26;i++)
                            jSpinButMat2D[i][6].setModel(new SpinnerNumberModel(Float.valueOf(jSpinButMat2D[i][6].getValue().toString()), Float.valueOf(0.0f),Float.valueOf(jSpinButMat2D[i][6].getValue().toString()) , Float.valueOf(0.01f)));
                    }
                    else if(dispPor<0)
                    {
                     * float dispPorPart=Float.valueOf(jSpinButMat2D[iIdx][6].getValue().toString())-listFract2D[valRecSelFract2D[0]].obtTransMat()[iIdx][6];
                        //jSpinButMat2D[iIdx][6].setValue(val+dispPor);
                        //for(int i=0;i<26;i++)
                        //    jSpinButMat2D[i][6].setModel(new SpinnerNumberModel(Float.valueOf(jSpinButMat2D[i][6].getValue().toString()), Float.valueOf(0.0f),Float.valueOf(jSpinButMat2D[i][6].getValue().toString()) , Float.valueOf(0.01f)));
                        
                    }
                    else if(dispPor>0)
                    {
                        //jSpinButMat2D[iIdx][jIdx].setModel(new SpinnerNumberModel(Float.valueOf(val), Float.valueOf(0.0f), Float.valueOf(val+dispPor), Float.valueOf(0.01f)));
                        //val=0.0f;
                        //jSpinButMat2D[iIdx][jIdx].setValue(val);
                    }*/
                }
                listFract2D[valRecSelFract2D[0]].modDatoTrans(iIdx, jIdx,val);
                barnsGL2D.modPuntos(listFract2D[valRecSelFract2D[0]].obtPuntos());
            }
        }
    }
    private void jSpinButMat3DStateChanged(ChangeEvent evt)
    {
        if(!modoInactivo3D)
        {
            String nomBtn=evt.toString().substring(evt.toString().indexOf("SpinMat3D")+9,evt.toString().indexOf("]")+1);
            int iIdx=Integer.valueOf(nomBtn.substring(nomBtn.indexOf("[")+1,nomBtn.indexOf(",")));
            int jIdx=Integer.valueOf(nomBtn.substring(nomBtn.indexOf(",")+1,nomBtn.indexOf("]")));
            float val=Float.parseFloat(jSpinButMat3D[iIdx][jIdx].getValue().toString());

        if(valRecSelFract3D[0]>=0)
        {
            if(jIdx==12)
            {
                float dispPor=Utiles.compDispPor(Utiles.adqPorcentajes(listFract3D[valRecSelFract3D[0]].obtTransMat()));
                if(dispPor<=0)
                {
                    val=0.0f;
                    jSpinButMat3D[iIdx][jIdx].setValue(val);
                }
            }
            listFract3D[valRecSelFract3D[0]].modDatoTrans(iIdx, jIdx,val);
            barnsGL3D.modPuntos(listFract3D[valRecSelFract3D[0]].obtPuntos());
        }
        }
    }
    private void jForTextActionPerformed(ActionEvent evt)
    {
    }
    private void jForTextPropertyChange(PropertyChangeEvent evt)
    {
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPanePrincipal2D = new javax.swing.JSplitPane();
        jPanelHerr = new javax.swing.JPanel();
        jTogButVerEjes = new javax.swing.JToggleButton();
        jSliderZoom = new javax.swing.JSlider();
        jLbZoom = new javax.swing.JLabel();
        jLbMen = new javax.swing.JLabel();
        jLbMas = new javax.swing.JLabel();
        jLbColFon = new javax.swing.JLabel();
        jLbCenCoord = new javax.swing.JLabel();
        jLbTitVisu = new javax.swing.JLabel();
        jBtnColFon = new javax.swing.JButton();
        //jBtnColFon.setBackground(colFon);
        jLbTitOri = new javax.swing.JLabel();
        jScrollPaneListFract = new javax.swing.JScrollPane();
        jListFrac = new javax.swing.JList();
        jSpinnerIter = new javax.swing.JSpinner();
        jLbSelFrac = new javax.swing.JLabel();
        jLbIter = new javax.swing.JLabel();
        jCheckBoxPro = new javax.swing.JCheckBox();
        jSeparatorOpc1 = new javax.swing.JSeparator();
        jSeparatorOpc3 = new javax.swing.JSeparator();
        jSeparatorOcp2 = new javax.swing.JSeparator();
        jCheckBoxColDeg = new javax.swing.JCheckBox();
        jSeparatorOpc4 = new javax.swing.JSeparator();
        jLbTitEdi = new javax.swing.JLabel();
        jSeparatorOpc6 = new javax.swing.JSeparator();
        jRadBtnCar = new javax.swing.JRadioButton();
        jRadBtnGeo = new javax.swing.JRadioButton();
        jSplitPaneGrama = new javax.swing.JSplitPane();
        jScrollPaneGrama = new javax.swing.JScrollPane();
        jPanelGramatical = new javax.swing.JPanel();
        jPanelConGrama = new javax.swing.JPanel();
        jSpinnerGrama = new javax.swing.JSpinner();
        jSliderGrama = new javax.swing.JSlider();
        jBtnCarGramatica = new javax.swing.JButton();
        jLbRest = new javax.swing.JLabel();
        jBtnRest = new javax.swing.JButton();
        //jBtnColFon.setBackground(colFon);
        jSeparatorOpc5 = new javax.swing.JSeparator();
        jLbTitEdi1 = new javax.swing.JLabel();
        jSeparatorOpc7 = new javax.swing.JSeparator();
        jTabbedPane = new javax.swing.JTabbedPane();
        jSplitPaneSecundario2D = new javax.swing.JSplitPane();
        jSplitPaneTrans2D = new javax.swing.JSplitPane();
        jScrollPaneHerr2D = new javax.swing.JScrollPane();
        jPanelTrans2D = new javax.swing.JPanel();
        jPanelCantTrans2D = new javax.swing.JPanel();
        jSpinnerCantTrans2D = new javax.swing.JSpinner();
        jSliderCantTrans2D = new javax.swing.JSlider();
        jLbTitTran2D = new javax.swing.JLabel();
        jSplitPaneSecundario3D = new javax.swing.JSplitPane();
        jSplitPaneTrans3D = new javax.swing.JSplitPane();
        jPanelCantTrans3D = new javax.swing.JPanel();
        jSpinnerCantTrans3D = new javax.swing.JSpinner();
        jSliderCantTrans3D = new javax.swing.JSlider();
        jLbTitTran3D = new javax.swing.JLabel();
        jScrollPaneHerr3D = new javax.swing.JScrollPane();
        jPanelTrans3D = new javax.swing.JPanel();
        statusPane = new javax.swing.JPanel();
        progressBar = new javax.swing.JProgressBar();
        jSeparatorVert = new javax.swing.JSeparator();
        jLbPlano = new javax.swing.JLabel();
        jSeparatorVert1 = new javax.swing.JSeparator();
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jMenuItemNuevoFrac = new javax.swing.JMenuItem();
        jMenuItemNuevoGrama = new javax.swing.JMenuItem();
        jSeparatorMenu = new javax.swing.JSeparator();
        jMenuItemAbrir = new javax.swing.JMenuItem();
        jMenuItemAbrirGrama = new javax.swing.JMenuItem();
        jSeparatorArchivo = new javax.swing.JSeparator();
        jMenuIGuardar = new javax.swing.JMenuItem();
        jMenuItemGuardarComo = new javax.swing.JMenuItem();
        jMenuIGuardarGrama = new javax.swing.JMenuItem();
        jMenuItemGuardarComoGrama = new javax.swing.JMenuItem();
        jSeparatorArchivoSeg = new javax.swing.JSeparator();
        jMenuItemSalir = new javax.swing.JMenuItem();
        jMenuOrigenDatos = new javax.swing.JMenu();
        jMenuItemSelecGrama = new javax.swing.JMenuItem();
        jMenuHerra = new javax.swing.JMenu();
        jMenuItemExport = new javax.swing.JMenuItem();
        jMenuItemTransVar = new javax.swing.JMenuItem();
        jMenuModoPant = new javax.swing.JMenu();
        jRadioButtonMenuItemPantalla = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItemVentana = new javax.swing.JRadioButtonMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        jMenuItemHelp = new javax.swing.JMenuItem();
        jMenuAyudaRap = new javax.swing.JMenu();
        jMenuItemAppFrac = new javax.swing.JMenuItem();
        jMenuItemAvanCien = new javax.swing.JMenuItem();
        jMenuItemFuncUsos = new javax.swing.JMenuItem();
        jMenuItemManBas = new javax.swing.JMenuItem();
        jMenuItemQueFrac = new javax.swing.JMenuItem();
        jMenuItemDesc = new javax.swing.JMenuItem();
        jSeparatorAyuda = new javax.swing.JSeparator();
        jMenuItemEnlaceWeb = new javax.swing.JMenuItem();
        jMenuItemAcerca = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPanePrincipal2D.setDividerLocation(640);
        jSplitPanePrincipal2D.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jSplitPanePrincipal2DJSplitPaneExt_Resized(evt);
            }
        });

        jTogButVerEjes.setText("Ver Ejes");
        jTogButVerEjes.setToolTipText("");
        jTogButVerEjes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTogButVerEjes_ActionPerformed(evt);
            }
        });

        jSliderZoom.setValue(25);
        jSliderZoom.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderZoom_state(evt);
            }
        });

        jLbZoom.setText("Zoom");

        jLbMen.setText("-");

        jLbMas.setText("+");

        jLbColFon.setText("Color Fondo");

        jLbCenCoord.setText("Cent. Coord");

        jLbTitVisu.setText("Opciones de visualizaci�n  ");

        jBtnColFon.setText("Color Plano");
        jBtnColFon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnColFonActionPerformed(evt);
            }
        });

        jLbTitOri.setText("Opciones de origen de datos  ");

        jListFrac.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListFrac.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListFracValueChanged(evt);
            }
        });
        jScrollPaneListFract.setViewportView(jListFrac);

        jSpinnerIter.setModel(new javax.swing.SpinnerNumberModel(0, 0, 500000, 5000));
        jSpinnerIter.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerIterjSpinnerStateChanged(evt);
            }
        });

        jLbSelFrac.setText("Seleccion de fractal");

        jLbIter.setText("Iteracciones");

        jCheckBoxPro.setLabel("Probabilidad automatica");
        jCheckBoxPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxProActionPerformed(evt);
            }
        });

        jCheckBoxColDeg.setSelected(true);
        jCheckBoxColDeg.setText("Color degrade");
        jCheckBoxColDeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxColDegActionPerformed(evt);
            }
        });

        jLbTitEdi.setText("Opciones de edici�n fractal");

        jRadBtnCar.setSelected(true);
        jRadBtnCar.setText("Edici�n Cartesiana");

        jRadBtnGeo.setText("Edici�n Geometrica");

        jSplitPaneGrama.setDividerLocation(25);
        jSplitPaneGrama.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.GroupLayout jPanelGramaticalLayout = new javax.swing.GroupLayout(jPanelGramatical);
        jPanelGramatical.setLayout(jPanelGramaticalLayout);
        jPanelGramaticalLayout.setHorizontalGroup(
            jPanelGramaticalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 425, Short.MAX_VALUE)
        );
        jPanelGramaticalLayout.setVerticalGroup(
            jPanelGramaticalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 331, Short.MAX_VALUE)
        );

        jScrollPaneGrama.setViewportView(jPanelGramatical);

        jSplitPaneGrama.setBottomComponent(jScrollPaneGrama);

        jSpinnerGrama.setModel(new javax.swing.SpinnerNumberModel(0, 0, 26, 1));
        jSpinnerGrama.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerGramaStateChanged(evt);
            }
        });

        jSliderGrama.setMaximum(26);
        jSliderGrama.setValue(0);
        jSliderGrama.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderGramaStateChanged(evt);
            }
        });

        jBtnCarGramatica.setText("Cargar Gra.");
        jBtnCarGramatica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCarGramaticaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelConGramaLayout = new javax.swing.GroupLayout(jPanelConGrama);
        jPanelConGrama.setLayout(jPanelConGramaLayout);
        jPanelConGramaLayout.setHorizontalGroup(
            jPanelConGramaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConGramaLayout.createSequentialGroup()
                .addComponent(jSpinnerGrama, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jSliderGrama, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnCarGramatica))
        );
        jPanelConGramaLayout.setVerticalGroup(
            jPanelConGramaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConGramaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jSpinnerGrama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jBtnCarGramatica))
            .addComponent(jSliderGrama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jSplitPaneGrama.setLeftComponent(jPanelConGrama);

        jLbRest.setText("Restaurar datos");

        jBtnRest.setText("Reiniciar fractal");
        jBtnRest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnRestActionPerformed(evt);
            }
        });

        jLbTitEdi1.setText("Control Gramatical del Caos");

        javax.swing.GroupLayout jPanelHerrLayout = new javax.swing.GroupLayout(jPanelHerr);
        jPanelHerr.setLayout(jPanelHerrLayout);
        jPanelHerrLayout.setHorizontalGroup(
            jPanelHerrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparatorOpc3, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
            .addComponent(jLbTitOri)
            .addComponent(jCheckBoxPro)
            .addComponent(jLbSelFrac)
            .addGroup(jPanelHerrLayout.createSequentialGroup()
                .addGroup(jPanelHerrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLbZoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLbCenCoord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLbColFon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelHerrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelHerrLayout.createSequentialGroup()
                        .addComponent(jLbMen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSliderZoom, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLbMas))
                    .addComponent(jBtnColFon, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                    .addComponent(jTogButVerEjes, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)))
            .addGroup(jPanelHerrLayout.createSequentialGroup()
                .addComponent(jCheckBoxColDeg)
                .addContainerGap(101, Short.MAX_VALUE))
            .addComponent(jLbTitVisu)
            .addComponent(jLbTitEdi)
            .addGroup(jPanelHerrLayout.createSequentialGroup()
                .addComponent(jRadBtnCar)
                .addContainerGap(81, Short.MAX_VALUE))
            .addComponent(jRadBtnGeo)
            .addComponent(jSeparatorOpc6, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
            .addComponent(jSeparatorOpc4, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
            .addComponent(jSeparatorOcp2, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
            .addComponent(jSeparatorOpc1, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
            .addGroup(jPanelHerrLayout.createSequentialGroup()
                .addGroup(jPanelHerrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLbIter, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLbRest))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelHerrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSpinnerIter, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(jBtnRest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(jScrollPaneListFract, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelHerrLayout.createSequentialGroup()
                .addComponent(jLbTitEdi1)
                .addContainerGap(62, Short.MAX_VALUE))
            .addComponent(jSeparatorOpc5, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
            .addComponent(jSeparatorOpc7, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
            .addComponent(jSplitPaneGrama, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
        );
        jPanelHerrLayout.setVerticalGroup(
            jPanelHerrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHerrLayout.createSequentialGroup()
                .addComponent(jLbTitOri)
                .addGap(5, 5, 5)
                .addComponent(jSeparatorOpc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLbSelFrac)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneListFract, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelHerrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLbIter)
                    .addComponent(jSpinnerIter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelHerrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLbRest)
                    .addComponent(jBtnRest))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxPro)
                .addGap(4, 4, 4)
                .addComponent(jSeparatorOcp2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLbTitVisu)
                .addGap(10, 10, 10)
                .addComponent(jSeparatorOpc3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelHerrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLbCenCoord)
                    .addComponent(jTogButVerEjes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelHerrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLbColFon)
                    .addComponent(jBtnColFon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelHerrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelHerrLayout.createSequentialGroup()
                        .addGroup(jPanelHerrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelHerrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLbZoom)
                                .addComponent(jLbMen))
                            .addComponent(jSliderZoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxColDeg))
                    .addComponent(jLbMas))
                .addGap(4, 4, 4)
                .addComponent(jSeparatorOpc4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLbTitEdi)
                .addGap(8, 8, 8)
                .addComponent(jSeparatorOpc6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadBtnCar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadBtnGeo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparatorOpc5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLbTitEdi1)
                .addGap(8, 8, 8)
                .addComponent(jSeparatorOpc7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPaneGrama, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
        );

        jSplitPanePrincipal2D.setRightComponent(jPanelHerr);

        jTabbedPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        //jTabbedPane2D.putClientProperty(SubstanceLookAndFeel.WATERMARK_VISIBLE, false);
        //repaint();
        jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneStateChanged(evt);
            }
        });

        jSplitPaneSecundario2D.setDividerLocation(480);
        jSplitPaneSecundario2D.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPaneSecundario2D.setLeftComponent(canvas2D);
        jSplitPaneSecundario2D.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jSplitPaneSecundario2DJSplitPaneInt2D_Resized(evt);
            }
        });

        jSplitPaneTrans2D.setDividerLocation(70);

        jScrollPaneHerr2D.setPreferredSize(new java.awt.Dimension(640, 480));

        javax.swing.GroupLayout jPanelTrans2DLayout = new javax.swing.GroupLayout(jPanelTrans2D);
        jPanelTrans2D.setLayout(jPanelTrans2DLayout);
        jPanelTrans2DLayout.setHorizontalGroup(
            jPanelTrans2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 554, Short.MAX_VALUE)
        );
        jPanelTrans2DLayout.setVerticalGroup(
            jPanelTrans2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 510, Short.MAX_VALUE)
        );

        jScrollPaneHerr2D.setViewportView(jPanelTrans2D);

        jSplitPaneTrans2D.setRightComponent(jScrollPaneHerr2D);

        jSpinnerCantTrans2D.setModel(new javax.swing.SpinnerNumberModel(0, 0, 26, 1));
        jSpinnerCantTrans2D.setPreferredSize(new java.awt.Dimension(40, 20));
        jSpinnerCantTrans2D.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerCantTrans2DjSpinnerCantStateChanged(evt);
            }
        });

        jSliderCantTrans2D.setMaximum(0);
        jSliderCantTrans2D.setMinimum(-26);
        jSliderCantTrans2D.setOrientation(javax.swing.JSlider.VERTICAL);
        jSliderCantTrans2D.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderCantTrans2DjSliderCantStateChanged(evt);
            }
        });

        jLbTitTran2D.setText("Trans.");

        javax.swing.GroupLayout jPanelCantTrans2DLayout = new javax.swing.GroupLayout(jPanelCantTrans2D);
        jPanelCantTrans2D.setLayout(jPanelCantTrans2DLayout);
        jPanelCantTrans2DLayout.setHorizontalGroup(
            jPanelCantTrans2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCantTrans2DLayout.createSequentialGroup()
                .addGroup(jPanelCantTrans2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSpinnerCantTrans2D, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLbTitTran2D, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSliderCantTrans2D, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelCantTrans2DLayout.setVerticalGroup(
            jPanelCantTrans2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCantTrans2DLayout.createSequentialGroup()
                .addComponent(jLbTitTran2D)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinnerCantTrans2D, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSliderCantTrans2D, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
        );

        jSplitPaneTrans2D.setLeftComponent(jPanelCantTrans2D);

        jSplitPaneSecundario2D.setRightComponent(jSplitPaneTrans2D);

        jTabbedPane.addTab("Visualizacion 2D", jSplitPaneSecundario2D);

        jSplitPaneSecundario3D.setDividerLocation(480);
        jSplitPaneSecundario3D.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPaneSecundario3D.setLeftComponent(canvas3D);
        jSplitPaneSecundario3D.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jSplitPaneSecundario3DjSplitPaneInt3D_Resized(evt);
            }
        });

        jSplitPaneTrans3D.setDividerLocation(70);

        jSpinnerCantTrans3D.setModel(new javax.swing.SpinnerNumberModel(0, 0, 26, 1));
        jSpinnerCantTrans3D.setPreferredSize(new java.awt.Dimension(40, 20));
        jSpinnerCantTrans3D.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerCantTrans3DStateChanged(evt);
            }
        });

        jSliderCantTrans3D.setMaximum(0);
        jSliderCantTrans3D.setMinimum(-26);
        jSliderCantTrans3D.setOrientation(javax.swing.JSlider.VERTICAL);
        jSliderCantTrans3D.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderCantTrans3DStateChanged(evt);
            }
        });

        jLbTitTran3D.setText("Trans.");

        javax.swing.GroupLayout jPanelCantTrans3DLayout = new javax.swing.GroupLayout(jPanelCantTrans3D);
        jPanelCantTrans3D.setLayout(jPanelCantTrans3DLayout);
        jPanelCantTrans3DLayout.setHorizontalGroup(
            jPanelCantTrans3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCantTrans3DLayout.createSequentialGroup()
                .addGroup(jPanelCantTrans3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSpinnerCantTrans3D, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLbTitTran3D, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSliderCantTrans3D, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelCantTrans3DLayout.setVerticalGroup(
            jPanelCantTrans3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCantTrans3DLayout.createSequentialGroup()
                .addComponent(jLbTitTran3D)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinnerCantTrans3D, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSliderCantTrans3D, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
        );

        jSplitPaneTrans3D.setLeftComponent(jPanelCantTrans3D);

        jScrollPaneHerr3D.setPreferredSize(new java.awt.Dimension(640, 480));

        javax.swing.GroupLayout jPanelTrans3DLayout = new javax.swing.GroupLayout(jPanelTrans3D);
        jPanelTrans3D.setLayout(jPanelTrans3DLayout);
        jPanelTrans3DLayout.setHorizontalGroup(
            jPanelTrans3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );
        jPanelTrans3DLayout.setVerticalGroup(
            jPanelTrans3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        jScrollPaneHerr3D.setViewportView(jPanelTrans3D);

        jSplitPaneTrans3D.setRightComponent(jScrollPaneHerr3D);

        jSplitPaneSecundario3D.setRightComponent(jSplitPaneTrans3D);

        jTabbedPane.addTab("Visualizacion 3D", jSplitPaneSecundario3D);

        jSplitPanePrincipal2D.setLeftComponent(jTabbedPane);

        jSeparatorVert.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparatorVert.setPreferredSize(new java.awt.Dimension(25, 25));

        jLbPlano.setText("Coord Plano: x=[0.0] y=[-1.0] z=[-4.0]");

        jSeparatorVert1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparatorVert1.setPreferredSize(new java.awt.Dimension(25, 25));

        javax.swing.GroupLayout statusPaneLayout = new javax.swing.GroupLayout(statusPane);
        statusPane.setLayout(statusPaneLayout);
        statusPaneLayout.setHorizontalGroup(
            statusPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPaneLayout.createSequentialGroup()
                .addContainerGap(470, Short.MAX_VALUE)
                .addComponent(jSeparatorVert1, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLbPlano)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparatorVert, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        statusPaneLayout.setVerticalGroup(
            statusPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPaneLayout.createSequentialGroup()
                .addGroup(statusPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparatorVert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLbPlano)
                    .addComponent(jSeparatorVert1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuBar.setBorderPainted(false);

        fileMenu.setText("Archivo");
        fileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuActionPerformed(evt);
            }
        });

        jMenuItemNuevoFrac.setText("Nuevo Fractal");
        jMenuItemNuevoFrac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNuevoFracActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemNuevoFrac);

        jMenuItemNuevoGrama.setText("Nueva Gramatica");
        jMenuItemNuevoGrama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNuevoGramaActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemNuevoGrama);
        fileMenu.add(jSeparatorMenu);

        jMenuItemAbrir.setText("Abrir Fractal");
        fileMenu.add(jMenuItemAbrir);

        jMenuItemAbrirGrama.setText("Abrir Gramatica");
        fileMenu.add(jMenuItemAbrirGrama);
        fileMenu.add(jSeparatorArchivo);

        jMenuIGuardar.setText("Guardar Fractal");
        fileMenu.add(jMenuIGuardar);

        jMenuItemGuardarComo.setText("Guardar Fractal Como...");
        fileMenu.add(jMenuItemGuardarComo);

        jMenuIGuardarGrama.setText("Guardar Gramatica");
        fileMenu.add(jMenuIGuardarGrama);

        jMenuItemGuardarComoGrama.setText("Guardar Gramatica Como...");
        fileMenu.add(jMenuItemGuardarComoGrama);
        fileMenu.add(jSeparatorArchivoSeg);

        jMenuItemSalir.setText("Salir de Barnsley Fractals");
        jMenuItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSalirActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemSalir);

        menuBar.add(fileMenu);

        jMenuOrigenDatos.setText("Origen de datos");

        jMenuItemSelecGrama.setText("Seleccionar Gramatica");
        jMenuItemSelecGrama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSelecGramaActionPerformed(evt);
            }
        });
        jMenuOrigenDatos.add(jMenuItemSelecGrama);

        menuBar.add(jMenuOrigenDatos);

        jMenuHerra.setText("Herramientas");

        jMenuItemExport.setText("Exportar Imagen");
        jMenuItemExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExportActionPerformed(evt);
            }
        });
        jMenuHerra.add(jMenuItemExport);

        jMenuItemTransVar.setText("Emular Transformaciones variables");
        jMenuHerra.add(jMenuItemTransVar);

        jMenuModoPant.setText("Modo Pantalla");

        jRadioButtonMenuItemPantalla.setText("Pantalla Completa");
        jMenuModoPant.add(jRadioButtonMenuItemPantalla);

        jRadioButtonMenuItemVentana.setSelected(true);
        jRadioButtonMenuItemVentana.setText("Ventana de ejecuci�n");
        jMenuModoPant.add(jRadioButtonMenuItemVentana);

        jMenuHerra.add(jMenuModoPant);

        menuBar.add(jMenuHerra);

        helpMenu.setText("Ayuda");

        jMenuItemHelp.setText("Ayuda Contenidos");
        jMenuItemHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemHelpActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItemHelp);

        jMenuAyudaRap.setText("Ayuda Rapida");

        jMenuItemAppFrac.setText("Aplicaciones de los Fractales");
        jMenuAyudaRap.add(jMenuItemAppFrac);

        jMenuItemAvanCien.setText("Avances Cientificos");
        jMenuAyudaRap.add(jMenuItemAvanCien);

        jMenuItemFuncUsos.setText("Funciones y usos de Barnsley Fractals");
        jMenuAyudaRap.add(jMenuItemFuncUsos);

        jMenuItemManBas.setText("Manejo basico");
        jMenuAyudaRap.add(jMenuItemManBas);

        jMenuItemQueFrac.setText("Que es un Fractal");
        jMenuAyudaRap.add(jMenuItemQueFrac);

        jMenuItemDesc.setText("Descripcion de Barnsley Fractals");
        jMenuAyudaRap.add(jMenuItemDesc);

        helpMenu.add(jMenuAyudaRap);
        helpMenu.add(jSeparatorAyuda);

        jMenuItemEnlaceWeb.setText("Enlace Web");
        helpMenu.add(jMenuItemEnlaceWeb);

        jMenuItemAcerca.setText("Acerca de.");
        jMenuItemAcerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAcercaActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItemAcerca);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPanePrincipal2D, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jSplitPanePrincipal2D, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusPane, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTogButVerEjes_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTogButVerEjes_ActionPerformed
        if(jTabbedPane.getSelectedIndex()==0)
        {
            verEjes2D=jTogButVerEjes.isSelected();
            verificarEjes2D();
        }
        else if(jTabbedPane.getSelectedIndex()==1)
        {
            verEjes3D=jTogButVerEjes.isSelected();
            verificarEjes3D();
        }
}//GEN-LAST:event_jTogButVerEjes_ActionPerformed

    private void jSliderZoom_state(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderZoom_state
        if(jTabbedPane.getSelectedIndex()==0)
        {
            barnsGL2D.modZPos(-100/(float)jSliderZoom.getValue()+1.0f);
            jLbPlano.setText("Coord. Plano: x=["+barnsGL2D.obtXPos()+"] y=["+barnsGL2D.obtYPos()+"] z=["+barnsGL2D.obtZPos()+"]");
        }
        else if(jTabbedPane.getSelectedIndex()==1)
        {
            barnsGL3D.modZPos(-100/(float)jSliderZoom.getValue()+1.0f);
            jLbPlano.setText("Coord. Plano: x=["+barnsGL3D.obtXPos()+"] y=["+barnsGL3D.obtYPos()+"] z=["+barnsGL3D.obtZPos()+"]");
        }
}//GEN-LAST:event_jSliderZoom_state

    private void jBtnColFonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnColFonActionPerformed
        if(jTabbedPane.getSelectedIndex()==0)
        {
            Color colAux=JColorChooser.showDialog(this, "Selector de color de fondo 2D", Utiles.obtColor(colFon2D));
            if(colAux!=null)
            {
                asigFondo2D(colAux);
                if(valRecSelFract2D[0]>=0)
                    listFract2D[valRecSelFract2D[0]].modColFon(Utiles.obtColor(colAux));
                verificarEjes2D();
            }
        }
        else if(jTabbedPane.getSelectedIndex()==1)
        {
            Color colAux=JColorChooser.showDialog(this, "Selector de color de fondo 3D", Utiles.obtColor(colFon3D));
            if(colAux!=null)
            {
                asigFondo3D(colAux);
                if(valRecSelFract3D[0]>=0)
                    listFract3D[valRecSelFract3D[0]].modColFon(Utiles.obtColor(colAux));
                verificarEjes3D();
            }
        }
}//GEN-LAST:event_jBtnColFonActionPerformed

    private void jListFracValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListFracValueChanged
        if(jTabbedPane.getSelectedIndex()==0)
        {
            valRecSelFract2D[2]=valRecSelFract2D[1];
            valRecSelFract2D[1]=valRecSelFract2D[0];
            valRecSelFract2D[0]=jListFrac.getSelectedIndex();
            if(valRecSelFract2D[0]>=0)
            {
                asigDatos2D();
                verificarEjes2D();
                if(valRecSelFract2D[2]!=-1)
                    listFract2D[valRecSelFract2D[2]].libPuntos();
            }
        }
        else if(jTabbedPane.getSelectedIndex()==1)
        {
            valRecSelFract3D[2]=valRecSelFract3D[1];
            valRecSelFract3D[1]=valRecSelFract3D[0];
            valRecSelFract3D[0]=jListFrac.getSelectedIndex();
            if(valRecSelFract3D[0]>=0)
            {
                asigDatos3D();
                verificarEjes3D();
                if(valRecSelFract3D[2]!=-1)
                    listFract3D[valRecSelFract3D[2]].libPuntos();
            }
        }
}//GEN-LAST:event_jListFracValueChanged

    private void jSpinnerIterjSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerIterjSpinnerStateChanged
        if(jTabbedPane.getSelectedIndex()==0)
        {
            if(valRecSelFract2D[0]>=0)
            {
            if(!modoInactivo2D)
            {
                if(valRecSelFract2D[0]>=0)
                {
                    listFract2D[valRecSelFract2D[0]].modMaxInter(Integer.valueOf(jSpinnerIter.getValue().toString()));
                    barnsGL2D.modPuntos(listFract2D[valRecSelFract2D[0]].obtPuntos());
                }
            }
            }
        }
        else if(jTabbedPane.getSelectedIndex()==1)
        {
            if(valRecSelFract3D[0]>=0)
            {
            if(!modoInactivo3D)
            {
                if(valRecSelFract3D[0]>=0)
                {
                    listFract3D[valRecSelFract3D[0]].modMaxInter(Integer.valueOf(jSpinnerIter.getValue().toString()));
                    barnsGL3D.modPuntos(listFract3D[valRecSelFract3D[0]].obtPuntos());
                }
            }
            }
        }
}//GEN-LAST:event_jSpinnerIterjSpinnerStateChanged

    private void jCheckBoxProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxProActionPerformed

}//GEN-LAST:event_jCheckBoxProActionPerformed

    private void jCheckBoxColDegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxColDegActionPerformed
        if(jTabbedPane.getSelectedIndex()==0)
        {
            colDeg2D=jCheckBoxColDeg.isSelected();
            if(valRecSelFract2D[0]>=0)
            {
                listFract2D[valRecSelFract2D[0]].genPuntos(colDeg2D);
                barnsGL2D.modPuntos(listFract2D[valRecSelFract2D[0]].obtPuntos());
            }
        }
        else if(jTabbedPane.getSelectedIndex()==1)
        {
            colDeg3D=jCheckBoxColDeg.isSelected();
            if(valRecSelFract3D[0]>=0)
            {
                listFract3D[valRecSelFract3D[0]].genPuntos(colDeg3D);
                barnsGL3D.modPuntos(listFract3D[valRecSelFract3D[0]].obtPuntos());
            }
        }
}//GEN-LAST:event_jCheckBoxColDegActionPerformed

    private void jSplitPanePrincipal2DJSplitPaneExt_Resized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jSplitPanePrincipal2DJSplitPaneExt_Resized
        jSplitPanePrincipal2D.setDividerLocation(0.8);
}//GEN-LAST:event_jSplitPanePrincipal2DJSplitPaneExt_Resized

    private void jMenuItemNuevoFracActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNuevoFracActionPerformed

    }//GEN-LAST:event_jMenuItemNuevoFracActionPerformed

    private void fileMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuActionPerformed
        
    }//GEN-LAST:event_fileMenuActionPerformed

    private void jTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneStateChanged
       switch(jTabbedPane.getSelectedIndex())
       {
           case 0:
               int val2D=valRecSelFract2D[0];
               jListFrac.setModel(new javax.swing.AbstractListModel() {
                String[] strings = stgFiles2D;
                public int getSize() { return strings.length; }
                public Object getElementAt(int i) { return strings[i]; }
                });
                if(val2D!=-1)
                    jListFrac.setSelectedIndex(val2D);
                asigFondo2D(colFon2D);
                jTogButVerEjes.setSelected(verEjes2D);
                jCheckBoxColDeg.setSelected(colDeg2D);
                break;
           case 1:
                int val3D=valRecSelFract3D[0];
                jListFrac.setModel(new javax.swing.AbstractListModel() {
                String[] strings = stgFiles3D;
                public int getSize() { return strings.length; }
                public Object getElementAt(int i) { return strings[i]; }
                });
                if(val3D!=-1)
                    jListFrac.setSelectedIndex(val3D);
                asigFondo3D(colFon3D);
                jTogButVerEjes.setSelected(verEjes3D);
                jCheckBoxColDeg.setSelected(colDeg3D);
               break;
       }
}//GEN-LAST:event_jTabbedPaneStateChanged

    private void jSplitPaneSecundario3DjSplitPaneInt3D_Resized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jSplitPaneSecundario3DjSplitPaneInt3D_Resized
        jSplitPaneSecundario3D.setDividerLocation(0.8);
}//GEN-LAST:event_jSplitPaneSecundario3DjSplitPaneInt3D_Resized

    private void jSplitPaneSecundario2DJSplitPaneInt2D_Resized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jSplitPaneSecundario2DJSplitPaneInt2D_Resized
        jSplitPaneSecundario2D.setDividerLocation(0.8);
}//GEN-LAST:event_jSplitPaneSecundario2DJSplitPaneInt2D_Resized

    private void jSliderCantTrans2DjSliderCantStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderCantTrans2DjSliderCantStateChanged
        int val=-1*jSliderCantTrans2D.getValue();
        jSpinnerCantTrans2D.setValue(val);
}//GEN-LAST:event_jSliderCantTrans2DjSliderCantStateChanged

    private void jSpinnerCantTrans2DjSpinnerCantStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerCantTrans2DjSpinnerCantStateChanged
        if(valRecSelFract2D[0]>=0) {
            int val=Integer.valueOf(jSpinnerCantTrans2D.getValue().toString());
            jSliderCantTrans2D.setValue(-1*val);
            habFilasTrans2D(val);
            if(!modoInactivo2D) {
                listFract2D[valRecSelFract2D[0]].modCantTrans(val);
                barnsGL2D.modPuntos(listFract2D[valRecSelFract2D[0]].obtPuntos());
            }
        }
}//GEN-LAST:event_jSpinnerCantTrans2DjSpinnerCantStateChanged

    private void jSpinnerGramaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerGramaStateChanged
        if(jTabbedPane.getSelectedIndex()==0)
        {
            if(valRecSelFract2D[0]>=0)
            {
                int val=Integer.valueOf(jSpinnerGrama.getValue().toString());
                jSliderGrama.setValue(val);
                habFilasGrama(val);
            }
        }
        else if(jTabbedPane.getSelectedIndex()==1)
        {
            if(valRecSelFract3D[0]>=0)
            {
                int val=Integer.valueOf(jSpinnerGrama.getValue().toString());
                jSliderGrama.setValue(val);
                habFilasGrama(val);
            }
        }
    }//GEN-LAST:event_jSpinnerGramaStateChanged

    private void jSliderGramaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderGramaStateChanged
        int val=jSliderGrama.getValue();
        jSpinnerGrama.setValue(val);
    }//GEN-LAST:event_jSliderGramaStateChanged

    private void jSpinnerCantTrans3DStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerCantTrans3DStateChanged
        if(valRecSelFract3D[0]>=0) {
            int val=Integer.valueOf(jSpinnerCantTrans3D.getValue().toString());
            jSliderCantTrans3D.setValue(-1*val);
            habFilasTrans3D(val);
        }
    }//GEN-LAST:event_jSpinnerCantTrans3DStateChanged

    private void jSliderCantTrans3DStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSliderCantTrans3DStateChanged
        int val=-1*jSliderCantTrans3D.getValue();
        jSpinnerCantTrans3D.setValue(val);
    }//GEN-LAST:event_jSliderCantTrans3DStateChanged

    private void jBtnRestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnRestActionPerformed
        if(jTabbedPane.getSelectedIndex()==0)
        {
            if(valRecSelFract2D[0]>=0)
            {                
                String dir2D="Data"+File.separator+"Data2D"+File.separator;
                String line;
                int inter=0;
                int cantTrans=0;
                float val;

                float ColorFon[]=new float[3];
                float transMat[][]=new float[26][7];
                float colTransFloat[][]=new float[26][3];

                File file=new File(dir2D+stgFiles2D[valRecSelFract2D[0]]+".dtf");
                BufferedReader bufRead;

                if(file.isFile())
                {
                    try
                    {
                        bufRead = new BufferedReader(new FileReader(file));
                        line=bufRead.readLine();

                        inter=Integer.valueOf(line.substring(0,line.indexOf('\t')));
                        line=line.substring(line.indexOf('\t')+1,line.length());

                        cantTrans=Integer.valueOf(line.substring(0,line.indexOf('\t')));
                        line=line.substring(line.indexOf('\t')+1,line.length());
                        line=line.substring(line.indexOf('\t')+1,line.length());
                        line=line.substring(line.indexOf('\t')+1,line.length());
                        line=line.substring(line.indexOf('\t')+1,line.length());

                        transMat=new float[cantTrans][7];
                        colTransFloat=new float[cantTrans][3];

                        ColorFon=Utiles.obtColor(new Color(Integer.valueOf(line)));

                        for(int j=0;j<cantTrans;j++)
                        {
                            if(bufRead.ready())
                            {
                                line=bufRead.readLine();
                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][0]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][1]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][2]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][3]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][4]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][5]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][6]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                colTransFloat[j]=Utiles.obtColor(new Color(Integer.valueOf(line)));
                            }
                        }
                    }
                    catch (FileNotFoundException ex)
                    {
                        JOptionPane.showMessageDialog(null, "Error en lectura de archivo: "+ex.toString());
                    }
                    catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(null, "Error en lectura: "+ex.toString());
                    }
                }
                listFract2D[valRecSelFract2D[0]]=new Fractal2D(cantTrans,inter,transMat,colTransFloat,ColorFon);
                asigDatos2D();
                verificarEjes2D();
            }
        }
        else if(jTabbedPane.getSelectedIndex()==1)
        {
            if(valRecSelFract3D[0]>=0)
            {
                String dir3D="Data"+File.separator+"Data3D"+File.separator;
                String line;
                int inter=0;
                int cantTrans=0;
                float val;

                float ColorFon[]=new float[3];
                float transMat[][]=new float[26][13];
                float colTransFloat[][]=new float[26][3];

                File file=new File(dir3D+stgFiles3D[valRecSelFract3D[0]]+".d3f");
                BufferedReader bufRead;

                if(file.isFile())
                {
                    try
                    {
                        bufRead = new BufferedReader(new FileReader(file));
                        line=bufRead.readLine();

                        inter=Integer.valueOf(line.substring(0,line.indexOf('\t')));
                        line=line.substring(line.indexOf('\t')+1,line.length());

                        cantTrans=Integer.valueOf(line.substring(0,line.indexOf('\t')));
                        line=line.substring(line.indexOf('\t')+1,line.length());
                        line=line.substring(line.indexOf('\t')+1,line.length());
                        line=line.substring(line.indexOf('\t')+1,line.length());
                        line=line.substring(line.indexOf('\t')+1,line.length());

                        transMat=new float[cantTrans][13];
                        colTransFloat=new float[cantTrans][3];

                        ColorFon=Utiles.obtColor(new Color(Integer.valueOf(line)));

                        for(int j=0;j<cantTrans;j++)
                        {
                            if(bufRead.ready())
                            {
                                line=bufRead.readLine();
                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][0]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][1]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][2]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][3]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][4]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][5]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][6]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][7]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][8]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][9]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][10]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][11]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                val=Float.valueOf(line.substring(0,line.indexOf('\t')).replace(',', '.'));
                                transMat[j][12]=val;
                                line=line.substring(line.indexOf('\t')+1,line.length());

                                colTransFloat[j]=Utiles.obtColor(new Color(Integer.valueOf(line)));
                            }
                        }
                    }
                    catch (FileNotFoundException ex)
                    {
                        JOptionPane.showMessageDialog(null, "Error en lectura de archivo: "+ex.toString());
                    }
                    catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(null, "Error en lectura: "+ex.toString());
                    } 
                }
                
                listFract3D[valRecSelFract3D[0]]=new Fractal3D(cantTrans,inter,transMat,colTransFloat,ColorFon);
                asigDatos3D();
                verificarEjes3D();
            }
        }
    }//GEN-LAST:event_jBtnRestActionPerformed

    private void jMenuItemHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemHelpActionPerformed

    }//GEN-LAST:event_jMenuItemHelpActionPerformed

    private void jBtnCarGramaticaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCarGramaticaActionPerformed
        if(jTabbedPane.getSelectedIndex()==0)
        {
            if(valRecSelFract2D[0]>=0)
            {
                actGramatica=true;
                crearGramatica2D();
            }
        }
        else if(jTabbedPane.getSelectedIndex()==1)
        {
            if(valRecSelFract3D[0]>=0)
            {
                actGramatica=true;
                crearGramatica3D();
            }
        }
    }//GEN-LAST:event_jBtnCarGramaticaActionPerformed

    private void jMenuItemAcercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAcercaActionPerformed
        AcercaDe acerca=new AcercaDe(this,true);
        acerca.setLocationRelativeTo(this);
        acerca.setVisible(true);  
    }//GEN-LAST:event_jMenuItemAcercaActionPerformed

    private void jMenuItemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItemSalirActionPerformed

    private void jMenuItemNuevoGramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNuevoGramaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemNuevoGramaActionPerformed

    private void jMenuItemExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExportActionPerformed
        
    }//GEN-LAST:event_jMenuItemExportActionPerformed

    private void jMenuItemSelecGramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSelecGramaActionPerformed
        SelecGrama selGrama=new SelecGrama(this,true);
        selGrama.setLocationRelativeTo(this);
        selGrama.setVisible(true);
        if(selGrama.obtRepuesta()==true)
            asigGrama(selGrama.obtGramatica());

    }//GEN-LAST:event_jMenuItemSelecGramaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnCarGramatica;
    private javax.swing.JButton jBtnColFon;
    private javax.swing.JButton jBtnRest;
    private javax.swing.JCheckBox jCheckBoxColDeg;
    private javax.swing.JCheckBox jCheckBoxPro;
    private javax.swing.JLabel jLbCenCoord;
    private javax.swing.JLabel jLbColFon;
    private javax.swing.JLabel jLbIter;
    private javax.swing.JLabel jLbMas;
    private javax.swing.JLabel jLbMen;
    private javax.swing.JLabel jLbPlano;
    private javax.swing.JLabel jLbRest;
    private javax.swing.JLabel jLbSelFrac;
    private javax.swing.JLabel jLbTitEdi;
    private javax.swing.JLabel jLbTitEdi1;
    private javax.swing.JLabel jLbTitOri;
    private javax.swing.JLabel jLbTitTran2D;
    private javax.swing.JLabel jLbTitTran3D;
    private javax.swing.JLabel jLbTitVisu;
    private javax.swing.JLabel jLbZoom;
    private javax.swing.JList jListFrac;
    private javax.swing.JMenu jMenuAyudaRap;
    private javax.swing.JMenu jMenuHerra;
    private javax.swing.JMenuItem jMenuIGuardar;
    private javax.swing.JMenuItem jMenuIGuardarGrama;
    private javax.swing.JMenuItem jMenuItemAbrir;
    private javax.swing.JMenuItem jMenuItemAbrirGrama;
    private javax.swing.JMenuItem jMenuItemAcerca;
    private javax.swing.JMenuItem jMenuItemAppFrac;
    private javax.swing.JMenuItem jMenuItemAvanCien;
    private javax.swing.JMenuItem jMenuItemDesc;
    private javax.swing.JMenuItem jMenuItemEnlaceWeb;
    private javax.swing.JMenuItem jMenuItemExport;
    private javax.swing.JMenuItem jMenuItemFuncUsos;
    private javax.swing.JMenuItem jMenuItemGuardarComo;
    private javax.swing.JMenuItem jMenuItemGuardarComoGrama;
    private javax.swing.JMenuItem jMenuItemHelp;
    private javax.swing.JMenuItem jMenuItemManBas;
    private javax.swing.JMenuItem jMenuItemNuevoFrac;
    private javax.swing.JMenuItem jMenuItemNuevoGrama;
    private javax.swing.JMenuItem jMenuItemQueFrac;
    private javax.swing.JMenuItem jMenuItemSalir;
    private javax.swing.JMenuItem jMenuItemSelecGrama;
    private javax.swing.JMenuItem jMenuItemTransVar;
    private javax.swing.JMenu jMenuModoPant;
    private javax.swing.JMenu jMenuOrigenDatos;
    private javax.swing.JPanel jPanelCantTrans2D;
    private javax.swing.JPanel jPanelCantTrans3D;
    private javax.swing.JPanel jPanelConGrama;
    private javax.swing.JPanel jPanelGramatical;
    private javax.swing.JPanel jPanelHerr;
    private javax.swing.JPanel jPanelTrans2D;
    private javax.swing.JPanel jPanelTrans3D;
    private javax.swing.JRadioButton jRadBtnCar;
    private javax.swing.JRadioButton jRadBtnGeo;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemPantalla;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemVentana;
    private javax.swing.JScrollPane jScrollPaneGrama;
    private javax.swing.JScrollPane jScrollPaneHerr2D;
    private javax.swing.JScrollPane jScrollPaneHerr3D;
    private javax.swing.JScrollPane jScrollPaneListFract;
    private javax.swing.JSeparator jSeparatorArchivo;
    private javax.swing.JSeparator jSeparatorArchivoSeg;
    private javax.swing.JSeparator jSeparatorAyuda;
    private javax.swing.JSeparator jSeparatorMenu;
    private javax.swing.JSeparator jSeparatorOcp2;
    private javax.swing.JSeparator jSeparatorOpc1;
    private javax.swing.JSeparator jSeparatorOpc3;
    private javax.swing.JSeparator jSeparatorOpc4;
    private javax.swing.JSeparator jSeparatorOpc5;
    private javax.swing.JSeparator jSeparatorOpc6;
    private javax.swing.JSeparator jSeparatorOpc7;
    private javax.swing.JSeparator jSeparatorVert;
    private javax.swing.JSeparator jSeparatorVert1;
    private javax.swing.JSlider jSliderCantTrans2D;
    private javax.swing.JSlider jSliderCantTrans3D;
    private javax.swing.JSlider jSliderGrama;
    private javax.swing.JSlider jSliderZoom;
    private javax.swing.JSpinner jSpinnerCantTrans2D;
    private javax.swing.JSpinner jSpinnerCantTrans3D;
    private javax.swing.JSpinner jSpinnerGrama;
    private javax.swing.JSpinner jSpinnerIter;
    private javax.swing.JSplitPane jSplitPaneGrama;
    private javax.swing.JSplitPane jSplitPanePrincipal2D;
    private javax.swing.JSplitPane jSplitPaneSecundario2D;
    private javax.swing.JSplitPane jSplitPaneSecundario3D;
    private javax.swing.JSplitPane jSplitPaneTrans2D;
    private javax.swing.JSplitPane jSplitPaneTrans3D;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JToggleButton jTogButVerEjes;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JPanel statusPane;
    // End of variables declaration//GEN-END:variables

}
