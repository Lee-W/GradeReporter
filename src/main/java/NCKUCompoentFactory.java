import org.jsoup.Connection.Response;

public class NCKUCompoentFactory implements GradeComponentFactory {
    @Override
    public GradePageAuthenticator createGradePageAuthenticator(String studentID, String password) {
        return new NCKUAuthenticator(studentID, password);
    }

    @Override
    public GradeCrawler createGradeCrawler(Response loggedInResponse) {
        return new NCKUGradeCrawler(loggedInResponse);
    }

    @Override
    public GradeParser createGradeParser(Response loggedInResponse) {
        return new NCKUGradeParser(loggedInResponse);
    }
}
