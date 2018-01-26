/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.awt.event.KeyEvent;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Administrador
 */
public class Usuarios1 extends javax.swing.JDialog {

    /**
     * Creates new form Usuarios1
     */
    int cont = 0;
    boolean paraBuscar;
    String cedUser;
    TableColumnModel modeloColumna;

    public Usuarios1(java.awt.Frame parent, boolean modal, String cedUsu) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
        cedUser = cedUsu;
        botonesInicio();
        txtBloqueo(false);
        cargarTablaUsuarios("");

        jLabel_ConfirmarContrasenia.setVisible(false);
        jLabel_ContraseniasDiferentes.setVisible(false);
        llenarComboBox();
        mostrarDatosSeleccionaTabla();

    }

    public void mostrarDatosSeleccionaTabla() {
        jTable_Usuarios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override //ir cambiando los valores si se selecciona una fila
            public void valueChanged(ListSelectionEvent e) {

                if (jTable_Usuarios.getSelectedRow() != -1) {
                    int fila = jTable_Usuarios.getSelectedRow();
                    jTextField_Cedula.setText(String.valueOf(jTable_Usuarios.getValueAt(fila, 0)).trim());
                    jTextField_Apellido.setText(String.valueOf(jTable_Usuarios.getValueAt(fila, 1)).trim());
                    jTextField_Nombre.setText(String.valueOf(jTable_Usuarios.getValueAt(fila, 2)).trim());
                    jComboBox_Cargo.setSelectedItem(String.valueOf(jTable_Usuarios.getValueAt(fila, 3)).trim());
                    jPasswordField_Contraseña.setText(String.valueOf(jTable_Usuarios.getValueAt(fila, 4)).trim());
                    jPasswordField_ContraseñaCon.setText(String.valueOf(jTable_Usuarios.getValueAt(fila, 4)).trim());
                    txtBloqueo(true);
                    jTextField_Cedula.setEnabled(false);
                    botonesActualizar();
                }
            }
        });
    }

    public void establecerTamañoColumnas() {
        modeloColumna = jTable_Usuarios.getColumnModel();
        modeloColumna.getColumn(0).setPreferredWidth(80);
        modeloColumna.getColumn(1).setPreferredWidth(110);
        modeloColumna.getColumn(2).setPreferredWidth(110);
        modeloColumna.getColumn(3).setPreferredWidth(90);
        modeloColumna.getColumn(4).setPreferredWidth(120);

    }

    public void cargarTablaUsuarios(String Dato) {

        String[] titulos = {"CÉDULA", "APELLIDO", "NOMBRE", "CARGO", "CONTRASEÑA"};
        String[] registros = new String[5];
        jTable_Usuarios.getTableHeader().setReorderingAllowed(false);
        jTable_Usuarios.getTableHeader().setResizingAllowed(false);
        DefaultTableModel modeloTabla = new DefaultTableModel(null, titulos) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ConexionTienda cc = new ConexionTienda();
        Connection cn = cc.conectar();
        String sql = "";
        if (paraBuscar) {
            sql = "select * from usuarios where cod_usu like '" + Dato + "%' and cod_usu <> '1103070734' order by ape_usu";
        } else {
            sql = "select * from usuarios where ape_usu like '" + Dato + "%' and cod_usu <> '1103070734' order by ape_usu";
        }

        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql); //Manejar celda por celda el resultado del statement (consulta)
            while (rs.next()) {
                registros[0] = rs.getString("cod_usu").trim();
                registros[1] = rs.getString("ape_usu").trim();
                registros[2] = rs.getString("nom_usu").trim();
                registros[3] = rs.getString("cargo").trim();
                registros[4] = Encriptacion.Desencriptar(rs.getString("cla_usu"));
                modeloTabla.addRow(registros);

            }
            jTable_Usuarios.setModel(modeloTabla);
            establecerTamañoColumnas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (Exception ex) {
        }

    }

    public boolean validarContrasenias() {
        String contraseña = jPasswordField_Contraseña.getText();
        contraseña = contraseña.trim();
        char con[] = contraseña.toCharArray();
        int hayLetra = 0;
        int hayNumero = 0;
        for (int i = 0; i < con.length; i++) {
            char c = con[i];
            if (c < '0' || c > '9') {
                hayLetra += 1;
            }
            if (c > '0' && c < '9') {
                hayNumero += 1;
            }
        }
        if (contraseña.length() < 6) {
            JOptionPane.showMessageDialog(null, "Contraseña no válida\nContraseña debe tener:\n6 caracteres entre letras y números", "Error", JOptionPane.ERROR_MESSAGE);
            jPasswordField_Contraseña.setText("");
            jPasswordField_ContraseñaCon.setText("");
            jPasswordField_Contraseña.requestFocus();
            return false;
        } else if (hayLetra == 0) {
            JOptionPane.showMessageDialog(null, "Contraseña no válida\nContraseña debe tener al menos una letra", "Error", JOptionPane.ERROR_MESSAGE);
            jPasswordField_Contraseña.setText("");
            jPasswordField_ContraseñaCon.setText("");
            jPasswordField_Contraseña.requestFocus();
            return false;
        } else if (hayNumero == 0) {
            JOptionPane.showMessageDialog(null, "Contraseña no válida\nContraseña debe tener al menos un número", "Error", JOptionPane.ERROR_MESSAGE);
            jPasswordField_Contraseña.setText("");
            jPasswordField_ContraseñaCon.setText("");
            jPasswordField_Contraseña.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    public void guardar() {
        if (jTextField_Cedula.getText().isEmpty() || !Metodos.verificadorCédula(jTextField_Cedula.getText())) {
            JOptionPane.showMessageDialog(null, "Debe ingresar una cédula correcta");
            jTextField_Cedula.requestFocus();
        } else if (jTextField_Nombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el apellido");
            jTextField_Nombre.requestFocus();
        } else if (jTextField_Apellido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el nombre");
            jTextField_Apellido.requestFocus();
        } else if (jPasswordField_Contraseña.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la contraseña");
            jPasswordField_Contraseña.requestFocus();
        } else if (jPasswordField_ContraseñaCon.getText().isEmpty()) {
            jLabel_ConfirmarContrasenia.setVisible(true);
            //jLabel_ContraseniasDiferentes.setEnabled(false);
            jPasswordField_ContraseñaCon.requestFocus();
            System.out.println(jPasswordField_Contraseña.getText());
        } else if (!jPasswordField_Contraseña.getText().equals(jPasswordField_ContraseñaCon.getText())) {
            jLabel_ConfirmarContrasenia.setVisible(false);
            jLabel_ContraseniasDiferentes.setVisible(true);
            jPasswordField_ContraseñaCon.requestFocus();
        } else if (validarContrasenias()) {
            ConexionTienda cc = new ConexionTienda();
            Connection cn = cc.conectar();
            String cod_usu, ape_usu, nom_usu, cargo, cla_usu;
            cod_usu = jTextField_Cedula.getText().toUpperCase();
            ape_usu = jTextField_Apellido.getText().toUpperCase().trim();
            nom_usu = jTextField_Nombre.getText().toUpperCase().trim();
            cargo = jComboBox_Cargo.getSelectedItem().toString();
            cla_usu = jPasswordField_Contraseña.getText().trim();
            String sql = "insert into usuarios (cod_usu,ape_usu,nom_usu,cargo,cla_usu) values(?,?,?,?,?)";
            try {
                PreparedStatement psd = cn.prepareStatement(sql);
                psd.setString(1, cod_usu);
                psd.setString(2, ape_usu);
                psd.setString(3, nom_usu);
                psd.setString(4, cargo);
                psd.setString(5, Encriptacion.Encriptar(cla_usu));

                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Datos ingresados correctamente");
                    cargarTablaUsuarios("");
                    txtLimpiar();
                    txtBloqueo(false);
                    jLabel_ContraseniasDiferentes.setVisible(false);
                    botonesInicio();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    public void actualizar() {
        if (jTextField_Nombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el nombre");
            jTextField_Nombre.requestFocus();
        } else if (jTextField_Apellido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el apellido");
            jTextField_Apellido.requestFocus();
        } else if (jPasswordField_Contraseña.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la contraseña");
            jPasswordField_Contraseña.requestFocus();
        } else if (jPasswordField_ContraseñaCon.getText().isEmpty()) {
            jLabel_ContraseniasDiferentes.setEnabled(false);
            jLabel_ConfirmarContrasenia.setVisible(true);
            jPasswordField_ContraseñaCon.requestFocus();
            System.out.println(jPasswordField_Contraseña.getText());
        } else if (!jPasswordField_Contraseña.getText().equals(jPasswordField_ContraseñaCon.getText())) {
            jLabel_ConfirmarContrasenia.setVisible(false);
            jLabel_ContraseniasDiferentes.setVisible(true);
            jPasswordField_ContraseñaCon.requestFocus();
        } else if (validarContrasenias()) {
            if (jPasswordField_Contraseña.getText().equals(jPasswordField_ContraseñaCon.getText())) {
                ConexionTienda cc = new ConexionTienda();
                Connection cn = cc.conectar();
                String sql = "";
                System.out.println(jComboBox_Cargo.getSelectedItem().toString().trim());
                sql = "update usuarios set NOM_USU='" + jTextField_Nombre.getText().trim().toUpperCase() + "' "
                        + ",APE_USU='" + jTextField_Apellido.getText().trim().toUpperCase() + "' "
                        + ",CARGO='" + jComboBox_Cargo.getSelectedItem().toString().trim() + "' "
                        + ",CLA_USU='" + Encriptacion.Encriptar(jPasswordField_Contraseña.getText()).trim() + "' "
                        + "where COD_USU='" + jTextField_Cedula.getText().trim() + "'";
                try {
                    PreparedStatement psd = cn.prepareStatement(sql);
                    int n = psd.executeUpdate();
                    if (n > 0) {
                        JOptionPane.showMessageDialog(null, "Se actualizó el registro correctamente");
                        cargarTablaUsuarios("");
                        txtLimpiar();
                        jLabel_ContraseniasDiferentes.setVisible(false);
                        botonesInicio();
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        }
    }

    public void borrar() {

        ConexionTienda cc = new ConexionTienda();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "delete from usuarios where cod_usu='" + jTextField_Cedula.getText() + "'";
        //sql = "update auto set AUT_ESTADO='" + 0 + "' where AUT_PLACA='" + ucTextLetras12.getText() + "'";;
        boolean autenticado = false;
        int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea borrar?", "Borrar Dato", JOptionPane.YES_NO_OPTION);
        if (confirm == 0) {
            try {

                if (jTextField_Cedula.getText().equals(cedUser)) {
                    autenticado = true;
                } else {
                    autenticado = false;
                }

                if (!autenticado) {
                    PreparedStatement psd = cn.prepareStatement(sql);
                    int n = psd.executeUpdate();
                    if (n > 0) {
                        JOptionPane.showMessageDialog(null, "Se borró el registro correctamente");
                        cargarTablaUsuarios("");
                        txtLimpiar();
                        botonesInicio();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error no puede eliminar usuario de la sesión activa");
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

        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel_ContraseniasDiferentes = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel_ConfirmarContrasenia = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField_Cedula = new javax.swing.JTextField();
        jTextField_Apellido = new javax.swing.JTextField();
        jTextField_Nombre = new javax.swing.JTextField();
        jTextField_Buscar = new javax.swing.JTextField();
        jComboBox_Cargo = new javax.swing.JComboBox();
        jPasswordField_Contraseña = new javax.swing.JPasswordField();
        jPasswordField_ContraseñaCon = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        jButton_Nuevo = new javax.swing.JButton();
        jButton_Guardar = new javax.swing.JButton();
        jButton_Actualizar = new javax.swing.JButton();
        jButton_Cancelar = new javax.swing.JButton();
        jButton_Borrar = new javax.swing.JButton();
        jButton_Volver = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Usuarios = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setText("Cédula:");

        jLabel9.setText("Buscar:");

        jLabel_ContraseniasDiferentes.setForeground(new java.awt.Color(255, 0, 0));
        jLabel_ContraseniasDiferentes.setText("Las contraseñas no coinciden.");

        jLabel7.setText("Confirmar Contraseña:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Cargo:");

        jLabel5.setText("Contraseña:");

        jLabel_ConfirmarContrasenia.setForeground(new java.awt.Color(255, 0, 0));
        jLabel_ConfirmarContrasenia.setText("Confirme la contraseña porfavor.");

        jLabel1.setText("Apellido:");

        jTextField_Cedula.setNextFocusableComponent(jTextField_Apellido);
        jTextField_Cedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_CedulaKeyTyped(evt);
            }
        });

        jTextField_Apellido.setNextFocusableComponent(jTextField_Nombre);
        jTextField_Apellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_ApellidoKeyTyped(evt);
            }
        });

        jTextField_Nombre.setNextFocusableComponent(jComboBox_Cargo);
        jTextField_Nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_NombreKeyTyped(evt);
            }
        });

        jTextField_Buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_BuscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_BuscarKeyTyped(evt);
            }
        });

        jComboBox_Cargo.setNextFocusableComponent(jPasswordField_Contraseña);

        jPasswordField_Contraseña.setNextFocusableComponent(jPasswordField_ContraseñaCon);
        jPasswordField_Contraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jPasswordField_ContraseñaKeyTyped(evt);
            }
        });

        jPasswordField_ContraseñaCon.setNextFocusableComponent(jButton_Nuevo);
        jPasswordField_ContraseñaCon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jPasswordField_ContraseñaConKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(20, 20, 20)
                            .addComponent(jTextField_Buscar))
                        .addComponent(jLabel_ContraseniasDiferentes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField_Apellido)
                                .addComponent(jTextField_Nombre)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox_Cargo, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField_Cedula, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPasswordField_Contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPasswordField_ContraseñaCon, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(0, 0, Short.MAX_VALUE)))))
                    .addComponent(jLabel_ConfirmarContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField_Cedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField_Apellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField_Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox_Cargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jPasswordField_Contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField_ContraseñaCon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_ConfirmarContrasenia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_ContraseniasDiferentes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton_Nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        jButton_Nuevo.setText("Nuevo");
        jButton_Nuevo.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jButton_Nuevo.setNextFocusableComponent(jButton_Guardar);
        jButton_Nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_NuevoActionPerformed(evt);
            }
        });

        jButton_Guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        jButton_Guardar.setText("Guardar");
        jButton_Guardar.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jButton_Guardar.setNextFocusableComponent(jButton_Actualizar);
        jButton_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarActionPerformed(evt);
            }
        });

        jButton_Actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
        jButton_Actualizar.setText("Actualizar");
        jButton_Actualizar.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jButton_Actualizar.setName(""); // NOI18N
        jButton_Actualizar.setNextFocusableComponent(jButton_Cancelar);
        jButton_Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ActualizarActionPerformed(evt);
            }
        });

        jButton_Cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelar.png"))); // NOI18N
        jButton_Cancelar.setText("Cancelar");
        jButton_Cancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jButton_Cancelar.setNextFocusableComponent(jButton_Borrar);
        jButton_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CancelarActionPerformed(evt);
            }
        });

        jButton_Borrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/borrar.png"))); // NOI18N
        jButton_Borrar.setText("Borrar");
        jButton_Borrar.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jButton_Borrar.setNextFocusableComponent(jButton_Volver);
        jButton_Borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_BorrarActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_Guardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Actualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Cancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Borrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Volver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Nuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton_Nuevo)
                .addGap(18, 18, 18)
                .addComponent(jButton_Guardar)
                .addGap(18, 18, 18)
                .addComponent(jButton_Actualizar)
                .addGap(18, 18, 18)
                .addComponent(jButton_Cancelar)
                .addGap(18, 18, 18)
                .addComponent(jButton_Borrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jButton_Volver)
                .addGap(22, 22, 22))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTable_Usuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable_Usuarios.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(jTable_Usuarios);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void llenarComboBox() {
        DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel();
        jComboBox_Cargo.setModel(modeloCombo);
        modeloCombo.addElement("Administrador");
        modeloCombo.addElement("Vendedor");
        modeloCombo.addElement("Bodeguero");

    }

    public void txtLimpiar() {
        jTextField_Cedula.setText("");
        jTextField_Apellido.setText("");
        jTextField_Nombre.setText("");
        jPasswordField_Contraseña.setText("");
        jPasswordField_ContraseñaCon.setText("");
        txtBloqueo(false);

    }

    public void txtBloqueo(boolean tutia) {
        jTextField_Cedula.requestFocus();
        jTextField_Cedula.setEnabled(tutia);
        jTextField_Apellido.setEnabled(tutia);
        jTextField_Nombre.setEnabled(tutia);
        jComboBox_Cargo.setEnabled(tutia);
        jPasswordField_Contraseña.setEnabled(tutia);
        jPasswordField_ContraseñaCon.setEnabled(tutia);
    }

    public void botonesNuevo() {
        jButton_Actualizar.setEnabled(false);
        jButton_Borrar.setEnabled(false);
        jButton_Cancelar.setEnabled(true);
        jButton_Guardar.setEnabled(true);
        jButton_Nuevo.setEnabled(false);
        jButton_Volver.setEnabled(true);
    }

    public void botonesInicio() {
        jButton_Actualizar.setEnabled(false);
        jButton_Borrar.setEnabled(false);
        jButton_Cancelar.setEnabled(false);
        jButton_Guardar.setEnabled(false);
        jButton_Nuevo.setEnabled(true);
        jButton_Volver.setEnabled(true);
    }

    public void botonesActualizar() {
        jButton_Actualizar.setEnabled(true);
        jButton_Borrar.setEnabled(true);
        jButton_Cancelar.setEnabled(true);
        jButton_Guardar.setEnabled(false);
        jButton_Nuevo.setEnabled(false);
        jButton_Volver.setEnabled(true);
    }

    public void ValidarIngresoBusqueda(KeyEvent evt, JTextField componente) {
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (cont == 0) {
            if (Character.isDigit(c)) {
                paraBuscar = true;

            } else if (Character.isLetter(c)) {
                paraBuscar = false;
            }
        }
        cont++;
        if (componente.getText().isEmpty()) {
            cont = 0;
        }


        cargarTablaUsuarios(componente.getText());
    }

    public void validarSoloLetrasSoloNumerosBuscar(KeyEvent evt) {
        // TODO add your handling code here:
        if (cont != 0) {
            char c = evt.getKeyChar();
            if (paraBuscar) {
                if (Character.isLetter(c)) {
                    evt.consume();
                }
            } else if (!paraBuscar) {
                if (Character.isDigit(c)) {
                    evt.consume();
                }
            }
        }
    }

    private void jTextField_CedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_CedulaKeyTyped
        // TODO add your handling code here:
        Metodos.validarCamposSoloNumeros(evt, jTextField_Cedula, 10);
    }//GEN-LAST:event_jTextField_CedulaKeyTyped

    private void jTextField_ApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_ApellidoKeyTyped
        // TODO add your handling code here:
        Metodos.validarCamposSoloLetras(evt, jTextField_Apellido, 15);
    }//GEN-LAST:event_jTextField_ApellidoKeyTyped

    private void jTextField_NombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_NombreKeyTyped
        // TODO add your handling code here:
        Metodos.validarCamposSoloLetras(evt, jTextField_Nombre, 15);
    }//GEN-LAST:event_jTextField_NombreKeyTyped

    private void jPasswordField_ContraseñaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField_ContraseñaKeyTyped
        // TODO add your handling code here:
        if (jPasswordField_Contraseña.getText().length() >= 15) {
            evt.consume();
        }
    }//GEN-LAST:event_jPasswordField_ContraseñaKeyTyped

    private void jPasswordField_ContraseñaConKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField_ContraseñaConKeyTyped
        // TODO add your handling code here:
        if (jPasswordField_ContraseñaCon.getText().length() >= 15) {
            evt.consume();
        }
    }//GEN-LAST:event_jPasswordField_ContraseñaConKeyTyped

    private void jButton_VolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_VolverActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton_VolverActionPerformed

    private void jButton_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NuevoActionPerformed
        // TODO add your handling code here:
        botonesNuevo();
        txtBloqueo(true);
    }//GEN-LAST:event_jButton_NuevoActionPerformed

    private void jButton_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarActionPerformed
        // TODO add your handling code here:
        guardar();
    }//GEN-LAST:event_jButton_GuardarActionPerformed

    private void jButton_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CancelarActionPerformed
        // TODO add your handling code here:
        botonesInicio();
        txtLimpiar();
        txtBloqueo(false);
        jLabel_ConfirmarContrasenia.setVisible(false);
        jLabel_ContraseniasDiferentes.setVisible(false);

    }//GEN-LAST:event_jButton_CancelarActionPerformed

    private void jButton_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ActualizarActionPerformed
        // TODO add your handling code here:
        actualizar();
    }//GEN-LAST:event_jButton_ActualizarActionPerformed

    private void jButton_BorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_BorrarActionPerformed
        // TODO add your handling code here:
        borrar();
    }//GEN-LAST:event_jButton_BorrarActionPerformed

    private void jTextField_BuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_BuscarKeyTyped
        // TODO add your handling code here:
        validarSoloLetrasSoloNumerosBuscar(evt);

    }//GEN-LAST:event_jTextField_BuscarKeyTyped

    private void jTextField_BuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_BuscarKeyReleased
        // TODO add your handling code here:
        ValidarIngresoBusqueda(evt, jTextField_Buscar);
    }//GEN-LAST:event_jTextField_BuscarKeyReleased

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
            java.util.logging.Logger.getLogger(Usuarios1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuarios1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuarios1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuarios1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                Usuarios1 dialog = new Usuarios1(new javax.swing.JFrame(), true, "");
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
    private javax.swing.JButton jButton_Actualizar;
    private javax.swing.JButton jButton_Borrar;
    private javax.swing.JButton jButton_Cancelar;
    private javax.swing.JButton jButton_Guardar;
    private javax.swing.JButton jButton_Nuevo;
    private javax.swing.JButton jButton_Volver;
    private javax.swing.JComboBox jComboBox_Cargo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_ConfirmarContrasenia;
    private javax.swing.JLabel jLabel_ContraseniasDiferentes;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField_Contraseña;
    private javax.swing.JPasswordField jPasswordField_ContraseñaCon;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Usuarios;
    private javax.swing.JTextField jTextField_Apellido;
    private javax.swing.JTextField jTextField_Buscar;
    private javax.swing.JTextField jTextField_Cedula;
    private javax.swing.JTextField jTextField_Nombre;
    // End of variables declaration//GEN-END:variables
}
