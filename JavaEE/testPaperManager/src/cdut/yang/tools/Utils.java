package cdut.yang.tools;

import java.util.UUID;

public class Utils {
	public static String uuid(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
