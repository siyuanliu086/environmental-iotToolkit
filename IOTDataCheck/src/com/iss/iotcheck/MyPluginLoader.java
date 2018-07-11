package com.iss.iotcheck;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import com.iss.iotcheck.plugin.IProcessing;

public class MyPluginLoader extends ClassLoader{

    /**
     * 加载指定路径下的解析器
     * @param 		: pluginPath 路径
     * @return		: List<IProcessing> 解析接口
     * @author 		: Liu Siyuan
     * @Date 		: 2018年7月5日 下午4:16:18
     * @version 1.0.0
     */
    public List<IProcessing> getProcessList(String pluginPath) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<IProcessing> processList = new ArrayList<>();
        File dir = new File(pluginPath);
        if(!dir.exists() || !dir.isDirectory()) {
            return processList;
        }
        String[] pFilePath = dir.list();
        if(pFilePath.length == 0) {
            return processList;  
        }
        
        // 获取并返回类对象
        for(String pluginFile : pFilePath) {
            if(pluginFile.endsWith(".java") || new File(pluginPath + pluginFile).isDirectory()) {
                continue;
            }
            Class<IProcessing> clazzP = (Class<IProcessing>) loadClass(pluginPath + pluginFile);
            processList.add(clazzP.newInstance());
        }
        return processList;
    } 
    
    public Class<?> findClass(String pluginPath) throws ClassNotFoundException {
//        String fileName = name.replace(".", "/") + ".class";
        File classFile = new File(pluginPath);
        String className = classFile.getName();
        className = className.replace(".class", "");
        if(!classFile.exists()) {
            throw new ClassNotFoundException(classFile.getPath() + " 不存在");
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ByteBuffer bf = ByteBuffer.allocate(1024);
            FileInputStream fis = null;
            FileChannel fc = null;

            try {
                fis = new FileInputStream(classFile);
                fc = fis.getChannel();

                while(fc.read(bf) > 0) {
                    bf.flip();
                    bos.write(bf.array(), 0, bf.limit());
                    bf.clear();
                }
            } catch (FileNotFoundException var20) {
                var20.printStackTrace();
            } catch (IOException var21) {
                var21.printStackTrace();
            } finally {
                try {
                    fis.close();
                    fc.close();
                } catch (IOException var19) {
                    var19.printStackTrace();
                }
            }
//            return this.defineClass(className, bos.toByteArray(), 0, bos.toByteArray().length);
            return this.defineClass(bos.toByteArray(), 0, bos.toByteArray().length);
        }
    }
    
    public static void main(String[] args) {
        MyPluginLoader myLoader = new MyPluginLoader();
        try {
            List<IProcessing> list = myLoader.getProcessList("D:/run_pre/iotServer/process/plugin/");
            System.out.println(list.size());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
