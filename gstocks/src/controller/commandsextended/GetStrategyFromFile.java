package controller.commandsextended;

import controller.FeaturesExtended;
import view.IStockTutorView;
import view.ITextBasedView;

public class GetStrategyFromFile implements ITutorCommandExtended {

  private final ITextBasedView view;

  /**
   * Creates a SavePortfolioToFile object by taking in the view component.
   *
   * @param view A {@link IStockTutorView} object which is the view of the application.
   */
  public GetStrategyFromFile(ITextBasedView view) {
    this.view = view;
  }

  @Override
  public void performCommand(FeaturesExtended feature) throws IllegalArgumentException {

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
