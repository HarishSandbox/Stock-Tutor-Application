package view;

import java.util.Date;

/**
 * This interface has methods for text based view of the application. It extends {@code
 * IStockTutorViewExtended} interface which provides operations required by the view to perform
 * irrespective of the interface type.
 */
public interface ITextBasedView extends IStockTutorViewExtended {

  /**
   * A method which gets the input from the user. When a user enters 'q' or "quit" the application
   * terminates.
   *
   * @return A {@code String} object containing the input from the user.
   */
  String getStringInput();

  /**
   * A method which will take valid input date from the user and returns it. The date is in 24 hour
   * date format.
   *
   * @return A {@code Date} object entered by the user.
   */
  Date getDate();

  /**
   * A method which will take valid numeric input from the user which can hold floating values.
   *
   * @return A {@code float} value of the input given by the user.
   */
  float getFloat();

  /**
   * A method which will take valid numeric input from the user which can hold positive integer
   * values.
   *
   * @return A {@code int} value of the input given by the user.
   */
  int getInt();

  /**
   * A method which will display the list of operations the user is allowed to perform.
   */
  void displayMenu();
}
