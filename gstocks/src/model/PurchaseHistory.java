package model;

import java.util.Date;

/**
 * This class has the info about a purchase made. Every purchase of a stock is stored as a
 * combination of ticker, quantity, purchase amount, date of purchase and commission fees.
 */
class PurchaseHistory implements IPurchaseHistory {

  private final String tickerSymbol;
  private final float quantity;
  private final Date dateOfPurchase;
  private final float purchaseAmount;

  /**
   * Creates a purchase item by taking ticker, quantity, purchase amount and date of purchase.
   *
   * @param tickerSymbol   the ticker symbol of the stock.
   * @param quantity       the quantity purchased.
   * @param purchaseAmount the amount for which the purchase has been made.
   * @param dateOfPurchase the date at which it is purchased.
   */
  PurchaseHistory(String tickerSymbol, float quantity, float purchaseAmount,
                  Date dateOfPurchase) {

    this.tickerSymbol = tickerSymbol;
    this.quantity = quantity;
    this.purchaseAmount = purchaseAmount;
    this.dateOfPurchase = dateOfPurchase;
  }

  @Override
  public String getTickerSymbol() {
    return tickerSymbol;
  }

  @Override
  public float getQuantity() {
    return quantity;
  }

  @Override
  public Date getDateOfPurchase() {

    return dateOfPurchase;
  }

  @Override
  public float getCostBasisForPurchase() {

    return purchaseAmount;
  }
}
