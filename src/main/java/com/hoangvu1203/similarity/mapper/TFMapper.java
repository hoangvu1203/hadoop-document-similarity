package com.hoangvu1203.similarity.mapper;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import com.hoangvu1203.similarity.preprocess.TextPreprocessor;

import java.io.IOException;

public class TFMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private Text termAtDoc = new Text();
    private static final IntWritable ONE = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context ctx)
            throws IOException, InterruptedException {
        String line = TextPreprocessor.clean(value.toString());
        String docId = ctx.getInputSplit().toString();
        for (String term : line.split("\\s+")) {
            termAtDoc.set(term + "@" + docId);
            ctx.write(termAtDoc, ONE);
        }
    }
}
