package core.model;


import io.github.sskorol.data.Source;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Source(path = "exp/test.json")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TestData {

  public List<DataSets> dataSets;

  public Boolean enabled = true;
  public String[] groups;
  public String[] excludeGroups;
  public String[] testClass;
  public String[] testMethod;
}
