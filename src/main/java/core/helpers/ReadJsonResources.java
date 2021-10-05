package core.helpers;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public enum ReadJsonResources {

  INSTANCE;

  final static String workingDir = System.getProperty("user.dir");
  final static String fileDir = String.format("%s/src/test/resources",
      workingDir);
  private final List<HashMap> jsonSets = new ArrayList<HashMap>();
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static ObjectMapper objectMapper() {
    return OBJECT_MAPPER;
  }

  ReadJsonResources() {
  }

  public List<HashMap> getJsonSet() {
    if (jsonSets.isEmpty()) {
      readFiles();
    }
    List<HashMap> copyJsonSets = new ArrayList<HashMap>(jsonSets);
    return copyJsonSets;
  }

  private void readFiles() {
    File dir = new File(fileDir);
    String[] extensions = new String[]{"json"};
    List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);

    files.forEach(file -> {
      try {
        String jsonFile = IOUtils.resourceToString(file.getPath().replaceAll(fileDir, ""), UTF_8);
        HashMap<String, Object> jsonObject = (HashMap<String, Object>) objectMapper()
            .readValue(jsonFile, Map.class);
        this.jsonSets.add(jsonObject);
      } catch (Exception e) {
      }
    });
  }
}
