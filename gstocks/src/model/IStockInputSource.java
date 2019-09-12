package model;

/**
 * This interface provides operations to support fetching of a stock over the network.
 */
interface IStockInputSource {

  /**
   * This method takes in a ticker symbol and fetches the {@code Stock} of the symbol.
   *
   * @param ticker the unique symbol of the company.
   * @return A {@code Stock} object corresponding to the symbol which is fetched.
   * @throws IllegalStateException thrown when there are issues fetching or parsing the response
   *                               from the network.
   */
  Stock fetchStock(String ticker) throws IllegalStateException;
}
