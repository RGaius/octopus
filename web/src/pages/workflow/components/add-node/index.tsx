/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { Button } from "antd";
import { PlusOutlined } from "@ant-design/icons";

import { useAddNode } from "./use-add-node";

export const AddNode = (props: { disabled: boolean }) => {
  const addNode = useAddNode();
  return (
    <Button
      data-testid="demo.free-layout.add-node"
      color="primary"
      icon={<PlusOutlined />}
      style={{ backgroundColor: "rgba(171,181,255,0.3)", borderRadius: "8px" }}
      disabled={props.disabled}
      onClick={(e) => {
        const rect = e.currentTarget.getBoundingClientRect();
        addNode(rect);
      }}
    >
      Add Node
    </Button>
  );
};
