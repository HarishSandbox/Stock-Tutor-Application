import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.StockTutorMainControllerExtended;
import model.IPortfolioInfo;
import model.IPurchaseHistory;
import model.IStockTutorStrategy;
import model.StockTutorStrategy;
import util.DateUtility;
import view.IStockTutorViewExtended;
import view.TextBasedView;

import static org.junit.Assert.assertEquals;

/**
 * This class tests all functionality related to buying stocks for a portfolio when applying
 * strategy.
 */
public class StrategyTest {

  // Tests for createCustomWeightStrategy()
  @Test
  public void testCreateCustomStrategyWithCommissionFeesNewPortFolio() {
    IStockTutorStrategy model = new StockTutorStrategy();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in),
            new PrintStream(outContent));
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", 50f);
    stocks.put("MSFT", 50f);

    String expected = "Strategy is created.\n" +
            "Strategy is applied on the portfolio successfully.\n";

    Date date = DateUtility.
            convertStringFromUserToDate("18-03-2019 12:00:00");

    controller.createCustomWeightStrategy("CustomStrategy", 2000f
            , date, stocks, 0);
    controller.applyStrategy("NewPortfolio", "CustomStrategy");

    assertEquals(expected, outContent.toString());

    List<IPortfolioInfo> portfolioList = model.getAllUserPortfolio();

    // Assert on all user portfolios
    assertEquals("NewPortfolio", portfolioList.get(0).getPortfolioName());

    List<IPurchaseHistory> purchaseHistoryList =
            portfolioList.get(0).getPortfolioItems();

    // Assert on 1st purchase item
    assertEquals(8.5, purchaseHistoryList.get(0).getQuantity(), 0.1);
    String actualDate = DateUtility.convertDateToUserReadableString(
            purchaseHistoryList.get(0).getDateOfPurchase());
    assertEquals("18-03-2019 12:00:00", actualDate);
    assertEquals(1000, purchaseHistoryList.get(0).getCostBasisForPurchase(), 0.01);
    assertEquals("MSFT", purchaseHistoryList.get(0).getTickerSymbol());

    // Assert on 2nd purchase item

    assertEquals(0.84, purchaseHistoryList.get(1).getQuantity(), 0.1);
    String actualDate2 = DateUtility.convertDateToUserReadableString(
            purchaseHistoryList.get(1).getDateOfPurchase());
    assertEquals("18-03-2019 12:00:00", actualDate2);
    assertEquals(1000, purchaseHistoryList.get(1).getCostBasisForPurchase(), 0.01);
    assertEquals("GOOG", purchaseHistoryList.get(1).getTickerSymbol());

    Date dateToCalculateCostBasis = DateUtility.
            convertStringFromUserToDate("13-04-2019 13:30:00");

    Date dateToCalculateTotalValue = DateUtility.
            convertStringFromUserToDate("13-04-2019 13:30:00");

    assertEquals(2000, model.getTotalCostBasis("NewPortfolio",
            dateToCalculateCostBasis), 0.01);

    assertEquals(2057.129, model.getTotalValueForGivenDate("NewPortfolio",
            dateToCalculateTotalValue), 0.1);
  }


  @Test
  public void testCreateCustomStrategyOnExistingPortfolio() {
    IStockTutorStrategy model = new StockTutorStrategy();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in),
            new PrintStream(outContent));
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", 50f);
    stocks.put("MSFT", 50f);

    String expected = "Strategy is created.\n" +
            "Strategy is applied on the portfolio successfully.\n";

    Date date = DateUtility.
            convertStringFromUserToDate("18-03-2019 12:00:00");

    model.createPortfolio("NewPortfolio");

    controller.createCustomWeightStrategy("CustomStrategy", 2000f
            , date, stocks, 0);
    controller.applyStrategy("NewPortfolio", "CustomStrategy");

    assertEquals(expected, outContent.toString());
  }


  // Tests for createEqualWeightStrategy()
  @Test
  public void testEqualWeightStrategyWithCommissionFeesNewPortFolio() {
    IStockTutorStrategy model = new StockTutorStrategy();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in), System.out);
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    List<String> stocks = new ArrayList<>();
    stocks.add("GOOG");
    stocks.add("MSFT");

    Date date = DateUtility.
            convertStringFromUserToDate("18-03-2019 12:00:00");

    controller.createEqualWeightStrategy("EqualWeightStrategy", 2000f
            , date, stocks, 50);
    controller.applyStrategy("NewPortfolio", "EqualWeightStrategy");

    List<IPortfolioInfo> portfolioList = model.getAllUserPortfolio();

    // Assert on all user portfolios
    assertEquals("NewPortfolio", portfolioList.get(0).getPortfolioName());

    List<IPurchaseHistory> purchaseHistoryList =
            portfolioList.get(0).getPortfolioItems();

    // Assert on 1st purchase item
    assertEquals(8.08, purchaseHistoryList.get(0).getQuantity(), 0.1);
    String actualDate = DateUtility.convertDateToUserReadableString(
            purchaseHistoryList.get(0).getDateOfPurchase());
    assertEquals("18-03-2019 12:00:00", actualDate);
    assertEquals(1000, purchaseHistoryList.get(0).getCostBasisForPurchase(), 0.01);
    assertEquals("MSFT", purchaseHistoryList.get(0).getTickerSymbol());

    // Assert on 2nd purchase item

    assertEquals(0.84, purchaseHistoryList.get(1).getQuantity(), 0.1);
    String actualDate2 = DateUtility.convertDateToUserReadableString(
            purchaseHistoryList.get(1).getDateOfPurchase());
    assertEquals("18-03-2019 12:00:00", actualDate2);
    assertEquals(1000, purchaseHistoryList.get(1).getCostBasisForPurchase(), 0.01);
    assertEquals("GOOG", purchaseHistoryList.get(1).getTickerSymbol());

    Date dateToCalculateCostBasis = DateUtility.
            convertStringFromUserToDate("13-04-2019 13:30:00");

    Date dateToCalculateTotalValue = DateUtility.
            convertStringFromUserToDate("13-04-2019 13:30:00");

    assertEquals(2000, model.getTotalCostBasis("NewPortfolio",
            dateToCalculateCostBasis), 0.01);

    assertEquals(1954.27, model.getTotalValueForGivenDate("NewPortfolio",
            dateToCalculateTotalValue), 0.1);
  }


  @Test
  public void testMultipleStrategiesOnSamePortfolio() {
    IStockTutorStrategy model = new StockTutorStrategy();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in),
            new PrintStream(outContent));
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", 40f);
    stocks.put("MSFT", 10f);
    stocks.put("FB", 20f);
    stocks.put("AAPL", 30f);

    String expected = "Strategy is created.\n" +
            "Strategy is applied on the portfolio successfully.\n" +
            "Strategy is created.\n" +
            "Strategy is applied on the portfolio successfully.\n";

    Date date = DateUtility.
            convertStringFromUserToDate("14-03-2019 12:00:00");

    controller.createCustomWeightStrategy("CustomStrategy", 2000f
            , date, stocks, 0);
    controller.applyStrategy("NewPortfolio", "CustomStrategy");

    Date startDate = DateUtility.
            convertStringFromUserToDate("18-03-2018 12:00:00");

    Date endDate = DateUtility.
            convertStringFromUserToDate("18-01-2019 12:00:00");

    controller.createDollarCostStrategy("DollarCostStrategy", 2000f, 30
            , startDate, endDate, stocks, 50);
    controller.applyStrategy("NewPortfolio", "DollarCostStrategy");

    assertEquals(expected, outContent.toString());
  }


  // Tests for createDollarCostStrategy()
  @Test
  public void testDollarCostStrategyWithCommissionFeesNewPortFolioOnWeekend() {
    IStockTutorStrategy model = new StockTutorStrategy();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in), System.out);
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", 30f);
    stocks.put("MSFT", 70f);

    Date startDate = DateUtility.
            convertStringFromUserToDate("18-03-2018 12:00:00");

    Date endDate = DateUtility.
            convertStringFromUserToDate("18-01-2019 12:00:00");

    controller.createDollarCostStrategy("DollarCostStrategy", 2000f, 30
            , startDate, endDate, stocks, 50);
    controller.applyStrategy("NewPortfolio", "DollarCostStrategy");

    List<IPortfolioInfo> portfolioList = model.getAllUserPortfolio();

    // Assert on all user portfolios
    assertEquals("NewPortfolio", portfolioList.get(0).getPortfolioName());

    List<IPurchaseHistory> purchaseHistoryList =
            portfolioList.get(0).getPortfolioItems();

    assertEquals(22, purchaseHistoryList.size());

    // Assert on 1st purchase item
    assertEquals(14.53, purchaseHistoryList.get(0).getQuantity(), 0.1);
    String actualDate = DateUtility.convertDateToUserReadableString(
            purchaseHistoryList.get(0).getDateOfPurchase());
    assertEquals("19-03-2018 12:00:00", actualDate);
    assertEquals(1400, purchaseHistoryList.get(0).getCostBasisForPurchase(), 0.01);
    assertEquals("MSFT", purchaseHistoryList.get(0).getTickerSymbol());

    // Assert on 10th purchase item

    assertEquals(0.459, purchaseHistoryList.get(9).getQuantity(), 0.1);
    String actualDate2 = DateUtility.convertDateToUserReadableString(
            purchaseHistoryList.get(9).getDateOfPurchase());
    assertEquals("18-07-2018 12:00:00", actualDate2);
    assertEquals(600, purchaseHistoryList.get(9).getCostBasisForPurchase(), 0.01);
    assertEquals("GOOG", purchaseHistoryList.get(9).getTickerSymbol());

    // get cost basis and total value for present date
    Date dateToCalculateCostBasis = DateUtility.
            convertStringFromUserToDate("15-04-2019 13:30:00");

    Date dateToCalculateTotalValue = DateUtility.
            convertStringFromUserToDate("15-04-2019 13:30:00");

    assertEquals(22000, model.getTotalCostBasis("NewPortfolio",
            dateToCalculateCostBasis), 0.01);

    assertEquals(24082.60, model.getTotalValueForGivenDate("NewPortfolio",
            dateToCalculateTotalValue), 0.1);

    // get cost basis and total value for in between date
    Date dateToCalculateCostBasis1 = DateUtility.
            convertStringFromUserToDate("18-07-2018 12:00:00");

    Date dateToCalculateTotalValue1 = DateUtility.
            convertStringFromUserToDate("18-07-2018 12:00:00");

    assertEquals(10000, model.getTotalCostBasis("NewPortfolio",
            dateToCalculateCostBasis1), 0.01);

    assertEquals(10167.86, model.getTotalValueForGivenDate("NewPortfolio",
            dateToCalculateTotalValue1), 0.1);
  }


  // weights negative tests for custom strategy
  @Test
  public void testCreateCustomStrategyWeightsNot100() {
    IStockTutorStrategy model = new StockTutorStrategy();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in),
            new PrintStream(outContent));
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", 30f);
    stocks.put("MSFT", 20f);

    String expected = "Weights dont sum up to 100\n";

    Date date = DateUtility.
            convertStringFromUserToDate("18-03-2019 12:00:00");

    controller.createCustomWeightStrategy("CustomStrategy", 2000f
            , date, stocks, 0);

    assertEquals(expected, outContent.toString());
  }


  @Test
  public void testCreateCustomStrategyNegativeWeights() {
    IStockTutorStrategy model = new StockTutorStrategy();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in),
            new PrintStream(outContent));
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", -30f);
    stocks.put("MSFT", -70f);

    String expected = "Weights dont sum up to 100\n";

    Date date = DateUtility.
            convertStringFromUserToDate("18-03-2019 12:00:00");

    controller.createCustomWeightStrategy("CustomStrategy", 2000f
            , date, stocks, 0);

    assertEquals(expected, outContent.toString());
  }

  @Test
  public void testCreateCustomStrategyZeroWeights() {
    IStockTutorStrategy model = new StockTutorStrategy();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in),
            new PrintStream(outContent));
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", 0f);
    stocks.put("MSFT", 0f);

    String expected = "Weights dont sum up to 100\n";

    Date date = DateUtility.
            convertStringFromUserToDate("18-03-2019 12:00:00");

    controller.createCustomWeightStrategy("CustomStrategy", 2000f
            , date, stocks, 0);

    assertEquals(expected, outContent.toString());
  }


  // weights negative tests for dollar strategy
  @Test
  public void testCreateDollarStrategyWeightsNot100() {
    IStockTutorStrategy model = new StockTutorStrategy();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in),
            new PrintStream(outContent));
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", 30f);
    stocks.put("MSFT", 20f);

    String expected = "Weights dont sum up to 100\n";

    Date date = DateUtility.
            convertStringFromUserToDate("18-03-2019 12:00:00");

    controller.createDollarCostStrategy("DollarCostStrategy", 2000f
            , 45, date, null, stocks, 0);

    assertEquals(expected, outContent.toString());
  }


  @Test
  public void testCreateDollarStrategyNegativeWeights() {
    IStockTutorStrategy model = new StockTutorStrategy();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in),
            new PrintStream(outContent));
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", -30f);
    stocks.put("MSFT", -70f);

    String expected = "Weights dont sum up to 100\n";

    Date date = DateUtility.
            convertStringFromUserToDate("18-03-2019 12:00:00");

    controller.createDollarCostStrategy("DollarCostStrategy", 2000f
            , 45, date, null, stocks, 0);

    assertEquals(expected, outContent.toString());
  }

  @Test
  public void testCreateDollarStrategyZeroWeights() {
    IStockTutorStrategy model = new StockTutorStrategy();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in),
            new PrintStream(outContent));
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", 0f);
    stocks.put("MSFT", 0f);

    String expected = "Weights dont sum up to 100\n";

    Date date = DateUtility.
            convertStringFromUserToDate("18-03-2019 12:00:00");

    controller.createDollarCostStrategy("DollarCostStrategy", 2000f
            , 45, date, null, stocks, 0);

    assertEquals(expected, outContent.toString());
  }

  @Test
  public void testDollarCostStrategyEndDateLessThanStartDate() {
    IStockTutorStrategy model = new StockTutorStrategy();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in),
            new PrintStream(outContent));
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", 30f);
    stocks.put("MSFT", 70f);

    Date startDate = DateUtility.
            convertStringFromUserToDate("18-03-2019 12:00:00");

    Date endDate = DateUtility.
            convertStringFromUserToDate("18-01-2018 12:00:00");
    String expected = "End date cannot be greater than start date\n";
    controller.createDollarCostStrategy("DollarCostStrategy", 2000f, 30
            , startDate, endDate, stocks, 50);

    assertEquals(expected, outContent.toString());
  }

  @Test
  public void testDollarCostStrategyCommissionFeesNegative() {
    IStockTutorStrategy model = new StockTutorStrategy();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in),
            new PrintStream(outContent));
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", 30f);
    stocks.put("MSFT", 70f);

    Date startDate = DateUtility.
            convertStringFromUserToDate("18-03-2018 12:00:00");

    Date endDate = DateUtility.
            convertStringFromUserToDate("18-01-2019 12:00:00");
    String expected = "Commission fees cannot be negative\n";
    controller.createDollarCostStrategy("DollarCostStrategy", 2000f, 30
            , startDate, endDate, stocks, -50);

    assertEquals(expected, outContent.toString());
  }

  // If end date not specified, transact till today
  @Test
  public void testDollarCostStrategyEndDateNull() {
    IStockTutorStrategy model = new StockTutorStrategy();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in),
            new PrintStream(outContent));
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", 30f);
    stocks.put("MSFT", 70f);

    Date startDate = DateUtility.
            convertStringFromUserToDate("15-10-2018 12:00:00");

    String expected = "Strategy is created.\n" +
            "Strategy is applied on the portfolio successfully.\n";

    controller.createDollarCostStrategy("DollarCostStrategy", 2000f, 30
            , startDate, null, stocks, 50);
    controller.applyStrategy("NewPortfolio", "DollarCostStrategy");

    assertEquals(expected, outContent.toString());

    Date dateToCalculateCostBasis1 = DateUtility.
            convertStringFromUserToDate("15-04-2019 12:00:00");

    Date dateToCalculateTotalValue1 = DateUtility.
            convertStringFromUserToDate("15-04-2019 12:00:00");

    assertEquals(14000, model.getTotalCostBasis("NewPortfolio",
            dateToCalculateCostBasis1), 0.01);

    assertEquals(14770.3, model.getTotalValueForGivenDate("NewPortfolio",
            dateToCalculateTotalValue1), 0.1);
  }


  @Test
  public void testDollarCostStrategyStartDateNull() {
    IStockTutorStrategy model = new StockTutorStrategy();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in),
            new PrintStream(outContent));
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", 30f);
    stocks.put("MSFT", 70f);

    Date endDate = DateUtility.
            convertStringFromUserToDate("18-01-2018 12:00:00");
    String expected = "Start date cannot be null\n";
    controller.createDollarCostStrategy("DollarCostStrategy", 2000f, 30
            , null, endDate, stocks, 50);

    assertEquals(expected, outContent.toString());
  }


  @Test
  public void testDollarCostStrategyStartDateFuture() {
    IStockTutorStrategy model = new StockTutorStrategy();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in),
            new PrintStream(outContent));
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", 30f);
    stocks.put("MSFT", 70f);

    Date startDate = DateUtility.
            convertStringFromUserToDate("15-10-2019 12:00:00");

    Date endDate = DateUtility.
            convertStringFromUserToDate("20-10-2019 12:00:00");
    String expected = "Strategy is created.\n" +
            "Strategy cannot be applied to future date\n";
    controller.createDollarCostStrategy("DollarCostStrategy", 2000f, 30
            , startDate, endDate, stocks, 50);
    controller.applyStrategy("NewPortfolio", "DollarCostStrategy");

    assertEquals(expected, outContent.toString());
  }


  // tests for frequency
  @Test
  public void testDollarCostStrategyFrequencyZero() {
    IStockTutorStrategy model = new StockTutorStrategy();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in),
            new PrintStream(outContent));
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", 30f);
    stocks.put("MSFT", 70f);

    Date startDate = DateUtility.
            convertStringFromUserToDate("15-03-2019 12:00:00");

    Date endDate = DateUtility.
            convertStringFromUserToDate("18-14-2019 12:00:00");
    String expected = "Frequency cannot be less than or equal to zero\n";
    controller.createDollarCostStrategy("DollarCostStrategy", 2000f, 0
            , startDate, endDate, stocks, 50);

    assertEquals(expected, outContent.toString());
  }


  @Test
  public void testDollarCostStrategyFrequencyNegative() {
    IStockTutorStrategy model = new StockTutorStrategy();
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    IStockTutorViewExtended view = new TextBasedView(new InputStreamReader(System.in),
            new PrintStream(outContent));
    StockTutorMainControllerExtended controller = new StockTutorMainControllerExtended(
            model, view);
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("GOOG", 30f);
    stocks.put("MSFT", 70f);

    Date startDate = DateUtility.
            convertStringFromUserToDate("15-03-2019 12:00:00");

    Date endDate = DateUtility.
            convertStringFromUserToDate("18-14-2019 12:00:00");
    String expected = "Frequency cannot be less than or equal to zero\n";
    controller.createDollarCostStrategy("DollarCostStrategy", 2000f, -50
            , startDate, endDate, stocks, 50);

    assertEquals(expected, outContent.toString());
  }

}

