package com.realtimestudio.transport.streaming;

public interface SignalCollector {
	void send(String signal);
	void send(String key, String signal);
	void close();

}
