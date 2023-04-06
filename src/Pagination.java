import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;

public class Pagination extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;
    private JButton prevButton, nextButton;
    private ItemsTableModel tableModel;
    private final String PREVIOUS_BTN_TEXT = "<<", NEXT_BTN_TEXT = ">>";
    private JLabel connectedStatus = new JLabel("Not Connected"), timeLabel = new JLabel("00:00:00");

    public Pagination() {
        // Initialize the table model with your data
        try {
            tableModel = new ItemsTableModel();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


        // Create the table using the table model
        table = new JTable(tableModel);

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 0));
        infoPanel.add(connectedStatus);
        infoPanel.add(timeLabel);

        // Create the scroll pane and add the table to it
        scrollPane = new JScrollPane(table);

        // Create the previous button
        prevButton = new JButton(PREVIOUS_BTN_TEXT);
        prevButton.setEnabled(false);
        prevButton.addActionListener(new MyButtonActionListener());

        // Create the next button
        nextButton = new JButton(NEXT_BTN_TEXT);
        nextButton.setEnabled(MyGlobals.ITEMS.length > MyGlobals.ITEMS_PER_PAGE);
        nextButton.addActionListener(new MyButtonActionListener());

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        // Add the scroll pane and button panel to the frame
        getContentPane().add(infoPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Set the frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);


    }


    private class MyButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the source of the action event
            MyGlobals.CURRENT_PAGE = ((JButton) e.getSource()).getText().equals(PREVIOUS_BTN_TEXT) ? MyGlobals.CURRENT_PAGE - 1 : MyGlobals.CURRENT_PAGE + 1;
            prevButton.setEnabled(MyGlobals.CURRENT_PAGE > 1);
            nextButton.setEnabled((MyGlobals.CURRENT_PAGE * MyGlobals.ITEMS_PER_PAGE) < MyGlobals.ITEMS.length);
            tableModel.fireTableStructureChanged();
        }
    }

}
