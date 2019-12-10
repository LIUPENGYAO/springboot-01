/**
 * 文档管理service层实现类
 *
 * @author 刘鹏尧
 * @date
 */
package com.qfedu.service;

import com.qfedu.dao.DocumentDao;
import com.qfedu.pojo.Document;
import com.qfedu.pojo.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("documentService") //service实例创建出来默认是单例，不能在其中定义private User user;等，多线程下会相互修改
//与@Resource(name = "documentService") 要么都指定名字要么都不指定 会按类型
@Transactional
public class DocumentServiceImpl implements DocumentService {
    private static final Logger logger = LogManager.getLogger(DocumentServiceImpl.class);


    //DocumentDao documentDao = new DocumentDaoImpl();
    @Autowired
    //@Resource(name = "documentDao")
    private DocumentDao documentDao;

    public void setDocumentDao(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }


    public List<Document> findDocumentList(Map<String,Object> condition) {

        return documentDao.findDocumentList(condition);
    }

    public Document getDocumentById(int id) {

        return documentDao.getDocumentById(id);
    }

    public int deleteDocumentById(int id) {
        return documentDao.deleteDocumentById(id);
    }

    @Override
    public int addDocument(Document document) {
        return documentDao.addDocument(document);
    }

    @Override
    public int updateDocumentById(Document document) {
        return documentDao.updateDocumentById(document);
    }

    @Override
    public int batchDel(Integer[] ids) {
        return documentDao.batchDel(ids);
    }

    @Override
    public Page page(Map<String, Object> condition) {
        if(condition.get("pageNum")==null) {
            condition.put("pageNum","1");
        }
        if(condition.get("pageSize")==null) { //或者
            condition.put("pageSize","5");
        }
        /*获取pageNum和pageSize*/
        int pageNum=Integer.parseInt((String)condition.get("pageNum"));
        int pageSize=Integer.parseInt((String)condition.get("pageSize"));

        /*查询符合条件的数据有多少*/
        int pageCount = documentDao.pageCount(condition);
        //查询总共能分几页  在page类里计算
        //int totalPage = pageCount % pageSize == 0 ? pageCount / pageSize : pageCount / pageSize + 1;
/*        //翻页时要对pageNum限制 在前台限制
        if(pageNum>=totalPage && totalPage > 0){
            pageNum = totalPage;
        }else if(pageNum<=1){
            pageNum = 1;
        }*/


        /*实例化一个page对象，用来存放分页相关的内容*/
        /*page里保存了pageNum、pageSiez，在构造方法中根据这两个参数计算了startIndex*/
        Page page=new Page(pageNum,pageSize);

        /*放入计算好的startIndex*/
        condition.put("startIndex", page.getStartIndex());
        /*由于原来传入pageSiez是字符串类型的，所以此处用int型的进行覆盖*/
        condition.put("pageSize",pageSize);

        Set<Map.Entry<String, Object>> entries = condition.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            logger.info(entry.getKey()+";"+entry.getValue());
        }
        /*查询对应的数据*/
        page.setResult(documentDao.page(condition));
        page.setTotal(pageCount);

        return page;
    }

    @Override
    public int pageCount(Map<String, Object> condition) {
        return documentDao.pageCount(condition);
    }


}
