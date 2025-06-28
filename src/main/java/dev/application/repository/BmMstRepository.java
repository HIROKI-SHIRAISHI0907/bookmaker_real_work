package dev.application.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import dev.application.entity.BookDataInsertEntity;

@Mapper
public interface BmMstRepository {

	/** DB登録 */
	public int soccerBmInsert(List<BookDataInsertEntity> entity);

}
