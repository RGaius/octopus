/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { useState } from "react";

import { Button, Tabs, Tooltip } from "antd";
import { MinusOutlined } from "@ant-design/icons";

import iconVariable from "../../../assets/icon-variable.png";
import { GlobalVariableEditor } from "./global-variable-editor";
import { FullVariableList } from "./full-variable-list";

import styles from "./index.module.less";

export function VariablePanel() {
  const [isOpen, setOpen] = useState<boolean>(false);

  return (
    <div className={styles["panel-wrapper"]}>
      <Tooltip title="Toggle Variable Panel">
        <Button
          className={`${styles["variable-panel-button"]} ${
            isOpen ? styles.close : ""
          }`}
          type="text"
          onClick={() => setOpen((_open) => !_open)}
        >
          {isOpen ? (
            <MinusOutlined style={{ width: "20px", height: "20px" }} />
          ) : (
            <img src={iconVariable} width={20} height={20} />
          )}
        </Button>
      </Tooltip>
      {isOpen ? (
        <div className={styles["panel-container"]}>
          <Tabs>
            <Tabs.TabPane key="variables" tab="Variable List">
              <FullVariableList />
            </Tabs.TabPane>
            <Tabs.TabPane key="global" tab="Global Editor">
              <GlobalVariableEditor />
            </Tabs.TabPane>
          </Tabs>
        </div>
      ) : null}
    </div>
  );
}
