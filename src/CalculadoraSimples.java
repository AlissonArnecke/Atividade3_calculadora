import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculadoraSimples extends JFrame {
    private JTextField display = new JTextField();

    public CalculadoraSimples() {
        setTitle("Calculadora");
        setSize(250, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4, 4));
        String[] buttons = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", "C", "=", "+"};

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(e -> buttonClick(text));
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
    }

    private String input = "";

    private void buttonClick(String text) {
        if (text.equals("C")) {
            input = "";
            display.setText("");
        } else if (text.equals("=")) {
            try {
                display.setText(String.valueOf(eval(input)));
                input = "";
            } catch (Exception e) {
                display.setText("Erro");
            }
        } else {
            input += text;
            display.setText(input);
        }
    }

    private double eval(String expression) {
        try {
            return evaluateExpression(expression);
        } catch (Exception e) {
            return 0;
        }
    }

    // Função que avalia a expressão
    private double evaluateExpression(String expression) {
        // Remove espaços da expressão
        expression = expression.replaceAll(" ", "");

        // Expressão para lidar com os operadores de multiplicação e divisão primeiro
        expression = evaluateSimpleExpression(expression, "*", "/");

        // Agora, podemos lidar com a soma e subtração
        expression = evaluateSimpleExpression(expression, "+", "-");

        return Double.parseDouble(expression); // Retorna o resultado final
    }

    // Função que avalia expressões de soma ou subtração, multiplicação ou divisão
    private String evaluateSimpleExpression(String expression, String operator1, String operator2) {
        int operatorPos;

        while ((operatorPos = expression.indexOf(operator1)) != -1 || (operatorPos = expression.indexOf(operator2)) != -1) {
            String[] parts = expression.split("[" + operator1 + operator2 + "]");
            double left = Double.parseDouble(parts[0]);
            double right = Double.parseDouble(parts[1]);
            double result = 0;

            if (operator1.equals("*") || operator1.equals("/")) {
                if (expression.charAt(operatorPos) == operator1.charAt(0)) {
                    result = left * right;
                } else {
                    result = left / right;
                }
            }
            expression = String.valueOf(result);
        }
        return expression;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculadoraSimples().setVisible(true);
        });
    }
}
