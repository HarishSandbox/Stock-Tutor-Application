package model;

import java.io.InputStream;

import strategies.InvestmentStrategy;

/**
 * This interface provides methods to persist strategy in an external file. It provides methods to
 * get the strategy from a file and save an existing strategy to a file. Each strategy object is
 * saved into a different file.
 *
 * <p>
 * The structure of the JSON is as follows:
 * </p>
 *
 * <p>
 * { "strategyName" : "name of strategy", "startDate": "Day, Month Date, YEAR HOUR:MINUTE:SECOND
 * AM/PM TIMEZONE", "frequency": frequency of investment in days, "price": price to invest each
 * time, "commissionFee": commission fee of investment, "stocks": { "Stock ticker symbol": stock
 * weightage for a total of 100 } }
 * </p>
 */
public interface IStrategyFileHandler {

  /**
   * This method fetches the strategy from an external file and loads it into the application.
   *
   * @param fileReader input stream which contains the strategy to be loaded.
   * @return investment strategy object
   * @throws IllegalStateException if it is unable to parse the input stream object.
   */
  InvestmentStrategy getStrategy(InputStream fileReader) throws IllegalStateException;

  /**
   * This method saves an existing strategy to an external file.
   *
   * @param strategy investment strategy object that is to be saved.
   * @param fileURL  path where the strategy is saved, specified as a string
   * @throws IllegalStateException if it is not able to save the strategy to a file.
   */
  void saveStrategyToFile(InvestmentStrategy strategy, String fileURL)
          throws IllegalStateException;
}
