/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { useState } from "react";

import {
  usePlayground,
  usePlaygroundTools,
} from "@flowgram.ai/free-layout-editor";
import { Dropdown } from "antd";
import type { MenuProps } from 'antd';

import { SelectZoom } from "./styles";

export const ZoomSelect = () => {
  const tools = usePlaygroundTools({ maxZoom: 2, minZoom: 0.25 });
  const playground = usePlayground();
  const [dropDownVisible, openDropDown] = useState(false);
  const items: MenuProps["items"] = [
    {
      key: "1",
      label: <a onClick={() => tools.zoomin()}>Zoom in</a>,
    },
    {
      key: "2",
      label: <a onClick={() => tools.zoomout()}>Zoom out</a>,
    },
    {
      key: "3",
      label: (
        <a onClick={() => playground.config.updateZoom(0.5)}>Zoom to 50%</a>
      ),
    },
    {
      key: "4",
      label: (
        <a onClick={() => playground.config.updateZoom(1)}>Zoom to 100%</a>
      ),
    },
    {
      key: "5",
      label: (
        <a onClick={() => playground.config.updateZoom(1.5)}>Zoom to 150%</a>
      ),
    },
    {
      key: "6",
      label: (
        <a onClick={() => playground.config.updateZoom(2.0)}>Zoom to 200%</a>
      ),
    },
  ];
  return (
    <Dropdown
      placement="top"
      trigger={["click"]}
      open={dropDownVisible}
      onOpenChange={() => openDropDown(false)}
      menu={{ items }}
    >
      <SelectZoom onClick={() => openDropDown(true)}>
        {Math.floor(tools.zoom * 100)}%
      </SelectZoom>
    </Dropdown>
  );
};
