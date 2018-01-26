/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Acceso;

import formularios.ConexionTienda;
import formularios.Encriptacion;
import formularios.FramePrincipal;
import formularios.Metodos;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author PC
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        ponerIcono();
        EstablecerValoresPorDefecto();
        PonerImagenFondo();
        this.setLocationRelativeTo(null);
        jLabel_Usuario.setVisible(false);
        
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
        ImageIcon uno = new ImageIcon(this.getClass().getResource("/imagenes/fondo1.jpg"));
        JLabel fondo = new JLabel();
        fondo.setIcon(uno);
        getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0, 0, uno.getIconWidth(), uno.getIconHeight());
    }
    
    private void EstablecerValoresPorDefecto() {
        this.setSize(470, 270);
        ucTextNumeros_Usuario.setText("");
        jPassword_Pass.setText("");
    }
    
    public void cargarUsuarios() throws HeadlessException, Exception {
        
        if (ucTextNumeros_Usuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el usuario");
            ucTextNumeros_Usuario.requestFocus();
        } else if (jPassword_Pass.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresa la contraseña");
            jPassword_Pass.requestFocus();
        } else {
            String nom_usu = ucTextNumeros_Usuario.getText().trim();
            String cla_usu = jPassword_Pass.getText().trim();
            ConexionTienda cc = new ConexionTienda();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "SELECT * FROM USUARIOS";
            try {
                Statement psd = cn.createStatement(); //statement devuelve todas las filas
                ResultSet rs = psd.executeQuery(sql);
                while (rs.next()) {
                    //cuando cambies componente solo cambias nom_usu por cod_usu
                    String var1 = rs.getString("COD_USU").toUpperCase();
                    String var2 = Encriptacion.Desencriptar(rs.getString("CLA_USU"));
                    String var5 = rs.getString("CARGO");
                    
                    if (var1.equals(nom_usu) && var2.equals(cla_usu)) {
                        if ("Vendedor".equals(var5)) {
                            this.dispose();
                            FramePrincipal men = new FramePrincipal(var1);
                            men.btn_Administrador.setEnabled(false);
                            men.btn_Reportes.setEnabled(false);
                            men.btn_Proveedores.setEnabled(false);

                            men.setVisible(true);
                            
                        } else if ("Administrador".equals(var5)) {
                            this.dispose();
                            FramePrincipal men = new FramePrincipal(var1);
                            men.btn_Administrador.setEnabled(true);
                            men.btn_Clientes.setEnabled(true);
                            men.btn_Inventario.setEnabled(true);
                            men.btn_Ventas.setEnabled(true);
                            men.btn_Reportes.setEnabled(true);
                            men.btn_Proveedores.setEnabled(true);
                            men.setVisible(true);
                            
                        } else if ("Bodeguero".equals(var5)) {
                            this.dispose();
                            FramePrincipal men = new FramePrincipal(var1);
                            men.btn_Administrador.setEnabled(false);
                            men.btn_Clientes.setEnabled(false);
                            men.btn_Inventario.setEnabled(true);
                            men.btn_Ventas.setEnabled(false);
                            men.btn_Reportes.setEnabled(false);
                            men.btn_Proveedores.setEnabled(false);

                            men.setVisible(true);
                            
                        }
                    } else {
                        jLabel_Usuario.setVisible(true);
                        ucTextNumeros_Usuario.setText("");
                        ucTextNumeros_Usuario.requestFocus();
                        jPassword_Pass.setText("");
                    }



//                {
//          
////                    j.setVisible(true);
////                    txtcalve.setText("");
//                }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPassword_Pass = new javax.swing.JPasswordField();
        btn_Aceptar = new javax.swing.JButton();
        btn_Cancelar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        ucTextNumeros_Usuario = new uctextletras.UcTextNumeros();
        jLabel_Usuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Usuario:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Contraseña:");

        btn_Aceptar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_Aceptar.setText("Aceptar");
        btn_Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AceptarActionPerformed(evt);
            }
        });

        btn_Cancelar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btn_Cancelar.setText("Cancelar");
        btn_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Iniciar Sesión");

        ucTextNumeros_Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ucTextNumeros_UsuarioKeyTyped(evt);
            }
        });

        jLabel_Usuario.setForeground(new java.awt.Color(255, 0, 0));
        jLabel_Usuario.setText("Usuario o Contraseña incorrecto");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(jLabel3)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(btn_Aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1)
                            .addComponent(ucTextNumeros_Usuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jPassword_Pass, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel3)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPassword_Pass, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(ucTextNumeros_Usuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jLabel_Usuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Cancelar)
                    .addComponent(btn_Aceptar))
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AceptarActionPerformed
        try {
            // TODO add your handling code here:
            //        String Pass = new String(jPassword_Pass.getPassword());
            //        if (ucTextLetrasMayusculas_Usuario.getText().equals(Usuario) && Pass.equals(Contraseña)) {
            //            FramePrincipal principal = new FramePrincipal();
            //            principal.setVisible(true);
            //            dispose();
            //        }
            cargarUsuarios();
        } catch (HeadlessException ex) {
            //Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            //Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_AceptarActionPerformed
    
    private void btn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelarActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btn_CancelarActionPerformed
    
    private void ucTextNumeros_UsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ucTextNumeros_UsuarioKeyTyped
        // TODO add your handling code here:
        if (ucTextNumeros_Usuario.getText().length() >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_ucTextNumeros_UsuarioKeyTyped

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Aceptar;
    private javax.swing.JButton btn_Cancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel_Usuario;
    private javax.swing.JPasswordField jPassword_Pass;
    private uctextletras.UcTextNumeros ucTextNumeros_Usuario;
    // End of variables declaration//GEN-END:variables
}
