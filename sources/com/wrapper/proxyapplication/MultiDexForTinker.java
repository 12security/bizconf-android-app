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
import java.util.zip.ZipFile;

public class MultiDexForTinker {
    static final String TAG = "MultiDexForTinker";
    static int hasInjected = 0;

    private static final class V19 {
        private V19() {
        }

        /* access modifiers changed from: private */
        public static void install(ClassLoader loader, Field pathListField, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException {
            IOException[] dexElementsSuppressedExceptions;
            Object dexPathList = pathListField.get(loader);
            ArrayList<IOException> suppressedExceptions = new ArrayList<>();
            Object[] DexElementslist = makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory, suppressedExceptions);
            if (DexElementslist.length != additionalClassPathEntries.size()) {
                throw new IOException("load dex failed");
            }
            MultiDexForTinker.expandFieldArray(dexPathList, "dexElements", DexElementslist);
            if (suppressedExceptions.size() > 0) {
                Iterator it = suppressedExceptions.iterator();
                while (it.hasNext()) {
                    Log.w(MultiDexForTinker.TAG, "Exception in makeDexElement", (IOException) it.next());
                }
                Field suppressedExceptionsField = MultiDexForTinker.findField(dexPathList, "dexElementsSuppressedExceptions");
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
        }

        private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory, ArrayList<IOException> suppressedExceptions) {
            try {
                try {
                    return (Object[]) MultiDexForTinker.findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class).invoke(dexPathList, new Object[]{files, optimizedDirectory});
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
                Log.w(MultiDexForTinker.TAG, "not old makeDexElements,try new makeDexElements");
                try {
                    try {
                        return (Object[]) MultiDexForTinker.findMethod(dexPathList, "makeDexElements", ArrayList.class, File.class, ArrayList.class).invoke(dexPathList, new Object[]{files, optimizedDirectory, suppressedExceptions});
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
                    Log.w(MultiDexForTinker.TAG, "not makeDexElements,try  makePathElements");
                    try {
                    } catch (NoSuchMethodException e22) {
                        Log.w(MultiDexForTinker.TAG, "Strange!no Elements,a new or modified android version ");
                        e22.printStackTrace();
                    }
                    try {
                        return (Object[]) MultiDexForTinker.findMethod(dexPathList, "makePathElements", List.class, File.class, List.class).invoke(dexPathList, new Object[]{files, optimizedDirectory, suppressedExceptions});
                    } catch (IllegalAccessException e23) {
                        e23.printStackTrace();
                        e15.printStackTrace();
                        e5.printStackTrace();
                        return null;
                    } catch (IllegalArgumentException e24) {
                        e24.printStackTrace();
                        e15.printStackTrace();
                        e5.printStackTrace();
                        return null;
                    } catch (InvocationTargetException e25) {
                        e25.printStackTrace();
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
        public static void install(ClassLoader loader, Field pathListField, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, IOException {
            int extraSize = additionalClassPathEntries.size();
            StringBuilder path = new StringBuilder((String) pathListField.get(loader));
            String[] extraPaths = new String[extraSize];
            File[] extraFiles = new File[extraSize];
            ZipFile[] extraZips = new ZipFile[extraSize];
            DexFile[] extraDexs = new DexFile[extraSize];
            if (extraDexs.length != extraSize) {
                throw new IOException("load dex failed");
            }
            pathListField.set(loader, path.toString());
            MultiDexForTinker.expandFieldArray(loader, "mPaths", extraPaths);
            MultiDexForTinker.expandFieldArray(loader, "mFiles", extraFiles);
            MultiDexForTinker.expandFieldArray(loader, "mZips", extraZips);
            MultiDexForTinker.expandFieldArray(loader, "mDexs", extraDexs);
        }
    }

    private MultiDexForTinker() {
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

    private static String getprefixname(String fullname) {
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

    private static void installDexes(ClassLoader loader, String jarordexpathlist, String Odexpath) throws IOException {
        ArrayList<File> jarordexfilelist = splitPaths(jarordexpathlist);
        File Odexdir = new File(Odexpath);
        try {
            V19.install(loader, findField(loader, "pathList"), jarordexfilelist, Odexdir);
        } catch (IllegalArgumentException e5) {
            e5.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
        } catch (IOException e) {
            throw new IOException("v19,load dex fail");
        } catch (NoSuchFieldException e4) {
            Log.w(TAG, "no pathlist,try path field");
            try {
                V4.install(loader, findField(loader, "path"), jarordexfilelist, Odexdir);
            } catch (IllegalArgumentException e12) {
                e12.printStackTrace();
            } catch (IllegalAccessException e22) {
                e22.printStackTrace();
            } catch (IOException e6) {
                throw new IOException("v4, load dex fail");
            } catch (NoSuchFieldException e7) {
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
