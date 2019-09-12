package controller.commands;

import controller.Features;
import view.IStockTutorView;
import view.ITextBasedView;

/**
 * This command takes the necessary input for saving a portfolio to a file. This class implements
 * {@link ITutorCommand} interface.
 */
public class SavePortfolioToFile implements ITutorCommand {

  private final ITextBasedView view;
  private final Features feature;

  /**
   * Creates a SavePortfolioToFile object by taking in the view component.
   *
   * @param view    A {@link IStockTutorView} object which is the view of the application.
   * @param feature The features object which can be used to call for action for the operation to be
   *                done.
   */
  public SavePortfolioToFile(ITextBasedView view, Features feature) {
    this.view = view;
    this.feature = feature;
  }

  @Override
  public void performCommand() throws IllegalArgumentException {

    view.displayInfo("Enter the name of the portfolio which has to be saved to file.");
    String portfolioName = view.getStringInput();

    view.displayInfo("Enter the absolute path which includes the file name where the portfolio " +
            "has to be saved.");
    String filePath = view.getStringInput();

    try {
      feature.savePortfolio(portfolioName, filePath);
      view.displayMenu();
    } catch (IllegalArgumentException e) {
      view.displayInfo(e.getMessage());
      view.displayMenu();
    }
  }
}
