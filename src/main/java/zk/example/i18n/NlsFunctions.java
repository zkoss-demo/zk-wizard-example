package zk.example.i18n;

import org.zkoss.util.resource.Labels;

public class NlsFunctions {
	
	public static String nls(Object key) {
		return nlsArgs(key);
	}

	public static String nls2(Object key, Object arg1) {
		return  nlsArgs(key, arg1);
	}

	public static String nls3(Object key, Object arg1, Object arg2) {
		return  nlsArgs(key, arg1, arg2);
	}
	
	public static String nlsArgs(Object key, Object... args) {
		if(key instanceof Enum<?>) {
			return nlsSub((Enum<?>)key, "label");
		} else {
			String label = Labels.getLabel(key.toString(), args);
			if(label == null) {
				return "{" + key + "} : MISSING LABEL";
			}
			return label;
		}
	}
	
	public static String nlsSub(Enum<?> enumValue, String suffix) {
		return Labels.getLabel(nlsId(enumValue, suffix));
	}

	public static String nlsId(Enum<?> enumValue, String suffix) {
		return enumValue.getClass().getSimpleName() + '.' + enumValue.name() + '.' + suffix;
	}
}
