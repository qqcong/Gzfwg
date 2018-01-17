package com.bwoil.userJdbc;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by wwj on 2017/12/15.
 */
public class FileUtil {

    // 检测指定目录下 以某个日期命名的文件夹下的文件数
    public static int autoDectect(String savePath, String date) throws Exception {
        File file = new File(savePath + "/" + date);

        if (!file.exists()) {
            if (file.mkdirs()) {
            } else {
                throw new Exception("失败：无法创建以下目录:[" + savePath + "/" + date + "]");
            }
        }

        int totalFiles = 0;

        File[] subFiles = file.listFiles();

        if (subFiles != null && subFiles.length > 0) {

            String patternStr = "_" + date + "_";

            for (int i = 0; i < subFiles.length; i++) {

                String fileName = subFiles[i].getName();

                if (null != fileName && fileName.indexOf(patternStr) != -1) {

                    totalFiles++;
                }
            }
        }

        return totalFiles;
    }

    public static void zipFile(String filePath, String fileName) throws Exception {
        String[] filenames = new String[]{fileName};
        byte[] buf = new byte[1024];
        String outFilename = fileName + ".zip";

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(filePath + "/" + outFilename));

        for (int i = 0; i < filenames.length; i++) {
            FileInputStream in = new FileInputStream(filePath + "/" + filenames[i]);
            out.putNextEntry(new ZipEntry(filenames[i]));
            int len = 0;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            out.closeEntry();
            in.close();
        }

        out.close();
    }

    public static void mkDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String writeFile(String path, String fileName, String content) throws IOException {
        return writeFile(path, fileName,content, "UTF-8");
    }

    public static String writeFile(String path, String fileName, String content, String charset) throws IOException {
        mkDir(path);
        String filePath = path + File.separator + fileName;
        File file = new File(filePath);

        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileOutputStream fops = new FileOutputStream(file);
        fops.write((content).getBytes(charset));

        fops.flush();
        fops.close();
        return filePath;
    }

    public static String writeFile(File file, String path, String fileName, String content, String charset) throws IOException {
        mkDir(path);
        String filePath = path + File.separator + fileName;
        file = new File(filePath);

        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileOutputStream fops = new FileOutputStream(file);

        fops.write((content).getBytes(charset));

        fops.flush();
        fops.close();
        return filePath;
    }

    public static String readFile(String filePath) throws IOException {
        File file = new File(filePath);

        FileInputStream fin = new FileInputStream(file);

        ByteArrayOutputStream bs = new ByteArrayOutputStream();

        while (true) {
            int read = fin.read();
            if (read == -1) {
                break;
            } else {
                bs.write(read);
            }
        }

        String content = new String(bs.toByteArray(), "UTF-8");

        fin.close();
        bs.close();
        return content;
    }

    public static void main(String[] args) throws Exception {}

    public static byte[] getFileBytes(String filePath) throws IOException {
        File file = new File(filePath);

        FileInputStream fin = new FileInputStream(file);

        ByteArrayOutputStream bs = new ByteArrayOutputStream();

        while (true) {
            int read = fin.read();
            if (read == -1) {
                break;
            } else {
                bs.write(read);
            }
        }

        byte[] bytesArr = bs.toByteArray();

        fin.close();
        bs.close();
        return bytesArr;
    }

    public static byte[] createFileBytesByFileContent(String path, String fileName, String fileContent) {
        return createFileBytesByFileContent(path, fileName, fileContent, "ISO8859-1");
    }

    public static byte[] createFileBytesByFileContent(String path, String fileName, String fileContent, String charset) {
        String filePath = null;
        try {
            filePath = writeFile(path, fileName, fileContent, charset);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] retunBytes = null;
        try {
            retunBytes = getFileBytes(filePath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return retunBytes;
    }

    public static String getStringByFile(File file,String charset) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);


            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static List<Map<String,String>> getMapByFile(File file, String charset)throws Exception {
        List<Map<String,String>> result = new ArrayList<Map<String, String>>();

        BufferedReader reader = null;

        reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
        String line = "";
        while ((line = reader.readLine()) != null) {
            result.add(getMapByJson(line));
        }


        return result;
    }

    /**
     * 将json转换为map
      * @param msg
     * @return
     */
    public static Map<String,String> getMapByJson(String msg){

        String[] parms = msg.split("\",");
        Map<String,String> m = new HashMap<String, String>();
        for (String parm:parms){
            String s = parm.replaceAll(",","").replaceAll("\'","").replaceAll("\\{","").replaceAll("\\}","").replaceAll("\\[","").replaceAll("\\]","");
            String[] temp = s.split("\":");
            if(temp.length==2)
                m.put(temp[0].trim().replaceAll("\"",""),temp[1].replaceAll("\"",""));
            else if(temp.length==1)
                m.put(temp[0].trim().replaceAll("\"",""),"");
        }
        return m;
    }




}
