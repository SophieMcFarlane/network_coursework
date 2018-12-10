#!/bin/bash
javac -cp ./ -sourcepath ./ Client.java
java -cp ./ Client localHost 6066
