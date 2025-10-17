/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { nanoid } from "nanoid";

import { WorkflowNodeType } from "../constants";
import { FlowNodeRegistry } from "../../typings";
import iconInterface from "../../assets/icon-interface.svg";
import { formMeta } from "./form-meta";

let index = 0;

export const InterfaceNodeRegistry: FlowNodeRegistry = {
  type: WorkflowNodeType.Interface,
  info: {
    icon: iconInterface,
    description: "Call the Interface",
  },
  meta: {
    size: {
      width: 360,
      height: 390,
    },
  },
  onAdd() {
    return {
      id: `interface_${nanoid(5)}`,
      type: "interface",
      data: {
        title: `Interface_${++index}`,
        interface: "GET",
        inputsValues: {
        },
        outputs: {
          type: "object",
          properties: {},
        },
        inputs: {
          type: "object",
          properties: {},
        },
      },
    };
  },
  formMeta: formMeta,
};
