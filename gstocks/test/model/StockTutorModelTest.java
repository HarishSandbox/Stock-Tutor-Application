package model;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import util.DateUtility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This class represents all tests for StockTutorModel.
 */
public class StockTutorModelTest {

  // Test for getStockPrice()
  @Test
  public void testgetPriceOfStock() {

    IStockTutorModel model = new StockTutorModel();
    Date date = DateUtility.convertStringFromUserToDate("18-03-2019 10:30:00");
    float price = model.getStockPrice("GOOG", date);
    assertEquals(1184.26, price, 0.01);
  }


  // Negative tests for getStockPrice()
  @Test(expected = IllegalArgumentException.class)
  public void testFetchEmptyStock() {

    IStockTutorModel model = new StockTutorModel();

    String expected = "Illegal ticker symbol received.";
    try {
      model.getStockPrice("", new Date());

    } catch (Exception e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Empty ticker symbol fetch did not fail");
  }


  @Test(expected = IllegalArgumentException.class)
  public void testFetchNullStock() {
    IStockTutorModel model = new StockTutorModel();

    String expected = "Illegal ticker symbol received.";
    try {
      model.getStockPrice(null, new Date());
    } catch (Exception e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Null ticker symbol fetch didnot fail");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetStockForInvalidDate() {


    IStockTutorModel model = new StockTutorModel();
    Date date = DateUtility.convertStringFromUserToDate("18-03-2019");
    String expected = "Date cannot be null or Invalid";
    try {
      model.getStockPrice("GOOG", date);
    } catch (Exception e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Empty portfolio creation did not fail");
  }


  // Test createPortfolio()

  @Test
  public void testCreatePortfolio() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("ABC");

    List<IPortfolioInfo> portfolioList = model.getAllUserPortfolio();

    // Assert on all user portfolios
    assertEquals("ABC", portfolioList.get(0).getPortfolioName());
  }


  @Test
  public void testCreatePortfolioWithNum() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("ABC11");

    List<IPortfolioInfo> portfolioList = model.getAllUserPortfolio();

    // Assert on all user portfolios
    assertEquals("ABC11", portfolioList.get(0).getPortfolioName());
  }

  // Negative tests for createPortfolio()

  @Test(expected = IllegalArgumentException.class)
  public void testCreateEmptyPortfolio() {

    IStockTutorModel model = new StockTutorModel();
    String expected = "Portfolio name cannot be empty";
    try {
      model.createPortfolio("");
    } catch (Exception e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Empty portfolio creation didnot fail");

  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateNullPortfolio() {

    IStockTutorModel model = new StockTutorModel();
    String expected = "Portfolio cannot be null";
    try {
      model.createPortfolio(null);
    } catch (Exception e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Null portfolio creation did not fail");

  }


  @Test(expected = IllegalArgumentException.class)
  public void testCreateDuplicatePortfolio() {

    IStockTutorModel model = new StockTutorModel();
    String expected = "Portfolio name should be unique";
    model.createPortfolio("TVS");
    try {
      model.createPortfolio("TVS");
    } catch (Exception e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Duplicate portfolio creation did not fail");

  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPortfolioName() {

    IStockTutorModel model = new StockTutorModel();
    String expected = "Portfolio cannot have special characters.";
    try {
      model.createPortfolio("TVS@");
    } catch (Exception e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Invalid portfolio name did not fail");

  }

  // Test getPortfolioAsString()

  @Test
  public void getGetPortfolio() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("ABC");
    List<IPortfolioInfo> portfolioList = model.getAllUserPortfolio();

    // Assert on user portfolios
    assertEquals("ABC", portfolioList.get(0).getPortfolioName());
  }


  // Negative tests for getPortfolioAsString()

  @Test(expected = IllegalArgumentException.class)
  public void getNullPortfolio() {

    IStockTutorModel model = new StockTutorModel();
    String expected = "Portfolio cannot be null";
    try {
      model.getPortfolio(null);
    } catch (IllegalArgumentException e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Null portfolio did not throw exception");
  }

  @Test(expected = IllegalArgumentException.class)
  public void getEmptyPortfolio() {

    IStockTutorModel model = new StockTutorModel();
    String expected = "Portfolio cannot be null";
    try {
      model.getPortfolio(null);
    } catch (IllegalArgumentException e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Null portfolioName did not throw exception");
  }


  // Test getAllUserPortfolio()

  @Test
  public void getAllUserPortfolio() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("ABC");
    model.createPortfolio("EFG");
    model.createPortfolio("XYZ");

    List<IPortfolioInfo> portfolioList = model.getAllUserPortfolio();

    // Assert on all user portfolios
    assertEquals("ABC", portfolioList.get(0).getPortfolioName());
    assertEquals("EFG", portfolioList.get(1).getPortfolioName());
    assertEquals("XYZ", portfolioList.get(2).getPortfolioName());
  }


  // Negative tests for getAllUserPortfolio
  @Test
  public void getPortfolioWhenNoPortfolio() {

    IStockTutorModel model = new StockTutorModel();
    List<IPortfolioInfo> portfolioList = model.getAllUserPortfolio();
    assertTrue(portfolioList.isEmpty());
  }

  // Tests for getCostBasisForPurchase()
  @Test
  public void getTotalCostBasis() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("Health Portfolio");

    Date msftDate = DateUtility.convertStringFromUserToDate("12-05-2005 13:30:00");
    Date googDate = DateUtility.convertStringFromUserToDate("02-05-2014 13:30:00");
    Date dateToCalculateCostBasis = DateUtility.
            convertStringFromUserToDate("30-05-2016 13:30:00");

    model.buyStock("Health Portfolio", "MSFT", 100f, msftDate);

    assertEquals(100.0, model.getTotalCostBasis("Health Portfolio",
            dateToCalculateCostBasis), 0);

    model.buyStock("Health Portfolio", "GOOG", 2000, googDate);

    assertEquals(1683.79, model.getTotalCostBasis("Health Portfolio",
            dateToCalculateCostBasis), 0.01);

  }

  @Test
  public void getTotalCostBasisInPast() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("Health Portfolio");

    Date msftDate = DateUtility.convertStringFromUserToDate("12-05-2005 13:30:00");
    Date googDate = DateUtility.convertStringFromUserToDate("02-05-2014 13:30:00");
    Date dateToCalculateCostBasis = DateUtility.
            convertStringFromUserToDate("30-05-2013 13:30:00");

    model.buyStock("Health Portfolio", "MSFT", 100f, msftDate);

    assertEquals(100, model.getTotalCostBasis("Health Portfolio",
            dateToCalculateCostBasis), 0);

    float unusedAmount = model.buyStock("Health Portfolio", "GOOG",
            2000, googDate);

    assertEquals(416.21, unusedAmount, 0.01);

    assertEquals(100, model.getTotalCostBasis("Health Portfolio",
            dateToCalculateCostBasis), 0.01);

  }


  @Test
  public void getTotalCostBasisPriorToAnyPurchase() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("Health Portfolio");

    Date msftDate = DateUtility.convertStringFromUserToDate("12-05-2005 13:30:00");
    Date googDate = DateUtility.convertStringFromUserToDate("02-05-2014 13:30:00");

    Date dateToCalculateCostBasis = DateUtility.
            convertStringFromUserToDate("30-05-2003 13:30:00");

    model.buyStock("Health Portfolio", "MSFT", 100f, msftDate);
    model.buyStock("Health Portfolio", "GOOG", 2000, googDate);

    assertEquals(0, model.getTotalCostBasis("Health Portfolio",
            dateToCalculateCostBasis), 0);
  }

  @Test
  public void getTotalCostBasisForEmptyPortfolio() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("Health Portfolio");

    Date dateToCalculateCostBasis = DateUtility.
            convertStringFromUserToDate("02-05-2014 13:30:00");
    assertEquals(0, model.getTotalCostBasis("Health Portfolio",
            dateToCalculateCostBasis), 0);
  }

  // Negative tests for getCostBasisForPurchase()

  @Test(expected = IllegalArgumentException.class)
  public void getTotalCostBasisForInvalidDate() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("Health Portfolio");

    Date dateToCalculateCostBasis = DateUtility.
            convertStringFromUserToDate("02-05-2014");
    String expected = "Date cannot not be null or Invalid";
    try {
      model.getTotalCostBasis("Health Portfolio",
              dateToCalculateCostBasis);
    } catch (IllegalArgumentException e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Invalid date did not throw exception");
  }


  // Tests for getTotalValueForGivenDate()
  @Test
  public void getTotalValueForGivenDate() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("XYZ Portfolio");

    Date msftDate = DateUtility.convertStringFromUserToDate("14-07-2003 13:30:00");
    Date dateToCalculateTotalValue = DateUtility.
            convertStringFromUserToDate("30-05-2014 13:30:00"); // msft = 40.94

    model.buyStock("XYZ Portfolio", "MSFT", 100, msftDate); // buys 4

    assertEquals(122.82, model.getTotalValueForGivenDate("XYZ Portfolio",
            dateToCalculateTotalValue), 0.01);
  }

  @Test
  public void getTotalValueForGivenDateBeforePurchaseDate() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("XYZ Portfolio");

    Date msftDate = DateUtility.convertStringFromUserToDate("14-07-2003 13:30:00");
    Date dateToCalculateTotalValue = DateUtility.
            convertStringFromUserToDate("30-05-2001 13:30:00"); // msft = 40.94

    model.buyStock("XYZ Portfolio", "MSFT", 100, msftDate); // buys 4

    assertEquals(0, model.getTotalValueForGivenDate("XYZ Portfolio",
            dateToCalculateTotalValue), 0.01);
  }


  // Negative tests for getTotalValueForGivenDate()
  @Test(expected = IllegalArgumentException.class)
  public void getTotalValueForInvalidDate() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("Health Portfolio");

    Date dateToCalculateTotalValue = DateUtility.
            convertStringFromUserToDate("30");
    String expected = "Date cannot not be null or Invalid";
    try {
      model.getTotalValueForGivenDate("Health Portfolio",
              dateToCalculateTotalValue);
    } catch (IllegalArgumentException e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Invalid date did not throw exception");
  }

  @Test
  public void getTotalValueForEmptyPortfolio() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("Health Portfolio");

    Date dateToCalculateTotalValue = DateUtility.
            convertStringFromUserToDate("02-05-2014 13:30:00");
    assertEquals(0, model.getTotalValueForGivenDate("Health Portfolio",
            dateToCalculateTotalValue), 0);
  }

