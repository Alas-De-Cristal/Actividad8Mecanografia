import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TutorMecanografia extends JFrame {
    private JTextArea textArea;

    public TutorMecanografia() {
        super("Tutor de Mecanografía");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el teclado virtual
        JPanel keyboardPanel = new JPanel(new GridLayout(4, 10));
        String[] buttonLabels = {
                "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
                "A", "S", "D", "F", "G", "H", "J", "K", "L", "Ñ",
                "Z", "X", "C", "V", "B", "N", "M"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new KeyboardButtonListener());
            keyboardPanel.add(button);
        }

        // Crear el área de texto
        textArea = new JTextArea();
        textArea.setEditable(false);

        // Diseño general de la GUI
        setLayout(new BorderLayout());
        add(keyboardPanel, BorderLayout.CENTER);
        add(new JScrollPane(textArea), BorderLayout.SOUTH);

        // Configuración de la ventana
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Agregar un oyente para cerrar la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Salir del bucle de espera cuando se cierra la ventana
                dispose();
            }
        });

        setVisible(true);
    }

    private class KeyboardButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource();
            String buttonText = sourceButton.getText();
            textArea.append(buttonText);
            sourceButton.setEnabled(false); // Desactivar el botón para resaltado
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TutorMecanografia();
        });
    }
}