package model;

import org.junit.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import util.DateUtility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class tests all functionality related to buying a stock with commission fees.
 */
public class StockTutorModelWithCommissionTest {

  // Negative tests for buyStockWithCommission()
  @Test(expected = IllegalArgumentException.class)
  public void buyStockForInvalidDate() {

    IStockTutorModelExtended model = new StockTutorModelExtended();
    model.createPortfolio("Health Portfolio");

    Date invalidDate = DateUtility.
            convertStringFromUserToDate("30");
    String expected = "Date should not be null or Invalid";
    try {
      model.buyStockWithCommission("Health Portfolio", "GOOG", 1000,
              invalidDate, 1000);
    } catch (IllegalArgumentException e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Invalid date did not throw exception");
  }


  @Test(expected = IllegalArgumentException.class)
  public void buyStockOnWeekend() {

    IStockTutorModelExtended model = new StockTutorModelExtended();

    model.createPortfolio("Health Portfolio");

    Date date = DateUtility.
            convertStringFromUserToDate("16-03-2019 13:30:00");
    String expected = "Stock cannot be purchased on a holiday.";
    try {
      model.buyStockWithCommission("Health Portfolio", "GOOG",
              1000, date, 100);
    } catch (IllegalArgumentException e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Trading during weekend did not throw exception");
  }


  @Test(expected = IllegalArgumentException.class)
  public void buyStockAfterTradingHours() {

    IStockTutorModelExtended model = new StockTutorModelExtended();
    model.createPortfolio("Health Portfolio");

    Date date = DateUtility.
            convertStringFromUserToDate("18-03-2019 17:30:00");
    String expected = "Stock should be purchased between 9am - 4pm only.";
    try {
      model.buyStockWithCommission("Health Portfolio", "GOOG",
              1000, date, 10);
    } catch (IllegalArgumentException e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("After hours trading did not throw exception");
  }

  @Test(expected = IllegalArgumentException.class)
  public void buyStockUsingInsufficientAmount() {

    IStockTutorModelExtended model = new StockTutorModelExtended();
    model.createPortfolio("Health Portfolio");
    float purchaseAmount = 10;
    String ticker = "GOOG";

    Date date = DateUtility.
            convertStringFromUserToDate("18-03-2019 13:30:00");
    String expected = "The price of the stock is " + 1184.26 + ". Give "
            + "sufficient amount to make a purchase.";
    try {
      model.buyStockWithCommission("Health Portfolio", ticker, purchaseAmount,
              date, 10);
    } catch (IllegalArgumentException e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Insufficient amount did not throw exception");
  }

  @Test(expected = IllegalArgumentException.class)
  public void buyStockUsingNegativeCommission() {

    IStockTutorModelExtended model = new StockTutorModelExtended();
    model.createPortfolio("Health Portfolio");
    float purchaseAmount = 2000;
    String ticker = "GOOG";

    String expected = "Commission fees cannot be negative";

    Date date = DateUtility.
            convertStringFromUserToDate("18-03-2019 13:30:00");
    try {
      model.buyStockWithCommission("Health Portfolio", ticker, purchaseAmount,
              date, -10);
    } catch (IllegalArgumentException e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Negative commission fees did not throw exception");
  }


  @Test(expected = IllegalArgumentException.class)
  public void buyStockUsingCommissionGreaterThanPurchase() {

    IStockTutorModelExtended model = new StockTutorModelExtended();
    model.createPortfolio("Health Portfolio");
    float purchaseAmount = 2000;
    String ticker = "GOOG";

    String expected = "Commission fees cannot exceed purchase amount";

    Date date = DateUtility.
            convertStringFromUserToDate("18-03-2019 13:30:00");
    try {
      model.buyStockWithCommission("Health Portfolio", ticker, purchaseAmount,
              date, 3000);
    } catch (IllegalArgumentException e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Commission fees greater than purchase did not throw exception");
  }

  // Complete flow
  @Test
  public void testBuyStockWithCommissionCompleteFlow() {

    IStockTutorModelExtended model = new StockTutorModelExtended();

    // Create two portfolios for the model
    model.createPortfolio("ABC Portfolio");
    model.createPortfolio("XYZ Portfolio");

    // Buy google stock to "ABC Portfolio"
    Date googDate = DateUtility.convertStringFromUserToDate("3-4-2019 12:00:00");
    model.buyStockWithCommission("ABC Portfolio", "GOOG", 20000, googDate,
            500);

    // Buy microsoft stock to "XYZ Portfolio"
    Date msftDate = DateUtility.
            convertStringFromUserToDate("14-07-2003 13:30:00");
    model.buyStockWithCommission("XYZ Portfolio", "MSFT", 160,
            msftDate, 100); // buys 4

    // Assert on all user portfolios
    List<IPortfolioInfo> portfolioList = model.getAllUserPortfolio();
    assertEquals("ABC Portfolio", portfolioList.get(0).getPortfolioName());
    assertEquals("XYZ Portfolio", portfolioList.get(1).getPortfolioName());

    // assert on purchase history items for "ABC portfolio"
    List<IPurchaseHistory> purchaseHistoryList1 =
            portfolioList.get(0).getPortfolioItems();

    assertEquals(16, purchaseHistoryList1.get(0).getQuantity());
    String actualDate = DateUtility.convertDateToUserReadableString(
            purchaseHistoryList1.get(0).getDateOfPurchase());
    assertEquals("03-04-2019 12:00:00", actualDate);
    assertEquals(19794.72, purchaseHistoryList1.get(0).getCostBasisForPurchase(), 0.01);
    assertEquals("GOOG", purchaseHistoryList1.get(0).getTickerSymbol());

    // assert on purchase history items for "XYZ portfolio"
    List<IPurchaseHistory> purchaseHistoryList2 =
            portfolioList.get(1).getPortfolioItems();

    assertEquals(2, purchaseHistoryList2.get(0).getQuantity());
    String actualDate2 = DateUtility.convertDateToUserReadableString(
            purchaseHistoryList2.get(0).getDateOfPurchase());
    assertEquals("14-07-2003 13:30:00", actualDate2);
    assertEquals(154.80, purchaseHistoryList2.get(0).getCostBasisForPurchase(), 0.01);
    assertEquals("MSFT", purchaseHistoryList2.get(0).getTickerSymbol());

    // for both
    Date dateToCalculateCostBasis = DateUtility.
            convertStringFromUserToDate("30-05-2013 13:30:00");

    Date dateToCalculateTotalValue = DateUtility.
            convertStringFromUserToDate("30-05-2014 13:30:00");

    assertEquals(154.80, model.getTotalCostBasis("XYZ Portfolio",
            dateToCalculateCostBasis), 0.01);

    assertEquals(0, model.getTotalValueForGivenDate("ABC Portfolio",
            dateToCalculateTotalValue), 0.01);
  }

  @Test
  public void testBuyStockWithAndWithoutCommissionCompleteFlow() {

    IStockTutorModelExtended model = new StockTutorModelExtended();

    // Create two portfolios for the model
    model.createPortfolio("ABC Commission");
    model.createPortfolio("XYZ NoCommission");

    // Buy google stock to "ABC Commission"
    Date googDate = DateUtility.convertStringFromUserToDate("3-4-2019 12:00:00");
    model.buyStockWithCommission("ABC Commission", "GOOG", 20000, googDate,
            5000);

    model.buyStock("XYZ NoCommission", "GOOG", 20000, googDate);

    // Assert on all user portfolios
    List<IPortfolioInfo> portfolioList1 = model.getAllUserPortfolio();
    assertEquals("ABC Commission", portfolioList1.get(0).getPortfolioName());
    assertEquals("XYZ NoCommission", portfolioList1.get(1).getPortfolioName());

    // assert on purchase history items for "ABC Commission"
    List<IPurchaseHistory> purchaseHistoryList1 =
            portfolioList1.get(0).getPortfolioItems();

    assertEquals(12, purchaseHistoryList1.get(0).getQuantity());
    String actualDate = DateUtility.convertDateToUserReadableString(
            purchaseHistoryList1.get(0).getDateOfPurchase());
    assertEquals("03-04-2019 12:00:00", actualDate);
    assertEquals(19471.04, purchaseHistoryList1.get(0).getCostBasisForPurchase(), 0.01);
    assertEquals("GOOG", purchaseHistoryList1.get(0).getTickerSymbol());


    // assert on purchase history items for "XYZ NoCommission"
    List<IPurchaseHistory> purchaseHistoryList2 =
            portfolioList1.get(1).getPortfolioItems();

    assertEquals(16, purchaseHistoryList2.get(0).getQuantity());
    String actualDate2 = DateUtility.convertDateToUserReadableString(
            purchaseHistoryList2.get(0).getDateOfPurchase());
    assertEquals("03-04-2019 12:00:00", actualDate2);
    assertEquals(19294.72, purchaseHistoryList2.get(0).getCostBasisForPurchase(), 0.01);
    assertEquals("GOOG", purchaseHistoryList2.get(0).getTickerSymbol());


    // for both
    Date dateToCalculateCostBasis = DateUtility.
            convertStringFromUserToDate("03-04-2019 12:00:00");

    Date dateToCalculateTotalValuePast = DateUtility.
            convertStringFromUserToDate("01-01-2019 12:00:00");

    assertEquals(19471.04, model.getTotalCostBasis("ABC Commission",
            dateToCalculateCostBasis), 0.01);
    assertEquals(19294.72, model.getTotalCostBasis("XYZ NoCommission",
            dateToCalculateCostBasis), 0.01);

    assertEquals(0, model.getTotalValueForGivenDate("ABC Commission",
            dateToCalculateTotalValuePast), 0.01);

    assertEquals(0, model.getTotalValueForGivenDate("XYZ NoCommission",
            dateToCalculateTotalValuePast), 0.01);
  }

  //File Operation tests
  @Test
  public void testFileSaveAsJsonOperations() {

    IStockTutorModelExtended model = new StockTutorModelExtended();
    model.createPortfolio("test save");
    Date date = DateUtility.convertStringFromUserToDate("3-4-2019 12:30:30");
    model.buyStockWithCommission("test save", "GOOG", 20000.0f, date,
            0.0f);
    String path = getClass().getClassLoader().toString() + "TestSave";
    model.savePortfolioToFile(path, "test save");
    try {

      byte[] encoded = Files.readAllBytes(Paths.get("D:\\checkTestSave.txt"));
      String fileData = new String(encoded, StandardCharsets.UTF_8);
      assertEquals("{\n" +
              "  \"portfolioName\": \"test save\",\n" +
              "  \"portfolioItems\": [\n" +
              "    {\n" +
              "      \"tickerSymbol\": \"GOOG\",\n" +
              "      \"quantity\": 16,\n" +
              "      \"purchaseAmount\": 19294.72,\n" +
              "      \"dateOfPurchase\": \"03-04-2019 12:30:30\"\n" +
              "    }\n" +
              "  ]\n" +
              "}", fileData);

    } catch (Exception e) {

      fail(e.getMessage());
    }
  }

  @Test
  public void testFileOpenAndDeserializationOperations() {

    IStockTutorModelExtended model = new StockTutorModelExtended();
    InputStream filePath = getClass().getClassLoader()
            .getResourceAsStream("testReadJSON.txt");
    IPortfolioInfo portfolio = model.getPortfolioFromFile(filePath);
    assertEquals("Portfolio name", portfolio.getPortfolioName());
    assertEquals(2, portfolio.getPortfolioItems().size());
  }

  @Test
  public void testPortfolioNotfoundFileOperation() {

    IStockTutorModelExtended model = new StockTutorModelExtended();

    try {
      model.savePortfolioToFile("D:\\checkTestSave.txt", "test save");
    } catch (Exception e) {

      assertEquals("Portfolio does not exist", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioObjectFileRead() {

    IStockTutorModelExtended model = new StockTutorModelExtended();
    InputStream filePath = getClass().getClassLoader()
            .getResourceAsStream("NoValidPOrtfolio.txt");
    try {
      IPortfolioInfo portfolio = model.getPortfolioFromFile(filePath);
    } catch (Exception e) {
      assertEquals("Unable to read portfolio.", e.getMessage());
    }
  }
}