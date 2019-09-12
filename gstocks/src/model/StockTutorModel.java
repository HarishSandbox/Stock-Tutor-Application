package model;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import util.DateUtility;

/**
 * The model class of the tutoring application. This model only allows trading during thw working
 * hours of 9 AM to 4PM on working days. Stocks cannot be bought on holidays or after after hours.
 *
 * <p> It implements {@link IStockTutorModel} interface.</p>
 *
 * <p>Previously the class has returned String representations of Portfolio details and list of
 * portfolios. In this implementation it has been corrected to represent IPortfolioInfo objects
 * instead of strings. These objects provide readonly ways to get data about Portfolio.</p>
 */
public class StockTutorModel implements IStockTutorModel {

  private final IPortfolioStorage portfolioStorage;
  private final IStockStorage stockStorage;

  /**
   * This creates a new model object which is the main point of contact for the controller. It
   * initializes portfolio storage and stock storage instance variables. not take any arguments.
   */
  public StockTutorModel() {

    this.portfolioStorage = PortfolioStorage.getInstance();
    this.stockStorage = StockStorage.getInstance();
  }

  @Override
  public float getStockPrice(String tickerSymbol, Date date) throws IllegalArgumentException {

    if (tickerSymbol == null || tickerSymbol.equals("")) {
      throw new IllegalArgumentException("Illegal ticker symbol received.");
    }
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null or Invalid");
    }

    Stock stock = getStock(tickerSymbol);
    return stock.getPrice(date);
  }

  @Override
  public void createPortfolio(String portfolioName) throws IllegalArgumentException {

    if (portfolioName == null) {
      throw new IllegalArgumentException("Portfolio cannot be null");
    }
    if (portfolioName.equals("")) {
      throw new IllegalArgumentException("Portfolio name cannot be empty");
    }
    if (!isPortfolioNameUnique(portfolioName)) {
      throw new IllegalArgumentException("Portfolio name should be unique");
    }

    Pattern p = Pattern.compile("[^A-Za-z0-9 ]", Pattern.CASE_INSENSITIVE);
    Matcher matcher = p.matcher(portfolioName);

    if (matcher.find()) {
      throw new IllegalArgumentException("Portfolio cannot have special characters.");
    }
    portfolioStorage.addPortfolio(new Portfolio(portfolioName));
  }

  private boolean isPortfolioNameUnique(String name) {

    List<IPortfolio> portfolios = portfolioStorage.getAllUserPortfolios();
    IPortfolio portfolio =
            portfolios.stream().filter(p -> p.getPortfolioName()
                    .equalsIgnoreCase(name)).findAny().orElse(null);

    return portfolio == null;
  }

  @Override
  public IPortfolioInfo getPortfolio(String portfolio) throws IllegalArgumentException {

    return new PortfolioInfo(portfolioStorage.getPortfolio(portfolio));
  }

  @Override
  public List<IPortfolioInfo> getAllUserPortfolio() {

    return portfolioStorage.getAllUserPortfolios().stream()
            .map(p -> new PortfolioInfo(p)).collect(Collectors.toList());
  }

  @Override
  public float getTotalCostBasis(String portfolio, Date date)
          throws IllegalArgumentException {
    return this.getPortfolioObj(portfolio).getTotalCostBasis(date);
  }

  @Override
  public float getTotalValueForGivenDate(String portfolio, Date date)
          throws IllegalArgumentException {
    return this.getPortfolioObj(portfolio).getTotalValueForGivenDate(date);
  }

  @Override
  public float buyStock(String portfolio, String tickerSymbol, float amount,
                        Date dateOfPurchase) throws IllegalArgumentException {

    float priceOfStock = validateAndGetPriceOfStock(tickerSymbol, amount, dateOfPurchase);

    IPortfolio portfolioObj = this.getPortfolioObj(portfolio);

    // Check for excess amount when buying the stocks
    float amountRemaining = amount % priceOfStock;

    // Calculate no. of quantities that can be bought for the given amount
    int quantity = (int) (amount / priceOfStock);

    float actualPurchaseAmount = amount - amountRemaining;

    // Add stock purchased to portfolio
    portfolioObj.addPurchase(new PurchaseHistory(tickerSymbol, quantity, actualPurchaseAmount,
            dateOfPurchase));
    return Float.parseFloat(String.format("%.2f", amountRemaining));
  }

  protected float validateAndGetPriceOfStock(String tickerSymbol, float amount,
                                             Date dateOfPurchase) {
    if (dateOfPurchase == null) {
      throw new IllegalArgumentException("Date should not be null or Invalid");
    }
    if (DateUtility.isDateAWeekend(dateOfPurchase)) {
      throw new IllegalArgumentException("Stock cannot be purchased on a holiday.");
    }
    if (!DateUtility.isDateInWorkingHours(dateOfPurchase)) {
      throw new IllegalArgumentException("Stock should be purchased between 9am - 4pm only.");
    }
    Stock currentStock = getStock(tickerSymbol);
    float priceOfStock = currentStock.getPrice(dateOfPurchase);
    if (amount < priceOfStock) {
      throw new IllegalArgumentException("The price of the stock is " + priceOfStock
              + ". Give "
              + "sufficient amount to make a purchase.");
    }
    return priceOfStock;
  }

  protected Stock getStock(String ticker) throws IllegalArgumentException {

    ticker = ticker.toUpperCase();

    Stock currentStock = stockStorage.getStock(ticker);
    if (currentStock == null) {
      IStockInputSource apiInputSource = new StockAPI();
      currentStock = apiInputSource.fetchStock(ticker);
      stockStorage.addStock(currentStock);
    }
    return currentStock;
  }

  protected IPortfolio getPortfolioObj(String portfolio) throws IllegalArgumentException {

    return portfolioStorage.getPortfolio(portfolio);
  }
}