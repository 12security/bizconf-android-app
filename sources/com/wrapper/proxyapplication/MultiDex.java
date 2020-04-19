package com.wrapper.proxyapplication;

import android.os.Build.VERSION;
import android.util.Log;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.ZipFile;

public final class MultiDex {
    static final String TAG = "MultiDex";
    static int hasInjected = 0;

    private static final class V19 {
        private V19() {
        }

        /* access modifiers changed from: private */
        public static ArrayList<Object> install(ClassLoader loader, Field pathListField, List<File> additionalClassPathEntries, File optimizedDirectory, boolean ifresetcookie, boolean ifreturndexfile) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
            IOException[] dexElementsSuppressedExceptions;
            Object dexPathList = pathListField.get(loader);
            ArrayList<IOException> suppressedExceptions = new ArrayList<>();
            ArrayList arrayList = new ArrayList(additionalClassPathEntries);
            Object[] DexElementslist = makeDexElements(dexPathList, arrayList, optimizedDirectory, suppressedExceptions);
            if (DexElementslist == null || DexElementslist.length != additionalClassPathEntries.size()) {
                return null;
            }
            ArrayList<Object> objcookieordexfilelist = new ArrayList<>();
            int length = DexElementslist.length;
            for (int i = 0; i < length; i++) {
                Object DexElements = DexElementslist[i];
                Object objdexfile = MultiDex.findField(DexElements, "dexFile").get(DexElements);
                Field cookieField = MultiDex.findField(objdexfile, "mCookie");
                Log.w(MultiDex.TAG, "cookieField type is " + cookieField.getType().getName());
                if (cookieField.getType().getName().equals("int")) {
                    Log.w(MultiDex.TAG, "cookie version is android  4.X");
                    objcookieordexfilelist.add(Integer.valueOf(cookieField.getInt(objdexfile)));
                } else if (cookieField.getType().getName().equals("long")) {
                    Log.w(MultiDex.TAG, "cookie version is android 5.0");
                    Log.w(MultiDex.TAG, "cookieField is " + cookieField.getLong(objdexfile));
                    objcookieordexfilelist.add(Long.valueOf(cookieField.getLong(objdexfile)));
                    if (ifresetcookie) {
                        cookieField.setLong(objdexfile, 0);
                    }
                } else {
                    Log.w(MultiDex.TAG, "cookie version is android 6.0");
                    Log.w(MultiDex.TAG, "cookieField is " + cookieField.get(objdexfile));
                    if (ifreturndexfile) {
                        objcookieordexfilelist.add(objdexfile);
                    }
                    objcookieordexfilelist.add(cookieField.get(objdexfile));
                    if (ifresetcookie) {
                        cookieField.set(objdexfile, null);
                    }
                }
            }
            MultiDex.expandFieldArray(dexPathList, "dexElements", DexElementslist);
            if (suppressedExceptions.size() > 0) {
                Iterator it = suppressedExceptions.iterator();
                while (it.hasNext()) {
                    Log.w(MultiDex.TAG, "Exception in makeDexElement", (IOException) it.next());
                }
                Field suppressedExceptionsField = MultiDex.findField(dexPathList, "dexElementsSuppressedExceptions");
                IOException[] dexElementsSuppressedExceptions2 = (IOException[]) suppressedExceptionsField.get(dexPathList);
                if (dexElementsSuppressedExceptions2 == null) {
                    dexElementsSuppressedExceptions = (IOException[]) suppressedExceptions.toArray(new IOException[suppressedExceptions.size()]);
                } else {
                    IOException[] combined = new IOException[(suppressedExceptions.size() + dexElementsSuppressedExceptions2.length)];
                    suppressedExceptions.toArray(combined);
                    System.arraycopy(dexElementsSuppressedExceptions2, 0, combined, suppressedExceptions.size(), dexElementsSuppressedExceptions2.length);
                    dexElementsSuppressedExceptions = combined;
                }
                suppressedExceptionsField.set(dexPathList, dexElementsSuppressedExceptions);
            }
            return objcookieordexfilelist;
        }

