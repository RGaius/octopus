/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { IFlowConstantRefValue } from "@flowgram.ai/runtime-interface";
import { FlowNodeJSON } from "@flowgram.ai/free-layout-editor";
import {
  IFlowTemplateValue,
  IFlowValue,
  IJsonSchema,
} from "@flowgram.ai/form-materials";

export interface InterfaceNodeJSON extends FlowNodeJSON {
  data: {
    title: string;
    interface: object;
    inputsValues: Record<string, IFlowValue>;
    inputs: IJsonSchema<"object">;
    outputsValues: Record<string, IFlowValue>;
    outputs: IJsonSchema<"object">;
  };
}