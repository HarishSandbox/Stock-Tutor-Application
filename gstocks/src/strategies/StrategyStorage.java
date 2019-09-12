package strategies;

import java.util.ArrayList;
import java.util.List;

/**
 * The local investment strategy storage for this application. It stores the strategy objects which
 * is retrieved at runtime in the system memory. This class implements {@link StrategyStorage}
 * interface.
 */
public class StrategyStorage implements IStrategyStorage {

  private final List<InvestmentStrategy> strategies;
  private static IStrategyStorage instance;

  private StrategyStorage() {

    strategies = new ArrayList<>();
  }

  /**
   * Returns the instance of this class which is lazily initialized.
   *
   * @return The instance of this class.
   */
  public static IStrategyStorage getInstance() {

    if (instance == null) {

      instance = new StrategyStorage();
    }
    return instance;
  }

  @Override
  public void addInvestmentStrategy(InvestmentStrategy strategy) throws IllegalArgumentException {

    strategies.add(strategy);
  }

  @Override
  public List<InvestmentStrategy> getAllStrategies() {

    return strategies;
  }

  @Override
  public InvestmentStrategy getInvestmentStrategy(String strategy)
          throws IllegalArgumentException {

    if (strategy == null) {

      throw new IllegalArgumentException("Strategy cannot be null");
    }
    if (strategy.equals("")) {

      throw new IllegalArgumentException("Strategy name cannot be empty");
    }

    return getStrategyObj(strategy);
  }

  private InvestmentStrategy getStrategyObj(String strategyName) throws IllegalArgumentException {

    InvestmentStrategy strategy = null;
    for (InvestmentStrategy strategyObj : this.strategies) {

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
