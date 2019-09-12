package view.tabs;

import java.awt.Insets;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;

import util.DateUtility;
import util.ViewUtils;

/**
 * This class represents the view for buy stock tab in the stock tutor GUI. The class provides
 * methods to setup the view and provides methods to validate all the input text fields within the
 * buy stock panel.
 */
public class BuyStockPanel extends JPanel implements ActionListener {

  private final JTextField portfolioNameTextField;
  private final JLabel portfolioNameLabel;

  private final JTextField tickerSymbolTextField;
  private final JLabel tickerSymbolLabel;

  private final JTextField amountTextField;
  private final JLabel amountLabel;

  private final JTextField dateOfPurchaseTextField;
  private final JLabel dateOfPurchaseLabel;

  private final JTextField commissionTextField;
  private final JLabel commissionLabel;

  private final JComboBox<String> commissionBox;
  private final JLabel commissionComboBoxLabel;

  private boolean commissionFlag;

  private final JButton buyButton;

  /**
   * Constructor for buy stock panel that initializes all the labels, text fields and buttons that
   * are used within this panel. It also calls methods to setup the initial view.
   */
  BuyStockPanel() {

    this.setBackground(Color.white);
    portfolioNameLabel = new JLabel("Portfolio Name");
    portfolioNameTextField = new JTextField(15);
    portfolioNameTextField.setActionCommand("Text Field");

    tickerSymbolLabel = new JLabel("Ticker Symbol");
    tickerSymbolTextField = new JTextField(10);
    tickerSymbolTextField.setActionCommand("Text Field");

    amountLabel = new JLabel("Amount($)");
    amountTextField = new JTextField(7);
    amountTextField.setActionCommand("Text Field");

    dateOfPurchaseLabel = new JLabel("Date of purchase");
    dateOfPurchaseTextField = new JTextField(10);
    dateOfPurchaseTextField.setActionCommand("Text Field");

    commissionLabel = new JLabel("Commission Fees($)");
    commissionTextField = new JTextField(5);
    commissionTextField.setActionCommand("Text Field");

    buyButton = new JButton("Buy");

    String[] comboValues = {"Yes", "No"};
    commissionComboBoxLabel = new JLabel("Enter Commission?");
    commissionBox = new JComboBox<>(comboValues);

    commissionBox.addActionListener(this);
    commissionBox.setActionCommand("Combo Box");

    this.setLayout(new GridBagLayout());
    this.setPanelView();
  }

  /**
   * Getter method that fetches portfolioNameTextField.
   *
   * @return portfolioNameTextField used to enter portfolio name.
   */
  public JTextField getPortfolioNameTextField() {
    return portfolioNameTextField;
  }

  /**
   * Getter method that fetches tickerSymbolTextField.
   *
   * @return tickerSymbolTextField used to enter ticker symbol.
   */
  public JTextField getTickerSymbolTextField() {
    return tickerSymbolTextField;
  }

  /**
   * Getter method that fetches amountTextField.
   *
   * @return amountTextField used to enter amount.
   */
  public JTextField getAmountTextField() {
    return amountTextField;
  }

  /**
   * Getter method that fetches dateOfPurchaseTextField.
   *
   * @return dateOfPurchaseTextField used to enter date of purchase.
   */
  public JTextField getDateOfPurchaseTextField() {
    return dateOfPurchaseTextField;
  }

  /**
   * Getter method that fetches commissionTextField.
   *
   * @return commissionTextField used to enter commission fees.
   */
  public JTextField getCommissionTextField() {
    return commissionTextField;
  }

  /**
   * Method that fetches boolean commissionFlag value.
   *
   * @return commissionFlag boolean flag that checks if commission fees is required or not.
   */
  public boolean getCommissionFlag() {
    return commissionFlag;
  }

  @Override
  public void paint(Graphics g) {

    super.paint(g);
    buyButton.setActionCommand("Buy Stock");
  }


  private void setPanelView() {

    setPortfolioNameView();
    setTickerSymbolView();
    setAmountView();
    setDateOfPurchaseView();
    setComboBoxView();
    setCommissionFeesView();
    setBuyButtonView();

  }

