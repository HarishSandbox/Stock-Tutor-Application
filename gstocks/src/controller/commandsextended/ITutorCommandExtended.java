package controller.commandsextended;

import controller.FeaturesExtended;
import controller.commands.ITutorCommand;

/**
 * This interface has the methods that a command class would perform. It takes the model which has
 * to be delegated the appropriate actions to be performed.
 */
public interface ITutorCommandExtended extends ITutorCommand {

  /**
   * This method has to be called for the object to perform the appropriate command given by the
   * user.
   *
   * @param feature the model object which will be performing the required operations.
   * @throws IllegalArgumentException thrown when any of the given actions result in quitting the
   *                                  application.
   */
  void performCommand(FeaturesExtended feature) throws IllegalArgumentException;
}