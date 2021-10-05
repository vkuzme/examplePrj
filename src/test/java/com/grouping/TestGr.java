package com.grouping;

import static io.github.sskorol.data.TestDataReader.use;

import core.model.DataSets;
import core.model.TestData;
import io.github.sskorol.core.DataSupplier;
import io.github.sskorol.data.JsonReader;
import java.io.IOException;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestGr {

  static Integer i = 2;

  @DataSupplier
  public List<DataSets> getTestData() {
    // ...
    List<TestData> td = use(JsonReader.class).withTarget(TestData.class).withSource("dam/test.json").read().toList();
    return td.get(0).getDataSets();
  }

  @Test(dataProvider = "getTestData")
  public void test2(final DataSets testData) throws IOException, InterruptedException {

    System.out.println(new Object() {
    }.getClass().getEnclosingMethod().getName() + ": " + testData.toString());
    Assert.assertTrue(i + 1 == 3, "FAIL");
  }

}
