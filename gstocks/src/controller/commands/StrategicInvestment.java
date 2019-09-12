package controller.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.FeaturesExtended;
import util.DateUtility;
import view.IStockTutorView;
import view.ITextBasedView;

/**
 * This command takes the necessary input to create a investment strategy and apply it to a
 * portfolio. It provides user an option to select one of three strategies : Equal weights
 * non-recurring investment, Custom weights non-recurring investment and Dollar cost averaging
 * investment. This class implements {@link ITutorCommand} interface.
 */
public class StrategicInvestment implements ITutorCommand {

  private final ITextBasedView view;
  private final FeaturesExtended feature;

  /**
   * Creates a StrategicInvestment command object and takes a view object to interact with it.
   *
   * @param view A {@link IStockTutorView} object with is the view component of the application.
   * @param feature {@link FeaturesExtended} object which has features related to strategy.
   */
  public StrategicInvestment(ITextBasedView view, FeaturesExtended feature) {
    this.view = view;
    this.feature = feature;
  }

  @Override
  public void performCommand() throws IllegalArgumentException {

    view.displayInfo("Enter the name of the strategy");
    String strategyName = view.getStringInput();

    view.displayInfo("Enter the number of stocks to add to the plan.");
    int numberOfStocks = view.getInt();

    view.displayInfo("Enter the option number for the type of investment.");
    view.displayInfo("1. Equal weights non-recurring investment. \n2. Custom weights " +
            "non-recurring investment. \n3. Dollar cost averaging investment.");
    int investmentOption = view.getInt();

    view.displayInfo("Enter the investment amount");
    float investmentValue = view.getFloat();

    view.displayInfo("Enter the commission fee.");
    float commissionFee = view.getFloat();

    Date startDate = null;
    while (startDate == null) {

      if (investmentOption == 3) {
        view.displayInfo("Enter the start date in the format DD-MM-YYYY.");
      } else {
        view.displayInfo("Enter the date of investment in the format DD-MM-YYYY.");
      }

      String dateString = view.getStringInput();

      startDate = DateUtility.convertStringFromUserToDate(dateString + " 10:30:00");
    }

    int frequency = 0;
    Date endDate = null;
    if (investmentOption == 3) {
      view.displayInfo("Enter the number of days after which the investment has to recur again.");
      frequency = view.getInt();

      view.displayInfo("Do you want to specify an end date. Press 'y' for yes, " +
              "any other input for No");
      String endDateOption = view.getStringInput();

      if (endDateOption.equals("y") || endDateOption.equals("Y")) {

        view.displayInfo("Enter the end date.");
        endDate = view.getDate();
      }
    }

    if (investmentOption == 1) {

      List<String> stocks = new ArrayList<>();
      for (int i = 1; i <= numberOfStocks; i++) {

        view.displayInfo("Enter the ticker symbol of the stock.");
        String symbol = view.getStringInput();
        stocks.add(symbol);
      }
      try {
        if (!feature.createEqualWeightStrategy(strategyName, investmentValue, startDate,
                stocks, commissionFee)) {
          view.displayMenu();
          return;
        }
      } catch (IllegalArgumentException e) {
        view.displayInfo(e.getMessage());
        view.displayMenu();
        return;
      }
    } else {

      Map<String, Float> stocks = new HashMap<>();

      for (int i = 1; i <= numberOfStocks; i++) {

        view.displayInfo("Enter the ticker symbol of the stock.");
        String symbol = view.getStringInput();
        view.displayInfo("Enter the weight of the stock in percent on a scale of 100%.");
        float weight = view.getFloat();
        stocks.put(symbol, weight);
      }
      try {
        if (investmentOption == 2) {

          if (!feature.createCustomWeightStrategy(strategyName, investmentValue, startDate, stocks,
                  commissionFee)) {
            view.displayMenu();
            return;
          }
        } else {
          if (!feature.createDollarCostStrategy(strategyName, investmentValue, frequency, startDate,
                  endDate, stocks, commissionFee)) {
            view.displayMenu();
            return;
          }
        }
      } catch (IllegalArgumentException e) {
        view.displayInfo(e.getMessage());
        view.displayMenu();
        return;
      }
    }

    view.displayInfo("Enter the name of the portfolio.");
    String portfolioName = view.getStringInput();

    feature.applyStrategy(portfolioName, strategyName);
    view.displayMenu();
  }
}