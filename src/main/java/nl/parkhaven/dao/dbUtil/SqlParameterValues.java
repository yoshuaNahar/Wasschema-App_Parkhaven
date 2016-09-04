package nl.parkhaven.dao.dbUtil;

import java.util.HashMap;
import java.util.Map;

public class SqlParameterValues {

	private Map<String, Object> values = new HashMap<String, Object>();

	public SqlParameterValues addValue(String key, Object value) {
		values.put(key, value);
		return this;
	}

	public Map<String, Object> getValues() {
		return values;
	}

	public void setValues(Map<String, Object> values) {
		this.values = values;
	}
}
