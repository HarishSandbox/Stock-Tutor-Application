package view;

import java.io.File;
import java.util.Date;
import java.util.List;

import java.awt.Color;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Features;
import model.IPortfolioInfo;
import util.DateUtility;
import view.tabs.TabbedPane;

/**
 * This represents a GUI based view for the application. It implements {@code IStockTutorView}
 * interface which has all the methods that are required by the view to perform. It also extends
 * JFrame class which is the base component of the view.
 */
public class GraphicalView extends JFrame implements IStockTutorView {

  protected TabbedPane tabs;

  /**
   * It initialises and layouts the GUI view. The initial visible elements are binded together in
   * this constructor.
   */
  public GraphicalView() {

    super("Stock Tutor Application");
    this.setLayout(new CardLayout());
    this.setSize(950, 730);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setBackground(Color.white);
    this.tabs = new TabbedPane();
    this.add(tabs);
    makeVisible();
  }

  protected void makeVisible() {

    this.setVisible(true);
  }

  @Override
  public void displayInfo(String info) throws IllegalStateException {

    JOptionPane.showMessageDialog(this, info,
            "Stock Tutor Message", JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void displayPortfolioInfo(IPortfolioInfo portfolio) {

    int index = tabs.getSelectedIndex();

    if (index == 4) {

      tabs.getFileOperationsPanel().displayPortfolioInfo(portfolio);
    } else if (index == 1) {

      tabs.getPortfolioInfoPanel().setPortfolioData(portfolio);
    }
  }

  @Override
  public void displayAllUserPortfolios(List<IPortfolioInfo> portfolioList) {

    tabs.getPortfolioInfoPanel().setPortfolioListData(portfolioList);
  }

  @Override
  public void displayTotalCostBasis(String portfolio, Date date, float price) {

    tabs.getPortfolioInvestmentPanel().setTextToMessageLabel(String
            .format("The cost basis of the portfolio %s on %s is %.2f",
                    portfolio, date.toString(), price));
  }

  @Override
  public void displayTotalValueForGivenDate(String portfolio, Date date, float price) {

    tabs.getPortfolioInvestmentPanel().setTextToMessageLabel(String
            .format("The total value of the portfolio %s on %s is %.2f",
                    portfolio, date.toString(), price));
  }

  @Override
  public void displayBuyStock(String portfolioName, String tickerSymbol, float amountUsed,
                              Date dateOfPurchase, float remainingAmount) {

    JOptionPane.showMessageDialog(this, String.format("Stock %s has been bought" +
                    " in %s portfolio for $%.2f and the amount remaining is %.2f", tickerSymbol,
            portfolioName, amountUsed, remainingAmount), "Stock Purchased!",
            JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void setFeatures(Features feature) {

    setCreatePortfolioActions(feature);

    setFileOperationsPanelActions(feature);

    setInvestmentDetailsActions(feature);

    setBuyStockActions(feature);

    setGetPortfolioActions(feature);

    setGetAllPortfoliosActions(feature);
  }

  @Override
  public void displayErrorMsg(String error) throws IllegalStateException {
    JOptionPane.showMessageDialog(this, error,
            "Error", JOptionPane.ERROR_MESSAGE);
  }

  private void setBuyStockActions(Features feature) {

    // Listener to buy a stock
    tabs.getBuyStockPanel().setActionListener(l -> {
      if (tabs.getBuyStockPanel().validateDataEntry()) {
        this.callBuyStockBasedOnCommissionFlag(feature);
        tabs.getBuyStockPanel().resetData();
      }
    });
  }

  private void callBuyStockBasedOnCommissionFlag(Features feature) {

    if (tabs.getBuyStockPanel().getCommissionFlag()) {

      feature.buyStockWithCommission(tabs.getBuyStockPanel().getPortfolioNameTextField().getText(),
              tabs.getBuyStockPanel().getTickerSymbolTextField().getText(),
              Float.parseFloat(tabs.getBuyStockPanel().getAmountTextField().getText()),
              DateUtility.convertStringFromUserToDate(tabs.getBuyStockPanel()
                      .getDateOfPurchaseTextField().getText()),
              Float.parseFloat(tabs.getBuyStockPanel().getCommissionTextField().getText()));
    } else {

      feature.buyStock(tabs.getBuyStockPanel().getPortfolioNameTextField().getText(),
              tabs.getBuyStockPanel().getTickerSymbolTextField().getText(),
              Float.parseFloat(tabs.getBuyStockPanel().getAmountTextField().getText()),
              DateUtility.convertStringFromUserToDate(tabs.getBuyStockPanel()
                      .getDateOfPurchaseTextField().getText()));
    }
  }

  private void setCreatePortfolioActions(Features feature) {

    // Listener to create a portfolio
    tabs.getCreatePortfolioPanel().setActionListener(l -> {

      if (tabs.getCreatePortfolioPanel().validateDataEntry()) {

        feature.createPortfolio(tabs.getCreatePortfolioPanel().getTextField().getText());
        tabs.getCreatePortfolioPanel().resetData();
      }
    });

  }

  private void setGetPortfolioActions(Features feature) {

    tabs.getPortfolioInfoPanel().setActionListeners(l -> {

      if (tabs.getPortfolioInfoPanel().validateData()) {

        tabs.getPortfolioInfoPanel().resetPortfolioData();
        feature.getPortfolio(tabs.getPortfolioInfoPanel().getPortfolioNameTextField().getText());
        tabs.getPortfolioInfoPanel().resetPortfolioTextFieldValue();
      }
    });
  }

  private void setGetAllPortfoliosActions(Features feature) {

    tabs.getPortfolioInfoPanel().setGetAllPortfoliosButtonActionListener(l -> {

      feature.getAllUserPortfolio();
    });
  }

  private void setFileOperationsPanelActions(Features feature) {

    tabs.getFileOperationsPanel().setActionListenerForGetPortfolioFromFile(l -> {
      final JFileChooser fileChooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "Portfolio files", "txt");
      fileChooser.setFileFilter(filter);
      int returnState = fileChooser.showOpenDialog(this);
      if (returnState == JFileChooser.APPROVE_OPTION) {
        File f = fileChooser.getSelectedFile();
        String filePath = f.getAbsolutePath();
        feature.getPortfolioFromFile(filePath);
      }
    });

    tabs.getFileOperationsPanel().setActionListenerForSavePortfolioToFile(l -> {

      if (tabs.getFileOperationsPanel().validateData()) {
        final JFileChooser fchooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Portfolio files", "txt");
        fchooser.setFileFilter(filter);
        int retvalue = fchooser.showSaveDialog(this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          String filePath = f.getAbsolutePath();

          feature.savePortfolio(tabs.getFileOperationsPanel().getTextField().getText(),
                  filePath);
        }
      }
    });
  }

  private void setInvestmentDetailsActions(Features feature) {

    tabs.getPortfolioInvestmentPanel().setActionListenerForCostBasis(l -> {

      if (tabs.getPortfolioInvestmentPanel().validateFields()) {

        Date date = DateUtility.convertStringFromUserToDate(
                tabs.getPortfolioInvestmentPanel().getDateTextField().getText());
        feature.getTotalCostBasis(tabs.getPortfolioInvestmentPanel()
                .getPortfolioNameTextField().getText(), date);
      }
    });

    tabs.getPortfolioInvestmentPanel().setActionListenerForTotalValue(l -> {

      if (tabs.getPortfolioInvestmentPanel().validateFields()) {

        Date date = DateUtility.convertStringFromUserToDate(
                tabs.getPortfolioInvestmentPanel().getDateTextField().getText());
        feature.getTotalValueForGivenDate(
                tabs.getPortfolioInvestmentPanel().getPortfolioNameTextField().getText(), date);
      }
    });
  }
}