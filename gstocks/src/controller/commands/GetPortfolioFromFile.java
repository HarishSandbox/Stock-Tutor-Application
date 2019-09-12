package controller.commands;

import controller.Features;
import view.IStockTutorView;
import view.ITextBasedView;

/**
 * This class provides the implementation of command used to get a portfolio from a file. This class
 * implements {@link ITutorCommand} interface.
 */
public class GetPortfolioFromFile implements ITutorCommand {

  private final ITextBasedView view;
  private final Features feature;

  /**
   * Creates a SavePortfolioToFile object by taking in the view component.
   *
   * @param view    A {@link IStockTutorView} object which is the view of the application.
   * @param feature The features object which can be used to call for action for the operation to be
   *                done.
   */
  public GetPortfolioFromFile(ITextBasedView view, Features feature) {
    this.view = view;
    this.feature = feature;
  }

  @Override
  public void performCommand() throws IllegalArgumentException {

    view.displayInfo("Enter the absolute path from where the file has to be fetched.");
    String filePath = view.getStringInput();

    try {
      feature.getPortfolioFromFile(filePath);
      view.displayMenu();
    } catch (IllegalArgumentException e) {
      view.displayInfo(e.getMessage());
      view.displayMenu();
    }
  }
}