  // Negative tests for buyStock()
  @Test(expected = IllegalArgumentException.class)
  public void buyStockForInvalidDate() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("Health Portfolio");

    Date invalidDate = DateUtility.
            convertStringFromUserToDate("30");
    String expected = "Date should not be null or Invalid";
    try {
      model.buyStock("Health Portfolio", "GOOG", 1000, invalidDate);
    } catch (IllegalArgumentException e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Invalid date did not throw exception");
  }


  @Test(expected = IllegalArgumentException.class)
  public void buyStockOnWeekend() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("Health Portfolio");

    Date date = DateUtility.
            convertStringFromUserToDate("16-03-2019 13:30:00");
    String expected = "Stock cannot be purchased on a holiday.";
    try {
      model.buyStock("Health Portfolio", "GOOG", 1000, date);
    } catch (IllegalArgumentException e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Trading during weekend did not throw exception");
  }


  @Test(expected = IllegalArgumentException.class)
  public void buyStockAfterTradingHours() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("Health Portfolio");

    Date date = DateUtility.
            convertStringFromUserToDate("18-03-2019 17:30:00");
    String expected = "Stock should be purchased between 9am - 4pm only.";
    try {
      model.buyStock("Health Portfolio", "GOOG", 1000, date);
    } catch (IllegalArgumentException e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("After hours trading did not throw exception");
  }

