/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Usuario
 */
public class Clientes extends javax.swing.JDialog {

    DefaultTableModel model;
    TableColumnModel modeloColumna;

    public Clientes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        cargarDatosClientes("");

        botonesInicio();
        txtBloqueo(false);
        mostrarDatosSeleccionaTabla();
        jTextField_Buscar.setEnabled(true);
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


        cargarDatosClientes(componente.getText());
    }

    public void mostrarDatosSeleccionaTabla() {
        jTable_DatosClientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jTable_DatosClientes.getSelectedRow() != -1) {
                    int fila = jTable_DatosClientes.getSelectedRow();

                    jTextField_CedCli.setText(jTable_DatosClientes.getValueAt(fila, 0).toString().trim());
                    jTextField_ApeCli.setText(jTable_DatosClientes.getValueAt(fila, 1).toString().trim());
                    jTextField_NomCli.setText(jTable_DatosClientes.getValueAt(fila, 2).toString().trim());
                    jTextField_DirCli.setText(jTable_DatosClientes.getValueAt(fila, 3).toString().trim());
                    jTextField_TelCli.setText(jTable_DatosClientes.getValueAt(fila, 4).toString().trim());
                    jTextField_Email.setText(jTable_DatosClientes.getValueAt(fila, 5).toString().trim());
                    txtBloqueo(true);
                    jTextField_CedCli.setEnabled(false);
                    botonesBorrar();
                    botonesActualizar();
                }
            }
        });
    }

    public void txtLimpiar() {
        jTextField_CedCli.setText("");
        jTextField_NomCli.setText("");
        jTextField_ApeCli.setText("");
        jTextField_DirCli.setText("");
        jTextField_TelCli.setText("");
        jTextField_Buscar.setText("");
        jTextField_Email.setText("");
        txtBloqueo(false);

    }

    public void botonesBorrar() {
        jButton_Nuevo.setEnabled(false);
        jButton_Guardar.setEnabled(false);
        jButton_Actualizar.setEnabled(true);
        jButton_Cancelar.setEnabled(true);
        jButton_Borrar.setEnabled(true);
        jButton_Volver.setEnabled(true);

    }

    public void botonesActualizar() {
        jButton_Nuevo.setEnabled(false);
        jButton_Guardar.setEnabled(false);
        jButton_Actualizar.setEnabled(true);
        jButton_Cancelar.setEnabled(true);
        jButton_Volver.setEnabled(true);
    }

    public void txtBloqueo(boolean tutia) {
        jTextField_CedCli.requestFocus();
        jTextField_CedCli.setEnabled(tutia);
        jTextField_ApeCli.setEnabled(tutia);
        jTextField_NomCli.setEnabled(tutia);
        jTextField_DirCli.setEnabled(tutia);
        jTextField_TelCli.setEnabled(tutia);
        jTextField_Email.setEnabled(tutia);
        //jTextField_Buscar.setEnabled(tutia);

    }

    public void botonesNuevo() {
        jButton_Actualizar.setEnabled(false);
        jButton_Cancelar.setEnabled(true);
        jButton_Guardar.setEnabled(true);
        jButton_Nuevo.setEnabled(false);
        jButton_Borrar.setEnabled(false);
    }

    public void botonesInicio() {
        jButton_Actualizar.setEnabled(false);
        jButton_Cancelar.setEnabled(false);
        jButton_Guardar.setEnabled(false);
        jButton_Nuevo.setEnabled(true);
        jButton_Borrar.setEnabled(false);
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

    /**
     * Creates new form Clientes
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
////////    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField_CedCli = new javax.swing.JTextField();
        jTextField_ApeCli = new javax.swing.JTextField();
        jTextField_NomCli = new javax.swing.JTextField();
        jTextField_DirCli = new javax.swing.JTextField();
        jTextField_TelCli = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField_Buscar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField_Email = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton_Nuevo = new javax.swing.JButton();
        jButton_Guardar = new javax.swing.JButton();
        jButton_Actualizar = new javax.swing.JButton();
        jButton_Cancelar = new javax.swing.JButton();
        jButton_Borrar = new javax.swing.JButton();
        jButton_Volver = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_DatosClientes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("Cédula:");

        jLabel2.setText("Apellido:");

        jLabel3.setText("Nombre:");

        jLabel4.setText("Dirección:");

        jLabel5.setText("Teléfono:");

        jTextField_CedCli.setEnabled(false);
        jTextField_CedCli.setNextFocusableComponent(jTextField_ApeCli);
        jTextField_CedCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_CedCliActionPerformed(evt);
            }
        });
        jTextField_CedCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_CedCliKeyTyped(evt);
            }
        });

        jTextField_ApeCli.setEnabled(false);
        jTextField_ApeCli.setNextFocusableComponent(jTextField_NomCli);
        jTextField_ApeCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_ApeCliKeyTyped(evt);
            }
        });

        jTextField_NomCli.setEnabled(false);
        jTextField_NomCli.setNextFocusableComponent(jTextField_DirCli);
        jTextField_NomCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_NomCliKeyTyped(evt);
            }
        });

        jTextField_DirCli.setEnabled(false);
        jTextField_DirCli.setNextFocusableComponent(jTextField_TelCli);
        jTextField_DirCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_DirCliKeyTyped(evt);
            }
        });

        jTextField_TelCli.setEnabled(false);
        jTextField_TelCli.setNextFocusableComponent(jTextField_Email);
        jTextField_TelCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_TelCliKeyTyped(evt);
            }
        });

        jLabel6.setText("Buscar:");

        jTextField_Buscar.setEnabled(false);
        jTextField_Buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_BuscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_BuscarKeyTyped(evt);
            }
        });

        jLabel7.setText("E_mail:");

        jTextField_Email.setNextFocusableComponent(jButton_Guardar);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_CedCli, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_ApeCli, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_NomCli, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_DirCli, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_TelCli, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField_CedCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField_ApeCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField_NomCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField_DirCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField_TelCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton_Nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        jButton_Nuevo.setText("Nuevo");
        jButton_Nuevo.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
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
        jButton_Guardar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jButton_GuardarKeyTyped(evt);
            }
        });

        jButton_Actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
        jButton_Actualizar.setText("Actualizar");
        jButton_Actualizar.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
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
        jButton_Volver.setNextFocusableComponent(jTextField_CedCli);
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
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_Nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Actualizar)
                    .addComponent(jButton_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Borrar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Volver, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jButton_Nuevo)
                .addGap(18, 18, 18)
                .addComponent(jButton_Guardar)
                .addGap(18, 18, 18)
                .addComponent(jButton_Actualizar)
                .addGap(18, 18, 18)
                .addComponent(jButton_Cancelar)
                .addGap(18, 18, 18)
                .addComponent(jButton_Borrar)
                .addGap(33, 33, 33)
                .addComponent(jButton_Volver)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable_DatosClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable_DatosClientes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable_DatosClientes.setDragEnabled(true);
        jTable_DatosClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_DatosClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_DatosClientes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void establecerTamañoColumnas() {
        modeloColumna = jTable_DatosClientes.getColumnModel();
        modeloColumna.getColumn(0).setPreferredWidth(80);
        modeloColumna.getColumn(1).setPreferredWidth(110);
        modeloColumna.getColumn(2).setPreferredWidth(110);
        modeloColumna.getColumn(3).setPreferredWidth(120);
        modeloColumna.getColumn(4).setPreferredWidth(80);
        modeloColumna.getColumn(5).setPreferredWidth(150);

    }

    public void cargarDatosClientes(String Dato) {

        String[] titulos = {"CEDULA", "APELLIDO", "NOMBRE", "DIRECCION", "TELEFONO", "E-MAIL"};
        String[] registros = new String[6];
        jTable_DatosClientes.getTableHeader().setReorderingAllowed(false);
        jTable_DatosClientes.getTableHeader().setResizingAllowed(false);
        model = new DefaultTableModel(null, titulos) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ConexionTienda cc = new ConexionTienda();
        Connection cn = cc.conectar();
        String sql = "";
        if (paraBuscar) {
            sql = "select * from clientes where CED_CLI LIKE '" + Dato + "%' order by ape_cli";
        } else {
            sql = "select * from clientes where APE_CLI LIKE '" + Dato + "%' order by ape_cli";
        }
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString("CED_CLI");
                registros[1] = rs.getString("APE_CLI");
                registros[2] = rs.getString("NOM_CLI");
                registros[3] = rs.getString("DIR_CLI");
                registros[4] = rs.getString("TEL_CLI");
                registros[5] = rs.getString("EMAIL_CLI");
                model.addRow(registros);

            }
            jTable_DatosClientes.setModel(model);
            establecerTamañoColumnas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (Exception ex1) {
        }

    }

    public void guardar() {


        if (jTextField_CedCli.getText().isEmpty() || !Metodos.verificadorCédula(jTextField_CedCli.getText())) {
            JOptionPane.showMessageDialog(null, "Cédula Errónea");
            jTextField_CedCli.requestFocus(); // Para posicionar el raton
        } else if (jTextField_ApeCli.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el apellido");
            jTextField_ApeCli.requestFocus();
        } else if (jTextField_NomCli.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el nombre");
            jTextField_NomCli.requestFocus();
        } else if (jTextField_DirCli.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la dirección");
            jTextField_DirCli.requestFocus();
        } else {

            ConexionTienda cc = new ConexionTienda();
            Connection cn = cc.conectar();
            String CED_CLI, APE_CLI, NOM_CLI, DIR_CLI, TEL_CLI, EMAIL_CLI;
            CED_CLI = jTextField_CedCli.getText().trim();
            APE_CLI = jTextField_ApeCli.getText().trim().toUpperCase();
            NOM_CLI = jTextField_NomCli.getText().trim().toUpperCase();
            DIR_CLI = jTextField_DirCli.getText().trim().toUpperCase();
            TEL_CLI = jTextField_TelCli.getText().trim();
            EMAIL_CLI = jTextField_Email.getText().trim().toLowerCase();
            String sql = "";
            sql = "insert into clientes(CED_CLI, APE_CLI, NOM_CLI, DIR_CLI, TEL_CLI, EMAIL_CLI)"
                    + "values(?,?,?,?,?,?)";
            try {
                PreparedStatement psd = cn.prepareStatement(sql);
                psd.setString(1, CED_CLI); //(Numero de campo/ nombre)
                psd.setString(2, APE_CLI);
                psd.setString(3, NOM_CLI);
                psd.setString(4, DIR_CLI);
                psd.setString(5, TEL_CLI);
                psd.setString(6, EMAIL_CLI);
                int n = psd.executeUpdate();

                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Se insertó la información correctamente");
                    cargarDatosClientes(""); //Actualizar la carga de datos
                    txtLimpiar();
                    txtBloqueo(false);
                    botonesInicio();

                }

            } catch (SQLException ex) { //permite manejar la excepcion de la base de datos
                JOptionPane.showMessageDialog(null, ex);
            } catch (Exception ex) {
            }
        }


    }

    public void actualizar() {
        if (jTextField_ApeCli.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el apellido");
            jTextField_ApeCli.requestFocus();
        } else if (jTextField_NomCli.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el nombre");
            jTextField_NomCli.requestFocus();
        } else if (jTextField_DirCli.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la dirección");
            jTextField_DirCli.requestFocus();
        } else {
            ConexionTienda cc = new ConexionTienda();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "UPDATE clientes SET APE_CLI='" + jTextField_ApeCli.getText().toUpperCase() + "'"
                    + ",NOM_CLI='" + jTextField_NomCli.getText().toUpperCase() + "'"
                    + ",DIR_CLI='" + jTextField_DirCli.getText().toUpperCase() + "'"
                    + ",TEL_CLI='" + jTextField_TelCli.getText() + "'"
                    + ",EMAIL_CLI='" + jTextField_Email.getText().toLowerCase() + "'"
                    + " WHERE CED_CLI=" + jTextField_CedCli.getText();
            try {
                PreparedStatement psd = cn.prepareStatement(sql);
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Se actualizo el registro correctamente ");
                }
                cargarDatosClientes("");
                txtLimpiar();
                txtBloqueo(false);
                botonesInicio();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar los datos. Intentelo nuevamente");
            }
        }
    }

    public void borrar() {
        int n = JOptionPane.showConfirmDialog(null, "Desea eliminar el registro ", "Borrar", JOptionPane.YES_NO_OPTION);

        if (n == 0) {
            ConexionTienda cc = new ConexionTienda();
            Connection cn = cc.conectar();
            String sql = "";
            String sql1 = "select * from usuarios where cod_usu='" + FramePrincipal.cedUsuario + "'";
            boolean esAdmin = false;
            sql = "DELETE FROM CLIENTES WHERE CED_CLI='" + jTextField_CedCli.getText() + "'";
            //sql = "UPDATE AUTO SET AUT_ESTADO='0' WHERE AUT_PLACA='" + txtPlaca.getText() + "'";
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql1);
                while (rs.next()) {
                    if (rs.getString(4).equals("Administrador")) {
                        esAdmin = true;
                        break;
                    } else {
                        esAdmin = false;
                    }
                }
                if (esAdmin) {
                    PreparedStatement psd = cn.prepareStatement(sql);
                    int m = psd.executeUpdate();
                    if (m > 0) {
                        JOptionPane.showMessageDialog(null, "Se borró el registro correctamente");
                        cargarDatosClientes("");
                        txtBloqueo(false);
                        txtLimpiar();
                        botonesInicio();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No cuenta con permisos necesarios para borrar");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar los datos. Intentelo nuevamente");
            }
        } else {
//            cargarDatosClientes("");
//            txtBloqueo(false);
//            txtLimpiar();
//            botonesInicio();
        }
    }

    private void jTable_DatosClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_DatosClientesMouseClicked
        mostrarDatosSeleccionaTabla();
    }//GEN-LAST:event_jTable_DatosClientesMouseClicked

    private void jTextField_ApeCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_ApeCliKeyTyped
        // TODO add your handling code here:
        Metodos.validarLetras(evt, jTextField_ApeCli);
    }//GEN-LAST:event_jTextField_ApeCliKeyTyped

    private void jTextField_NomCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_NomCliKeyTyped
        // TODO add your handling code here:
        Metodos.validarLetras(evt, jTextField_NomCli);
    }//GEN-LAST:event_jTextField_NomCliKeyTyped

    private void jTextField_DirCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_DirCliKeyTyped
        // TODO add your handling code here:

        if (jTextField_DirCli.getText().length() >= 15) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField_DirCliKeyTyped

    private void jTextField_TelCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_TelCliKeyTyped
        // TODO add your handling code here:
        Metodos.validarTelefono(evt, jTextField_TelCli);
    }//GEN-LAST:event_jTextField_TelCliKeyTyped
    int cont = 0;
    boolean paraBuscar;
    private void jTextField_BuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_BuscarKeyReleased
        ValidarIngresoBusqueda(evt, jTextField_Buscar);
    }//GEN-LAST:event_jTextField_BuscarKeyReleased

    private void jButton_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarActionPerformed
        // TODO add your handling code here:
        guardar();
    }//GEN-LAST:event_jButton_GuardarActionPerformed

    private void jButton_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NuevoActionPerformed
        // TODO add your handling code here:
        botonesNuevo();
        txtBloqueo(true);
    }//GEN-LAST:event_jButton_NuevoActionPerformed

    private void jButton_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CancelarActionPerformed
        // TODO add your handling code here:
        txtLimpiar();
        txtBloqueo(false);
        botonesInicio();
    }//GEN-LAST:event_jButton_CancelarActionPerformed

    private void jButton_VolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_VolverActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton_VolverActionPerformed

    private void jTextField_BuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_BuscarKeyTyped
        validarSoloLetrasSoloNumerosBuscar(evt);
    }//GEN-LAST:event_jTextField_BuscarKeyTyped

    private void jTextField_CedCliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_CedCliKeyTyped
        // TODO add your handling code here:
        Metodos.validarTelefono(evt, jTextField_CedCli);
    }//GEN-LAST:event_jTextField_CedCliKeyTyped

    private void jButton_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ActualizarActionPerformed
        // TODO add your handling code here:
        actualizar();
    }//GEN-LAST:event_jButton_ActualizarActionPerformed

    private void jButton_BorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_BorrarActionPerformed
        // TODO add your handling code here:
        borrar();
    }//GEN-LAST:event_jButton_BorrarActionPerformed

    private void jTextField_CedCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_CedCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_CedCliActionPerformed

    private void jButton_GuardarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton_GuardarKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_GuardarKeyTyped

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
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                Clientes dialog = new Clientes(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_DatosClientes;
    private javax.swing.JTextField jTextField_ApeCli;
    private javax.swing.JTextField jTextField_Buscar;
    private javax.swing.JTextField jTextField_CedCli;
    private javax.swing.JTextField jTextField_DirCli;
    private javax.swing.JTextField jTextField_Email;
    private javax.swing.JTextField jTextField_NomCli;
    private javax.swing.JTextField jTextField_TelCli;
    // End of variables declaration//GEN-END:variables
}
