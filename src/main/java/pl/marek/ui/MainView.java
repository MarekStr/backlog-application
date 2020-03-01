package pl.marek.ui;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import pl.marek.security.Logout;
import pl.marek.security.SecurityUtils;
import pl.marek.security.Settings;
import pl.marek.ui.views.DoneGrid;
import pl.marek.ui.views.InProgressGrid;
import pl.marek.ui.views.ToDoGrid;
import pl.marek.ui.views.UserStoriesGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route("")
@PageTitle("Backlog Application by MS")
@PWA(name = "Backlog Application",
        shortName = "Backlog App",
        description = "This is an example Backlog application.",
        enableInstallPrompt = true)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends AppLayout {


    private final Tabs menu;

    public MainView() {

        H1 welcomeText= new H1("Welcome in Backlog application");
        H2 instruction = new H2("Please use icons above to add another user story " +
                "and follow to next grid to add more details");
        VerticalLayout verticalLayout=new VerticalLayout(welcomeText,instruction);
        setContent(verticalLayout);
        menu = createMenuTabs();
        addToNavbar(menu);
    }

    private Tabs createMenuTabs() {

        final Tabs tabs=new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.add(getAvailableTabs());
        return tabs;

    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab(VaadinIcon.LIST_OL,"User Stories", UserStoriesGrid.class));
        tabs.add(createTab(VaadinIcon.CLIPBOARD_TEXT,"To Do",  ToDoGrid.class));
        tabs.add(createTab(VaadinIcon.CALENDAR_CLOCK,"In progress", InProgressGrid.class));
        tabs.add(createTab(VaadinIcon.RECORDS,"Done", DoneGrid.class));
        if (SecurityUtils.isAccessGranted(Settings.class)){
        tabs.add(createTab(VaadinIcon.COG_O,"Settings", Settings.class));}
        tabs.add(createTab(VaadinIcon.SIGN_OUT,"Logout", Logout.class));
        return tabs.toArray(new Tab[tabs.size()]);
    }

    private static Tab createTab(VaadinIcon icon,String title,
                                 Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass),icon, title));
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }
    private static <T extends HasComponents> T populateLink(T a, VaadinIcon icon, String title) {
        a.add(icon.create());
        a.add(title);
        return a;
    }
    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        selectTab();
    }
    private void selectTab() {
        String target = RouteConfiguration.forSessionScope()
                .getUrl(getContent().getClass());
        Optional<Component> tabToSelect = menu.getChildren().filter(tab -> {
            Component child = tab.getChildren().findFirst().get();
            return child instanceof RouterLink
                    && ((RouterLink) child).getHref().equals(target);
        }).findFirst();
        tabToSelect.ifPresent(tab -> menu.setSelectedTab((Tab) tab));
    }

}