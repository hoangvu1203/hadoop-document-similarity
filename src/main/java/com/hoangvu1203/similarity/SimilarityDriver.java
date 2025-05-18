package com.hoangvu1203.similarity;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.hoangvu1203.similarity.mapper.TFMapper;
import com.hoangvu1203.similarity.mapper.PairMapper;
import com.hoangvu1203.similarity.reducer.TFReducer;
import com.hoangvu1203.similarity.reducer.PairReducer;

public class SimilarityDriver extends Configured implements Tool {
    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new SimilarityDriver(), args);
        System.exit(res);
    }

    @Override
    public int run(String[] args) throws Exception {
		if (args.length != 3) {
			System.err.println("Usage: -job [tf|sim] <input> <output>");
			return -1;
		}

		String jobType = args[0];
		Path inputPath = new Path(args[1]);
		Path outputPath = new Path(args[2]);
		Job job;

		if (jobType.equals("tf")) {
			job = Job.getInstance(getConf(), "TFJob");
			job.setJarByClass(SimilaritySearch.class);
			job.setMapperClass(TFMapper.class);
			job.setReducerClass(TFReducer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
		} else if (jobType.equals("sim")) {
			job = Job.getInstance(getConf(), "SimilarityJob");
			job.setJarByClass(SimilaritySearch.class);
			job.setMapperClass(PairMapper.class);
			job.setReducerClass(PairReducer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(IntWritable.class);
		} else {
			System.err.println("Invalid job type: " + jobType);
			return -1;
		}

		FileInputFormat.setInputPaths(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		return job.waitForCompletion(true) ? 0 : 1;
	}
}
