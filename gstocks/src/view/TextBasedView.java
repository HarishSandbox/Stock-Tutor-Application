package view;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import controller.Features;
import controller.FeaturesExtended;
import model.IPortfolioInfo;
import model.IPurchaseHistory;
import strategies.InvestmentStrategy;
import util.DateUtility;
import util.ViewUtils;

/**
 * The view component of the tutoring application. It is a Text based interface which works via
 * command line interface. The user enters input in the console and reads response on the console
 * screen.
 */
public class TextBasedView implements ITextBasedView {

  private final Scanner scanner;
  private final Appendable out;

  /**
   * Creates the view component by initializing its members.
   *
   * @param in  Readable object
   * @param out Appendable object.
   */
  public TextBasedView(Readable in, Appendable out) {

    this.out = out;
    this.scanner = new Scanner(in);
  }

  @Override
  public String getStringInput() throws IllegalStateException {

    String input = scanner.nextLine();

    while (input.isEmpty()) {
      input = scanner.nextLine();
    }
    this.checkForQuitting(input);
    return input;
  }

  @Override
  public void displayInfo(String info) throws IllegalStateException {

    try {
      this.out.append(info).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable object failed");
    }
  }

  @Override
  public void displayPortfolioInfo(IPortfolioInfo portfolio) throws IllegalStateException {

    StringBuilder portfolioState = new StringBuilder();
    portfolioState.append("***Portfolio: ").append(portfolio.getPortfolioName())
            .append("***").append("\n");

    if (portfolio.getPortfolioItems().isEmpty()) {
      displayErrorMsg("\n" + "----No stocks presents in the portfolio----");
      return;
    }
    IPurchaseHistory purchaseHistoryItem;
    for (int i = 0; i < portfolio.getPortfolioItems().size(); i++) {

      purchaseHistoryItem = portfolio.getPortfolioItems().get(i);

      String purchaseInfo = this.displayPurchaseInfo(purchaseHistoryItem);
      portfolioState.append("\t").append(i + 1).append(": ").append(purchaseInfo)
              .append("\n");
    }
    displayInfo(portfolioState.toString());
  }

  @Override
  public void displayAllUserPortfolios(List<IPortfolioInfo> portfolios)
          throws IllegalStateException {

    if (portfolios.size() == 0) {

      displayErrorMsg("User has no portfolios\n");
      return;
    }
    StringBuilder portfolioAsString = new StringBuilder();
    int i = 1;
    for (IPortfolioInfo portfolioObj : portfolios) {

      portfolioAsString.append("portfolio").append(i + ": ").append(portfolioObj.getPortfolioName())
              .append("\n\n");
      i++;
    }
    displayInfo(portfolioAsString.toString());
  }

  @Override
  public void displayTotalCostBasis(String portfolio, Date date, float price) {
    displayInfo("The total cost of the investment made into " + portfolio + " upto the" +
            " date " + date.toString() + " is " + String.format("%.2f", price));
  }

  @Override
  public void displayTotalValueForGivenDate(String portfolio, Date date, float price) {
    displayInfo("The current value of the investment made into " + portfolio + " upto the" +
            " date " + date.toString() + " is " + String.format("%.2f", price));
  }

  @Override
  public void displayBuyStock(String portfolioName, String tickerSymbol,
                              float amountUsed, Date dateOfPurchase, float remainingAmount) {
    String date = DateUtility.convertDateToUserReadableString(dateOfPurchase);
    displayInfo(String.format("A purchase has been made for $%.2f of %s stock in %s "
                    + "portfolio, for the date %s.", amountUsed, tickerSymbol,
            portfolioName, date));
    displayInfo(String.format("Remaining amount is $%.2f ", remainingAmount));
  }

  @Override
  public void displayStrategy(InvestmentStrategy strategy) {

    displayInfo(ViewUtils.constructStrategyInfoStringForGUI(strategy));
  }

  @Override
  public void setFeatures(Features features) {
    /**
     * This class doesn't use feature call back mechanism directly as it is handled by the
     * StockTutorCommandController class. This is because we have common controller which drives
     * the application. The setFeatures() initializes the features only in case of GUI and we have
     * decided to keep this method empty here in order to avoid typecasting.
     */
  }

  @Override
  public void setFeaturesExtended(FeaturesExtended feature) {
    /**
     * This class doesn't use feature call back mechanism directly as it is handled by the
     * StockTutorCommandController class. This is because we have common controller which drives
     * the application. The setFeatures() initializes the features only in case of GUI and we have
     * decided to keep this method empty here in order to avoid typecasting.
     */
  }

  @Override
  public void displayErrorMsg(String error) throws IllegalStateException {
    try {
      this.out.append(error).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable object failed");
    }
  }

  private String displayPurchaseInfo(IPurchaseHistory purchaseHistory) {

    return (String.format("%.2f", purchaseHistory.getQuantity()))
            + " shares of " + purchaseHistory.getTickerSymbol()
            + " bought for $"
            + String.format("%.2f", purchaseHistory.getCostBasisForPurchase())
            + " on " + purchaseHistory.getDateOfPurchase();
  }

  @Override
  public Date getDate() {

    Date date;
    while (true) {
      String dateAsString = getStringInput();
      checkForQuitting(dateAsString);

      try {
        date = DateUtility.convertStringFromUserToDate(dateAsString);
        if (date == null) {
          throw new IllegalArgumentException("Date is in wrong format.");
        }
        break;
      } catch (Exception e) {
        this.displayErrorMsg("Invalid date. Please enter date in"
                + " \"dd-MM-yyyy HH:mm:ss\" format");
      }
    }
    return date;
  }

  @Override
  public float getFloat() {

    float number;
    while (true) {
      String num = getStringInput();
      checkForQuitting(num);

      try {

        number = Float.parseFloat(num);
        break;
      } catch (Exception e) {

        this.displayErrorMsg("Enter correct numeric input");
      }
    }
    return number;
  }

  @Override
  public int getInt() {

    int number;
    while (true) {

      String num = getStringInput();
      checkForQuitting(num);

      try {

        number = Integer.parseInt(num);
        break;
      } catch (Exception e) {

        this.displayErrorMsg("Enter correct numeric input");
      }
    }
    return number;
  }

  private void checkForQuitting(String input) throws IllegalStateException {

    if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {

      throw new IllegalStateException("QUIT");
    }
  }

  @Override
  public void displayMenu() {

    String display = "\t\t<---Main Menu---->\n"
            + "Enter the following number to perform the respective actions\n"
            + "1. Create portfolio\n"
            + "2. Get all my portfolios\n"
            + "3. Get a specific portfolio\n"
            + "4. Buy stock\n"
            + "5. Get total cost basis\n"
            + "6. Get total value\n"
            + "7. Save portfolio to file\n"
            + "8. Display portfolio from file\n"
            + "9. Strategic investment plans\n"
            + "10. Save strategy to file\n"
            + "11. Display strategy from file\n"
            + "***Enter \"q\" or \"quit\" to exit the application***\n";
    this.displayInfo(display);
  }
}