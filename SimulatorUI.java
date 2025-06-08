import javax.swing.*;
import java.awt.event.*;

public class SimulatorUI extends JFrame {
    JTextField inputField, outputField, errorField, errorPosField;
    JButton encodeButton, simulateErrorButton, detectButton;
    String encodedData = "";

    public SimulatorUI() {
        setTitle("Hamming SEC-DED Simülatörü");
        setLayout(null);
        setSize(620, 300);

        JLabel label1 = new JLabel("Giriş Verisi (8,16,32 bit):");
        label1.setBounds(20, 20, 200, 25);
        add(label1);

        inputField = new JTextField();
        inputField.setBounds(220, 20, 250, 25);
        add(inputField);

        encodeButton = new JButton("Kodla");
        encodeButton.setBounds(20, 60, 100, 25);
        add(encodeButton);

        outputField = new JTextField();
        outputField.setBounds(130, 60, 450, 25);
        add(outputField);

        JLabel errorPosLabel = new JLabel("Bozulacak bit (sağdan, 1-indexli):");
        errorPosLabel.setBounds(20, 100, 200, 25);
        add(errorPosLabel);

        errorPosField = new JTextField("5");
        errorPosField.setBounds(230, 100, 50, 25);
        add(errorPosField);

        simulateErrorButton = new JButton("Hata Ekle");
        simulateErrorButton.setBounds(290, 100, 120, 25);
        add(simulateErrorButton);

        detectButton = new JButton("Hata Tespit / Düzelt");
        detectButton.setBounds(420, 100, 160, 25);
        add(detectButton);

        errorField = new JTextField();
        errorField.setBounds(20, 140, 560, 25);
        add(errorField);

        encodeButton.addActionListener(e -> {
            String data = inputField.getText().trim();

            if (!data.matches("[01]+")) {
                JOptionPane.showMessageDialog(null, "Lütfen sadece 0 ve 1 giriniz.");
                return;
            }

            // Tersleme YOK! encode() zaten tersliyor
            encodedData = HammingEncoder.encode(data);
            outputField.setText(encodedData);
        });


        simulateErrorButton.addActionListener(e -> {
            try {
                int bitNumber = Integer.parseInt(errorPosField.getText().trim());
                int bitIndex = encodedData.length() - bitNumber;
                if (!encodedData.isEmpty() && bitIndex >= 0 && bitIndex < encodedData.length()) {
                    char[] chars = encodedData.toCharArray();
                    chars[bitIndex] = (chars[bitIndex] == '1') ? '0' : '1';
                    encodedData = new String(chars);
                    outputField.setText(encodedData);
                } else {
                    JOptionPane.showMessageDialog(null, "Geçerli bir bit konumu girin.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Lütfen geçerli bir sayı girin.");
            }
        });

        detectButton.addActionListener(e -> {
            int errorBit = HammingDecoder.detectError(encodedData);
            if (errorBit == 0) {
                errorField.setText("Hata yok.");
            } else {
                int rightIndex = encodedData.length() - errorBit + 1;
                errorField.setText("Hatalı bit (sağdan): " + rightIndex + " — Düzeltildi.");
                encodedData = HammingDecoder.correctError(encodedData, errorBit);
                outputField.setText(encodedData);
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}