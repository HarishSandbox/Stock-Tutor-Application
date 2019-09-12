Our design follows MVC architecture. 

1.The Model contains all the business logic of this tutoring application.
2.The interface IStockTutorModel has the operations that the user can use to train himself on stock
market.
3.In the second version of this application, features like adding commission during purchase and save
and retrieve portfolios are added. These are encapsulated in IStockTutorModelExtended interface which extends
IStockTutorModel interface.


************** DESIGN CHANGES AND ENHANCEMENTS FROM V2 to V3  ****************

In order to incorporate Investment strategy in our design, we made following changes from
previous version (V2).

--------MODEL--------

1. StockTutorStrategy : This class implements IStockTutorStrategy interface and extends IStockTutorExtended(from V2).
                        This class provides methods to support strategy persistence and a buyPartialStocksForStrategy()
                        which is used by the strategy to buy stocks to a portfolio.

2. StrategyFileHandler : This class implements IStrategyFileHandler which provides methods to serialize and deserialize
                        investment strategy object. It used GSON library. This is done to allow saving an retrieving of
                        strategies to an external file.

-------CONTROLLER-------

1. FeaturesExtended : This interface extends earlier Features interface to provide support for investment strategy.
                      The additional features includes creation of 3 types of investment strategies: EqualWeight strategy,
                      CustomWeight strategy and DollarCostAveraging strategy. It also provides methods applyStrategy()
                      and save and retrieve strategy.

2. StockTutorMainControllerExtended : This class extends previous StockTutorMainController and implements the FeaturesExtended
                      interface. It implements all of the methods of FeaturesExtended interface. This class creates different
                      types of strategy objects using the classes inside strategies package.

2. StockTutorCommandControllerExtended : This class extends previous StockTutorCommandController and overrides initializeCommands()
                      to include additional commands as part of strategy creation to command design pattern.

-------STRATEGIES-------
1. StockTutorInvestmentStrategy: This class implements from InvestmentStrategy and provides getters
                       to fetch information about an investment strategy. This class has an inner class
                       which uses builder design pattern inorder to help construct a strategy object.
2. StrategyStorage: This class acts as a repository of investment strategies by storing them as and
                    when they are created.

-------View-------

1. GraphicalViewExtended: This class extends GraphicalView class and implements IStockTutorViewExtended interface
                        and provides methods to display strategy. It also sets extended features' action listeners.
2. InvestmentStrategyPanel : This panel class is used to create and apply a strategy on a portfolio.
                        The panel supports all 3 types of investment strategies.
3. StrategySaveAndRetrievePanel : This panel class is used to save and retrieve strategies to an
                        external file.

-------MAIN-------
1. ProgramRunner: This class now creates view and model for extended interfaces and then the extended controller
                  calls setView() to start the application.


**********  DESIGN CHANGES AND ENHANCEMENTS FROM V1 to V2  *************

The IStockTutorModel now returns IPortfolioInfo object instead of Strings
(It follows adapter pattern). The output formatting is now done in the view layer.

The new model interface IStockTutorModelExtended has new methods for the extra features in version
2. This interface extends IStockTutorModel interface. The new model class StockTutorModelExtended
extends StockTutorModel and implements the IStockTutorModelExtended interface.

We have a new interface IFileHandler and a class implementing this interface FileHandler which
handles file save and open operations.

We have a new PurchaseHistoryCommission object which adds commission fee to purchase amount and cost
basis.

The new Features interface has methods for the view to perform callbacks. This interface is
implemented by StockTutorMainController which also implements IStockTutorController. This controller
 is used for the GUI view. For GUI we are following command callback design pattern.

For Console based view we are still following Command design pattern. The TUI uses
StockTutorCommandController class which uses StockTutorMainController as a composite object to call
the model methods. This ensures that the functionality of model is done similarly in both the
views.

We introduced GUI class which extends JFrame and uses swing library for GUI components. This class
implements IStockTutorView interface.

Controller:

The controller is the binding component between the view and the model.

1. In V1 we had a controller which implements CommandDesignPattern since we had a text based
interface.
2. With V2 we created a Features interface for any kind of view to use it to request controller for
a feature if required.
3. So a new controller class StockTutorMainController implements Features interface.
4. The command pattern controller StockTutorCommandController has StockTutorMainController object
which is used to delegate the requests to be made to the model. It follows composition pattern.

