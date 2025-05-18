#!/bin/bash
INPUT=$1
OUTPUT=$2
JAR=target/SimilaritySearch-1.0.0-jar-with-dependencies.jar

# 1. Term Frequency
hadoop jar $JAR \
  com.yourname.similarity.SimilarityDriver \
  -job tf \
  -in $INPUT \
  -out $OUTPUT/tf

# 2. Pairwise Similarity
hadoop jar $JAR \
  com.yourname.similarity.SimilarityDriver \
  -job sim \
  -in $OUTPUT/tf \
  -out $OUTPUT/similarity
