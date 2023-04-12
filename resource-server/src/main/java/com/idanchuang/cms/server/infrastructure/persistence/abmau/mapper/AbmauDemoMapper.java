package com.idanchuang.cms.server.infrastructure.persistence.abmau.mapper;

import com.idanchuang.resource.server.infrastructure.persistence.model.ArticleSubjectDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2021-11-30 09:35
 * @Desc:
 * @Copyright VTN Limited. All rights reserved.
 */
@Mapper
public interface AbmauDemoMapper {

    String DEFAULT_COLUMN_LIST = "id,date_format(publish_at,'%M,%d,%Y') as publish_at,image,like_count,win_title," +
            "content,title,sub_title";

    String TABLE_NAME = "w_article_subject";

    @Select("<script>" +
            "select " + DEFAULT_COLUMN_LIST + " from " + TABLE_NAME +
            " where id = #{id} " +
            "</script>")
    ArticleSubjectDO queryBySubjectId(@Param("id") Long id);
}
