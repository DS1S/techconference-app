package backend.systems;

import frontend.MenuUI;
import utility.RunnableSystem;
import utility.inputprocessors.IndexProcessor;
import utility.inputprocessors.OptionIndexProcessor;

import java.util.Scanner;

/**
 * Represents an abstract Subsystem.
 */
public abstract class MenuSystem implements RunnableSystem {
    private int numOptions;
    private IndexProcessor<Integer> indexProcessor;
    protected Scanner input = new Scanner(System.in);

    /**
     * Constructs a subsystem with a number of options and an IndexProcessor to handle them.
     * @param numOptions the number of options accepted by this system.
     *
     */
    public MenuSystem(int numOptions) {
        this.numOptions = numOptions;
        this.indexProcessor = new OptionIndexProcessor(input, numOptions);
    }

    public MenuSystem(){
        this.numOptions = 1;
        this.indexProcessor = new OptionIndexProcessor(input, numOptions);
    }

    protected void changeNumOptions(int numOptions){
        this.numOptions = numOptions;
        this.indexProcessor = new OptionIndexProcessor(input, numOptions);
    }

    /**
     * Runs a subsystem by asking for the required number of options and processing them.
     */
    @Override
    public void run() {
        int option;
        do{
            displayOptions();
            option = indexProcessor.processInput();
            processInput(option);
        }while(option != numOptions);
    }

    /**
     * Displays the options for the subsystem.
     */
    protected abstract void displayOptions();

    /**
     * Processes an integer input in the subsystem.
     * @param index The input to be processed.
     */
    protected abstract void processInput(int index);

    /**
     * Asks the user for an input and checks if the input is valid.
     * @param attribute The attribute the user is asked for.
     * @return Whatever the user inputted.
     */
    protected String askForString(String attribute) {
        MenuUI errorUI = new MenuUI();
        String string = "";
        while (string.isEmpty()) {
            string = input.nextLine();
            if (string.isEmpty()) errorUI.displayError(attribute + " is invalid, please input a " + attribute + "!");
        }
        return string;
    }
}