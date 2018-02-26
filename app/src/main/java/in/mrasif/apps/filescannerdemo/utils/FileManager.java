package in.mrasif.apps.filescannerdemo.utils;

/**
 * Created by asif on 26/2/18.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Environment;

public class FileManager {
    Context rootContext;
    List<FileType> myFileTypes;

    public FileManager(Context c){
        rootContext=c;
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    public File getSDCardPath(){
        String sdf = Environment.getExternalStorageDirectory().getName();
        return new File(Environment.getExternalStorageDirectory().getPath().replace(sdf,"0/storage/"));
    }
    public File getExternalRootPath(){
        return Environment.getExternalStorageDirectory();
    }
    public File getInternalRootPath(){
        return Environment.getRootDirectory();
    }
    public File getDatabaseDirectory(){
        return rootContext.getDatabasePath("");
    }
    public File getDatabasePath(String dbName){
        return rootContext.getDatabasePath(dbName);
    }

    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int extensionPos = filename.lastIndexOf('.');
        int lastUnixPos = filename.lastIndexOf('/');
        int lastWindowsPos = filename.lastIndexOf('\\');
        int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);

        int index = lastSeparator > extensionPos ? -1 : extensionPos;
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }

    public List<FileType> ScanFiles(File path, String extension){
        myFileTypes=new ArrayList<>();
        readDirectory(path,extension);
        return myFileTypes;
    }

    private void readDirectory(File path, String extension) {
        final File[] files = path.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                FileType fileType=new FileType(file);
                if (fileType.getExtension().equals(extension)){
                    myFileTypes.add(new FileType(file));
                }
            } else {
                readDirectory(file,extension);
            }
        }
    }

    public boolean WriteTextFile(File path, String filename, String data){
        boolean flag=false;
        File file = new File(path, filename);
        try{
            FileOutputStream fostream = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fostream);
            myOutWriter.append(data);
            myOutWriter.close();
            fostream.close();
            flag=true;
            //Toast.makeText(rootContext.getApplicationContext(), "Success !",Toast.LENGTH_SHORT).show();
        } catch(FileNotFoundException e){
            flag=false;
            //Toast.makeText(rootContext.getApplicationContext(), "File not found !",Toast.LENGTH_SHORT).show();
        } catch(IOException ioe){
            flag=false;
            //Toast.makeText(rootContext.getApplicationContext(), "error in input/output !",Toast.LENGTH_SHORT).show();
        }
        return flag;
    }

    public String ReadTextFile(File path, String filename){
        String data=null;
        File file = new File(path, filename);
        try{
            FileInputStream fistream=new FileInputStream(file);
            InputStreamReader myInReader=new InputStreamReader(fistream);
            BufferedReader in=new BufferedReader(myInReader);
            StringBuffer sb=new StringBuffer("");
            String l="";
            String nl=System.getProperty("line.separator");
            while((l=in.readLine()) !=null){
                sb.append(l+nl);
            }
            in.close();
            data=sb.toString();
            myInReader.close();
            fistream.close();
        }catch(FileNotFoundException e){
            data="false";
            //Toast.makeText(rootContext.getApplicationContext(), "File not found !",Toast.LENGTH_SHORT).show();
        } catch(IOException ioe){
            data="false";
            //Toast.makeText(rootContext.getApplicationContext(), "error in input/output !",Toast.LENGTH_SHORT).show();
        }
        return data;
    }

    public boolean WriteBinaryData(File path, String filename, byte[] data){
        boolean flag=false;
        File file = new File(path, filename);
        try{
            OutputStream ostream=new FileOutputStream(file);
            ostream.write(data);
            ostream.close();
            flag=true;
            //Toast.makeText(rootContext.getApplicationContext(), "Success !",Toast.LENGTH_SHORT).show();
        } catch(FileNotFoundException e){
            flag=false;
            //Toast.makeText(rootContext.getApplicationContext(), "File not found !",Toast.LENGTH_SHORT).show();
        } catch(IOException ioe){
            flag=false;
            //Toast.makeText(rootContext.getApplicationContext(), "error in input/output !",Toast.LENGTH_SHORT).show();
        }
        return flag;
    }

    public byte[] ReadBinaryData(File path, String filename){
        byte[] data=null;
        File file = new File(path, filename);
        try{
            FileInputStream fistream=new FileInputStream(file);
            fistream.read(data);
            fistream.close();
        }catch(FileNotFoundException e){
            //Toast.makeText(rootContext.getApplicationContext(), "File not found !",Toast.LENGTH_SHORT).show();
        } catch(IOException ioe){
            //Toast.makeText(rootContext.getApplicationContext(), "error in input/output !",Toast.LENGTH_SHORT).show();
        }
        return data;
    }

    public boolean CopyFromAssets(String fromFileName,File toPath){
        boolean flag=false;
        try {
            File toFile=new File(toPath,fromFileName);
            InputStream istream=rootContext.getAssets().open(fromFileName);
            OutputStream ostream=new FileOutputStream(toFile);
            byte[] data=new byte[istream.available()];
            istream.read(data);
            ostream.write(data);
            istream.close();
            ostream.close();
            flag=true;
        } catch (IOException e) {
            flag=false;
            e.printStackTrace();
        }
        return flag;
    }

    public boolean CopyAssetsToDbDirectory(String fromFileName,String toFileName){
        boolean flag=false;
        try {
            File toFile=new File(rootContext.getDatabasePath(toFileName).getPath());
            InputStream istream=rootContext.getAssets().open(fromFileName);
            OutputStream ostream=new FileOutputStream(toFile);
            byte[] data=new byte[istream.available()];
            istream.read(data);
            ostream.write(data);
            istream.close();
            ostream.close();
            flag=true;
        } catch (IOException e) {
            flag=false;
            e.printStackTrace();
        }
        return flag;
    }

}

