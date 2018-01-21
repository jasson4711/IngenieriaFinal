/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JRViewer;
import org.apache.commons.collections.map.HashedMap;

/**
 *
 * @author USUARIO
 */
public class FacturaCliente extends javax.swing.JDialog {

    /**
     * Creates new form FacturaCliente
     */
    String cedula;
    DefaultTableModel modeloTabla;
    TableColumnModel modeloColumna;

    public FacturaCliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public FacturaCliente(java.awt.Frame parent, boolean modal, String ced) {
        super(parent, modal);
        initComponents();
        cedula = ced;

        this.setLocationRelativeTo(null);
        setearCliente();
        cargarTabla();
    }

    public void establecerTamañoColumnas() {
        modeloColumna = jTable_Factura.getColumnModel();
        modeloColumna.getColumn(0).setPreferredWidth(60);
        modeloColumna.getColumn(1).setPreferredWidth(100);
        modeloColumna.getColumn(2).setPreferredWidth(80);
        jTable_Factura.setColumnModel(modeloColumna);

    }
    boolean clienteExiste = false;

    public void cargarTabla() {
        if (!clienteExiste) {
            JOptionPane.showMessageDialog(null, "Cliente no existe");
            this.dispose();
        } else {
            setearModeloTabla();
        }
    }

    public void setearCliente() {
        String sql = "select * from clientes where ced_cli='" + cedula + "'";
        ConexionTienda cc = new ConexionTienda();
        Connection cn = cc.conectar();
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                jTextField_Ced.setText(cedula);
                jTextField_Nom.setText(rs.getString("nom_cli") + " " + rs.getString("ape_cli"));
                clienteExiste = true;
            }
            cn.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al intengar obtener datos de la base");
        }
    }

    public void setearModeloTabla() {
        String[] titulos = {"Nª FACTURA", "FECHA", "VALOR"};
        String[] datos = new String[3];
        jTable_Factura.getTableHeader().setReorderingAllowed(false);
        jTable_Factura.getTableHeader().setResizingAllowed(false);
        modeloTabla = new DefaultTableModel(null, titulos) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String sql = "select * from ventas_cab where ced_cli_ven='" + cedula + "' order by fec_ven";
        ConexionTienda cc = new ConexionTienda();
        Connection cn = cc.conectar();
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt("num_ven"));
                datos[1] = String.valueOf(rs.getDate("fec_ven"));
                datos[2] = String.valueOf(rs.getFloat("tot_ven"));
                modeloTabla.addRow(datos);
            }
            cn.close();
            jTable_Factura.setModel(modeloTabla);
            establecerTamañoColumnas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar obtener datos de la base " + ex);
        }
    }

    public void reporte() {
        if (jTable_Factura.getSelectedRow() > -1) {
            ConexionTienda cc = new ConexionTienda();
            Connection cn = cc.conectar();
            Map parametros = new HashedMap();
            parametros.put("numFac", Integer.valueOf(jTable_Factura.getValueAt(jTable_Factura.getSelectedRow(), 0).toString()));
            JasperReport reporte;
            try {
                reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/rptFacturaVenta.jasper"));
                JasperPrint imprimir = JasperFillManager.fillReport(reporte, parametros, cn);
                //JasperViewer.viewReport(imprimir, false);
                JDialog frame = new JDialog(this);
                frame.getContentPane().add(new JRViewer(imprimir));
                frame.pack();
                frame.setResizable(true);
                frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                //frame.setLocationRelativeTo(null);
                frame.setSize(1000, 700);
                //frame.setUndecorated(false);
                frame.setVisible(true);
                cn.close();
            } catch (JRException ex) {
                JOptionPane.showMessageDialog(null, "Error al generar el reporte\n" + ex);
            } catch (Exception ex) {
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField_Ced = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField_Nom = new javax.swing.JTextField();
        jButton_Facturar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Factura = new javax.swing.JTable();

        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("DATOS CLIENTE"));

        jLabel1.setText("Cédula:");

        jLabel2.setText("Apellidos y nombres:");

        jButton_Facturar.setText("Generar Factura");
        jButton_Facturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_FacturarActionPerformed(evt);
            }
        });

        jButton1.setText("Volver");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField_Ced, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField_Nom, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_Facturar, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField_Ced, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField_Nom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Facturar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable_Factura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable_Factura);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_FacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_FacturarActionPerformed
        // TODO add your handling code here:
        reporte();
    }//GEN-LAST:event_jButton_FacturarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FacturaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FacturaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FacturaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FacturaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FacturaCliente(new javax.swing.JFrame(), true).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_Facturar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Factura;
    private javax.swing.JTextField jTextField_Ced;
    private javax.swing.JTextField jTextField_Nom;
    // End of variables declaration//GEN-END:variables
}
