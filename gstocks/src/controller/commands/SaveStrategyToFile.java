package controller.commands;

import controller.FeaturesExtended;
import view.IStockTutorView;
import view.ITextBasedView;

/**
 * This command takes the necessary input for saving a strategy to a file. This class implements
 * {@link ITutorCommand} interface.
 */
public class SaveStrategyToFile implements ITutorCommand {

  private final ITextBasedView view;
  private final FeaturesExtended feature;

  /**
   * Creates a SaveStrategyToFile command object and takes a view object to interact with it.
   *
   * @param view A {@link IStockTutorView} object with is the view component of the application.
   * @param feature {@link FeaturesExtended} object which has features related to strategy.
   */
  public SaveStrategyToFile(ITextBasedView view, FeaturesExtended feature) {
    this.view = view;
    this.feature = feature;
  }

  @Override
  public void performCommand() throws IllegalArgumentException {

    view.displayInfo("Enter the name of the strategy which has to be saved to file.");
    String strategyName = view.getStringInput();

    view.displayInfo("Enter the absolute path which includes the file name where the strategy " +
            "has to be saved.");
    String filePath = view.getStringInput();

    try {
      feature.saveStrategy(strategyName, filePath);
      view.displayMenu();
    } catch (IllegalArgumentException e) {
      view.displayInfo(e.getMessage());
      view.displayMenu();
    }
  }
}
