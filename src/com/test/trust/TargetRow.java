package com.test.trust;

import java.util.ArrayList;
import java.util.HashMap;

public class TargetRow {

	HashMap<String, Priority> columnList = new HashMap<String, Priority>();

	public static void main(String args[]) {
		// Load metadata
		MetaData m = new MetaData();
		m.addRule(100, "pdate", "milestone_ctms");
		m.addRule(90, "pdate", "milestone_ps");
		m.addRule(80, "pdate", "milestone_es");
		m.addRule(90, "adate", "milestone_ctms");
		m.addRule(100, "adate", "milestone_ps");
		m.addRule(80, "adate", "milestone_es");

		// read rows from eav per domain sorted by md5, columnName.
		ArrayList<Priority.Source> sourceList = new ArrayList<Priority.Source>();
		sourceList.add(new Priority.Source("md51", "milestone_ctms", "pdate", "domain1", "2016-01-01"));
		sourceList.add(new Priority.Source("md51", "milestone_ps", "pdate", "domain1", "2016-01-01"));

		sourceList.add(new Priority.Source("md51", "milestone_ctms", "adate", "domain1", "2016-01-01"));
		sourceList.add(new Priority.Source("md51", "milestone_ps", "adate", "domain1", "2016-01-02"));
		sourceList.add(new Priority.Source("md52", "milestone_ctms", "pdate", "domain1", "2016-01-03"));
		sourceList.add(new Priority.Source("md52", "milestone_ps", "pdate", "domain1", "2016-01-01"));

		sourceList.add(new Priority.Source("md53", "milestone_ps", "pdate", "domain1", "2016-01-01"));
		TargetRow t = new TargetRow();
		Priority p = null;
		String prevMd5 = null;
		String prevDomain = null;
		String prevColumnName = null;
		for (Priority.Source source : sourceList) {
			if (prevMd5 == null) {
				prevMd5 = source.getMd5();
				prevDomain = source.getDomain();
				prevColumnName = source.getColumnName();
				p = new Priority();
			}
			if (source.getMd5() == prevMd5 && source.getDomain() == prevDomain && source.columnName == prevColumnName) {
				p.addSource(m.getRule(source.getTableName(), source.getColumnName(), source.getDomain()), source);
				t.columnList.put(source.getColumnName(), p);

			} else if (source.getMd5() == prevMd5 && source.getDomain() == prevDomain
					&& source.columnName != prevColumnName) {
				p = new Priority();
				p.addSource(m.getRule(source.getTableName(), source.getColumnName(), source.getDomain()), source);
				t.columnList.put(source.getColumnName(), p);
			} else if (source.getMd5() != prevMd5 && source.getDomain() == prevDomain) {

				// Insert new row into target table

				insertIntoTarget(t);
				p = new Priority();
				t = new TargetRow();
				p.addSource(m.getRule(source.getTableName(), source.getColumnName(), source.getDomain()), source);
				t.columnList.put(source.getColumnName(), p);

			}
			if ( source.getDomain() != prevDomain) {

				// Insert new row into target table

				insertIntoTarget(t);
				p = new Priority();
				t = new TargetRow();
				p.addSource(m.getRule(source.getTableName(), source.getColumnName(), source.getDomain()), source);
				t.columnList.put(source.getColumnName(), p);
			}
			prevMd5 = source.getMd5();
			prevDomain = source.getDomain();
			prevColumnName = source.getColumnName();

		}

		// Handle Last row

		insertIntoTarget(t);

	}

	private static void insertIntoTarget(TargetRow t) {
		// Clean up;
		StringBuffer insertStatment = 
		new StringBuffer( "Insert into " + t.columnList.values().iterator().next().getHighRankValue().getDomain() + " ( "); 
		 
		for (String columnName : t.columnList.keySet()) {
			// Prepare Insert Statment
            
			insertStatment.append(  columnName  + ",");
		}
		insertStatment.append( "id");
		insertStatment.append(" ) values ( ");
		for (String columnName : t.columnList.keySet()) {
			// Prepare Insert Statment
            
			insertStatment.append(  t.columnList.get(columnName).getHighRankValue().getValue()   + " ,");
		}
		insertStatment.append(   t.columnList.values().iterator().next().getHighRankValue().getMd5()  
				  );
	
		insertStatment.append( " ) ; ");
		System.out.println(insertStatment.toString()) ;
		
	}

}
