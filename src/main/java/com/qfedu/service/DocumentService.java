/**
 * 文档管理service层接口
 *
 * @author 刘鹏尧
 * @date
 */
package com.qfedu.service;

import com.qfedu.pojo.Document;
import com.qfedu.pojo.Page;

import java.util.List;
import java.util.Map;

public interface DocumentService {

    List<Document> findDocumentList(Map<String, Object> condition);

    Document getDocumentById(int id);

    int deleteDocumentById(int id);

    int addDocument(Document document);

    int updateDocumentById(Document document);

    int batchDel(Integer[] ids);

    /*分页查询*/
    Page page(Map<String, Object> condition);

    /*分页查询配套的查询，用来查询符合条件的记录条数*/
    int pageCount(Map<String, Object> condition);

}
