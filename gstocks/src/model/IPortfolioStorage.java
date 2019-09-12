package model;

import java.util.List;

/**
 * This interface represents a storage for portfolios. It provides methods to add a portfolio, get
 * portfolios.
 */
interface IPortfolioStorage {

  /**
   * A method that adds the specified portfolio to the portfolio storage.
   *
   * @param portfolio the portfolio to be added.
   * @throws IllegalArgumentException if the portfolio already exists.
   */
  void addPortfolio(IPortfolio portfolio) throws IllegalArgumentException;

  /**
   * This method fetches all the existing portfolios for the user.
   *
   * @return list of portfolios that the user has created.
   */
  List<IPortfolio> getAllUserPortfolios();

  /**
   * Returns the name and details of the portfolio.
   *
   * @return A {@link  IPortfolio} object which represent the details of the portfolio.
   */
  IPortfolio getPortfolio(String portfolio) throws IllegalArgumentException;
}