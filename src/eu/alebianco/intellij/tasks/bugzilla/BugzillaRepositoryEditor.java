package eu.alebianco.intellij.tasks.bugzilla;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.tasks.config.BaseRepositoryEditor;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.Consumer;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Project: bugzilla-intellij-task-provider
 * <p/>
 * Author:  Alessandro Bianco
 * Website: http://alessandrobianco.eu
 * Twitter: @alebianco
 * Created: 17/02/2014 16:33
 * <p/>
 * Copyright © 2013 Alessandro Bianco
 */
public class BugzillaRepositoryEditor extends BaseRepositoryEditor<BugzillaRepository> {

    private ComboBox myProductCombobox;
    private JBLabel myComponentLabel;
    private ComboBox myComponentCombobox;
    private JBLabel myProductLabel;
    private JTextField myOtherUsersText;
    private JBLabel myOtherUsersLabel;
    private JBCheckBox myIncludeClosedCheck;
    private JBLabel myIncludeClosedLabel;

    public BugzillaRepositoryEditor(Project project, BugzillaRepository repository, Consumer<BugzillaRepository> consumer) {
        super(project, repository, consumer);
    }

    @Nullable
    @Override
    protected JComponent createCustomPanel() {
        myProductCombobox = new ComboBox(300);
        myProductLabel = new JBLabel("Product:", SwingConstants.RIGHT);
        myProductLabel.setLabelFor(myProductCombobox);

        myComponentCombobox = new ComboBox(300);
        myComponentLabel = new JBLabel("Component:", SwingConstants.RIGHT);
        myComponentLabel.setLabelFor(myComponentCombobox);

        myOtherUsersText = new JTextField("");
        myOtherUsersLabel = new JBLabel("Users:", SwingConstants.RIGHT);
        myOtherUsersLabel.setLabelFor(myOtherUsersText);

        myIncludeClosedCheck = new JBCheckBox();
        myIncludeClosedLabel = new JBLabel("Include closed:", SwingConstants.RIGHT);
        myIncludeClosedLabel.setLabelFor(myIncludeClosedCheck);

        return FormBuilder.createFormBuilder()
                .addLabeledComponent(myProductLabel, myProductCombobox)
                .addLabeledComponent(myComponentLabel, myComponentCombobox)
                .addLabeledComponent(myOtherUsersLabel, myOtherUsersText)
                .addLabeledComponent(myIncludeClosedLabel, myIncludeClosedCheck)
                .getPanel();
    }

    @Override
    public void apply() {
        super.apply();
        // TODO update changes into repository
    }

    @Override
    protected void afterTestConnection(boolean connectionSuccessful) {
        super.afterTestConnection(connectionSuccessful);
        if (connectionSuccessful) {
            // TODO update info in filtering tab
        }
    }
}
