package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.MemberDao;
import com.example.app.dao.OrdersDao;
import com.example.app.dao.PaymentDao;
import com.example.app.dao.ReservesDao;
import com.example.app.dao.SchedulesDao;
import com.example.app.dao.TypeDao;
import com.example.app.domain.Member;
import com.example.app.domain.Orders;
import com.example.app.domain.Payment;
import com.example.app.domain.Progress;
import com.example.app.domain.Reserves;
import com.example.app.domain.ReservesForm;
import com.example.app.domain.Schedules;
import com.example.app.domain.Type;

@Service
@Transactional
public class ReservesServiceImpl implements ReservesService {

	//テーブル操作用オブジェクト
	@Autowired
	private ReservesDao reservesDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private OrdersDao ordersDao;
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private SchedulesDao schedulesDao;
	@Autowired
	private TypeDao typeDao;

	//一覧を返す
	@Override
	public List<Reserves> getAllReserves() throws Exception {
		return reservesDao.selectAll();
	}

	//memberIdを引数に対象のListを返す
	@Override
	public List<Reserves> getMemberReserves(Integer memberId) throws Exception {
		return reservesDao.selectMemberReserves(memberId);
	}

	//idを引数に対象のオブジェクトを返す
	@Override
	public Reserves getById(Integer id) throws Exception {
		return reservesDao.selectById(id);
	}

	//テーブルに追加
	@Override
	public void addReserve(Reserves reserves) throws Exception {
		reservesDao.insert(reserves);

	}

	//レコードの変更
	@Override
	public void editReserve(Reserves reserves) throws Exception {
		reservesDao.update(reserves);

	}

	//レコードの削除。物理削除
	@Override
	public void deleteReserve(Reserves reserves) throws Exception {
		reservesDao.delete(reserves);

	}

	//すべての会員情報を取得
	@Override
	public List<Member> getMemberList() throws Exception {
		return memberDao.selectAll();
	}

	//すべての扱いを取得
	@Override
	public List<Orders> getOrdersList() throws Exception {
		return ordersDao.selectAll();
	}

	//すべての支払い方法を取得
	@Override
	public List<Payment> getPaymentList() throws Exception {
		return paymentDao.selectAll();
	}

	//すべての公演回を取得
	@Override
	public List<Schedules> getSchedulesList() throws Exception {
		return schedulesDao.selectAll();
	}

	//すべての券種を取得
	@Override
	public List<Type> getTypeList() throws Exception {
		return typeDao.selectAll();
	}

	//券種と枚数を引数に合計を返す
	@Override
	public int getTotalPrice(Reserves reserves) throws Exception {
		return (int) (reserves.getType().getPrice() * reserves.getAmount());
	}

	//ReservesFormオブジェクト内の各IntegerをSchedulesなどの各オブジェクトに置き換え、
	//Reservesオブジェクトに代入する。
	@Override
	public Reserves changeIdToString(ReservesForm form) throws Exception {
		Reserves reserves = new Reserves();
		Schedules schedules = schedulesDao.selectById(form.getSchedulesId());
		reserves.setSchedules(schedules);
		Type type = typeDao.selectById(form.getTypeId());
		reserves.setType(type);
		Payment payment = paymentDao.selectById(form.getPaymentId());
		reserves.setPayment(payment);
		Orders orders = ordersDao.selectById(form.getOrdersId());
		reserves.setOrders(orders);
		reserves.setAmount(form.getAmount());
		reserves.setNote(form.getNote());
		return reserves;
	}


	//Reservesオブジェクト内の各オブジェクトのidを取得し、
	//ReservesFormオブジェクトに代入する。
	@Override
	public ReservesForm StringToChangeId(Reserves reserves) throws Exception {
		ReservesForm form = new ReservesForm();
		form.setSchedulesId(reserves.getSchedules().getId());
		form.setTypeId(reserves.getType().getId());
		form.setPaymentId(reserves.getPayment().getId());
		form.setOrdersId(reserves.getOrders().getId());
		form.setAmount(reserves.getAmount());
		form.setNote(reserves.getNote());
		return form;
	}

	//ページ分割用（すべての予約情報）
	@Override
	public int getTotalPages(int numPerPage) throws Exception {
		double totalNum = (double) reservesDao.count();
		return (int) Math.ceil(totalNum / numPerPage);
	}

	//ページ分割用（すべての予約情報）
	@Override
	public List<Reserves> getReserveListByPage(int page, int numPerPage) throws Exception {
		int offset = numPerPage * (page - 1);
		return reservesDao.selectLimited(offset, numPerPage);
	}

	//ページ分割用（すべての予約情報）
	@Override
	public List<Reserves> getEachCount() throws Exception {
		return reservesDao.selectEachCount();
	}

	//ページ分割用（扱い別の予約情報（関係者画面用））
	@Override
	public List<Reserves> getReserveOrdersListByPage(int ordersId, int page, int numPerPage) throws Exception {
		int offset = numPerPage * (page - 1);
		return reservesDao.selectLimitedByOrders(ordersId, offset, numPerPage);
	}

	//ページ分割用（扱い別の予約情報（関係者画面用））
	@Override
	public int getTotalPagesByOrders(int ordersId, int numPerPage) throws Exception {
		double totalNum = (double) reservesDao.countByOrders(ordersId);
		return (int) Math.ceil(totalNum / numPerPage);
	}

	//ここから来場者管理画面用↓
	//公演回での絞り込み
	@Override
	public List<Reserves> getReserveListBySchedules(Integer schedulesId) throws Exception {
		return reservesDao.selectBySchedules(schedulesId);
	}
	//来場済みフラグ操作
	@Override
	public void visitedReserve(Integer id) throws Exception {
		reservesDao.visited(id);
	}

	@Override
	public void notVisitedReserve(Integer id) throws Exception {
		reservesDao.notVisited(id);
	}

	//精算済みフラグ操作
	@Override
	public void paidReserve(Integer id) throws Exception {
		reservesDao.paid(id);
	}

	@Override
	public void notPaidReserve(Integer id) throws Exception {
		reservesDao.notPaid(id);
	}

	//来場者集計コンポーネント用
	@Override
	public Progress getProgress(Integer schedulesId) throws Exception {
		Integer stageAmount = 0;
		Integer stageTotal = 0;
		Integer count = 0;
		Integer proceeds = 0;
		for (Reserves reserves : reservesDao.selectBySchedules(schedulesId)) {
			if (reserves.getVisited() == null) {
				stageAmount += reserves.getAmount();
				count++;
			}
			if (reserves.getPaid() == null) {
				stageTotal += reserves.getTotal();
			}
			if (reserves.getPaid() != null) {
				proceeds += reserves.getTotal();
			}
		}
		Progress progress = new Progress();
		progress.count=count;
		progress.stageAmount = stageAmount;
		progress.stageTotal = stageTotal;
		progress.proceeds = proceeds;
		return progress;
	}
	//ここまで来場者管理画面用↑

}
