package model;

import java.io.InputStream;
import java.util.Date;

import strategies.InvestmentStrategy;

/**
 * This interface supports additional functionality of supporting investment strategies. It offers
 * methods to save and retrieve strategies from file and a method to buy stocks which is customized
 * inorder to support investment strategy. This interface extends {@code IStockTutorModelExtended}
 * interface.
 */
public interface IStockTutorStrategy extends IStockTutorModelExtended {

  /**
   * This method saves an existing strategy to an external file.
   *
   * @param strategy the name of the strategy to be saved.
   * @param fileURL  the file path where the strategy has to be saved.
   */
  void saveStrategyToFile(String strategy, String fileURL);

  /**
   * This method allows the user to load a strategy from a file.
   *
   * @param fileURL path from where the strategy is fetched.
   * @return Investment strategies object.
   * @throws IllegalStateException thrown when the InputStream is null or could not fetch the file.
   */
  InvestmentStrategy getStrategyFromFile(InputStream fileURL) throws IllegalStateException;

  /**
   * Buys shares to a given portfolio. If there is no such portfolio it creates one. It buys partial
   * stocks into the portfolio if the amount dosent make up to a round figure. The commision fee is
   * reduced from the amount before buying a stock. If the date of purchase given is on a public
   * holiday it buys the stock on the next available date.
   *
   * @param portfolio      the portfolio into which the stock has to be bought into.
   * @param tickerSymbol   the ticker symbol of the stock to be bought.
   * @param amount         the amount to be invested.
   * @param dateOfPurchase the date on which the purchase has to be made. If it is a public holiday
   *                       the purchase will be made on the next available working date.
   * @param commissionFees the commission fee to be included for the purchase.
   * @return A float value which has a value if there is any excess amount left after purchase.
   * @throws IllegalArgumentException when there are any illegal arguments sent with which the
   *                                  purchase cannot be done.
   */
  float buyPartialStocksForStrategy(String portfolio, String tickerSymbol, float amount,
                                    Date dateOfPurchase, float commissionFees)
          throws IllegalArgumentException;
}
