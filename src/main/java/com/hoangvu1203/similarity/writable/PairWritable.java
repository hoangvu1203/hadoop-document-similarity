package com.hoangvu1203.similarity.writable;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

import java.io.*;

public class PairWritable implements WritableComparable<PairWritable> {
    private String docA, docB;

    public PairWritable() { }

    public PairWritable(String a, String b) {
        this.docA = a;
        this.docB = b;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        WritableUtils.writeString(out, docA);
        WritableUtils.writeString(out, docB);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        docA = WritableUtils.readString(in);
        docB = WritableUtils.readString(in);
    }

    @Override
    public int compareTo(PairWritable o) {
        int cmp = docA.compareTo(o.docA);
        return (cmp != 0) ? cmp : docB.compareTo(o.docB);
    }

    @Override
    public int hashCode() {
        return docA.hashCode() * 163 + docB.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PairWritable) {
            PairWritable p = (PairWritable) o;
            return docA.equals(p.docA) && docB.equals(p.docB);
        }
        return false;
    }

    @Override
    public String toString() {
        return docA + "\t" + docB;
    }
}
