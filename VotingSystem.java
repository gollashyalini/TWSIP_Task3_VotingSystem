import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class VotingSystem extends JFrame {
    private JTextField nameField;
    private JTextField phoneField;
    private ButtonGroup partyGroup;
    private static Map<String, Integer> votes;

    public VotingSystem() {
        setTitle("Online Voting System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2));

        votes = new HashMap<>();
        votes.put("Party A", 0);
        votes.put("Party B", 0);
        votes.put("Party C", 0);

        // GUI components
        add(new JLabel("Enter Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Enter Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Cast Your Vote Here:"));

        partyGroup = new ButtonGroup();
        JRadioButton partyA = new JRadioButton("Party A");
        partyGroup.add(partyA);
        add(partyA);

        JRadioButton partyB = new JRadioButton("Party B");
        partyGroup.add(partyB);
        add(partyB);

        JRadioButton partyC = new JRadioButton("Party C");
        partyGroup.add(partyC);
        add(partyC);

        JButton submitButton = new JButton("Submit Your Vote");
        submitButton.addActionListener(new SubmitVoteListener());
        add(submitButton);

        JButton checkResultsButton = new JButton("Check Results");
        checkResultsButton.addActionListener(new CheckResultsListener());
        add(checkResultsButton);
    }

    private class SubmitVoteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String selectedParty = null;

            for (java.util.Enumeration<AbstractButton> buttons = partyGroup.getElements(); buttons.hasMoreElements(); ) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    selectedParty = button.getText();
                    break;
                }
            }

            if (name.isEmpty() || phone.isEmpty() || selectedParty == null) {
                JOptionPane.showMessageDialog(VotingSystem.this, "Please fill in all fields and select a party.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            votes.put(selectedParty, votes.get(selectedParty) + 1);
            JOptionPane.showMessageDialog(VotingSystem.this, "Vote submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class CheckResultsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder results = new StringBuilder();
            results.append("Party A: ").append(votes.get("Party A")).append("\n");
            results.append("Party B: ").append(votes.get("Party B")).append("\n");
            results.append("Party C: ").append(votes.get("Party C")).append("\n");

            String leadingParty = "";
            int maxVotes = 0;

            for (Map.Entry<String, Integer> entry : votes.entrySet()) {
                if (entry.getValue() > maxVotes) {
                    maxVotes = entry.getValue();
                    leadingParty = entry.getKey();
                } else if (entry.getValue() == maxVotes) {
                    leadingParty = "Tie";
                }
            }

            if (!leadingParty.equals("Tie")) {
                results.append(leadingParty).append(" has a lead.");
            } else {
                results.append("It's a tie.");
            }

            JOptionPane.showMessageDialog(VotingSystem.this, results.toString(), "Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VotingSystem votingSystem = new VotingSystem();
            votingSystem.setVisible(true);
        });
    }
}
