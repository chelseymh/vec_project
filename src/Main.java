import paint_gui.Gui;

/**
 * Starts up the application by creating an instance of Gui and calling its <code>createGUI</code> method.
 */
public class Main {
    /**
     * The standard method signature for the JVM to run the program.
     * @param args List of possible arguments. Is always empty in our program.
     */
    public static void main(String[] args) {
        new Gui().createGUI();
    }
}
