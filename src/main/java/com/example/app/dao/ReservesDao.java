package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Reserves;

@Mapper
public interface ReservesDao {
//MyBatisとの連携用。予約情報テーブルの操作
	List<Reserves>selectAll() throws Exception;
	List<Reserves>selectMemberReserves(Integer memberId) throws Exception;
	Reserves selectById(Integer id)throws Exception;
	void insert(Reserves reserves)throws Exception;
	void update(Reserves reserves)throws Exception;
	void delete(Reserves reserves)throws Exception;

//来場済み、精算済みのフラグ操作
	void visited(Integer id)throws Exception;
	void notVisited(Integer id)throws Exception;
	void paid(Integer id)throws Exception;
	void notPaid(Integer id)throws Exception;

//公演回で絞り込み表示用
	List<Reserves>selectBySchedules(Integer schedulesId)throws Exception;

//売り上げ状況ビューの呼び出し
	List<Reserves>selectEachCount() throws Exception;

//ページ分割表示用（管理者画面）
	Long count()throws Exception;
	List<Reserves>selectLimited(@Param("offset")int offset,
			@Param("numPerPage")int numPerPage)throws Exception;

//ページ分割表示用（関係者画面）
	Long countByOrders(Integer ordersId)throws Exception;
	List<Reserves>selectLimitedByOrders(@Param("id")int id,@Param("offset")int offset,
			@Param("numPerPage")int numPerPage)throws Exception;



}
