package controller.commands;

/**
 * This interface has the methods that a command class would perform. It takes the model which has
 * to be delegated the appropriate actions to be performed.
 */
public interface ITutorCommand {

  /**
   * This method has to be called for the object to perform the appropriate command given by the
   * user.
   *
   * @throws IllegalArgumentException thrown when any of the given actions result in quitting the
   *                                  application.
   */
  void performCommand() throws IllegalArgumentException;
}