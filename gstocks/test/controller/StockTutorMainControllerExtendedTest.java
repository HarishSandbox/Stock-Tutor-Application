package controller;

import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import model.IPortfolioInfo;
import model.IStockTutorStrategy;
import strategies.InvestmentStrategy;
import view.ITextBasedView;

/**
 * To test new features added in version 3 for thr controller view and model.
 */
public class StockTutorMainControllerExtendedTest {

  private class MockModelTest implements IStockTutorStrategy {

    Appendable log;

    public MockModelTest(Appendable log) {

      this.log = log;
    }

    public MockModelTest() {

      log = new StringWriter();
    }

    @Override
    public float buyStockWithCommission(String portfolio, String tickerSymbol, float amount,
                                        Date dateOfPurchase, float commissionFees)
            throws IllegalArgumentException {

      return amount;
    }

    @Override
    public void savePortfolioToFile(String fileUrl, String portfolio) throws IllegalStateException {

      try {
        log.append(fileUrl).append(portfolio);
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
    }

    @Override
    public IPortfolioInfo getPortfolioFromFile(InputStream stream) throws IllegalStateException {

      try {
        log.append(stream.toString());
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
      return null;
    }

    @Override
    public float getStockPrice(String tickerSymbol, Date date) throws IllegalArgumentException {
      return 1003.0f;
    }

    @Override
    public void createPortfolio(String portfolioName) throws IllegalArgumentException {

      try {
        log.append(portfolioName);
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
    }

    @Override
    public IPortfolioInfo getPortfolio(String portfolio) throws IllegalArgumentException {

      try {
        log.append(portfolio);
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
      return null;
    }

    @Override
    public List<IPortfolioInfo> getAllUserPortfolio() {

      try {
        log.append("All portfolios");
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
      return null;
    }

    @Override
    public float getTotalCostBasis(String portfolio, Date date) throws IllegalArgumentException {

      try {
        log.append(portfolio).append(date.toString());
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
      return 102.0f;
    }

    @Override
    public float getTotalValueForGivenDate(String portfolio, Date date)
            throws IllegalArgumentException {

      try {
        log.append(portfolio).append(date.toString());
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
      return 103.0f;
    }

    @Override
    public float buyStock(String portfolio, String tickerSymbol, float amount, Date dateOfPurchase)
            throws IllegalArgumentException {

      try {
        log.append(portfolio).append(dateOfPurchase.toString()).append(tickerSymbol);
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
      return amount;
    }

    @Override
    public void saveStrategyToFile(String strategy, String fileURL) {

      try {
        log.append(fileURL).append(strategy);
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
    }

    @Override
    public InvestmentStrategy getStrategyFromFile(InputStream fileURL)
            throws IllegalStateException {

      try {
        log.append(fileURL.toString());
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
      return null;
    }

    @Override
    public float buyPartialStocksForStrategy(String portfolio, String tickerSymbol, float amount,
                                             Date dateOfPurchase, float commissionFees)
            throws IllegalArgumentException {

      try {
        log.append(portfolio).append(dateOfPurchase.toString()).append(tickerSymbol).append(
                "partial buying");
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
      return amount;
    }
  }


  private class MockViewTest implements ITextBasedView {


    Appendable log;
    int value;

    public MockViewTest(Appendable log, int value) {

      this.log = log;
      this.value = value;
    }

    public MockViewTest() {

      log = new StringWriter();
    }

    @Override
    public String getStringInput() {

      switch (value) {

        case 1:
          throw new IllegalStateException("QUIT");
        case 2:
          value = 3;
          try {
            log.append("strategy creation");
          } catch (Exception e) {

            System.out.println(e.getMessage());
          }
          return "9";
        case 3:
          value = 4;
          return "strategy name";
        case 4:
          value = 5;
          return "1";
        case 5:
          value = 6;
          return "1";
        case 6:
          value = 7;
          return "4567";
        case 7:
          value = 8;
          return "12";
        case 8:
          value = 9;
          return "21-12-2016";
        case 9:
          value = 10;
          return "Ticker";
        case 10:
          value = 1;
          return "portfolio name";
        case 11:
          value = 12;
          return "10";
        case 12:
          value = 13;
          return "strategy name";
        case 13:
          value = 1;
          return "file url";
        case 14:
          value = 1;
          return "strategy name";
        case 15:
          value = 16;
          return "portfolio name";
        case 16:
          value = 1;
          return "file path";
        case 17:
          value = 16;
          return "8";
        default:
          return "q";
      }
    }

    @Override
    public Date getDate() {
      return new Date();
    }

    @Override
    public float getFloat() {
      return 100.0f;
    }

    @Override
    public int getInt() {
      return 10;
    }

    @Override
    public void displayMenu() {

      try {
        log.append("Menu");
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
    }

    @Override
    public void displayInfo(String info) throws IllegalStateException {

      try {
        log.append("21-12-2016");
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
    }

    @Override
    public void displayPortfolioInfo(IPortfolioInfo portfolio) {

      try {
        log.append("portfolio");
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
    }

    @Override
    public void displayAllUserPortfolios(List<IPortfolioInfo> portfolioList) {

      try {
        log.append("all portfolio");
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
    }

    @Override
    public void displayTotalCostBasis(String portfolio, Date date, float price) {

      try {
        log.append("cost basis");
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
    }

    @Override
    public void displayTotalValueForGivenDate(String portfolio, Date date, float price) {

      try {
        log.append("total value");
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
    }

    @Override
    public void displayBuyStock(String portfolioName, String tickerSymbol, float amountUsed,
                                Date dateOfPurchase, float remainingAmount) {

      try {
        log.append(portfolioName).append(tickerSymbol).append(amountUsed + "")
                .append(dateOfPurchase.toString()).append(remainingAmount + "");
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
    }

    @Override
    public void setFeatures(Features features) {

      try {
        log.append("features");
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
    }

    @Override
    public void displayErrorMsg(String error) throws IllegalStateException {

      try {
        log.append(error);
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
    }

    @Override
    public void displayStrategy(InvestmentStrategy strategy) {

      try {
        log.append("strategy name");
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
    }

    @Override
    public void setFeaturesExtended(FeaturesExtended feature) {

      try {
        log.append("features extended");
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
    }
  }

  @Test
  public void testControllerSetup() {

    Appendable writer = new StringWriter();
    Appendable writer2 = new StringWriter();
    ITextBasedView view = new StockTutorMainControllerExtendedTest.MockViewTest(writer, 1);
    IStockTutorStrategy model = new StockTutorMainControllerExtendedTest.MockModelTest(writer2);
    IStockTutorController cont = new StockTutorMainControllerExtended(model, view);
    cont.setView();
    Assert.assertEquals("features extended", writer.toString());
    Assert.assertEquals("", writer2.toString());
  }

  @Test
  public void testConsoleControllerSetup() {

    Appendable writer = new StringWriter();
    Appendable writer2 = new StringWriter();
    ITextBasedView view = new StockTutorMainControllerExtendedTest.MockViewTest(writer, 1);
    IStockTutorStrategy model = new StockTutorMainControllerExtendedTest.MockModelTest(writer2);
    IStockTutorController cont = new StockTutorCommandControllerExtended(model, view);
    cont.setView();
    Assert.assertEquals("infoMenuinfo", writer.toString());
    Assert.assertEquals("", writer2.toString());
  }

  @Test
  public void createEqualWeightStrategyTest() {

    Appendable writer = new StringWriter();
    Appendable writer2 = new StringWriter();
    ITextBasedView view = new StockTutorMainControllerExtendedTest.MockViewTest(writer, 2);
    IStockTutorStrategy model = new StockTutorMainControllerExtendedTest.MockModelTest(writer2);
    IStockTutorController cont = new StockTutorCommandControllerExtended(model, view);
    cont.setView();
    Assert.assertEquals("21-12-2016Menustrategy creation21-12-201621-12-201621-12-2016" +
            "21-12-201621-12-201621-12-201621-12-201621-12-201621-12-201621-12-201621-12-2016" +
            "21-12-201621-12-201621-12-201621-12-201621-12-201621-12-2016", writer.toString());
    Assert.assertEquals("", writer2.toString());
  }

  @Test
  public void createCustomWeightStrategyTest() {

    Appendable writer = new StringWriter();
    Appendable writer2 = new StringWriter();
    ITextBasedView view = new StockTutorMainControllerExtendedTest.MockViewTest(writer, 2);
    IStockTutorStrategy model = new StockTutorMainControllerExtendedTest.MockModelTest(writer2);
    IStockTutorController cont = new StockTutorCommandControllerExtended(model, view);
    cont.setView();
    Assert.assertEquals("21-12-2016Menustrategy creation21-12-201621-12-201621-12-2016" +
            "21-12-201621-12-201621-12-201621-12-201621-12-201621-12-201621-12-201621-12-2016" +
            "21-12-201621-12-201621-12-201621-12-201621-12-201621-12-2016", writer.toString());
    Assert.assertEquals("", writer2.toString());
  }

  @Test
  public void createDollarCostWeightStrategyTest() {

    Appendable writer = new StringWriter();
    Appendable writer2 = new StringWriter();
    ITextBasedView view = new StockTutorMainControllerExtendedTest.MockViewTest(writer, 2);
    IStockTutorStrategy model = new StockTutorMainControllerExtendedTest.MockModelTest(writer2);
    IStockTutorController cont = new StockTutorCommandControllerExtended(model, view);
    cont.setView();
    Assert.assertEquals("21-12-2016Menustrategy creation21-12-201621-12-201621-12-2016" +
            "21-12-201621-12-201621-12-201621-12-201621-12-201621-12-201621-12-201621-12-2016" +
            "21-12-201621-12-201621-12-201621-12-201621-12-201621-12-2016", writer.toString());
    Assert.assertEquals("", writer2.toString());
  }

  @Test
  public void saveStrategyTest() {

    Appendable writer = new StringWriter();
    Appendable writer2 = new StringWriter();
    ITextBasedView view = new StockTutorMainControllerExtendedTest.MockViewTest(writer, 11);
    IStockTutorStrategy model = new StockTutorMainControllerExtendedTest.MockModelTest(writer2);
    IStockTutorController cont = new StockTutorCommandControllerExtended(model, view);
    cont.setView();
    Assert.assertEquals("21-12-2016Menu21-12-201621-12-201621-12-2016Menu21-12-2016",
            writer.toString());
    Assert.assertEquals("file urlstrategy name", writer2.toString());
  }

  @Test
  public void getStrategyTest() {

    Appendable writer = new StringWriter();
    Appendable writer2 = new StringWriter();
    ITextBasedView view = new StockTutorMainControllerExtendedTest.MockViewTest(writer, 14);
    IStockTutorStrategy model = new StockTutorMainControllerExtendedTest.MockModelTest(writer2);
    IStockTutorController cont = new StockTutorCommandControllerExtended(model, view);
    cont.setView();
    Assert.assertEquals("21-12-2016Menu21-12-201621-12-2016", writer.toString());
    Assert.assertEquals("", writer2.toString());
  }
}