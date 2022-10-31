package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Payment;

@Mapper
public interface PaymentDao {
//MyBatisとの連携用。支払い方法テーブルの操作
	List<Payment>selectAll() throws Exception;
	Payment selectById(Integer id) throws Exception;
	void insert(Payment payment)throws Exception;
	void update(Payment payment)throws Exception;
	void delete(Integer id)throws Exception;


}
