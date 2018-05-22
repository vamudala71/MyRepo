package com.test.trust;

import java.util.ArrayList;
/**
 * Meta data for the trust rule
 * @author amudalav
 *
 */

public class MetaData {

	ArrayList<Rule> sourceList = new ArrayList<MetaData.Rule>();

	public void addRule( Integer priority, String columnName, String tableName) {
		sourceList.add(new Rule( priority, columnName, tableName));
	}

	public Rule getRule(String tableName, String columnName, String system) {

		for (Rule rule : sourceList) {
			if (rule.getColumnName().equalsIgnoreCase(columnName)  
					&& rule.getTableName().equalsIgnoreCase(tableName)) {
				return rule;
			}
		}
		return null;

	}

	// inner class
	public static class Rule {
		 
		private Integer priority;
		private String columnName;
		private String tableName;

		public Rule(  Integer priority, String columnName, String tableName) {
			super();
			 this.priority = priority;
			this.columnName = columnName;
			this.tableName = tableName;
		}

		 
		public String getColumnName() {
			return columnName;
		}

		public String getTableName() {
			return tableName;
		}

		public Integer getPriority() {
			return priority;
		}

	}
}