  @Test
  public void createPortfolioAddWrongAndRightStocks() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("Health Portfolio");

    Date date = DateUtility.convertStringFromUserToDate("18-03-2019 17:30:00");
    String expected = "Stock should be purchased between 9am - 4pm only.";
    try {
      model.buyStock("Health Portfolio", "GOOG", 1000, date);
      fail("Buying not allowed.");
    } catch (IllegalArgumentException e) {
      assertEquals(expected, e.getMessage());
    }

    Date date2 = DateUtility.convertStringFromUserToDate("18-03-2019 15:59:59");
    try {
      model.buyStock("Health Portfolio", "GOOG", 2000, date2);

    } catch (IllegalArgumentException e) {

      fail(e.getMessage());
    }

    List<IPortfolioInfo> portfolioList = model.getAllUserPortfolio();

    // Assert on all user portfolios
    assertEquals("Health Portfolio", portfolioList.get(0).getPortfolioName());

    // assert on purchase history items for "Health Portfolio"
    List<IPurchaseHistory> purchaseHistoryList1 =
            portfolioList.get(0).getPortfolioItems();

    assertEquals(1, purchaseHistoryList1.get(0).getQuantity());
    String actualDate = DateUtility.convertDateToUserReadableString(
            purchaseHistoryList1.get(0).getDateOfPurchase());
    assertEquals("18-03-2019 15:59:59", actualDate);
    assertEquals(1184.26, purchaseHistoryList1.get(0).getCostBasisForPurchase(), 0.01);
    assertEquals("GOOG", purchaseHistoryList1.get(0).getTickerSymbol());


