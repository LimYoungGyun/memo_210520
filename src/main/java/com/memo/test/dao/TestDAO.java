package com.memo.test.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface TestDAO {

	public List<Map<String, Object>> selectUserList();
}
