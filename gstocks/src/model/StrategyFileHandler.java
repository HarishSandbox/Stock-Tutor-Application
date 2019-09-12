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

import strategies.InvestmentStrategy;
import strategies.StockTutorInvestmentStrategy;

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
 * { "strategyName" : "name of strategy", "startDate": "Day, Month Date, YEAR HOUR:MINUTE:SECOND
 * AM/PM TIMEZONE", "frequency": frequency of investment in days, "price": price to invest each
 * time, "commissionFee": commission fee of investment, "stocks": { "Stock ticker symbol": stock
 * weightage for a total of 100 } }
 * </p>
 */
public class StrategyFileHandler implements IStrategyFileHandler {

  private static StrategyFileHandler instance;

  private final Gson jsonParser;

  private StrategyFileHandler() {

    GsonBuilder builder = setCustomSerializerAndDeserializer();
    jsonParser =
            builder.setPrettyPrinting().setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
  }

  /**
   * It creates a single instance of the class and returns it. This is a singleton implementation of
   * the strategy file handler class.
   *
   * @return The instance of this class.
   */
  public static StrategyFileHandler getInstance() {

    if (instance == null) {

      instance = new StrategyFileHandler();
    }

    return instance;
  }

  private GsonBuilder setCustomSerializerAndDeserializer() {

    GsonBuilder builder = new GsonBuilder();
    addCustomInvestmentStrategySerializer(builder);
    addCustomInvestmentStrategyDeserializer(builder);
    return builder;
  }

  private void addCustomInvestmentStrategySerializer(GsonBuilder builder) {

    JsonSerializer<InvestmentStrategy> purchase = new
            JsonSerializer<InvestmentStrategy>() {

      @Override
      public JsonElement serialize(InvestmentStrategy strategy, Type type,
                                   JsonSerializationContext jsonSerializationContext) {

        JsonObject strategyObj = new JsonObject();

        return strategyObj;
      }
    };

    builder.registerTypeAdapter(IPurchaseHistory.class, purchase);
  }

  private void addCustomInvestmentStrategyDeserializer(GsonBuilder builder) {

    JsonDeserializer<InvestmentStrategy> deserializer = new
            JsonDeserializer<InvestmentStrategy>() {

      @Override
      public InvestmentStrategy deserialize(JsonElement jsonElement, Type type,
                                            JsonDeserializationContext
                                                    jsonDeserializationContext)
              throws JsonParseException {

        JsonObject strategyJsonObj = jsonElement.getAsJsonObject();
        return null;
      }
    };

    builder.registerTypeAdapter(IPurchaseHistory.class, deserializer);
  }

  @Override
  public void saveStrategyToFile(InvestmentStrategy strategy, String fileURL)
          throws IllegalStateException {

    String jsonString;

    try {

      jsonString = jsonParser.toJson(strategy);
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
  public InvestmentStrategy getStrategy(InputStream fileReader) throws IllegalStateException {

    InvestmentStrategy strategy;
    try {

      strategy = jsonParser.fromJson(new InputStreamReader(fileReader),
              StockTutorInvestmentStrategy.class);
      fileReader.close();

    } catch (Exception e) {

      throw new IllegalStateException("Unable to read strategy.");
    }
    return strategy;
  }
}