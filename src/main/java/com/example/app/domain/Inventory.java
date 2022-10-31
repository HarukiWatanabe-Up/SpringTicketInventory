package com.example.app.domain;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class Inventory {
//残席数情報クラス

	private Integer id;
	private Schedules schedules;
	private Type type;

	//2次開発追加
	@NotNull
	@Range(min= 0,max = 9999)
	private Integer sheet;
	//↑ここまで

}