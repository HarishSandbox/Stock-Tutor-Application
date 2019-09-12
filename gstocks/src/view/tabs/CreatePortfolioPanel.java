package view.tabs;

import java.awt.Insets;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;

import util.ViewUtils;

/**
 * This class represents the view for create portfolio tab in the stock tutor GUI. The class
 * provides methods to setup the view and provides methods to validate all the input text fields
 * within the create portfolio panel.
 */
public class CreatePortfolioPanel extends JPanel {


  private final JTextField textField;
  private final JButton createButton;
  private final JLabel messageLabel;

  /**
   * Constructor for create portfolio panel that initializes all the labels, text fields and buttons
   * that are used within this panel. It also calls methods to setup the initial view.
   */
  CreatePortfolioPanel() {

    this.setBackground(Color.white);
    textField = new JTextField(100);
    createButton = new JButton("Create Portfolio");
    messageLabel = new JLabel();
  }

  /**
   * Getter method that fetches the textField within the panel.
   *
   * @return textField used to enter portfolio name to be created.
   */
  public JTextField getTextField() {
    return textField;
  }

  @Override
  public void paint(Graphics g) {

    super.paint(g);

    this.setLayout(new GridBagLayout());

    messageLabel.setText("<html>Enter the name of the portfolio <font " +
            "color='red'>*</font></html>");
    createButton.setActionCommand("Create portfolio");
    textField.setBorder(BorderFactory.createTitledBorder("(A-Z, a-z , 0-9 are only allowed)"));

    this.setPanelView();
  }

  private void setPanelView() {

    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 0.3, 0);
    constraints.insets = new Insets(40, 20, 0, 0);
    this.add(messageLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 0, 0.7, 0);
    constraints.insets = new Insets(40, 10, 0, 100);
    this.add(textField, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 0, 1, 0.3, 0);
    constraints.insets = new Insets(100, 0, 10, 0);
    constraints.gridwidth = 2;
    this.add(createButton, constraints);
  }

  /**
   * Method that resets the text field to empty.
   */
  public void resetData() {

    textField.setText("");
  }

  /**
   * Method that sets the action listener for create button.
   *
   * @param listener ActionListener for the button action event.
   */
  public void setActionListener(ActionListener listener) {

    createButton.addActionListener(listener);
  }

  /**
   * Method that validates all the text fields in the panel.
   *
   * @return true, if the input is valid, otherwise false.
   */
  public boolean validateDataEntry() {

    boolean result = ViewUtils.validatePortfolioNameInput(textField.getText());
    if (!result) {
      JOptionPane.showMessageDialog(this, "Enter valid portfolio name",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
    return result;
  }
}