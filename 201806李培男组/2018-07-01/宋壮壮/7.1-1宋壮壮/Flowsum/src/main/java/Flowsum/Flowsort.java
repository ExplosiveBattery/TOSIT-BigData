package Flowsum;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class Flowsort {


    public static class SortMapper extends Mapper<LongWritable, Text, Flowsum, NullWritable>{

        @Override
        protected void map(LongWritable key, Text value,Context context)
                throws IOException, InterruptedException {
            String line = value.toString();

            String[] fileds = StringUtils.split(line,"\t");

            String phoneNB=fileds[0];
            long u_load=Long.parseLong(fileds[1]);
            long d_load=Long.parseLong(fileds[2]);

            context.write(new Flowsum(phoneNB, u_load, d_load), NullWritable.get());

        }

    }

    public static class sortReducder extends Reducer<Flowsum, NullWritable, Text, Flowsum>{
        @Override
        protected void reduce(Flowsum key, Iterable<NullWritable> values,Context context)
                throws IOException, InterruptedException {

            String phoneNB=key.getPhoneNB();

            context.write(new Text(phoneNB), new Flowsum(phoneNB, key.getU_load(), key.getD_load()));


        }

    }


    public static void main(String[] args) throws Exception {

        Configuration conf=new Configuration();

        Job job=Job.getInstance(conf);

        job.setJarByClass(Flowsort.class);

        job.setMapperClass(SortMapper.class);
        job.setReducerClass(sortReducder.class);

        job.setMapOutputKeyClass(Flowsum.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Flowsum.class);

        FileInputFormat.setInputPaths(job, new Path("F:/hadoop/flow/output"));

        FileOutputFormat.setOutputPath(job, new Path("F:/hadoop/flow/output1"));

        job.waitForCompletion(true);

    }


}
