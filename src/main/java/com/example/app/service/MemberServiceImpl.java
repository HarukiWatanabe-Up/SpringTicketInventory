package com.example.app.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.MemberDao;
import com.example.app.domain.Member;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	//テーブル操作用オブジェクト
	@Autowired
	private MemberDao memberDao;

	//メールアドレスとパスワードが正しいかチェック
	@Override
	public boolean isCorrectEmailAndPassword(String email, String pass) throws Exception {
		Member member = memberDao.selectByEmail(email);
		if (member == null) {
			return false;
		}
		//パスワードはハッシュ化して照合
		if (!BCrypt.checkpw(pass, member.getPassword())) {
			return false;
		}
		return true;
	}

	//一覧を返す
	@Override
	public List<Member> getMemberList() throws Exception {
		return memberDao.selectAll();
	}

	//idを引数に対象のオブジェクトを返す
	@Override
	public Member getMemberById(Integer id) throws Exception {
		return memberDao.selectById(id);
	}

	//idを引数に対象のemailを返す
	@Override
	public Member getMemberByEmail(String email) throws Exception {
		return memberDao.selectByEmail(email);
	}

	//テーブルに追加。パスワードはハッシュ化して登録
	@Override
	public void addMember(Member member) throws Exception {
		member.setPassword(BCrypt.hashpw(member.getPassword(), BCrypt.gensalt()));
		memberDao.insert(member);
	}

	//レコードの変更。パスワードはハッシュ化して登録
	@Override
	public void editMember(Member member) throws Exception {
		member.setPassword(BCrypt.hashpw(member.getPassword(), BCrypt.gensalt()));
		memberDao.update(member);
	}

	//レコードの削除。物理削除
	@Override
	public void deleteMember(Integer id) throws Exception {
		memberDao.delete(id);

	}

	//対象レコードのorder_idに扱いidデータの追加。statusをstaffに更新
	@Override
	public void linkOrder(Member member) throws Exception {
		memberDao.link(member);

	}

	//対象レコードのorder_idをnullに更新。statusをmemberに更新
	@Override
	public void resetOrder(Integer id) throws Exception {
		memberDao.reset(id);
	}

	//ページ分割用
	@Override
	public int getTotalPages(int numPerPage) throws Exception {
		double totalNum = (double) memberDao.count();
		return (int) Math.ceil(totalNum / numPerPage);
	}

	//ページ分割用
	@Override
	public List<Member> getMemberListByPage(int page, int numPerPage) throws Exception {
		int offset = numPerPage * (page - 1);
		return memberDao.selectLimited(offset, numPerPage);
	}

}
