package egovframework.example.sample.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.example.sample.service.LockTableService;

@Service("lockTableService")
public class LockTableServiceImpl implements LockTableService {

	
	@Resource(name="lockTableServiceMapper")
	private LockTableServiceMapper lockTableServiceMapper;
	

	@Override
	public void updateSample(Map<String, Object> vo) throws Exception {
		lockTableServiceMapper.updateSample(vo);
	}

	@Override
	public Map<String, Object> selectSample(Map<String, Object> vo) throws Exception {
		return lockTableServiceMapper.selectSample(vo);
	}

}
