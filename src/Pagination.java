import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Pagination extends JFrame {
    private JTable table;
    public JScrollPane scrollPane;
    public JButton prevButton, nextButton;

    public Pagination() {
        // Initialize the table model with your data
        ItemsTableModel tableModel = new ItemsTableModel();

        // Create the table using the table model
        table = new JTable(tableModel);

        // Create the scroll pane and add the table to it
        scrollPane = new JScrollPane(table);

        // Create the previous button
        prevButton = new JButton("Previous");
        prevButton.setEnabled(false);
        prevButton.addActionListener(new MyButtonActionListener());

        // Create the next button
        nextButton = new JButton("Next");
        nextButton.addActionListener(new MyButtonActionListener());

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        // Add the scroll pane and button panel to the frame
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Set the frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Pagination Example");
        pack();
        setVisible(true);


    }


    private class MyButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the source of the action event
            MyGlobals.CURRENT_PAGE = ((JButton) e.getSource()).getText().equals("prev") ? MyGlobals.CURRENT_PAGE-- : MyGlobals.CURRENT_PAGE--;
            prevButton.setEnabled(MyGlobals.CURRENT_PAGE > 1);
            nextButton.setEnabled((MyGlobals.CURRENT_PAGE * MyGlobals.ITEMS_PER_PAGE) < MyGlobals.ITEMS.length);

        }
    }


}
