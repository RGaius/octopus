/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { useState, useEffect } from "react";

import { useRefresh } from "@flowgram.ai/free-layout-editor";
import { useClientContext } from "@flowgram.ai/free-layout-editor";
import { Tooltip, Button, Divider } from "antd";
import { UndoOutlined, RedoOutlined } from "@ant-design/icons";

import { TestRunButton } from "../testrun/testrun-button";
import { AddNode } from "../add-node";
import { ZoomSelect } from "./zoom-select";
import { SwitchLine } from "./switch-line";
import { ToolContainer, ToolSection } from "./styles";
import { Readonly } from "./readonly";
import { MinimapSwitch } from "./minimap-switch";
import { Minimap } from "./minimap";
import { FitView } from "./fit-view";
import { AutoLayout } from "./auto-layout";
import { ProblemButton } from "../problem-panel";

export const DemoTools = () => {
  const { history, playground } = useClientContext();
  const [canUndo, setCanUndo] = useState(false);
  const [canRedo, setCanRedo] = useState(false);
  const [minimapVisible, setMinimapVisible] = useState(true);
  useEffect(() => {
    const disposable = history.undoRedoService.onChange(() => {
      setCanUndo(history.canUndo());
      setCanRedo(history.canRedo());
    });
    return () => disposable.dispose();
  }, [history]);
  const refresh = useRefresh();

  useEffect(() => {
    const disposable = playground.config.onReadonlyOrDisabledChange(() =>
      refresh()
    );
    return () => disposable.dispose();
  }, [playground]);

  return (
    <ToolContainer className="demo-free-layout-tools">
      <ToolSection>
        <AutoLayout />
        <SwitchLine />
        <ZoomSelect />
        <FitView />
        <MinimapSwitch
          minimapVisible={minimapVisible}
          setMinimapVisible={setMinimapVisible}
        />
        <Minimap visible={minimapVisible} />
        <Readonly />
        <Tooltip title="Undo">
          <Button
            type="text"
            icon={<UndoOutlined />}
            disabled={!canUndo || playground.config.readonly}
            onClick={() => history.undo()}
          />
        </Tooltip>
        <Tooltip title="Redo">
          <Button
            type="text"
            icon={<RedoOutlined />}
            disabled={!canRedo || playground.config.readonly}
            onClick={() => history.redo()}
          />
        </Tooltip>
        <ProblemButton />
        <Divider type="vertical" style={{ height: "16px", margin: 3 }} />
        <AddNode disabled={playground.config.readonly} />
        <Divider type="vertical" style={{ height: "16px", margin: 3 }} />
        <TestRunButton disabled={playground.config.readonly} />
      </ToolSection>
    </ToolContainer>
  );
};
