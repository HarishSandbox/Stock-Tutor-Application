package util;

import java.awt.GridBagConstraints;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.IPortfolioInfo;
import model.IPurchaseHistory;
import strategies.InvestmentStrategy;

/**
 * This class provides utils required for view classes.
 */
public class ViewUtils {

  /**
   * Constructs a gridBag layout constraint.
   *
   * @param fill    the int value of fill in gridbag layout.
   * @param gridx   the int value of gridx.
   * @param gridy   the int value fo gridy.
   * @param weightx the double value of weightx.
   * @param weighty the double value of weighty.
   * @return GridBagConstraints object.
   */
  public static GridBagConstraints makeGridBagConstraints(int fill, int gridx, int gridy,
                                                          double weightx,
                                                          double weighty) {
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = fill;
    constraints.gridx = gridx;
    constraints.gridy = gridy;
    constraints.weightx = weightx;
    constraints.weighty = weighty;
    return constraints;
  }

  /**
   * Validates the name for a portfolio.
   *
   * @param name A string value of the name.
   * @return A boolean true if valid, false otherwise.
   */
  public static boolean validatePortfolioNameInput(String name) {

    String text = name;

    Pattern p = Pattern.compile("[^A-Za-z0-9 ]", Pattern.CASE_INSENSITIVE);
    Matcher matcher = p.matcher(text);

    if (matcher.find()) {

      return false;
    }

    return !text.equals("");
  }

  /**
   * Validates the name for a strategy.
   *
   * @param name A string value of the name.
   * @return A boolean true if valid, false otherwise.
   */
  public static boolean validateStrategyNameInput(String name) {

    String text = name;

    Pattern p = Pattern.compile("[^A-Za-z0-9 ]", Pattern.CASE_INSENSITIVE);
    Matcher matcher = p.matcher(text);

    if (matcher.find()) {

      return false;
    }

    return !text.equals("");
  }


  /**
   * Constructs a string to represent the portfolio object.
   *
   * @param portfolio A IPortfolioInfo object.
   * @return A String value of the object.
   */
  public static String constructPortfolioInfoStringForGUI(IPortfolioInfo portfolio) {

    StringBuilder portfolioString = new StringBuilder();

    portfolioString.append("Portfolio name:  ").append(portfolio.getPortfolioName());
    portfolioString.append("\n   \n");

    int count = 1;
    if (portfolio.getPortfolioItems().size() == 0) {

      portfolioString.append("No purchases made in this portfolio\n");
    }
    for (IPurchaseHistory item : portfolio.getPortfolioItems()) {

      if (item == null) {
        portfolioString.append("No purchases made in this portfolio\n");
        break;
      }
      portfolioString.append(count).append(" ").append(item.getTickerSymbol()).append(" of "
              + "quantity ").append(item.getQuantity()).append(" bought on ")
              .append(item.getDateOfPurchase().toString()).append(" for an amount ")
              .append(item.getCostBasisForPurchase());
      portfolioString.append("\n\n");
    }
    return portfolioString.toString();
  }

  /**
   * Constructs a string to represent the investment strategy object.
   *
   * @param strategy A InvestmentStrategy object.
   * @return A String value of the object.
   */
  public static String constructStrategyInfoStringForGUI(InvestmentStrategy strategy) {

    StringBuilder strategyString = new StringBuilder();

    strategyString.append("Strategy name:  ").append(strategy.getStrategyName());
    strategyString.append("\n");

    strategyString.append("Start date:  ").append(strategy.getStartDate().toString());
    strategyString.append("\n");

    if (strategy.getEndDate() != null) {
      strategyString.append("End date:  ").append(strategy.getEndDate().toString());
      strategyString.append("\n");
    }

    strategyString.append("Frequency:  ").append(strategy.getFrequencyOfInvestment());
    strategyString.append("\n");

    strategyString.append("Investment amount:  ").append(strategy.getPriceOfInvestment());
    strategyString.append("\n");

    strategyString.append("Commission fee:  ").append(strategy.getCommissionFee());
    strategyString.append("\n\n");

    strategyString.append("Stocks and weights listing\n");

    int count = 1;
    if (strategy.getStockAndWeights().size() == 0) {

      strategyString.append("No stocks are present in this strategy\n");
      return strategyString.toString();
    }
    Map<String, Float> stocks = strategy.getStockAndWeights();
    for (String ticker : stocks.keySet()) {

      strategyString.append(count).append(".").append("  ").append(ticker).append(":")
              .append("  ").append(String.format("%.2f", stocks.get(ticker))).append("\n");
    }
    return strategyString.toString();
  }
}