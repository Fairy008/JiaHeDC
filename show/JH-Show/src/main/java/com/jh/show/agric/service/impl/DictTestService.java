package com.jh.show.agric.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jh.biz.persist.BaseService;
import com.jh.show.agric.service.IDictTestService;
// import com.jh.show.agric.mapping.IDictMapper;
import com.jh.show.agric.entity.DictEntity;

@Service
public class DictTestService extends BaseService<DictEntity> implements IDictTestService{
	// @Autowired
	// private IDictMapper dictMapper;
}