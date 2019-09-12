package controller;

import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import model.IPortfolioInfo;
import model.IStockTutorModelExtended;
import strategies.InvestmentStrategy;
import view.ITextBasedView;

/**
 * Contains tests to test the controller component.
 */
public class StockTutorControllerTest {

  private class MockModelTest implements IStockTutorModelExtended {

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
            log.append("portfolio creation");
          } catch (Exception e) {

            System.out.println(e.getMessage());
          }
          return "1";
        case 3:
          value = 1;
          return "portfolio name";
        case 4:
          value = 1;
          return "2";
        case 5:
          value = 6;
          return "3";
        case 6:
          value = 1;
          return "ABC";
        case 7:
          value = 8;
          return "4";
        case 8:
          value = 9;
          return "Portfolio name";
        case 9:
          value = 10;
          return "Ticker";
        case 10:
          value = 1;
          return "Y";
        case 11:
          value = 12;
          return "5";
        case 12:
          value = 1;
          return "portfolio name";
        case 13:
          value = 12;
          return "6";
        case 14:
          value = 15;
          return "7";
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
      return 0;
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
        log.append("info");
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
        log.append(strategy.getStrategyName());
      } catch (Exception e) {

        System.out.println(e.getMessage());
      }
    }

    @Override
    public void setFeaturesExtended(FeaturesExtended feature) {
      /**
       * This method is not used int his testing class.
       */
    }
  }

  @Test
  public void invalidTestRetry() {

    Appendable writer = new StringWriter();
    ITextBasedView view = new MockViewTest(writer, 1);
    IStockTutorModelExtended model = new MockModelTest();

    IStockTutorController controller = new StockTutorCommandController(model, view);
    controller.setView();

    Assert.assertEquals("infoMenuinfo", writer.toString());
  }

  @Test
  public void testControllerSetup() {

    Appendable writer = new StringWriter();
    Appendable writer2 = new StringWriter();
    ITextBasedView view = new MockViewTest(writer, 1);
    IStockTutorModelExtended model = new MockModelTest(writer2);
    IStockTutorController cont = new StockTutorMainController(model, view);
    cont.setView();
    Assert.assertEquals("features", writer.toString());
    Assert.assertEquals("", writer2.toString());
  }

  @Test
  public void testConsoleControllerSetup() {

    Appendable writer = new StringWriter();
    Appendable writer2 = new StringWriter();
    ITextBasedView view = new MockViewTest(writer, 1);
    IStockTutorModelExtended model = new MockModelTest(writer2);
    IStockTutorController cont = new StockTutorCommandController(model, view);
    cont.setView();
    Assert.assertEquals("infoMenuinfo", writer.toString());
    Assert.assertEquals("", writer2.toString());
  }

  @Test
  public void getAllPortfoliosTest() {

    Appendable writer = new StringWriter();
    Appendable writer2 = new StringWriter();
    ITextBasedView view = new MockViewTest(writer, 4);
    IStockTutorModelExtended model = new MockModelTest(writer2);
    IStockTutorController cont = new StockTutorCommandController(model, view);
    cont.setView();
    Assert.assertEquals("infoMenuall portfolioMenuinfo", writer.toString());
    Assert.assertEquals("All portfolios", writer2.toString());
  }

  @Test
  public void createPortfolioTest() {

    Appendable writer = new StringWriter();
    Appendable writer2 = new StringWriter();
    ITextBasedView view = new MockViewTest(writer, 2);
    IStockTutorModelExtended model = new MockModelTest(writer2);
    IStockTutorController cont = new StockTutorCommandController(model, view);
    cont.setView();
    Assert.assertEquals("infoMenuportfolio creationinfoinfoMenuinfo", writer.toString());
    Assert.assertEquals("portfolio name", writer2.toString());
  }

  @Test
  public void getPortfoliosTest() {

    Appendable writer = new StringWriter();
    Appendable writer2 = new StringWriter();
    ITextBasedView view = new MockViewTest(writer, 5);
    IStockTutorModelExtended model = new MockModelTest(writer2);
    IStockTutorController cont = new StockTutorCommandController(model, view);
    cont.setView();
    Assert.assertEquals("infoMenuinfoportfolioMenuinfo", writer.toString());
    Assert.assertEquals("ABC", writer2.toString());
  }

  @Test
  public void buyStockTest() {

    Appendable writer = new StringWriter();
    Appendable writer2 = new StringWriter();
    ITextBasedView view = new MockViewTest(writer, 7);
    IStockTutorModelExtended model = new MockModelTest(writer2);
    IStockTutorController cont = new StockTutorCommandController(model, view);
    cont.setView();
    Assert.assertEquals("infoMenuinfoinfoinfoinfoinfoinfoPortfolio " +
            "nameTicker0.0Thu Apr 04 16:56:38 EDT 2019100.0Menuinfo", writer.toString());
  }

  @Test
  public void costBasisTest() {

    Appendable writer = new StringWriter();
    Appendable writer2 = new StringWriter();
    ITextBasedView view = new MockViewTest(writer, 11);
    IStockTutorModelExtended model = new MockModelTest(writer2);
    IStockTutorController cont = new StockTutorCommandController(model, view);
    cont.setView();
    Assert.assertEquals("infoMenuinfoinfocost basisMenuinfo", writer.toString());
    Assert.assertEquals("portfolio nameThu Apr 04 17:12:11 EDT 2019", writer2.toString());
  }

  @Test
  public void totalValueTest() {

    Appendable writer = new StringWriter();
    Appendable writer2 = new StringWriter();
    ITextBasedView view = new MockViewTest(writer, 13);
    IStockTutorModelExtended model = new MockModelTest(writer2);
    IStockTutorController cont = new StockTutorCommandController(model, view);
    cont.setView();
    Assert.assertEquals("infoMenuinfoinfototal valueMenuinfo", writer.toString());
    Assert.assertEquals("portfolio nameThu Apr 04 17:12:37 EDT 2019", writer2.toString());
  }

  @Test
  public void savePortfolioToFileTest() {

    Appendable writer = new StringWriter();
    Appendable writer2 = new StringWriter();
    ITextBasedView view = new MockViewTest(writer, 14);
    IStockTutorModelExtended model = new MockModelTest(writer2);
    IStockTutorController cont = new StockTutorCommandController(model, view);
    cont.setView();
    Assert.assertEquals("infoMenuinfoinfoinfoMenuinfo", writer.toString());
    Assert.assertEquals("file pathportfolio name", writer2.toString());
  }

  @Test
  public void getPortfolioFromFileTest() {

    Appendable writer = new StringWriter();
    Appendable writer2 = new StringWriter();
    ITextBasedView view = new MockViewTest(writer, 16);
    IStockTutorModelExtended model = new MockModelTest(writer2);
    IStockTutorController cont = new StockTutorCommandController(model, view);
    cont.setView();
    Assert.assertEquals("infoMenuinfoinfo", writer.toString());
    Assert.assertEquals("", writer2.toString());
  }
}