        private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory, ArrayList<IOException> suppressedExceptions) {
            try {
                Log.w(MultiDex.TAG, "try  makePathElements");
                try {
                    return (Object[]) MultiDex.findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class).invoke(dexPathList, new Object[]{files, optimizedDirectory});
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e2) {
                    e2.printStackTrace();
                } catch (InvocationTargetException e3) {
                    e3.printStackTrace();
                } catch (RuntimeException e4) {
                    e4.printStackTrace();
                }
            } catch (NoSuchMethodException e5) {
                Log.w(MultiDex.TAG, "not old makeDexElements,try new makeDexElements");
                try {
                    try {
                        return (Object[]) MultiDex.findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class, ArrayList.class).invoke(dexPathList, new Object[]{files, optimizedDirectory, suppressedExceptions});
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (IllegalArgumentException e12) {
                        e12.printStackTrace();
                    } catch (InvocationTargetException e13) {
                        e13.printStackTrace();
                    } catch (RuntimeException e14) {
                        e14.printStackTrace();
                    }
                } catch (NoSuchMethodException e15) {
                    Log.w(MultiDex.TAG, "not makeDexElements,try  makePathElements");
                    try {
                    } catch (NoSuchMethodException e22) {
                        Log.w(MultiDex.TAG, "Strange!no Elements,a new or modified android version ");
                        e22.printStackTrace();
                    } catch (RuntimeException e23) {
                        e23.printStackTrace();
                    }
                    try {
                        return (Object[]) MultiDex.findMethod(dexPathList, "makePathElements", List.class, File.class, List.class).invoke(dexPathList, new Object[]{files, optimizedDirectory, suppressedExceptions});
                    } catch (IllegalAccessException e24) {
                        e24.printStackTrace();
                        e15.printStackTrace();
                        e5.printStackTrace();
                        return null;
                    } catch (IllegalArgumentException e25) {
                        e25.printStackTrace();
                        e15.printStackTrace();
                        e5.printStackTrace();
                        return null;
                    } catch (InvocationTargetException e26) {
                        e26.printStackTrace();
                        e15.printStackTrace();
                        e5.printStackTrace();
                        return null;
                    }
                }
            }
        }
    }

    private static final class V4 {
        private V4() {
        }

        /* access modifiers changed from: private */
        public static ArrayList<Object> install(ClassLoader loader, Field pathListField, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, IOException {
            int extraSize = additionalClassPathEntries.size();
            StringBuilder path = new StringBuilder((String) pathListField.get(loader));
            String[] extraPaths = new String[extraSize];
            File[] extraFiles = new File[extraSize];
            ZipFile[] extraZips = new ZipFile[extraSize];
            DexFile[] extraDexs = new DexFile[extraSize];
            ArrayList<Object> objcookielist = new ArrayList<>();
            Log.w(MultiDex.TAG, "v4 inject");
            ListIterator<File> iterator = additionalClassPathEntries.listIterator();
            while (iterator.hasNext()) {
                File additionalEntry = (File) iterator.next();
                String entryPath = additionalEntry.getAbsolutePath();
                String odexdirPath = optimizedDirectory.getAbsolutePath();
                String odexprefix = MultiDex.getprefixname(entryPath);
                path.append(':').append(entryPath);
                int index = iterator.previousIndex();
                extraPaths[index] = entryPath;
                extraFiles[index] = additionalEntry;
                ZipFile zipFile = new ZipFile(additionalEntry);
                extraZips[index] = zipFile;
                extraDexs[index] = DexFile.loadDex(entryPath, new StringBuilder(String.valueOf(odexdirPath)).append("/").append(odexprefix).append(".dex").toString(), 0);
                objcookielist.add(Integer.valueOf(MultiDex.findField(extraDexs[index], "mCookie").getInt(extraDexs[index])));
            }
            pathListField.set(loader, path.toString());
            MultiDex.expandFieldArray(loader, "mPaths", extraPaths);
            MultiDex.expandFieldArray(loader, "mFiles", extraFiles);
            MultiDex.expandFieldArray(loader, "mZips", extraZips);
            MultiDex.expandFieldArray(loader, "mDexs", extraDexs);
            if (objcookielist.size() == 0) {
                return null;
            }
            return objcookielist;
        }
    }

    private MultiDex() {
    }

    private static ArrayList<File> splitPaths(String searchPath) {
        ArrayList<File> result = new ArrayList<>();
        if (searchPath != null) {
            for (String path : searchPath.split(File.pathSeparator)) {
                result.add(new File(path));
            }
        }
        return result;
    }

    /* access modifiers changed from: private */
    public static String getprefixname(String fullname) {
        String filename;
        Log.w(TAG, "fullname is " + fullname);
        int preindex = fullname.lastIndexOf("/");
        if (preindex >= 0) {
            filename = fullname.substring(preindex + 1);
        } else {
            filename = fullname;
        }
        int sufindex = filename.lastIndexOf(".");
        if (sufindex >= 0) {
            return filename.substring(0, sufindex);
        }
        return filename;
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:29:0x0067=Splitter:B:29:0x0067, B:24:0x005d=Splitter:B:24:0x005d} */
    private static ArrayList<Object> openallDexes(ClassLoader loader, String jarordexpathlist, String Odexpath) {
        Object dexPathList;
        Method loadDexFileId;
        ArrayList<Object> objdexfilelist;
        ArrayList<File> jarordexfilelist = splitPaths(jarordexpathlist);
        File Odexdir = new File(Odexpath);
        ArrayList<Object> objdexfilelist2 = null;
        try {
            try {
                dexPathList = findField(loader, "pathList").get(loader);
                try {
                    loadDexFileId = findMethod(dexPathList, "loadDexFile", File.class, File.class);
                    try {
                        objdexfilelist = new ArrayList<>();
                    } catch (InvocationTargetException e) {
                        e = e;
                        e.printStackTrace();
                        return objdexfilelist2;
                    }
                } catch (NoSuchMethodException e2) {
                    e = e2;
                }
            } catch (IllegalAccessException e3) {
                e = e3;
            } catch (IllegalArgumentException e4) {
                e = e4;
                e.printStackTrace();
                return objdexfilelist2;
            }
            try {
                Iterator dexfileit = jarordexfilelist.iterator();
                while (dexfileit.hasNext()) {
                    objdexfilelist.add(loadDexFileId.invoke(dexPathList, new Object[]{dexfileit.next(), Odexdir}));
                }
                return objdexfilelist;
            } catch (InvocationTargetException e5) {
                e = e5;
                objdexfilelist2 = objdexfilelist;
                e.printStackTrace();
                return objdexfilelist2;
            } catch (NoSuchMethodException e6) {
                e = e6;
                objdexfilelist2 = objdexfilelist;
                e.printStackTrace();
                return objdexfilelist2;
            } catch (IllegalAccessException e7) {
                e = e7;
                objdexfilelist2 = objdexfilelist;
                e.printStackTrace();
                return objdexfilelist2;
            } catch (IllegalArgumentException e8) {
                e = e8;
                objdexfilelist2 = objdexfilelist;
                e.printStackTrace();
                return objdexfilelist2;
            } catch (NoSuchFieldException e9) {
                e = e9;
                objdexfilelist2 = objdexfilelist;
                e.printStackTrace();
                return objdexfilelist2;
            }
        } catch (NoSuchFieldException e10) {
            e = e10;
        }
    }

    private static ArrayList<Object> installDexes(ClassLoader loader, String jarordexpathlist, String Odexpath, boolean ifresetcookie, boolean ifreturndexfile) {
        ArrayList<File> jarordexfilelist = splitPaths(jarordexpathlist);
        File Odexdir = new File(Odexpath);
        try {
            try {
                return V19.install(loader, findField(loader, "pathList"), jarordexfilelist, Odexdir, ifresetcookie, ifreturndexfile);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
            } catch (NoSuchMethodException e4) {
                e4.printStackTrace();
            }
            return null;
        } catch (NoSuchFieldException e5) {
            Log.w(TAG, "no pathlist,try path field");
            try {
                try {
                    return V4.install(loader, findField(loader, "path"), jarordexfilelist, Odexdir);
                } catch (IllegalArgumentException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e12) {
                    e12.printStackTrace();
                } catch (IOException e13) {
                    e13.printStackTrace();
                }
            } catch (NoSuchFieldException e6) {
                Log.w(TAG, "Strange!no pathlist&path,a new or modified android version ");
            }
        }
    }

    /* access modifiers changed from: private */
    public static Field findField(Object instance, String name) throws NoSuchFieldException {
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

    private static Method findMethodinClazz(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        while (clazz != null) {
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method " + name + " with parameters " + Arrays.asList(parameterTypes) + " not found in " + clazz);
    }

    /* access modifiers changed from: private */
    public static Method findMethod(Object instance, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class<?> clazz = instance.getClass();
        while (clazz != null) {
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method " + name + " with parameters " + Arrays.asList(parameterTypes) + " not found in " + instance.getClass());
    }

    /* access modifiers changed from: private */
    public static void expandFieldArray(Object instance, String fieldName, Object[] extraElements) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field jlrField = findField(instance, fieldName);
        Object[] original = (Object[]) jlrField.get(instance);
        Object[] combined = (Object[]) Array.newInstance(original.getClass().getComponentType(), original.length + extraElements.length);
        if (VERSION.SDK_INT == 28 && hasInjected == 0 && original.length > 1) {
            System.arraycopy(original, 0, combined, 0, original.length - 1);
            System.arraycopy(extraElements, 0, combined, original.length - 1, extraElements.length);
            System.arraycopy(original, original.length - 1, combined, (extraElements.length + original.length) - 1, 1);
        } else {
            System.arraycopy(extraElements, 0, combined, 0, extraElements.length);
            System.arraycopy(original, 0, combined, extraElements.length, original.length);
        }
        jlrField.set(instance, combined);
    }
}
