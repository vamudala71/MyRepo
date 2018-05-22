package com.test.trust;

import java.util.HashMap;

public class Priority {

	HashMap<MetaData.Rule, Source> metaPriority = new HashMap<MetaData.Rule, Source>();

	public void addSource(MetaData.Rule rule, Source value) {
		metaPriority.put(rule, value);

	}

	public Source getHighRankValue() {

		MetaData.Rule highSource = null;
		for (MetaData.Rule currentSource : metaPriority.keySet()) {
			if (highSource == null && metaPriority.get(currentSource) != null) {
				highSource = currentSource;

			} else if (currentSource.getPriority() > highSource.getPriority()
					&& metaPriority.get(currentSource) != null) {
				highSource = currentSource;

			}
		}
		return metaPriority.get(highSource);
	}

	 	public static class Source {
		String md5;
		String tableName;
		String columnName;
		String domain;
		String value;

		public Source(String md5, String tableName, String columnName, String domain, String value) {
			this.md5 = md5;
			this.tableName = tableName;
			this.columnName = columnName;
			this.domain = domain;
			this.value = value;
		}

		public String getMd5() {
			return md5;
		}

		public String getTableName() {
			return tableName;
		}

		public String getColumnName() {
			return columnName;
		}

		public String getDomain() {
			return domain;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return "Source [md5=" + md5 + ", tableName=" + tableName + ", columnName=" + columnName + ", domain="
					+ domain + ", value=" + value + "]";
		}

	}

}
