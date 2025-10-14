/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { usePlaygroundTools } from '@flowgram.ai/free-layout-editor';
import { Button, Tooltip } from 'antd';
import { ExpandAltOutlined } from '@ant-design/icons';

export const FitView = () => {
  const tools = usePlaygroundTools();
  return (
    <Tooltip title="FitView">
      <Button
        icon={<ExpandAltOutlined />}
        type="text"
        onClick={() => tools.fitView()}
      />
    </Tooltip>
  );
};
