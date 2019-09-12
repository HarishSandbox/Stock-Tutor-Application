package model;

/**
 * The stock storage interface provides ways to read and write to the storage units of the
 * application. This interface has methods to read and add new Stocks to the storage
 * classes/repositories.
 */
interface IStockStorage {

  /**
   * Gets a stock and adds it to the repository.
   *
   * @param stock A {@code Stock} object to be added to the repository.
   * @throws IllegalArgumentException when a stock is already present in the database.
   */
  void addStock(Stock stock) throws IllegalArgumentException;

  /**
   * Retrieves a {@code stock} from the repository when given a ticker symbol of the stock. It
   * return null if there is no stock present in the repo.
   *
   * @param ticker A string ticker symbol of the stock.
   * @return A {@code Stock} object of the stock if present, else null.
   */
  Stock getStock(String ticker);
}