package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;

import util.DateUtility;

/**
 * This class implements {@link IFileHandler} interface. It converts portfolios into json
 * representations by using GSon library serialisation techniques and saves them into a file. It
 * again uses Gson library deserialization to convert text from files to IPortfolio object
 * representations. The files are saved as .txt files in the path specified.
 *
 * <p>
 * The structure of the JSON is as follows:
 * </p>
 *
 * <p>
 * { "portfolioName" : "name of portfolio", "portfolioItems" : [ { "tickerSymbol" : "ticker symbol",
 * "quantity" : Integer value of quantity, "purchaseAmount" : float value of purchase amount,
 * "dateOfPurchase" : "DD-MM-YYYY HH:mm:ss" } ] }
 * </p>
 */
public class FileHandler implements IFileHandler {

  private static FileHandler instance;

  private final Gson jsonParser;

  private FileHandler() {

    GsonBuilder builder = setCustomSerializerAndDeserializer();
    jsonParser =
            builder.setPrettyPrinting().setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
  }

  /**
   * It creates a single instance of the class and returns it. This is a singleton implementation of
   * the file handler class.
   *
   * @return The instance of this class.
   */
  public static FileHandler getInstance() {

    if (instance == null) {

      instance = new FileHandler();
    }

    return instance;
  }

  private GsonBuilder setCustomSerializerAndDeserializer() {

    GsonBuilder builder = new GsonBuilder();
    addCustomPurchaseHistorySerializer(builder);
    addCustomPurchaseHistoryDeserializer(builder);
    return builder;
  }

  private void addCustomPurchaseHistorySerializer(GsonBuilder builder) {

    JsonSerializer<IPurchaseHistory> purchase = new JsonSerializer<IPurchaseHistory>() {

      @Override
      public JsonElement serialize(IPurchaseHistory purchaseHistory, Type type,
                                   JsonSerializationContext jsonSerializationContext) {

        JsonObject purchaseObj = new JsonObject();
        purchaseObj.addProperty("tickerSymbol", purchaseHistory.getTickerSymbol());
        purchaseObj.addProperty("quantity", purchaseHistory.getQuantity());
        purchaseObj.addProperty("purchaseAmount",
                purchaseHistory.getCostBasisForPurchase());

        Date dop = purchaseHistory.getDateOfPurchase();
        purchaseObj.addProperty("dateOfPurchase",
                DateUtility.convertDateToUserReadableString(dop));

        return purchaseObj;
      }
    };

    builder.registerTypeAdapter(IPurchaseHistory.class, purchase);
  }

  private void addCustomPurchaseHistoryDeserializer(GsonBuilder builder) {

    JsonDeserializer<IPurchaseHistory> deserializer = new JsonDeserializer<IPurchaseHistory>() {

      @Override
      public IPurchaseHistory deserialize(JsonElement jsonElement, Type type,
                                          JsonDeserializationContext
                                                  jsonDeserializationContext)
              throws JsonParseException {

        JsonObject purchaseJsonObject = jsonElement.getAsJsonObject();
        String dop = purchaseJsonObject.get("dateOfPurchase").getAsString();

        IPurchaseHistory purchaseHistory = new PurchaseHistoryCommission(
                purchaseJsonObject.get("tickerSymbol").getAsString(),
                purchaseJsonObject.get("quantity").getAsInt(),
                purchaseJsonObject.get("purchaseAmount").getAsFloat(),
                DateUtility.convertStringFromUserToDate(dop), 0);

        return purchaseHistory;
      }
    };

    builder.registerTypeAdapter(IPurchaseHistory.class, deserializer);
  }

  @Override
  public void savePortfolioToFile(IPortfolioInfo portfolio, String fileURL)
          throws IllegalStateException {

    String jsonString;

    try {

      Portfolio obj = new Portfolio(portfolio.getPortfolioName(), portfolio.getPortfolioItems());
      jsonString = jsonParser.toJson(obj);
    } catch (Exception e) {

      throw new IllegalStateException("error in creating json string.");
    }

    new Thread(new Runnable() {
      @Override
      public void run() {

        String filePath = fileURL;

        try {

          if (!filePath.endsWith(".txt")) {

            filePath = filePath + ".txt";
          }
          FileWriter file = new FileWriter(filePath);
          file.write(jsonString);
          file.flush();

        } catch (IOException e) {

          throw new IllegalStateException("Unable to save file");
        }
      }
    }).start();
  }

  @Override
  public PortfolioInfo getPortfolio(InputStream fileReader) throws IllegalStateException {

    Portfolio portfolio;
    try {

      portfolio = jsonParser.fromJson(new InputStreamReader(fileReader), Portfolio.class);
      fileReader.close();

    } catch (Exception e) {

      throw new IllegalStateException("Unable to read portfolio.");
    }
    return new PortfolioInfo(portfolio);
  }
}