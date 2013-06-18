package apps;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppPropertiesLoader {

	public static Properties load(String appConfigurationFile) {
		// Create and load default properties
		Properties prop = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream("src/apps/default.cfg");
			prop.load(in);
			in.close();
		} catch (IOException e) {
			System.err.println("The default configuration file cannot be read.");
			e.printStackTrace();
			System.exit(0);
		}

		// Load application properties
		try {
			in = new FileInputStream(appConfigurationFile);
			prop.load(in);
			in.close();
		} catch (IOException e) {
			System.err.println("Warning: the application configuration file cannot be read.");
		}
		return prop;
	}

}
