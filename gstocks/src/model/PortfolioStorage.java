package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents portfolio storage in system memory and implements {@link IPortfolioStorage}
 * interface.
 */
class PortfolioStorage implements IPortfolioStorage {

  private static PortfolioStorage instanceVariable;
  private List<IPortfolio> listOfPortfolios;

  private PortfolioStorage() {
    this.listOfPortfolios = new ArrayList<>();
  }

  /**
   * Returns the instance of this class which is lazily initialized.
   *
   * @return The instance of this class.
   */
  static PortfolioStorage getInstance() {

    if (instanceVariable == null) {

      instanceVariable = new PortfolioStorage();
    }
    return instanceVariable;
  }

  @Override
  public void addPortfolio(IPortfolio portfolio) throws IllegalArgumentException {

    if (portfolio == null) {

      throw new IllegalArgumentException("Portfolio cannot be null.");
    }
    listOfPortfolios.add(portfolio);
  }

  @Override
  public List<IPortfolio> getAllUserPortfolios() {

    return listOfPortfolios;
  }

  @Override
  public IPortfolio getPortfolio(String portfolio) {

    if (portfolio == null) {

      throw new IllegalArgumentException("Portfolio cannot be null");
    }
    if (portfolio.equals("")) {

      throw new IllegalArgumentException("Portfolio name cannot be empty");
    }

    return getPortfolioObj(portfolio);
  }

  private IPortfolio getPortfolioObj(String portfolio) throws IllegalArgumentException {

    IPortfolio portfolioObj = null;
    for (IPortfolio eachPortfolio : this.listOfPortfolios) {

      if (eachPortfolio.getPortfolioName().equals(portfolio)) {

        portfolioObj = eachPortfolio;
      }
    }
    if (portfolioObj == null) {

      throw new IllegalArgumentException("Portfolio does not exist");
    }
    return portfolioObj;
  }
}