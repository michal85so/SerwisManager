package domain;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ServiceStatus {
	private int id;
	private String name;
	
	@SuppressWarnings("serial")
	public static Map<Integer, String> listOfStatus = new HashMap<Integer, String>() {{ 
		put(2, "serviceman");
		put(3, "seller");
		put(4, "client");
	}};
	
	public static int getKeyByValue(String value) {
		for (java.util.Map.Entry<Integer, String> entry : listOfStatus.entrySet()) {
			if (entry.getValue().equals(value))
				return entry.getKey();
		}
		return 0;
	}
}
