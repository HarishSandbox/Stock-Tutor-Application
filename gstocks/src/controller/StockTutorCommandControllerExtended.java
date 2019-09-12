package controller;

import controller.commands.GetStrategyFromFile;
import controller.commands.SaveStrategyToFile;
import controller.commands.StrategicInvestment;
import model.IStockTutorStrategy;
import view.ITextBasedView;

/**
 * This is the controller class of the text based tutoring application. It extends {@link
 * StockTutorCommandController} interface. It is a point of communication between text based view
 * and controller, and controller and model. It acts to the actions done by the user on the view and
 * returns to the user with the necessary response.
 *
 * <p>The input is given in the form of commands.</p>
 */
public class StockTutorCommandControllerExtended extends StockTutorCommandController {

  private final FeaturesExtended featureExtended;

  /**
   * It creates an object of the controller by taking in a {@link IStockTutorStrategy} and {@link
   * ITextBasedView} objects. It throws an exception if any of them are null.
   *
   * @param model A {@link IStockTutorStrategy} which has to be used with the controller and has the
   *              business logic of the program.
   * @param view  The view {@code ITextBasedView} which is part of the program.
   * @throws IllegalArgumentException thrown when any of the arguments are null.
   */
  public StockTutorCommandControllerExtended(IStockTutorStrategy model, ITextBasedView view)
          throws IllegalArgumentException {

    super(model, view);
    this.featureExtended = new StockTutorMainControllerExtended(model, view);
    initializeCommands();
  }

  protected void initializeCommands() {

    super.initializeCommands();
    commands.put("9", new StrategicInvestment(view, featureExtended));
    commands.put("10", new SaveStrategyToFile(view, featureExtended));
    commands.put("11", new GetStrategyFromFile(view, featureExtended));
  }
}