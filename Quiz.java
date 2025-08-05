import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Quiz extends JFrame implements ActionListener {
    String[][] questions = {
        {"which one is not a opps concept in java?", "A.Encapsulation", "B.Abstraction", "C.Class", "D.Inheritance", "C"},
        {"Java is a platform independent?", "A. No", "B. Yes", "C. None of the above", "D. A nad B", "B"},
        {"Which of the following is not a java keyword?", "A. static", "B. boolean", "C. extends", "D. final", "B"},
        {"Which methos is the entry point for a java program?", "A. start()", "B.init()", "C.main()", "D. run()", "C"},
        {"Which of the following is used to handle exceptions in java?", "A.catch", "B.try", "C.throw", "D. all of the above", "D"}
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
