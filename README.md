# Protobuf Performance vs REST

## Plan
1. Simple benchmark using Protobuf and JSON serialization
    1. Create a domain model (Recipe List) 
    2. Add Protobuf to project
        1. Create protobuf files
        2. Setup auto-generated .proto files
        3. Create simple serialization converstion POJOs
    3. Add JSON to project
        1. Create new ObjectFactory
        2. Add annotations on classes
    4. Create method that takes in an Object and serializes and passes back
    5. Create de-serialization method, passes back Object
    6. Create test cases that show/prove serialization
    7. Create test harness to test timing

2. Investigate the Payload as a second source of performance
    1. Create a RESTful service that speaks Protobuf and JSON and stores/retrieves Recipes (in-memory)
    2. Build test suite that runs service locally
    3. Benchmark: query service with Protobuf objects only
    4. Benchmark: query service with JSON objects only



## To Run Benchmark Tests:
```mvn clean verify -Pbenchmark```

## To Run Service Tests:
```mvn clean verify```

## Helpful Links
http://tutorials.jenkov.com/java-performance/jmh.html


Recipe --has-a--> List<IngredientsUsed> --has-a--> Ingredient 
                                     && --has-a--> Quantity 
                                     && --has-a--> MeasurementType