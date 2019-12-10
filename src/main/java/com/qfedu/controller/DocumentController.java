/**
 * 文档管理控制器
 *
 * @author 刘鹏尧
 * @date
 */
package com.qfedu.controller;

import com.qfedu.pojo.Document;
import com.qfedu.pojo.Page;
import com.qfedu.service.DocumentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/*Spring的组件扫描，如果发现这个注解，就会实例化这个类，然后放入Spring容器*/
@Controller
@RequestMapping("/document")
public class DocumentController {

    //DocumentService documentService=new DocumentServiceImpl();
    //@Autowired
    @Resource(name = "documentService")
    private DocumentService documentService;


  /*  @Value("${IMG_URL}")
    String imgUrl;*/


    /**
     * 分页查询：前端必须传入 pageNum和pageSize两个参数
     * 接受到的搜索条件 如果什么都不输入，为""
     *   pageNum：页
     *   pageSize：每页显示多少行
     * @param condition：放的是前端传入的相关参数
     * @param model
     * @return
     */
    @RequestMapping("/page")
    public String page(@RequestParam Map<String,Object> condition, Model model){
        Page page=documentService.page(condition);
        model.addAttribute("page",page);//@RequestParam是从前端接受的参数 model是向jsp传参数的

        model.addAttribute("docname", condition.get("docname"));
        model.addAttribute("upname", condition.get("upname"));
        model.addAttribute("doctype", condition.get("doctype"));
        return "documentList";
    }

    /**
     * 跳转到新增/修改页面
     * @return
     */
    @RequestMapping("/showAdd")
    public String showAdd(Model model){
        Document document = new Document();
        model.addAttribute("document",document); //th:value="${document.docname}" 如果document不存在 直接报错?
        return "addDocument";
    }


    /**
     * 新增/修改方法
     * @param document
     * @return
     */
    @RequestMapping("/save")
    public String saveOrUpdate(Document document){

        if(document.getId() != null) {
            documentService.updateDocumentById(document);
        }
        else
        documentService.addDocument(document);
        //return "redirect:/document/list";
        return "redirect:/document/page";
    }


    /**
     * 修改时查询该文档信息，回显
     * @param ids
     * @param model
     * @return
     */
    @RequestMapping("/edit")
    public String showEdit( Integer[] ids, Model model){

        Document document =  documentService.getDocumentById(ids[0]);

        if(document != null){
            model.addAttribute("document",document);
            return "addDocument";
        }else{
            model.addAttribute("msg","没有该文档信息");
            return "addDocument";
        }
    }

/*
    @RequestMapping("/delete")
    public String delete(@RequestParam(name="_id",defaultValue ="0",required = true) int id){

        System.out.println(id);

        documentService.deleteDocumentById(id);
        return "redirect:/document/page";
       // return "redirect:/document/list";

    }*/
    /**
     * 删除方法,支持批量删除
     * @param
     * @return
     */
    @RequestMapping("/batchDel")
    public String batchDel(Integer[] ids){
        String s = Arrays.toString(ids).replace("[","(").replace("]",")");
        documentService.batchDel(ids);
        return "redirect:/document/page";
    }

}
