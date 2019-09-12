package view.tabs;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import util.DateUtility;
import util.ViewUtils;

/**
 * This class represents the view for investment strategy tab in the stock tutor GUI. The class
 * provides methods to setup the view and provides methods to validate all the input text fields
 * within the investment strategy panel.
 */

public class InvestmentStrategyPanel extends JPanel implements ActionListener {

  private final JTextField strategyNameTextField;
  private final JLabel strategyNameLabel;

  private final JTextField portfolioNameTextField;
  private final JLabel portfolioNameLabel;

  private final JComboBox<Integer> stocksCountComboBox;
  private final JLabel stocksCountComboBoxLabel;

  private final JComboBox<String> strategyTypeComboBox;
  private final JLabel strategyTypeLabel;

  private final JTextField amountTextField;
  private final JLabel amountLabel;

  private final JTextField frequencyTextField;
  private final JLabel frequencyLabel;

  private final JTextField startDateTextField;
  private final JLabel startDateLabel;

  private final JTextField endDateTextField;
  private final JLabel endDateLabel;

  private final JTextField commissionTextField;
  private final JLabel commissionLabel;

  private final JComboBox<String> commissionBox;
  private final JLabel commissionComboBoxLabel;

  private int countOfStocks;

  private final JPanel scrollablePanel;
  private final JScrollPane scrollPane;

  private final Map<JTextField, JTextField> dollarCostMap;
  private final Map<JTextField, JTextField> customWeightMap;

  private final List<JTextField> equalWeightsList;

  private final JButton applyStrategyButton;


  /**
   * Constructor for investment strategy panel that initializes all the labels, text fields and
   * buttons that are used within this panel. It also calls methods to setup the initial view.
   */
  InvestmentStrategyPanel() {

    this.setBackground(Color.white);

    strategyNameLabel = new JLabel("Strategy Name");
    strategyNameTextField = new JTextField(15);
    strategyNameTextField.setActionCommand("Text Field");

    portfolioNameLabel = new JLabel("Portfolio Name");
    portfolioNameTextField = new JTextField(15);
    portfolioNameTextField.setActionCommand("Text Field");

    stocksCountComboBoxLabel = new JLabel("Select no. of Stocks");
    stocksCountComboBox = new JComboBox<>();
    for (int i = 0; i < 50; i++) {
      stocksCountComboBox.addItem(new Integer(i + 1));
    }
    stocksCountComboBox.addActionListener(this);
    stocksCountComboBox.setActionCommand("Stock Combo Box");

    String[] comboValues = {"Custom Weights NonRecurring", "Equal Weights NonRecurring", "Dollar " +
                          "Cost Averaging"};
    strategyTypeLabel = new JLabel("Select strategy type");
    strategyTypeComboBox = new JComboBox(comboValues);
    strategyTypeComboBox.addActionListener(this);
    strategyTypeComboBox.setActionCommand("Strategy Combo Box");

    amountLabel = new JLabel("Amount($)");
    amountTextField = new JTextField(7);
    amountTextField.setActionCommand("Text Field");

    frequencyLabel = new JLabel("Enter frequency as no. of days");
    frequencyTextField = new JTextField(10);
    frequencyTextField.setActionCommand("Text Field");


    startDateLabel = new JLabel("Start Date");
    startDateTextField = new JTextField(10);
    startDateTextField.setActionCommand("Text Field");

    endDateLabel = new JLabel("End Date");
    endDateTextField = new JTextField(5);
    endDateTextField.setActionCommand("Text Field");

    commissionLabel = new JLabel("Commission Fees($)");
    commissionTextField = new JTextField(5);
    commissionTextField.setActionCommand("Text Field");

    String[] commissionValues = {"Yes", "No"};
    commissionComboBoxLabel = new JLabel("Enter Commission?");
    commissionBox = new JComboBox<>(commissionValues);
    commissionBox.setActionCommand("Commission Combo Box");
    commissionBox.addActionListener(this);

    scrollablePanel = new JPanel();
    scrollPane = new JScrollPane(scrollablePanel);

    applyStrategyButton = new JButton("Apply");
    applyStrategyButton.setActionCommand("Apply Strategy");

    dollarCostMap = new HashMap<>();
    customWeightMap = new HashMap<>();
    equalWeightsList = new ArrayList<>();

    this.setLayout(new GridBagLayout());
    this.setPanelView();
  }

