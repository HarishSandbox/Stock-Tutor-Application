package view.tabs;


import java.awt.Color;
import java.awt.CardLayout;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;

/**
 * This class provides a tabbed pane view for the stock tutor GUI by implementing JTabbedPane of
 * java swing. The class creates panels for each of the tab within the tabbed pane window.
 */
public class TabbedPane extends JTabbedPane {

  private final CreatePortfolioPanel createPortfolioPanel;

  private final PortfolioInfoPanel portfolioInfoPanel;

  private final BuyStockPanel buyStockPanel;

  private final InvestmentDetailsPanel portfolioInvestmentPanel;

  private final SaveRetrievePortfolioPanel fileOperationsPanel;

  private final InvestmentStrategyPanel strategyPanel;

  private final StrategySaveAndRetrievePanel strategyFileOperationsPanel;

  /**
   * Getter method that fetches the CreatePortfolioPanel of the create portfolio tab.
   *
   * @return CreatePortfolioPanel that has the view for creating a portfolio.
   */
  public CreatePortfolioPanel getCreatePortfolioPanel() {
    return createPortfolioPanel;
  }

  /**
   * Getter method that fetches the PortfolioInfoPanel of the create portfolio tab.
   *
   * @return PortfolioInfoPanel that has the view for creating a portfolio.
   */
  public PortfolioInfoPanel getPortfolioInfoPanel() {
    return portfolioInfoPanel;
  }

  /**
   * Getter method that fetches the BuyStockPanel of the buy stock tab.
   *
   * @return BuyStockPanel that has the view for buying a stock.
   */
  public BuyStockPanel getBuyStockPanel() {
    return buyStockPanel;
  }

  /**
   * Getter method that fetches the portfolioInvestmentPanel of the investment details tab.
   *
   * @return portfolioInvestmentPanel that has the view for getting investment details for a
   *        portfolio.
   */
  public InvestmentDetailsPanel getPortfolioInvestmentPanel() {
    return portfolioInvestmentPanel;
  }

  /**
   * Getter method that fetches the SaveRetrievePortfolioPanel of the save/retrieve tab.
   *
   * @return SaveRetrievePortfolioPanel that has the view for save/retrieve portfolio.
   */
  public SaveRetrievePortfolioPanel getFileOperationsPanel() {
    return fileOperationsPanel;
  }

  /**
   * Getter method that fetches the InvestmentStrategyPanel of the strategy tab.
   *
   * @return strategyPanel that has the view for applying a strategy to a portfolio.
   */
  public InvestmentStrategyPanel getStrategyPanel() {
    return strategyPanel;
  }

  /**
   * Getter method that fetches the StrategySaveAndRetrievePanel of the save/retrieve Investment
   * tab.
   *
   * @return SaveRetrievePortfolioPanel that has the view for save/retrieve Investment strategy.
   */
  public StrategySaveAndRetrievePanel getStrategyFileOperationsPanel() {
    return strategyFileOperationsPanel;
  }

  /**
   * Constructor that initializes the view to be a tabbed view. It also adds panels to each of the
   * tabs.
   */
  public TabbedPane() {

    super();
    this.setLayout(new CardLayout());
    this.addChangeListener(this::stateChanged);
    this.setBackground(Color.white);

    createPortfolioPanel = new CreatePortfolioPanel();
    this.addTab("Create Portfolio", createPortfolioPanel);

    portfolioInfoPanel = new PortfolioInfoPanel();
    this.addTab("Portfolio Info", portfolioInfoPanel);

    buyStockPanel = new BuyStockPanel();
    this.addTab("Buy Stock", buyStockPanel);

    portfolioInvestmentPanel = new InvestmentDetailsPanel();
    this.addTab("Investment Details", portfolioInvestmentPanel);

    fileOperationsPanel = new SaveRetrievePortfolioPanel();
    this.addTab("Save/Open Portfolio", fileOperationsPanel);

    strategyPanel = new InvestmentStrategyPanel();
    this.addTab("Strategy Details", strategyPanel);

    strategyFileOperationsPanel = new StrategySaveAndRetrievePanel();
    this.addTab("Save/Open Investment Strategy", strategyFileOperationsPanel);

    this.setVisible(true);
    this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
  }

  /**
   * Action handler for changes happened to the tabs. Thsi method performs the changes for the
   * change listerner of tabbed pane.
   *
   * @param e The ChangeEvent object which has info related to event raised.
   */
  public void stateChanged(ChangeEvent e) {

    int instance = this.getSelectedIndex();
    switch (instance) {

      case 0:
        createPortfolioPanel.repaint();
        break;
      case 1:
        portfolioInfoPanel.repaint();
        break;
      case 2:
        buyStockPanel.repaint();
        break;
      case 3:
        portfolioInvestmentPanel.repaint();
        break;
      case 4:
        fileOperationsPanel.repaint();
        break;
      case 5:
        strategyPanel.repaint();
        break;
      case 6:
        strategyFileOperationsPanel.repaint();
        break;
      default:
        break;
    }
  }
}