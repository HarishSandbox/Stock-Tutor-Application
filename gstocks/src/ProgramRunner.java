import java.io.InputStreamReader;

import controller.IStockTutorController;
import controller.StockTutorCommandControllerExtended;
import controller.StockTutorMainControllerExtended;
import model.IStockTutorStrategy;
import model.StockTutorStrategy;
import view.GraphicalViewExtended;
import view.IStockTutorViewExtended;
import view.ITextBasedView;
import view.TextBasedView;

/**
 * This class is the starting point of stock tutor application. The class creates objects of model,
 * controller and view and lets controller communicate between model and the view.
 */
public class ProgramRunner {

  /**
   * This method binds the view , controller and model components of the program and starts the
   * application.
   *
   * @param args command line arguments. When "gui" argument is passed in the arguments it runs a
   *             GUI based application. For any other kind of input it runs a text based
   *             application.
   */
  public static void main(String[] args) {

    String arg = args != null && args.length > 0 ? args[0] : "";
    IStockTutorStrategy model = new StockTutorStrategy();
    IStockTutorController obj;

    if (arg.equals("gui")) {

      IStockTutorViewExtended guiView = new GraphicalViewExtended();
      obj = new StockTutorMainControllerExtended(model, guiView);
    } else {

      ITextBasedView view = new TextBasedView(new InputStreamReader(System.in), System.out);
      obj = new StockTutorCommandControllerExtended(model, view);
    }

    obj.setView();
  }
}