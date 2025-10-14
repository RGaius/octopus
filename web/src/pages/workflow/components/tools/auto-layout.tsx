/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { useCallback } from 'react';

import { usePlayground, usePlaygroundTools } from '@flowgram.ai/free-layout-editor';
import { Button, Tooltip } from 'antd';

import { IconAutoLayout } from '../../assets/icon-auto-layout';

export const AutoLayout = () => {
  const tools = usePlaygroundTools();
  const playground = usePlayground();
  const autoLayout = useCallback(async () => {
    await tools.autoLayout({
      enableAnimation: true,
      animationDuration: 1000,
    });
  }, [tools]);

  return (
    <Tooltip title={'Auto Layout'}>
      <Button
        disabled={playground.config.readonly}
        type="text"
        onClick={autoLayout}
        icon={IconAutoLayout}
      />
    </Tooltip>
  );
};
