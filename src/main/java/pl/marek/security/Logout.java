package pl.marek.security;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import pl.marek.ui.MainView;

@Route(value = "Logout", layout = MainView.class)
public class Logout extends VerticalLayout {

    private Anchor logoutYes = new Anchor("/logout", " Yes ");
    private Anchor logoutNo = new Anchor("", " No ");
    private Dialog logoutDialog = new Dialog();

    public Logout() {

        HorizontalLayout yesNoOptions = new HorizontalLayout(logoutYes, logoutNo);
        yesNoOptions.setPadding(true);
        yesNoOptions.setJustifyContentMode(JustifyContentMode.EVENLY);
        // ustawianie Dialog zeby mozna go bylo tylko zamknac wybierajac opcje yes/no z okienka oraz jego rozmiar
        logoutDialog.setCloseOnEsc(false);
        logoutDialog.setCloseOnOutsideClick(false);
        logoutDialog.setWidth("170px");
        logoutDialog.setHeight("80px");

        logoutDialog.open();

        logoutDialog.add(new Label(" Logout? Are you sure ? "), yesNoOptions );

    }
}