  /**
   * Getter method that fetches strategyNameTextField.
   *
   * @return strategyNameTextField used to enter strategy name.
   */
  public JTextField getStrategyNameTextField() {
    return strategyNameTextField;
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
   * Getter method that fetches amountTextField.
   *
   * @return amountTextField used to enter amount.
   */
  public JTextField getAmountTextField() {
    return amountTextField;
  }

  /**
   * Getter method that fetches frequencyTextField.
   *
   * @return frequencyTextField used to enter frequency of strategy to be applied.
   */
  public JTextField getFrequencyTextField() {
    return frequencyTextField;
  }

  /**
   * Getter method that fetches startDateTextField.
   *
   * @return startDateTextField used to enter start date of strategy.
   */
  public JTextField getStartDateTextField() {
    return startDateTextField;
  }

  /**
   * Getter method that fetches endDateTextField.
   *
   * @return endDateTextField used to enter end date of strategy.
   */
  public JTextField getEndDateTextField() {
    return endDateTextField;
  }

  /**
   * Getter method that fetches commissionTextField.
   *
   * @return commissionTextField used to commission fees for a strategy.
   */
  public JTextField getCommissionTextField() {
    return commissionTextField;
  }

  /**
   * Method that fetches dollarCostMap which is map of JTextfields of tickerSymbol and weights.
   *
   * @return dollarCostMap which is map of JTextfields of tickerSymbol and weights
   */
  public Map<JTextField, JTextField> getDollarCostMap() {
    return dollarCostMap;
  }

  /**
   * Method that fetches customWeightMap which is map of JTextfields of tickerSymbol and weights.
   *
   * @return customWeightMap which is map of JTextfields of tickerSymbol and weights
   */
  public Map<JTextField, JTextField> getCustomWeightMap() {
    return customWeightMap;
  }

  /**
   * Method that fetches list of JTextField objects containing ticker symbols for creating equal
   * weights strategy.
   *
   * @return list of JTextField objects.
   */
  public List<JTextField> getEqualWeightsList() {
    return equalWeightsList;
  }

  /**
   * Method that fetches strategyTypeComboBox which decides the type of strategy.
   *
   * @return JCombox which holds information on the strategy type.
   */
  public JComboBox<String> getStrategyTypeComboBox() {
    return strategyTypeComboBox;
  }

  @Override
  public void paint(Graphics g) {

    super.paint(g);

  }

  /**
   * Sets the action listener to the apply strategy button.
   * @param listener The ActionListern object to be set to the button.
   */
  public void setActionListener(ActionListener listener) {

    new Thread(new Runnable() {
      @Override
      public void run() {

        applyStrategyButton.addActionListener(listener);
      }
    }).start();
  }


  private void setPanelView() {

    setStrategyNameView();
    setPortfolioNameView();
    setCountComboBoxView();
    setStrategyTypeComboBoxView();


    setAmountView();
    setFrequencyView();
    setStartDateView();
    setEndDateView();

    setCommissionComboBoxView();
    setCommissionFeesAreaView();

    setScrollablePanelView();
    setApplyStrategyButtonView();

  }

  private void setStrategyNameView() {
    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 0.1, 0.05);
    constraints.insets = new Insets(0, 100, 0, 0);
    this.add(strategyNameLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 0, 0, 0.05);
    constraints.insets = new Insets(0, -50, 0, 100);
    strategyNameTextField.setBorder(BorderFactory.
            createTitledBorder("(A-Z, a-z , 0-9 are only allowed)"));
    this.add(strategyNameTextField, constraints);
  }

