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

import model.IPortfolioInfo;
import util.ViewUtils;

/**
 * This class represents the view for save/retrieve info tab in the stock tutor GUI. The class
 * provides methods to setup the view and provides methods to validate all the input text fields
 * within the save/retrieve panel.
 */
public class SaveAndRetrievePanel extends JPanel {

  private final JTextField textField;
  private final JButton openPortfolioButton;
  private final JButton savePortfolioButton;
  private final JLabel portfolioName;

  private final JLabel portfolioInfo;
  private final JTextArea portfolioInfoArea;

  private final JLabel openFile;

  /**
   * Constructor for save/retrieve panel that initializes all the labels, text fields and buttons
   * that are used within this panel. It also calls methods to setup the initial view.
   */
  SaveAndRetrievePanel() {

    this.setBackground(Color.white);
    textField = new JTextField(100);
    textField.setText("");
    savePortfolioButton = new JButton("Save portfolio");
    openPortfolioButton = new JButton("Open portfolio");
    portfolioName = new JLabel("Enter portfolio name");
    portfolioInfo = new JLabel("");
    portfolioInfoArea = new JTextArea("");
    portfolioInfoArea.setEditable(false);
    portfolioInfoArea.setAutoscrolls(true);
    openFile = new JLabel("Open File: ");
    this.setLayout(new GridBagLayout());

    setPanelView();
  }

  /**
   * Getter method that fetches the JTextField within the panel.
   *
   * @return textField used to enter portfolio name.
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
    this.add(portfolioName, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 0, 0.7, 0);
    constraints.insets = new Insets(100, 0, 0, 100);
    this.add(textField, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 0, 1, 0.3, 0);
    constraints.insets = new Insets(50, 0, 10, 10);
    constraints.gridwidth = 2;
    this.add(savePortfolioButton, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 2, 0.3, 0);
    constraints.gridwidth = 2;
    constraints.insets = new Insets(100, 0, 10, 10);
    constraints.fill = GridBagConstraints.HORIZONTAL;
    this.add(new JSeparator(JSeparator.HORIZONTAL),constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.LINE_START, 0, 3, 0.3, 0);
    constraints.insets = new Insets(100, 0, 10, 10);
    this.add(openFile, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.LINE_START, 0, 3, 0.3, 0);
    constraints.gridwidth = 2;
    constraints.insets = new Insets(100, 0, 10, 10);
    this.add(openPortfolioButton, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 0, 4, 0, 0);
    constraints.ipady = 0;
    constraints.insets = new Insets(30, 0, 0, 0);
    constraints.gridwidth = 2;
    this.add(portfolioInfo, constraints);

    constraints = ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 0, 5, 0, 0.6);
    constraints.ipady = 2;
    constraints.gridwidth = 3;
    constraints.insets = new Insets(0, 0, 10, 0);
    this.add(portfolioInfoArea, constraints);

    JLabel filler = new JLabel();
    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 0, 6, 0, 0.7);
    constraints.ipady = 0;
    constraints.gridwidth = 2;
    this.add(filler, constraints);
  }

  /**
   * Method that sets button listener for save portfolio button with this panel.
   *
   * @param listener ActionListener for the total value button action event.
   */
  public void setActionListenerForSavePortfolioToFile(ActionListener listener) {

    savePortfolioButton.addActionListener(listener);
  }

  /**
   * Method that sets button listener for get portfolio button with this panel.
   *
   * @param listener ActionListener for the total value button action event.
   */
  public void setActionListenerForGetPortfolioFromFile(ActionListener listener) {

    openPortfolioButton.addActionListener(listener);
  }

  /**
   * Method that displays portfolio information in the panel.
   *
   * @param portfolio object to be displayed.
   */
  public void displayPortfolioInfo(IPortfolioInfo portfolio) {

    StringBuilder title = new StringBuilder();
    title.append("<html><h3>").append(portfolio.getPortfolioName()).append("</h3></html>");
    portfolioInfo.setText(title.toString());
    portfolioInfoArea.setText(ViewUtils.constructPortfolioInfoStringForGUI(portfolio));
  }

  /**
   * Method that validates all the text fields in the panel.
   *
   * @return true, if the input is valid, otherwise false.
   */
  public boolean validateData() {

    boolean result = ViewUtils.validatePortfolioNameInput(textField.getText());
    if (!result) {
      JOptionPane.showMessageDialog(this, "Enter valid portfolio name",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
    return result;
  }
}