This ensures that there is a single controller which is talking to model irrespective of the view's
interface style.

Both the controllers implement IStockTutorController interface. The controller takes in a view and
model objects.

The GUI view in this application uses java swing as its graphical components. It implements
IStockTutorView.

The text based view implements ITextBasedView interface which extends IStockTutorView interface.


-----Model------

1. Stock : The stock class has the ticker symbol and a map of date and the price at that date and time.
		It is an entity class holding the details of a single stock. The Stock class represents this.

2. PortfolioInfo: This class implements an immutable portfolio interface which has only getter methods that provides information
           about the portfolio objects. This class is used by view layer to get information about the portfolio.
		
3. Portfolio : A portfolio of stocks is represented by Portfolio class and it implements IPortfolio interface which extends
        the immutable IPortfolioInfo interface. It provides operations to do some operations on a portfolio. A Portfolio has
        list of purchases made in it.
		
4. Purchase history: A purchase history is a class which holds the details of a purchase made for a portfolio. This class implements
			IPurchaseHistory class. Every object of this class is holds an entry of the purchase made.

5. StockAPI: This class gets the data from the network API. StockAPI implements IStockInputSource interface. It
			provides a method to fetch the stock the response API.
			
6. StockStorage: This class stores all the stocks from various input sources in it as a List<Stock>. Because, we only
				want a single source of storage, this class is made singleton by using lazy initialization. The class
				inherits from IStockStorage interface and implements addStock() and getStock() methods.
7. StockTutorModel: This class is the main model class which performs/delegates operations to other classes within the model
					package. The class inherits from IStockTutorModel which provides all the methods/ operations that the
					user can perform our tutor application. This is the only class in model which is used by controller
					to perform operations.
8. PortfolioStorage: This class represents the storage of portfolios that are created by the user. It implements IPortfolioStorage
					interface.
9. FileHandler: This class is used to save and retrieve portfolios from files. The class uses Gson library to serialize and
                deserialize data. The class inherits from IFileHandler interface.
10. PurchaseHistoryCommission: This class extends purchaseHistory class and provides a constructor that takes an additional
                argument - commission fees.
11. StockTutorModelExtended: This class is implements IStockTutorModelExtended interface which provides support for additional
                functionality of including commission fees, save and retrieving portfolios from file.


- New Design -
8. PurchaseHistoryCommission: This class extends PurchaseHistory class and provides support for
                additional functionality - adding commission fees for every purchase made.

9. StockTutorModelExtended - This class extends IStockTutorModelCommission interface which is an
                interface that provides support for additional functionality of including commission fees
                and saving and retrieving of files. This interface is an extension of IStockTutorModel interface
                with following additional methods:
                - buyStockWithCommission() which now takes commission.
                - savePortfolioToFile() - to save a portfolio to a file.
                - getPortfolioFromFile() - to retrieve a portfolio from a file.


10. PortfolioInfo - This class extends IPortfolioInfo interface which has non mutating methods for a portfolio.
                This is done in order to prevent view from mutating an the portfolio object it uses
                from model component.
                This interface is then extended by a mutable interface IPortfolio which provides a method to
                add a purchase item.
					
------CONTROLLER----

1. StockTutorController: This class is the main controller class which implements
   IStockTutorController and Features interface. It provides one method setView() which starts the
   application. This class follows call back design pattern.

2. Commands package: Has all the commands as classes which performs various operations. All these
    command classes inherit from ITutorCommand interface which provides a single method
    performCommand(Features feature) which performs the user operation by calling the methods from
    Features interface.

--------VIEW--------

1. GraphicalView: This class represents GUI view and implements IStockTutorView interface.

2. TextBasedView:This class deals with displaying output using appendable object and reading input using readable object.
                  			The class implements IStockTutorView which provides methods to support reading of user input and displaying
                  			the output.
			
-----MAIN METHOD----

1. ProgramRunner: This is the starting point of our application where the main() method resides. This method creates an	
		object of model and view and passes it to the controller's object. When the args "gui" are
		passed to it it creates a Graphical Interface based view. For any other kind og arguments it
		 creates a text based view.It then starts the application by calling
		 controllerObj.setView().


  
