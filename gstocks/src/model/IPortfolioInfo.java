package model;

import java.util.Date;
import java.util.List;

/**
 * This interface provides a portfolio info which is immutable. A portfolio of stocks is simply a
 * collection of stocks (e.g. 10 shares of company X, 10 shares of company Y, etc.). The total value
 * of a portfolio is then the sum of the values of its individual holdings. Thus the value of a
 * portfolio, much like individual stocks, also changes with time. An individual investor may track
 * multiple portfolios: a retirement portfolio, a college-savings portfolio, health savings
 * portfolio, etc. This interface lets to perform operations on such a portfolio like creation,
 * examination and adding stocks to the portfolio.
 */

public interface IPortfolioInfo {

  /**
   * A method that returns the list of purchase history items for this portfolio.
   *
   * @return list of purchase history items.
   */
  List<IPurchaseHistory> getPortfolioItems();

  /**
   * Returns the name of the portfolio.
   *
   * @return A {@code String} object which gives the name of the portfolio.
   */
  String getPortfolioName();

  /**
   * Returns the total investment made into this portfolio.
   *
   * @param date A {@code Date} value to check the valuation of stocks until that date.
   * @return A {@code float} value which is the sum of all the investment made into this profile.
   */
  float getTotalCostBasis(Date date) throws IllegalArgumentException;

  /**
   * Returns the total valuation of the stocks on a certain date.
   *
   * @param date A {@code Date} value to check the valuation of stocks until that date.
   * @return A float value fo the total value of the portfolio.
   */
  float getTotalValueForGivenDate(Date date) throws IllegalArgumentException;
}
