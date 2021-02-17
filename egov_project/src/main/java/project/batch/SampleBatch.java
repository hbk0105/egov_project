package project.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import egovframework.example.sample.service.LockTableService;

public class SampleBatch {
	
	private static final String ONE_MIN = "PT30S"; // 1분동안 Lock
	
	@Resource(name = "lockTableService")
	private LockTableService lockTableService;
	
	
	@Scheduled(cron = "0/30 * * * * ?")
    public void getSomething() throws Exception {
		
		// 서버 이중화로 인해, 배치가 두번 실행 되는 경우는lock 테이블로 처리 !
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("email", "play-batch");
		map.put("id", "1");
		
		Map<String,Object> data = lockTableService.selectSample(map);
		if(data != null && "Y".equals(data.get("use_yn"))) {
			map.put("useYn", "N");
			lockTableService.updateSample(map);
		}else {
			map.put("useYn", "Y");
			lockTableService.updateSample(map);
		}
		
    }
	
	/*
		   create table lock_table (
		       id bigint not null,
		        check_data_time timestamp,
		        email varchar(255),
		        use_yn varchar(255) not null,
		        primary key (id)
		    )
	
	
			 insert into
	            lock_table
	            (check_data_time, email, use_yn, id) 
	        values
	            (now(), 'play-batch', 'Y', 1)
	
	
	*/
}
