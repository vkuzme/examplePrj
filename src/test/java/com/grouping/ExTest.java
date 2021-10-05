package com.grouping;

import static io.github.sskorol.data.TestDataReader.use;

import core.model.DataSets;
import core.model.TestData;
import io.github.sskorol.core.DataSupplier;
import io.github.sskorol.data.JsonReader;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class ExTest {

  static Integer i = 2;
  private ThreadLocal<String> testName = new ThreadLocal<>();

  @BeforeSuite(alwaysRun = true)
  void beforeSuite() {
    System.out.println("BeforeSuite");
  }

  @DataSupplier
  public List<DataSets> getTestData() {
    List<TestData> td = use(JsonReader.class).withTarget(TestData.class).withSource("exp/test.json").read().toList();
    return td.get(0).getDataSets();
  }

  @Test(dataProvider = "getTestData")
  public void testFetchData(final DataSets testRequest) {
    System.out.println(testRequest);
    Assert.assertTrue(testRequest.enabled, "FAIL");
  }

}
