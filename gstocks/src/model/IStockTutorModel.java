package model;

import java.util.Date;
import java.util.List;

/**
 * This class represents the model of Stock Tutoring. It offers operations to perform the business
 * logic of this application. It supports the following operations. This model dose not support
 * after hours trading. The Date is always in 24 hour format. We are considering the closing price
 * of the day in this tutoring application. Any purchase made on that day is calculated taking the
 * closing price on that day. We cannot trade during stock market holidays. In this application the
 * holidays are Saturday and Sunday.
 *
 * <ul>
 * <li>Allow a user to create one or more portfolios and examine its composition.</li>
 * <li>Buy shares of some stock in a portfolio worth a certain amount at a certain date.</li>
 * <li>Determine the total cost basis and total value of a portfolio at a certain date.</li>
 * </ul>
 */
public interface IStockTutorModel {

  /**
   * Fetches the price of the stock for the given date and ticker symbol. It throws an error when
   * the stock symbol is wrong or unable to fetch a stock.
   *
   * @param tickerSymbol The ticker symbol of the stock.
   * @param date         A {@code Date} object for which the price has to be found.
   * @return The {@code String} value of the price on that date.
   * @throws IllegalArgumentException thrown when the stock symbol is invalid or unable to fetch a
   *                                  stock. Exception is also thrown if invalid date is entered.
   */
  float getStockPrice(String tickerSymbol, Date date) throws IllegalArgumentException;

  /**
   * Creates a portfolio when given a unique name. It throws an exception if the given name does not
   * the naming rules or is invalid.
   *
   * @param portfolioName A {@code String} which should comprise only of alphanumeric symbols and
   *                      can be separated by spaces.
   * @throws IllegalArgumentException Thrown when the name given doesn't follow the naming rules.
   */
  void createPortfolio(String portfolioName) throws IllegalArgumentException;

  /**
   * Returns the portfolio description when given the name of the portfolio. Throws an exception if
   * the name is invalid or a profile doesnt exist for the given name. A portfolio name can only
   * take characters a-z, A-Z, 0-9 and space in it. "q" or "quit" (case insensitive) are not allowed
   * as portfolio names.
   *
   * @param portfolio A {@code String} value of the portfolio.
   * @return A {@code String} description of the portfolio.
   * @throws IllegalArgumentException if the name is invalid or a profile does not exist for the
   *                                  given name.
   */
  IPortfolioInfo getPortfolio(String portfolio) throws IllegalArgumentException;

  /**
   * Returns all the portfolios created by the user.
   *
   * @return list of portfolios that the user owns.
   */
  List<IPortfolioInfo> getAllUserPortfolio();

  /**
   * Returns the total investment made in the portfolio given on or before the date that is
   * specified. It calculates purchases made on or before that day and not the future purchases. It
   * throws an exception if the profile given is invalid or not present.
   *
   * @param portfolio A {@code String} value of the portfolio name.
   * @param date      The date at which the investment value has to be found.
   * @return A {@code float} value of the total investment made.
   * @throws IllegalArgumentException thrown if the profile given is invalid or not present.
   */
  float getTotalCostBasis(String portfolio, Date date) throws IllegalArgumentException;

  /**
   * Returns the total value of the investment that it amounts to in the market for a given date for
   * a portfolio. It calculates purchases made on or before that day and not the future purchases.
   * It throws an exception if the profile given is invalid or not present.
   *
   * @param portfolio A {@code String} value of the portfolio name.
   * @param date      The date at which the investment value has to be found.
   * @return A {@code float} value of the total return made.
   * @throws IllegalArgumentException thrown if the profile given is invalid or not present.
   */
  float getTotalValueForGivenDate(String portfolio, Date date)
          throws IllegalArgumentException;

  /**
   * Buy a stock of specific company and add them to a profile for a certain amount and time. It
   * throws an exception if the stock cannot be bought because the amount given is less or it is an
   * invalid stock which is not present. If the portfolio is invalid or not present and if the date
   * is invalid it throws an exception. It return excess amount remaining after the stock has been
   * bought. Our business logic for buying a stock is implemented such that the price of the stock
   * at any give date during the trading hours remains the same. For this, our design only considers
   * the date alone.
   *
   * @param portfolio      the portfolio name into which the stock has to be added to.
   * @param tickerSymbol   the string symbol for the company.
   * @param amount         the amount to be invested in the stock.
   * @param dateOfPurchase the date at which the stock has to be bought.
   * @return A {@code float} value of the amount remaining after the stock has been bought.
   * @throws IllegalArgumentException It throws an exception if the stock cannot be bought because
   *                                  the amount given is less or it is an invalid stock which is
   *                                  not present. If the portfolio is invalid or not present and if
   *                                  the date is invalid it throws an exception. It also throws an
   *                                  exception if buying option is tried outside of trading hours
   *                                  (9am - 4pm)
   */
  float buyStock(String portfolio, String tickerSymbol, float amount,
                 Date dateOfPurchase)
          throws IllegalArgumentException;

}
