/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { useVariableTree } from '@flowgram.ai/form-materials';
import { Tree } from 'antd';

export function FullVariableList() {
  const treeData = useVariableTree({});

  return <Tree treeData={treeData} />;
}
