/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { FC, useCallback, useState, type MouseEvent } from "react";

import {
  delay,
  useClientContext,
  usePlaygroundTools,
  useService,
  WorkflowDragService,
  WorkflowNodeEntity,
  WorkflowSelectService,
} from "@flowgram.ai/free-layout-editor";
import { NodeIntoContainerService } from "@flowgram.ai/free-container-plugin";
import { Dropdown, Button } from "antd";
import type { MenuProps } from "antd";
import { MoreOutlined } from "@ant-design/icons";

import { FlowNodeRegistry } from "../../typings";
import { PasteShortcut } from "../../shortcuts/paste";
import { CopyShortcut } from "../../shortcuts/copy";

interface NodeMenuProps {
  node: WorkflowNodeEntity;
  updateTitleEdit?: (setEditing: boolean) => void;
  deleteNode: () => void;
}

export const NodeMenu: FC<NodeMenuProps> = ({
  node,
  deleteNode,
  updateTitleEdit,
}) => {
  const [visible, setVisible] = useState(true);
  const clientContext = useClientContext();
  const registry = node.getNodeRegistry<FlowNodeRegistry>();
  const nodeIntoContainerService = useService(NodeIntoContainerService);
  const selectService = useService(WorkflowSelectService);
  const dragService = useService(WorkflowDragService);
  const canMoveOut = nodeIntoContainerService.canMoveOutContainer(node);
  const tools = usePlaygroundTools();

  const rerenderMenu = useCallback(() => {
    // force destroy component - 强制销毁组件触发重新渲染
    setVisible(false);
    requestAnimationFrame(() => {
      setVisible(true);
    });
  }, []);

  const handleMoveOut = useCallback(
    async (e: MouseEvent) => {
      e.stopPropagation();
      const sourceParent = node.parent;
      // move out of container - 移出容器
      nodeIntoContainerService.moveOutContainer({ node });
      await delay(16);
      // clear invalid lines - 清除非法线条
      await nodeIntoContainerService.clearInvalidLines({
        dragNode: node,
        sourceParent,
      });
      rerenderMenu();
      // select node - 选中节点
      selectService.selectNode(node);
      // start drag node - 开始拖拽
      dragService.startDragSelectedNodes(e);
    },
    [nodeIntoContainerService, node, rerenderMenu]
  );

  const handleCopy = useCallback(
    (e: React.MouseEvent) => {
      const copyShortcut = new CopyShortcut(clientContext);
      const pasteShortcut = new PasteShortcut(clientContext);
      const data = copyShortcut.toClipboardData([node]);
      pasteShortcut.apply(data);
      e.stopPropagation(); // Disable clicking prevents the sidebar from opening
    },
    [clientContext, node]
  );

  const handleDelete = useCallback(
    (e: React.MouseEvent) => {
      deleteNode();
      e.stopPropagation(); // Disable clicking prevents the sidebar from opening
    },
    [clientContext, node]
  );
  const handleEditTitle = useCallback(
    (e: React.MouseEvent) => {
      updateTitleEdit?.(true);
      e.stopPropagation(); // Disable clicking prevents the sidebar from opening
    },
    [updateTitleEdit]
  );

  const handleAutoLayout = useCallback(
    (e: React.MouseEvent) => {
      e.stopPropagation(); // Disable clicking prevents the sidebar from opening
      tools.autoLayout({
        containerNode: node,
        enableAnimation: true,
        animationDuration: 1000,
        disableFitView: true,
      });
    },
    [tools]
  );

  if (!visible) {
    return <></>;
  }
  const items: MenuProps["items"] = [
    {
      key: "edit",
      label: (
        <Button onClick={handleEditTitle} type="text">
          Edit Title
        </Button>
      ),
    },
    canMoveOut
      ? {
          key: "moveOut",
          label: (
            <Button onClick={handleMoveOut} type="text">
              Move out
            </Button>
          ),
        }
      : null,
    {
      key: "copy",
      label: (
        <Button
          onClick={handleCopy}
          disabled={registry.meta!.copyDisable === true}
          type="text"
        >
          Create Copy
        </Button>
      ),
    },
    registry.meta.isContainer
      ? {
          key: "autoLayout",
          label: (
            <Button onClick={handleAutoLayout} type="text">
              Auto Layout
            </Button>
          ),
        }
      : null,
    {
      key: "delete",
      label: (
        <Button
          onClick={handleDelete}
          type="text"
          disabled={
            !!(
              registry.canDelete?.(clientContext, node) ||
              registry.meta!.deleteDisable
            )
          }
        >
          Delete
        </Button>
      ),
    },
  ];

  return (
    <Dropdown trigger={["hover"]} placement="bottomRight" menu={{ items }}>
      <Button
        type="text"
        icon={<MoreOutlined  style={{ color: "blue" }} />}
        onClick={(e) => e.stopPropagation()}
      />
    </Dropdown>
  );
};
