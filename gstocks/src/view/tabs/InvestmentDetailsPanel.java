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
import javax.swing.JTextArea;
import javax.swing.BorderFactory;


import util.ViewUtils;

/**
 * This class represents the view for investment details tab in the stock tutor GUI. The class
 * provides methods to setup the view and provides methods to validate all the input text fields
 * within the investment details panel.
 */
public class InvestmentDetailsPanel extends JPanel {

  private final JTextField portfolioNameTextField;
  private final JLabel portfolioNameLabel;

  private final JTextField dateTextField;
  private final JLabel dateLabel;

  private final JButton costBasisButton;
  private final JButton totalValueButton;

  private final JTextArea valueMessage;

  /**
   * Constructor for investment details panel that initializes all the labels, text fields and
   * buttons that are used within this panel. It also calls methods to setup the initial view.
   */
  InvestmentDetailsPanel() {

    this.setBackground(Color.white);
    portfolioNameLabel = new JLabel();
    portfolioNameTextField = new JTextField(15);


    dateLabel = new JLabel();
    dateTextField = new JTextField(10);

    costBasisButton = new JButton("Cost Basis");
    totalValueButton = new JButton("Total Value");
    valueMessage = new JTextArea("");

    this.setLayout(new GridBagLayout());
    this.setPanelView();
  }

  /**
   * Getter method that fetches the textField within the panel.
   *
   * @return textField used to enter portfolio name.
   */
  public JTextField getPortfolioNameTextField() {
    return portfolioNameTextField;
  }

  /**
   * Getter method that fetches the textField within the panel.
   *
   * @return textField used to enter date for which the investment details needs to be fetched.
   */
  public JTextField getDateTextField() {
    return dateTextField;
  }

  @Override
  public void paint(Graphics g) {

    super.paint(g);
    costBasisButton.setActionCommand("Cost Basis");
    totalValueButton.setActionCommand("Total Value");
  }

  private void setPanelView() {

    setPortfolioView();
    setDateView();
    setCostBasisButtonView();
    setTotalValueButtonView();
    setMessageLabelConstraints();
  }


  private void setPortfolioView() {

    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 0.1, 0);
    constraints.insets = new Insets(50, 70, 100, 0);
    portfolioNameLabel.setText("Portfolio name");
    this.add(portfolioNameLabel, constraints);

    constraints = ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 0, 0.7, 0);
    constraints.insets = new Insets(50, -70, 100, 150);
    portfolioNameTextField.setBorder(BorderFactory.
            createTitledBorder("(A-Z, a-z , 0-9 are only allowed)"));
    this.add(portfolioNameTextField, constraints);
  }


  private void setDateView() {

    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 1, 0.1, 0);
    constraints.insets = new Insets(-50, 50, 100, 0);
    dateLabel.setText("<html>Date at which you want<br>" +
            " the cost basis/ total value</html>");
    this.add(dateLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 1, 0.7, 0);
    constraints.insets = new Insets(-50, -70, 100, 150);
    dateTextField.setBorder(BorderFactory.
            createTitledBorder("dd-MM-YYYY HH:mm:ss (24hr format)"));
    this.add(dateTextField, constraints);
  }

  // view setup for cost basis button
  private void setCostBasisButtonView() {

    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 0, 3, 0.6, 0);
    constraints.insets = new Insets(-20, 50, 50, -10);
    this.add(costBasisButton, constraints);
  }

  // view setup for total value button
  private void setTotalValueButtonView() {

    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 1, 3, 0.6, 0);
    constraints.insets = new Insets(-20, 0, 50, 100);
    this.add(totalValueButton, constraints);
  }

  private void setMessageLabelConstraints() {

    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 4, 0, 0);
    constraints.insets = new Insets(20, 30, 0, 10);
    this.add(valueMessage, constraints);
  }

  /**
   * Method that sets the message label within the panel.
   *
   * @param message string to be set as label.
   */
  public void setTextToMessageLabel(String message) {

    valueMessage.setText(message);
  }

  /**
   * Method that sets the action listener for cost basis button.
   *
   * @param listener ActionListener for the cost basis button action event.
   */
  public void setActionListenerForCostBasis(ActionListener listener) {

    costBasisButton.addActionListener(listener);
  }

  /**
   * Method that sets the action listener for total value button.
   *
   * @param listener ActionListener for the total value button action event.
   */
  public void setActionListenerForTotalValue(ActionListener listener) {

    totalValueButton.addActionListener(listener);
  }

  /**
   * Method that validates all the text fields in the panel.
   *
   * @return true, if the input is valid, otherwise false.
   */
  public boolean validateFields() {

    boolean result = ViewUtils.validatePortfolioNameInput(portfolioNameTextField.getText());
    if (!result) {
      JOptionPane.showMessageDialog(this, "Enter valid portfolio name",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
    return result;
  }
}
