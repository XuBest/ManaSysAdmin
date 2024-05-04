package com.spring.service.impl;

import com.base.ServiceBase;
import com.spring.dao.TicketMapper;
import com.spring.entity.Ticket;
import com.spring.service.TicketService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("TicketService")
public class TicketServiceImpl extends ServiceBase<Ticket> implements TicketService {
    @Resource
    private TicketMapper dao;

    @Override
    protected TicketMapper getDao(){
        return dao;
    }
}
