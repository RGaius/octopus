/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { useCallback } from 'react';

import { usePlayground } from '@flowgram.ai/free-layout-editor';
import { IconButton, Tooltip } from 'antd';
import { IconUnlock, IconLock } from '@douyinfe/semi-icons';

export const Readonly = () => {
  const playground = usePlayground();
  const toggleReadonly = useCallback(() => {
    playground.config.readonly = !playground.config.readonly;
  }, [playground]);
  return playground.config.readonly ? (
    <Tooltip title="Editable">
      <IconButton
        theme="borderless"
        type="tertiary"
        icon={<IconLock size="default" />}
        onClick={toggleReadonly}
      />
    </Tooltip>
  ) : (
    <Tooltip title="Readonly">
      <IconButton
        theme="borderless"
        type="tertiary"
        icon={<IconUnlock size="default" />}
        onClick={toggleReadonly}
      />
    </Tooltip>
  );
};
