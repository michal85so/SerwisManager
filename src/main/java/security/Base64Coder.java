package security;

import java.util.Base64;

import com.sun.istack.internal.NotNull;

public class Base64Coder {
	
	@NotNull
	public static String encodeStringToBase64(@NotNull String text) {
		return Base64.getEncoder().encodeToString(text.getBytes());
	}
	
	@NotNull
	public static String decodeStringFromBase64(@NotNull String code) {
		return new String(Base64.getDecoder().decode(code));
	}
}
