/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import {
  Field,
  FormMeta,
  FormRenderProps,
} from "@flowgram.ai/free-layout-editor";
import {
  createInferInputsPlugin,
  DisplayOutputs,
  IFlowValue,
  IJsonSchema,
  InputsValues,
  JsonSchemaEditor,
} from "@flowgram.ai/form-materials";
import { Divider } from "antd";

import { FormHeader, FormContent, FormItem } from "../../form-components";
import { InterfaceNodeJSON } from "./types";
import { Inputs } from "./components/inputs";
import { Outputs } from "./components/outputs";
import { Interface } from "./components/interface";
import { defaultFormMeta } from "../default-form-meta";

export const FormRender = ({ form }: FormRenderProps<InterfaceNodeJSON>) => (
  <>
    <FormHeader />
    <FormContent>
      <Interface/>
      <Divider size="small" style={{ margin: 0 }} />
      <Inputs />
      <Divider size="small" style={{ margin: 0 }} />
      <Outputs />
    </FormContent>
  </>
);

export const formMeta: FormMeta = {
  render: (props) => <FormRender {...props} />,
  effect: defaultFormMeta.effect,
  validate: defaultFormMeta.validate,
  plugins: [
    createInferInputsPlugin({ sourceKey: "inputsValues", targetKey: "inputs" }),
  ],
};
