package core.helpers;

import io.github.sskorol.core.IAnnotationTransformerInterceptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;
import org.testng.annotations.ITestAnnotation;

public class LtaIAnnotationTransformerInterceptorImpl implements
    IAnnotationTransformerInterceptor { // IAnnotationTransformerInterceptor IAnnotationTransformer

  private final Logger logger = Logger.getLogger(LtaIAnnotationTransformerInterceptorImpl.class);

  @Override
  public void transform(ITestAnnotation annotation, Class testClass,
      Constructor testConstructor, Method testMethod) {

    logger.info("transform");

    List<HashMap> jsonSets = new ArrayList<>(ReadJsonResources.INSTANCE.getJsonSet());
    jsonSets.forEach(jsonSet -> overrideAnnotationForMethod(annotation, testMethod, jsonSet));
  }

  private void overrideAnnotationForMethod(ITestAnnotation annotation, Method testMethod,
      HashMap jsonSet) {

    String[] overrideGroups = {""};
    Set<String> classesList = new TreeSet();
    Set<String> methodsList = new TreeSet();
    Set<String> mergedList = new TreeSet();
    String regex = "[\\]\\[\\ ]";

    if (jsonSet.get("classes") != null && !"".equals(jsonSet.get("classes"))) {
      classesList.addAll(
          Arrays.asList(jsonSet.get("classes").toString().replaceAll(regex, "").split(",")));
    }

    if (jsonSet.get("methods") != null && !"".equals(jsonSet.get("methods"))) {
      methodsList.addAll(
          Arrays.asList(jsonSet.get("methods").toString().replaceAll(regex, "").split(",")));
    }

    if (classesList.contains(testMethod.getDeclaringClass().getName())
        && methodsList.contains(testMethod.getName())) {

      logger.info(testMethod.getName() + " >>> groups BEFORE overriding ... " + Arrays.toString(
          annotation.getGroups()));

      if (jsonSet.get("enabled") != null && "false".equals(jsonSet.get("enabled").toString())) {
        annotation.setEnabled(false);
        logger.info(testMethod.getName() + " >>> Enabled has been set to FALSE ... ");
      } else {

        if (jsonSet.get("groups") != null && !"".equals(jsonSet.get("groups"))) {
          mergedList.addAll(
              Arrays.asList(jsonSet.get("groups").toString().replaceAll(regex, "").split(",")));
        }

        ArrayList<Object> dataSets = (ArrayList<Object>) jsonSet.get("dataSets");
        for (Object child : dataSets) {
          if (((LinkedHashMap) child).get("groups") != null
              && !"".equals(((LinkedHashMap) child).get("groups"))) {
            mergedList.addAll(
                Arrays.asList(
                    ((LinkedHashMap) child).get("groups").toString().replaceAll(regex, "")
                        .split(",")));
          }
        }
        if (mergedList.size() > 0) {
          overrideGroups = mergedList.toArray(overrideGroups);
          if (overrideGroups.length > 0) {
            annotation.setGroups(overrideGroups);
            logger.info(
                testMethod.getName() + " >>> groups has been overridden ... " + Arrays.toString(
                    annotation.getGroups()));
          }
        }
      }
    }
  }

}
