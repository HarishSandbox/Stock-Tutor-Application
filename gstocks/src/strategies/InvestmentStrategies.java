package strategies;

import java.util.Date;
import java.util.Map;

public interface InvestmentStrategies {

  Date getStartDate();

  Date getEndDate();

  int getFrequencyOfInvestment();

  Float getPriceOfInvestment();

  Map<String,Float> getStockAndWeightsList();

  String getStrategyName();
}