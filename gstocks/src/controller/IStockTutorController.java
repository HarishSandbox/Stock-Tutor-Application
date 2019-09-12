package controller;

/**
 * This interface represents the controller component of the tutoring application. It has operations
 * to start using the application.
 */
public interface IStockTutorController {

  /**
   * This method should be called to initiate the usage of the application. It should show
   * appropriate message or components required for a user who just opened the application and
   * provide appropriate tools/commands to proceed and use the application.
   */
  void setView();
}
