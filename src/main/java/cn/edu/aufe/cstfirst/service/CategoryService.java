package cn.edu.aufe.cstfirst.service;

import cn.edu.aufe.cstfirst.domian.Category;

import java.util.List;

/**
 * @author zhuwenwen
 * @date 2019/9/5 22:10
 **/
public interface CategoryService {

    /**
     * 查询所有分类
     *
     * @return {@link List<Category>}
     */
    List<Category> listCategories();


}
