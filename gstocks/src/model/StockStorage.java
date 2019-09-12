package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The local stock storage for this application. It stores the stock objects retrieved at runtime in
 * the system memory. This class implements {@link IStockStorage} interface.
 */
class StockStorage implements IStockStorage {

  private static StockStorage instanceVariable;
  private List<Stock> listOfStocks;

  private StockStorage() {
    this.listOfStocks = new ArrayList<>();
  }

  /**
   * Returns the instance of this class which is lazily initialized.
   *
   * @return The instance of this class.
   */
  public static StockStorage getInstance() {

    if (instanceVariable == null) {
      instanceVariable = new StockStorage();
    }
    return instanceVariable;
  }

  @Override
  public void addStock(Stock stock) throws IllegalArgumentException {

    if (isStockPresent(stock)) {
      throw new IllegalArgumentException("Stock already present.");
    }
    listOfStocks.add(stock);
  }

  private boolean isStockPresent(Stock stock) {

    Stock oldStock = listOfStocks.stream().filter(s -> s.getTickerSymbol().
            equalsIgnoreCase(stock.getTickerSymbol())).findAny().orElse(null);
    return (oldStock != null);
  }

  @Override
  public Stock getStock(String tickerSymbol) {

    if (this.listOfStocks.isEmpty()) {
      return null;
    }
    for (Stock stock : this.listOfStocks) {
      if (stock.getTickerSymbol().equals(tickerSymbol)) {
        return stock;
      }
    }
    return null;
  }

}
