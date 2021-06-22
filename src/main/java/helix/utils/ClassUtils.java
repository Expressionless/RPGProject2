package helix.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class ClassUtils {
	public static final String CLASS_PATH = new File("").getAbsolutePath() + "/src/main/java/main";

	public static Set<Class<?>> getClasses() {
		return getClasses(CLASS_PATH);
	}
	
	public static Set<Class<?>> getClasses(String path) {
		File currentDir = new File(path);
		Set<String> classNameList = new HashSet<String>();

		for (String p : currentDir.list())
			classNameList.addAll(getClasses(classNameList, path + "/" + p));

		Set<Class<?>> classList = new HashSet<Class<?>>();
		for (String className : classNameList) {
			try {
				classList.add(Class.forName(className));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return classList;
	}

	private static Set<String> getClasses(Set<String> classesList, String path) {
		// Add all current classes
		for (String clazz : classesList)
			classesList.add(clazz);

		File currentDir = new File(path);
		if (!currentDir.exists()) {
			System.err.println("premature return");
			return classesList;
		}

		if (currentDir.isDirectory()) {
			for (String p : currentDir.list()) {
				File next = new File(path + "/" + p);
				if (next.isDirectory()) {
					classesList.addAll(getClasses(classesList, path + "/" + p));
				} else {
					classesList.add(getFullyQualifiedName(path + "/" + p));
				}
			}

		} else if (!currentDir.isDirectory()) {
			String qualifiedName = getFullyQualifiedName(path);
			classesList.add(qualifiedName);
		}

		return classesList;
	}

	public static String getFullyQualifiedName(String path) {
		int index = path.indexOf("java/main");

		// Remove everything before incl. "java/" and then remove ".java" from the end
		path = path.substring(index).substring(5).replaceAll("/", ".");
		return path.substring(0, path.length() - 5);
	}
	
	public static boolean setStatic(Class<?> targetClass, String fieldName, Object val) {
		Field field;
		
		try {
			field = targetClass.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			return false;
		}
		
		Object old;
		try {
			old = field.get(targetClass);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			field.set(old, val);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
