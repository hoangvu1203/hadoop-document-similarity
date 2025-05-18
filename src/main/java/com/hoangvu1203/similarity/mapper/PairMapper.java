package com.hoangvu1203.similarity.mapper;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public static class PairMapper extends Mapper<LongWritable, Text, PairWritable, IntWritable> {
    private Map<String, List<Pair<String, Integer>>> termToDocs = new HashMap<>();

    @Override
    protected void map(LongWritable key, Text value, Context ctx) {
        // line: term@docId \t tf
        String[] parts = value.toString().split("\\t");
        if (parts.length != 2) return;
        String[] termDoc = parts[0].split("@");
        if (termDoc.length != 2) return;

        String term = termDoc[0];
        String doc = termDoc[1];
        int tf = Integer.parseInt(parts[1]);

        termToDocs
            .computeIfAbsent(term, k -> new ArrayList<>())
            .add(new Pair<>(doc, tf));
    }

    @Override
    protected void cleanup(Context ctx) throws IOException, InterruptedException {
        for (List<Pair<String, Integer>> docs : termToDocs.values()) {
            for (int i = 0; i < docs.size(); i++) {
                for (int j = i + 1; j < docs.size(); j++) {
                    Pair<String, Integer> a = docs.get(i);
                    Pair<String, Integer> b = docs.get(j);
                    PairWritable pairKey = new PairWritable(a.first, b.first);
                    int product = a.second * b.second;
                    ctx.write(pairKey, new IntWritable(product));
                }
            }
        }
    }

    private static class Pair<A, B> {
        A first;
        B second;
        Pair(A a, B b) {
            first = a;
            second = b;
        }
    }
}

