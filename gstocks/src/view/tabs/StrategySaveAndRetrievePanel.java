package view.tabs;

import java.awt.event.ActionListener;

import java.awt.Insets;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JSeparator;

import strategies.InvestmentStrategy;
import util.ViewUtils;

/**
 * This class represents the view for save/retrieve strategy info tab in the stock tutor GUI. The
 * class provides methods to setup the view and provides methods to validate all the input text
 * fields within the panel.
 */
public class StrategySaveAndRetrievePanel extends JPanel {

  private final JTextField textField;
  private final JButton openStrategyButton;
  private final JButton saveStrategyButton;
  private final JLabel strategyName;

  private final JLabel strategyInfo;
  private final JTextArea strategyInfoArea;

  private final JLabel openFile;

  /**
   * Constructor for strategy save/retrieve panel that initializes all the labels, text fields and
   * buttons that are used within this panel. It also calls methods to setup the initial view.
   */
  StrategySaveAndRetrievePanel() {

    this.setBackground(Color.white);
    textField = new JTextField(100);
    textField.setText("");
    saveStrategyButton = new JButton("Save strategy");
    openStrategyButton = new JButton("Open strategy");
    strategyName = new JLabel("Enter strategy name");
    strategyInfo = new JLabel("");
    strategyInfoArea = new JTextArea("");
    strategyInfoArea.setEditable(false);
    strategyInfoArea.setAutoscrolls(true);
    openFile = new JLabel("Open File: ");
    this.setLayout(new GridBagLayout());

    setPanelView();
  }

  /**
   * Getter method that fetches the JTextField within the panel.
   *
   * @return textField used to enter strategy name.
   */
  public JTextField getTextField() {
    return textField;
  }

  @Override
  public void paint(Graphics g) {

    super.paint(g);
    setPanelView();
  }

  private void setPanelView() {

    GridBagConstraints constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 0, 0.3, 0);
    constraints.insets = new Insets(100, 50, 0, -50);
    this.add(strategyName, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 0, 0.7, 0);
    constraints.insets = new Insets(100, 0, 0, 100);
    this.add(textField, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 0, 1, 0.3, 0);
    constraints.insets = new Insets(50, 0, 10, 10);
    constraints.gridwidth = 2;
    this.add(saveStrategyButton, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 2, 0.3, 0);
    constraints.gridwidth = 2;
    constraints.insets = new Insets(100, 0, 10, 10);
    constraints.fill = GridBagConstraints.HORIZONTAL;
    this.add(new JSeparator(JSeparator.HORIZONTAL), constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.LINE_START, 0, 3, 0.3, 0);
    constraints.insets = new Insets(100, 0, 10, 10);
    this.add(openFile, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.LINE_START, 0, 3, 0.3, 0);
    constraints.gridwidth = 2;
    constraints.insets = new Insets(100, 0, 10, 10);
    this.add(openStrategyButton, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 0, 4, 0, 0);
    constraints.ipady = 0;
    constraints.insets = new Insets(30, 0, 0, 0);
    constraints.gridwidth = 2;
    this.add(strategyInfo, constraints);

    constraints = ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 0, 5, 0, 0.6);
    constraints.ipady = 2;
    constraints.gridwidth = 3;
    constraints.insets = new Insets(0, 0, 10, 0);
    this.add(strategyInfoArea, constraints);

    JLabel filler = new JLabel();
    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 0, 6, 0, 0.7);
    constraints.ipady = 0;
    constraints.gridwidth = 2;
    this.add(filler, constraints);
  }

  /**
   * Method that sets button listener for save strategy button with this panel.
   *
   * @param listener ActionListener for the save strategy button action event.
   */
  public void setActionListenerForSaveStrategyToFile(ActionListener listener) {

    saveStrategyButton.addActionListener(listener);
  }

  /**
   * Method that sets button listener for get strategy button with this panel.
   *
   * @param listener ActionListener for the get strategy button action event.
   */
  public void setActionListenerForGetStrategyFromFile(ActionListener listener) {

    openStrategyButton.addActionListener(listener);
  }

  /**
   * Method that displays strategy information in the panel.
   *
   * @param strategy object to be displayed.
   */
  public void displayStrategyInfo(InvestmentStrategy strategy) {

    StringBuilder title = new StringBuilder();
    title.append("<html><h3>").append(strategy.getStrategyName()).append("</h3></html>");
    strategyInfo.setText(title.toString());
    strategyInfoArea.setText(ViewUtils.constructStrategyInfoStringForGUI(strategy));
  }

  /**
   * Method that validates all the text fields in the panel.
   *
   * @return true, if the input is valid, otherwise false.
   */
  public boolean validateData() {

    boolean result = ViewUtils.validateStrategyNameInput(textField.getText());
    if (!result) {
      JOptionPane.showMessageDialog(this, "Enter valid strategy name",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
    return result;
  }
}