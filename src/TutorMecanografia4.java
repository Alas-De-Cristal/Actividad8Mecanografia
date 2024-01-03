import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TutorMecanografia4 extends JFrame {
    private JTextArea textArea;
    private JTextArea pangramArea;
    private JLabel pangramaSeleccionadoLabel;
    private JLabel pulsacionesCorrectasLabel;
    private JLabel pulsacionesIncorrectasLabel;
    private List<String> pangramas;
    private Random random;
    private int pulsacionesCorrectas;
    private int pulsacionesIncorrectas;
    private String pangramaActual;
    private int contadorEspaciosConsecutivos;

    public TutorMecanografia4() {
        super("Tutor de Mecanografía4");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicializar la lista de pangramas, el generador de números aleatorios y los contadores
        pangramas = new ArrayList<>();
        random = new Random();
        pulsacionesCorrectas = 0;
        pulsacionesIncorrectas = 0;
        pangramaActual = "";
        contadorEspaciosConsecutivos = 0;  // Inicializar el contador de espacios

        // Agregar pangramas al archivo y cargarlos a la lista
        agregarPangramas();
        cargarPangramas();

        // Crear el teclado virtual
        JPanel keyboardPanel = new JPanel(new GridLayout(4, 10));
        String[] buttonLabels = {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
                "Q", "W", "E", "R", "T", "Y", "U", "I", "O",
                "P", "A", "S", "D", "F", "G", "H", "J", "K", "L",
                "Ñ", "Z", "X", "C", "V", "B", "N", "M",  " "
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new KeyboardButtonListener());
            keyboardPanel.add(button);
        }

        // Crear el área de texto para la mecanografía
        textArea = new JTextArea();
        textArea.setEditable(true);

        // Crear el área para mostrar y editar los pangramas
        pangramArea = new JTextArea();
        pangramArea.setEditable(true);
        mostrarPangramas();

        // Crear el JLabel para mostrar el pangrama seleccionado
        pangramaSeleccionadoLabel = new JLabel();
        pangramaSeleccionadoLabel.setHorizontalAlignment(JLabel.CENTER);
        pangramaSeleccionadoLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Crear los JLabel para mostrar las pulsaciones correctas e incorrectas
        pulsacionesCorrectasLabel = new JLabel("Pulsaciones correctas: 0");
        pulsacionesIncorrectasLabel = new JLabel("Pulsaciones incorrectas: 0");

        // Diseño general de la GUI
        setLayout(new BorderLayout());
        add(keyboardPanel, BorderLayout.CENTER);
        add(new JScrollPane(textArea), BorderLayout.SOUTH);
        add(new JScrollPane(pangramArea), BorderLayout.NORTH);
        add(pangramaSeleccionadoLabel, BorderLayout.NORTH);
        add(pulsacionesCorrectasLabel, BorderLayout.WEST);
        add(pulsacionesIncorrectasLabel, BorderLayout.EAST);

        // Configuración de la ventana
        setSize(800, 400);
        setLocationRelativeTo(null);

        // Agregar un oyente para cerrar la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarPangramas();
                dispose();
            }
        });

        setVisible(true);

        // Seleccionar y mostrar un pangrama aleatorio al inicio
        cambiarPangramaAleatorio();

        // Agregar un DocumentListener al JTextArea para rastrear cambios
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarContadores();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarContadores();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // No es relevante para un JTextArea simple
            }
        });
    }

    private class KeyboardButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource();
            String buttonText = sourceButton.getText();

            if (buttonText.equals(" ")) {
                contadorEspaciosConsecutivos++;
            } else {
                contadorEspaciosConsecutivos = 0;
            }

            textArea.append(buttonText);
            sourceButton.setEnabled(false);

            // Cambiar el pangrama solo si hay más de 3 espacios consecutivos escritos
            if (contadorEspaciosConsecutivos > 3) {
                cambiarPangramaAleatorio();
            }
        }
    }

    private void agregarPangramas() {
        pangramas.add("El veloz murciélago hindú comía feliz cardillo y kiwi.");
        pangramas.add("La cigüeña tocaba el saxofón detrás del palenque de paja." );
        pangramas.add("El pingüino Wenceslao hizo kilómetros bajo exhaustiva lluvia y frío, añoraba a su querido cachorro.");
        pangramas.add("Jovencillo emponzoñado de whisky, qué mala figurota exhibes.");
        pangramas.add("Exhíbanse politiquillos zafios, con orejas kilométricas y uñas de gavilán.");
        pangramas.add( "Jovencillo emponzoñado de whisky: ¡qué figurota exhibe!");
        pangramas.add("¡Exijo hablar de un pequeño y fugaz armisticio en Kuwait! ¿Vale?");
        pangramas.add("Le gustaba cenar un exquisito sándwich de jamón con zumo de piña y vodka fría.");
        pangramas.add("El jefe buscó el éxtasis en un imprevisto baño de whisky y gozó como un duque.");
        pangramas.add("El viejo Señor Gómez pedía queso, kiwi y habas, pero le ha tocado un saxofón.");
        pangramas.add( "La cigüeña tocaba cada vez mejor el saxofón y el búho pedía kiwi y queso.");
        pangramas.add("El jefe que goza con un imprevisto busca el éxtasis en un baño de whisky.");
        pangramas.add("Borja quiso el extraño menú de gazpacho, kiwi, uva y flan.");
        pangramas.add("El extraño whisky quemó como fuego la boca del joven López.");
        pangramas.add("Ex-duque gozó con imprevisto baño de flojo whisky.");
        pangramas.add("Fidel exporta gazpacho, jamón, kiwi, viñas y buques.");
        pangramas.add("Joven emponzoñado con whisky, ¡qué mala figura exhibiste! (Modificado).");
        pangramas.add("La vieja cigüeña fóbica quiso empezar hoy un éxodo a Kuwait.");
        pangramas.add("Mi pequeño ex-jefe, loco gozaba vertiendo whisky.");
        pangramas.add("Mi jefe goza porque ve baño de excelente whisky.");
    }

    private void cargarPangramas() {
        // Implementación opcional para cargar pangramas desde un archivo
    }

    private void mostrarPangramas() {
        StringBuilder pangramasTexto = new StringBuilder();
        for (String pangrama : pangramas) {
            pangramasTexto.append(pangrama).append("\n");
        }
        pangramArea.setText(pangramasTexto.toString());
    }

    private void guardarPangramas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pangramas.txt", StandardCharsets.UTF_8))) {
            for (String pangrama : pangramas) {
                writer.write(pangrama);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores, puedes personalizar según tus necesidades
        }
    }

    private void cambiarPangramaAleatorio() {
        contadorEspaciosConsecutivos = 2;  // Reinicia el contador de espacios
        pangramaActual = pangramas.get(random.nextInt(pangramas.size()));
        pangramaSeleccionadoLabel.setText("Pangrama seleccionado: " + pangramaActual);
    }

    private void actualizarContadores() {
        String textoIngresado = textArea.getText();
        String pangramaSeleccionado = pangramaActual; // Usar el pangramaActual almacenado

        pulsacionesCorrectas = contarPulsacionesCorrectas(textoIngresado, pangramaSeleccionado);
        pulsacionesIncorrectas = contarPulsacionesIncorrectas(textoIngresado, pangramaSeleccionado);

        pulsacionesCorrectasLabel.setText("Pulsaciones correctas: " + pulsacionesCorrectas);
        pulsacionesIncorrectasLabel.setText("Pulsaciones incorrectas: " + pulsacionesIncorrectas);
    }

    private int contarPulsacionesCorrectas(String textoIngresado, String pangramaSeleccionado) {
        int minLen = Math.min(textoIngresado.length(), pangramaSeleccionado.length());
        int correctas = 0;

        for (int i = 0; i < minLen; i++) {
            if (textoIngresado.charAt(i) == pangramaSeleccionado.charAt(i)) {
                correctas++;
            }
        }

        return correctas;
    }

    private int contarPulsacionesIncorrectas(String textoIngresado, String pangramaSeleccionado) {
        int minLen = Math.min(textoIngresado.length(), pangramaSeleccionado.length());
        int incorrectas = 0;

        for (int i = 0; i < minLen; i++) {
            if (textoIngresado.charAt(i) != pangramaSeleccionado.charAt(i)) {
                incorrectas++;
            }
        }

        return incorrectas;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TutorMecanografia4();
        });
    }
}