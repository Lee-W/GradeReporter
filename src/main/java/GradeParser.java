import org.json.JSONObject;
import org.jsoup.nodes.Document;

public interface GradeParser {
    JSONObject parse(Document document);
    JSONObject getSemesterData();
    JSONObject getRawData();
}
