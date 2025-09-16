import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;

public class MetricsAPIConnector {
    public static void main(String[] args) {

        try {
            HttpClient client = HttpClient.newHttpClient();

            String metadataBody = "[{\"productId\":14100417}]";

            HttpRequest metaRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://www150.statcan.gc.ca/t1/wds/rest/getCubeMetadata"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(metadataBody))
                    .build();

            HttpResponse<String> metaResponse = client.send(metaRequest, HttpResponse.BodyHandlers.ofString());

            // Parse response as JSONArray, not JSONObject
            JSONArray jsonArray = new JSONArray(metaResponse.body());
            String prettyJson = jsonArray.toString(4); // 4-space indentation

            // Write to file
            Files.writeString(Path.of("response_pretty.json"), prettyJson);

            System.out.println("Metadata written to response_pretty.json");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

