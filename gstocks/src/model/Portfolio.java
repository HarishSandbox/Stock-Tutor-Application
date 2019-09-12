package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import util.DateUtility;

/**
 * This class represents a portfolio of stocks in investment sector. It implements {@code
 * IPortfolio} interface. This class has the purchased stock information.
 */
class Portfolio implements IPortfolio {

  private final String portfolioName;
  private List<IPurchaseHistory> portfolioItems;

  /**
   * This constructor taken in the name of the portfolio to create the portfolio.
   *
   * @param portfolioName A {@code String} which gives the name of the portfolio.
   */
  Portfolio(String portfolioName) {
    this.portfolioName = portfolioName;
    this.portfolioItems = new ArrayList<>();
  }

  /**
   * This constructor returns a Portfolio object when a portfolio name ans list of purchases are
   * given.
   *
   * @param portfolioName A String value of the portfolio name.
   * @param items         The list of purchase items to be added to the portfolio.
   */
  Portfolio(String portfolioName, List<IPurchaseHistory> items) {

    this.portfolioName = portfolioName;
    this.portfolioItems = items;
  }

  @Override
  public String getPortfolioName() {
    return portfolioName;
  }

  @Override
  public List<IPurchaseHistory> getPortfolioItems() {
    return Collections.unmodifiableList(portfolioItems);
  }

  @Override
  public void addPurchase(IPurchaseHistory purchaseHistory) {
    portfolioItems.add(purchaseHistory);
  }

  @Override
  public float getTotalCostBasis(Date date) throws IllegalArgumentException {

    if (date == null) {
      throw new IllegalArgumentException("Date cannot not be null or Invalid");
    }

    float totalAmount = 0;
    IPurchaseHistory purchaseHistoryItem;

    for (IPurchaseHistory portfolioItem : this.portfolioItems) {
      purchaseHistoryItem = portfolioItem;
      Date purchaseDate = purchaseHistoryItem.getDateOfPurchase();
      if (DateUtility.isGivenDateLessThanOrEqualToOtherDate(purchaseDate, date)) {
        totalAmount += purchaseHistoryItem.getCostBasisForPurchase();
      }
    }
    return this.roundToTwoDecimal(totalAmount);
  }

  @Override
  public float getTotalValueForGivenDate(Date date) throws IllegalArgumentException {

    if (date == null) {
      throw new IllegalArgumentException("Date cannot not be null or Invalid");
    }

    IStockStorage localStock = StockStorage.getInstance();
    float totalAmount = 0;
    IPurchaseHistory purchaseHistoryItem;

    for (IPurchaseHistory portfolioItem : this.portfolioItems) {
      purchaseHistoryItem = portfolioItem;
      Date purchaseDate = purchaseHistoryItem.getDateOfPurchase();
      if (DateUtility.isGivenDateLessThanOrEqualToOtherDate(purchaseDate, date)) {
        totalAmount += purchaseHistoryItem.getQuantity() *
                localStock.getStock(purchaseHistoryItem.getTickerSymbol())
                        .getLatestPriceUntil(date);
      }
    }
    return this.roundToTwoDecimal(totalAmount);
  }

  private float roundToTwoDecimal(float num) {

    return Float.parseFloat(String.format("%.2f", num));
  }
}