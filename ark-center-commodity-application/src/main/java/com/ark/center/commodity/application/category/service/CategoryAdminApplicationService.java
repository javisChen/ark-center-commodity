package com.ark.center.commodity.application.category.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.TreeUtil;
import com.ark.center.commodity.client.category.command.CategoryCreateCommand;
import com.ark.center.commodity.client.category.command.CategoryUpdateCommand;
import com.ark.center.commodity.client.category.dto.CategoryDTO;
import com.ark.center.commodity.client.category.dto.TreeDTO;
import com.ark.center.commodity.client.category.dto.TreeifyDTO;
import com.ark.center.commodity.client.category.query.CategoryPageQuery;
import com.ark.center.commodity.dao.entity.CategoryDO;
import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.center.commodity.domain.category.factory.CategoryFactory;
import com.ark.center.commodity.domain.category.repository.CategoryRepository;
import com.ark.center.commodity.infrastructure.category.assembler.CategoryAssembler;
import com.ark.component.dto.PageResponse;
import com.ark.component.web.util.bean.BeanConvertor;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryAdminApplicationService {

    private final CategoryRepository categoryRepository;
    private final CategoryFactory categoryFactory;
    private final CategoryAssembler categoryAssembler;
    ;

    public PageResponse<CategoryDTO> pageList(CategoryPageQuery queryDTO) {
        PageResponse<Category> page = categoryRepository.pageList(queryDTO);
        return categoryAssembler.entityToDTO(page);
    }

    public Long createCategory(CategoryCreateCommand command) {
        Category category = categoryFactory.create(command);
        return categoryRepository.save(category);
    }

    public void updateCategory(CategoryUpdateCommand reqDTO) {
        Category category = categoryAssembler.updateCmdToCategory(reqDTO);
        categoryRepository.update(category);
    }

    public TreeDTO<CategoryDTO> getTree(CategoryPageQuery queryDTO) {
        List<Category> list = categoryRepository.list(queryDTO);
        List<CategoryDTO> categoryDTOList = categoryAssembler.entityToDTO(list);
        return treeify(categoryDTOList);
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

    private<S extends TreeifyDTO>  void fillTree(Map<Long, List<S>> pidMap,
                                                 List<S> sourceList,
                                                 List<TreeDTO.Node<S>> nodes) {
        for (S sourceItem : sourceList) {
            TreeDTO.Node<S> node = new TreeDTO.Node<>(sourceItem);
            List<S> list = pidMap.get(sourceItem.getId());
            if (CollectionUtil.isNotEmpty(list)) {
                node.setNodes(Lists.newArrayListWithCapacity(list.size()));
                fillTree(pidMap, list, node.getNodes());
            }
            nodes.add(node);
        }
    }

    public void removeCategoryById(Long id) {
        categoryRepository.removeCategory(id);
    }

    public CategoryDTO getCategoryInfo(Long id) {
        Category category = categoryRepository.findById(id);
        return categoryAssembler.entityToDTO(category);
    }
}
