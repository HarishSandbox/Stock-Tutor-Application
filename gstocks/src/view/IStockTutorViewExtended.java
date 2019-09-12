package view;

import controller.FeaturesExtended;
import strategies.InvestmentStrategy;

/**
 * An extended interface for view component of the Stock tutoring application. It is responsible for
 * getting appropriate input from the user to the controller, and passing the info sent by the
 * controller to the user.
 */
public interface IStockTutorViewExtended extends IStockTutorView {

  void setFeaturesExtended(FeaturesExtended feature);

  /**
   * A method that displays information about a strategy being applied.
   *
   * @param strategy investment strategy
   */
  void displayStrategy(InvestmentStrategy strategy);

}