    model.createPortfolio("Test new");

    // Assert on all user portfolios
    List<IPortfolioInfo> portfolioList1 = model.getAllUserPortfolio();
    assertEquals("Health Portfolio", portfolioList1.get(0).getPortfolioName());
    assertEquals("Test new", portfolioList1.get(1).getPortfolioName());

  }


  @Test(expected = IllegalArgumentException.class)
  public void buyStockUsingInsufficientAmount() {

    IStockTutorModel model = new StockTutorModel();
    model.createPortfolio("Health Portfolio");
    float purchaseAmount = 10;
    String ticker = "GOOG";

    Date date = DateUtility.
            convertStringFromUserToDate("18-03-2019 13:30:00");
    String expected = "The price of the stock is " + 1184.26 + ". Give "
            + "sufficient amount to make a purchase.";
    try {
      model.buyStock("Health Portfolio", ticker, purchaseAmount, date);
    } catch (IllegalArgumentException e) {
      assertEquals(expected, e.getMessage());
      throw e;
    }
    fail("Insufficient amount did not throw exception");
  }

  // Complete flow
  @Test
  public void testBuyStockCompleteFlow() {

    IStockTutorModel model = new StockTutorModel();

    // Create two portfolios for the model
    model.createPortfolio("ABC Portfolio");
    model.createPortfolio("XYZ Portfolio");

    // Buy microsoft stock to "XYZ Portfolio"
    Date msftDate = DateUtility.
            convertStringFromUserToDate("14-07-2003 13:30:00");
    model.buyStock("XYZ Portfolio", "MSFT", 160, msftDate); // buys 4

    // Buy google stock to "ABC Portfolio"
    Date googDate = DateUtility.convertStringFromUserToDate("02-05-2014 13:30:00");
    model.buyStock("ABC Portfolio", "GOOG", 2000, googDate);

    List<IPortfolioInfo> portfolioList = model.getAllUserPortfolio();

    // Assert on all user portfolios
    assertEquals("ABC Portfolio", portfolioList.get(0).getPortfolioName());
    assertEquals("XYZ Portfolio", portfolioList.get(1).getPortfolioName());

    // assert on purchase history items for "ABC portfolio"
    List<IPurchaseHistory> purchaseHistoryList1 =
            portfolioList.get(0).getPortfolioItems();

    assertEquals(3, purchaseHistoryList1.get(0).getQuantity());
    String actualDate = DateUtility.convertDateToUserReadableString(
            purchaseHistoryList1.get(0).getDateOfPurchase());
    assertEquals("02-05-2014 13:30:00", actualDate);
    assertEquals(1583.79, purchaseHistoryList1.get(0).getCostBasisForPurchase(), 0.01);
    assertEquals("GOOG", purchaseHistoryList1.get(0).getTickerSymbol());

    // assert on purchase history items for "ABC portfolio"
    List<IPurchaseHistory> purchaseHistoryList2 =
            portfolioList.get(1).getPortfolioItems();

    assertEquals(5, purchaseHistoryList2.get(0).getQuantity());
    String actualDate2 = DateUtility.convertDateToUserReadableString(
            purchaseHistoryList2.get(0).getDateOfPurchase());
    assertEquals("14-07-2003 13:30:00", actualDate2);
    assertEquals(137.00, purchaseHistoryList2.get(0).getCostBasisForPurchase(), 0.01);
    assertEquals("MSFT", purchaseHistoryList2.get(0).getTickerSymbol());

    Date dateToCalculateCostBasis = DateUtility.
            convertStringFromUserToDate("30-05-2013 13:30:00");

    Date dateToCalculateTotalValue = DateUtility.
            convertStringFromUserToDate("30-05-2014 13:30:00");

    assertEquals(137, model.getTotalCostBasis("XYZ Portfolio",
            dateToCalculateCostBasis), 0.01);

    assertEquals(1679.67, model.getTotalValueForGivenDate("ABC Portfolio",
            dateToCalculateTotalValue), 0.01);
  }

}