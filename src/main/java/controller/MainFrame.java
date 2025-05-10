package controller;

import model.Estudiante;
import view.EstudianteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    private JTable tblData;
    private DefaultTableModel dtblmData;
    private EstudianteService service = new EstudianteService();

    private String[] clNames = {"Id","Nombres", "Edad", "País", "Correo", "Celular"};

    public MainFrame() {
        setTitle("MySQL Crud");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        addOptions();
        addTable();
    }

    void addOptions() {
        JPanel pnlOptions = new JPanel();
        pnlOptions.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Add +");
        btnAdd.addActionListener(_-> saveOrEdit(-1));
        pnlOptions.add(btnAdd);
        add(pnlOptions, BorderLayout.NORTH);
    }

    void addTable() {
        dtblmData = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        dtblmData.setColumnIdentifiers(clNames);
        updateData();
        tblData = new JTable(dtblmData);
        JScrollPane spData = new JScrollPane(tblData);
        add(spData, BorderLayout.CENTER);
        tblData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Doble clic para editar
                    int row = tblData.getSelectedRow();
                    if (row != -1) {
                        // Asumiendo que el ID es la primera columna (índice 0) y es un Integer
                        Object idObject = dtblmData.getValueAt(row, 0);
                        if (idObject instanceof Integer) {
                            int id = (Integer) idObject;
                            saveOrEdit(id);
                        } else {
                            System.err.println("Error: El ID no es un número: " + idObject);
                            // Manejar el error apropiadamente
                        }
                    }
                }
            }
        });
    }

    void saveOrEdit(int id) {
        GridLayout gl = new GridLayout(0,2);
        gl.setVgap(5);
        JPanel pnlForm = new JPanel(gl);
        pnlForm.setSize(200,100);
        JLabel lblNombre = new JLabel("Nombre");
        JTextField txtNombre = new JTextField();
        JLabel lblApellidos = new JLabel("Apellidos");
        JTextField txtApellidos = new JTextField();
        JLabel lblEdad = new JLabel("Edad");
        JTextField txtEdad = new JTextField();
        JLabel lblPais = new JLabel("Pais");
        JTextField txtPais = new JTextField();
        JLabel lblCorreo = new JLabel("Correo");
        JTextField txtCorreo = new JTextField();
        JLabel lblCelular = new JLabel("Celular");
        JTextField txtCelular = new JTextField();

        JDialog dlgForm = new JDialog(this, (id > 0 ? "Editar" : "Agregar"), true);
        dlgForm.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dlgForm.setLayout(new BorderLayout());

        Estudiante editableData = null;
        if (id > 0) {
            editableData = service.listById(id);
            if (editableData != null) {
                String[] nombres = editableData.getNombre().split(", ");
                if (nombres.length == 2) {
                    txtApellidos.setText(nombres[0]);
                    txtNombre.setText(nombres[1]);
                } else {
                    txtNombre.setText(editableData.getNombre());
                }
                txtEdad.setText(editableData.getEdad());
                txtPais.setText(editableData.getPais());
                txtCorreo.setText(editableData.getCorreo());
                txtCelular.setText(editableData.getCelular());
            } else {
                JOptionPane.showMessageDialog(dlgForm, "No se encontró el estudiante con ID: "+id, "Error", JOptionPane.ERROR_MESSAGE);
                dlgForm.dispose();
                return;
            }
        }

        pnlForm.add(lblNombre);
        pnlForm.add(txtNombre);
        pnlForm.add(lblApellidos);
        pnlForm.add(txtApellidos);

        pnlForm.add(lblEdad);
        pnlForm.add(txtEdad);
        pnlForm.add(lblPais);
        pnlForm.add(txtPais);

        pnlForm.add(lblCorreo);
        pnlForm.add(txtCorreo);
        pnlForm.add(lblCelular);
        pnlForm.add(txtCelular);

        JPanel pnlOptions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Guardar");
        final int finalId = id;
        btnSave.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellidos.getText().trim();
            String edad = txtEdad.getText().trim();
            String pais = txtPais.getText().trim();
            String correo = txtCorreo.getText().trim();
            String celular = txtCelular.getText().trim();
            if (nombre.isEmpty() || apellido.isEmpty() || edad.isEmpty() || pais.isEmpty() || correo.isEmpty()) {
                JOptionPane.showMessageDialog(dlgForm, "Datos incompletos", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (finalId > 0) {
                service.update(finalId, nombre, apellido, edad, pais, correo, celular);
            } else {
                service.save(nombre, apellido, edad, pais, correo, celular);
            }

        });
        JButton btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(e -> dlgForm.dispose());

        pnlOptions.add(btnSave);
        pnlOptions.add(btnCancel);

        dlgForm.add(pnlForm, BorderLayout.CENTER);
        dlgForm.add(pnlOptions, BorderLayout.SOUTH);
        dlgForm.pack();
        dlgForm.setLocationRelativeTo(this);
        dlgForm.setVisible(true);
    }

    void updateData() {
        dtblmData.setRowCount(0);
        for (Estudiante e : service.list()) {
            dtblmData.addRow(new Object[]{e.getId(), e.getApellido()+", "+e.getNombre(), e.getEdad(), e.getPais(), e.getCorreo(), e.getCelular()});
        }
    }
}
