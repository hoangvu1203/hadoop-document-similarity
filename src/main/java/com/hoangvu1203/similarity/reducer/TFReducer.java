package com.hoangvu1203.similarity.reducer;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class TFReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private IntWritable sumWritable = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> vals, Context ctx)
            throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable v : vals) sum += v.get();
        sumWritable.set(sum);
        ctx.write(key, sumWritable);
    }
}
