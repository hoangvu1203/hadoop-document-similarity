package com.hoangvu1203.similarity.reducer;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;
import com.hoangvu1203.similarity.writable.PairWritable;

import java.io.IOException;

public class PairReducer extends Reducer<PairWritable, IntWritable, PairWritable, DoubleWritable> {
    @Override
    protected void reduce(PairWritable key, Iterable<IntWritable> vals, Context ctx)
            throws IOException, InterruptedException {
        // sum co-occurrences → similarity score (or TF product)
        double score = 0;
        for (IntWritable v : vals) score += v.get();
        ctx.write(key, new DoubleWritable(score));
    }
}
