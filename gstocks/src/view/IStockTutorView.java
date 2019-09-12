package view;

import java.util.Date;
import java.util.List;

import controller.Features;
import model.IPortfolioInfo;

/**
 * An interface for view component of the Stock tutoring application. It is responsible for getting
 * appropriate input from the user to the controller, and passing the info sent by the controller to
 * the user.
 */
public interface IStockTutorView {

  /**
   * Displays the info sent to the user/on the view.
   *
   * @param info A {@code String} object which has information to be displayed.
   */
  void displayInfo(String info) throws IllegalStateException;

  /**
   * Displays the information related to a portfolio that the user has created.
   *
   * @param portfolio name of the portfolio specified as a {@code String}
   */
  void displayPortfolioInfo(IPortfolioInfo portfolio);


  /**
   * Displays all the portfolios that the user has created.
   *
   * @param portfolioList list of portfolios.
   */
  void displayAllUserPortfolios(List<IPortfolioInfo> portfolioList);

  /**
   * A method that displays total cost basis for a given portfolio for the specified date.
   *
   * @param portfolio name of portfolio.
   * @param date      date for which the buying costs needs to be fetched.
   * @param price     the total cost basis for the purchase of a stock.
   */
  void displayTotalCostBasis(String portfolio, Date date, float price);


  /**
   * A method that displays total value of the purchase for a given portfolio for the specified
   * date.
   *
   * @param portfolio name of portfolio.
   * @param date      date for which the total value of a purchase is calculated.
   * @param price     the total value for the purchase of a stock.
   */
  void displayTotalValueForGivenDate(String portfolio, Date date, float price);

  /**
   * A method that displays information about a stock being purchased.
   *
   * @param portfolioName   name of portfolio.
   * @param tickerSymbol    stock symbol.
   * @param amountUsed      purchase amount.
   * @param dateOfPurchase  date for which the stock is purchased.
   * @param remainingAmount the remaining amount the user has after buying the stock.
   */
  void displayBuyStock(String portfolioName, String tickerSymbol,
                       float amountUsed, Date dateOfPurchase,
                       float remainingAmount);

  /**
   * This method sets the features interface object with the view. The view uses this object to
   * request operations to be performed by the controller.
   *
   * @param features A {@code Features} object which is used to request operations to be performed
   *                 by a view.
   */
  void setFeatures(Features features);

  /**
   * Displays the error sent to the view.
   *
   * @param error A {@code String} object which has error to be displayed.
   */
  void displayErrorMsg(String error) throws IllegalStateException;

}
