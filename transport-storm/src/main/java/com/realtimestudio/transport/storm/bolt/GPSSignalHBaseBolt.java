package com.realtimestudio.transport.storm.bolt;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.FailedException;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import com.realtimestudio.transport.dao.GPSSignalDao;
import com.realtimestudio.transport.model.RoutePoint;
import com.realtimestudio.transport.storm.util.Constants;
import com.realtimestudio.transport.utils.SpringUtils;

public class GPSSignalHBaseBolt extends BaseBasicBolt{
	private static final long serialVersionUID = 303956404497484148L;

	private final static Logger LOGGER = LoggerFactory.getLogger(GPSSignalHBaseBolt.class);
	
	private GPSSignalDao dao;
	
	@Override
	public void prepare (Map conf, TopologyContext context){
		LOGGER.info("GPSSIgnalHBaseBolt is being intialized");	
		dao = SpringUtils.getBean(GPSSignalDao.class);
	}
	

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String key = (String) input.getValueByField(Constants.CARIDFIELD);
		RoutePoint point = (RoutePoint) input.getValueByField(Constants.ROUTEPOINTFIELD);
		try{
			LOGGER.trace(String.format("Saving to HBase: key=%s; value=%s", key, point.toString()));
			dao.put(key, point);
		}
		catch(RuntimeException e){
			String errorMessage = String.format("Saving to hbase failed: key=%s; value=%s", key, point.toString());
			LOGGER.error(errorMessage, e);
			throw new FailedException(e);
		}
		
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		//no emission
		
	}	

}
