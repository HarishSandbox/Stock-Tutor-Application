package controller;

import java.util.Date;

/**
 * This interface represents the features a view can request from a controller. The view has to give
 * all the inputs required for the operation to be handled.
 */
public interface Features {

  /**
   * To create a portfolio with a given name.
   *
   * @param portfolioName A {@code String} value of the portfolio name.
   */
  void createPortfolio(String portfolioName);

  /**
   * To get the details of a particular portfolio, when given the name of the portfolio.
   *
   * @param portfolio A {@code String} value of the portfolio name.
   */
  void getPortfolio(String portfolio);

  /**
   * To get all the list of portfolios created by the user.
   */
  void getAllUserPortfolio();

  /**
   * To get the cost basis of a portfolio. It takes a portfolio name and a date on which the cost
   * basis has to be found.
   *
   * @param portfolio A String value of the portfolio.
   * @param date      A Date value for the cost basis to be found.
   */
  void getTotalCostBasis(String portfolio, Date date);

  /**
   * To get the total value of a portfolio. It takes a portfolio name and a date on which the total
   * value has to be found.
   *
   * @param portfolio A String value of the portfolio.
   * @param date      A Date value for the total value to be found.
   */
  void getTotalValueForGivenDate(String portfolio, Date date);

  /**
   * To buy a stock for a particular portfolio. The ticker symbol of the stock and the date of
   * purchase are taken. It makes a purchase for the given amount of a whole number of stocks
   * possible.
   *
   * @param portfolio      A string value of the portfolio.
   * @param tickerSymbol   The ticker symbol of the stock.
   * @param amount         The amount for which the stock has to be purchased.
   * @param dateOfPurchase The date of purchase for the stock.
   */
  void buyStock(String portfolio, String tickerSymbol, float amount,
                Date dateOfPurchase);

  /**
   * To buy a stock for a particular portfolio. The ticker symbol of the stock and the date of
   * purchase are taken. It makes a purchase for the given amount of a whole number of stocks
   * possible. It takes the commission fee required for the purchase.
   *
   * @param portfolio      A string value of the portfolio.
   * @param tickerSymbol   The ticker symbol of the stock.
   * @param amount         The amount for which the stock has to be purchased.
   * @param dateOfPurchase The date of purchase for the stock.
   * @param commission     The commission fee required for the purchase.
   */
  void buyStockWithCommission(String portfolio, String tickerSymbol, float amount,
                              Date dateOfPurchase, float commission);

  /**
   * To save a portfolio in a file. It takes a portfolio name and file path where it has to be
   * saved.
   *
   * @param portfolioName the name of the portfolio object to be saved.
   * @param fileURL       the file path where the file has to be saved.
   */
  void savePortfolio(String portfolioName, String fileURL);

  /**
   * To get a portfolio from a text based file.
   *
   * @param fileName the file path from which the portfolio has be retrieved.
   */
  void getPortfolioFromFile(String fileName);

}