package com.spring.service.impl;

import com.base.ServiceBase;
import com.spring.dao.TicketinfoMapper;
import com.spring.entity.Ticketinfo;
import com.spring.service.TicketinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service("TicketinfoService")
public class TicketinfoServiceImpl  extends ServiceBase<Ticketinfo> implements TicketinfoService {
    @Resource
    private TicketinfoMapper dao;

    @Override
    protected TicketinfoMapper getDao() {
        return dao;
    }
}
