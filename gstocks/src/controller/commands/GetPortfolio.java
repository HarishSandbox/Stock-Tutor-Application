package controller.commands;

import controller.Features;
import view.ITextBasedView;
import view.IStockTutorView;

/**
 * This class provides the implementation of command used to get portfolio details. a specific
 * portfolio. This class implements {@link ITutorCommand} interface.
 */
public class GetPortfolio implements ITutorCommand {

  private final ITextBasedView view;
  private final Features feature;

  /**
   * Creates a GetPortfolio object by taking in the view component.
   *
   * @param view    A {@link IStockTutorView} object which is the view of the application.
   * @param feature The features object which can be used to call for action for the operation to be
   *                done.
   */
  public GetPortfolio(ITextBasedView view, Features feature) {
    this.view = view;
    this.feature = feature;
  }

  @Override
  public void performCommand() {

    view.displayInfo("Enter the portfolio name to be fetched");
    String portfolioName = view.getStringInput();
    try {
      feature.getPortfolio(portfolioName);
      view.displayMenu();
    } catch (IllegalArgumentException e) {
      view.displayInfo(e.getMessage());
      view.displayMenu();
    }
  }
}
