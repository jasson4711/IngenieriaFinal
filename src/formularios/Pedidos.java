/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author PC
 */
public class Pedidos extends javax.swing.JFrame {

    /**
     * Creates new form Pedidos
     */
    Connection cn = null;
    ConexionTienda cc = new ConexionTienda();
    int codCab;
    DefaultTableModel modeloTabla;
    TableColumnModel modeloColumna;
    JScrollPane scroll;
    

    public Pedidos() {
        initComponents();
        this.setLocationRelativeTo(null);
        modeloTablaCarrito();
    }
    public void modeloTablaCarrito() {
        String[] titulos = {"CÓDIGO", "NOMBRE", "TALLA", "CANTIDAD", "V/U", "V/T"};
        jTable_CarritoCompra.getTableHeader().setReorderingAllowed(false);
        jTable_CarritoCompra.getTableHeader().setResizingAllowed(false);
        modeloTabla = new DefaultTableModel(null, titulos) {
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable_CarritoCompra.setModel(modeloTabla);
        
    }

    public ArrayList<Producto> obtenerProductos() {
        Producto p;
        ArrayList<Producto> productos = new ArrayList();
        for (int i = 0; i < jTable_CarritoCompra.getRowCount(); i++) {
            p = new Producto(jTable_CarritoCompra.getValueAt(i, 0).toString(), Integer.valueOf(jTable_CarritoCompra.getValueAt(i, 3).toString()));
            productos.add(p);
        }
        return productos;
    }

    public void agregarArticulo() {
        String codigo;
        int cantidad = 0;
        InventarioVendedor sel = new InventarioVendedor(null, true, obtenerProductos());
        sel.setVisible(true);
        boolean existe = false;
        if (!sel.isShowing() && sel.vale) {
            codigo = sel.enviarCodigo();
            cantidad = sel.obtenerCantidad();
            int numFilas = jTable_CarritoCompra.getRowCount();
            for (int i = 0; i < numFilas; i++) {
                if (codigo.equals(jTable_CarritoCompra.getValueAt(i, 0).toString())) {
                    cantidad += Integer.valueOf(jTable_CarritoCompra.getValueAt(i, 3).toString());
                    jTable_CarritoCompra.setValueAt(cantidad, i, 3);
                    existe = true;
                }
            }
            if (!existe) {
                String[] registros = new String[4];
                cn = cc.conectar();
                String sql = "SELECT p.id_pro, p.tip_pro, t.des_tal"
                        + "FROM productos p, tallas t "
                        + "WHERE p.id_pro = '" + codigo + "' "
                        + "AND p.id_tal_per= t.id_tal";
                try {
                    Statement st = cn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        registros[0] = rs.getString(1);
                        registros[1] = rs.getString(2);
                        registros[2] = rs.getString(3);
                        registros[3] = String.valueOf(cantidad);
                    }
                    modeloTabla.addRow(registros);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Ocurrió un error al obtener datos de la base:\n" + ex);
                    try {
                        cn.close();
                    } catch (SQLException ex1) {
                        JOptionPane.showMessageDialog(null, "Error de conexión");
                    }
                } catch (Exception ex) {
                }
            }
            if (jTable_CarritoCompra.getRowCount() > 0) {
                jButton_Eliminar.setEnabled(true);
                jTextField_NumElim.setEnabled(true);
                jButtonFacturar.setEnabled(true);
            }
        }
    }
    public void cargarCliente() {
        if (jTextField_Cod_Prov.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese código");
            jTextField_Cod_Prov.requestFocus();
        } else {
            String sql = "select * from proveedores where cod_pro='" + jTextField_Cod_Prov.getText() + "'";
            boolean clienteExiste = false;
//        String sql = "select * from clientes where ced_cli='172438393'";
            cn = cc.conectar();
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    jTextField_Nom_Prov.setText(rs.getString("NOM_PRO"));
                    jTextField_Dir_Prov.setText(rs.getString("DIR_PRO"));
                    jTextField_Tel_Prov.setText(rs.getString("TEL_PRO"));
                    jTextField_Cod_Prov.setEditable(false);
                    jButton_Cargar.setEnabled(false);
                    clienteExiste = true;
                    jButton_Seleccionar.setEnabled(true);
                }
                cn.close();
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error al obtener datos de la base:\n" + ex);
                try {
                    cn.close();
                } catch (SQLException ex1) {
                    JOptionPane.showMessageDialog(null, "Error de conexión");
                }
            } catch (Exception ex) {
            }
            if (!clienteExiste) {
                
            } else {
                
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

        jPanel5 = new javax.swing.JPanel();
        jLabel_Fecha = new javax.swing.JLabel();
        jPanel_Fondo = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField_Cod_Prov = new javax.swing.JTextField();
        jTextField_Nom_Prov = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField_Tel_Prov = new javax.swing.JTextField();
        jTextField_Dir_Prov = new javax.swing.JTextField();
        jButton_Cargar = new javax.swing.JButton();
        jButton_Guardar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jButton_Eliminar = new javax.swing.JButton();
        jButton_Seleccionar = new javax.swing.JButton();
        jTextField_NumElim = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_CarritoCompra = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jButtonFacturar = new javax.swing.JButton();
        jButton_Cancelar = new javax.swing.JButton();
        jTextField_SubTotal = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField_Iva = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField_Descuento = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField_Total = new javax.swing.JTextField();
        jSpinner_Descuento = new javax.swing.JSpinner();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jTextField_Ced_Enc = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField_Nom_Enc = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setAutoscrolls(true);

        jLabel_Fecha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_Fecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel_Fecha.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel_Fondo.setBorder(javax.swing.BorderFactory.createTitledBorder("PEDIDOS "));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("DATOS DEL PROVEEDOR"));

