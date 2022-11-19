package ca.bcit.comp2522.termproject.secretwonders;

import javafx.stage.Window;

/**
 * Represents a game window.
 * @author Rhys Mahannah
 * @version 19 November 2022
 */
public class InstructionsWindow extends Window {

    private final int    windowHeight;
    private final int    windowWidth;
    private final String windowName;
    private final String controllerFXMLFileName;

    /**
     * Creates an application window.
     *
     * @param windowHeight the height of the window (int).
     * @param windowWidth the width of the window (int).
     * @param windowName the name of the window (String).
     * @param controllerFXMLFileName the name of the FXML controller file (String).
     */
    InstructionsWindow(final int windowHeight, final int windowWidth,
                       final String windowName, final String controllerFXMLFileName) {
            this.windowHeight = windowHeight;
            this.windowWidth = windowWidth;
            this.windowName = windowName;
            this.controllerFXMLFileName = "/" + controllerFXMLFileName;
    }
}
