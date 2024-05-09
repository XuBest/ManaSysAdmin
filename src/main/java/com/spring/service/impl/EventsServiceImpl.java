package com.spring.service.impl;

import com.base.ServiceBase;
import com.spring.dao.EventsMapper;
import com.spring.entity.Events;
import com.spring.service.EventsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("EventsService")
public class EventsServiceImpl extends ServiceBase<Events> implements EventsService {
    @Resource
    private EventsMapper dao;

    @Override
    protected EventsMapper getDao() { return dao; }
}
