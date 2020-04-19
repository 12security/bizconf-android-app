package com.wrapper.proxyapplication;

import android.content.Context;
import android.os.Process;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Util {
    public static String CPUABI = null;
    static final int ERROR_EXCEPTION = -2;
    static final int ERROR_FALSE = 0;
    static final int ERROR_FILE_EXIST = 2;
    static final int ERROR_FILE_NOT_FOUND = -1;
    static final int ERROR_FILE_NOT_FOUND_INZIP = -3;
    static final int ERROR_SUCCESS = 1;
    public static int MAX_DEX_NUM = 300;
    public static String TAG = "Util";
    public static String dexname = "classes.dex";
    public static boolean ifoverwrite = true;
    public static String libname;
    public static String securename0 = "00O000ll111l.dex";
    public static String securename1 = "00O000ll111l.jar";
    public static String securename2 = "000O00ll111l.dex";
    public static String securename3 = "0000000lllll.dex";
    public static String securename4 = "000000olllll.dex";
    public static String securename5 = "0OO00l111l1l";
    public static String securename6 = "o0oooOO0ooOo.dat";
    public static String securename7 = "exportService.txt";
    public static String securename8 = ".flag00O000ll111l.dex";
    public static String securename9 = ".updateIV.dat";
    public static String simplelibname = "tosprotection";
    public static String versionname = "tosversion";

    static {
        libname = "";
        getCPUABI();
        if (CPUABI == "x86") {
            libname = "libshellx-super.2019.so";
        } else {
            libname = "shell-super.2019";
        }
    }

    public static int DeleteFile(String filepath) {
        File tmpfile = new File(filepath);
        if (!tmpfile.exists()) {
            return ERROR_FILE_NOT_FOUND;
        }
        if (!tmpfile.delete()) {
            return ERROR_EXCEPTION;
        }
        return 1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:74:0x009a A[SYNTHETIC, Splitter:B:74:0x009a] */
    public static boolean UnzipFile(ZipFile zf, String filepathinzip, File fileinfiledir) {
        BufferedOutputStream Output_fos = null;
        BufferedInputStream bufbr = null;
        try {
            ZipEntry ze = zf.getEntry(filepathinzip);
            if (ze != null) {
                BufferedOutputStream Output_fos2 = new BufferedOutputStream(new FileOutputStream(fileinfiledir));
                try {
                    byte[] buf = new byte[65536];
                    BufferedInputStream bufbr2 = new BufferedInputStream(zf.getInputStream(ze));
                    while (true) {
                        try {
                            int readlen = bufbr2.read(buf);
                            if (readlen < 0) {
                                break;
                            }
                            Output_fos2.write(buf, 0, readlen);
                        } catch (Exception e) {
                            e = e;
                            bufbr = bufbr2;
                            Output_fos = Output_fos2;
                        } catch (Throwable th) {
                            th = th;
                            bufbr = bufbr2;
                            Output_fos = Output_fos2;
                            if (Output_fos != null) {
                                try {
                                    Output_fos.close();
                                    if (bufbr != null) {
                                        try {
                                            bufbr.close();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                            return false;
                                        }
                                    }
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                    if (bufbr == null) {
                                        return false;
                                    }
                                    try {
                                        bufbr.close();
                                        return false;
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                        return false;
                                    }
                                } finally {
                                    if (bufbr != null) {
                                        try {
                                            bufbr.close();
                                        } catch (IOException e5) {
                                            e5.printStackTrace();
                                            return false;
                                        }
                                    }
                                }
                            }
                            throw th;
                        }
                    }
                    if (Output_fos2 != null) {
                        try {
                            Output_fos2.close();
                            if (bufbr2 != null) {
                                try {
                                    bufbr2.close();
                                } catch (IOException e6) {
                                    e6.printStackTrace();
                                    BufferedInputStream bufferedInputStream = bufbr2;
                                    BufferedOutputStream bufferedOutputStream = Output_fos2;
                                    return false;
                                }
                            }
                        } catch (IOException e7) {
                            e7.printStackTrace();
                            if (bufbr2 != null) {
                                try {
                                    bufbr2.close();
                                } catch (IOException e8) {
                                    e8.printStackTrace();
                                    BufferedInputStream bufferedInputStream2 = bufbr2;
                                    BufferedOutputStream bufferedOutputStream2 = Output_fos2;
                                    return false;
                                }
                            }
                            BufferedInputStream bufferedInputStream3 = bufbr2;
                            BufferedOutputStream bufferedOutputStream3 = Output_fos2;
                            return false;
                        } finally {
                            if (bufbr2 != null) {
                                try {
                                    bufbr2.close();
                                } catch (IOException e9) {
                                    e9.printStackTrace();
                                    BufferedInputStream bufferedInputStream4 = bufbr2;
                                    BufferedOutputStream bufferedOutputStream4 = Output_fos2;
                                    return false;
                                }
                            }
                        }
                    }
                    BufferedInputStream bufferedInputStream5 = bufbr2;
                    BufferedOutputStream bufferedOutputStream5 = Output_fos2;
                    return true;
                } catch (Exception e10) {
                    e = e10;
                    Output_fos = Output_fos2;
                    try {
                        e.printStackTrace();
                        if (Output_fos == null) {
                            return false;
                        }
                        try {
                            Output_fos.close();
                            if (bufbr == null) {
                                return false;
                            }
                            try {
                                bufbr.close();
                                return false;
                            } catch (IOException e11) {
                                e11.printStackTrace();
                                return false;
                            }
                        } catch (IOException e12) {
                            e12.printStackTrace();
                            if (bufbr == null) {
                                return false;
                            }
                            try {
                                bufbr.close();
                                return false;
                            } catch (IOException e13) {
                                e13.printStackTrace();
                                return false;
                            }
                        } finally {
                            if (bufbr != null) {
                                try {
                                    bufbr.close();
                                } catch (IOException e14) {
                                    e14.printStackTrace();
                                    return false;
                                }
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (Output_fos != null) {
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    Output_fos = Output_fos2;
                    if (Output_fos != null) {
                    }
                    throw th;
                }
            } else if (Output_fos == null) {
                return false;
            } else {
                try {
                    Output_fos.close();
                    if (bufbr == null) {
                        return false;
                    }
                    try {
                        bufbr.close();
                        return false;
                    } catch (IOException e15) {
                        e15.printStackTrace();
                        return false;
                    }
                } catch (IOException e16) {
                    e16.printStackTrace();
                    if (bufbr == null) {
                        return false;
                    }
                    try {
                        bufbr.close();
                        return false;
                    } catch (IOException e17) {
                        e17.printStackTrace();
                        return false;
                    }
                } finally {
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e18) {
                            e18.printStackTrace();
                            return false;
                        }
                    }
                }
            }
        } catch (Exception e19) {
            e = e19;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x0099 A[SYNTHETIC, Splitter:B:50:0x0099] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x00cf A[SYNTHETIC, Splitter:B:76:0x00cf] */
    public static int Comparetxtinzip(ZipFile apkzf, String filepathinzip, File fileinfiledir) {
        String tmpzipstr;
        BufferedInputStream checkfbr;
        int result;
        BufferedInputStream checkzbr = null;
        BufferedInputStream checkfbr2 = null;
        ZipEntry cookie_entry = apkzf.getEntry(filepathinzip);
        if (cookie_entry == null) {
            try {
                Log.i(TAG, "no this file in zip");
                if (checkzbr != null) {
                    try {
                        checkzbr.close();
                        if (checkfbr2 != null) {
                            try {
                                checkfbr2.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                return ERROR_EXCEPTION;
                            }
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        if (checkfbr2 != null) {
                            try {
                                checkfbr2.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                                return ERROR_EXCEPTION;
                            }
                        }
                        return ERROR_EXCEPTION;
                    } finally {
                        if (checkfbr2 != null) {
                            try {
                                checkfbr2.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                                return ERROR_EXCEPTION;
                            }
                        }
                    }
                }
                return ERROR_FILE_NOT_FOUND_INZIP;
            } catch (Exception e5) {
                e = e5;
                try {
                    e.printStackTrace();
                    if (checkzbr != null) {
                        try {
                            checkzbr.close();
                            if (checkfbr2 != null) {
                                try {
                                    checkfbr2.close();
                                } catch (IOException e6) {
                                    e6.printStackTrace();
                                    return ERROR_EXCEPTION;
                                }
                            }
                        } catch (IOException e7) {
                            e7.printStackTrace();
                            if (checkfbr2 != null) {
                                try {
                                    checkfbr2.close();
                                } catch (IOException e8) {
                                    e8.printStackTrace();
                                    return ERROR_EXCEPTION;
                                }
                            }
                            return ERROR_EXCEPTION;
                        } finally {
                            if (checkfbr2 != null) {
                                try {
                                    checkfbr2.close();
                                } catch (IOException e9) {
                                    e9.printStackTrace();
                                    return ERROR_EXCEPTION;
                                }
                            }
                        }
                    }
                    return ERROR_EXCEPTION;
                } catch (Throwable th) {
                    th = th;
                    if (checkzbr != null) {
                        try {
                            checkzbr.close();
                            if (checkfbr2 != null) {
                                try {
                                    checkfbr2.close();
                                } catch (IOException e10) {
                                    e10.printStackTrace();
                                    return ERROR_EXCEPTION;
                                }
                            }
                        } catch (IOException e11) {
                            e11.printStackTrace();
                            if (checkfbr2 != null) {
                                try {
                                    checkfbr2.close();
                                } catch (IOException e12) {
                                    e12.printStackTrace();
                                    return ERROR_EXCEPTION;
                                }
                            }
                            return ERROR_EXCEPTION;
                        } finally {
                            if (checkfbr2 != null) {
                                try {
                                    checkfbr2.close();
                                } catch (IOException e13) {
                                    e13.printStackTrace();
                                    return ERROR_EXCEPTION;
                                }
                            }
                        }
                    }
                    throw th;
                }
            }
        } else {
            byte[] checkzipbuf = new byte[1024];
            byte[] checkfilebuf = new byte[1024];
            BufferedInputStream checkzbr2 = new BufferedInputStream(apkzf.getInputStream(cookie_entry));
            try {
                tmpzipstr = new String(checkzipbuf).substring(0, checkzbr2.read(checkzipbuf));
                checkfbr = new BufferedInputStream(new FileInputStream(fileinfiledir));
            } catch (Exception e14) {
                e = e14;
                checkzbr = checkzbr2;
                e.printStackTrace();
                if (checkzbr != null) {
                }
                return ERROR_EXCEPTION;
            } catch (Throwable th2) {
                th = th2;
                checkzbr = checkzbr2;
                if (checkzbr != null) {
                }
                throw th;
            }
            try {
                if (new String(checkfilebuf).substring(0, checkfbr.read(checkfilebuf)).equals(tmpzipstr)) {
                    result = 1;
                } else {
                    result = 0;
                }
                if (checkzbr2 != null) {
                    try {
                        checkzbr2.close();
                        if (checkfbr != null) {
                            try {
                                checkfbr.close();
                            } catch (IOException e15) {
                                e15.printStackTrace();
                                BufferedInputStream bufferedInputStream = checkfbr;
                                BufferedInputStream bufferedInputStream2 = checkzbr2;
                                return ERROR_EXCEPTION;
                            }
                        }
                    } catch (IOException e16) {
                        e16.printStackTrace();
                        if (checkfbr != null) {
                            try {
                                checkfbr.close();
                            } catch (IOException e17) {
                                e17.printStackTrace();
                                BufferedInputStream bufferedInputStream3 = checkfbr;
                                BufferedInputStream bufferedInputStream4 = checkzbr2;
                                return ERROR_EXCEPTION;
                            }
                        }
                        BufferedInputStream bufferedInputStream5 = checkfbr;
                        BufferedInputStream bufferedInputStream6 = checkzbr2;
                        return ERROR_EXCEPTION;
                    } finally {
                        if (checkfbr != null) {
                            try {
                                checkfbr.close();
                            } catch (IOException e18) {
                                e18.printStackTrace();
                                BufferedInputStream bufferedInputStream7 = checkfbr;
                                BufferedInputStream bufferedInputStream8 = checkzbr2;
                                return ERROR_EXCEPTION;
                            }
                        }
                    }
                }
                BufferedInputStream bufferedInputStream9 = checkfbr;
                BufferedInputStream bufferedInputStream10 = checkzbr2;
                return result;
            } catch (Exception e19) {
                e = e19;
                checkfbr2 = checkfbr;
                checkzbr = checkzbr2;
            } catch (Throwable th3) {
                th = th3;
                checkfbr2 = checkfbr;
                checkzbr = checkzbr2;
                if (checkzbr != null) {
                }
                throw th;
            }
        }
    }

    public static boolean deleteDir(File file) {
        boolean result = true;
        if (file.isDirectory()) {
            String[] children = file.list();
            for (String file2 : children) {
                if (!deleteDir(new File(file, file2))) {
                    return false;
                }
            }
        }
        if (file.exists()) {
            result = file.delete();
            Log.i("zhrzhang", "delete file " + file + " is " + result);
        }
        return result;
    }

    /* JADX WARNING: Removed duplicated region for block: B:336:0x0ef0 A[SYNTHETIC, Splitter:B:336:0x0ef0] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x02fa  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0374  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x03ee  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0471 A[SYNTHETIC, Splitter:B:75:0x0471] */
    public static int PrepareSecurefiles(Context ctx, ZipFile apkzf) {
        ZipEntry fileUnzip;
        ZipEntry fileUnzip2;
        ZipEntry fileUnzip3;
        RandomAccessFile raf = null;
        FileLock file_lock = null;
        RandomAccessFile raf2 = null;
        String Appfiledir = new StringBuilder(String.valueOf(ctx.getFilesDir().getAbsolutePath())).append("/prodexdir").toString();
        File Appprofiledir = new File(Appfiledir);
        if (!Appprofiledir.isDirectory()) {
            Appprofiledir.mkdir();
        }
        String Cookiefilepath = new StringBuilder(String.valueOf(Appfiledir)).append("/").append(versionname).toString();
        String backupfilepath = new StringBuilder(String.valueOf(Appfiledir)).append("/backUp").toString();
        String firstloadfilepath = new StringBuilder(String.valueOf(Appfiledir)).append("/firstLoad").toString();
        String Cookiefileinzip = "assets/" + versionname;
        String Libnameinapk = "libtosprotection." + CPUABI + ".so";
        try {
            raf = new RandomAccessFile(Cookiefilepath, "rw");
            try {
                raf = raf.getChannel();
                file_lock = raf.lock();
                File Cookiefile = new File(Cookiefilepath);
                try {
                    if (Cookiefile.length() != 0) {
                        int compareResult = Comparetxtinzip(apkzf, Cookiefileinzip, Cookiefile);
                        if (compareResult == 1) {
                            File secureDataFile = new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename5).toString());
                            if (!secureDataFile.exists() || secureDataFile.length() == 0) {
                                SafeUnzipFile(apkzf, "assets/" + securename5, secureDataFile, 0);
                            } else {
                                SafeUnzipFile(apkzf, "assets/" + securename5, secureDataFile, getFileCRC32(secureDataFile));
                            }
                            ZipEntry fileUnzip4 = apkzf.getEntry("assets/" + Libnameinapk);
                            if (fileUnzip4 != null) {
                                if (!isFileValid(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(libname).toString(), fileUnzip4.getSize())) {
                                    UnzipFile(apkzf, "assets/" + Libnameinapk, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(libname).toString()));
                                }
                            }
                            ZipEntry fileUnzip5 = apkzf.getEntry("assets/" + Libnameinapk);
                            if (fileUnzip5 != null) {
                                if (!isFileValid(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename6).toString(), fileUnzip5.getSize())) {
                                    UnzipFile(apkzf, "assets/" + securename6, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename6).toString()));
                                }
                            }
                            ZipEntry fileUnzip6 = apkzf.getEntry("assets/" + Libnameinapk);
                            if (fileUnzip6 != null) {
                                if (!isFileValid(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename7).toString(), fileUnzip6.getSize())) {
                                    UnzipFile(apkzf, "assets/" + securename7, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename7).toString()));
                                }
                            }
                            ZipEntry fileUnzip7 = apkzf.getEntry(Cookiefileinzip);
                            if (fileUnzip7 != null && !isFileValid(Cookiefilepath, fileUnzip7.getSize())) {
                                File file = new File(Cookiefilepath);
                                UnzipFile(apkzf, Cookiefileinzip, file);
                            }
                            if (file_lock != null) {
                                try {
                                    file_lock.release();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                            if (raf != null) {
                                                try {
                                                    raf.close();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                    RandomAccessFile randomAccessFile = raf;
                                                    File file2 = Cookiefile;
                                                    return ERROR_FILE_NOT_FOUND;
                                                }
                                            }
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                            if (raf != null) {
                                                try {
                                                    raf.close();
                                                } catch (IOException e3) {
                                                    e3.printStackTrace();
                                                    RandomAccessFile randomAccessFile2 = raf;
                                                    File file3 = Cookiefile;
                                                    return ERROR_FILE_NOT_FOUND;
                                                }
                                            }
                                            RandomAccessFile randomAccessFile3 = raf;
                                            File file4 = Cookiefile;
                                            return ERROR_FILE_NOT_FOUND;
                                        } finally {
                                            if (raf != null) {
                                                try {
                                                    raf.close();
                                                } catch (IOException e4) {
                                                    e4.printStackTrace();
                                                    RandomAccessFile randomAccessFile4 = raf;
                                                    File file5 = Cookiefile;
                                                    return ERROR_FILE_NOT_FOUND;
                                                }
                                            }
                                        }
                                    }
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                            if (raf != null) {
                                                try {
                                                    raf.close();
                                                } catch (IOException e6) {
                                                    e6.printStackTrace();
                                                    RandomAccessFile randomAccessFile5 = raf;
                                                    File file6 = Cookiefile;
                                                    return ERROR_FILE_NOT_FOUND;
                                                }
                                            }
                                        } catch (IOException e7) {
                                            e7.printStackTrace();
                                            if (raf != null) {
                                                try {
                                                    raf.close();
                                                } catch (IOException e8) {
                                                    e8.printStackTrace();
                                                    RandomAccessFile randomAccessFile6 = raf;
                                                    File file7 = Cookiefile;
                                                    return ERROR_FILE_NOT_FOUND;
                                                }
                                            }
                                            RandomAccessFile randomAccessFile7 = raf;
                                            File file8 = Cookiefile;
                                            return ERROR_FILE_NOT_FOUND;
                                        } finally {
                                            if (raf != null) {
                                                try {
                                                    raf.close();
                                                } catch (IOException e9) {
                                                    e9.printStackTrace();
                                                    RandomAccessFile randomAccessFile8 = raf;
                                                    File file9 = Cookiefile;
                                                    return ERROR_FILE_NOT_FOUND;
                                                }
                                            }
                                        }
                                    }
                                    RandomAccessFile randomAccessFile9 = raf;
                                    File file10 = Cookiefile;
                                    return ERROR_FILE_NOT_FOUND;
                                } finally {
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                            if (raf != null) {
                                                try {
                                                    raf.close();
                                                } catch (IOException e10) {
                                                    e10.printStackTrace();
                                                    RandomAccessFile randomAccessFile10 = raf;
                                                    File file11 = Cookiefile;
                                                    return ERROR_FILE_NOT_FOUND;
                                                }
                                            }
                                        } catch (IOException e11) {
                                            e11.printStackTrace();
                                            if (raf != null) {
                                                try {
                                                    raf.close();
                                                } catch (IOException e12) {
                                                    e12.printStackTrace();
                                                    RandomAccessFile randomAccessFile11 = raf;
                                                    File file12 = Cookiefile;
                                                    return ERROR_FILE_NOT_FOUND;
                                                }
                                            }
                                            RandomAccessFile randomAccessFile12 = raf;
                                            File file13 = Cookiefile;
                                            return ERROR_FILE_NOT_FOUND;
                                        } finally {
                                            if (raf != null) {
                                                try {
                                                    raf.close();
                                                } catch (IOException e13) {
                                                    e13.printStackTrace();
                                                    RandomAccessFile randomAccessFile13 = raf;
                                                    File file14 = Cookiefile;
                                                    return ERROR_FILE_NOT_FOUND;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            RandomAccessFile randomAccessFile14 = raf;
                            File file15 = Cookiefile;
                            return 2;
                        } else if (compareResult != 0) {
                            Process.killProcess(Process.myPid());
                            System.exit(0);
                        }
                    }
                    DeleteFile(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(libname).toString());
                    DeleteFile(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename6).toString());
                    int file_count = 0;
                    while (file_count < MAX_DEX_NUM) {
                        int deletedexresult = DeleteFile(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(CreatenewFileName(securename0, ".", "_" + file_count)).toString());
                        int deletejarresult = DeleteFile(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(CreatenewFileName(securename1, ".", "_" + file_count)).toString());
                        int deleteodexresult = DeleteFile(new StringBuilder(String.valueOf(Appfiledir)).append("/").append("odexdir").append("/").append(CreatenewFileName(securename0, ".", "_" + file_count)).toString());
                        int deleteflagresult = DeleteFile(new StringBuilder(String.valueOf(Appfiledir)).append("/").append("odexdir").append("/").append(CreatenewFileName(securename8, ".", "_" + file_count)).toString());
                        DeleteFile(new StringBuilder(String.valueOf(Appfiledir)).append("/").append("oat/arm").append("/").append(CreatenewFileName(securename8, ".", "_" + file_count)).toString());
                        if (ERROR_FILE_NOT_FOUND == deletedexresult && ERROR_FILE_NOT_FOUND == deletejarresult && ERROR_FILE_NOT_FOUND == deleteodexresult) {
                            break;
                        }
                        if (ERROR_EXCEPTION == deletedexresult || ERROR_EXCEPTION == deletejarresult || ERROR_EXCEPTION == deleteodexresult) {
                            Process.killProcess(Process.myPid());
                            System.exit(0);
                        }
                        file_count++;
                    }
                    DeleteFile(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename9).toString());
                    DeleteFile(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename5).toString());
                    UnzipFile(apkzf, "assets/" + securename5, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename5).toString()));
                    UnzipFile(apkzf, "assets/" + libname, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(libname).toString()));
                    UnzipFile(apkzf, "assets/" + securename6, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename6).toString()));
                    UnzipFile(apkzf, "assets/" + securename7, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename7).toString()));
                    File file16 = new File(Cookiefilepath);
                    UnzipFile(apkzf, Cookiefileinzip, file16);
                    for (int file_count2 = 0; file_count2 < file_count; file_count2++) {
                        int deletedexresult2 = DeleteFile(new StringBuilder(String.valueOf(backupfilepath)).append("/").append(CreatenewFileName(securename0, ".", "_" + file_count2)).toString());
                        int deletejarresult2 = DeleteFile(new StringBuilder(String.valueOf(backupfilepath)).append("/").append(CreatenewFileName(securename1, ".", "_" + file_count2)).toString());
                        int deleteodexresult2 = DeleteFile(new StringBuilder(String.valueOf(backupfilepath)).append("/").append("odexdir").append("/").append(CreatenewFileName(securename0, ".", "_" + file_count2)).toString());
                        int deleteflagresult2 = DeleteFile(new StringBuilder(String.valueOf(backupfilepath)).append("/").append("odexdir").append("/").append(CreatenewFileName(securename8, ".", "_" + file_count2)).toString());
                        DeleteFile(new StringBuilder(String.valueOf(backupfilepath)).append("/").append("oat/arm").append("/").append(CreatenewFileName(securename8, ".", "_" + file_count2)).toString());
                        if (ERROR_FILE_NOT_FOUND == deletedexresult2 && ERROR_FILE_NOT_FOUND == deletejarresult2 && ERROR_FILE_NOT_FOUND == deleteodexresult2) {
                            break;
                        }
                        if (ERROR_EXCEPTION == deletedexresult2 || ERROR_EXCEPTION == deletejarresult2 || ERROR_EXCEPTION == deleteodexresult2) {
                            Process.killProcess(Process.myPid());
                            System.exit(0);
                        }
                    }
                    for (int file_count3 = 0; file_count3 < file_count; file_count3++) {
                        int deletedexresult3 = DeleteFile(new StringBuilder(String.valueOf(firstloadfilepath)).append("/").append(CreatenewFileName(securename0, ".", "_" + file_count3)).toString());
                        int deletejarresult3 = DeleteFile(new StringBuilder(String.valueOf(firstloadfilepath)).append("/").append(CreatenewFileName(securename1, ".", "_" + file_count3)).toString());
                        int deleteodexresult3 = DeleteFile(new StringBuilder(String.valueOf(firstloadfilepath)).append("/").append("odexdir").append("/").append(CreatenewFileName(securename0, ".", "_" + file_count3)).toString());
                        int deleteflagresult3 = DeleteFile(new StringBuilder(String.valueOf(firstloadfilepath)).append("/").append("odexdir").append("/").append(CreatenewFileName(securename8, ".", "_" + file_count3)).toString());
                        DeleteFile(new StringBuilder(String.valueOf(firstloadfilepath)).append("/").append("oat/arm").append("/").append(CreatenewFileName(securename8, ".", "_" + file_count3)).toString());
                        if (ERROR_FILE_NOT_FOUND == deletedexresult3 && ERROR_FILE_NOT_FOUND == deletejarresult3 && ERROR_FILE_NOT_FOUND == deleteodexresult3) {
                            break;
                        }
                        if (ERROR_EXCEPTION == deletedexresult3 || ERROR_EXCEPTION == deletejarresult3 || ERROR_EXCEPTION == deleteodexresult3) {
                            Process.killProcess(Process.myPid());
                            System.exit(0);
                        }
                    }
                    ZipEntry fileUnzip8 = apkzf.getEntry("assets/" + Libnameinapk);
                    if (fileUnzip8 != null) {
                        if (!isFileValid(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(libname).toString(), fileUnzip8.getSize())) {
                            UnzipFile(apkzf, "assets/" + Libnameinapk, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(libname).toString()));
                        }
                    }
                    ZipEntry fileUnzip9 = apkzf.getEntry("assets/" + Libnameinapk);
                    if (fileUnzip9 != null) {
                        if (!isFileValid(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename6).toString(), fileUnzip9.getSize())) {
                            UnzipFile(apkzf, "assets/" + securename6, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename6).toString()));
                        }
                    }
                    ZipEntry fileUnzip10 = apkzf.getEntry("assets/" + Libnameinapk);
                    if (fileUnzip10 != null) {
                        if (!isFileValid(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename7).toString(), fileUnzip10.getSize())) {
                            UnzipFile(apkzf, "assets/" + securename7, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename7).toString()));
                        }
                    }
                    ZipEntry fileUnzip11 = apkzf.getEntry(Cookiefileinzip);
                    if (fileUnzip11 != null && !isFileValid(Cookiefilepath, fileUnzip11.getSize())) {
                        File file17 = new File(Cookiefilepath);
                        UnzipFile(apkzf, Cookiefileinzip, file17);
                    }
                    if (file_lock != null) {
                        try {
                            file_lock.release();
                            if (raf != null) {
                                try {
                                    raf.close();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e14) {
                                            e14.printStackTrace();
                                            RandomAccessFile randomAccessFile15 = raf;
                                            File file18 = Cookiefile;
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                } catch (IOException e15) {
                                    e15.printStackTrace();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e16) {
                                            e16.printStackTrace();
                                            RandomAccessFile randomAccessFile16 = raf;
                                            File file19 = Cookiefile;
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                    RandomAccessFile randomAccessFile17 = raf;
                                    File file20 = Cookiefile;
                                    return ERROR_FILE_NOT_FOUND;
                                } finally {
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e17) {
                                            e17.printStackTrace();
                                            RandomAccessFile randomAccessFile18 = raf;
                                            File file21 = Cookiefile;
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                }
                            }
                        } catch (IOException e18) {
                            e18.printStackTrace();
                            if (raf != null) {
                                try {
                                    raf.close();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e19) {
                                            e19.printStackTrace();
                                            RandomAccessFile randomAccessFile19 = raf;
                                            File file22 = Cookiefile;
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                } catch (IOException e20) {
                                    e20.printStackTrace();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e21) {
                                            e21.printStackTrace();
                                            RandomAccessFile randomAccessFile20 = raf;
                                            File file23 = Cookiefile;
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                    RandomAccessFile randomAccessFile21 = raf;
                                    File file24 = Cookiefile;
                                    return ERROR_FILE_NOT_FOUND;
                                } finally {
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e22) {
                                            e22.printStackTrace();
                                            RandomAccessFile randomAccessFile22 = raf;
                                            File file25 = Cookiefile;
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                }
                            }
                            RandomAccessFile randomAccessFile23 = raf;
                            File file26 = Cookiefile;
                            return ERROR_FILE_NOT_FOUND;
                        } finally {
                            if (raf != null) {
                                try {
                                    raf.close();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e23) {
                                            e23.printStackTrace();
                                            RandomAccessFile randomAccessFile24 = raf;
                                            File file27 = Cookiefile;
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                } catch (IOException e24) {
                                    e24.printStackTrace();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e25) {
                                            e25.printStackTrace();
                                            RandomAccessFile randomAccessFile25 = raf;
                                            File file28 = Cookiefile;
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                    RandomAccessFile randomAccessFile26 = raf;
                                    File file29 = Cookiefile;
                                    return ERROR_FILE_NOT_FOUND;
                                } finally {
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e26) {
                                            e26.printStackTrace();
                                            RandomAccessFile randomAccessFile27 = raf;
                                            File file30 = Cookiefile;
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    RandomAccessFile randomAccessFile28 = raf;
                    File file31 = Cookiefile;
                    return 0;
                } catch (Exception e27) {
                    e = e27;
                    raf2 = raf;
                    File file32 = Cookiefile;
                } catch (Throwable th) {
                    th = th;
                    raf2 = raf;
                    File file33 = Cookiefile;
                    ZipEntry fileUnzip12 = apkzf.getEntry("assets/" + Libnameinapk);
                    if (fileUnzip12 != null && !isFileValid(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(libname).toString(), fileUnzip12.getSize())) {
                        UnzipFile(apkzf, "assets/" + Libnameinapk, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(libname).toString()));
                    }
                    ZipEntry fileUnzip13 = apkzf.getEntry("assets/" + Libnameinapk);
                    if (fileUnzip13 != null && !isFileValid(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename6).toString(), fileUnzip13.getSize())) {
                        UnzipFile(apkzf, "assets/" + securename6, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename6).toString()));
                    }
                    ZipEntry fileUnzip14 = apkzf.getEntry("assets/" + Libnameinapk);
                    if (fileUnzip14 != null && !isFileValid(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename7).toString(), fileUnzip14.getSize())) {
                        UnzipFile(apkzf, "assets/" + securename7, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename7).toString()));
                    }
                    ZipEntry fileUnzip15 = apkzf.getEntry(Cookiefileinzip);
                    if (fileUnzip15 != null && !isFileValid(Cookiefilepath, fileUnzip15.getSize())) {
                        File file34 = new File(Cookiefilepath);
                        UnzipFile(apkzf, Cookiefileinzip, file34);
                    }
                    if (file_lock != null) {
                        try {
                            file_lock.release();
                            if (raf != null) {
                                try {
                                    raf.close();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e28) {
                                            e28.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                } catch (IOException e29) {
                                    e29.printStackTrace();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e30) {
                                            e30.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                    return ERROR_FILE_NOT_FOUND;
                                } finally {
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e31) {
                                            e31.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                }
                            }
                        } catch (IOException e32) {
                            e32.printStackTrace();
                            if (raf != null) {
                                try {
                                    raf.close();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e33) {
                                            e33.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                } catch (IOException e34) {
                                    e34.printStackTrace();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e35) {
                                            e35.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                    return ERROR_FILE_NOT_FOUND;
                                } finally {
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e36) {
                                            e36.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                }
                            }
                            return ERROR_FILE_NOT_FOUND;
                        } finally {
                            if (raf != null) {
                                try {
                                    raf.close();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e37) {
                                            e37.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                } catch (IOException e38) {
                                    e38.printStackTrace();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e39) {
                                            e39.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                    return ERROR_FILE_NOT_FOUND;
                                } finally {
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e40) {
                                            e40.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    throw th;
                }
            } catch (Exception e41) {
                e = e41;
                raf2 = raf;
                try {
                    e.printStackTrace();
                    fileUnzip = apkzf.getEntry("assets/" + Libnameinapk);
                    if (fileUnzip != null) {
                        if (!isFileValid(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(libname).toString(), fileUnzip.getSize())) {
                            UnzipFile(apkzf, "assets/" + Libnameinapk, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(libname).toString()));
                        }
                    }
                    fileUnzip2 = apkzf.getEntry("assets/" + Libnameinapk);
                    if (fileUnzip2 != null) {
                        if (!isFileValid(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename6).toString(), fileUnzip2.getSize())) {
                            UnzipFile(apkzf, "assets/" + securename6, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename6).toString()));
                        }
                    }
                    fileUnzip3 = apkzf.getEntry("assets/" + Libnameinapk);
                    if (fileUnzip3 != null) {
                        if (!isFileValid(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename7).toString(), fileUnzip3.getSize())) {
                            UnzipFile(apkzf, "assets/" + securename7, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename7).toString()));
                        }
                    }
                    ZipEntry fileUnzip16 = apkzf.getEntry(Cookiefileinzip);
                    if (fileUnzip16 != null && !isFileValid(Cookiefilepath, fileUnzip16.getSize())) {
                        File file35 = new File(Cookiefilepath);
                        UnzipFile(apkzf, Cookiefileinzip, file35);
                    }
                    if (file_lock != null) {
                        try {
                            file_lock.release();
                            if (raf != null) {
                                try {
                                    raf.close();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e42) {
                                            e42.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                } catch (IOException e43) {
                                    e43.printStackTrace();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e44) {
                                            e44.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                    return ERROR_FILE_NOT_FOUND;
                                } finally {
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e45) {
                                            e45.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                }
                            }
                        } catch (IOException e46) {
                            e46.printStackTrace();
                            if (raf != null) {
                                try {
                                    raf.close();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e47) {
                                            e47.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                } catch (IOException e48) {
                                    e48.printStackTrace();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e49) {
                                            e49.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                    return ERROR_FILE_NOT_FOUND;
                                } finally {
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e50) {
                                            e50.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                }
                            }
                            return ERROR_FILE_NOT_FOUND;
                        } finally {
                            if (raf != null) {
                                try {
                                    raf.close();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e51) {
                                            e51.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                } catch (IOException e52) {
                                    e52.printStackTrace();
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e53) {
                                            e53.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                    return ERROR_FILE_NOT_FOUND;
                                } finally {
                                    if (raf != null) {
                                        try {
                                            raf.close();
                                        } catch (IOException e54) {
                                            e54.printStackTrace();
                                            return ERROR_FILE_NOT_FOUND;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return ERROR_FILE_NOT_FOUND;
                } catch (Throwable th2) {
                    th = th2;
                    ZipEntry fileUnzip122 = apkzf.getEntry("assets/" + Libnameinapk);
                    UnzipFile(apkzf, "assets/" + Libnameinapk, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(libname).toString()));
                    ZipEntry fileUnzip132 = apkzf.getEntry("assets/" + Libnameinapk);
                    UnzipFile(apkzf, "assets/" + securename6, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename6).toString()));
                    ZipEntry fileUnzip142 = apkzf.getEntry("assets/" + Libnameinapk);
                    UnzipFile(apkzf, "assets/" + securename7, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename7).toString()));
                    ZipEntry fileUnzip152 = apkzf.getEntry(Cookiefileinzip);
                    File file342 = new File(Cookiefilepath);
                    UnzipFile(apkzf, Cookiefileinzip, file342);
                    if (file_lock != null) {
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                raf2 = raf;
                ZipEntry fileUnzip1222 = apkzf.getEntry("assets/" + Libnameinapk);
                UnzipFile(apkzf, "assets/" + Libnameinapk, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(libname).toString()));
                ZipEntry fileUnzip1322 = apkzf.getEntry("assets/" + Libnameinapk);
                UnzipFile(apkzf, "assets/" + securename6, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename6).toString()));
                ZipEntry fileUnzip1422 = apkzf.getEntry("assets/" + Libnameinapk);
                UnzipFile(apkzf, "assets/" + securename7, new File(new StringBuilder(String.valueOf(Appfiledir)).append("/").append(securename7).toString()));
                ZipEntry fileUnzip1522 = apkzf.getEntry(Cookiefileinzip);
                File file3422 = new File(Cookiefilepath);
                UnzipFile(apkzf, Cookiefileinzip, file3422);
                if (file_lock != null) {
                }
                throw th;
            }
        } catch (Exception e55) {
            e = e55;
            e.printStackTrace();
            fileUnzip = apkzf.getEntry("assets/" + Libnameinapk);
            if (fileUnzip != null) {
            }
            fileUnzip2 = apkzf.getEntry("assets/" + Libnameinapk);
            if (fileUnzip2 != null) {
            }
            fileUnzip3 = apkzf.getEntry("assets/" + Libnameinapk);
            if (fileUnzip3 != null) {
            }
            ZipEntry fileUnzip162 = apkzf.getEntry(Cookiefileinzip);
            File file352 = new File(Cookiefilepath);
            UnzipFile(apkzf, Cookiefileinzip, file352);
            if (file_lock != null) {
            }
            return ERROR_FILE_NOT_FOUND;
        }
    }

    private static boolean isFileValid(String path, long length) {
        File tmpfile = new File(path);
        if (!tmpfile.exists() || tmpfile.length() != length) {
            return false;
        }
        return true;
    }

    public static void getCPUABI() {
        if (CPUABI == null) {
            try {
                String os_cpuabi = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop ro.product.cpu.abi").getInputStream())).readLine();
                if (os_cpuabi.contains("x86")) {
                    CPUABI = "x86";
                } else if (os_cpuabi.contains("armeabi-v7a") || os_cpuabi.contains("arm64-v8a")) {
                    CPUABI = "armeabi-v7a";
                } else {
                    CPUABI = "armeabi";
                }
            } catch (Exception e) {
                CPUABI = "armeabi";
            }
        }
    }

    public static long getCRC32(File fileUri) {
        CheckedInputStream checkedinputstream;
        CRC32 crc32 = new CRC32();
        long crc = 0;
        try {
            BufferedInputStream bufbr = new BufferedInputStream(new FileInputStream(fileUri));
            try {
                checkedinputstream = new CheckedInputStream(bufbr, crc32);
            } catch (FileNotFoundException e) {
                e = e;
                BufferedInputStream bufferedInputStream = bufbr;
                e.printStackTrace();
                return crc;
            } catch (IOException e2) {
                e = e2;
                BufferedInputStream bufferedInputStream2 = bufbr;
                e.printStackTrace();
                return crc;
            }
            try {
                do {
                } while (checkedinputstream.read(new byte[65536]) >= 0);
                crc = checkedinputstream.getChecksum().getValue();
                checkedinputstream.close();
                CheckedInputStream checkedInputStream = checkedinputstream;
                BufferedInputStream bufferedInputStream3 = bufbr;
            } catch (FileNotFoundException e3) {
                e = e3;
                CheckedInputStream checkedInputStream2 = checkedinputstream;
                BufferedInputStream bufferedInputStream4 = bufbr;
                e.printStackTrace();
                return crc;
            } catch (IOException e4) {
                e = e4;
                CheckedInputStream checkedInputStream3 = checkedinputstream;
                BufferedInputStream bufferedInputStream5 = bufbr;
                e.printStackTrace();
                return crc;
            }
        } catch (FileNotFoundException e5) {
            e = e5;
            e.printStackTrace();
            return crc;
        } catch (IOException e6) {
            e = e6;
            e.printStackTrace();
            return crc;
        }
        return crc;
    }

    public static String CreatenewFileName(String Oldfilename, String SplitString, String InsertString) {
        int index = Oldfilename.lastIndexOf(SplitString);
        if (index >= 0) {
            return Oldfilename.substring(0, index) + InsertString + Oldfilename.substring(index, Oldfilename.length());
        }
        return null;
    }

    public static boolean SafeUnzipFile(ZipFile zf, String filepathinzip, File fileinfiledir) {
        return SafeUnzipFile(zf, filepathinzip, fileinfiledir, 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:124:0x0101 A[SYNTHETIC, Splitter:B:124:0x0101] */
    public static boolean SafeUnzipFile(ZipFile zf, String filepathinzip, File fileinfiledir, long crc) {
        BufferedOutputStream Output_fos = null;
        BufferedInputStream bufbr = null;
        try {
            ZipEntry ze = zf.getEntry(filepathinzip);
            if (ze == null) {
                if (Output_fos != null) {
                    try {
                        Output_fos.close();
                        if (bufbr != null) {
                            try {
                                bufbr.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                return false;
                            }
                        }
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        if (bufbr != null) {
                            try {
                                bufbr.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                                return false;
                            }
                        }
                        return false;
                    } finally {
                        if (bufbr != null) {
                            try {
                                bufbr.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                                return false;
                            }
                        }
                    }
                }
                return false;
            }
            if (crc != 0) {
                if (ze.getCrc() == crc) {
                    if (Output_fos != null) {
                        try {
                            Output_fos.close();
                            if (bufbr != null) {
                                try {
                                    bufbr.close();
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                    return false;
                                }
                            }
                        } catch (IOException e6) {
                            e6.printStackTrace();
                            if (bufbr != null) {
                                try {
                                    bufbr.close();
                                } catch (IOException e7) {
                                    e7.printStackTrace();
                                    return false;
                                }
                            }
                            return false;
                        } finally {
                            if (bufbr != null) {
                                try {
                                    bufbr.close();
                                } catch (IOException e8) {
                                    e8.printStackTrace();
                                    return false;
                                }
                            }
                        }
                    }
                    return true;
                }
            }
            byte[] buf = UnzipFile(zf, ze);
            if (1 != 0) {
                BufferedOutputStream Output_fos2 = new BufferedOutputStream(new FileOutputStream(fileinfiledir));
                try {
                    Output_fos2.write(buf, 0, buf.length);
                    Output_fos = Output_fos2;
                } catch (Exception e9) {
                    e = e9;
                    Output_fos = Output_fos2;
                    try {
                        e.printStackTrace();
                        if (Output_fos != null) {
                            try {
                                Output_fos.close();
                                if (bufbr != null) {
                                    try {
                                        bufbr.close();
                                    } catch (IOException e10) {
                                        e10.printStackTrace();
                                        return false;
                                    }
                                }
                            } catch (IOException e11) {
                                e11.printStackTrace();
                                if (bufbr != null) {
                                    try {
                                        bufbr.close();
                                    } catch (IOException e12) {
                                        e12.printStackTrace();
                                        return false;
                                    }
                                }
                                return false;
                            } finally {
                                if (bufbr != null) {
                                    try {
                                        bufbr.close();
                                    } catch (IOException e13) {
                                        e13.printStackTrace();
                                        return false;
                                    }
                                }
                            }
                        }
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        if (Output_fos != null) {
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    Output_fos = Output_fos2;
                    if (Output_fos != null) {
                        try {
                            Output_fos.close();
                            if (bufbr != null) {
                                try {
                                    bufbr.close();
                                } catch (IOException e14) {
                                    e14.printStackTrace();
                                    return false;
                                }
                            }
                        } catch (IOException e15) {
                            e15.printStackTrace();
                            if (bufbr != null) {
                                try {
                                    bufbr.close();
                                } catch (IOException e16) {
                                    e16.printStackTrace();
                                    return false;
                                }
                            }
                            return false;
                        } finally {
                            if (bufbr != null) {
                                try {
                                    bufbr.close();
                                } catch (IOException e17) {
                                    e17.printStackTrace();
                                    return false;
                                }
                            }
                        }
                    }
                    throw th;
                }
            }
            if (Output_fos != null) {
                try {
                    Output_fos.close();
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e18) {
                            e18.printStackTrace();
                            return false;
                        }
                    }
                } catch (IOException e19) {
                    e19.printStackTrace();
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e20) {
                            e20.printStackTrace();
                            return false;
                        }
                    }
                    return false;
                } finally {
                    if (bufbr != null) {
                        try {
                            bufbr.close();
                        } catch (IOException e21) {
                            e21.printStackTrace();
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (Exception e22) {
            e = e22;
        }
    }

    public static byte[] UnzipFile(ZipFile zf, ZipEntry ze) throws IOException {
        byte[] buf = new byte[((int) ze.getSize())];
        BufferedInputStream bufbr = new BufferedInputStream(zf.getInputStream(ze));
        int totallen = 0;
        do {
            int readlen = bufbr.read(buf, totallen, ((int) ze.getSize()) - totallen);
            if (readlen < 0) {
                break;
            }
            totallen += readlen;
        } while (((long) totallen) != ze.getSize());
        if (totallen == ((int) ze.getSize())) {
            return buf;
        }
        throw new IOException("incorrect zip file size");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0036 A[SYNTHETIC, Splitter:B:20:0x0036] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0045 A[SYNTHETIC, Splitter:B:28:0x0045] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0051 A[SYNTHETIC, Splitter:B:34:0x0051] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:25:0x0040=Splitter:B:25:0x0040, B:17:0x0031=Splitter:B:17:0x0031} */
    private static long getFileCRC32(File file) {
        long result = -1;
        byte[] filebuf = new byte[((int) file.length())];
        BufferedInputStream filebr = null;
        CRC32 crc32 = new CRC32();
        try {
            BufferedInputStream filebr2 = new BufferedInputStream(new FileInputStream(file));
            int totallen = 0;
            while (true) {
                try {
                    int readlen = filebr2.read(filebuf);
                    if (readlen < 0) {
                        break;
                    }
                    crc32.update(filebuf);
                    totallen += readlen;
                } catch (FileNotFoundException e) {
                    e1 = e;
                    filebr = filebr2;
                    try {
                        e1.printStackTrace();
                        if (filebr != null) {
                            try {
                                filebr.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        return result;
                    } catch (Throwable th) {
                        th = th;
                        if (filebr != null) {
                        }
                        throw th;
                    }
                } catch (IOException e3) {
                    e = e3;
                    filebr = filebr2;
                    e.printStackTrace();
                    if (filebr != null) {
                        try {
                            filebr.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    return result;
                } catch (Throwable th2) {
                    th = th2;
                    filebr = filebr2;
                    if (filebr != null) {
                        try {
                            filebr.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    throw th;
                }
            }
            result = crc32.getValue();
            if (filebr2 != null) {
                try {
                    filebr2.close();
                    BufferedInputStream bufferedInputStream = filebr2;
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
                return result;
            }
            BufferedInputStream bufferedInputStream2 = filebr2;
        } catch (FileNotFoundException e7) {
            e1 = e7;
            e1.printStackTrace();
            if (filebr != null) {
            }
            return result;
        } catch (IOException e8) {
            e = e8;
            e.printStackTrace();
            if (filebr != null) {
            }
            return result;
        }
        return result;
    }
}
