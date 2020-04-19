package com.wrapper.proxyapplication;

import android.annotation.SuppressLint;
import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class CustomerClassLoader extends PathClassLoader {
    private static final boolean VERBOSE_DEBUG = false;
    private boolean initialized;
    private final String libPath;
    private final String mDexOutputPath;
    private DexFile[] mDexs;
    private File[] mFiles;
    private String[] mLibPaths;
    private String[] mPaths;
    private ZipFile[] mZips;
    private final String path;

    private native int ShowLogs(String str, int i);

    public CustomerClassLoader(String path2, String optimizedDirectory, String libPath2, ClassLoader parent) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException, NullPointerException, IOException {
        super("", libPath2, parent.getParent());
        if (path2 == null || optimizedDirectory == null) {
            throw new NullPointerException();
        }
        this.path = path2;
        this.libPath = libPath2;
        this.mDexOutputPath = optimizedDirectory;
        try {
            findField(parent, "parent").set(parent, this);
            ensureInit();
        } catch (NoSuchFieldException e) {
            throw e;
        } catch (IOException e2) {
            throw e2;
        }
    }

    private synchronized void ensureInit() throws IOException {
        if (!this.initialized) {
            this.initialized = true;
            this.mPaths = this.path.split(":");
            int length = this.mPaths.length;
            this.mFiles = new File[length];
            this.mZips = new ZipFile[length];
            this.mDexs = new DexFile[length];
            for (int i = 0; i < length; i++) {
                File pathFile = new File(this.mPaths[i]);
                this.mFiles[i] = pathFile;
                if (pathFile.isFile()) {
                    try {
                        this.mZips[i] = new ZipFile(pathFile);
                    } catch (IOException e) {
                    }
                    try {
                        if (this.mDexOutputPath != null) {
                            this.mDexs[i] = DexFile.loadDex(this.mPaths[i], generateOutputName(this.mPaths[i], this.mDexOutputPath), 0);
                        }
                    } catch (IOException e2) {
                        throw new IOException("load dex fail");
                    } catch (RuntimeException e3) {
                        throw new IOException("load dex fail");
                    }
                }
            }
            String pathList = System.getProperty("java.library.path", ".");
            String pathSep = System.getProperty("path.separator", ":");
            String fileSep = System.getProperty("file.separator", "/");
            if (this.libPath != null) {
                if (pathList.length() > 0) {
                    pathList = this.libPath + pathSep + pathList;
                } else {
                    pathList = this.libPath;
                }
            }
            this.mLibPaths = pathList.split(pathSep);
            int length2 = this.mLibPaths.length;
            for (int i2 = 0; i2 < length2; i2++) {
                if (!this.mLibPaths[i2].endsWith(fileSep)) {
                    String[] strArr = this.mLibPaths;
                    strArr[i2] = strArr[i2] + fileSep;
                }
            }
        }
    }

    private static String generateOutputName(String sourcePathName, String outputDir) {
        String sourceFileName;
        StringBuilder newStr = new StringBuilder(80);
        newStr.append(outputDir);
        if (!outputDir.endsWith("/")) {
            newStr.append("/");
        }
        int lastSlash = sourcePathName.lastIndexOf("/");
        if (lastSlash < 0) {
            sourceFileName = sourcePathName;
        } else {
            sourceFileName = sourcePathName.substring(lastSlash + 1);
        }
        int lastDot = sourceFileName.lastIndexOf(".");
        if (lastDot < 0) {
            newStr.append(sourceFileName);
        } else {
            newStr.append(sourceFileName, 0, lastDot);
        }
        newStr.append(".dex");
        return newStr.toString();
    }

    /* access modifiers changed from: protected */
    public Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            ensureInit();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        int length = this.mPaths.length;
        for (int i = 0; i < length; i++) {
            if (this.mDexs[i] != null) {
                ShowLogs(name, i);
                Class clazz = this.mDexs[i].loadClass(name.replace('.', '/'), this);
                if (clazz != null) {
                    return clazz;
                }
                try {
                    Class cla = super.findClass(name);
                    if (cla != null) {
                        return cla;
                    }
                } catch (ClassNotFoundException e) {
                }
            } else if (this.mZips[i] != null) {
                byte[] data = loadFromArchive(this.mZips[i], name.replace('.', '/') + ".class");
            } else if (this.mFiles[i].isDirectory()) {
                byte[] data2 = loadFromDirectory(new StringBuilder(String.valueOf(this.mPaths[i])).append("/").append(name.replace('.', '/')).append(".class").toString());
            }
        }
        throw new ClassNotFoundException(new StringBuilder(String.valueOf(name)).append(" in loader ").append(this).toString());
    }

    /* access modifiers changed from: protected */
    public URL findResource(String name) {
        URL url = super.findResource(name);
        if (url != null) {
            return url;
        }
        int length = this.mPaths.length;
        int i = 0;
        while (i < length) {
            File pathFile = this.mFiles[i];
            ZipFile zip = this.mZips[i];
            if (this.mPaths[i].endsWith(".dex") || zip.getEntry(name) == null) {
                i++;
            } else {
                try {
                    return new URL("jar:" + pathFile.toURL() + "!/" + name);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    private byte[] loadFromDirectory(String path2) {
        try {
            RandomAccessFile raf = new RandomAccessFile(path2, "r");
            try {
                byte[] fileData = new byte[((int) raf.length())];
                raf.read(fileData);
                raf.close();
                return fileData;
            } catch (IOException e) {
                System.err.println("Error reading from " + path2);
                return null;
            }
        } catch (FileNotFoundException e2) {
            return null;
        }
    }

    private byte[] loadFromArchive(ZipFile zip, String name) {
        ZipEntry entry = zip.getEntry(name);
        if (entry == null) {
            return null;
        }
        try {
            InputStream stream = zip.getInputStream(entry);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream((int) entry.getSize());
            byte[] buf = new byte[4096];
            while (true) {
                int count = stream.read(buf);
                if (count <= 0) {
                    stream.close();
                    return byteStream.toByteArray();
                }
                byteStream.write(buf, 0, count);
            }
        } catch (IOException e) {
            return null;
        }
    }

    private boolean isInArchive(ZipFile zip, String name) {
        return zip.getEntry(name) != null;
    }

    public String findLibrary(String libname) {
        try {
            ensureInit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = System.mapLibraryName(libname);
        for (String valueOf : this.mLibPaths) {
            String pathName = new StringBuilder(String.valueOf(valueOf)).append(fileName).toString();
            if (new File(pathName).exists()) {
                return pathName;
            }
            String path2 = super.findLibrary(libname);
            if (path2 != null) {
                return path2;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"NewApi"})
    public Package getPackage(String name) {
        Package pack = null;
        if (name != null && !"".equals(name)) {
            synchronized (this) {
                pack = super.getPackage(name);
                if (pack == null) {
                    pack = definePackage(name, "Unknown", "0.0", "Unknown", "Unknown", "0.0", "Unknown", null);
                }
            }
        }
        return pack;
    }

    private static Field findField(Object instance, String name) throws NoSuchFieldException {
        Class<?> clazz = instance.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(name);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }
}
