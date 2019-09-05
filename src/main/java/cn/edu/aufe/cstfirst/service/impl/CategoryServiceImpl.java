package cn.edu.aufe.cstfirst.service.impl;

import cn.edu.aufe.cstfirst.domian.Category;
import cn.edu.aufe.cstfirst.domian.CategoryExample;
import cn.edu.aufe.cstfirst.mapper.CategoryMapper;
import cn.edu.aufe.cstfirst.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhuwenwen
 * @date 2019/9/5 22:10
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper cateGoryMapper;

    @Override
    public List<Category> listCategories() {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andValidEqualTo(1);
        return cateGoryMapper.selectByExample(example);
    }
}
