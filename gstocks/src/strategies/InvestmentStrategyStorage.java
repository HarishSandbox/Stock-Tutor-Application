package strategies;

import java.util.ArrayList;
import java.util.List;

public class InvestmentStrategyStorage implements IStrategyStorage {

  private final List<InvestmentStrategies> strategies;
  private static IStrategyStorage instance;

  private InvestmentStrategyStorage() {

    strategies = new ArrayList<>();
  }

  public static IStrategyStorage getInstance() {

    if (instance == null) {

      instance = new InvestmentStrategyStorage();
    }
    return instance;
  }

  @Override
  public void addInvestmentStrategy(InvestmentStrategies strategy) throws IllegalArgumentException {

    strategies.add(strategy);
  }

  @Override
  public List<InvestmentStrategies> getAllStrategies() {
    
    return strategies;
  }

  @Override
  public InvestmentStrategies getInvestmentStrategy(String strategy)
          throws IllegalArgumentException {

    if (strategy == null) {

      throw new IllegalArgumentException("Strategy cannot be null");
    }
    if (strategy.equals("")) {

      throw new IllegalArgumentException("Strategy name cannot be empty");
    }

    return getStrategyObj(strategy);
  }

  private InvestmentStrategies getStrategyObj(String strategyName) throws IllegalArgumentException {

    InvestmentStrategies strategy = null;
    for (InvestmentStrategies strategyObj : this.strategies) {

      if (strategyObj.getStrategyName().equals(strategyName)) {

        strategy = strategyObj;
        break;
      }
    }
    if (strategy == null) {

      throw new IllegalArgumentException("Strategy does not exist");
    }
    return strategy;
  }
}