  private void setPortfolioNameView() {
    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 1, 0.1, 0.05);
    constraints.insets = new Insets(0, 100, 0, 0);
    this.add(portfolioNameLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 1, 0.7, 0.05);
    constraints.insets = new Insets(0, -50, 0, 100);
    portfolioNameTextField.setBorder(BorderFactory.
            createTitledBorder("(A-Z, a-z , 0-9 are only allowed)"));
    this.add(portfolioNameTextField, constraints);
  }

  private void setCountComboBoxView() {
    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 2, 0.1, 0.02);
    constraints.insets = new Insets(0, 100, 0, 0);
    this.add(stocksCountComboBoxLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 1, 2, 0.6, 0.02);
    constraints.insets = new Insets(0, -50, 0, 100);
    this.add(stocksCountComboBox, constraints);
    stocksCountComboBox.setSelectedIndex(0);
  }

  private void setStrategyTypeComboBoxView() {
    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 3, 0.1, 0.05);
    constraints.insets = new Insets(0, 100, 0, 0);
    this.add(strategyTypeLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 1, 3, 0.6, 0.05);
    constraints.insets = new Insets(0, -50, 0, 100);
    this.add(strategyTypeComboBox, constraints);
    strategyTypeComboBox.setSelectedIndex(1);
  }


  private void setAmountView() {
    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 4, 0.3, 0.02);
    constraints.insets = new Insets(0, 100, 0, 0);
    this.add(amountLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 4, 0.7, 0.02);
    constraints.insets = new Insets(0, -50, 0, 100);
    amountTextField.setBorder(BorderFactory.
            createTitledBorder("Ex: 100.00"));
    this.add(amountTextField, constraints);
  }

  private void setFrequencyView() {
    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 5, 0.3, 0.05);
    constraints.insets = new Insets(0, 100, 0, 0);
    this.add(frequencyLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 5, 0.7, 0.05);
    constraints.insets = new Insets(0, -50, 0, 100);
    frequencyTextField.setBorder(BorderFactory.
            createTitledBorder("Ex: 30"));
    this.add(frequencyTextField, constraints);

  }

  private void setStartDateView() {
    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 6, 0.3, 0.05);
    constraints.insets = new Insets(0, 100, 0, 0);
    this.add(startDateLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 6, 0.7, 0.05);
    constraints.insets = new Insets(0, -50, 0, 100);
    startDateTextField.setBorder(BorderFactory.
            createTitledBorder("Ex: dd-MM-YYYY"));
    this.add(startDateTextField, constraints);

  }

  private void setEndDateView() {
    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 7, 0.3, 0.05);
    constraints.insets = new Insets(0, 100, 0, 0);
    this.add(endDateLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 7, 0.7, 0.05);
    constraints.insets = new Insets(0, -50, 0, 100);
    endDateTextField.setBorder(BorderFactory.
            createTitledBorder("Ex: dd-MM-YYYY"));
    this.add(endDateTextField, constraints);
  }

  // view setup for combo box
  private void setCommissionComboBoxView() {

    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 8, 0.1, 0.05);
    constraints.insets = new Insets(0, 100, 0, 0);
    this.add(commissionComboBoxLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 1, 8, 0.6, 0.05);
    constraints.insets = new Insets(0, -50, 0, 100);
    this.add(commissionBox, constraints);
    commissionBox.setSelectedIndex(1);
  }

  // view setup for commission fees
  private void setCommissionFeesAreaView() {

    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 9, 0.3, 0.05);
    constraints.insets = new Insets(0, 100, 0, 0);
    this.add(commissionLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 9, 0.7, 0.05);
    constraints.insets = new Insets(0, -50, 0, 100);
    commissionTextField.setBorder(BorderFactory.
            createTitledBorder("Ex: 100.00"));
    this.add(commissionTextField, constraints);

    commissionLabel.setVisible(false);
    commissionTextField.setVisible(false);
  }

  private void setScrollablePanelView() {

    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 0, 11, 0.3, .3);
    constraints.insets = new Insets(0, 100, 0, 100);
    constraints.fill = GridBagConstraints.BOTH;
    scrollablePanel.setBorder(BorderFactory.
            createTitledBorder("Enter Stocks and Weights (weights should sum up to 100"));
    scrollablePanel.setLayout(new GridBagLayout());
    scrollPane.setVisible(true);
    constraints.gridwidth = 2;
    this.add(scrollPane, constraints);
  }

  private void setApplyStrategyButtonView() {
    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.PAGE_END, 1, 12, 0, 0);
    constraints.insets = new Insets(0, 150, 0, 0);
    this.add(applyStrategyButton, constraints);
  }


  private void showEqualWeightsPanel() {
    equalWeightsList.clear();
    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 0, 0);
    for (int i = 0; i < countOfStocks; i++) {
      constraints.gridy = i;
      JTextField stockField = new JTextField(8);
      stockField.setBorder(BorderFactory.createTitledBorder("ticker " + String.valueOf(i + 1)));
      scrollablePanel.add(stockField, constraints);
      equalWeightsList.add(stockField);
    }
  }

  private void showDollarCostPanel() {
    dollarCostMap.clear();
    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 0, 0);
    for (int i = 0; i < countOfStocks; i++) {
      constraints.gridx = 0;
      constraints.gridy = i;
      JTextField stockField = new JTextField(8);
      stockField.setBorder(BorderFactory.createTitledBorder("ticker " + String.valueOf(i + 1)));
      scrollablePanel.add(stockField, constraints);

      constraints.gridx = 1;
      constraints.gridy = i;
      JTextField weightField = new JTextField(8);
      weightField.setBorder(BorderFactory.createTitledBorder("weight " + String.valueOf(i + 1)));
      scrollablePanel.add(weightField, constraints);

      // Add ticker and weight pair to map
      dollarCostMap.put(stockField, weightField);
    }
  }


  private void showCustomWeightPanel() {
    customWeightMap.clear();
    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 0, 0);
    for (int i = 0; i < countOfStocks; i++) {
      constraints.gridx = 0;
      constraints.gridy = i;
      JTextField stockField = new JTextField(8);
      stockField.setBorder(BorderFactory.createTitledBorder("ticker " + String.valueOf(i + 1)));
      scrollablePanel.add(stockField, constraints);

      constraints.gridx = 1;
      constraints.gridy = i;
      JTextField weightField = new JTextField(8);
      weightField.setBorder(BorderFactory.createTitledBorder("weight " + String.valueOf(i + 1)));
      scrollablePanel.add(weightField, constraints);

      //Add ticker and weight pair to map
      customWeightMap.put(stockField, weightField);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String componentName = e.getActionCommand();
    switch (componentName) {
      case "Stock Combo Box": {
        String option = Objects.requireNonNull(stocksCountComboBox.getSelectedItem()).toString();
        countOfStocks = Integer.valueOf(option);
        break;
      }
      case "Strategy Combo Box": {
        String option = Objects.requireNonNull(strategyTypeComboBox.getSelectedItem()).toString();
        //setScrollablePanelView();
        showStocksAndWeightsPanel(option);
        break;
      }
      case "Commission Combo Box": {
        String option = Objects.requireNonNull(commissionBox.getSelectedItem()).toString();
        showCommissionBasedOnComboBox(option);
        break;
      }
      default: break;
    }
  }

  private void showStocksAndWeightsPanel(String option) {
    switch (option) {
      case "Custom Weights NonRecurring":
        scrollablePanel.removeAll();
        scrollablePanel.revalidate();
        showCustomWeightPanel();
        this.endDateTextField.setVisible(false);
        this.frequencyTextField.setVisible(false);
        this.endDateLabel.setVisible(false);
        this.frequencyLabel.setVisible(false);

        this.revalidate();
        this.repaint();
        scrollPane.setVisible(true);
        break;
      case "Equal Weights NonRecurring":
        scrollablePanel.removeAll();
        scrollablePanel.revalidate();
        showEqualWeightsPanel();
        this.endDateTextField.setVisible(false);
        this.frequencyTextField.setVisible(false);
        this.endDateLabel.setVisible(false);
        this.frequencyLabel.setVisible(false);

        this.revalidate();
        this.repaint();
        scrollPane.setVisible(true);
        break;
      case "Dollar Cost Averaging":
        scrollablePanel.removeAll();
        scrollablePanel.revalidate();
        showDollarCostPanel();
        this.endDateTextField.setVisible(true);
        this.frequencyTextField.setVisible(true);
        this.endDateLabel.setVisible(true);
        this.frequencyLabel.setVisible(true);

        this.revalidate();
        this.repaint();
        scrollPane.setVisible(true);
        break;
      default:
        break;
    }
  }


  // Commission box
  private void showCommissionBasedOnComboBox(String option) {
    switch (option) {
      case "Yes":
        commissionLabel.setVisible(true);
        commissionTextField.setVisible(true);
        break;
      case "No":
        commissionLabel.setVisible(false);
        commissionTextField.setVisible(false);
        break;
      default:
        break;
    }
  }


  // Validate fields

  /**
   * Method that validates all the text fields in the panel.
   *
   * @return true, if the input is valid, otherwise false.
   */
  public boolean validateDataEntry() {
    boolean dataValid;
    dataValid = (validateStrategyField() && validatePortfolioField()
            && validateAmount() && validateStartDate());
    if (!dataValid) {
      return false;
    }
    if (Objects.requireNonNull(commissionBox.getSelectedItem())
            .toString().equals("Yes")) {
      dataValid = dataValid && validateCommission();
    }
    if (!dataValid) {
      return false;
    }
    if (Objects.requireNonNull(strategyTypeComboBox.getSelectedItem()).
            toString().equals("Equal Weights NonRecurring")) {
      return validateEqualWeightMap() && dataValid;
    } else if (Objects.requireNonNull(strategyTypeComboBox.getSelectedItem()).
            toString().equals("Dollar Cost Averaging")) {
      return validateDollarCostMap() && dataValid && validateFrequency() && validateEndDate();
    } else {
      return validateCustomWeightMap() && dataValid;
    }
  }

  private boolean validateStrategyField() {

    boolean result = ViewUtils.validateStrategyNameInput(strategyNameTextField.getText());
    if (!result) {
      JOptionPane.showMessageDialog(this, "Enter valid strategy name",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
    return result;
  }

  private boolean validatePortfolioField() {

    boolean result = ViewUtils.validatePortfolioNameInput(portfolioNameTextField.getText());
    if (!result) {
      JOptionPane.showMessageDialog(this, "Enter valid portfolio name",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
    return result;
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

  private boolean validateFrequency() {

    String amount = amountTextField.getText();
    try {
      Integer.parseInt(amount);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Enter valid frequency",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
      return false;
    }
    return true;
  }

  private boolean validateStartDate() {

    String date = startDateTextField.getText().concat(" 12:00:00");
    if (DateUtility.convertStringFromUserToDate(date) == null) {
      JOptionPane.showMessageDialog(this, "Enter valid date",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
      return false;
    }
    return true;
  }

  private boolean validateEndDate() {

    String date = endDateTextField.getText().concat(" 12:00:00");
    if (DateUtility.convertStringFromUserToDate(date) == null) {
      JOptionPane.showMessageDialog(this, "Enter valid date",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
      return false;
    }
    return true;
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

  private boolean validateEqualWeightMap() {
    for (JTextField stockObj : equalWeightsList) {
      if (stockObj.getText().equals("")) {
        JOptionPane.showMessageDialog(this, "Ticker symbol cannot be empty",
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        return false;
      }
    }
    return true;
  }

  private boolean validateCustomWeightMap() {

    float sum = 0;
    for (Map.Entry<JTextField, JTextField> entry : customWeightMap.entrySet()) {
      try {
        Float num = Float.parseFloat(entry.getValue().getText());
        sum += num;
        if (entry.getKey().getText().equals("")) {
          JOptionPane.showMessageDialog(this, "Ticker symbol cannot be empty",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid weight entered",
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
      }
    }
    if (sum != 100) {
      JOptionPane.showMessageDialog(this, "Weights don't sum to 100",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
      return false;
    } else {
      return true;
    }
  }

  private boolean validateDollarCostMap() {

    float sum = 0;
    for (Map.Entry<JTextField, JTextField> entry : dollarCostMap.entrySet()) {
      try {
        Float num = Float.parseFloat(entry.getValue().getText());
        sum += num;
        if (entry.getKey().getText().equals("")) {
          JOptionPane.showMessageDialog(this, "Ticker symbol cannot be empty",
                  "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid weight entered",
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
      }
    }
    if (sum != 100) {
      JOptionPane.showMessageDialog(this, "Weights don't sum to 100",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
      return false;
    } else {
      return true;
    }
  }

  /**
   * Method that resets the all the JComponents within the panel.
   */
  public void resetData() {

    strategyNameTextField.setText("");
    portfolioNameTextField.setText("");
    strategyTypeComboBox.setSelectedIndex(0);
    stocksCountComboBox.setSelectedIndex(0);
    amountTextField.setText("");
    frequencyTextField.setText("");
    startDateTextField.setText("");
    endDateTextField.setText("");
    commissionTextField.setText("");
    commissionBox.setSelectedIndex(0);
  }
}


