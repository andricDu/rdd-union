# RDD Union 
Simple example showing the reading of files as RDDs
and generating the union which is written out to a file.

## Dependencies

- Java 8
- Maven

(Optional)
- Spark
    - version: spark-1.6.0-bin-hadoop2.6
    - get it here: http://spark.apache.org/downloads.html
    
## Running
Clone the repo and open it up in IntelliJ/Eclipse and run the main class.

Alternatively you can build the uber jar with:
```bash
mvn package
```

and run it against spark if you have it installed. 

## Output 
Output should be written to `/tmp/union.txt/`
```bash
$ cat /tmp/union.txt/part-00000
{"foo": "bar"}
{"foo": "123", "baz": true}
```

