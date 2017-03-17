package org.icgc.dcc.rddunion;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.stream.Collectors.toList;

public class UnionMain {

  @SneakyThrows
  public static void main(String... args){
    val inputDir = ClassLoader.getSystemResource("inputs");

    /**
     * Get files we want
     */
    val files = Files.walk(Paths.get(inputDir.toURI()));
    val filePaths = files.filter(Files::isRegularFile)
            .map(Path::toAbsolutePath)
            .map(Path::toString)
            .collect(toList());

    System.out.println(filePaths.toString());

    /**
     * The Spark Portion
     */
    val conf = new SparkConf().setAppName("RDD Union").setMaster("local");
    val sc = new JavaSparkContext(conf);
    val union = filePaths.stream().map(sc::textFile).reduce((a,b) -> a.union(b)); // The secret sauce

    if (union.isPresent()) {
      val unionValue = union.get();
      System.out.println("The union of the files is: ");

      // Using single partition just to demonstrate this can go to a single file
      unionValue.repartition(1).saveAsTextFile("/tmp/union.txt");
    }
  }

}
