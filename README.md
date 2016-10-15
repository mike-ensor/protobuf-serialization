# Protobuf Performance vs REST

## Plan
1. Simple benchmark using Protobuf and JSON serialization
   1. Create a domain model (Recipe List) 
   2. Add Protobuf to project
   3. Add JSON to project
   4. Create method that takes in an Object and serializes and passes back
   5. Create de-serialization method, passes back Object
   6. Create test cases that show/prove serialization
   7. Create test harness to test timing

2. Extend benchmarking to do a simple call to a stand-alone service implemented using both JSON and Protobuf


## Helpful Links
http://tutorials.jenkov.com/java-performance/jmh.html