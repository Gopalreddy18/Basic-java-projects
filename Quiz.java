import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Quiz extends JFrame implements ActionListener {
    String[][] questions = {
        {"What is the capital of France?", "A. Paris", "B. London", "C. Rome", "D. Berlin", "A"},
        {"Which planet is known as the Red Planet?", "A. Earth", "B. Mars", "C. Jupiter", "D. Venus", "B"},
        {"Who wrote 'Romeo and Juliet'?", "A. Charles Dickens", "B. Mark Twain", "C. William Shakespeare", "D. Jane Austen", "C"},
        {"Which gas do plants absorb from the atmosphere?", "A. Oxygen", "B. Nitrogen", "C. Carbon Dioxide", "D. Hydrogen", "C"},
        {"Which is the largest ocean on Earth?", "A. Atlantic Ocean", "B. Indian Ocean", "C. Pacific Ocean", "D. Arctic Ocean", "C"}
    };

    int currentQuestion = 0;
    int score = 0;

    JLabel questionLabel;
    JButton[] optionButtons = new JButton[4];
    boolean waiting = false;

    public Quiz() {
        setTitle("Quiz Game");
        setSize(600, 300);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        questionLabel = new JLabel("Question", JLabel.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(questionLabel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            optionButtons[i].setFocusPainted(false);
            optionButtons[i].addActionListener(this);
            buttonPanel.add(optionButtons[i]);
        }
        add(buttonPanel, BorderLayout.CENTER);
        displayQuestion();
        setVisible(true);
    }

    void displayQuestion() {
        if (currentQuestion >= questions.length) {
            showScore();
            return;
        }
        for (JButton btn : optionButtons) {
            btn.setBackground(null);
            btn.setEnabled(true);
        }
        String[] q = questions[currentQuestion];
        questionLabel.setText("Q" + (currentQuestion + 1) + ": " + q[0]);
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(q[i + 1]);
        }
        waiting = false;
    }

    public void actionPerformed(ActionEvent e) {
        if (waiting) return;
        waiting = true;
        JButton clicked = (JButton) e.getSource();
        String selected = clicked.getText();
        String correct = questions[currentQuestion][5];

        if (selected.startsWith(correct)) {
            clicked.setBackground(Color.GREEN);
            score++;
        } else {
            clicked.setBackground(Color.RED);
            for (JButton btn : optionButtons) {
                if (btn.getText().startsWith(correct)) {
                    btn.setBackground(Color.GREEN);
                    break;
                }
            }
        }
        for (JButton btn : optionButtons) {
            btn.setEnabled(false);
        }
        Timer pause = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                currentQuestion++;
                displayQuestion();
            }
        });
        pause.setRepeats(false);
        pause.start();
    }

    void showScore() {
        JOptionPane.showMessageDialog(this,
            "Quiz Over!\nYour score: " + score + "/" + questions.length,
            "Result",
            JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
    public static void main(String[] args) {
        new Quiz();
    }
}
