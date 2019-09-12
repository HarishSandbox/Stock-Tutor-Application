package model;

import java.io.InputStream;
import java.util.Date;

import strategies.InvestmentStrategy;
import strategies.StrategyStorage;

/**
 * This class extends StockTutorModelExtended class and implements IStockTutorStrategy interface. It
 * has all the implementations that process strategic purchase related to investment strategies and
 * file reading and writing of the strategies.Commission fee is added at the time of purchase and a
 * purchase may or may not have commission fee.
 *
 * <p>
 * If a stock has to be bought on a public holiday it checks for a working date after the public
 * holiday and makes a purchase.
 * </p>
 */
public class StockTutorStrategy extends StockTutorModelExtended implements IStockTutorStrategy {

  @Override
  public void saveStrategyToFile(String strategy, String fileURL) {

    InvestmentStrategy strategyObj =
            StrategyStorage.getInstance().getInvestmentStrategy(strategy);
    StrategyFileHandler.getInstance().saveStrategyToFile(strategyObj, fileURL);
  }

  @Override
  public InvestmentStrategy getStrategyFromFile(InputStream fileURL)
          throws IllegalStateException {

    InvestmentStrategy strategy = StrategyFileHandler.getInstance().getStrategy(fileURL);
    StrategyStorage.getInstance().addInvestmentStrategy(strategy);
    return strategy;
  }

  @Override
  public float buyPartialStocksForStrategy(String portfolio, String tickerSymbol, float amount,
                                           Date dateOfPurchase, float commissionFees)
          throws IllegalArgumentException {
    float priceOfStock;
    try {
      priceOfStock = getStockPriceForInvestment(tickerSymbol, dateOfPurchase);
    } catch (Exception e) {
      throw new IllegalArgumentException("Date should not be null or Invalid");
    }


    if (commissionFees < 0) {
      throw new IllegalArgumentException("Commission fees cannot be negative");
    }

    IPortfolio portfolioObj;
    try {
      portfolioObj = this.getPortfolioObj(portfolio);
    } catch (IllegalArgumentException exp) {

      if (exp.getMessage().equals("Portfolio does not exist")) {

        this.createPortfolio(portfolio);
        portfolioObj = this.getPortfolioObj(portfolio);
      } else {

        throw exp;
      }
    }

    float amountLeftAfterCommission;

    if (commissionFees >= amount) {
      throw new IllegalArgumentException("Commission fees cannot exceed purchase amount");
    } else {
      amountLeftAfterCommission = amount - commissionFees;
    }

    // Calculate no of quantities that can be bought for the given amount
    float quantity = (amountLeftAfterCommission / priceOfStock);

    // Add stock purchased to portfolio
    portfolioObj.addPurchase(new PurchaseHistoryCommission(tickerSymbol, quantity,
            amountLeftAfterCommission, dateOfPurchase, commissionFees));
    return 0;
  }

  private float getStockPriceForInvestment(String ticker, Date date)
          throws IllegalArgumentException {

    Stock stock = getStock(ticker);
    return stock.getPriceOfNearestWorkingDay(date);
  }
}