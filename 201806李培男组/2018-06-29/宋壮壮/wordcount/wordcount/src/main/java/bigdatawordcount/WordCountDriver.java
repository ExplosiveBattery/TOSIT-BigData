package bigdatawordcount;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordCountDriver {

    public static class WordCountMapper extends  Mapper<LongWritable, Text, Text, IntWritable> {  // {}:java里面的包裹类体，方法和字段


        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            // super.map(key, value, context); 删掉
            String[] words  = value.toString().split(" "); // 切分成数组，里面存放的是单词

            for (String word:words  ) {
                String word_ = word;
                context.write(new Text(word_) ,new IntWritable(1) );
            }
        }
    }


    public static class WordCountReducer extends  Reducer<Text, IntWritable, Text, IntWritable>{


        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

            int count_value = 0 ;
            for (IntWritable value:values ) {
                count_value += 1 ;
            }

            context.write(key , new IntWritable(count_value));
        }
    }

    public static void main(String[] args) throws  Exception {
        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
        Job job =  Job.getInstance(configuration);
        job.setNumReduceTasks(1);

        job.setJarByClass(WordCountDriver.class);
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);


        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);


        org.apache.hadoop.mapreduce.lib.input.FileInputFormat.setInputPaths(job,new Path(args[0]));
        org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.setOutputPath(job,new Path(args[1]));

        boolean flag = job.waitForCompletion(true);
        System.exit(flag ? 1:0);
    }
}
