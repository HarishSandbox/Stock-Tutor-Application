package model;

/**
 * This interface extends IPortfolioInfo interface which has methods to query data of a portfolio.
 * It provides a method to add a purchase item to the portfolio.
 */
public interface IPortfolio extends IPortfolioInfo {

  /**
   * Adds a purchase of a stock to the portfolio. It takes {@code PurchaseHistory} object which has
   * all the details of the stock to be added.
   *
   * @param purchaseHistory A {@code PurchaseHistory} object which has all the details of the
   *                        stock.
   */
  void addPurchase(IPurchaseHistory purchaseHistory);
}
