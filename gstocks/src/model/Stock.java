package model;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import util.DateUtility;

/**
 * The stock class has details of the stock. It has the symbol and prices of the stock on different
 * dates.
 */
class Stock {

  private final String tickerSymbol;
  private Map<Date, Float> priceList;

  /**
   * Creates a new stock object given name and date, price dictionary.
   *
   * @param tickerSymbol A String value of the ticker symbol of the stock.
   * @param priceList    A map of {@code Date} keys and {@code float} prices.
   */
  Stock(String tickerSymbol, Map<Date, Float> priceList) {

    this.tickerSymbol = tickerSymbol;
    this.priceList = priceList;
  }

  /**
   * Returns the price of the stock at a given date. An exception is thrown if the stock price is
   * not present.
   *
   * @param date the date at which the price has to be found out.
   * @return A float value of the stock price at the given date.
   * @throws IllegalArgumentException thrown when a stock price cannot be found for a given date.
   */
  float getPrice(Date date) throws IllegalArgumentException {

    if (priceList == null || priceList.isEmpty()) {
      throw new IllegalArgumentException("There is no price found for the given date.");
    }

    if (date == null) {

      throw new IllegalArgumentException("There is no price found for the given date.");
    }

    Date keyDate = null;
    for (Date key : priceList.keySet()) {
      if (DateUtility.checkBothDatesAreOnSameDays(key, date)) {
        keyDate = key;
        break;
      }
    }
    if (keyDate == null) {
      throw new IllegalArgumentException("There is no price found for the given date.");
    }
    return this.priceList.get(keyDate);
  }

  /**
   * Returns the price on the date given if found. If not found it will return the last recent price
   * before the date given.
   *
   * @param date The Date at which the price has to be found.
   * @return A Float value of the giving the price of the stock which is most recent until date.
   * @throws IllegalArgumentException Thrown when the date value is null or when no price is found.
   */
  float getLatestPriceUntil(Date date) throws IllegalArgumentException {

    float price = 0.0f;

    try {

      price = getPrice(date);
    } catch (IllegalArgumentException exp) {

      if (exp.getMessage().equals("There is no price found for the given date.")) {
        TreeMap<Date, Float> sorted = new TreeMap<>(priceList);
        if (DateUtility.isGivenDateGreaterThanOtherDate(date, sorted.firstKey())) {

          Date lastDate = sorted.lowerKey(date);
          price = getPrice(lastDate);
        }
      }
    }
    return price;
  }

  /**
   * Returns the price of the stock on the given date if found. If not returns the immediate next
   * working day price.
   *
   * @param date The Date at which the price has to be found.
   * @return A float value which is the price of the stock on the date or next working date.
   * @throws IllegalArgumentException Thrown when the date value is null or when no price is found.
   */
  float getPriceOfNearestWorkingDay(Date date) throws IllegalArgumentException {

    float price = 0.0f;

    try {

      price = getPrice(date);
    } catch (IllegalArgumentException exp) {

      if (exp.getMessage().equals("There is no price found for the given date.")) {
        TreeMap<Date, Float> sorted = new TreeMap<>(priceList);
        if (DateUtility.isGivenDateGreaterThanOtherDate(date, sorted.firstKey())) {

          Date nextDate = sorted.higherKey(date);
          price = getPrice(nextDate);
        }
      }
    }
    return price;
  }

  /**
   * Returns the ticker symbol of the stock.
   *
   * @return A String value of the ticker symbol.
   */
  String getTickerSymbol() {
    return this.tickerSymbol;
  }
}