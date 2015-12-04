package com.realtimestudio.transport.storm.bolt;

import static com.realtimestudio.transport.storm.util.Constants.HDFSFILEPREFIX;
import static com.realtimestudio.transport.storm.util.Constants.HDFSFILEROTATIONTIMEMIN;
import static com.realtimestudio.transport.storm.util.Constants.HDFSPATH;
import static com.realtimestudio.transport.storm.util.Constants.HDFSURL;
import static com.realtimestudio.transport.storm.util.Constants.HIVEDBNAME;
import static com.realtimestudio.transport.storm.util.Constants.HIVEMETASTOREURL;
import static com.realtimestudio.transport.storm.util.Constants.HIVESTAGINGTABLE;

import org.apache.storm.hdfs.bolt.HdfsBolt;
import org.apache.storm.hdfs.bolt.format.DefaultFileNameFormat;
import org.apache.storm.hdfs.bolt.format.FileNameFormat;
import org.apache.storm.hdfs.bolt.format.RecordFormat;
import org.apache.storm.hdfs.bolt.sync.CountSyncPolicy;
import org.apache.storm.hdfs.bolt.sync.SyncPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.realtimestudio.transport.storm.util.Parameters;

public class GPSSignalHDFSBolt extends HdfsBolt {
	private static final long serialVersionUID = 5819784875238561416L;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(GPSSignalHDFSBolt.class);

	public GPSSignalHDFSBolt() {
		config();
	}

	private void config() {
		String rootPath = Parameters.getProperty(HDFSPATH);
		String prefix = Parameters.getProperty(HDFSFILEPREFIX);
		String fsUrl = Parameters.getProperty(HDFSURL);
		String sourceMetastoreUrl = Parameters.getProperty(HIVEMETASTOREURL);
		String hiveStagingTableName = Parameters.getProperty(HIVESTAGINGTABLE);
		String databaseName = Parameters.getProperty(HIVEDBNAME);
		Float rotationTimeInMinutes = Float.valueOf(Parameters
				.getProperty(HDFSFILEROTATIONTIMEMIN));

		RecordFormat recordFormat = new GPSSignalRecordFormat()
				.withFieldDelimiter(",");

		// Synchronize data buffer with the filesystem every 100 tuples
		SyncPolicy syncPolicy = new CountSyncPolicy(100);

		// Rotate every X minutes
		FileTimeRotationPolicy rotationPolicy = new FileTimeRotationPolicy(
				rotationTimeInMinutes, FileTimeRotationPolicy.Units.MINUTES);

		// Hive Partition Action
		HiveTablePartitionAction hivePartitionAction = new HiveTablePartitionAction(
				sourceMetastoreUrl, hiveStagingTableName, databaseName, fsUrl);

		FileNameFormat fileNameFormat = new DefaultFileNameFormat().withPath(
				rootPath + "/staging").withPrefix(prefix);

		this.withFsUrl(fsUrl).withFileNameFormat(fileNameFormat)
				.withRecordFormat(recordFormat)
				.withRotationPolicy(rotationPolicy)
				.withSyncPolicy(syncPolicy)
		//		.addRotationAction(hivePartitionAction)
				;

	}

}
