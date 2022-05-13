#!/bin/bash
export className=DemoApplication
echo "## Running $className..."
shift
mvn exec:java -Dexec.mainClass="com.example.demo.$className"
