import org.jsoup.Connection.Response;

public interface GradeComponentFactory {
    GradePageAuthenticator createGradePageAuthenticator(String studentID, String password);
    GradeCrawler createGradeCrawler(Response loggedInResponse);
    GradeParser createGradeParser(Response loggedInResponse);
}
