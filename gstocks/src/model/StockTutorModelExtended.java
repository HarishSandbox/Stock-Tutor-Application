package model;

import java.io.InputStream;
import java.util.Date;

import util.ViewUtils;

/**
 * This class extends StockTutorModel class and implements IStockTutorModelExtended interface. It
 * has all the implementations that process commission fee and file reading and writing. Commission
 * fee is added at the time of purchase and a purchase may or may not have comission fee. The
 * commission fee is added into the purchase amount once per transaction.
 */
public class StockTutorModelExtended extends StockTutorModel
        implements IStockTutorModelExtended {

  private FileHandler portfolioFileHandler;

  /**
   * Creates an instance of StockTutorModelExtended which handles commission fee and file save and
   * open operations.
   */
  public StockTutorModelExtended() {

    super();
    portfolioFileHandler = FileHandler.getInstance();
  }

  @Override
  public float buyStockWithCommission(String portfolio, String tickerSymbol,
                                      float amount, Date dateOfPurchase,
                                      float commissionFees)
          throws IllegalArgumentException {

    float priceOfStock = validateAndGetPriceOfStock(tickerSymbol, amount, dateOfPurchase);

    if (commissionFees < 0) {
      throw new IllegalArgumentException("Commission fees cannot be negative");
    }
    IPortfolio portfolioObj = this.getPortfolioObj(portfolio);

    float amountLeftAfterCommission;

    if (commissionFees >= amount) {
      throw new IllegalArgumentException("Commission fees cannot exceed purchase amount");
    } else {
      amountLeftAfterCommission = amount - commissionFees;
    }
    // Check for excess amount when buying the stocks
    float amountRemaining = amountLeftAfterCommission % priceOfStock;

    // Calculate no of quantities that can be bought for the given amount
    int quantity = (int) (amountLeftAfterCommission / priceOfStock);

    float actualPurchaseAmount = amount - amountRemaining;

    // Add stock purchased to portfolio
    portfolioObj.addPurchase(new PurchaseHistory(tickerSymbol, quantity, actualPurchaseAmount,
            dateOfPurchase));

    return Float.parseFloat(String.format("%.2f", amountRemaining));
  }

  @Override
  public void savePortfolioToFile(String fileUrl, String portfolio) throws IllegalStateException {

    if (fileUrl == null || fileUrl.equals("") || portfolio == null ||
            !ViewUtils.validatePortfolioNameInput(portfolio)) {

      throw new IllegalStateException("Invalid input parameters");
    }

    IPortfolioInfo portfolioInfo = getPortfolio(portfolio);
    portfolioFileHandler.savePortfolioToFile(portfolioInfo, fileUrl);
  }

  @Override
  public IPortfolioInfo getPortfolioFromFile(InputStream stream) throws IllegalStateException {

    if (stream == null) {

      throw new IllegalStateException("Invalid input parameters");
    }

    IPortfolioInfo portfolio = portfolioFileHandler.getPortfolio(stream);
    IPortfolio portfolioObj = new Portfolio(portfolio.getPortfolioName(),
            portfolio.getPortfolioItems());
    PortfolioStorage.getInstance().addPortfolio(portfolioObj);

    if (portfolio == null) {

      throw new IllegalStateException("No portfolios found.");
    }
    return portfolio;
  }
}
