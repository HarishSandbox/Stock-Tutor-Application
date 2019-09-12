package model;

import java.util.Date;

/**
 * This class represents a purchase history of a stock purchase made. It extends {@link
 * PurchaseHistory} and adds another parameter commission fees that is charged for every purchase of
 * a stock.
 */
public class PurchaseHistoryCommission extends PurchaseHistory {

  /**
   * Creates a purchase item by taking ticker, quantity, purchase amount, date of purchase and
   * commission fees.
   *
   * @param tickerSymbol   the ticker symbol of the stock.
   * @param quantity       the quantity purchased.
   * @param purchaseAmount the amount for which the purchase has been made.
   * @param dateOfPurchase the date at which it is purchased.
   * @param commissionFees brokerage fees that is charged for every purchase.
   */
  PurchaseHistoryCommission(String tickerSymbol, float quantity, float purchaseAmount,
                            Date dateOfPurchase, float commissionFees) {

    super(tickerSymbol, quantity, purchaseAmount + commissionFees, dateOfPurchase);
  }
}
