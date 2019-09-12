package controller.commands;

import java.util.Date;

import controller.Features;
import view.ITextBasedView;
import view.IStockTutorView;

/**
 * This class provides the implementation of command used to buy a stock. This class implements
 * {@link ITutorCommand} interface.
 */
public class BuyStock implements ITutorCommand {

  private final ITextBasedView view;
  private final Features feature;

  /**
   * Creates a BuyStock command object and takes a view object to interact with it.
   *
   * @param view    A {@link IStockTutorView} object with is the view component of the application.
   * @param feature The features object which can be used to call for action for the operation to be
   *                done.
   */
  public BuyStock(ITextBasedView view, Features feature) {
    this.view = view;
    this.feature = feature;
  }

  @Override
  public void performCommand() throws IllegalArgumentException {

    view.displayInfo("Enter the name of the portfolio.");
    String portfolioName = view.getStringInput();

    view.displayInfo("Enter ticker symbol for the stock you want to purchase");
    String tickerSymbol = view.getStringInput();

    view.displayInfo("Enter date in dd-MM-YYYY HH:mm:ss (24hr format)");
    Date dateOfPurchase = view.getDate();

    view.displayInfo("Enter the purchase amount");
    float amount = view.getFloat();

    view.displayInfo("Do you want to enter commission fees? Press Y to enter commission fee else" +
            " press any other key");
    try {
      String option = view.getStringInput();
      if (option.equals("Y") || option.equals("y")) {
        view.displayInfo("Enter the commission fees");
        float commissionFees = view.getFloat();
        feature.buyStockWithCommission(portfolioName,
                tickerSymbol, amount, dateOfPurchase, commissionFees);
        view.displayMenu();
      } else {
        feature.buyStock(portfolioName, tickerSymbol, amount, dateOfPurchase);
        view.displayMenu();
      }
    } catch (IllegalArgumentException e) {
      view.displayInfo(e.getMessage());
      view.displayMenu();
    }
  }
}