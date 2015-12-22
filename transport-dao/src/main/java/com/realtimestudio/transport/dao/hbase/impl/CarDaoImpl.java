package com.realtimestudio.transport.dao.hbase.impl;

import static com.realtimestudio.transport.dao.Constants.*;


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
		String color = Bytes.toString(result.getValue(CAR_FAMILY_H, COLOR_H));
		Integer year = Bytes.toInt(result.getValue(CAR_FAMILY_H, YEAR_H));
		Car car = new Car(carID, model, color, year);

		String driverName = Bytes.toString(result.getValue(DRIVER_FAMILY_H,
				NAME_H));
		String driverID = Bytes
				.toString(result.getValue(DRIVER_FAMILY_H, ID_H));
		long dob = Bytes.toLong(result.getValue(DRIVER_FAMILY_H, DOB_H));
		long licenseDate = Bytes.toLong(result.getValue(DRIVER_FAMILY_H,
				LICENSEDATE_H));
		int riskFactor = Bytes.toInt(result.getValue(DRIVER_FAMILY_H,
				RISKFACTOR_H));
		String phoneNum = Bytes.toString(result.getValue(DRIVER_FAMILY_H, PHONENUM_H));
		String email = Bytes.toString(result.getValue(DRIVER_FAMILY_H, EMAIL_H));
		String licenseNum = Bytes.toString(result.getValue(DRIVER_FAMILY_H, LICENSENUM_H));

		Driver driver = new Driver(driverName, phoneNum, email, licenseNum, driverID, new Date(dob),
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
		put.addColumn(CAR_FAMILY_H, COLOR_H, Bytes.toBytes(car.getColor()));
		put.addColumn(CAR_FAMILY_H, YEAR_H, Bytes.toBytes(car.getYear()));
		
		put.addColumn(DRIVER_FAMILY_H, ID_H, Bytes.toBytes(car.getDriver().getId()));
		put.addColumn(DRIVER_FAMILY_H, NAME_H, Bytes.toBytes(car.getDriver().getName()));
		put.addColumn(DRIVER_FAMILY_H, DOB_H, Bytes.toBytes(car.getDriver().getDob().getTime()));
		put.addColumn(DRIVER_FAMILY_H, LICENSEDATE_H, Bytes.toBytes(car.getDriver().getLicenseDate().getTime()));
		put.addColumn(DRIVER_FAMILY_H, RISKFACTOR_H, Bytes.toBytes(car.getDriver().getRiskFactor()));		
		put.addColumn(DRIVER_FAMILY_H, PHONENUM_H, Bytes.toBytes(car.getDriver().getPhoneNum()));
		put.addColumn(DRIVER_FAMILY_H, EMAIL_H, Bytes.toBytes(car.getDriver().getEmail()));
		put.addColumn(DRIVER_FAMILY_H, LICENSENUM_H, Bytes.toBytes(car.getDriver().getLicenseNum()));

		return put;
	}

}
