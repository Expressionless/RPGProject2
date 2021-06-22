package main.launch;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

import helix.utils.ClassUtils;

public class DynamicLoaderCode {
	public static final String CLASS_PATH = new File("").getAbsolutePath() + "/src/main/java/main";

	public static void main(String[] args) {
		
		System.out.println("Trying dynamic loader");
		
		Set<Class<?>> classes = ClassUtils.getClasses(CLASS_PATH);
		ArrayList<String> names = new ArrayList<>();
		
		
		names.sort(new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.toCharArray()[0] - s2.toCharArray()[0];
			}
		});
	}
}