        jLabel1.setText("Código:");

        jTextField_Cod_Prov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_Cod_ProvKeyTyped(evt);
            }
        });

        jTextField_Nom_Prov.setEditable(false);
        jTextField_Nom_Prov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_Nom_ProvKeyTyped(evt);
            }
        });

        jLabel2.setText("Nombre:");

        jLabel3.setText("Ciudad:");

        jLabel4.setText("Teléfono:");

        jTextField_Tel_Prov.setEditable(false);
        jTextField_Tel_Prov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_Tel_ProvKeyTyped(evt);
            }
        });

        jTextField_Dir_Prov.setEditable(false);
        jTextField_Dir_Prov.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_Dir_ProvKeyTyped(evt);
            }
        });

        jButton_Cargar.setText("Cargar");
        jButton_Cargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CargarActionPerformed(evt);
            }
        });

        jButton_Guardar.setText("Guardar");
        jButton_Guardar.setEnabled(false);
        jButton_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField_Cod_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_Cargar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField_Nom_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField_Dir_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_Guardar, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(jTextField_Tel_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(126, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField_Cod_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Cargar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField_Nom_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField_Tel_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField_Dir_Prov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Guardar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("ARTÍCULO"));

        jLabel6.setText("Seleccione:");

        jButton_Eliminar.setText("Eliminar");
        jButton_Eliminar.setEnabled(false);
        jButton_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EliminarActionPerformed(evt);
            }
        });

        jButton_Seleccionar.setText("...");
        jButton_Seleccionar.setEnabled(false);
        jButton_Seleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SeleccionarActionPerformed(evt);
            }
        });

        jTextField_NumElim.setToolTipText("Si se deja vacío se vaciará todas las existencias del producto");
        jTextField_NumElim.setEnabled(false);
        jTextField_NumElim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_NumElimKeyTyped(evt);
            }
        });

        jLabel7.setText("Cantidad:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jButton_Seleccionar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jTextField_NumElim, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton_Eliminar)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jButton_Seleccionar)
                    .addComponent(jButton_Eliminar)
                    .addComponent(jTextField_NumElim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("CARRITO DE PEDIDOS"));

        jTable_CarritoCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Descripción", "Cantidad", "V/U", "V/T"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable_CarritoCompra);

        jLabel10.setText("Subtotal:");

        jButtonFacturar.setText("Facturar");
        jButtonFacturar.setEnabled(false);
        jButtonFacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFacturarActionPerformed(evt);
            }
        });

        jButton_Cancelar.setText("Cancelar");
        jButton_Cancelar.setEnabled(false);
        jButton_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CancelarActionPerformed(evt);
            }
        });

        jTextField_SubTotal.setEditable(false);
        jTextField_SubTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel9.setText("Iva:");

        jTextField_Iva.setEditable(false);
        jTextField_Iva.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel12.setText("Descuento:");

        jTextField_Descuento.setEditable(false);
        jTextField_Descuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel14.setText("Total:");

        jTextField_Total.setEditable(false);
        jTextField_Total.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jSpinner_Descuento.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));
        jSpinner_Descuento.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner_DescuentoStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jButtonFacturar)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_Cancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField_SubTotal)
                            .addComponent(jTextField_Total)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jSpinner_Descuento, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_Descuento, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField_Iva))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField_SubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField_Iva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField_Descuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner_Descuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 2, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonFacturar)
                            .addComponent(jButton_Cancelar)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jTextField_Total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("DATOS DEL ENCARGADO"));

        jLabel8.setText("Cedula:");

        jTextField_Ced_Enc.setEditable(false);

        jLabel11.setText("Nombres:");

        jTextField_Nom_Enc.setEditable(false);
        jTextField_Nom_Enc.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jTextField_Ced_Enc, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_Nom_Enc, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11)
                    .addComponent(jTextField_Ced_Enc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_Nom_Enc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel_FondoLayout = new javax.swing.GroupLayout(jPanel_Fondo);
        jPanel_Fondo.setLayout(jPanel_FondoLayout);
        jPanel_FondoLayout.setHorizontalGroup(
            jPanel_FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_FondoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_FondoLayout.setVerticalGroup(
            jPanel_FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_FondoLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(65, 65, 65))
        );

        jLabel13.setText("Fecha:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_Fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addComponent(jPanel_Fondo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_Fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel_Fondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel_Fondo.getAccessibleContext().setAccessibleName("PEDIDOS");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 697, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 707, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_Cod_ProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_Cod_ProvKeyTyped
        // TODO add your handling code here:
//        deshabilitarCancelar();
        //Metodos.validarTelefono(evt, jTextField_Cod_Prov);
    }//GEN-LAST:event_jTextField_Cod_ProvKeyTyped

    private void jTextField_Nom_ProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_Nom_ProvKeyTyped
        // TODO add your handling code here:
        Metodos.validarLetras(evt, jTextField_Nom_Prov);
    }//GEN-LAST:event_jTextField_Nom_ProvKeyTyped

    private void jTextField_Tel_ProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_Tel_ProvKeyTyped
        // TODO add your handling code here:
        Metodos.validarTelefono(evt, jTextField_Tel_Prov);
    }//GEN-LAST:event_jTextField_Tel_ProvKeyTyped

    private void jTextField_Dir_ProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_Dir_ProvKeyTyped
        // TODO add your handling code here:
        if (jTextField_Dir_Prov.getText().length() >= 15) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField_Dir_ProvKeyTyped

    private void jButton_CargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CargarActionPerformed
        cargarCliente();
    }//GEN-LAST:event_jButton_CargarActionPerformed

    private void jButton_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarActionPerformed
        // TODO add your handling code here:
        //guardarCliente();
    }//GEN-LAST:event_jButton_GuardarActionPerformed

    private void jButton_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EliminarActionPerformed
