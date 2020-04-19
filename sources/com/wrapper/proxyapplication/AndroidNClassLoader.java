package com.wrapper.proxyapplication;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

@TargetApi(14)
class AndroidNClassLoader extends PathClassLoader {
    private static final String TAG = "SecShell";
    private static String baseApkFullPath = null;
    private static Object oldDexPathListHolder = null;

    private AndroidNClassLoader(String dexPath, ClassLoader parent, Application application) {
        super(dexPath, parent);
        try {
            baseApkFullPath = application.getPackageCodePath();
        } catch (Throwable e) {
            Log.e(TAG, "AndroidNClassLoader init:" + dexPath, e);
        }
    }

    private static Object recreateDexPathList(Object originalDexPathList, ClassLoader newDefiningContext, boolean createEmptyOne) throws Exception {
        try {
            Constructor<?> dexPathListConstructor = ShareReflectUtil.findConstructor(originalDexPathList, ClassLoader.class, String.class, String.class, File.class);
            if (createEmptyOne) {
                return dexPathListConstructor.newInstance(new Object[]{newDefiningContext, "", null, null});
            }
            Object[] dexElements = (Object[]) ShareReflectUtil.findField(originalDexPathList, "dexElements").get(originalDexPathList);
            List<File> nativeLibraryDirectories = (List) ShareReflectUtil.findField(originalDexPathList, "nativeLibraryDirectories").get(originalDexPathList);
            StringBuilder dexPathBuilder = new StringBuilder();
            Field dexFileField = ShareReflectUtil.findField(dexElements.getClass().getComponentType(), "dexFile");
            boolean isFirstItem = true;
            int length = dexElements.length;
            for (int i = 0; i < length; i++) {
                DexFile dexFile = (DexFile) dexFileField.get(dexElements[i]);
                if (!(dexFile == null || dexFile.getName() == null || !dexFile.getName().equals(baseApkFullPath))) {
                    if (isFirstItem) {
                        isFirstItem = false;
                    } else {
                        dexPathBuilder.append(File.pathSeparator);
                    }
                    dexPathBuilder.append(dexFile.getName());
                }
            }
            String dexPath = dexPathBuilder.toString();
            StringBuilder libraryPathBuilder = new StringBuilder();
            boolean isFirstItem2 = true;
            for (File libDir : nativeLibraryDirectories) {
                if (libDir != null) {
                    if (isFirstItem2) {
                        isFirstItem2 = false;
                    } else {
                        libraryPathBuilder.append(File.pathSeparator);
                    }
                    libraryPathBuilder.append(libDir.getAbsolutePath());
                }
            }
            return dexPathListConstructor.newInstance(new Object[]{newDefiningContext, dexPath, libraryPathBuilder.toString(), null});
        } catch (Throwable e) {
            Log.e(TAG, "AndroidNClassLoader recreateDexPathList", e);
            return null;
        }
    }

    private static ClassLoader createAndroidNClassLoader(String dexPath, ClassLoader originalClassLoader, Application application) throws Exception {
        try {
            AndroidNClassLoader androidNClassLoader = new AndroidNClassLoader(dexPath, originalClassLoader, application);
            Field pathListField = ShareReflectUtil.findField((Object) originalClassLoader, "pathList");
            Object originPathList = pathListField.get(originalClassLoader);
            pathListField.set(androidNClassLoader, recreateDexPathList(originPathList, androidNClassLoader, false));
            ShareReflectUtil.findField(originPathList, "definingContext").set(originPathList, androidNClassLoader);
            oldDexPathListHolder = originPathList;
            return androidNClassLoader;
        } catch (Throwable e) {
            Log.e(TAG, "AndroidNClassLoader createAndroidNClassLoader", e);
            return originalClassLoader;
        }
    }

    private static void reflectPackageInfoClassloader(Application application, ClassLoader reflectClassLoader) throws Exception {
        try {
            Context baseContext = (Context) ShareReflectUtil.findField((Object) application, "mBase").get(application);
            Object basePackageInfo = ShareReflectUtil.findField((Object) baseContext, "mPackageInfo").get(baseContext);
            ShareReflectUtil.findField(basePackageInfo, "mClassLoader").set(basePackageInfo, reflectClassLoader);
        } catch (Throwable e) {
            Log.e(TAG, "AndroidNClassLoader reflectPackageInfoClassloader", e);
        }
    }

    public static ClassLoader inject(ClassLoader originClassLoader, Application application) throws Exception {
        try {
            ClassLoader classLoader = createAndroidNClassLoader("", originClassLoader, application);
            reflectPackageInfoClassloader(application, classLoader);
            return classLoader;
        } catch (Throwable e) {
            Log.e(TAG, "AndroidNClassLoader inject", e);
            return null;
        }
    }
}
