package controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This interface is extended from existing {@link Features} interface in order to support
 * additional functionality of applying an investment strategy to a portfolio. The interface
 * provides methods to create three different types of strategy namely EqualWeight strategy,
 * CustomWeight strategy and DollarCostAveraging strategy. It provides a method to apply any of the
 * strategy to a portfolio. It also provides methods to save and retrieve strategies in an external
 * file.
 */
public interface FeaturesExtended extends Features {

  /**
   * This method supports creation of equal weight strategy. This strategy allows an user to invest
   * a fixed amount into an portfolio containing multiple stocks, using equal weight for each stock
   * in the portfolio. This strategy is non recurring and applied ony once. It is not allowed to add
   * duplicate stocks to same strategy.
   *
   * @param strategyName     name of the strategy to be created.
   * @param investmentAmount amount to be invested.
   * @param startDate        start date of the strategy.
   * @param stocks           list of stocks that the strategy consists and will be applied on
   *                         portfolio.
   * @param commissionFee    commission fees that the user is charged for applying the strategy.
   *                         This commission fees is charged on per stock basis.
   * @return true if the creation of strategy did not throw any error, otherwise false.
   */
  boolean createEqualWeightStrategy(String strategyName, float investmentAmount,
                                    Date startDate, List<String> stocks, float commissionFee);

  /**
   * This method supports creation of custom weight strategy. This strategy allows an user to invest
   * a fixed amount into an portfolio containing multiple stocks, using custom weight for each stock
   * in the portfolio. The weights are taken as percentage and should add up to 100. This is a non
   * recurring strategy.It is not allowed to add duplicate stocks to same strategy.
   *
   * @param strategyName     name of the strategy to be created.
   * @param investmentAmount amount to be invested.
   * @param startDate        start date of the strategy.
   * @param stocks           map of stocks and their corresponding weights that the strategy
   *                         consists and will be applied on portfolio.
   * @param commissionFee    commission fees that the user is charged for applying the strategy.
   *                         This commission fees is charged on per stock basis.
   * @return true if the creation of strategy did not throw any error, otherwise false.
   */
  boolean createCustomWeightStrategy(String strategyName, float investmentAmount,
                                     Date startDate, Map<String, Float> stocks, float commissionFee)
          throws IllegalArgumentException;

  /**
   * This method supports creation of dollar cost averaging strategy. This is a recurring strategy
   * which takes frequency as days and applies it on a portfolio at regular intervals specified by
   * the frequency. It allows an user to invest a fixed amount into an portfolio containing multiple
   * stocks, using custom weight for each stock in the portfolio. The weights are taken as
   * percentage and should add up to 100. It is not allowed to add duplicate stocks to same
   * strategy.
   *
   * @param strategyName     name of the strategy to be created.
   * @param investmentAmount amount to be invested.
   * @param frequency        frequency at which the strategy needs to be applied, specified
   *                         as days.
   * @param startDate        start date of the strategy.
   * @param endDate          end date of the strategy.
   * @param stocks           map of stocks and their corresponding weights that the strategy
   *                         consists and will be applied on portfolio.
   * @param commissionFee    commission fees that the user is charged for applying the strategy.
   *                         This commission fees is charged on per stock basis.
   * @return true if the creation of strategy did not throw any error, otherwise false.
   */
  boolean createDollarCostStrategy(String strategyName, float investmentAmount, int frequency,
                                   Date startDate, Date endDate, Map<String, Float> stocks
          , float commissionFee);

  /**
   * This method applies one of the three strategies : Equal weights non-recurring investment,
   * Custom weights non-recurring investment and Dollar cost averaging investment strategy to a
   * portfolio. If the specified portfolio is not present, it creates a new portfolio. For dollar
   * cost averaging strategy, if no end date is specified, it is applied till current day. If any of
   * these strategies have start date on a public holiday or weekends, it method chooses the next
   * available day to invest.
   *
   * @param portfolioName name of the portfolio.
   * @param strategyName  name of the strategy.
   */
  void applyStrategy(String portfolioName, String strategyName);

  /**
   * This method allows the user to save an existing strategy to a file.
   *
   * @param strategyName name of the strategy.
   * @param fileURL      file path specified as a string.
   */
  void saveStrategy(String strategyName, String fileURL);

  /**
   * This method allows the user to load a strategy from a file.
   *
   * @param fileName name of the the file.
   */
  void getStrategy(String fileName);
}