//        eliminarDeCarritoDeCompra();
//        eliminarProducto();
//        if (jTable_CarritoCompra.getRowCount() <= 0) {
//            jButton_Eliminar.setEnabled(false);
//            jTextField_NumElim.setEnabled(false);
//            jButtonFacturar.setEnabled(false);
//        }
//        calcularValorFactura();
    }//GEN-LAST:event_jButton_EliminarActionPerformed

    private void jButton_SeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SeleccionarActionPerformed
        // TODO add your handling code here:
        agregarArticulo();
    }//GEN-LAST:event_jButton_SeleccionarActionPerformed

    private void jTextField_NumElimKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_NumElimKeyTyped
        // TODO add your handling code here:
        Metodos.validarCamposSoloNumeros(evt, jTextField_NumElim, 2);
    }//GEN-LAST:event_jTextField_NumElimKeyTyped

    private void jButtonFacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFacturarActionPerformed
//        int preg = JOptionPane.showConfirmDialog(this, "Realizar Venta?...", "Facturando...", JOptionPane.YES_OPTION);
//        if (preg == 0) {
//            facturar();
//        }
    }//GEN-LAST:event_jButtonFacturarActionPerformed

    private void jButton_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CancelarActionPerformed
//        confirmarCancelarVenta();
//        int confirm = JOptionPane.showConfirmDialog(null, "¿Desea cancelar?", "Cancelar venta", JOptionPane.YES_OPTION);
//        if (confirm == 0) {
//            this.dispose();
//        }
    }//GEN-LAST:event_jButton_CancelarActionPerformed

    private void jSpinner_DescuentoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner_DescuentoStateChanged
        // TODO add your handling code here:
//        calcularValorFactura();
    }//GEN-LAST:event_jSpinner_DescuentoStateChanged

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
            java.util.logging.Logger.getLogger(Pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Pedidos().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonFacturar;
    private javax.swing.JButton jButton_Cancelar;
    private javax.swing.JButton jButton_Cargar;
    private javax.swing.JButton jButton_Eliminar;
    private javax.swing.JButton jButton_Guardar;
    private javax.swing.JButton jButton_Seleccionar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_Fecha;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel_Fondo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner_Descuento;
    private javax.swing.JTable jTable_CarritoCompra;
    private javax.swing.JTextField jTextField_Ced_Enc;
    private javax.swing.JTextField jTextField_Cod_Prov;
    private javax.swing.JTextField jTextField_Descuento;
    private javax.swing.JTextField jTextField_Dir_Prov;
    private javax.swing.JTextField jTextField_Iva;
    private javax.swing.JTextField jTextField_Nom_Enc;
    private javax.swing.JTextField jTextField_Nom_Prov;
    private javax.swing.JTextField jTextField_NumElim;
    private javax.swing.JTextField jTextField_SubTotal;
    private javax.swing.JTextField jTextField_Tel_Prov;
    private javax.swing.JTextField jTextField_Total;
    // End of variables declaration//GEN-END:variables
}
