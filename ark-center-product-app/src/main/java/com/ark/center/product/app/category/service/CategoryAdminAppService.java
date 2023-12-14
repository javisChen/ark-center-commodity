package com.ark.center.product.app.category.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.ark.center.product.client.category.command.CategoryCreateCmd;
import com.ark.center.product.client.category.dto.CategoryDTO;
import com.ark.center.product.client.category.dto.TreeDTO;
import com.ark.center.product.client.category.dto.TreeifyDTO;
import com.ark.center.product.client.category.query.CategoryPageQry;
import com.ark.center.product.domain.category.Category;
import com.ark.center.product.domain.category.gateway.CategoryGateway;
import com.ark.center.product.infra.category.convertor.CategoryConvertor;
import com.ark.component.dto.PageResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryAdminAppService {

    private final CategoryGateway categoryGateway;

    private final CategoryConvertor categoryConvertor;

    public PageResponse<CategoryDTO> queryPages(CategoryPageQry queryDTO) {
        IPage<CategoryDTO> page = categoryGateway.selectPages(queryDTO);
        return PageResponse.of(page);
    }

    public Long save(CategoryCreateCmd command) {
        Category category = categoryConvertor.toCategory(command);
        if (category.getId() == null) {
            category.setCode(generateCategoryCode());
            categoryGateway.insert(category);
            // 默认层级路径是 -> "id."
            String levelPath = category.getId() + ".";
            int level = 1;
            // 如果非根节点的话，取上级的层级信息计算出当前的层级信息
            if (!isRootCategory(category)) {
                Category parentCategory = categoryGateway.selectById(category.getPid());
                levelPath = parentCategory.getLevelPath() + levelPath;
                level = parentCategory.getLevel() + 1;
            }
            category.setLevel(level);
            category.setLevelPath(levelPath);
            categoryGateway.update(category);
        } else {
            categoryGateway.update(category);
        }
        return category.getId();
    }

    private boolean isRootCategory(Category category) {
        return category.getPid() == null || category.getPid().equals(0L);
    }

    private String generateCategoryCode() {
        return RandomUtil.randomString(8);
    }

    public TreeDTO<CategoryDTO> getTree(CategoryPageQry queryDTO) {
        List<CategoryDTO> list = categoryGateway.selectList(queryDTO);
        return treeify(list);
    }

    public <S extends TreeifyDTO> TreeDTO<S> treeify(List<S> sourceList) {
        Map<Long, List<S>> groupByParentMap = sourceList.stream()
                .collect(Collectors.groupingBy(S::getPid));

        List<S> level1 = sourceList.stream()
                .filter(item -> item.getLevel().equals(1))
                .collect(Collectors.toList());

        TreeDTO<S> treeDTO = new TreeDTO<>();
        List<TreeDTO.Node<S>> nodes = new ArrayList<>();
        fillTree(groupByParentMap, level1, nodes);
        treeDTO.setNodes(nodes);
        return treeDTO;
    }

    private <S extends TreeifyDTO> void fillTree(Map<Long, List<S>> pidMap,
                                                 List<S> sourceList,
                                                 List<TreeDTO.Node<S>> nodes) {
        for (S sourceItem : sourceList) {
            TreeDTO.Node<S> node = new TreeDTO.Node<>(sourceItem.getId(), sourceItem.getName(),
                    sourceItem.getPid(), sourceItem.getCode());
            List<S> list = pidMap.get(sourceItem.getId());
            if (CollectionUtil.isNotEmpty(list)) {
                node.setNodes(Lists.newArrayListWithCapacity(list.size()));
                fillTree(pidMap, list, node.getNodes());
            }
            nodes.add(node);
        }
    }

    public void removeCategoryById(Long id) {
        categoryGateway.remove(id);
    }

    public CategoryDTO queryDetails(Long id) {
        Category category = categoryGateway.selectById(id);
        return categoryConvertor.toDTO(category);
    }
}
