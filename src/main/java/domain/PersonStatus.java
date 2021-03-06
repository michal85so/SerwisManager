package domain;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class PersonStatus {
	private int id;
	private String name;
	
	@SuppressWarnings("serial")
	public static Map<Integer, String> listOfStatus = new HashMap<Integer, String>() {{ 
		put(3, "serviceman");
		put(4, "seller");
		put(5, "client");
	}};
	
	public static int getKeyByValue(String value) {
		for (java.util.Map.Entry<Integer, String> entry : listOfStatus.entrySet()) {
			if (entry.getValue().equals(value))
				return entry.getKey();
		}
		return 0;
	}
}
