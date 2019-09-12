package controller;

import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.IStockTutorStrategy;
import strategies.InvestmentStrategy;
import strategies.InvestmentStrategyBuilder;
import strategies.StrategyStorage;
import strategies.StockTutorInvestmentStrategy;
import util.DateUtility;
import view.IStockTutorViewExtended;

/**
 * This is the controller class of the tutoring application. It implements {@link FeaturesExtended}
 * interface and extends {@link StockTutorMainController}. It is a point of communication between
 * view and controller, and controller and model. It acts to the actions done by the user on the
 * view and returns to the user with the necessary response.
 */
public class StockTutorMainControllerExtended extends StockTutorMainController
        implements FeaturesExtended {

  private final IStockTutorStrategy strategyModel;
  private final IStockTutorViewExtended strategyView;

  /**
   * This constructor creates an object of the constructor by taking in a view and model and
   * connects the components of the application.
   *
   * @param model A {@code IStockTutorModelExtended} object that represents the model of the
   *              application.
   * @param view  A {@code IStockTutorViewExtended} object which represents the view of the
   *              application.
   * @throws IllegalArgumentException thrown when any of the arguments are null.
   */
  public StockTutorMainControllerExtended(IStockTutorStrategy model, IStockTutorViewExtended view)
          throws IllegalArgumentException {

    super(model, view);
    strategyModel = model;
    strategyView = view;
  }

  @Override
  public boolean createEqualWeightStrategy(String name, float investmentAmount, Date startDate,
                                           List<String> stocks, float commissionFee) {
    if (!validateStrategy(name, investmentAmount, startDate)) {
      return false;
    }
    Map<String, Float> stocksWithWeights = new HashMap<>();

    if (stocks.size() != 0) {

      Float weight = 100.0f / stocks.size();
      for (String stockName : stocks) {

        stocksWithWeights.put(stockName, weight);
      }
    }

    return createCustomWeightStrategy(name, investmentAmount, startDate, stocksWithWeights,
            commissionFee);
  }

  @Override
  public boolean createCustomWeightStrategy(String name, float investmentAmount, Date startDate,
                                            Map<String, Float> stocks, float commissionFee)
          throws IllegalArgumentException {

    try {
      InvestmentStrategyBuilder builder = StockTutorInvestmentStrategy.getBuilder(name,
              investmentAmount, startDate).commission(commissionFee).stocks(stocks);
      InvestmentStrategy strategy = builder.build();
      StrategyStorage.getInstance().addInvestmentStrategy(strategy);
    } catch (IllegalArgumentException e) {
      strategyView.displayErrorMsg(e.getMessage());
      return false;
    }
    strategyView.displayInfo("Strategy is created.");
    return true;
  }

  @Override
  public boolean createDollarCostStrategy(String name, float investmentAmount, int frequency,
                                          Date startDate, Date endDate, Map<String, Float> stocks,
                                          float commissionFee) {
    if (!validateStrategy(name, investmentAmount, startDate)) {
      return false;
    }

    if (endDate != null && DateUtility.isGivenDateLessThanOrEqualToOtherDate(endDate, startDate)) {
      strategyView.displayErrorMsg("End date cannot be greater than start date");
      return false;
    }

    try {
      InvestmentStrategyBuilder builder = StockTutorInvestmentStrategy.getBuilder(name,
              investmentAmount, startDate).commission(commissionFee).stocks(stocks)
              .frequency(frequency).endDate(endDate);
      InvestmentStrategy strategy = builder.build();
      StrategyStorage.getInstance().addInvestmentStrategy(strategy);
    } catch (IllegalArgumentException e) {
      strategyView.displayErrorMsg(e.getMessage());
      return false;
    }

    strategyView.displayInfo("Strategy is created.");
    return true;
  }

  @Override
  public void applyStrategy(String portfolioName, String strategy) {
    InvestmentStrategy strategyObj;
    try {
      strategyObj =
              StrategyStorage.getInstance().getInvestmentStrategy(strategy);
    } catch (IllegalArgumentException e) {
      strategyView.displayErrorMsg(e.getMessage());
      return;
    }
    Date start = strategyObj.getStartDate();
    try {
      if (!DateUtility.isNotFutureDateForInvestment(start)) {

        strategyView.displayInfo("Strategy cannot be applied to future date");
        return;
      }
    } catch (Exception exp) {

      strategyView.displayErrorMsg(exp.getMessage());
      return;
    }
    Date end = strategyObj.getEndDate();
    Date today = new Date();
    if (end == null || !DateUtility.isNotFutureDateForInvestment(end)) {

      end = today;
    }

    int frequency = strategyObj.getFrequencyOfInvestment();

    float commissionFee = strategyObj.getCommissionFee();
    float amount = strategyObj.getPriceOfInvestment();
    Map<String, Float> stocks = strategyObj.getStockAndWeights();

    if (strategyObj.getFrequencyOfInvestment() == 0) {

      for (String ticker : stocks.keySet()) {

        Float weight = stocks.get(ticker);
        try {
          strategyModel.buyPartialStocksForStrategy(portfolioName, ticker,
                  (amount * weight) / 100.0f,
                  start, commissionFee);
        } catch (Exception e) {
          view.displayErrorMsg(e.getMessage());
          return;
        }
      }
      strategyView.displayInfo("Strategy is applied on the portfolio successfully.");
      return;
    }

    Date nextDate = start;

    nextDate = getNextDateIfAWeekend(nextDate);

    while (nextDate != null) {

      for (String ticker : stocks.keySet()) {

        Float weight = stocks.get(ticker);
        try {
          strategyModel.buyPartialStocksForStrategy(portfolioName, ticker,
                  (amount * weight) / 100.0f,
                  nextDate, commissionFee);
        } catch (Exception e) {
          view.displayErrorMsg(e.getMessage());
          return;
        }
      }

      Date next = DateUtility.getNextDate(nextDate, frequency);
      next = getNextDateIfAWeekend(next);

      if (next == null) {

        break;
      }

      if (DateUtility.isGivenDateLessThanOrEqualToOtherDate(next, end)) {
        nextDate = next;
      } else {
        nextDate = null;
      }
    }
    strategyView.displayInfo("Strategy is applied on the portfolio successfully.");
  }

  private Date getNextDateIfAWeekend(Date date) {

    if (DateUtility.isDateAWeekend(date)) {

      Date dateAfterWeekend = DateUtility.getNextDateAfterAWeekendDate(date);
      if (!DateUtility.isNotFutureDateForInvestment(dateAfterWeekend)) {

        return null;
      } else {

        return dateAfterWeekend;
      }
    }
    return date;
  }

  @Override
  public void saveStrategy(String strategyName, String fileURL) {

    try {
      strategyModel.saveStrategyToFile(strategyName, fileURL);
      strategyView.displayInfo("File saved.");
    } catch (Exception exp) {

      strategyView.displayErrorMsg(exp.getMessage());
    }
  }

  @Override
  public void getStrategy(String fileName) {

    try {
      InvestmentStrategy strategy = strategyModel.getStrategyFromFile(
              new FileInputStream(fileName));
      strategyView.displayStrategy(strategy);
    } catch (Exception e) {
      strategyView.displayErrorMsg(e.getMessage());
    }
  }

  private boolean validateStrategy(String name, float investmentAmount,
                                   Date startDate) {

    if (investmentAmount <= 0) {
      strategyView.displayErrorMsg("Investment amount cannot be less than or equal to zero");
      return false;
    }

    if (startDate == null) {
      strategyView.displayErrorMsg("Start date cannot be null");
      return false;
    }
    return true;
  }

  @Override
  public void setView() {

    strategyView.setFeaturesExtended(this);
  }
}