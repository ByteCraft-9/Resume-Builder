package resume.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {

    private JPanel menuPanel;
    private JPanel contentPanel;
    private JProgressBar progressBar;
    private int currentSectionIndex = 0;
    private final String[] sectionNames = {"Personal Info", "Work History", "Education", "Skills", "Summary", "Finalize"};
    private PersonalInfo pInfo;
    private WorkHistory workHistory;
    private Education education;
    private Experience skills;
    private Summary summary;
    private Finalize finalize;

    public Menu(int userId) {
        // Set up the frame
        setTitle("Resume Builder");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize menu panel
        menuPanel = new JPanel();
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        // Add spacing at the top of the menu panel
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Add buttons to the menu panel
        for (String section : sectionNames) {
            JButton button = createMenuButton(section, userId);
            menuPanel.add(button);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between buttons
        }

        // Add progress bar to the bottom of the menu panel
        progressBar = new JProgressBar(0, sectionNames.length - 1);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(140, 20));
        menuPanel.add(progressBar);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add spacing at the bottom

        // Initialize content panel
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BorderLayout());

        // Add panels to the frame
        add(menuPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // Show initial section
        showSection(sectionNames[currentSectionIndex], userId);

        setVisible(true);
    }

    private JButton createMenuButton(String section, int userId) {
        JButton button = new JButton(section);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(140, 40));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.addActionListener(new MenuButtonListener(section, userId));
        return button;
    }

    private class MenuButtonListener implements ActionListener {
        private String section;
        private int userId;

        public MenuButtonListener(String section, int userId) {
            this.section = section;
            this.userId = userId;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int newIndex = getSectionIndex(section);
            if (newIndex != -1) {
                currentSectionIndex = newIndex;
                showSection(section, userId);
                updateProgressBar();
                updateButtonColors();
            }
        }
    }

    private void showSection(String section, int userId) {
        contentPanel.removeAll();
        if (section.equals("Personal Info")) {
            if (pInfo == null) {
                pInfo = new PersonalInfo(userId);
                pInfo.setNextListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSectionIndex++;
                        showSection(sectionNames[currentSectionIndex], userId);
                        updateProgressBar();
                        updateButtonColors();
                    }
                });
            }
            contentPanel.add(pInfo, BorderLayout.CENTER);
        } else if (section.equals("Work History")) {
            if (workHistory == null) {
                workHistory = new WorkHistory(userId);
                workHistory.setBackListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSectionIndex--;
                        showSection(sectionNames[currentSectionIndex], userId);
                        updateProgressBar();
                        updateButtonColors();
                    }
                });
                workHistory.setNextListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSectionIndex++;
                        showSection(sectionNames[currentSectionIndex], userId);
                        updateProgressBar();
                        updateButtonColors();
                    }
                });
            }
            contentPanel.add(workHistory, BorderLayout.CENTER);
        } else if (section.equals("Education")) {
            if (education == null) {
                education = new Education(userId);
                education.setBackListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSectionIndex--;
                        showSection(sectionNames[currentSectionIndex], userId);
                        updateProgressBar();
                        updateButtonColors();
                    }
                });
                education.setNextListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSectionIndex++;
                        showSection(sectionNames[currentSectionIndex], userId);
                        updateProgressBar();
                        updateButtonColors();
                    }
                });
            }
            contentPanel.add(education, BorderLayout.CENTER);
        } else if (section.equals("Skills")) {
            if (skills == null) {
                skills = new Experience(userId);
                skills.setBackListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSectionIndex--;
                        showSection(sectionNames[currentSectionIndex], userId);
                        updateProgressBar();
                        updateButtonColors();
                    }
                });
                skills.setNextListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSectionIndex++;
                        showSection(sectionNames[currentSectionIndex], userId);
                        updateProgressBar();
                        updateButtonColors();
                    }
                });
            }
            contentPanel.add(skills, BorderLayout.CENTER);
        } else if (section.equals("Summary")) {
            if (summary == null) {
                summary = new Summary(userId);
                summary.setBackListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSectionIndex--;
                        showSection(sectionNames[currentSectionIndex], userId);
                        updateProgressBar();
                        updateButtonColors();
                    }
                });
                summary.setNextListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentSectionIndex++;
                        showSection(sectionNames[currentSectionIndex], userId);
                        updateProgressBar();
                        updateButtonColors();
                    }
                });
            }
            contentPanel.add(summary, BorderLayout.CENTER); // Add summary panel to contentPanel
        } else if (section.equals("Finalize")) {
            if (finalize == null) {
                finalize = new Finalize(userId);
            }
            contentPanel.add(finalize, BorderLayout.CENTER);
        }
        else {
            JLabel label = new JLabel("Selected: " + section);
            label.setFont(new Font("Segoe UI", Font.BOLD, 24));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            contentPanel.add(label, BorderLayout.CENTER);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void updateProgressBar() {
        progressBar.setValue(currentSectionIndex);
    }

    private void updateButtonColors() {
        Component[] components = menuPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals(sectionNames[currentSectionIndex])) {
                    button.setBackground(new Color(0, 102, 102)); // Set selected button's background color
                    button.setForeground(Color.WHITE); // Set selected button's text color to white
                } else {
                    button.setBackground(Color.WHITE); // Reset other buttons' background color
                    button.setForeground(Color.BLACK); // Reset other buttons' text color to black
                }
            }
        }
    }

    private int getSectionIndex(String section) {
        for (int i = 0; i < sectionNames.length; i++) {
            if (sectionNames[i].equals(section)) {
                return i;
            }
        }
        return -1; // Should never happen
    }


}
