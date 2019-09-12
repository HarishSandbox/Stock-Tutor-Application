package model;

import java.util.Date;

/**
 * This interface represents the purchase history that is made whenever the user buys a stock.
 */
public interface IPurchaseHistory {

  /**
   * This method calculates the total buying cost made for the purchase of a stock.
   *
   * @return fetches the total cost basis and returns as a float value.
   */
  float getCostBasisForPurchase();

  /**
   * The date at which the stock is purchased.
   *
   * @return A Date value of the date at which the stock is purchased.
   */
  Date getDateOfPurchase();

  /**
   * The quantity of stock purchased is returned.
   *
   * @return An {@code float} value of number of units of stock purchased.
   */
  float getQuantity();

  /**
   * Returns the ticker symbol of the stock purchased.
   *
   * @return A {@code String} of the ticker symbol of the stock.
   */
  String getTickerSymbol();
}
