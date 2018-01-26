/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.sql.*;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
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
 * @author user
 */
public class VerPedidos extends javax.swing.JDialog {

    /**
     * Creates new form VerPedidos
     */
    DefaultTableModel model;
    TableColumnModel modeloColumna;

    public VerPedidos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        cargarPedidos("");
    }

    public void establecerTamañoColumnas() {
        modeloColumna = jTable_Pedidos.getColumnModel();
        modeloColumna.getColumn(0).setPreferredWidth(80);
        modeloColumna.getColumn(1).setPreferredWidth(110);
        modeloColumna.getColumn(2).setPreferredWidth(110);
        modeloColumna.getColumn(3).setPreferredWidth(120);
        modeloColumna.getColumn(4).setPreferredWidth(80);
        modeloColumna.getColumn(5).setPreferredWidth(150);

    }

    public void cargarPedidos(String Dato) {
        String[] titulos = {"NÚMERO", "PROVEEDOR", "FECHA", "CONFIRMADO"};
        String[] registros = new String[4];
        jTable_Pedidos.getTableHeader().setReorderingAllowed(false);
        jTable_Pedidos.getTableHeader().setResizingAllowed(false);
        model = new DefaultTableModel(null, titulos) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ConexionTienda cc = new ConexionTienda();
        Connection cn = cc.conectar();
        String sql = "";

        sql = "select p.num_ped, pro.nom_pro, p.fec_ped, p.confirmado "
                + "from PEDIDOS_cab p,proveedores pro "
                + "where num_ped LIKE '" + Dato + "%' "
                + "and p.cod_pro_pid=pro.cod_pro "
                + "order by fec_ped desc";

        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString("num_ped");
                registros[1] = rs.getString("nom_pro");
                registros[2] = rs.getString("fec_ped");
                registros[3] = rs.getString("confirmado");
                model.addRow(registros);
            }
            jTable_Pedidos.setModel(model);
            establecerTamañoColumnas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (Exception ex1) {
        }

    }

    public void confirmarPedido() {
        if (jTable_Pedidos.getSelectedRow() > -1) {
            if (jTable_Pedidos.getValueAt(jTable_Pedidos.getSelectedRow(), 3).toString().equals("S")) {
                JOptionPane.showMessageDialog(null, "El pedido ya se ha confirmado");
            } else {
                int conf = JOptionPane.showConfirmDialog(null, "¿Confirmar este pedido?", "CONFIRMAR", JOptionPane.YES_OPTION);
                if (conf == 0) {
                    String sql = "select * from detalle_pedidos where num_ped_p=" + jTable_Pedidos.getValueAt(jTable_Pedidos.getSelectedRow(), 0).toString();
                    //String det = "select count(*) from detalle_pedidos where num_ped_p="+ jTable_Pedidos.getValueAt(jTable_Pedidos.getSelectedRow(), 0).toString();
                    String updPed = "update pedidos_cab set confirmado ='S' where num_ped=" + jTable_Pedidos.getValueAt(jTable_Pedidos.getSelectedRow(), 0).toString();
                    String updPro = "";
                    String id_pro;
                    int cantidad;
                    int n = 0;
                    ConexionTienda cc = new ConexionTienda();
                    Connection cn = cc.conectar();
                    try {
                        Statement st = cn.createStatement();
                        ResultSet rs = st.executeQuery(sql);
                        while (rs.next()) {
                            id_pro = rs.getString("id_pro_v");
                            cantidad = rs.getInt("cantidad");
                            updPro = "update productos set sto_pro=sto_pro+" + cantidad + " where id_pro='" + id_pro + "' ";
                            PreparedStatement psd = cn.prepareStatement(updPro);
                            n += psd.executeUpdate();
                        }
                        PreparedStatement psdPed = cn.prepareStatement(updPed);
                        n = psdPed.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Confirmación realizada correctamente");
                        cargarPedidos("");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Ocurrió un error confirmando el pedido\n" + ex);
                    } catch (Exception ex1) {
                    }
                }

            }

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un pedido");
        }
    }
    
    public void reporte(int numFac) {
        ConexionTienda cc = new ConexionTienda();
        Connection cn = cc.conectar();
        Map parametros = new HashedMap();
        parametros.put("numPed", numFac);
        JasperReport reporte;
        try {
            reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/Reportes/rptOrdenPedido.jasper"));
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Pedidos = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jTable_Pedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable_Pedidos);

        jButton1.setText("Confirmar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Volver");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Revisar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        confirmarPedido();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        if (jTable_Pedidos.getSelectedRow() > -1) {
            reporte(Integer.valueOf(jTable_Pedidos.getValueAt(jTable_Pedidos.getSelectedRow(), 0).toString()));
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un pedido");
        }

    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(VerPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VerPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VerPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VerPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                VerPedidos dialog = new VerPedidos(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Pedidos;
    // End of variables declaration//GEN-END:variables
}
