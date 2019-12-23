package hk.ust.comp4651;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

/*
 * Copy a file from a source to a destination.
 * The source and destination could be either the local filesystem
 * or HDFS.
 */
public class CopyFile {

	public static void main(String[] args) throws Exception {
		/*
		 * Validate that two arguments are passed from the command line.
		 */
		if (args.length != 2) {
			System.err.printf("Usage: CopyFile <src file> <dst file>\n");
			System.exit(-1);
		}

		String src = args[0];
		String dst = args[1];

		/*
		 * Prepare the input and output filesystems
		 */
		Configuration conf = new Configuration();
		FileSystem inFS = FileSystem.get(URI.create(src), conf);
		FileSystem outFS = FileSystem.get(URI.create(dst), conf);

		/*
		 * Prepare the input and output streams
		 */
		FSDataInputStream in = null;
		FSDataOutputStream out = null;

		// TODO: Your implementation goes here...
		try{
		in = inFS.open(new Path(src));
        	out = outFS.create(new Path(dst),
                	new Progressable() {
                    	/*
 	* * Print a dot whenever 64 KB of data has been written to
 	* * the datanode pipeline.
 	*	     */
                   	 public void progress() {
                        	System.out.print(".");
                    	}
                	});
       		int BufferSize = 4096;
        	IOUtils.copyBytes(in, out, BufferSize, false);
		}finally{
			IOUtils.closeStream(in);
			IOUtils.closeStream(out);
		}
		
	}
}
