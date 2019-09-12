package controller.commands;

import controller.FeaturesExtended;
import view.IStockTutorView;
import view.ITextBasedView;

/**
 * This class provides the implementation of command used to get a strategy from a file. This class
 * implements {@link ITutorCommand} interface.
 */
public class GetStrategyFromFile implements ITutorCommand {

  private final ITextBasedView view;
  private final FeaturesExtended feature;

  /**
   * Creates a SavePortfolioToFile object by taking in the view component.
   *
   * @param view    A {@link IStockTutorView} object which is the view of the application.
   * @param feature The features object which can be used to call for action for the operation to be
   *                done.
   */
  public GetStrategyFromFile(ITextBasedView view, FeaturesExtended feature) {
    this.view = view;
    this.feature = feature;
  }

  @Override
  public void performCommand() throws IllegalArgumentException {

    view.displayInfo("Enter the absolute path from where the file has to be fetched.");
    String filePath = view.getStringInput();

    try {
      feature.getStrategy(filePath);
      view.displayMenu();
    } catch (IllegalArgumentException e) {
      view.displayInfo(e.getMessage());
      view.displayMenu();
    }
  }
}
