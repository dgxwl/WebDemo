package cn.abc.def.service;

public interface ITestService {
	
	String testLock(Integer id, Boolean hasImg);
	
	String testLock(Integer id, Integer tryTimes);
}
