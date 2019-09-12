package strategies;

import java.util.Date;
import java.util.Map;

/**
 * This interface represent an investment strategy that can be applied to a portfolio. It has getter
 * methods to fetch details about the investment strategy. A strategy can be applied only on one
 * portfolio. Multiple strategies can be applied on a single portfolio.
 */
public interface InvestmentStrategy {

  /**
   * Method that returns the start date of the strategy.
   *
   * @return start date as a {@code Date} object.
   */
  Date getStartDate();

  /**
   * Method that returns the end date of the strategy.
   *
   * @return end date as a {@code Date} object.
   */
  Date getEndDate();

  /**
   * Method that returns the frequency of the strategy. Frequency represents the interval at which
   * the strategy needs to be applied.
   *
   * @return frequency of investment for the strategy.
   */
  int getFrequencyOfInvestment();

  /**
   * Method that returns the investment amount for the strategy.
   *
   * @return investment amount for the strategy.
   */
  Float getPriceOfInvestment();

  /**
   * Method that fetches a map of stocks and its corresponding weights that will be part of the
   * strategy.
   *
   * @return map of stocks and its corresponding weights.
   */
  Map<String, Float> getStockAndWeights();

  /**
   * Method that fetches the name of the strategy.
   *
   * @return name of the strategy as a string.
   */
  String getStrategyName();

  /**
   * Method that fetches the commission fees that is charged for this strategy.
   *
   * @return commission fees as float.
   */
  Float getCommissionFee();
}