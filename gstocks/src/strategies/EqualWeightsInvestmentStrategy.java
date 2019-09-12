package strategies;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EqualWeightsInvestmentStrategy implements InvestmentStrategies {

  private final String strategyName;
  private final Date startDate;
  private final Date endDate;
  private final int frequency;
  private final float investmentValue;
  private final List<String> stocks;

  public EqualWeightsInvestmentStrategy(String strategyName, Date startDate, Date endDate,
                                         int frequency,
                                         float investment, List<String> stocks) {
    this.strategyName = strategyName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.frequency = frequency;
    this.investmentValue = investment;
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

    return investmentValue;
  }

  @Override
  public Map<String, Float> getStockAndWeightsList() {

    Map<String,Float> stocksWithWeights = new HashMap<>();

    if (stocks.size() != 0) {

      Float weight = 100.0f / stocks.size();
      for (String stockName : stocks) {

        stocksWithWeights.put(stockName,weight);
      }
      return stocksWithWeights;
    }
    return null;
  }

  @Override
  public String getStrategyName() {

    return strategyName;
  }
}