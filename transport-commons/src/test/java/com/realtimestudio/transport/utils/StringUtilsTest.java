
package com.realtimestudio.transport.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void test() {
		System.out.println(StringUtils.removePadFromString("000100", '0',  true));
		assertEquals(StringUtils.removePadFromString("000100", '0',  true), "100");
		assertEquals(StringUtils.removePadFromString("000100", '0',  false), "0001");
	}

}
