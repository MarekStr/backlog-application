package pl.marek.ui.views;

import pl.marek.security.CustomRequestCache;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@Tag("sa-login-view")
@Route(value = LoginView.ROUTE)
@PageTitle("Login")
public class LoginView extends VerticalLayout {
    public static final String ROUTE = "login";

    private LoginOverlay login = new LoginOverlay();

    @Autowired
    public LoginView(AuthenticationManager authenticationManager,

                     CustomRequestCache requestCache) {
        // configures login dialog and adds it to the main view
        login.setOpened(true);
        login.setTitle("Backlog Manager");
        login.setDescription("Please login with your username and password or ask your manager how to get access to this application.");

        add(login);

        login.addLoginListener(e -> {

            try {
                // try to authenticate with given credentials, should always return not null or throw an {@link AuthenticationException}
                final Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(e.getUsername(), e.getPassword())); //

                // if authentication was successful we will update the security context and redirect to the page requested first
                SecurityContextHolder.getContext().setAuthentication(authentication); //

                login.close(); //

                UI.getCurrent().navigate(requestCache.resolveRedirectUrl()); //

            } catch (AuthenticationException ex) {

                // show default error message
                // Note: You should not expose any detailed information here like "username is known but password is wrong"
                // as it weakens security.
                login.setError(true);
            }
        });
    }
}