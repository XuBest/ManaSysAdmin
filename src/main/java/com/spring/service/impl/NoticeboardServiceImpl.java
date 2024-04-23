package com.spring.service.impl;

import com.base.ServiceBase;
import com.spring.dao.NoticeboardMapper;
import com.spring.entity.Noticeboard;
import com.spring.service.NoticeboardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("noticeboardService")
public class NoticeboardServiceImpl extends ServiceBase<Noticeboard> implements NoticeboardService {
    @Resource
    private NoticeboardMapper dao;

    @Override
    protected NoticeboardMapper getDao(){return dao;}
}
