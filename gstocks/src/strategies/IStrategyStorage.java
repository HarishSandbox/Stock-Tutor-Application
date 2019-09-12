package strategies;

import java.util.List;

/**
 * This interface provides ways to store and fetch investment strategy objects within the
 * application. This interface has methods to read and add strategies to the storage
 * classes/repositories. It also has methods to get stored strategies.
 */
public interface IStrategyStorage {

  /**
   * This method adds an {@code InvestmentStrategy} object to the storage.
   *
   * @param strategy InvestmentStrategy object ot be stored.
   * @throws IllegalArgumentException when a strategy is already present in the database.
   */
  void addInvestmentStrategy(InvestmentStrategy strategy) throws IllegalArgumentException;

  /**
   * Retrieves List of {@code InvestmentStrategy} that are currently stored in the repository.
   *
   * @return list of {@code InvestmentStrategy} objects.
   */
  List<InvestmentStrategy> getAllStrategies();

  /**
   * Retrieves a {@code InvestmentStrategy} from the repository when given strategy name is given.
   *
   * @param strategy name of the strategy.
   * @return {@code InvestmentStrategy} object.
   * @throws IllegalArgumentException if the strategy string is null or empty.
   */
  InvestmentStrategy getInvestmentStrategy(String strategy)
          throws IllegalArgumentException;
}
