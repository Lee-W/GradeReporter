import org.jsoup.Connection.Response;

public interface GradePageAuthenticator {
    Response getLoggedInResponse();
    void login();
    void logout();
}
