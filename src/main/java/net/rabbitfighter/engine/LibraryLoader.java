package main.java.net.rabbitfighter.engine;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Load the appropriate libraries depending on OS type and processor
 * 
 * @author rabbitfighter
 *
 */
public class LibraryLoader {

	/**
	 * Load native Libraries
	 * 
	 * @throws Exception
	 */
	public static void loadNativeLibraries() throws Exception {
		if (System.getProperty("os.name").equals("Mac OS X")) {
			addLibraryPath("libs/native/macosx");
		} else if (System.getProperty("os.name").equals("Linux")) {
			addLibraryPath("libs/native/linux");

		} else if (System.getProperty("os.name").equals("FreeBSD")) {
			addLibraryPath("libs/native/freebsd");

		} else if (System.getProperty("os.name").equals("Solaris")) {
			addLibraryPath("libs/native/solaris");

			if (System.getProperty("os.arch").equals("amd64") || System.getProperty("os.arch").equals("x86_64")) {
				System.loadLibrary("libopenal64.so");

			} else {
				System.loadLibrary("libopenal.so");
			}

		} else if (System.getProperty("os.name").equals("Windows")) {
			addLibraryPath("libs/native/windows");

			if (System.getProperty("os.arch").equals("amd64") || System.getProperty("os.arch").equals("x86_64")) {
				System.loadLibrary("OpenAL64");
			} else {
				System.loadLibrary("OpenAL32");
			}
		}
	}

	private static void addLibraryPath(String string) throws Exception {

		final Field usr_paths_field = ClassLoader.class.getDeclaredField("usr_paths");
		usr_paths_field.setAccessible(true);
		final String[] paths = (String[]) usr_paths_field.get(null);

		for (String path: paths) {
			if (path.equals(string)) {
				return;
			}
		}

		final String[] new_paths = Arrays.copyOf(paths, paths.length + 1);
		new_paths[paths.length - 1] = string;
		usr_paths_field.set(null, new_paths);
	}

}