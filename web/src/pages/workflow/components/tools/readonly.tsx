/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { useCallback } from "react";

import { usePlayground } from "@flowgram.ai/free-layout-editor";
import { Button, Tooltip } from "antd";
import { UnlockFilled, LockFilled } from "@ant-design/icons";

export const Readonly = () => {
  const playground = usePlayground();
  const toggleReadonly = useCallback(() => {
    playground.config.readonly = !playground.config.readonly;
  }, [playground]);
  return playground.config.readonly ? (
    <Tooltip title="Editable">
      <Button type="text" icon={<LockFilled />} onClick={toggleReadonly} />
    </Tooltip>
  ) : (
    <Tooltip title="Readonly">
      <Button type="text" icon={<UnlockFilled />} onClick={toggleReadonly} />
    </Tooltip>
  );
};
