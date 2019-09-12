package model;

import java.util.Date;
import java.util.List;

/**
 * This class implements {@link IPortfolioInfo} interface. It represents a simple adapter of the
 * {@code Portfolio} class and provides implementation to get details of the portfolio.
 */
public class PortfolioInfo implements IPortfolioInfo {

  private final IPortfolio portfolioObj;

  /**
   * It creates a PortfolioInfo object by taking in a IPortfolio object.
   *
   * @param portfolioObj A IPortfolio object which has to be wrapped to represent it as a
   *                     PortfolioInfo objcet.
   */
  PortfolioInfo(IPortfolio portfolioObj) {
    this.portfolioObj = portfolioObj;
  }

  @Override
  public List<IPurchaseHistory> getPortfolioItems() {

    return portfolioObj.getPortfolioItems();
  }

  @Override
  public String getPortfolioName() {
    return portfolioObj.getPortfolioName();
  }

  @Override
  public float getTotalCostBasis(Date date) throws IllegalArgumentException {
    return portfolioObj.getTotalCostBasis(date);
  }

  @Override
  public float getTotalValueForGivenDate(Date date) throws IllegalArgumentException {
    return portfolioObj.getTotalValueForGivenDate(date);
  }
}
