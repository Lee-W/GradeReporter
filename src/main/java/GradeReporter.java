import org.json.JSONObject;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;


public class GradeReporter {
    private GradeComponentFactory factory;
    private GradePageAuthenticator gradePageAuthenticator;
    private GradeCrawler gradeCrawler;
    private GradeParser gradeParser;
    private GradeStat gradeStat;

    public GradeReporter(String schoolName, String studentID, String password) {
        if (schoolName.toLowerCase().equals("ncku")) {
            factory = new NCKUCompoentFactory();

            gradePageAuthenticator = new NCKUAuthenticator(studentID, password);
            gradePageAuthenticator.login();
            Response loggedInResponse = gradePageAuthenticator.getLoggedInResponse();

            gradeCrawler = new NCKUGradeCrawler(loggedInResponse);
            gradeParser = new NCKUGradeParser(loggedInResponse);
            gradeStat = new NCKUStat("after104");
        } else {
            System.out.println("Currently not supported");
        }
    }

    @Override
    public void finalize() {
        gradePageAuthenticator.logout();
    }

    public JSONObject parseGradeData() {
        Document document = gradeCrawler.crawlGradePages();
        gradeParser.parse(document);

        JSONObject semesterData = gradeParser.getSemesterData();
        JSONObject summaryData = gradeStat.report(gradeParser.getRawData());

        JSONObject overallData = new JSONObject();
        overallData.put("courses", semesterData);
        overallData.put("summary", summaryData);

        return overallData;
    }
}
