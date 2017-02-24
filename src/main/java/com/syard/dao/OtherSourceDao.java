package com.syard.dao;

import java.util.List;

import com.github.abel533.mapper.Mapper;
import com.syard.pojo.OtherSource;

public interface OtherSourceDao extends Mapper<OtherSource>{

	List<OtherSource> getHotMsgData();

	List<OtherSource> getNewsMsgData();

}
