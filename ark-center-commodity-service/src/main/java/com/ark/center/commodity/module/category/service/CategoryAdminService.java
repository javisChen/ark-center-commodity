package com.ark.center.commodity.module.category.service;

import cn.hutool.core.collection.CollectionUtil;
import com.ark.center.commodity.dao.entity.CategoryDO;
import com.ark.center.commodity.dao.mapper.CategoryMapper;
import com.ark.center.commodity.module.category.dto.request.CategoryPageQueryReqDTO;
import com.ark.center.commodity.module.category.dto.request.CategoryUpdateReqDTO;
import com.ark.center.commodity.module.category.dto.response.CategoryRespDTO;
import com.ark.center.commodity.module.category.dto.response.TreeDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.ark.component.dto.PageResponse;
import com.ark.component.orm.mybatis.base.BaseEntity;
import com.ark.component.web.util.bean.BeanConvertor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品类目 服务实现类
 * </p>
 *
 * @author EOP
 * @since 2022-03-03
 */
@Service
public class CategoryAdminService extends ServiceImpl<CategoryMapper, CategoryDO> implements IService<CategoryDO> {

    @Transactional(rollbackFor = Exception.class)
    public Long createCategory(CategoryUpdateReqDTO reqDTO) {
        CategoryDO entity = BeanConvertor.copy(reqDTO, CategoryDO.class);
        entity.setId(null);
        save(entity);
        // 更新分类级别路径
        updateLevelAndLevelPath(entity);
        return entity.getId();
    }

    private void updateLevelAndLevelPath(CategoryDO entity) {
        String levelPath = "";
        int level = 1;
        if (entity.getPid() == null || entity.getPid() == 0) {
            levelPath = entity.getId() + ".";
        } else {
            CategoryDO parent = getById(entity.getPid());
            levelPath = parent.getLevelPath() + entity.getId() + ".";
            level = parent.getLevel() + 1;
        }
        CategoryDO updateEntity = new CategoryDO();
        updateEntity.setId(entity.getId());
        updateEntity.setLevelPath(levelPath);
        updateEntity.setLevel(level);
        updateById(updateEntity);
    }

    public PageResponse<CategoryRespDTO> getPageList(CategoryPageQueryReqDTO queryDTO) {
        IPage<CategoryRespDTO> page = lambdaQuery()
                .eq(Objects.nonNull(queryDTO.getLevel()), CategoryDO::getLevel, queryDTO.getLevel())
                .eq(Objects.nonNull(queryDTO.getPid()), CategoryDO::getPid, queryDTO.getPid())
                .orderByDesc(BaseEntity::getGmtCreate)
                .page(new Page<>(queryDTO.getCurrent(), queryDTO.getSize()))
                .convert(item -> BeanConvertor.copy(item, CategoryRespDTO.class));
        return BeanConvertor.copyPage(page, CategoryRespDTO.class);
    }

    public Long updateCategory(CategoryUpdateReqDTO reqDTO) {
        CategoryDO entity = BeanConvertor.copy(reqDTO, CategoryDO.class);
        updateById(entity);
        return entity.getId();
    }

    public CategoryRespDTO getCategoryInfo(Long CategoryId) {
        CategoryDO entity = getById(CategoryId);
        return BeanConvertor.copy(entity, CategoryRespDTO.class);
    }

    public TreeDTO<CategoryRespDTO> getTree(CategoryPageQueryReqDTO queryDTO) {
        List<CategoryDO> list = list();
        Map<Long, List<CategoryDO>> pidMap = list.stream().collect(Collectors.groupingBy(CategoryDO::getPid));
        List<CategoryDO> level1 = list.stream().filter(item -> item.getLevel().equals(1)).collect(Collectors.toList());
        TreeDTO<CategoryRespDTO> treeDTO = new TreeDTO<>();
        List<TreeDTO.Node<CategoryRespDTO>> nodes = new ArrayList<>();
        treeDTO.setNodes(nodes);
        fill(pidMap, level1, nodes);
        return treeDTO;
    }

    private void fill(Map<Long, List<CategoryDO>> pidMap, List<CategoryDO> categoryDOList, List<TreeDTO.Node<CategoryRespDTO>> nodes) {
        for (CategoryDO categoryDO : categoryDOList) {
            TreeDTO.Node<CategoryRespDTO> node = new TreeDTO.Node<>();
            node.setName(categoryDO.getName());
            node.setId(categoryDO.getId());
            node.setPid(categoryDO.getPid());
            List<CategoryDO> r = pidMap.get(categoryDO.getId());
            if (CollectionUtil.isNotEmpty(r)) {
                node.setNodes(Lists.newArrayListWithCapacity(r.size()));
                fill(pidMap, r, node.getNodes());
            }
            nodes.add(node);
        }
    }

    public void removeCategoryById(Long id) {
        CategoryDO categoryDO = getById(id);
        if (categoryDO != null) {
            lambdaUpdate()
                    .likeRight(CategoryDO::getLevelPath, categoryDO.getId())
                    .remove();
        }
    }

}
