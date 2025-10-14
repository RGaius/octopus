/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { useCallback } from 'react';

import { useService, WorkflowLinesManager } from '@flowgram.ai/free-layout-editor';
import { Button, Tooltip } from 'antd';

import { IconSwitchLine } from '../../assets/icon-switch-line';

export const SwitchLine = () => {
  const linesManager = useService(WorkflowLinesManager);
  const switchLine = useCallback(() => {
    linesManager.switchLineType();
  }, [linesManager]);

  return (
    <Tooltip title={'Switch Line'}>
      <Button type="text" onClick={switchLine} icon={IconSwitchLine} />
    </Tooltip>
  );
};
