package strategies;

import java.util.Date;
import java.util.Map;

/**
 * This interface represents investment strategy builder. It provides methods to customize the
 * construction of {@code StockTutorInvestmentStrategy} object. It follows builder design pattern.
 */
public interface InvestmentStrategyBuilder {

  /**
   * Method that takes number of days a frequency for a given strategy.
   *
   * @param days as investment frequency.
   * @return {@code InvestmentStrategyBuilder} object.
   * @throws IllegalArgumentException if days is less than or equal to zero.
   */
  InvestmentStrategyBuilder frequency(int days) throws IllegalArgumentException;

  /**
   * Method that takes start date for an investment strategy.
   *
   * @param date start date of investment strategy.
   * @return {@code InvestmentStrategyBuilder} object.
   */
  InvestmentStrategyBuilder startDate(Date date);

  /**
   * Method that takes end date for an investment strategy.
   *
   * @param date end date of investment strategy.
   * @return {@code InvestmentStrategyBuilder} object.
   */
  InvestmentStrategyBuilder endDate(Date date);

  /**
   * Method that takes investment amount for an investment strategy.
   *
   * @param investment amount to be invested for an investment strategy.
   * @return {@code InvestmentStrategyBuilder} object.
   */
  InvestmentStrategyBuilder amount(Float investment);

  /**
   * Method that takes stocks and corresponding weights as a map for an investment strategy.
   *
   * @param stocks map of ticker symbol and corresponding weights.
   * @return {@code InvestmentStrategyBuilder} object.
   */
  InvestmentStrategyBuilder stocks(Map<String, Float> stocks);

  /**
   * Method that takes commission fees to be applied for every stock in an investment strategy.
   *
   * @param commission commission fees charged per stock in an investment strategy.
   * @return {@code InvestmentStrategyBuilder} object.
   */
  InvestmentStrategyBuilder commission(Float commission);

  /**
   * Method that builds the {@code InvestmentStrategy} object.
   *
   * @return {@code InvestmentStrategy} object
   */
  InvestmentStrategy build();
}