package com.realtimestudio.transport.storm.spout;

import static com.realtimestudio.transport.storm.util.Constants.CARIDFIELD;
import static com.realtimestudio.transport.storm.util.Constants.ROUTEPOINTFIELD;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.realtimestudio.transport.event.GPSSignalParser;
import com.realtimestudio.transport.event.gps.GPSSignalParserImpl;

public class GPSSignalScheme implements Scheme{
	private static final long serialVersionUID = -21418484022973181L;
	private static final Logger LOGGER = LoggerFactory.getLogger(GPSSignalScheme.class);

	@Override
	public List<Object> deserialize(byte[] bytes) {
		try{
			String str = new String(bytes, "UTF-8");
			LOGGER.trace("The message is: " + str);
		    GPSSignalParser parser = new GPSSignalParserImpl(str);
		    return new Values(parser.getCarID(), parser.getRoutePoint());
		}
		catch(UnsupportedEncodingException | ParseException e){
			LOGGER.error("signal parsing failed: "+ new String(bytes), e);
			throw new RuntimeException(e);
			
		}
	}

	@Override
	public Fields getOutputFields() {
		return new Fields(CARIDFIELD, ROUTEPOINTFIELD);
	}

}
