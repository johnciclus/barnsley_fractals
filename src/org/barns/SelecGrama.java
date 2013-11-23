package org.barns;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * @author Jhon Garavito
 * Organización Uis-Grupo de investigacion teoria Caos
 * Año 2008 2009
 */
public class SelecGrama extends javax.swing.JDialog {

    private boolean opcSel=false;
    private String nomGrama[],grama[];
    private String carGrama="";
    private char alfaMay[]={'S','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','T','U','V','W','X','Y','Z'};

    public SelecGrama(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        cargarGrama();
    }
    private void cargarGrama()
    {
        String dirGrama="Grammar";      
        File filesGrama=new File(dirGrama);
        if(filesGrama.isDirectory())
        {
            nomGrama=filesGrama.list();
            for(int i=0;i<nomGrama.length;i++)
                nomGrama[i]=nomGrama[i].substring(0, nomGrama[i].length()-4);       
            jListGrama.setModel(new javax.swing.AbstractListModel() {
            String[] strings = nomGrama;
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        }
        if(nomGrama.length>0)
            jListGrama.setSelectedIndex(0);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JLabel imageLabel = new javax.swing.JLabel();
        javax.swing.JLabel appTitleLabel = new javax.swing.JLabel();
        javax.swing.JLabel appDescLabel = new javax.swing.JLabel();
        javax.swing.JLabel appDescLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel appDescLabel2 = new javax.swing.JLabel();
        jButtonCancelar = new javax.swing.JButton();
        jButtonAceptar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListGrama = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaGrama = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        appTitleLabel.setFont(appTitleLabel.getFont().deriveFont(appTitleLabel.getFont().getStyle() | java.awt.Font.BOLD, appTitleLabel.getFont().getSize()+4));
        appTitleLabel.setText("Seleccion de Gramatica");

        appDescLabel.setText("Por favor selecione una gramatica con la cantidad adecuada de simbolos terminales,");

        appDescLabel1.setText("los simbolos deben ser menor o igual a la cantidad de transformaciones, las");

        appDescLabel2.setText("gramaticas que no satisfagan esta opcion no se podran selecionar.");

        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jButtonAceptar.setText("Aceptar");
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarActionPerformed(evt);
            }
        });

        jListGrama.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListGrama.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListGramaValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListGrama);

        jTextAreaGrama.setColumns(20);
        jTextAreaGrama.setEditable(false);
        jTextAreaGrama.setRows(5);
        jScrollPane2.setViewportView(jTextAreaGrama);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(imageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(appTitleLabel)
                                .addGap(140, 140, 140))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jButtonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(appDescLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(appDescLabel1)
                        .addComponent(appDescLabel2)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 240, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(appTitleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(appDescLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(appDescLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(appDescLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancelar)
                    .addComponent(jButtonAceptar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jButtonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarActionPerformed
        this.setVisible(false);
        this.dispose();
        opcSel=true;
    }//GEN-LAST:event_jButtonAceptarActionPerformed

    private void jListGramaValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListGramaValueChanged
        carGrama="";
        String dirGrama="Grammar";
        String line;
        int Var=0;
        File file;
        BufferedReader bufRead;
        
        file=new File(dirGrama+File.separator+nomGrama[jListGrama.getSelectedIndex()]+".cgs");
        if(file.isFile())
        {
            try
            {
                
                bufRead = new BufferedReader(new FileReader(file));
                line=bufRead.readLine();
                Var=Integer.valueOf(line.substring(0,line.indexOf('\t')));
                grama=new String[Var];

                if(bufRead.ready())
                {
                    for(int i=0;i<Var;i++)
                    {
                        grama[i]=bufRead.readLine();
                        carGrama+=alfaMay[i]+"->"+grama[i]+"\n";
                    }
                    jTextAreaGrama.setText(carGrama);
                }
                
            }
            catch (FileNotFoundException ex)
            {

            }
            catch (IOException ex)
            {

            }
        }      
    }//GEN-LAST:event_jListGramaValueChanged
    public boolean obtRepuesta()
    {
        return opcSel;
    }
    public String[] obtGramatica()
    {
        return grama;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAceptar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JList jListGrama;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaGrama;
    // End of variables declaration//GEN-END:variables

}
