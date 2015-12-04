package com.realtimestudio.transport.dao.hbase.impl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;

import com.realtimestudio.transport.dao.CommonDao;
import com.realtimestudio.transport.dao.ConnectionManager;

public abstract class CommonDaoImpl<T> implements CommonDao<T> {
	@Autowired
	private ConnectionManager connectionManager;
	
	protected abstract T parseResult(Result result);
	protected abstract String getTableName();
	protected abstract Put buildRow(String key, T t);
	

	@Override
	public Map<String, T> findAll() {
		Connection connection = connectionManager.getConnection();
		try(Table table = connection.getTable(TableName.valueOf(getTableName()));
				ResultScanner scanner = table.getScanner(new Scan());) {
			Map<String, T> map = new LinkedHashMap<>();
			for(Result result : scanner){
				map.put(Bytes.toString(result.getRow()), parseResult(result));
			}
			return map;
		} 
		catch(IOException e){
			throw new RuntimeException(e);
		}
		finally{
			connectionManager.releaseConnection(connection);
		}
	}

	@Override
	public T findById(String key) {
		Connection connection = connectionManager.getConnection();
		try(Table table = connection.getTable(TableName.valueOf(getTableName()));) {
			Get get = new Get(Bytes.toBytes(key.toString()));
			Result result = table.get(get);
			if(result.isEmpty()) return null;
			return parseResult(result);
			
		} 
		catch(IOException e){
			throw new RuntimeException(e);
		}
		finally{
			connectionManager.releaseConnection(connection);
		}
	}
	

	@Override
	public void put(String key, T t) {
		Connection connection = connectionManager.getConnection();
		try(Table table = connection.getTable(TableName.valueOf(getTableName()));) {
			Put put = buildRow(key, t);
			table.put(put);			
		} 
		catch(IOException e){
			throw new RuntimeException(e);
		}
		finally{
			connectionManager.releaseConnection(connection);
		}		
		
	}
	
	@Override
	public void delete(String key){
		Connection connection = connectionManager.getConnection();
		try(Table table = connection.getTable(TableName.valueOf(getTableName()));) {
			Delete delete = new Delete(Bytes.toBytes(key));
			table.delete(delete);
		} 
		catch(IOException e){
			throw new RuntimeException(e);
		}
		finally{
			connectionManager.releaseConnection(connection);
		}			
	}
	

}
