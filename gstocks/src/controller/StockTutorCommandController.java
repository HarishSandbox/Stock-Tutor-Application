package controller;

import java.util.HashMap;
import java.util.Map;

import controller.commands.BuyStock;
import controller.commands.CostBasis;
import controller.commands.CreatePortfolio;
import controller.commands.GetAllPortfolioForUser;
import controller.commands.GetPortfolio;
import controller.commands.GetPortfolioFromFile;
import controller.commands.ITutorCommand;
import controller.commands.SavePortfolioToFile;
import controller.commands.TotalValue;
import model.IStockTutorModelExtended;
import view.ITextBasedView;
import view.IStockTutorView;

/**
 * This is the controller class of the text based tutoring application. It implements {@link
 * IStockTutorController} interface. It is a point of communication between text based view and
 * controller, and controller and model. It acts to the actions done by the user on the view and
 * returns to the user with the necessary response.
 *
 * <p>The input is given in the form of commands.</p>
 */
public class StockTutorCommandController implements IStockTutorController {

  protected final ITextBasedView view;
  private final Features feature;

  protected Map<String, ITutorCommand> commands;

  /**
   * It creates an object of the controller by taking in a {@link IStockTutorModelExtended} and
   * {@link IStockTutorView} objects. It throws an exception if any of them are null.
   *
   * @param model A {@link IStockTutorModelExtended} which has to be used with the controller and
   *              has the business logic of the program.
   * @param view  The view part of the programme.
   * @throws IllegalArgumentException thrown when any of the arguments are null.
   */
  public StockTutorCommandController(IStockTutorModelExtended model, ITextBasedView view)
          throws IllegalArgumentException {

    if (model == null || view == null) {

      throw new IllegalArgumentException("Model or View cannot be null");
    }

    this.view = view;
    this.feature = new StockTutorMainController(model, view);
    this.commands = new HashMap<>();
    this.initializeCommands();
  }

  protected void initializeCommands() {

    commands.put("1", new CreatePortfolio(view, feature));
    commands.put("2", new GetAllPortfolioForUser(view, feature));
    commands.put("3", new GetPortfolio(view, feature));
    commands.put("4", new BuyStock(view, feature));
    commands.put("5", new CostBasis(view, feature));
    commands.put("6", new TotalValue(view, feature));
    commands.put("7", new SavePortfolioToFile(view, feature));
    commands.put("8", new GetPortfolioFromFile(view, feature));
  }

  @Override
  public void setView() {

    displayInstructions();
    view.displayMenu();
    ITutorCommand userInput;
    String input;

    while (true) {

      try {

        input = view.getStringInput();

        userInput = commands.getOrDefault(input, null);
        if (userInput == null) {

          view.displayInfo("Enter correct menu number.");
          continue;
        }
        userInput.performCommand();
      } catch (IllegalStateException e) {
        if (e.getMessage().equals("QUIT")) {
          view.displayInfo("***Application Closed***");
          return;
        }
      }
    }
  }

  private void displayInstructions() {

    String display = "This is a tutoring application for stock marketing.\n" +
            "The following user operations are supported by this \napplication to " +
            "teach you the working of a stock market.\n";
    view.displayInfo(display);
  }
}