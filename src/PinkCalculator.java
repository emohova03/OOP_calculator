import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PinkCalculator extends JFrame implements ActionListener {
    private final TextField f = new TextField();
    private final JButton[] numberButtons = new JButton[10];
    private final JButton decimalButton = new JButton(".");
    private final JButton result = new JButton("=");
    private final JButton add = new JButton("+");
    private final JButton sub = new JButton("-");
    private final JButton mul = new JButton("*");
    private final JButton div = new JButton("/");
    private final JButton del = new JButton("C");
    private final JButton cancel = new JButton("D");
    private final JButton change = new JButton("+/-");
    private final JButton sqrt = new JButton("sqrt");

    private double a = 0, b = 0;
    private char operator;
    private String text = "";
    private boolean hasDecimal = false;

    public PinkCalculator() {
        Font font = new Font("Impact", Font.BOLD, 24);


        Color backgroundColor = new Color(255, 182, 193); // Light pink
        Color buttonColor = new Color(255, 105, 180);     // Hot pink
        Color textColor = Color.WHITE;

        // Frame settings
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(backgroundColor);  // Set frame background


        f.setFont(font);
        f.setBackground(backgroundColor);
        f.setForeground(textColor);        // White text
        f.setEditable(false);              // Makes the display non-editable
        f.setPreferredSize(new Dimension(360, 50)); // Set custom width for the text field
        add(f, BorderLayout.NORTH);//adds text field to the top of a frame

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10)); // 5 rows, 4 columns, 10px gap
        buttonPanel.setPreferredSize(new Dimension(400, 400));

        // Configure buttons
        configureButton(buttonColor, textColor, font, decimalButton);
        configureButton(buttonColor, textColor, font, result);
        configureButton(buttonColor, textColor, font, add);
        configureButton(buttonColor, textColor, font, sub);
        configureButton(buttonColor, textColor, font, mul);
        configureButton(buttonColor, textColor, font, div);
        configureButton(buttonColor, textColor, font, del);
        configureButton(buttonColor, textColor, font, cancel);
        configureButton(buttonColor, textColor, font, change);
        configureButton(buttonColor, textColor, font, sqrt);

        // Initialize number buttons
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            configureButton(buttonColor, textColor, font, numberButtons[i]);
        }

        // Add buttons to panel
        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(div);

        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(mul);

        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(sub);

        buttonPanel.add(change);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(decimalButton);
        buttonPanel.add(add);

        buttonPanel.add(del);
        buttonPanel.add(cancel);
        buttonPanel.add(sqrt);
        buttonPanel.add(result);

        // Add the panel to the center of the frame
        add(buttonPanel, BorderLayout.CENTER);

        // Fits the frame to components
        pack();

        // Set frame to be visible
        setVisible(true);

        // Action listeners for operator buttons
        addOperatorListeners();
    }

    private void configureButton(Color bgColor, Color fgColor, Font font, JButton button) {
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.addActionListener(this);
    }

    private void addOperatorListeners() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("\\d")) { //if digit
            text += command;
            f.setText(text);
        } else if (command.equals(".")) {
            if (!hasDecimal) {
                text += ".";
                f.setText(text);
                hasDecimal = true;
            }
        } else if (command.equals("C")) {
            text = "";
            f.setText(text);
            hasDecimal = false;
        } else if (command.equals("D")) {
            if (!text.isEmpty()) {
                text = text.substring(0, text.length() - 1);
                f.setText(text);
                hasDecimal = text.contains(".");
            }
        } else if (command.equals("+/-")) {
            if (!text.isEmpty() && !text.equals("0")) {
                if (text.startsWith("-")) {
                    text = text.substring(1);
                } else {
                    text = "-" + text;
                }
                f.setText(text);
            }
        } else if (command.equals("sqrt")) {
            if (!text.isEmpty()) {
                double result = Math.sqrt(Double.parseDouble(text));
                text = String.valueOf(result);
                f.setText(text);
            }
        } else if (command.equals("=")) {
            calculateResult();
        } else {
            a = Double.parseDouble(text);
            operator = command.charAt(0);
            text = "";
            f.setText(a + " " + operator);
            hasDecimal = false; //allows the user to add a decimal point in the next number.
        }
    }

    private void calculateResult() {
        b = Double.parseDouble(text);
        double result;
        switch (operator) {
            case '+' : result = a + b;break;
            case '-' : result = a - b;break;
            case '*' :result = a * b;break;
            case '/' : result = b != 0 ? a / b : 0;break;
            default : throw new IllegalStateException("Unexpected operator: " + operator);
        }
        text = String.valueOf(result);
        f.setText(text);
        hasDecimal = text.contains(".");
    }
}
