package view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.FeaturesExtended;
import strategies.InvestmentStrategy;
import util.DateUtility;

/**
 * This represents the latest GUI based view for the application. It implements {@code
 * IStockTutorViewExtended} interface which has all the methods that are required by the view to
 * perform. It also extends GraphicalView class.
 */
public class GraphicalViewExtended extends GraphicalView implements IStockTutorViewExtended {

  @Override
  public void setFeaturesExtended(FeaturesExtended feature) {

    super.setFeatures(feature);
    setStrategyFileOperationsActions(feature);
    setApplyStrategyActions(feature);
  }

  @Override
  public void displayStrategy(InvestmentStrategy strategy) {

    tabs.getStrategyFileOperationsPanel().displayStrategyInfo(strategy);
  }

  private void setStrategyFileOperationsActions(FeaturesExtended feature) {

    tabs.getStrategyFileOperationsPanel().setActionListenerForGetStrategyFromFile(l -> {
      final JFileChooser fileChooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "Investment Strategy files", "txt");
      fileChooser.setFileFilter(filter);
      int returnState = fileChooser.showOpenDialog(this);
      if (returnState == JFileChooser.APPROVE_OPTION) {
        File f = fileChooser.getSelectedFile();
        String filePath = f.getAbsolutePath();
        feature.getStrategy(filePath);
      }
    });

    tabs.getStrategyFileOperationsPanel().setActionListenerForSaveStrategyToFile(l -> {

      if (tabs.getStrategyFileOperationsPanel().validateData()) {
        final JFileChooser fchooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Investment strategy files", "txt");
        fchooser.setFileFilter(filter);
        int retvalue = fchooser.showSaveDialog(this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          String filePath = f.getAbsolutePath();

          feature.saveStrategy(tabs.getStrategyFileOperationsPanel().getTextField().getText(),
                  filePath);
        }
      }
    });
  }

  private void setApplyStrategyActions(FeaturesExtended feature) {

    // Listener for apply strategy
    tabs.getStrategyPanel().setActionListener(l -> {
      if (tabs.getStrategyPanel().validateDataEntry()) {
        if (!this.createStrategyBasedOnType(feature)) {
          return;
        }
        this.applyStrategy(feature);
        tabs.getStrategyPanel().resetData();
      }
    });

  }

  private boolean createStrategyBasedOnType(FeaturesExtended feature) {

    float commissionFees;
    if (tabs.getStrategyPanel().getCommissionTextField().getText().equals("")) {
      commissionFees = 0;
    } else {
      commissionFees = Float.parseFloat(tabs.getStrategyPanel().getCommissionTextField()
              .getText());
    }
    switch (tabs.getStrategyPanel().getStrategyTypeComboBox().getSelectedItem().toString()) {

      case "Dollar Cost Averaging":
        Map<String, Float> dollarCostStocks = convertMapOfJTextFieldToStocks(
                tabs.getStrategyPanel().getDollarCostMap());

        return feature.createDollarCostStrategy(tabs.getStrategyPanel().getStrategyNameTextField()
                        .getText(),
                Float.parseFloat(tabs.getStrategyPanel().getAmountTextField().getText()),
                Integer.parseInt(tabs.getStrategyPanel().getFrequencyTextField().getText()),
                DateUtility.convertStringFromUserToDate(tabs.getStrategyPanel()
                        .getStartDateTextField().getText().concat(" 12:00:00")),
                DateUtility.convertStringFromUserToDate(tabs.getStrategyPanel()
                        .getEndDateTextField().getText().concat(" 12:00:00"))
                , dollarCostStocks, commissionFees);
      case "Equal Weights NonRecurring":
        List<String> equalWeightsStock = convertListOfJTextFieldToStocks(
                tabs.getStrategyPanel().getEqualWeightsList());

        return feature.createEqualWeightStrategy(tabs.getStrategyPanel()
                        .getStrategyNameTextField().getText()
                , Float.parseFloat(tabs.getStrategyPanel().getAmountTextField().getText())
                , DateUtility.convertStringFromUserToDate(tabs.getStrategyPanel()
                        .getStartDateTextField().getText().concat(" 12:00:00"))
                , equalWeightsStock, commissionFees);
      case "Custom Weights NonRecurring":
        Map<String, Float> customWeightStocks = convertMapOfJTextFieldToStocks(
                tabs.getStrategyPanel().getCustomWeightMap());

        return feature.createCustomWeightStrategy(tabs.getStrategyPanel()
                        .getStrategyNameTextField().getText()
                , Float.parseFloat(tabs.getStrategyPanel().getAmountTextField().getText())
                , DateUtility.convertStringFromUserToDate(tabs.getStrategyPanel()
                        .getStartDateTextField().getText().concat(" 12:00:00"))
                , customWeightStocks, commissionFees);
      default:
        return false;
    }
  }

  private Map<String, Float> convertMapOfJTextFieldToStocks(Map<JTextField, JTextField> other) {
    Map<String, Float> resultStocks = new HashMap<>();
    for (Map.Entry<JTextField, JTextField> entry : other.entrySet()) {
      resultStocks.put(entry.getKey().getText(), Float.parseFloat(entry.getValue().getText()));
    }
    return resultStocks;
  }

  private List<String> convertListOfJTextFieldToStocks(List<JTextField> other) {
    List<String> resultStock = new ArrayList<>();
    for (JTextField field : other) {
      resultStock.add(field.getText());
    }
    return resultStock;
  }

  private void applyStrategy(FeaturesExtended feature) {
    feature.applyStrategy(tabs.getStrategyPanel().getPortfolioNameTextField().getText(),
            tabs.getStrategyPanel().getStrategyNameTextField().getText());
  }
}