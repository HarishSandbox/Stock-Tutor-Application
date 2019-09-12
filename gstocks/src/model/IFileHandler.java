package model;

import java.io.InputStream;

/**
 * Performs file writing and reading operations for specified objects. It converts objects to their
 * json representations and saves them into a text based file. Each given object is saved into a
 * different file.
 */
public interface IFileHandler {

  /**
   * Creates a file and saves a given {@code PortfolioInfo} object to a given file path. If there is
   * an existing file of the given name it will be rewritten if permitted. If the user is unable to
   * perform file writing operations it throws an exception. The filename is the name of the
   * portfolio and extension is .txt.
   *
   * @param fileURL   A String value of the absolute path where the file has to be saved.
   * @param portfolio A {@code PortfolioInfo} object which has to be saved t the file.
   * @throws IllegalStateException thrown when writing operations could not be performed or object
   *                               cannot be written.
   */
  void savePortfolioToFile(IPortfolioInfo portfolio, String fileURL) throws IllegalStateException;

  /**
   * Retrieves a file from a given file path and converts the info into an PortfolioInfo. It throws
   * an exception if the file could not be read or dose not exists or the portfolio object could not
   * be created.
   *
   * @param fileReader A InputStream object of the absolute path from where the file has to be
   *                   retrieved.
   * @return An {@code PortfolioInfo} object that is created from the data of the file.
   * @throws IllegalStateException thrown if the file could not be read or dose not exists or the
   *                               portfolio object could not be created.
   */
  IPortfolioInfo getPortfolio(InputStream fileReader) throws IllegalStateException;
}
