package scdx.bigdata.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

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

    /**
     * 第一个key value对表示的是数据输入形式，第二对是当前任务输出的结果
     */
   public static class WordCountReducer extends  Reducer<Text, IntWritable, Text, IntWritable>{

        /**
         * Iterable<IntWritable> :是输入的IntWritable  对maper输出的key进行了归并，[1,1,1,1,1,1]
         * @param key
         * @param values
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
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
        Configuration configuration = new Configuration();
        Job job =  Job.getInstance(configuration);
        job.setNumReduceTasks(1);

        job.setJarByClass(WordCountDriver.class);
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);


        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);


        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));

        boolean flag = job.waitForCompletion(true);
        System.exit(flag ? 1:0);
    }
}
