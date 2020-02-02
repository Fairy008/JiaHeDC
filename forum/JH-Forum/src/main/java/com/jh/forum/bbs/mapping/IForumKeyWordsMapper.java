package com.jh.forum.bbs.mapping;

import com.jh.forum.bbs.entity.ForumKeyWords;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IForumKeyWordsMapper {

    List<ForumKeyWords> getKeyWords();

}
