package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class user Alphadvantage third party source APIs to get data for the stocks present in
 * NASDAQ portfolio. It implements {@code IStockInputSource} interface.
 */
class StockAPI implements IStockInputSource {

  private final String apiKey = "4JYDHFPS3Q5I1SMV";
  private final String timeFuncParam = "TIME_SERIES_DAILY";

  @Override
  public Stock fetchStock(String ticker) throws IllegalStateException {

    String data = getDataFromAPI(ticker);
    try {
      return new Stock(ticker, parseStock(data));
    } catch (ParseException e) {
      throw new IllegalStateException("Failed to fetch stock from server");
    }
  }

  private String getDataFromAPI(String ticker) throws IllegalArgumentException {

    URL url;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=" + timeFuncParam
              + "&outputsize=full"
              + "&datatype=csv"
              + "&symbol"
              + "=" + ticker + "&apikey=" + apiKey);
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException("Unable to fetch stock data for " + ticker);
    }
    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try {
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
      return output.toString();
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + ticker);
    }
  }

  private Map<Date, Float> parseStock(String stockResult) throws ParseException {

    String[] entries = stockResult.split("\n");

    Map<Date, Float> tradeValues = new TreeMap<>();

    boolean discardInitialValue = false;
    for (String input : entries) {
      if (!discardInitialValue) {
        discardInitialValue = true;
        continue;
      }

      String[] entryValues = input.split(",");
      if (entryValues.length < 6) {
        throw new ParseException("no data found", entryValues.length);
      }

      String dateString = entryValues[0];
      Float price = Float.parseFloat(entryValues[4]);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date date = sdf.parse(dateString);
      tradeValues.put(date, price);
    }
    return tradeValues;
  }
}