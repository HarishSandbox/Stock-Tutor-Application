package controller.commandsextended;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.FeaturesExtended;
import view.IStockTutorView;
import view.ITextBasedView;

public class StrategicInvestment implements ITutorCommandExtended {

  private final ITextBasedView view;

  /**
   * Creates a BuyStock command object and takes a view object to interact with it.
   *
   * @param view A {@link IStockTutorView} object with is the view component of the application.
   */
  public StrategicInvestment(ITextBasedView view) {
    this.view = view;
  }

  @Override
  public void performCommand(FeaturesExtended feature) throws IllegalArgumentException {

    view.displayInfo("Enter the name of the strategy");
    String strategyName = view.getStringInput();

    view.displayInfo("Enter the number of stocks to add to the plan.");
    int numberOfStocks = view.getInt();

    view.displayInfo("Enter the option number for the type of investment.");
    view.displayInfo("1. Equal weights investment. \n 2. Dollar cost averaging investment.");
    int investmentOption = view.getInt();

    view.displayInfo("Enter the monthly investment to be made.");
    float investmentValue = view.getFloat();

    view.displayInfo("Enter the number of days after which the investment has to recur again.");
    int frequency = view.getInt();

    view.displayInfo("Enter the start date.");
    Date startDate = view.getDate();

    view.displayInfo("Do you want to specify an end date. Press 'y' for yes, any other input for " +
            "No");
    String endDateOption = view.getStringInput();
    Date endDate = null;
    if (endDateOption == "y" || endDateOption == "Y") {

      view.displayInfo("Enter the end date.");
      endDate = view.getDate();
    }

    if (investmentOption == 1) {

      List<String> stocks = new ArrayList<>();
      for (int i = 1; i <= numberOfStocks; i++) {

        view.displayInfo("Enter the ticker symbol of the stock.");
        String symbol = view.getStringInput();
        stocks.add(symbol);
      }

      feature.createEqualWeightStrategy(strategyName,investmentValue,frequency,startDate,endDate,
              stocks);
    }
    else {

      Map<String,Float> stocks = new HashMap<>();

      for (int i = 1; i <= numberOfStocks; i++) {

        view.displayInfo("Enter the ticker symbol of the stock.");
        String symbol = view.getStringInput();
        view.displayInfo("Enter the weight of the stock in percent on a scale of 100%.");
        float weight = view.getFloat();
        stocks.put(symbol,weight);
      }

      feature.createDollarCostStrategy(strategyName,investmentValue,frequency,startDate,endDate,
              stocks);
    }

    view.displayInfo("Enter the name of the portfolio.");
    String portfolioName = view.getStringInput();

    feature.applyStrategy(portfolioName, strategyName);
  }
}