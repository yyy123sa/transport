package com.realtimestudio.transport.dao;

import org.apache.hadoop.hbase.client.Connection;

public interface ConnectionManager {
	Connection getConnection();
	void releaseConnection(Connection connection);
	void closeAll();
}
