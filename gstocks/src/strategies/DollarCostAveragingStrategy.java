package strategies;

import java.util.Date;
import java.util.Map;

public class DollarCostAveragingStrategy implements InvestmentStrategies {

  private final String strategyName;
  private final Date startDate;
  private final Date endDate;
  private final int frequency;
  private final float price;
  private final Map<String,Float> stocks;

  public DollarCostAveragingStrategy(String strategyName, Date startDate, Date endDate,
                                     int frequency, float price,
                                     Map<String,Float> stocks) {

    this.strategyName = strategyName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.frequency = frequency;
    this.price = price;
    this.stocks = stocks;
  }

  public DollarCostAveragingStrategy(String strategyName, Date startDate, int frequency,
                                     float price,
                                     Map<String,Float> stocks) {

    this.strategyName = strategyName;
    this.startDate = startDate;
    this.endDate = null;
    this.frequency = frequency;
    this.price = price;
    this.stocks = stocks;
  }

  @Override
  public Date getStartDate() {

    return startDate;
  }

  @Override
  public Date getEndDate() {

    return endDate;
  }

  @Override
  public int getFrequencyOfInvestment() {

    return frequency;
  }

  @Override
  public Float getPriceOfInvestment() {

    return price;
  }

  @Override
  public Map<String, Float> getStockAndWeightsList() {

    return stocks;
  }

  @Override
  public String getStrategyName() {

    return strategyName;
  }
}