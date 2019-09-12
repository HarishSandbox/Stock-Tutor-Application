package view.tabs;


import java.awt.event.ActionListener;
import java.util.List;

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

import model.IPortfolioInfo;
import util.ViewUtils;

/**
 * This class represents the view for portfolio info tab in the stock tutor GUI. The class provides
 * methods to setup the view and provides methods to validate all the input text fields within the
 * portfolio info panel.
 */
public class PortfolioInfoPanel extends JPanel {

  private final JTextField portfolioNameTextField;
  private final JLabel portfolioNameLabel;

  private final JButton getAllPortfoliosButton;
  private final JButton getPortfolioButton;
  private final JLabel portfolioInfoLabel;
  private final JTextArea portfolioInfoArea;

  /**
   * Constructor for portfolio info panel that initializes all the labels, text fields and buttons
   * that are used within this panel. It also calls methods to setup the initial view.
   */
  PortfolioInfoPanel() {

    this.setBackground(Color.white);
    portfolioNameLabel = new JLabel();
    portfolioNameTextField = new JTextField(15);

    getAllPortfoliosButton = new JButton("Get All Portfolios");
    getPortfolioButton = new JButton("Get Portfolio");
    portfolioInfoLabel = new JLabel("");
    portfolioInfoArea = new JTextArea("");
    portfolioInfoArea.setEditable(false);
    portfolioInfoArea.setAutoscrolls(true);

    this.setLayout(new GridBagLayout());
    this.setPanelView();
  }

  /**
   * Getter method that fetches the JTextField within the panel.
   *
   * @return textField used to enter portfolio name.
   */
  public JTextField getPortfolioNameTextField() {
    return portfolioNameTextField;
  }


  @Override
  public void paint(Graphics g) {

    super.paint(g);
    setPanelView();
  }

  private void setPanelView() {

    GridBagConstraints constraints = new GridBagConstraints();
    setPortfolioView(constraints);
    setAllPortfoliosView(constraints);
    setPortfolioInfoView(constraints);
  }

  private void setPortfolioView(GridBagConstraints constraints) {

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 0, 1, 0.3, 0);
    constraints.insets = new Insets(100, 50, 0, 0);
    portfolioNameLabel.setText("Enter the portfolio name to be fetched");
    this.add(portfolioNameLabel, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.HORIZONTAL, 1, 1, 0.7, 0);
    constraints.insets = new Insets(100, 0, 0, 50);
    this.add(portfolioNameTextField, constraints);

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 0, 2, 0.7, 0);
    constraints.insets = new Insets(40, 0, 0, 10);
    constraints.gridwidth = 2;
    this.add(getPortfolioButton, constraints);
  }

  private void setAllPortfoliosView(GridBagConstraints constraints) {

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 0, 3, 0.7, 0);
    constraints.insets = new Insets(50, 0, 0, 10);
    constraints.gridwidth = 2;
    this.add(getAllPortfoliosButton, constraints);
  }

  private void setPortfolioInfoView(GridBagConstraints constraints) {

    constraints =
            ViewUtils.makeGridBagConstraints(GridBagConstraints.CENTER, 0, 4, 0, 0);
    constraints.ipady = 0;
    constraints.insets = new Insets(30, 0, 0, 0);
    constraints.gridwidth = 2;
    this.add(portfolioInfoLabel, constraints);

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
   * Method that displays the portfolio data within the panel.
   *
   * @param portfolio the portfolio object whose data has to be set.
   */
  public void setPortfolioData(IPortfolioInfo portfolio) {

    StringBuilder title = new StringBuilder();
    title.append("<html><h3>").append(portfolio.getPortfolioName()).append("</h3></html>");
    portfolioInfoLabel.setText(title.toString());
    portfolioInfoArea.setText(ViewUtils.constructPortfolioInfoStringForGUI(portfolio));
  }

  /**
   * Method that displays the portfolio details within the panel.
   *
   * @param portfolios the list of portfolios.
   */
  public void setPortfolioListData(List<IPortfolioInfo> portfolios) {

    StringBuilder title = new StringBuilder();
    title.append("<html><h3>").append("All Portfolios").append("</h3></html>");
    portfolioInfoLabel.setText(title.toString());

    if (portfolios.size() == 0) {

      portfolioInfoArea.setText("No Portfolios present");
      return;
    }
    StringBuilder listString = new StringBuilder();
    for (IPortfolioInfo item : portfolios) {

      listString.append(item.getPortfolioName()).append("\n\n");
    }
    portfolioInfoArea.setText(listString.toString());
  }

  /**
   * Method that resets the JComponents within the panel.
   */
  public void resetPortfolioData() {

    portfolioInfoLabel.setText("");
    portfolioInfoArea.setText("");
  }

  /**
   * Method that resets the portfolioNameTextField within the panel.
   */
  public void resetPortfolioTextFieldValue() {

    this.portfolioNameTextField.setText("");
  }

  /**
   * Method that validates all the text fields in the panel.
   *
   * @return true, if the input is valid, otherwise false.
   */
  public boolean validateData() {

    boolean result = ViewUtils.validatePortfolioNameInput(portfolioNameTextField.getText());
    if (!result) {
      JOptionPane.showMessageDialog(this, "Enter valid portfolio name",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }
    return result;
  }

  /**
   * Method that sets all button listeners with this panel.
   *
   * @param listener ActionListener for the total value button action event.
   */
  public void setActionListeners(ActionListener listener) {

    getPortfolioButton.addActionListener(listener);
  }

  /**
   * Method that sets all button listeners with this panel.
   *
   * @param listener ActionListener for the total value button action event.
   */
  public void setGetAllPortfoliosButtonActionListener(ActionListener listener) {

    getAllPortfoliosButton.addActionListener(listener);
  }
}