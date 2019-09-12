package model;

import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import controller.FeaturesExtended;
import controller.StockTutorMainControllerExtended;
import strategies.InvestmentStrategy;
import util.DateUtility;
import view.GraphicalViewExtended;
import view.IStockTutorViewExtended;

/**
 * This class will test all use cases related to buying stocks for a portfolio when applying
 * strategy.
 */
public class StockTutorStrategyTest {


  // Negative tests for buyStockWithCommission()
  @Test(expected = IllegalArgumentException.class)
  public void buyStockForInvalidDate() {

    IStockTutorStrategy model = new StockTutorStrategy();
    model.createPortfolio("Health Portfolio");

    Date invalidDate = DateUtility.
            convertStringFromUserToDate("30");
    String expected = "Date should not be null or Invalid";
    try {
      model.buyPartialStocksForStrategy("Health Portfolio", "GOOG",
              1000,
              invalidDate, 1000);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals(expected, e.getMessage());
      throw e;
    }
    Assert.fail("Invalid date did not throw exception");
  }

  //File Operation tests
  @Test
  public void testStrategyFileSaveAsJsonOperations() {

    Date date = DateUtility.convertStringFromUserToDate("3-4-2019 12:30:30");
    Map<String, Float> stocks = new HashMap<>();
    stocks.put("MSFT", 70f);
    stocks.put("GOOG", 30f);

    IStockTutorStrategy model = new StockTutorStrategy();
    IStockTutorViewExtended view = new GraphicalViewExtended();
    FeaturesExtended cont = new StockTutorMainControllerExtended(model, view);
    cont.createDollarCostStrategy("strategy name", 2000f, 30, date, null, stocks, 20f);
    cont.applyStrategy("test port", "strategy name");

    String path = getClass().getClassLoader().toString() + "TestSave";
    model.saveStrategyToFile("strategy name", "D:\\ewnr.txt");
    try {

      byte[] encoded =
              Files.readAllBytes(Paths.get("D:\\ewnr.txt"));
      String fileData = new String(encoded, StandardCharsets.UTF_8);
      Assert.assertEquals("{\n" +
              "  \"strategyName\": \"strategy name\",\n" +
              "  \"startDate\": \"Wednesday, April 3, 2019 12:30:30 PM EDT\",\n" +
              "  \"frequency\": 30,\n" +
              "  \"price\": 2000.0,\n" +
              "  \"commissionFee\": 20.0,\n" +
              "  \"stocks\": {\n" +
              "    \"MSFT\": 70.0,\n" +
              "    \"GOOG\": 30.0\n" +
              "  }\n" +
              "}", fileData);

    } catch (Exception e) {

      Assert.fail(e.getMessage());
    }
  }

  @Test
  public void testFileOpenAndDeserializationOperations() {

    IStockTutorStrategy model = new StockTutorStrategy();
    InputStream filePath = getClass().getClassLoader()
            .getResourceAsStream("testReadStrategy.txt");
    InvestmentStrategy strategy = model.getStrategyFromFile(filePath);
    Assert.assertEquals("test file", strategy.getStrategyName());
    Assert.assertEquals(2, strategy.getStockAndWeights().size());
  }

  @Test
  public void testPortfolioNotfoundFileOperation() {

    IStockTutorStrategy model = new StockTutorStrategy();

    try {
      model.saveStrategyToFile("D:\\checkTestSave.txt", "test save");
    } catch (Exception e) {

      Assert.assertEquals("Strategy does not exist", e.getMessage());
    }
  }

  @Test
  public void testInvalidPortfolioObjectFileRead() {

    IStockTutorStrategy model = new StockTutorStrategy();
    InputStream filePath = getClass().getClassLoader()
            .getResourceAsStream("NoValidStrategy.txt");
    try {
      InvestmentStrategy strategy = model.getStrategyFromFile(filePath);
    } catch (Exception e) {
      Assert.assertEquals("Unable to read strategy.", e.getMessage());
    }
  }
}