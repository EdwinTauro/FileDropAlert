package uk.co.sparktech.filedropalert.action;

import java.util.HashMap;
import java.util.Map;

public class ActionProcessorMapping {
	static Map<String, Class<?>> REGISTERED_DEFAULT_ACTIONS = new HashMap<String, Class<?>>();

	public static void register(final String key, final Class<?> value) {
		REGISTERED_DEFAULT_ACTIONS.put(key, value);
	}
	
	public static String registeredActions() {
		
		return REGISTERED_DEFAULT_ACTIONS.keySet().toString();
	}
}
