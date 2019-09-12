package controller.commands;

import java.util.Date;

import controller.Features;
import view.ITextBasedView;
import view.IStockTutorView;

/**
 * This class provides the implementation of command used to calculate total cost basis for a given
 * portfolio on a given date. This class implements {@link ITutorCommand} interface.
 */
public class CostBasis implements ITutorCommand {

  private final ITextBasedView view;
  private final Features feature;

  /**
   * Creates a CostBasis object by taking in the view component.
   *
   * @param view    A {@link IStockTutorView} object which is the view of the application.
   * @param feature The features object which can be used to call for action for the operation to be
   *                done.
   */
  public CostBasis(ITextBasedView view, Features feature) {
    this.view = view;
    this.feature = feature;
  }

  @Override
  public void performCommand() {

    view.displayInfo("Enter the name of the portfolio.");
    String portfolioName = view.getStringInput();

    view.displayInfo("Enter date as dd-MM-YYYY HH:mm:ss (24hr format)");
    Date date = view.getDate();

    try {
      feature.getTotalCostBasis(portfolioName, date);
      view.displayMenu();
    } catch (IllegalArgumentException e) {
      view.displayInfo(e.getMessage());
      view.displayMenu();
    }
  }
}
