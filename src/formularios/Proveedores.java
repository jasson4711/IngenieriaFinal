/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author PC
 */
public class Proveedores extends javax.swing.JDialog {

    DefaultTableModel model;
    TableColumnModel modeloColumna;

    /**
     * Creates new form Proveedores
     */
    public Proveedores(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        cargarDatosProveedor("");

        botonesInicio();
        txtBloqueo(false);
        mostrarDatosSeleccionaTabla();
        jTextField_Bus_Pro.setEnabled(true);
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


        cargarDatosProveedor(componente.getText());
    }

    public void cargarDatosProveedor(String Dato) {

        String[] titulos = {"CÓDIGO", "NOMBRE", "CIUDAD", "TELEFONO"};
        String[] registros = new String[4];
        jTable_Proveedores.getTableHeader().setReorderingAllowed(false);
        jTable_Proveedores.getTableHeader().setResizingAllowed(false);
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
            sql = "select * from proveedores where COD_PRO LIKE '" + Dato + "%' order by nom_pro";
        } else {
            sql = "select * from proveedores where NOM_PRO LIKE '" + Dato + "%' order by nom_pro";
        }
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString("COD_PRO");
                registros[1] = rs.getString("NOM_PRO");
                registros[2] = rs.getString("DIR_PRO");
                registros[3] = rs.getString("TEL_PRO");
                model.addRow(registros);

            }
            jTable_Proveedores.setModel(model);
            establecerTamañoColumnas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (Exception ex1) {
        }

    }

    public void mostrarDatosSeleccionaTabla() {
        jTable_Proveedores.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jTable_Proveedores.getSelectedRow() != -1) {
                    int fila = jTable_Proveedores.getSelectedRow();

                    jTextField_Cod_Pro.setText(jTable_Proveedores.getValueAt(fila, 0).toString().trim());
                    jTextField_Nom_Prov.setText(jTable_Proveedores.getValueAt(fila, 1).toString().trim());
                    jTextField_Dir_Pro.setText(jTable_Proveedores.getValueAt(fila, 2).toString().trim());
                    jTextField_Tel_Pro.setText(jTable_Proveedores.getValueAt(fila, 3).toString().trim());
                    txtBloqueo(true);
                    jTextField_Cod_Pro.setEnabled(false);
                    botonesBorrar();
                    botonesActualizar();
                    jButton1.setEnabled(true);
                }
            }
        });
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

    public void establecerTamañoColumnas() {
        modeloColumna = jTable_Proveedores.getColumnModel();
        modeloColumna.getColumn(0).setPreferredWidth(80);
        modeloColumna.getColumn(1).setPreferredWidth(110);
        modeloColumna.getColumn(2).setPreferredWidth(110);
        modeloColumna.getColumn(3).setPreferredWidth(80);
    }

    public void botonesInicio() {
        jButton_Actualizar.setEnabled(false);
        jButton_Cancelar.setEnabled(false);
        jButton_Guardar.setEnabled(false);
        jButton_Nuevo.setEnabled(true);
        jButton_Borrar.setEnabled(false);
    }

    public void txtBloqueo(boolean tutia) {
        jTextField_Cod_Pro.requestFocus();
        jTextField_Cod_Pro.setEnabled(tutia);
        jTextField_Nom_Prov.setEnabled(tutia);
        jTextField_Dir_Pro.setEnabled(tutia);
        jTextField_Tel_Pro.setEnabled(tutia);
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

    public void txtLimpiar() {
        jTextField_Cod_Pro.setText("");
        jTextField_Nom_Prov.setText("");
        jTextField_Dir_Pro.setText("");
        jTextField_Tel_Pro.setText("");
        jTextField_Bus_Pro.setText("");
        txtBloqueo(false);

    }

    public void botonesNuevo() {
        jButton_Actualizar.setEnabled(false);
        jButton_Cancelar.setEnabled(true);
        jButton_Guardar.setEnabled(true);
        jButton_Nuevo.setEnabled(false);
        jButton_Borrar.setEnabled(false);
    }

    private void jTextField_Dir_ProKeyTyped(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
        if (jTextField_Dir_Pro.getText().length() >= 20) {
            evt.consume();
        }
    }

    public void guardar() {
        if (jTextField_Cod_Pro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el Código del Proveedor");
            jTextField_Cod_Pro.requestFocus(); // Para posicionar el raton
        } else if (jTextField_Nom_Prov.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el nombre del Proveedor");
            jTextField_Nom_Prov.requestFocus();
        } else if (jTextField_Dir_Pro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la dirección");
            jTextField_Dir_Pro.requestFocus();
        } else if (jTextField_Tel_Pro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el teléfono");
            jTextField_Tel_Pro.requestFocus();
        } else {
            ConexionTienda cc = new ConexionTienda();
            Connection cn = cc.conectar();
            String COD_PRO, NOM_PRO, DIR_PRO, TEL_PRO;
            COD_PRO = jTextField_Cod_Pro.getText().trim().toUpperCase();
            NOM_PRO = jTextField_Nom_Prov.getText().trim().toUpperCase();
            DIR_PRO = jTextField_Dir_Pro.getText().trim().toUpperCase();
            TEL_PRO = jTextField_Tel_Pro.getText().trim();
            String sql = "";
            sql = "insert into proveedores(COD_PRO, NOM_PRO, DIR_PRO, TEL_PRO)"
                    + "values(?,?,?,?)";
            try {
                PreparedStatement psd = cn.prepareStatement(sql);
                psd.setString(1, COD_PRO); //(Numero de campo/ nombre)
                psd.setString(2, NOM_PRO);
                psd.setString(3, DIR_PRO);
                psd.setString(4, TEL_PRO);
                int n = psd.executeUpdate();

                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Se insertó la información correctamente");
                    cargarDatosProveedor(""); //Actualizar la carga de datos
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
        if (jTextField_Nom_Prov.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el nombre");
            jTextField_Nom_Prov.requestFocus();
        } else if (jTextField_Dir_Pro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar la dirección");
            jTextField_Dir_Pro.requestFocus();
        } else if (jTextField_Tel_Pro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el teléfono");
            jTextField_Tel_Pro.requestFocus();
        } else {
            ConexionTienda cc = new ConexionTienda();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "update proveedores set NOM_PRO='" + jTextField_Nom_Prov.getText().trim().toUpperCase() + "' "
                    + ",DIR_PRO='" + jTextField_Dir_Pro.getText().trim().toUpperCase() + "' "
                    + ",TEL_PRO='" + jTextField_Tel_Pro.getText() + "' "
                    + "where COD_PRO='" + jTextField_Cod_Pro.getText().trim().toUpperCase() + "'";
            try {
                PreparedStatement psd = cn.prepareStatement(sql);
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Se actualizó el registro correctamente");
                    cargarDatosProveedor("");
                    txtLimpiar();
                    botonesInicio();
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
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
            sql = "DELETE FROM PROVEEDORES WHERE COD_PRO='" + jTextField_Cod_Pro.getText() + "'";
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
                        cargarDatosProveedor("");
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Proveedores = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField_Cod_Pro = new javax.swing.JTextField();
        jTextField_Nom_Prov = new javax.swing.JTextField();
        jTextField_Tel_Pro = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField_Bus_Pro = new javax.swing.JTextField();
        jTextField_Dir_Pro = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton_Nuevo = new javax.swing.JButton();
        jButton_Guardar = new javax.swing.JButton();
        jButton_Actualizar = new javax.swing.JButton();
        jButton_Cancelar = new javax.swing.JButton();
        jButton_Borrar = new javax.swing.JButton();
        jButton_Volver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jTable_Proveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable_Proveedores);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Código:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Ciudad:");

        jLabel4.setText("Teléfono:");

        jTextField_Tel_Pro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_Tel_ProKeyTyped(evt);
            }
        });

        jLabel5.setText("Buscar:");

        jTextField_Bus_Pro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_Bus_ProKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_Bus_ProKeyTyped(evt);
            }
        });

        jButton1.setText("Hacer pedido");
        jButton1.setEnabled(false);
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
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField_Tel_Pro, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField_Cod_Pro, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1))
                            .addComponent(jTextField_Dir_Pro, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_Nom_Prov, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                            .addComponent(jTextField_Bus_Pro, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField_Cod_Pro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField_Nom_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField_Dir_Pro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField_Tel_Pro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField_Bus_Pro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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
        jButton_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarActionPerformed(evt);
            }
        });

        jButton_Actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
        jButton_Actualizar.setText("Actualizar");
        jButton_Actualizar.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jButton_Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ActualizarActionPerformed(evt);
            }
        });

        jButton_Cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cancelar.png"))); // NOI18N
        jButton_Cancelar.setText("Cancelar");
        jButton_Cancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        jButton_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CancelarActionPerformed(evt);
            }
        });

        jButton_Borrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/borrar.png"))); // NOI18N
        jButton_Borrar.setText("Borrar");
        jButton_Borrar.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
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
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_Nuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Actualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Guardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Cancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Borrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_Volver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton_Nuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_Guardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_Actualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_Borrar)
                .addGap(27, 27, 27)
                .addComponent(jButton_Volver)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_Tel_ProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_Tel_ProKeyTyped
        // TODO add your handling code here:
        Metodos.validarTelefono(evt, jTextField_Tel_Pro);
    }//GEN-LAST:event_jTextField_Tel_ProKeyTyped
    int cont = 0;
    boolean paraBuscar;
    private void jTextField_Bus_ProKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_Bus_ProKeyTyped
        // TODO add your handling code here:
        validarSoloLetrasSoloNumerosBuscar(evt);
    }//GEN-LAST:event_jTextField_Bus_ProKeyTyped

    private void jButton_NuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NuevoActionPerformed
        // TODO add your handling code here:
        botonesNuevo();
        txtBloqueo(true);
    }//GEN-LAST:event_jButton_NuevoActionPerformed

    private void jButton_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarActionPerformed
        // TODO add your handling code here:
        guardar();
    }//GEN-LAST:event_jButton_GuardarActionPerformed

    private void jButton_ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ActualizarActionPerformed
        // TODO add your handling code here:
        actualizar();
    }//GEN-LAST:event_jButton_ActualizarActionPerformed

    private void jButton_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CancelarActionPerformed
        // TODO add your handling code here:
        txtLimpiar();
        txtBloqueo(false);
        botonesInicio();
    }//GEN-LAST:event_jButton_CancelarActionPerformed

    private void jButton_BorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_BorrarActionPerformed
        // TODO add your handling code here:
        borrar();
    }//GEN-LAST:event_jButton_BorrarActionPerformed

    private void jButton_VolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_VolverActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton_VolverActionPerformed

    private void jTextField_Bus_ProKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_Bus_ProKeyReleased
        // TODO add your handling code here:
        ValidarIngresoBusqueda(evt, jTextField_Bus_Pro);
    }//GEN-LAST:event_jTextField_Bus_ProKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jTable_Proveedores.getSelectedRow()>-1) {
            new Pedidos(null,true,jTextField_Cod_Pro.getText()).setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null,"Seleccione un proveedor");
        }
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(Proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Proveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                Proveedores dialog = new Proveedores(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Proveedores;
    private javax.swing.JTextField jTextField_Bus_Pro;
    private javax.swing.JTextField jTextField_Cod_Pro;
    private javax.swing.JTextField jTextField_Dir_Pro;
    private javax.swing.JTextField jTextField_Nom_Prov;
    private javax.swing.JTextField jTextField_Tel_Pro;
    // End of variables declaration//GEN-END:variables
}
