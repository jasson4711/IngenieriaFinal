/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import Acceso.Login;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author PC
 */
public class FramePrincipal extends javax.swing.JFrame {

    /**
     * Creates new form FramePrincipal
     */
    public static String cedUsuario;
    public Usuario usuario;

    public FramePrincipal(String cedUsu) {
        initComponents();
        ponerIcono();
        cedUsuario = cedUsu;
        PonerImagenFondo();
        this.setLocationRelativeTo(null);
        usuario = obtenerUsuario();

    }
    
    public void ponerIcono(){
        try{
            this.setIconImage(new ImageIcon(getClass().getResource("../imagenes/LogoR.png")).getImage());
            System.out.println("hola");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "error poniendo fondo imagen");
        }
        
    }

    private void PonerImagenFondo() {
        ((JPanel) getContentPane()).setOpaque(false);
        ImageIcon uno = new ImageIcon(this.getClass().getResource("/imagenes/menu/fondoMenu.png"));
        JLabel fondo = new JLabel();
        fondo.setIcon(uno);
        getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0, 0, uno.getIconWidth(), uno.getIconHeight());
    }

    public static Usuario obtenerUsuario() {
        String sql = "select * from usuarios where cod_usu ='" + cedUsuario + "'";
        ConexionTienda cc = new ConexionTienda();
        Connection cn = cc.conectar();
        Usuario usu = null;
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                usu = new Usuario(cedUsuario, rs.getString(2), rs.getString(3), rs.getString(4));
                return usu;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al recuperar datos de la base \n" + ex);
            try {
                cn.close();
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "Error de conexión");
            }
        }
        return usu;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_Administrador = new javax.swing.JButton();
        btn_Clientes = new javax.swing.JButton();
        btn_Ventas = new javax.swing.JButton();
        btn_Inventario = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton_Salir = new javax.swing.JButton();
        btn_Proveedores = new javax.swing.JButton();
        btn_Reportes = new javax.swing.JButton();
        btn_Pedidos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        btn_Administrador.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_Administrador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/menu/administrador.png"))); // NOI18N
        btn_Administrador.setText("Administrador");
        btn_Administrador.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Administrador.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_Administrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AdministradorActionPerformed(evt);
            }
        });

        btn_Clientes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_Clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/menu/clientes.png"))); // NOI18N
        btn_Clientes.setText("Clientes");
        btn_Clientes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Clientes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_Clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ClientesActionPerformed(evt);
            }
        });

        btn_Ventas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_Ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/menu/venta.png"))); // NOI18N
        btn_Ventas.setText("Ventas");
        btn_Ventas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Ventas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_Ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_VentasActionPerformed(evt);
            }
        });

        btn_Inventario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_Inventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/menu/stock.png"))); // NOI18N
        btn_Inventario.setText("Inventario");
        btn_Inventario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Inventario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_Inventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_InventarioActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/menu/LogoR.png"))); // NOI18N

        jButton_Salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/if_Gnome-Application-Exit-48_55222.png"))); // NOI18N
        jButton_Salir.setText("Salir");
        jButton_Salir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_Salir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SalirActionPerformed(evt);
            }
        });

        btn_Proveedores.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_Proveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/menu/proveedores.png"))); // NOI18N
        btn_Proveedores.setText("Proveedores");
        btn_Proveedores.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Proveedores.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_Proveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ProveedoresActionPerformed(evt);
            }
        });

        btn_Reportes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_Reportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/menu/reportes.png"))); // NOI18N
        btn_Reportes.setText("Reportes");
        btn_Reportes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Reportes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_Reportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ReportesActionPerformed(evt);
            }
        });

        btn_Pedidos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_Pedidos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/menu/pedidos.png"))); // NOI18N
        btn_Pedidos.setText("Pedidos");
        btn_Pedidos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Pedidos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_Pedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PedidosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_Ventas, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Inventario, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_Proveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_Pedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Administrador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Salir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Reportes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Clientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_Reportes)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_Ventas, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_Administrador, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 84, Short.MAX_VALUE)))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_Inventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_Clientes, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton_Salir))
                    .addComponent(btn_Proveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Pedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ClientesActionPerformed
        // TODO add your handling code here:
        new Clientes(null, true).setVisible(true);
    }//GEN-LAST:event_btn_ClientesActionPerformed

    private void btn_AdministradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AdministradorActionPerformed
        // TODO add your handling code here:
        new Usuarios1(null, true, cedUsuario).setVisible(true);
    }//GEN-LAST:event_btn_AdministradorActionPerformed

    private void btn_InventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_InventarioActionPerformed
        // TODO add your handling code here:
        if (usuario.getCargo().equalsIgnoreCase("Vendedor")) {
            new InventarioVendedor(null, true).setVisible(true);
        } else {
            new Inventario(null, true).setVisible(true);
        }
    }//GEN-LAST:event_btn_InventarioActionPerformed

    private void jButton_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton_SalirActionPerformed

    private void btn_VentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_VentasActionPerformed
        // TODO add your handling code here:
        new Venta(null, true).setVisible(true);
    }//GEN-LAST:event_btn_VentasActionPerformed

    private void btn_ProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ProveedoresActionPerformed
        // TODO add your handling code here:
        new Proveedores(null, true).setVisible(true);
    }//GEN-LAST:event_btn_ProveedoresActionPerformed

    private void btn_ReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ReportesActionPerformed
        // TODO add your handling code here:
        Menu menu = new Menu();
        menu.setVisible(true);
    }//GEN-LAST:event_btn_ReportesActionPerformed

    private void btn_PedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PedidosActionPerformed
        // TODO add your handling code here:
        new VerPedidos(null, true).setVisible(true);
    }//GEN-LAST:event_btn_PedidosActionPerformed

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
            java.util.logging.Logger.getLogger(FramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FramePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new FramePrincipal("").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btn_Administrador;
    public javax.swing.JButton btn_Clientes;
    public javax.swing.JButton btn_Inventario;
    public javax.swing.JButton btn_Pedidos;
    public javax.swing.JButton btn_Proveedores;
    public javax.swing.JButton btn_Reportes;
    public javax.swing.JButton btn_Ventas;
    private javax.swing.JButton jButton_Salir;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
