package model;

import java.io.InputStream;
import java.util.Date;

/**
 * This interface provides methods for features added in version 2 of the application like
 * commission fee and saving and retrieving from a file. This interface extends {@code
 * IStockTutorModel} interface.
 */
public interface IStockTutorModelExtended extends IStockTutorModel {

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
   * @param commissionFees fees are charged by the brokerage service that an investor typically use
   *                       to buy stocks.
   * @return A {@code float} value of the amount remaining after the stock has been bought.
   * @throws IllegalArgumentException It throws an exception if the stock cannot be bought because
   *                                  the amount given is less or it is an invalid stock which is
   *                                  not present. If the portfolio is invalid or not present and if
   *                                  the date is invalid it throws an exception. It also throws an
   *                                  exception if buying option is tried outside of trading hours
   *                                  (9am - 4pm). It throws exception if commission fees is
   *                                  negative or if it is greater than purchase amount.
   */
  float buyStockWithCommission(String portfolio, String tickerSymbol, float amount,
                               Date dateOfPurchase, float commissionFees)
          throws IllegalArgumentException;

  /**
   * Creates a file and saves a given {@code PortfolioInfo} object to a given file path. If there is
   * an existing file of the given name it will be rewritten if permitted. If the user is unable to
   * perform file writing operations it throws an exception.
   *
   * @param fileUrl   A String value of the absolute path where the file has to be saved.
   * @param portfolio A {@code PortfolioInfo} object which has to be saved t the file.
   * @throws IllegalStateException thrown when writing operations could not be performed or object
   *                               cannot be written.
   */
  void savePortfolioToFile(String fileUrl, String portfolio) throws IllegalStateException;

  /**
   * Retrieves a file from a given file path and converts the info into an PortfolioInfo. It throws
   * an exception if the file could not be read or dose not exists or the portfolio object could not
   * be created.
   *
   * @param stream A InputStream object of the absolute path from where the file has to be
   *               retrieved.
   * @return An {@code PortfolioInfo} object that is created from the data of the file.
   * @throws IllegalStateException thrown if the file could not be read or dose not exists or the
   *                               portfolio object could not be created.
   */
  IPortfolioInfo getPortfolioFromFile(InputStream stream) throws IllegalStateException;
}