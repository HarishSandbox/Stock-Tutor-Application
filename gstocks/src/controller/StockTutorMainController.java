package controller;

import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

import model.IStockTutorModelExtended;
import model.IPortfolioInfo;
import view.IStockTutorView;

/**
 * This is the controller class of the tutoring application. It implements {@link
 * IStockTutorController} interface. It is a point of communication between view and controller, and
 * controller and model. It acts to the actions done by the user on the view and returns to the user
 * with the necessary response.
 */
public class StockTutorMainController implements Features, IStockTutorController {

  protected final IStockTutorModelExtended model;
  protected final IStockTutorView view;

  /**
   * This constructor creates an object of the constructor by taking in a view and model and
   * connects the components of the application.
   *
   * @param model A {@code IStockTutorModelExtended} object that represents the model of the
   *              application.
   * @param view  A {@code IStockTutorView} object which represents the view of the application.
   * @throws IllegalArgumentException thrown when any of the arguments are null.
   */
  public StockTutorMainController(IStockTutorModelExtended model, IStockTutorView view)
          throws IllegalArgumentException {

    if (model == null || view == null) {

      throw new IllegalArgumentException("Model or View cannot be null");
    }

    this.model = model;
    this.view = view;
  }

  @Override
  public void createPortfolio(String portfolioName) {

    try {
      model.createPortfolio(portfolioName);
      view.displayInfo("Portfolio created successfully");
    } catch (IllegalArgumentException e) {
      view.displayErrorMsg(e.getMessage());
    }
  }

  @Override
  public void getPortfolio(String portfolio) {
    try {
      IPortfolioInfo info = model.getPortfolio(portfolio);
      view.displayPortfolioInfo(info);
    } catch (IllegalArgumentException e) {
      view.displayErrorMsg(e.getMessage());
    }
  }

  @Override
  public void getAllUserPortfolio() {
    try {
      List<IPortfolioInfo> portfolioList = model.getAllUserPortfolio();
      view.displayAllUserPortfolios(portfolioList);
    } catch (IllegalArgumentException e) {
      view.displayErrorMsg(e.getMessage());
    }
  }

  @Override
  public void getTotalCostBasis(String portfolio, Date date) {
    try {
      float price = model.getTotalCostBasis(portfolio, date);
      view.displayTotalCostBasis(portfolio, date, price);
    } catch (IllegalArgumentException e) {
      view.displayErrorMsg(e.getMessage());
    }
  }

  @Override
  public void getTotalValueForGivenDate(String portfolio, Date date) {

    try {
      float price = model.getTotalValueForGivenDate(portfolio, date);
      view.displayTotalValueForGivenDate(portfolio, date, price);
    } catch (IllegalArgumentException e) {
      view.displayErrorMsg(e.getMessage());
    }
  }

  @Override
  public void buyStock(String portfolio, String tickerSymbol, float amount,
                       Date dateOfPurchase) {
    try {
      float remainingAmount = model.buyStock(portfolio, tickerSymbol, amount, dateOfPurchase);
      float amountUsed = amount - remainingAmount;
      view.displayBuyStock(portfolio, tickerSymbol, amountUsed, dateOfPurchase, remainingAmount);
    } catch (IllegalArgumentException e) {
      view.displayErrorMsg(e.getMessage());
    }
  }

  @Override
  public void buyStockWithCommission(String portfolio, String tickerSymbol,
                                     float amount, Date dateOfPurchase, float commissionFees) {
    try {
      float remainingAmount = model.buyStockWithCommission(portfolio, tickerSymbol, amount,
              dateOfPurchase, commissionFees);
      float amountUsed = amount - remainingAmount;
      view.displayBuyStock(portfolio, tickerSymbol, amountUsed, dateOfPurchase,
              remainingAmount);
    } catch (IllegalArgumentException e) {
      view.displayErrorMsg(e.getMessage());
    }
  }

  @Override
  public void savePortfolio(String portfolioName, String fileURL) {

    try {
      model.savePortfolioToFile(fileURL, portfolioName);
      view.displayInfo("File saved.");
    } catch (Exception e) {

      view.displayErrorMsg(e.getMessage());
    }
  }

  @Override
  public void getPortfolioFromFile(String fileName) {

    try {
      IPortfolioInfo portfolio = model.getPortfolioFromFile(new FileInputStream(fileName));
      view.displayPortfolioInfo(portfolio);
    } catch (Exception e) {
      view.displayErrorMsg(e.getMessage());
    }
  }

  @Override
  public void setView() {

    view.setFeatures(this);
  }
}