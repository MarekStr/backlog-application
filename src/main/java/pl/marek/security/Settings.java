package pl.marek.security;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import pl.marek.ui.MainView;

@Route(value = "Settings", layout = MainView.class)
@Secured("ROLE_Admin")
public class Settings extends VerticalLayout {


    public Settings() {

        H1 welcomeText = new H1("Settings");
        H2 instruction = new H2("It looks like you are an admin all setting will be here soon");
        add(welcomeText,instruction);
    }
}