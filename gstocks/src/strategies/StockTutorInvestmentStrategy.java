package strategies;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides method to get information about an investment strategy. It implements {@code
 * InvestmentStrategy}. It has getter methods to fetch details about an investment strategy.
 */
public class StockTutorInvestmentStrategy implements InvestmentStrategy {

  private final String strategyName;
  private final Date startDate;
  private final Date endDate;
  private final int frequency;
  private final float price;
  private final float commissionFee;
  private final Map<String, Float> stocks;

  private StockTutorInvestmentStrategy(String strategyName, Date startDate, Date endDate,
                                       int frequency, float price,
                                       Map<String, Float> stocks, float commissionFee) {

    this.strategyName = strategyName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.frequency = frequency;
    this.price = price;
    this.stocks = stocks;
    this.commissionFee = commissionFee;
  }

  /**
   * Builder method that creates an object of the builder class which is used as part of builder
   * design pattern.
   *
   * @param strategyName     name of the strategy.
   * @param investmentAmount investment amount for the strategy.
   * @param start            start date of the strategy.
   * @return {@code InvestmentStrategyBuilder} object.
   */
  public static InvestmentStrategyBuilder getBuilder(String strategyName,
                                                     Float investmentAmount,
                                                     Date start) throws IllegalArgumentException {

    return new StockTutorInvestmentBuilder(strategyName, investmentAmount, start);
  }

  /**
   * This is a StockTutorInvestmentBuilder class which follows builder design pattern to construct
   * an object of {@code StockTutorInvestmentStrategy} with customization. It provides methods to
   * set required variables of StockTutorInvestmentStrategy class.
   */
  public static class StockTutorInvestmentBuilder implements InvestmentStrategyBuilder {

    String strategyName;
    Date startDate;
    Float amount;
    Date endDate = null;
    int frequency = 0;
    Float commissionFee = 0.0f;
    Map<String, Float> stocksWithWeights;

    private StockTutorInvestmentBuilder(String strategyName, Float investmentAmount
            , Date start) throws IllegalArgumentException {
      validateStrategyName(strategyName);
      this.strategyName = strategyName;

      if (start == null) {
        throw new IllegalArgumentException("Date should not be null or Invalid");
      }

      this.startDate = start;
      if (investmentAmount <= 0) {
        throw new IllegalArgumentException("Investment amount can't be less than or equal to zero");
      }
      this.amount = investmentAmount;
      stocksWithWeights = new HashMap<>();
    }

    @Override
    public InvestmentStrategyBuilder frequency(int days) throws IllegalArgumentException {
      if (days <= 0) {
        throw new IllegalArgumentException("Frequency cannot be less than or equal to zero");
      }
      frequency = days;
      return this;
    }

    @Override
    public InvestmentStrategyBuilder startDate(Date date) {

      startDate = date;
      return this;
    }

    @Override
    public InvestmentStrategyBuilder endDate(Date date) {
      endDate = date;
      return this;
    }

    @Override
    public InvestmentStrategyBuilder amount(Float investment) {
      amount = investment;
      return this;
    }

    @Override
    public InvestmentStrategyBuilder stocks(Map<String, Float> stocks)
            throws IllegalArgumentException {

      float sum = 0;
      for (Float weight : stocks.values()) {
        sum += weight;
      }
      if (sum != 100) {
        throw new IllegalArgumentException("Weights dont sum up to 100");
      }
      stocksWithWeights = stocks;
      return this;
    }

    @Override
    public InvestmentStrategyBuilder commission(Float commission) {

      if (commission < 0) {
        throw new IllegalArgumentException("Commission fees cannot be negative");
      }
      if (commission >= amount) {
        throw new IllegalArgumentException("Commission fees cannot exceed purchase amount");
      }
      this.commissionFee = commission;
      return this;
    }

    @Override
    public InvestmentStrategy build() {

      return new StockTutorInvestmentStrategy(strategyName, startDate, endDate, frequency, amount,
              stocksWithWeights, commissionFee);
    }

    private void validateStrategyName(String strategyName) throws IllegalArgumentException {

      if (strategyName == null || strategyName.equals("")) {
        throw new IllegalArgumentException("Strategy cannot be null or empty");
      }

      if (!isStrategyNameUnique(strategyName)) {
        throw new IllegalArgumentException("Strategy name should be unique");
      }

      Pattern p = Pattern.compile("[^A-Za-z0-9 ]", Pattern.CASE_INSENSITIVE);
      Matcher matcher = p.matcher(strategyName);

      if (matcher.find()) {
        throw new IllegalArgumentException("Strategy cannot have special characters.");
      }
    }

    private boolean isStrategyNameUnique(String name) {

      IStrategyStorage strategyStorage = StrategyStorage.getInstance();
      List<InvestmentStrategy> strategies = strategyStorage.getAllStrategies();
      InvestmentStrategy strategy =
              strategies.stream().filter(p -> p.getStrategyName()
                      .equalsIgnoreCase(name)).findAny().orElse(null);

      return strategy == null;
    }
  }

  @Override
  public Date getStartDate() {

    return startDate;
  }

  @Override
  public Date getEndDate() {

    if (endDate == null && frequency == 0) {
      return new Date();
    }
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
  public Map<String, Float> getStockAndWeights() {

    return stocks;
  }

  @Override
  public String getStrategyName() {

    return strategyName;
  }

  @Override
  public Float getCommissionFee() {

    return commissionFee;
  }
}