package paint_gui;

import java.util.ArrayList;
import java.util.List;


public class History {
    Canvas canvas;
    public String labels[];
    private List<String> userSelection= new ArrayList<String>();

    public History(Canvas canvas) {
        this.canvas = canvas;
    }

    public void fillLabels() {
        List<String> tempCommands = new ArrayList<String>();
        tempCommands.clear(); // Clear the Jlist on open to ensure OLD / UNDOED drawings are removed
        tempCommands.addAll(canvas.getCommands());
        labels = tempCommands.toArray(new String[0]);
    }

    //displays a preview of what undo history would look
    //like if applied
    public void  displayPreview(int index){
        //clear final list for usage
        userSelection.clear();
        List<String> originalCommands= new ArrayList<String>();
        //calls the actual commands object in canvas
        List<String> commands =canvas.getCommands();
        // Backing up old commands
        originalCommands.addAll(commands);

        //remove all commands from the end till user selection
        //select(index);
        int i=commands.size()-1;
        while (i > index) {
            //remove commmand
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
        //for more user previewing
        commands.addAll(originalCommands);
    }

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
}
