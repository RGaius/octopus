/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { FunctionComponent } from 'react';

import { SelectorBoxPopoverProps } from '@flowgram.ai/free-layout-editor';
import { WorkflowGroupCommand } from '@flowgram.ai/free-group-plugin';
import { Button, Tooltip } from 'antd'
import { CopyOutlined, DeleteOutlined, ExpandOutlined, ShrinkOutlined } from '@ant-design/icons';

import { IconGroup } from '../group';
import { FlowCommandId } from '../../shortcuts/constants';
import ButtonGroup from 'antd/es/button/button-group';

const BUTTON_HEIGHT = 24;

export const SelectorBoxPopover: FunctionComponent<SelectorBoxPopoverProps> = ({
  bounds,
  children,
  flowSelectConfig,
  commandRegistry,
}) => (
  <>
    <div
      style={{
        position: 'absolute',
        left: bounds.right,
        top: bounds.top,
        transform: 'translate(-100%, -100%)',
      }}
      onMouseDown={(e) => {
        e.stopPropagation();
      }}
    >
      <ButtonGroup
        size="small"
        style={{ display: 'flex', flexWrap: 'nowrap', height: BUTTON_HEIGHT }}
      >
        <Tooltip title={'Collapse'}>
          <Button
            icon={<ShrinkOutlined />}
            style={{ height: BUTTON_HEIGHT }}
            type="primary"
            onMouseDown={(e) => {
              commandRegistry.executeCommand(FlowCommandId.COLLAPSE);
            }}
          />
        </Tooltip>

        <Tooltip title={'Expand'}>
          <Button
            icon={<ExpandOutlined />}
            style={{ height: BUTTON_HEIGHT }}
            type="primary"
            onMouseDown={(e) => {
              commandRegistry.executeCommand(FlowCommandId.EXPAND);
            }}
          />
        </Tooltip>

        <Tooltip title={'Create Group'}>
          <Button
            icon={<IconGroup size={14} />}
            style={{ height: BUTTON_HEIGHT }}
            type="primary"
            onClick={() => {
              commandRegistry.executeCommand(WorkflowGroupCommand.Group);
            }}
          />
        </Tooltip>

        <Tooltip title={'Copy'}>
          <Button
            icon={<CopyOutlined />}
            style={{ height: BUTTON_HEIGHT }}
            type="primary"
            onClick={() => {
              commandRegistry.executeCommand(FlowCommandId.COPY);
            }}
          />
        </Tooltip>

        <Tooltip title={'Delete'}>
          <Button
            type="primary"
            icon={<DeleteOutlined />}
            style={{ height: BUTTON_HEIGHT }}
            onClick={() => {
              commandRegistry.executeCommand(FlowCommandId.DELETE);
            }}
          />
        </Tooltip>
      </ButtonGroup>
    </div>
    <div>{children}</div>
  </>
);
