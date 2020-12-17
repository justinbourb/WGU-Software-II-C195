package Helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/** This class shows a confirm button in the GUI.*/
public class confirmView {
  
  /** This function shows a confirm button in the GUI.
  *@param confirmText, a string containing confirmation text to display
  *@return true if ok button is selected, false if not
  */
  public static boolean showAlert(String confirmText) {
    /*
    Purpose: show a confirmation dialog and return true if ok is selected
    Input: confirmText, the text you wish to display
    Output: none
    Return: true if ok is selected
    */
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, confirmText);
    Optional<ButtonType> result = alert.showAndWait();
    //wait for the result (user clicks a button) and return true if ok is pressed
    /* old style
     if (result.isPresent()){
        return (result.get() == ButtonType.OK);
      }
    //user clicked false
    return false;
     */
    //functional programming is more efficient
    return result.filter(buttonType -> (buttonType == ButtonType.OK)).isPresent();
  }
}
