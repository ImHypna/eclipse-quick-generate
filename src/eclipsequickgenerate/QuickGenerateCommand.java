package eclipsequickgenerate;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.handlers.HandlerUtil;

public class QuickGenerateCommand extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        Shell shell = Display.getDefault().getActiveShell();

        if (shell == null) {
            shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
        }

        QuickGenerateDialog dialog = new QuickGenerateDialog(shell);

        if (dialog.open() == Window.OK) {
            String action = dialog.getSelectedActionKey();
            if (action == null) return null;

            String commandId = null;

            if (action.equals("getters"))     commandId = "org.eclipse.jdt.ui.actions.GenerateGetterSetter";
            if (action.equals("constructor")) commandId = "org.eclipse.jdt.ui.actions.GenerateConstructorUsingFields";
            if (action.equals("tostring"))    commandId = "org.eclipse.jdt.ui.actions.GenerateToString";
            if (action.equals("hashcode"))    commandId = "org.eclipse.jdt.ui.actions.GenerateHashCodeEquals";
            if (action.equals("delegate"))    commandId = "org.eclipse.jdt.ui.actions.GenerateDelegateMethods";
            if (action.equals("override"))    commandId = "org.eclipse.jdt.ui.actions.OverrideMethods";
            if (action.equals("imports"))     commandId = "org.eclipse.jdt.ui.actions.OrganizeImports";
            if (action.equals("format"))      commandId = "org.eclipse.ui.edit.text.formatAll";
            if (action.equals("javadoc"))     commandId = "org.eclipse.jdt.ui.edit.text.java.create.javadoc.comment";

            if (commandId != null) {
                runCommand(commandId, event);
            }
        }

        return null;
    }

    private void runCommand(String commandId, ExecutionEvent event) {
        try {
            IWorkbenchPart part = HandlerUtil.getActivePart(event);
            if (part == null) {
                System.out.println("Nenhuma parte ativa encontrada");
                return;
            }

            IWorkbenchPartSite site = part.getSite();
            IActionBars bars = ((org.eclipse.ui.IEditorSite) site).getActionBars();
            IAction action = bars.getGlobalActionHandler(commandId);

            if (action != null && action.isEnabled()) {
                System.out.println("Executando action: " + commandId);
                action.run();
            } else {
                System.out.println("Fallback handler service: " + commandId);
                org.eclipse.ui.handlers.IHandlerService handlerService =
                    HandlerUtil.getActiveWorkbenchWindow(event)
                        .getService(org.eclipse.ui.handlers.IHandlerService.class);
                handlerService.executeCommand(commandId, null);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}