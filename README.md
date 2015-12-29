# transport

 #create kafka topic, retention min = 60min
 /usr/hdp/current/kafka-broker/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 4 --topic gpssignal --config retention.ms=60
 
 #check kafka topics
/usr/hdp/current/kafka-broker/bin/kafka-topics.sh --list --zookeeper localhost:2181

 #describe topic
 /usr/hdp/current/kafka-broker/bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic gpssignal
 
 #check the message in topic
/usr/hdp/current/kafka-broker/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic gpssignal --from-beginning

#create HBase tables
CREATE 'gpssignal', {NAME => 'd', VERSIONS => 40}
CREATE 'car_driver', {NAME => 'c'}, {NAME => 'd'}

#truncate HBase tables
truncate 'gpssignal'
truncate 'car_driver'

#new HBASE client API description
https://hbase.apache.org/apidocs/org/apache/hadoop/hbase/client/package-summary.html

#Load car/driver information to HBase
#Config files: HBase.properties;  Simulator.properties; logback.xml

#transport-simulator
#config files: HBase.properties; http.properties; Simulator.properties; logback.xml

