import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PeerFinder {

    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        /**
         * 
         * @param key - No idea
         * @param value - A full line of input from a file
         * @param context - How you output stuff ig?
         * @throws IOException
         * @throws InterruptedException
         * 
         * <b>itr</b> is a space-separated version of <b>value</b>
         * <b>context.write</b> writes a value (second parameter) into a list identified by a key (first parameter)
         */
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] in = value.toString().split(" ");
            int[] in2 = new int[in.length-1];
            for(int i = 1; i < in.length; i++){ in2[i-1]=Integer.parseInt(in[i]); }
            IntWritable common = new IntWritable(Integer.parseInt(in[0]));
            for(int first = 0; first<in2.length; first++){
                for(int second = 0; second<in2.length; second++){
                    if(first==second) { continue; }
                    word.set(Math.min(in2[first], in2[second])+" "+Math.max(in2[first], in2[second])+":");
                    context.write(word, common);
                }
            }
        }
    }

    public static class IntSumReducer extends Reducer<Text, IntWritable, Text, Text> {


        /**
         * 
         * @param key - The key to identify a list which has been filled by map's <b>context.write</b>
         * @param values - The values in the list
         * @param context - Again, not entirely sure, but this is how you give output
         * @throws IOException
         * @throws InterruptedException
         */
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            StringBuilder res = new StringBuilder();
            for (IntWritable val : values) {
                res.append(String.valueOf(val.get())+",");
            }
            String R = res.toString();
            R=R.substring(0,R.length()-1);
            context.write(key, new Text(R));
        }
    }


    
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "peer finder");
        job.setJarByClass(PeerFinder.class);
        job.setMapperClass(TokenizerMapper.class);
        // job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}