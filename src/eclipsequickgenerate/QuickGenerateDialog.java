package eclipsequickgenerate;

import java.util.Comparator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

public class QuickGenerateDialog extends FilteredItemsSelectionDialog {

    private static final String DIALOG_SETTINGS = "QuickGenerateDialogSettings";
    private static final IDialogSettings pluginSettings = new DialogSettings(DIALOG_SETTINGS);

    private static final String[][] ACTIONS = {
        {"Generate Getters and Setters",     "getters"},
        {"Generate Constructor",             "constructor"},
        {"Generate toString()",              "tostring"},
        {"Generate hashCode() and equals()", "hashcode"},
        {"Generate Delegate Methods",        "delegate"},
        {"Override/Implement Methods",       "override"},
        {"Organize Imports",                 "imports"},
        {"Format Document",                  "format"},
        {"Generate JavaDoc",                 "javadoc"},
    };

    public QuickGenerateDialog(Shell shell) {
        super(shell, false);
        setTitle("Quick Generate");
        setMessage("Digite para filtrar ações:");
        setInitialPattern("**");
        setListLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                if (element instanceof String[] action) {
                    return action[0];
                }
                return "";
            }
        });
        setDetailsLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                if (element instanceof String[] action) {
                    return action[0];
                }
                return "";
            }
        });
    }

    @Override
    protected Control createExtendedContentArea(Composite parent) {
        return null;
    }

    @Override
    protected IDialogSettings getDialogSettings() {
        IDialogSettings section = pluginSettings.getSection(DIALOG_SETTINGS);
        if (section == null) {
            section = pluginSettings.addNewSection(DIALOG_SETTINGS);
        }
        return section;
    }

    @Override
    protected IStatus validateItem(Object item) {
        return Status.OK_STATUS;
    }

    @Override
    protected ItemsFilter createFilter() {
        return new ItemsFilter() {
            @Override
            public boolean matchItem(Object item) {
                if (item instanceof String[] action) {
                    return matches(action[0]);
                }
                return false;
            }

            @Override
            public boolean isConsistentItem(Object item) {
                return true;
            }
        };
    }

    @Override
    protected Comparator<Object> getItemsComparator() {
        return (a, b) -> {
            if (a instanceof String[] aa && b instanceof String[] bb) {
                return aa[0].compareToIgnoreCase(bb[0]);
            }
            return 0;
        };
    }

    @Override
    protected void fillContentProvider(AbstractContentProvider provider,
            ItemsFilter filter, IProgressMonitor monitor) throws CoreException {
        monitor.beginTask("Carregando...", ACTIONS.length);
        for (String[] action : ACTIONS) {
            provider.add(action, filter);
            monitor.worked(1);
        }
        monitor.done();
    }

    @Override
    public String getElementName(Object item) {
        if (item instanceof String[] action) {
            return action[0];
        }
        return "";
    }

    @Override
    protected void handleSelected(StructuredSelection selection) {
        super.handleSelected(selection);
    }

    public String getSelectedActionKey() {
        Object[] result = getResult();
        if (result != null && result.length > 0 && result[0] instanceof String[] action) {
            return action[1];
        }
        return null;
    }

    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setSize(500, 400);
    }
}