  // view setup for portfolio name view
  private void setPortfolioNameView() {

    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 0.1, 0);
    constraints.insets = new Insets(40, 50, 0, 0);
    this.add(portfolioNameLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 0, 0.7, 0);
    constraints.insets = new Insets(40, 50, 0, 10);
    portfolioNameTextField.setBorder(BorderFactory.
            createTitledBorder("(A-Z, a-z , 0-9 are only allowed)"));
    this.add(portfolioNameTextField, constraints);

  }

  // view setup for ticker symbol view
  private void setTickerSymbolView() {

    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 1, 0.3, 0);
    constraints.insets = new Insets(40, 50, 0, 0);
    this.add(tickerSymbolLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 1, 0.7, 0);
    constraints.insets = new Insets(40, 50, 0, 10);
    tickerSymbolTextField.setBorder(BorderFactory.
            createTitledBorder("Ex: GOOG"));
    tickerSymbolTextField.addActionListener(this);
    this.add(tickerSymbolTextField, constraints);
  }

  // view setup for Amount view
  private void setAmountView() {

    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 2, 0.3, 0);
    constraints.insets = new Insets(40, 50, 0, 0);
    this.add(amountLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 2, 0.7, 0);
    constraints.insets = new Insets(40, 50, 0, 10);
    amountTextField.setBorder(BorderFactory.
            createTitledBorder("Ex: 100.00"));
    this.add(amountTextField, constraints);
  }

  // view setup for dateOfPurchase
  private void setDateOfPurchaseView() {

    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 3, 0.3, 0);
    constraints.insets = new Insets(40, 50, 0, 0);
    this.add(dateOfPurchaseLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 3, 0.7, 0);
    constraints.insets = new Insets(40, 50, 0, 10);
    dateOfPurchaseTextField.setBorder(BorderFactory.
            createTitledBorder("Ex: dd-MM-YYYY HH:mm:ss (24hr format)"));
    this.add(dateOfPurchaseTextField, constraints);

  }

  // view setup for combo box
  private void setComboBoxView() {

    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 4, 0.1, 0);
    constraints.insets = new Insets(40, 50, 0, 0);
    this.add(commissionComboBoxLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 1, 4, 0.6, 0.2);
    constraints.insets = new Insets(40, 0, 0, -10);
    this.add(commissionBox, constraints);
    commissionBox.setSelectedIndex(1);
  }

  // view setup for commission fees
  private void setCommissionFeesView() {

    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 5, 0.3, 0);
    constraints.insets = new Insets(40, 50, 0, 0);
    this.add(commissionLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 5, 0.7, 0);
    constraints.insets = new Insets(40, 50, 0, 10);
    commissionTextField.setBorder(BorderFactory.
            createTitledBorder("Ex: 100.00"));
    this.add(commissionTextField, constraints);

    commissionLabel.setVisible(false);
    commissionTextField.setVisible(false);
  }

  // view setup for buy button
  private void setBuyButtonView() {

    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.PAGE_END, 2, 7, 0.6, 0);
    constraints.insets = new Insets(10, 0, 10, -10);
    this.add(buyButton, constraints);
  }


  /**
   * Method that validates all the text fields in the panel.
   *
   * @return true, if the input is valid, otherwise false.
   */
  public boolean validateDataEntry() {
    boolean dataValid;
    dataValid = validatePortfolioField() && validateTickerSymbol() &&
            validateAmount() && validateDateOfPurchase();
    if (Objects.requireNonNull(commissionBox.getSelectedItem()).
            toString().equals("Yes")) {
      return validateCommission() && dataValid;
    } else {
      return dataValid;
    }
  }

  private boolean validateCommission() {

    String amount = commissionTextField.getText();
    try {
      Integer.parseInt(amount);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Enter valid amount",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
      return false;
    }
    return true;
  }

  private boolean validateAmount() {

    String amount = amountTextField.getText();
    try {
      Integer.parseInt(amount);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Enter valid amount",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
      return false;
    }
    return true;
  }

  private boolean validateDateOfPurchase() {

    String date = dateOfPurchaseTextField.getText();
    if (DateUtility.convertStringFromUserToDate(date) == null) {
      JOptionPane.showMessageDialog(this, "Enter valid date",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
      return false;
    }
    return true;
  }

  private boolean validateTickerSymbol() {

    String text = tickerSymbolTextField.getText();
    if (text.equals("")) {
      JOptionPane.showMessageDialog(this, "Enter ticker symbol",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
      return false;
    }
    return true;
  }

  private boolean validatePortfolioField() {

    boolean result = ViewUtils.validatePortfolioNameInput(portfolioNameTextField.getText());
    if (!result) {
      JOptionPane.showMessageDialog(this, "Enter valid portfolio name",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
    return result;
  }

  /**
   * Method that sets the action listener for buy button.
   *
   * @param listener ActionListener for the button action event.
   */
  public void setActionListener(ActionListener listener) {

    new Thread(new Runnable() {
      @Override
      public void run() {

        buyButton.addActionListener(listener);
      }
    }).start();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String componentName = e.getActionCommand();
    if (componentName.equals("Combo Box")) {
      String option = Objects.requireNonNull(commissionBox.getSelectedItem()).toString();
      showCommissionBasedOnComboBox(option);
    }
  }

  private void showCommissionBasedOnComboBox(String option) {
    switch (option) {
      case "Yes":
        commissionLabel.setVisible(true);
        commissionTextField.setVisible(true);
        commissionFlag = true;
        break;
      case "No":
        commissionLabel.setVisible(false);
        commissionTextField.setVisible(false);
        commissionFlag = false;
        break;
      default:
        break;
    }
  }

  /**
   * Method that resets the all the JComponents within the panel.
   */
  public void resetData() {

    portfolioNameTextField.setText("");
    tickerSymbolTextField.setText("");
    amountTextField.setText("");
    dateOfPurchaseTextField.setText("");
    commissionTextField.setText("");
    commissionBox.setSelectedIndex(1);
  }
}
