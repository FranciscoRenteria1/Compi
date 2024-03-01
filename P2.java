import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class P2 extends JFrame implements ActionListener {
    JTextArea txtCodigo;
    JTextArea txtMensaje;
    JTextArea txtLineas;
    JFrame frame;
    private Lexico analizadorLexico;
    // private Lexico analizadorLexico;
    private P1 p1;
    private JButton btnNuevo;
    private JButton btnBuscar;
    private JButton btnGuardar;
    private JButton btnCompilar;
    // Lexico analizadorLexico = new Lexico();
    JLabel labEstatus;
    File archivo;
    String dire = "";
    String estadoLexico="",estadoSintactico = "";
    String estatus = "1) Lexico "+estadoLexico+" 2) Sintaxis "+estadoSintactico+".";

    public P2() {
        Lexico lexico = new Lexico();
        
        Border borde = new LineBorder(Color.BLACK, 1);

        frame = new JFrame("CompiladorONE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);

        txtCodigo = new JTextArea();
        txtCodigo.setBorder(borde);

        txtMensaje = new JTextArea();
        txtMensaje.setBorder(borde);

        txtLineas = new JTextArea();
        txtLineas.setBackground(Color.LIGHT_GRAY);
        // txtLineas.setBorder(borde);
        txtLineas.setEditable(false);

        JScrollPane scrollCodigo = new JScrollPane(txtCodigo);
        JScrollPane scrollLineas = new JScrollPane(txtLineas);
        JScrollPane scrollMensaje = new JScrollPane(txtMensaje);

        labEstatus = new JLabel();
        
        labEstatus.setText(estatus);

        btnNuevo = new JButton("Nuevo");
        btnNuevo.addActionListener(this);

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this);

        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(this);

        btnCompilar = new JButton("Compilar");
        btnCompilar.addActionListener(this);

        // Configura el layout del frame
        frame.setLayout(null);

        // Configura la posición y tamaño de los componentes
        scrollLineas.setBounds(18, 41, 30, 340);
        scrollLineas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollLineas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollLineas.setViewportView(txtLineas);
        scrollCodigo.setBounds(45, 41, 530, 340);
        scrollMensaje.setBounds(18, 400, 560, 340);
        scrollMensaje.setViewportView(txtMensaje);
        labEstatus.setBounds(12, 384, 500, 13);
        btnNuevo.setBounds(96, 12, 90, 23);
        btnBuscar.setBounds(182, 12, 90, 23);
        btnGuardar.setBounds(263, 12, 90, 23);
        btnCompilar.setBounds(349, 12, 90, 23);
        scrollCodigo.getVerticalScrollBar().addAdjustmentListener(e -> {
            JScrollBar scrollBarCodigo = (JScrollBar) e.getSource();
            JScrollBar scrollBarLineas = scrollLineas.getVerticalScrollBar();

            // Ajusta la posición de la barra de desplazamiento de txtLineas
            scrollBarLineas.setValue(scrollBarCodigo.getValue());
        });

        // Agrega los componentes al contenedor (por ejemplo, un panel)

        // Agrega los componentes al frame
        frame.add(scrollLineas);
        frame.add(scrollCodigo);
        frame.add(scrollMensaje);
        frame.add(labEstatus);
        frame.add(btnNuevo);
        frame.add(btnBuscar);
        frame.add(btnGuardar);
        frame.add(btnCompilar);

        crearLineas();

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNuevo) {
            txtCodigo.setText("");
            txtMensaje.setText("");
        }
        if (e.getSource() == btnBuscar) {
            JFileChooser fileChooser = new JFileChooser();
            int seleccion = fileChooser.showOpenDialog(this);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                if (archivo.getName().endsWith(".inc")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                        StringBuilder contenido = new StringBuilder();
                        String linea;
                        while ((linea = reader.readLine()) != null) {
                            contenido.append(linea).append("\n");
                        }
                        txtCodigo.setText(contenido.toString());
                        dire = archivo.getPath();
                        System.out.println(dire);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error al abrir el archivo.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Selecciona un archivo .inc válido.");
                }
            }
        }
        if (e.getSource() == btnGuardar) {
            JFileChooser fileChooser = new JFileChooser();
            int seleccion = fileChooser.showSaveDialog(this);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                String nombreArchivo = archivo.getAbsolutePath();
                if (!nombreArchivo.endsWith(".inc")) {
                    nombreArchivo += ".inc";
                    archivo = new File(nombreArchivo);
                }
                try (FileWriter writer = new FileWriter(archivo)) {
                    writer.write(txtCodigo.getText());
                    writer.close();
                    JOptionPane.showMessageDialog(this, "El archivo se ha guardado exitosamente.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al guardar el archivo.");
                }
            }
        }
        if (e.getSource() == btnCompilar) {
            
            Lexico analizadorLexico = new Lexico();
            analizadorLexico.archivo = dire;
            String NodoImprimido = ">>Iniciar lexico\n";
            txtMensaje.setText(NodoImprimido);
            if (analizadorLexico != null) {
                analizadorLexico.archivo = dire;
                analizadorLexico.Lexico(); // Asumiendo que lexico() es el método que quieres llamar
                NodoImprimido += analizadorLexico.ImprimirNodo();
                txtMensaje.setText(NodoImprimido);
            }
            if(analizadorLexico.errorEncontrado == true){
                estadoLexico = "X";
                estadoSintactico = "X";
                estatus = "1) Lexico "+estadoLexico+" 2) Sintaxis "+estadoSintactico+".";
                labEstatus.setText(estatus);
                NodoImprimido += "Error en el Análisis Léxico\n";
                txtMensaje.setText(NodoImprimido);
                
            }else{
                estadoLexico = "OK";
                estatus = "1) Lexico "+estadoLexico+" 2) Sintaxis "+estadoSintactico+".";
                labEstatus.setText(estatus);
                NodoImprimido += "Analisis lexico Terminado Correctamente!\n";
                txtMensaje.setText(NodoImprimido);
                System.out.println("iniciando sintactico");
                NodoImprimido += "Iniciando Sintactico\n";
                txtMensaje.setText(NodoImprimido);
                P1 sintactico = new P1(analizadorLexico.cabeza);
                if (sintactico != null) {
                    String[] lineas = txtCodigo.getText().split("\n");
                    sintactico.linea = lineas;
                    sintactico.cabeza = analizadorLexico.cabeza; 
                    NodoImprimido += sintactico.program();
                    txtMensaje.setText(NodoImprimido);
                    if(sintactico.error==true){
                        estadoSintactico = "X";
                        estatus = "1) Lexico "+estadoLexico+" 2) Sintaxis "+estadoSintactico+".";
                        labEstatus.setText(estatus);
                    }else{
                        estadoSintactico = "OK";    
                        estatus = "1) Lexico "+estadoLexico+" 2) Sintaxis "+estadoSintactico+".";
                        labEstatus.setText(estatus);
                    }
                }
            }
            
        }
        /*
         * if(p1 != null){
         * p1.Lexico();
         * }else {
         * // Manejar la situación donde analizadorLexico es null
         * System.out.println("Error: sintactico no está inicializado correctamente");
         * }
         */
        // if (analizadorLexico != null) {
        // analizadorLexico.archivo = dire;
        // analizadorLexico.lexico();
        // //NodoImprimido+=analizadorLexico.ImprimirNodo();
        // txtMensaje.setText(NodoImprimido);
        // NodoImprimido+="\nFin Lexico";
        // txtMensaje.setText(NodoImprimido);
        // }

    }

    private void crearLineas() {
        txtCodigo.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            private void updateLineNumbers() {
                SwingUtilities.invokeLater(() -> {
                    int lineCount = txtCodigo.getLineCount();
                    StringBuilder numbersText = new StringBuilder();
                    for (int i = 1; i <= lineCount; i++) {
                        if (i > 1) {
                            numbersText.append("\n");
                        }
                        numbersText.append(i);
                    }
                    txtLineas.setText(numbersText.toString());
                });
            }
        });
    }
}