package core.model;

public class DataSets {

  public class Payload {

    private String type = "";
  }

  private Payload payload = new Payload();

  public String description = "default";
  public Boolean enabled = true;
  public String[] groups;
  public String[] excludeGroups;
  private Integer response;
  private String errorMessage;
}
