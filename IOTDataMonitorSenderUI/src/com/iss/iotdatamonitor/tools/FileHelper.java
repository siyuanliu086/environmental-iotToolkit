package com.iss.iotdatamonitor.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHelper {

    private String message;

    public boolean SaveNewsData(String filePath, String fileName, String fileContent, String encoding) {
        boolean result = false;
        File myFilePath = new File(filePath);
        if (!myFilePath.exists()) {
            boolean bcreated = myFilePath.mkdirs();
            if (bcreated) {
                Logger.getLogger(FileHelper.class.getName()).log(Level.INFO, null, "成功建立目录：" + filePath);
            } else {
                Logger.getLogger(FileHelper.class.getName()).log(Level.INFO, null, "失败建立目录：" + filePath);
            }
        }
        result = CreateFile(filePath + "/" + fileName, fileContent, encoding);
        return result;
    }

    public boolean SaveNewsLog(String filePath, String fileName, String fileContent) {
        boolean result = false;
        File myFilePath = new File(filePath);
        if (!myFilePath.exists()) {
            boolean bcreated = myFilePath.mkdirs();
            if (bcreated) {
                Logger.getLogger(FileHelper.class.getName()).log(Level.INFO, null, "成功建立目录：" + filePath);
            } else {
                Logger.getLogger(FileHelper.class.getName()).log(Level.INFO, null, "失败建立目录：" + filePath);
            }
        }
        result = CreateFile(filePath + "/" + fileName, fileContent, "UTF-8");
        return result;
    }

    public static String readTxt(String filePathAndName, String encoding) throws IOException {
        encoding = encoding.trim();
        StringBuffer str = new StringBuffer("");
        String st = "";
        try {
            FileInputStream fs = new FileInputStream(filePathAndName);
            InputStreamReader isr;
            if (encoding.equals(""))
                isr = new InputStreamReader(fs);
            else {
                isr = new InputStreamReader(fs, encoding);
            }
            BufferedReader br = new BufferedReader(isr);
            try {
                String data = "";
                while ((data = br.readLine()) != null)
                    str.append(data + " ");
            } catch (Exception e) {
                str.append(e.toString());
            } finally {
                br.close();
                isr.close();
                fs.close();
            }
            st = str.toString();
        } catch (IOException es) {
            st = "";
        }
        return st;
    }

    public String CreateFolder(String folderPath) {
        String txt = folderPath;
        try {
            File myFilePath = new File(txt);
            txt = folderPath;
            if (!myFilePath.exists())
                myFilePath.mkdir();
        } catch (Exception e) {
            this.message = "创建目录操作出错";
        }
        return txt;
    }

    public String CreateFolders(String folderPath, String paths) {
        String txts = folderPath;
        try {
            txts = folderPath;
            StringTokenizer st = new StringTokenizer(paths, "/");
            for (int i = 0; st.hasMoreTokens(); i++) {
                String txt = st.nextToken().trim();
                if (txts.lastIndexOf("/") != -1)
                    txts = CreateFolder(txts + txt);
                else
                    txts = CreateFolder(txts + txt + "/");
            }
        } catch (Exception e) {
            this.message = "创建目录操作出错！";
        }
        return txts;
    }

    public void CreateFile(String filePathAndName, String fileContent) {
        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.createNewFile();
            }
            FileWriter resultFile = new FileWriter(myFilePath);
            PrintWriter myFile = new PrintWriter(resultFile);
            String strContent = fileContent;
            myFile.println(strContent);
            myFile.close();
            resultFile.close();
        } catch (Exception e) {
            this.message = "创建文件操作出错";
        }
    }

    public boolean CreateFile(String filePathAndName, String fileContent, String encoding) {
        boolean result = false;
        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.createNewFile();
            }
            PrintWriter myFile = new PrintWriter(myFilePath, encoding);
            String strContent = fileContent;
            myFile.println(strContent);
            myFile.close();

            result = true;
        } catch (Exception e) {
            this.message = "创建文件操作出错";
        }
        return result;
    }

    public boolean DelFile(String filePathAndName) {
        boolean bea = false;
        try {
            String filePath = filePathAndName;
            File myDelFile = new File(filePath);
            if (myDelFile.exists()) {
                myDelFile.delete();
                bea = true;
            } else {
                bea = false;
                this.message = (filePathAndName + "删除文件操作出错");
            }
        } catch (Exception e) {
            this.message = e.toString();
        }
        return bea;
    }

    public void delFolder(String folderPath) {
        try {
            delAllFile(folderPath);
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete();
        } catch (Exception e) {
            this.message = "删除文件夹操作出错";
        }
    }

    public boolean delAllFile(String path) {
        boolean bea = false;
        File file = new File(path);
        if (!file.exists()) {
            return bea;
        }
        if (!file.isDirectory()) {
            return bea;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator))
                temp = new File(path + tempList[i]);
            else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);
                delFolder(path + "/" + tempList[i]);
                bea = true;
            }
        }
        return bea;
    }

    public void copyFile(String oldPathFile, String newPathFile) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPathFile);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPathFile);
                FileOutputStream fs = new FileOutputStream(newPathFile);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;

                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            this.message = "复制单个文件操作出错";
        }
    }

    public void copyFolder(String oldPath, String newPath) {
        try {
            new File(newPath).mkdirs();
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator))
                    temp = new File(oldPath + file[i]);
                else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);

                    FileOutputStream output = new FileOutputStream(newPath + "/" + temp.getName().toString());
                    byte[] b = new byte[5120];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory())
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
            }
        } catch (Exception e) {
            this.message = "复制整个文件夹内容操作出错";
        }
    }

    public void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        DelFile(oldPath);
    }

    public void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }

    public String getMessage() {
        return this.message;
    }

    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
}
