package nl.parkhaven.dao.dbUtil;

import java.util.HashMap;
import java.util.Map;

public class SqlBindingUtil {

	public static Map<String, Integer> getSqlParameters(String sql) {

		Map<String, Integer> parameters = new HashMap<String, Integer>();

		StringBuffer buffer = new StringBuffer();

		Integer parameterIndex = 1;

		char[] statement = sql.toCharArray();

		boolean isParameter = false;
		int start = 0;
		int end = 0;
		for (int i = 0; i < statement.length; i++) {
			if (statement[i] == ':') {
				isParameter = true;
				start = i;
				end = i;
			} else if (isParameter && (Character.isWhitespace(statement[i]) || i == statement.length - 1)) {
				isParameter = false;
				end = i;
				String paramName = new String(statement, start + 1,
						(i == statement.length - 1 ? end - start : end - start - 1));
				buffer.append(" ? ");
				parameters.put(paramName, parameterIndex);
				parameterIndex++;
			} else if (isParameter && (!Character.isWhitespace(statement[i]) && i != statement.length - 1)) {

			} else {
				buffer.append(statement[i]);
			}
		}
		sql = new String(buffer.toString());
		System.out.println(buffer.toString());
		return parameters;
	}

	@SuppressWarnings("unused")
	public static String convertToStandartBindings(String sql) {
		StringBuffer buffer = new StringBuffer();
		char[] statement = sql.toCharArray();

		boolean isParameter = false;
		int start = 0;
		int end = 0;
		for (int i = 0; i < statement.length; i++) {
			if (statement[i] == ':') {
				isParameter = true;
				start = i;
				end = i;
			} else if (isParameter && (Character.isWhitespace(statement[i]) || i == statement.length - 1)) {
				isParameter = false;
				end = i;

				buffer.append(" ? ");

			} else if (isParameter && (!Character.isWhitespace(statement[i]) && i != statement.length - 1)) {

			} else {
				buffer.append(statement[i]);
			}
		}

		return buffer.toString();
	}
}
