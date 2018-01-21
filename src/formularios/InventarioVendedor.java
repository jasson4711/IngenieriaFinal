/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Usuario
 */
public class InventarioVendedor extends javax.swing.JDialog {

    DefaultTableModel modeloTabla;
    DefaultComboBoxModel modeloCombo;
    TableColumnModel modeloColumna;
    boolean vale = false;
    int cantidadRestante;
    ArrayList<Producto> productos;

    /**
     * Creates new form Clientes
     */
    public InventarioVendedor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setearCodigo(jTable_Inventario, jLabel_Codigo);
        setLocationRelativeTo(parent);
        cargarDatosProductos("");
        jLabel_Codigo.setVisible(false);
        jTextField_Cant.setVisible(false);
        jButton_Añadir.setVisible(false);
        jLabel_Cant.setVisible(false);
    }

    public InventarioVendedor(java.awt.Frame parent, boolean modal, ArrayList<Producto> listaProd) {
        super(parent, modal);
        initComponents();
        setearCodigo(jTable_Inventario, jLabel_Codigo);
        setLocationRelativeTo(parent);
        cargarDatosProductos("");
        jLabel_Codigo.setVisible(false);
        productos = listaProd;
            jTextField_Cant.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public void establecerTamañoColumnas() {
        modeloColumna = jTable_Inventario.getColumnModel();
        modeloColumna.getColumn(0).setPreferredWidth(60);
        modeloColumna.getColumn(1).setPreferredWidth(80);
        modeloColumna.getColumn(2).setPreferredWidth(80);
        modeloColumna.getColumn(3).setPreferredWidth(60);
        modeloColumna.getColumn(4).setPreferredWidth(60);
        modeloColumna.getColumn(5).setPreferredWidth(55);
        modeloColumna.getColumn(6).setPreferredWidth(55);
        modeloColumna.getColumn(7).setPreferredWidth(140);
    }

    public void cargarDatosProductos(String Dato) {

        String[] titulos = {"CÓDIGO", "NOMBRE", "MARCA", "TALLA", "COLOR", "P/V", "STOCK", "DESCRIPCIÓN"};
        String[] registros = new String[8];
        jTable_Inventario.getTableHeader().setReorderingAllowed(false);
        jTable_Inventario.getTableHeader().setResizingAllowed(false);

        modeloTabla = new DefaultTableModel(null, titulos) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ConexionTienda cc = new ConexionTienda();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "select p.id_pro,p.tip_pro,p.mar_pro,t.des_tal,c.nom_col,p.pre_ven,p.sto_pro,p.des_pro "
                + "from productos p, colores c, tallas t "
                + "where p.TIP_PRO LIKE '" + Dato + "%' "
                + "and p.id_col_per=c.id_col "
                + "and p.id_tal_per=t.id_tal "
                + "order by p.TIP_PRO";
        //sql = "select * from productos where TIP_PRO LIKE '" + Dato + "%' order by TIP_PRO";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString(1);
                registros[1] = rs.getString(2);
                registros[2] = rs.getString(3);
                registros[3] = rs.getString(4);
                registros[4] = rs.getString(5);
                registros[5] = rs.getString(6);
                registros[6] = rs.getString(7);
                registros[7] = rs.getString(8);
                modeloTabla.addRow(registros);
            }
            jTable_Inventario.setModel(modeloTabla);
            establecerTamañoColumnas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (Exception ex1) {
        }

    }

    public void setearCodigo(final JTable tabla, final JLabel lab) {
        tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (tabla.getSelectedRow() != -1) {
                    int fila = tabla.getSelectedRow();
                    lab.setText(tabla.getValueAt(fila, 0).toString());
                    jTextField_Cant.requestFocus();
                }
            }
        });
    }

    public int obtenerCantidad() {
        return Integer.valueOf(jTextField_Cant.getText());
    }

    public int obtenerExistencia(String codigo) {
        String sql = "select * from productos where id_pro='" + codigo + "'";
        ConexionTienda cc = new ConexionTienda();
        Connection cn = cc.conectar();
        int aux = 0;
        for (int i = 0; i < productos.size(); i++) {
            if (codigo.equals(productos.get(i).getCodigo())) {
                aux = productos.get(i).getCantidad();
                break;
            }
        }
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                
                return rs.getInt("STO_PRO")-aux;
            }
            cn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al obtener datos de la base:\n" + ex);
            try {
                cn.close();
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "Error de conexión");
            }
        }
        return 0;
    }

    public boolean validarExistencia() {
        int existencia = obtenerExistencia(jLabel_Codigo.getText());
        int cantidad = 0;
        try {
            cantidad = Integer.valueOf(jTextField_Cant.getText());
        } catch (java.lang.NumberFormatException ex) {
        }
        if (cantidad > existencia) {
            JOptionPane.showMessageDialog(null, "No existe la cantidad ingresada de artículo ");
            return false;
        } else if (cantidad == existencia) {
            JOptionPane.showMessageDialog(null, "Aviso: pocos artículos de este tipo");
            vale = true;
            return true;
        }
        vale = true;
        return true;
    }

    public String enviarCodigo() {
        return jLabel_Codigo.getText();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Inventario = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jTextField_Buscar = new javax.swing.JTextField();
        jButton_Volver = new javax.swing.JButton();
        jLabel_Cant = new javax.swing.JLabel();
        jTextField_Cant = new javax.swing.JTextField();
        jButton_Añadir = new javax.swing.JButton();
        jLabel_Codigo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Inventario"));

        jTable_Inventario.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable_Inventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_InventarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_Inventario);

        jLabel8.setText("Buscar:");

        jTextField_Buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_BuscarKeyReleased(evt);
            }
        });

        jButton_Volver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salir.png"))); // NOI18N
        jButton_Volver.setText("Volver");
        jButton_Volver.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jButton_Volver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_VolverActionPerformed(evt);
            }
        });

        jLabel_Cant.setText("Cantidad:");

        jTextField_Cant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_CantKeyTyped(evt);
            }
        });

        jButton_Añadir.setText("Añadir");
        jButton_Añadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AñadirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel_Cant, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField_Cant, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton_Añadir))
                            .addComponent(jTextField_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(137, 137, 137)
                        .addComponent(jButton_Volver)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel_Codigo))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton_Volver)
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_Cant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_Cant))
                        .addGap(10, 10, 10)
                        .addComponent(jLabel_Codigo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextField_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton_Añadir)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable_InventarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_InventarioMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable_InventarioMouseClicked

    private void jTextField_BuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_BuscarKeyReleased
        // TODO add your handling code here:
        cargarDatosProductos(jTextField_Buscar.getText());
    }//GEN-LAST:event_jTextField_BuscarKeyReleased

    private void jButton_VolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_VolverActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton_VolverActionPerformed

    private void jTextField_CantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_CantKeyTyped
        // TODO add your handling code here:
        Metodos.validarCamposSoloNumeros(evt, jTextField_Cant, 2);
    }//GEN-LAST:event_jTextField_CantKeyTyped

    private void jButton_AñadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AñadirActionPerformed
        // TODO add your handling code here:
        if (jLabel_Codigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto");
        } else if (jTextField_Cant.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese la cantidad");
        } else if (validarExistencia()) {
            this.dispose();
        }
   }//GEN-LAST:event_jButton_AñadirActionPerformed

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
            java.util.logging.Logger.getLogger(InventarioVendedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InventarioVendedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InventarioVendedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InventarioVendedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                InventarioVendedor dialog = new InventarioVendedor(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton_Añadir;
    private javax.swing.JButton jButton_Volver;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel_Cant;
    public javax.swing.JLabel jLabel_Codigo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Inventario;
    private javax.swing.JTextField jTextField_Buscar;
    private javax.swing.JTextField jTextField_Cant;
    // End of variables declaration//GEN-END:variables
}
