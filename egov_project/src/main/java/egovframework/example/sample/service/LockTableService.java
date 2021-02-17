package egovframework.example.sample.service;

import java.util.Map;

public interface LockTableService {

	void updateSample(Map<String,Object> vo) throws Exception;

	Map<String,Object> selectSample(Map<String,Object> vo) throws Exception;
	
}
