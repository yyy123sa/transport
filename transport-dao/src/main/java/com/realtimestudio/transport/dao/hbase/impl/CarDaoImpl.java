package com.realtimestudio.transport.dao.hbase.impl;

import static com.realtimestudio.transport.dao.Constants.CARDRIVER_TAB;
import static com.realtimestudio.transport.dao.Constants.CAR_FAMILY_H;
import static com.realtimestudio.transport.dao.Constants.DOB_H;
import static com.realtimestudio.transport.dao.Constants.DRIVER_FAMILY_H;
import static com.realtimestudio.transport.dao.Constants.ID_H;
import static com.realtimestudio.transport.dao.Constants.LICENSEDATE_H;
import static com.realtimestudio.transport.dao.Constants.MODEL_H;
import static com.realtimestudio.transport.dao.Constants.NAME_H;
import static com.realtimestudio.transport.dao.Constants.RISKFACTOR_H;

import java.util.Date;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import com.realtimestudio.transport.dao.CarDao;
import com.realtimestudio.transport.model.Car;
import com.realtimestudio.transport.model.Driver;

@Repository
public class CarDaoImpl extends CommonDaoImpl<Car> implements CarDao {

	@Override
	protected Car parseResult(Result result) {
		String carID = Bytes.toString(result.getRow());
		String model = Bytes.toString(result.getValue(CAR_FAMILY_H, MODEL_H));
		Car car = new Car(carID, model);

		String driverName = Bytes.toString(result.getValue(DRIVER_FAMILY_H,
				NAME_H));
		String driverID = Bytes
				.toString(result.getValue(DRIVER_FAMILY_H, ID_H));
		long dob = Bytes.toLong(result.getValue(DRIVER_FAMILY_H, DOB_H));
		long licenseDate = Bytes.toLong(result.getValue(DRIVER_FAMILY_H,
				LICENSEDATE_H));
		int riskFactor = Bytes.toInt(result.getValue(DRIVER_FAMILY_H,
				RISKFACTOR_H));

		Driver driver = new Driver(driverName, driverID, new Date(dob),
				new Date(licenseDate), riskFactor);
		car.setDriver(driver);

		return car;
	}

	@Override
	protected String getTableName() {
		return CARDRIVER_TAB;
	}

	@Override
	protected Put buildRow(String carid, Car car) {
		Put put = new Put(Bytes.toBytes(carid));

		put.addColumn(CAR_FAMILY_H, MODEL_H, Bytes.toBytes(car.getModel()));
		put.addColumn(DRIVER_FAMILY_H, ID_H, Bytes.toBytes(car.getDriver().getId()));
		put.addColumn(DRIVER_FAMILY_H, NAME_H, Bytes.toBytes(car.getDriver().getName()));
		put.addColumn(DRIVER_FAMILY_H, DOB_H, Bytes.toBytes(car.getDriver().getDob().getTime()));
		put.addColumn(DRIVER_FAMILY_H, LICENSEDATE_H, Bytes.toBytes(car.getDriver().getLicenseDate().getTime()));
		put.addColumn(DRIVER_FAMILY_H, RISKFACTOR_H, Bytes.toBytes(car.getDriver().getRiskFactor()));		

		return put;
	}

}
