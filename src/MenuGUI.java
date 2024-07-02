import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class MenuGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    // Componentes da interface
    private JTextField display;

    public MenuGUI() {
        setTitle("Menu de Opções");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display para exibir resultados
        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        add(display, BorderLayout.NORTH);

        // Painel para os botões
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Adiciona margens

        // Botões para as funcionalidades
        JButton btnCalculadora = new JButton("Calculadora");
        JButton btnParImpar = new JButton("Par ou Ímpar");
        JButton btnNumeroPrimo = new JButton("Número Primo");
        JButton btnGerarTriangulo = new JButton("Gerar Triângulo");
        JButton btnNumerosAleatorios = new JButton("Números Aleatórios");

        // Estilizando os botões
        Font fonteBotao = new Font("Arial", Font.BOLD, 18);
        Color corBotao = new Color(70, 130, 180); // Cor azul
        Color corTextoBotao = Color.WHITE; // Cor do texto

        btnCalculadora.setFont(fonteBotao);
        btnCalculadora.setBackground(corBotao);
        btnCalculadora.setForeground(corTextoBotao);

        btnParImpar.setFont(fonteBotao);
        btnParImpar.setBackground(corBotao);
        btnParImpar.setForeground(corTextoBotao);

        btnNumeroPrimo.setFont(fonteBotao);
        btnNumeroPrimo.setBackground(corBotao);
        btnNumeroPrimo.setForeground(corTextoBotao);

        btnGerarTriangulo.setFont(fonteBotao);
        btnGerarTriangulo.setBackground(corBotao);
        btnGerarTriangulo.setForeground(corTextoBotao);

        btnNumerosAleatorios.setFont(fonteBotao);
        btnNumerosAleatorios.setBackground(corBotao);
        btnNumerosAleatorios.setForeground(corTextoBotao);

        // Adicionando os botões ao painel
        panel.add(btnCalculadora);
        panel.add(btnParImpar);
        panel.add(btnNumeroPrimo);
        panel.add(btnGerarTriangulo);
        panel.add(btnNumerosAleatorios);

        // Ação para cada botão
        btnCalculadora.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirCalculadora();
            }
        });

        btnParImpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                verificarParOuImpar();
            }
        });

        btnNumeroPrimo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                verificarNumeroPrimo();
            }
        });

        btnGerarTriangulo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gerarTriangulo();
            }
        });

        btnNumerosAleatorios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pedirNumerosAleatorios();
            }
        });

        add(panel, BorderLayout.CENTER);

        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Método para abrir a calculadora
    private void abrirCalculadora() {
        JFrame frameCalculadora = new JFrame("Calculadora");
        frameCalculadora.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas a janela atual

        JPanel panel = new JPanel(new BorderLayout());
        JTextField display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        panel.add(display, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        String[] buttonsText = {"7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"};

        for (String text : buttonsText) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String buttonText = e.getActionCommand();
                    if (buttonText.equals("=")) {
                        try {
                            double result = eval(display.getText());
                            display.setText(String.valueOf(result));
                        } catch (Exception ex) {
                            display.setText("Erro");
                        }
                    } else if (buttonText.equals("Limpar")) {
                        display.setText("");
                    } else {
                        display.setText(display.getText() + buttonText);
                    }
                }
            });
        }

        // Botão Limpar
        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setPreferredSize(new Dimension(panel.getWidth(), 50));
        btnLimpar.setFont(new Font("Arial", Font.PLAIN, 18));
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                display.setText("");
            }
        });

        frameCalculadora.add(panel, BorderLayout.NORTH);
        frameCalculadora.add(buttonsPanel, BorderLayout.CENTER);
        frameCalculadora.add(btnLimpar, BorderLayout.SOUTH); // Adiciona o botão Limpar ao painel inferior
        frameCalculadora.setSize(300, 400);
        frameCalculadora.setLocationRelativeTo(null);
        frameCalculadora.setVisible(true);
    }

    // Método para avaliar a expressão matemática
    private double eval(String expressao) {
        return new Object() {
            int posicao = -1, caractere;

            void proximoCaractere() {
                caractere = (++posicao < expressao.length()) ? expressao.charAt(posicao) : -1;
            }

            boolean espaco(int charToEat) {
                while (caractere == ' ') proximoCaractere();
                if (caractere == charToEat) {
                    proximoCaractere();
                    return true;
                }
                return false;
            }

            double parse() {
                proximoCaractere();
                double x = parseExpressao();
                if (posicao < expressao.length()) throw new RuntimeException("Caractere inesperado: " + (char)caractere);
                return x;
            }

            // Analisar a expressão
            double parseExpressao() {
                double x = parseTermo();
                for (;;) {
                    if      (espaco('+')) x += parseTermo(); // Adição
                    else if (espaco('-')) x -= parseTermo(); // Subtração
                    else return x;
                }
            }

            // Avaliar o termo
            double parseTermo() {
                double x = parseFator();
                for (;;) {
                    if      (espaco('*')) x *= parseFator(); // Multiplicação
                    else if (espaco('/')) x /= parseFator(); // Divisão
                    else return x;
                }
            }

            // Avaliar o fator
            double parseFator() {
                if (espaco('+')) return parseFator(); // Adição unária
                if (espaco('-')) return -parseFator(); // Subtração unária

                double x;
                int inicioPosicao = this.posicao;
                if (espaco('(')) { // Avaliar parenteses
                    x = parseExpressao();
                    espaco(')');
                } else if ((caractere >= '0' && caractere <= '9') || caractere == '.') { // Números
                    while ((caractere >= '0' && caractere <= '9') || caractere == '.') proximoCaractere();
                    x = Double.parseDouble(expressao.substring(inicioPosicao, this.posicao));
                } else {
                    throw new RuntimeException("Número inesperado: " + (char)caractere);
                }

                return x;
            }
        }.parse();
    }

    // Método para verificar se um número é par ou ímpar
    private void verificarParOuImpar() {
        String input = JOptionPane.showInputDialog(this, "Digite um número:");
        try {
            int num = Integer.parseInt(input);
            if (num % 2 == 0) {
                JOptionPane.showMessageDialog(this, num + " é um número par.");
            } else {
                JOptionPane.showMessageDialog(this, num + " é um número ímpar.");
            }
        } catch (NumberFormatException ex) {
            // Não exibir mensagem de erro ao fechar a tela
            if (input != null && !input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite um número inteiro válido.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para verificar se um número é primo
    private void verificarNumeroPrimo() {
        String input = JOptionPane.showInputDialog(this, "Digite um número:");
        try {
            int num = Integer.parseInt(input);
            boolean primo = true;
            if (num < 2) {
                primo = false;
            } else {
                for (int i = 2; i <= Math.sqrt(num); i++) {
                    if (num % i == 0) {
                        primo = false;
                        break;
                    }
                }
            }

            if (primo) {
                JOptionPane.showMessageDialog(this, num + " é um número primo.");
            } else {
                JOptionPane.showMessageDialog(this, num + " não é um número primo.");
            }
        } catch (NumberFormatException ex) {
            // Não exibir mensagem de erro ao fechar a tela
            if (input != null && !input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite um número inteiro válido.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para gerar triângulo centralizado
    private void gerarTriangulo() {
        String input = JOptionPane.showInputDialog(this, "Digite um número para gerar o triângulo:");
        try {
            int n = Integer.parseInt(input);
            String triangle = generateTriangle(n);
            JOptionPane.showMessageDialog(this, "Triângulo gerado:\n\n" + triangle);
        } catch (NumberFormatException ex) {
            // Não exibir mensagem de erro ao fechar a tela
            if (input != null && !input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite um número inteiro válido.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para gerar triângulo centralizado
    private String generateTriangle(int n) {
        StringBuilder triangle = new StringBuilder();
        // Espaço para centralizar o triângulo
        int espacoInicial = n - 1;

        for (int i = 1; i <= n; i++) {
            // Adiciona espaços iniciais para centralizar
            for (int j = 0; j < espacoInicial; j++) {
                triangle.append(" ");
            }
            // Adiciona números
            for (int j = 1; j <= i; j++) {
                triangle.append(j).append(" ");
            }
            triangle.append("\n");
            espacoInicial--;
        }
        return triangle.toString();
    }

    // Método para pedir números aleatórios
    private void pedirNumerosAleatorios() {
        JTextField quantidadeField = new JTextField(5);
        JTextField menorField = new JTextField(5);
        JTextField maiorField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Quantidade:"));
        myPanel.add(quantidadeField);
        myPanel.add(new JLabel("Menor valor:"));
        myPanel.add(menorField);
        myPanel.add(new JLabel("Maior valor:"));
        myPanel.add(maiorField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Digite a quantidade e os limites dos números aleatórios",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int quantidade = Integer.parseInt(quantidadeField.getText());
                int menor = Integer.parseInt(menorField.getText());
                int maior = Integer.parseInt(maiorField.getText());

                if (menor >= maior) {
                    JOptionPane.showMessageDialog(this, "Erro: O menor valor deve ser menor que o maior valor.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                StringBuilder resultado = new StringBuilder();
                Random random = new Random();
                for (int i = 0; i < quantidade; i++) {
                    int numeroAleatorio = random.nextInt(maior - menor + 1) + menor;
                    resultado.append(numeroAleatorio).append("\n");
                }

                JOptionPane.showMessageDialog(this, "Números aleatórios gerados:\n\n" + resultado.toString());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite valores numéricos válidos.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MenuGUI();
            }
        });
    }
}
