package paint_gui;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the undo history functionality.
 */
public class History {
    private Canvas canvas;
    private String[] labels;
    private List<String> userSelection= new ArrayList<String>();

    /**
     * Creates a <code>History</code> object to display and manipulate the command history on the specified canvas.
     * @param canvas The canvas from which to display and remove the history of commands to the user's liking
     */
    public History(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Fills up the <code>labels</code> array with the commands stored in Canvas.
     */
    public void fillLabels() {
        List<String> tempCommands = new ArrayList<String>();
        tempCommands.clear(); // Clear the Jlist on open to ensure OLD / UNDOED drawings are removed
        tempCommands.addAll(canvas.getCommands());
        labels = tempCommands.toArray(new String[0]);
    }

    /**
     * Displays a preview of what the canvas would look like at the given point in time that the user has selected.
     * @param index The index of the last command to display on the canvas.
     *              Is always smaller than the last index of the commands list.
     */
    public void  displayPreview(int index){
        //Clear final list for usage
        userSelection.clear();
        //Call the actual commands object in canvas
        List<String> commands = canvas.getCommands();
        //Back up old commands
        List<String> originalCommands = new ArrayList<String>(commands);

        //Remove all commands from the end till user selection
        int i = commands.size()-1;
        while (i > index) {
            //Remove commmand
            commands.remove(i);
            i--;
        }

        //save the list in case user wants this
        userSelection.addAll(commands);
        canvas.clean();
        canvas.readCommands();
        canvas.repaint();

        //clear commands
        commands.clear();
        //put back in the original list
        //for the ability to continue previewing
        commands.addAll(originalCommands);
    }

    /**
     * Repaints the canvas with the changes the user might have selected to make.
     */
    public void windowCloseAction() {
        System.out.println("History closed, applying changes...");
        List<String> commands =canvas.getCommands();
        //clear the old commands list
        commands.clear();
        //update with the users final decision
        commands.addAll(userSelection);
        canvas.clean();
        canvas.readCommands();
        canvas.repaint();
    }

    /**
     * Gets the <code>labels</code> array.
     * @return The list of labels to display in the history window.
     */
    public String[] getLabels() {
        return labels;
    }
}
