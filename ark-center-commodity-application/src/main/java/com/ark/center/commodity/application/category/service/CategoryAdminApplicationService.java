package com.ark.center.commodity.application.category.service;

import cn.hutool.core.collection.CollectionUtil;
import com.ark.center.commodity.client.category.command.CategoryCreateCmd;
import com.ark.center.commodity.client.category.command.CategoryUpdateCmd;
import com.ark.center.commodity.client.category.dto.CategoryDTO;
import com.ark.center.commodity.client.category.dto.TreeDTO;
import com.ark.center.commodity.client.category.dto.TreeifyDTO;
import com.ark.center.commodity.client.category.query.CategoryPageQry;
import com.ark.center.commodity.domain.category.aggregate.Category;
import com.ark.center.commodity.domain.category.factory.CategoryFactory;
import com.ark.center.commodity.domain.category.repository.CategoryRepository;
import com.ark.center.commodity.domain.category.assembler.CategoryAssembler;
import com.ark.component.dto.PageResponse;
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

    public PageResponse<CategoryDTO> pageList(CategoryPageQry queryDTO) {
        PageResponse<Category> page = categoryRepository.pageList(queryDTO);
        return categoryAssembler.toDTO(page);
    }
    public Long createCategory(CategoryCreateCmd command) {
        Category category = categoryFactory.create(command);
        return categoryRepository.store(category);
    }
    public void updateCategory(CategoryUpdateCmd reqDTO) {
        Category category = categoryAssembler.updateCmdToCategory(reqDTO);
        categoryRepository.update(category);
    }
    public TreeDTO<CategoryDTO> getTree(CategoryPageQry queryDTO) {
        List<Category> list = categoryRepository.list(queryDTO);
        List<CategoryDTO> categoryDTOList = categoryAssembler.toDTO(list);
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
        categoryRepository.remove(id);
    }

    public CategoryDTO getCategoryInfo(Long id) {
        Category category = categoryRepository.findById(id);
        return categoryAssembler.toDTO(category);
    }
}
