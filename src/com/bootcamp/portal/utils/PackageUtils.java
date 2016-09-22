package com.bootcamp.portal.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.bootcamp.portal.domain.AbstractEntity;

//TODO remove what is not used
public class PackageUtils {

	public static Iterable<Class<?>> getClasses(String packageName)
			throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (File directory : dirs) {
			@SuppressWarnings("deprecation")
			File dir = new File(URLDecoder.decode(directory.getAbsolutePath()));
			classes.addAll(findClasses(dir, packageName));
		}

		return classes;
	}

	private static List<Class<?>> findClasses(File directory, String packageName)
			throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				classes.addAll(findClasses(file,
						packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName
						+ '.'
						+ file.getName().substring(0,
								file.getName().length() - 6)));
			}
		}
		return classes;
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> findClassByName(Class<T> baseClass,
			String className) {
		try {
			return (Class<T>) Class.forName(baseClass.getPackage().getName()
					+ "." + TextUtil.firstLetterUpper(className));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static Class<AbstractEntity> findEntityClassByName(String className) {
		return PackageUtils.findClassByName(AbstractEntity.class, className);
	}

	public static AbstractEntity createEntityByClassName(String className) {
		Class<AbstractEntity> c = PackageUtils.findEntityClassByName(className);
		if (c != null) {
			try {
				return (AbstractEntity) c.getConstructor().newInstance();
			} catch (Exception e) {
				// OK
			}
		}
		return null;
	}

	public static List<Class<?>> findClassesByAnnotation(Class<?> baseClass,
			Class<? extends Annotation> annotationClass) {
		try {
			List<Class<?>> list = new ArrayList<Class<?>>();
			for (Class<?> clazz : getClasses(baseClass.getPackage().getName())) {
				if (clazz.isAnnotationPresent(annotationClass)) {
					list.add(clazz);
				}
			}
			return list;
		} catch (ClassNotFoundException | IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private static List<String> recurseEagerFields(Class<?> entity,
			boolean eagerRecursively, boolean onlyOneToMany) {
		List<String> eagerFields = new ArrayList<>();

		for (Field field : entity.getDeclaredFields()) {
			if (field.isAnnotationPresent(OneToMany.class)
					|| ((field.isAnnotationPresent(OneToOne.class) || field
							.isAnnotationPresent(ManyToOne.class)) && !onlyOneToMany)) {

				eagerFields.add(field.getName());
				// skip many to many (?)
				if (eagerRecursively
						&& !field.isAnnotationPresent(JoinTable.class)) {
					try {
						Type type = field.getGenericType();
						if (type instanceof ParameterizedType) {
							ParameterizedType genericType = (ParameterizedType) type;
							Class<?> cl = (Class<?>) genericType
									.getActualTypeArguments()[0];
							List<String> childFields = recurseEagerFields(cl,
									true, true);
							for (String cf : childFields) {
								eagerFields.add(field.getName() + "." + cf);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return eagerFields;
	}

	public static List<String> findAllEagerFields(Class<?> entity,
			boolean eagerRecursively) {
		List<String> eagerFields = recurseEagerFields(entity, eagerRecursively,
				false);
		return eagerFields;
	}

	@SuppressWarnings("unchecked")
	public static <E> List<E> castCollection(Collection<?> collection,
			Class<? extends E> clazz) {
		return (List<E>) collection;
	}

	public static void main(String[] args) throws Exception {
		String packageName = AbstractEntity.class.getPackage().getName();
		for (Class<?> clazz : PackageUtils.getClasses(packageName)) {
			System.out.println(clazz.getSimpleName());
		}
	}
}
