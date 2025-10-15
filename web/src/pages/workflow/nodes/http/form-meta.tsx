/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { FormMeta, FormRenderProps } from "@flowgram.ai/free-layout-editor";
import {
  createInferInputsPlugin,
  DisplayOutputs,
} from "@flowgram.ai/form-materials";
import { Divider } from "antd";

import { FormHeader, FormContent } from "../../form-components";
import { HTTPNodeJSON } from "./types";
import { Timeout } from "./components/timeout";
import { Params } from "./components/params";
import { Headers } from "./components/headers";
import { Body } from "./components/body";
import { Api } from "./components/api";
import { defaultFormMeta } from "../default-form-meta";

export const FormRender = ({ form }: FormRenderProps<HTTPNodeJSON>) => (
  <>
    <FormHeader />
    <FormContent>
      <Api />
      <Divider size="small" style={{margin: 0}}/>
      <Headers />
      <Divider size="small" style={{margin: 0}}/>
      <Params />
      <Divider size="small" style={{margin: 0}}/>
      <Body />
      <Divider size="small" style={{margin: 0}}/>
      <Timeout />
      <Divider size="small" style={{margin: 0}}/>
      <DisplayOutputs displayFromScope />
    </FormContent>
  </>
);

export const formMeta: FormMeta = {
  render: (props) => <FormRender {...props} />,
  effect: defaultFormMeta.effect,
  plugins: [
    createInferInputsPlugin({
      sourceKey: "headersValues",
      targetKey: "headers",
    }),
    createInferInputsPlugin({ sourceKey: "paramsValues", targetKey: "params" }),
  ],
};
