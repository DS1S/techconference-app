package frontend;

import java.util.List;

/**
 * A class that displays prompts and messages pertaining to a menu with various options.
 */
public class MenuUI {

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          Options                                                //
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Displays a message prompting the user to select an option.
     */
    public void displayIndexPrompt() { System.out.print("Enter the number of the option you wish to select: "); }

    /**
     * Displays a message informing the user that they have entered an invalid number.
     */
    public void displayInvalidIndex() { System.out.println("You have entered an invalid number."); }

    /**
     * Displays main menu options to the user.
     * @param optionNames the options of the menu to be displayed.
     * @param displayReturnPrompt
     * @param displayExit Whether the exit option will be displayed in this list of options.
     */
    public void displayOptions(List<String> optionNames, boolean displayReturnPrompt, boolean displayExit) {
        if (displayReturnPrompt) { System.out.println("\nPlease choose an option."); }

        for(int i = 0; i < optionNames.size(); i++){
            System.out.println((i + 1) + ". " + optionNames.get(i));
        }

        if (displayExit) { System.out.println(optionNames.size() + 1 + ": Return/Exit."); }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                          Errors                                                 //
    /////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Displays an error to the user.
     * @param s The error that is displayed.
     */
    public void displayError(String s) {
        System.out.println(s);
    }

}
