/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { useVariableTree } from '@flowgram.ai/form-materials';
import { Tree } from 'antd';

export function FullVariableList() {
  const treeData = useVariableTree({});
  // 遍历 treeData，给每个节点添加 title 属性，其值等于 label 属性
  treeData.forEach((node) => {
    node.title = node.label;
    if (node.children) {
      node.children.forEach((child) => {
        child.title = child.label;
      });
    }
  });
  return <Tree treeData={treeData} />;
}
