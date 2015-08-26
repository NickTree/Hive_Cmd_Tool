package tool.sql.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * 
 * @author jiang wenwei
 * @since 1.0.0
 * 
 *        <p>
 *        Created on 2012-07-02
 *        </p>
 */
public class FileUnit {
	//final static String path = "BMW";
	public static void write(String s ,String path) {
		try {
			FileWriter fw = new FileWriter(path,true);
			fw.write(s + "\n");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static HashSet<String> read(String path) {
		HashSet<String> s = new HashSet<String>();
		try {
			FileReader fr = new FileReader(path);
			BufferedReader b = new BufferedReader(fr);
			String k = b.readLine();
			while(k != null){
				s.add(k);
				k =  b.readLine();
			}
			b.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	public static String readAll(String path) {
		StringBuffer s = new StringBuffer();
		try {
			FileReader fr = new FileReader(path);
			BufferedReader b = new BufferedReader(fr);
			String k = b.readLine();
			while(k != null){
				s = s.append(k).append("\n");
				k =  b.readLine() ;
			}
			b.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s.toString();
	}
	
	public static String readAll(File path) {
        StringBuffer s = new StringBuffer();
        try {
            FileReader fr = new FileReader(path);
            BufferedReader b = new BufferedReader(fr);
            String k = b.readLine();
            while(k != null){
                s = s.append(k).append("\n");
                k =  b.readLine() ;
            }
            b.close();
            fr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return s.toString();
    }
	
	public static ArrayList<String> readList(String path) {
		ArrayList<String> s = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(path);
			BufferedReader b = new BufferedReader(fr);
			String k = b.readLine();
			while(k!=null){
				s.add(k);
				k =  b.readLine();
			}
			b.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	   public static ArrayList<String> readList(String path,String charset) {
	        ArrayList<String> s = new ArrayList<String>();
	        try {
	            InputStreamReader isr=new InputStreamReader(new FileInputStream(path),charset);
	            BufferedReader b = new BufferedReader(isr);
	            String k = b.readLine();
	            while(k!=null){
	                s.add(k);
	                k =  b.readLine();
	            }
	            b.close();
	            isr.close();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return s;
	    }

	
	public static HashSet<String> readList1(String path) {
		HashSet<String> s = new HashSet<String>();
		s.addAll(readList(path));
		return s;
	}
	public static void fileCopy(String path) {
		File f = new File(path);
		if (!f.exists())
			return;
		FileInputStream fi = null;
		FileChannel in = null;
		FileOutputStream fo = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(f);
			in = fi.getChannel();
			// fc.
			fo = new FileOutputStream(path + "_bak");
			out = fo.getChannel();
			out.transferFrom(in, 0, in.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fi != null)
					fi.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (fo != null)
					fo.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
