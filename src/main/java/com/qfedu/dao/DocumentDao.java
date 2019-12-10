/**
 * 文档管理dao层
 *
 * @author 刘鹏尧
 * @date
 */

package com.qfedu.dao;

import com.qfedu.pojo.Document;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DocumentDao {
    /*查询数据，可以传入条件，不分页*/
    List<Document> findDocumentList(Map<String, Object> condition);
    /*插入数据*/
    int addDocument(Document document);
    /*按照id获取数据*/
    Document getDocumentById(int id);
    /*更新数据*/
    int updateDocumentById(Document document);
    /*删除单个数据*/
    int deleteDocumentById(int id);

    /*批量删除*/
    int batchDel(Integer[] ids);

    /*分页查询*/
    List<Document> page(Map<String, Object> condition);

    /*分页查询配套的查询，用来查询符合条件的记录条数*/
    int pageCount(Map<String, Object> condition);

}
