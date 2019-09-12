package controller.commands;

import controller.Features;
import view.ITextBasedView;

/**
 * This class implements {@link ITutorCommand} interface. It provides the implementation details in
 * response to a create portfolio command.
 */
public class CreatePortfolio implements ITutorCommand {

  private final ITextBasedView view;
  private final Features feature;

  /**
   * Creates a CreatePortfolio object by taking in the view component.
   *
   * @param view    A {@link Features} object which is the view of the application.
   * @param feature The features object which can be used to call for action for the operation to be
   *                done.
   */
  public CreatePortfolio(ITextBasedView view, Features feature) {
    this.view = view;
    this.feature = feature;
  }

  @Override
  public void performCommand() throws IllegalArgumentException {

    view.displayInfo("Enter the name of the portfolio.");
    String portfolioName = view.getStringInput();
    try {
      feature.createPortfolio(portfolioName);
      view.displayMenu();
    } catch (IllegalArgumentException e) {
      view.displayInfo(e.getMessage());
      view.displayMenu();
    }
  }
}
