package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A Utility class to perform date operations. It reduces the overhead of creation time for date
 * formatters and rewriting of several widely used {@code Date} and {@code Calendar} related
 * functions.
 */
public class DateUtility {

  private static SimpleDateFormat simpleDate = new SimpleDateFormat();

  static {
    simpleDate.setLenient(false);
  }

  /**
   * Converts a given string taken from the user to a Date or null if the string cannot be converted
   * to a {@code Date}.
   *
   * @param stringDate The date in the string format which is entered by the user.
   * @return A {@code Date} object of the string given or null if the string cannot be converted to
   *        a {@code Date}.
   */
  public static Date convertStringFromUserToDate(String stringDate) {

    simpleDate.applyPattern("dd-MM-yyyy HH:mm:ss");
    try {

      return simpleDate.parse(stringDate);
    } catch (ParseException p) {

      return null;
    }
  }

  /**
   * Converts {@code Date} to user readable {@code String}.
   *
   * @param date A {@code Date} object to be converted to simple string.
   * @return A String object which represents the date.
   */
  public static String convertDateToUserReadableString(Date date) {

    simpleDate.applyPattern("dd-MM-YYYY HH:mm:ss");
    DateFormat dateFormat = simpleDate;
    return dateFormat.format(date);
  }

  /**
   * This methods checks if a given date falls on a saturday or a sunday.
   *
   * @param date A {@code Date} object to be checked for a weekend.
   * @return A boolean true if it is a weekend , false otherwise.
   */
  public static boolean isDateAWeekend(Date date) {

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    return (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
            || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
  }

  /**
   * This method checks if the time in the given {@code Date} is within the working hours of the
   * stock market.
   *
   * @param date A {@code Date} object to be checked for a working hours.
   * @return A boolean true if it is within working hours , false otherwise.
   */
  public static boolean isDateInWorkingHours(Date date) {

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    int hour = calendar.get(Calendar.HOUR_OF_DAY);

    return (hour >= 9 && hour <= 16);
  }

  /**
   * To check if two dates are on same days of the year.
   *
   * @param date1 The first Date.
   * @param date2 The Date to be compared with the previous argument date.
   * @return A boolean true if they are on same days of the year, false otherwise.
   */
  public static boolean checkBothDatesAreOnSameDays(Date date1, Date date2) {

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date1);

    int day1 = getDate(calendar);
    int month1 = getMonth(calendar);
    int year1 = getYear(calendar);

    calendar.setTime(date2);

    int day2 = getDate(calendar);
    int month2 = getMonth(calendar);
    int year2 = getYear(calendar);

    return (year1 == year2 && month1 == month2 && day1 == day2);
  }

  /**
   * To check in the given date if the first date is less than or equal to the second date.
   *
   * @param given The first Date.
   * @param other The Date to be compared with the previous argument date.
   * @return A {@code boolean} true if the first date is less than or equal to the second date,
   *        false otherwise.
   */
  public static boolean isGivenDateLessThanOrEqualToOtherDate(Date given, Date other) {

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(given);

    int day1 = getDate(calendar);
    int month1 = getMonth(calendar);
    int year1 = getYear(calendar);

    calendar.setTime(other);

    int day2 = getDate(calendar);
    int month2 = getMonth(calendar);
    int year2 = getYear(calendar);

    if (year1 < year2) {

      return true;
    } else if (year1 == year2) {

      if (month1 < month2) {

        return true;
      } else if (month1 == month2) {

        return day1 <= day2;
      }
    }
    return false;
  }

  /**
   * To check if the given date is greater than the other date.
   *
   * @param given The first date.
   * @param other The Date to be compared with the previous argument date.
   * @return A {@code boolean} true if the first date is greater than to the second date, false
   *        otherwise.
   */
  public static boolean isGivenDateGreaterThanOtherDate(Date given, Date other) {

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(given);

    int day1 = getDate(calendar);
    int month1 = getMonth(calendar);
    int year1 = getYear(calendar);

    calendar.setTime(other);

    int day2 = getDate(calendar);
    int month2 = getMonth(calendar);
    int year2 = getYear(calendar);

    if (year1 > year2) {

      return true;
    } else if (year1 == year2) {

      if (month1 > month2) {

        return true;
      } else if (month1 == month2) {

        return day1 > day2;
      }
    }
    return false;
  }

  /**
   * To check if a given date is in future time from today. It also checks if the date is within the
   * investment open hours if the date is today's date.
   *
   * @param given The given date to compare if it is in future.
   * @return A boolean true if it is not a future date and within the investment timings.
   */
  public static boolean isNotFutureDateForInvestment(Date given) {

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(given);

    int day1 = getDate(calendar);
    int month1 = getMonth(calendar);
    int year1 = getYear(calendar);
    int hour1 = calendar.get(Calendar.HOUR_OF_DAY);

    calendar.setTime(new Date());

    int day2 = getDate(calendar);
    int month2 = getMonth(calendar);
    int year2 = getYear(calendar);

    if (year1 < year2) {

      return true;
    } else if (year1 == year2) {

      if (month1 < month2) {

        return true;
      } else if (month1 == month2) {

        if (day1 < day2) {

          return true;
        } else if (day1 == day2) {

          return hour1 < 16;
        }
      }
    }
    return false;
  }

  /**
   * Use this method to get the next date by adding the specified number of days to the given date.
   *
   * @param date  the date to which days have to be added.
   * @param after the number of days that have to be added to the given date.
   * @return A Date object that is the final date after adding days.
   */
  public static Date getNextDate(Date date, int after) {

    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DAY_OF_YEAR, after);
    return c.getTime();
  }

  /**
   * Returns the next Date which is a non weekend if the given date is a weekend.
   *
   * @param date the date which has to be checked.
   * @return A Date object which is the date after the weekend if the given date is a weekend.
   */
  public static Date getNextDateAfterAWeekendDate(Date date) {

    Calendar c = Calendar.getInstance();
    c.setTime(date);
    if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {

      c.add(Calendar.DAY_OF_YEAR, 2);
      return c.getTime();
    } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {

      c.add(Calendar.DAY_OF_YEAR, 1);
      return c.getTime();
    }
    return date;
  }

  /**
   * Returns the previous Date which is a non weekend if the given date is a weekend.
   *
   * @param date the date which has to be checked.
   * @return A Date object which is the date after the weekend if the given date is a weekend.
   */
  public static Date getPreviousDateToAWeekend(Date date) {

    Calendar c = Calendar.getInstance();
    c.setTime(date);
    if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {

      c.add(Calendar.DAY_OF_YEAR, -1);
      return c.getTime();
    } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {

      c.add(Calendar.DAY_OF_YEAR, -2);
      return c.getTime();
    }
    return date;
  }

  private static int getDate(Calendar calendar) {

    return calendar.get(Calendar.DAY_OF_MONTH);
  }

  private static int getMonth(Calendar calendar) {

    return calendar.get(Calendar.MONTH) + 1;
  }

  private static int getYear(Calendar calendar) {

    return calendar.get(Calendar.YEAR);
  }
}