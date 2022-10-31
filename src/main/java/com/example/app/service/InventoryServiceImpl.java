package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.InventoryDao;
import com.example.app.domain.Inventory;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {
	//InventoryServiceの実装クラス。

	//テーブル操作用オブジェクト
	@Autowired
	private InventoryDao inventoryDao;

	//一覧を返す
	@Override
	public List<Inventory> getInventoryList() throws Exception {
		return inventoryDao.selectAll();
	}

	//idを引数に対象のオブジェクトを返す
	@Override
	public Inventory getInventoryById(Integer id) throws Exception {
		return inventoryDao.selectById(id);
	}

	//schedulesIdとtypeIdを引数に対象のオブジェクトを返す
	@Override
	public Inventory getBySchedulesIdAndTypeId(Integer schedulesId, Integer typeId) throws Exception {
		return inventoryDao.selectBySchedulesIdAndTypeId(schedulesId, typeId);
	}

	//予約時、残席数より購入数が少ないかチェック
	@Override
	public boolean checkInventory(Integer schedulesId, Integer typeId, Integer amount) throws Exception {
		Inventory inventory = inventoryDao.selectBySchedulesIdAndTypeId(schedulesId, typeId);
		if ((inventory.getSheet() - amount) < 0) {
			return false;
		}
		return true;
	}

	//テーブルに追加
	@Override
	public void add(Inventory inventory) throws Exception {
		inventoryDao.insert(inventory);
	}

	//レコードの変更
	@Override
	public void update(Inventory inventory) throws Exception {
		inventoryDao.update(inventory);
	}

	//レコードの削除。物理削除
	@Override
	public void delete(Integer id) throws Exception {
		inventoryDao.delete(id);
	}

}
