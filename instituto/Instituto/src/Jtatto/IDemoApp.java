/*
 * Copyright 2002 and later by MH Software-Entwicklung. All rights reserved.
 * Use is subject to license terms.
 */

package jtatto;

import javax.swing.JTabbedPane;

/**
 *
 * @author Michael Hagen
 */
public interface IDemoApp {

    public GUIProperties getGuiProps();
    public void setMainTabbedPane(JTabbedPane tabPane);
    public JTabbedPane getMainTabbedPane();
    public void updateLookAndFeel(String lf);
    public void updateTheme(String theme);
    public void setLookAndFeelFlag(String propName, boolean flag);
    public void performExit();

}
