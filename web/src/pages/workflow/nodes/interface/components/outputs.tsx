/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { Field, useWatch, useForm } from "@flowgram.ai/free-layout-editor";
import {
  DisplayOutputs,
  IJsonSchema,
  JsonSchemaEditor,
} from "@flowgram.ai/form-materials";

import { useIsSidebar, useNodeRenderContext } from "../../../hooks";
import { FormItem } from "../../../form-components";

export function Outputs() {
  const { readonly } = useNodeRenderContext();
  const isSidebar = useIsSidebar();
  const interfaceData = useWatch("interface");
  const form = useForm();
  if (interfaceData == "POST") {
    form.setValueIn("outputs", {
      type: "object",
      properties: {
        out1: { type: "string" },
        out2: { type: "string" },
      },
    });
  } else {
    form.setValueIn("outputs", {
      type: "string",
      properties: {},
    });
  }
  if (!isSidebar) {
    return (
      <>
        <Field<IJsonSchema> name="outputs">
          {({ field }) => <DisplayOutputs value={field.value} />}
        </Field>
      </>
    );
  }

  return (
    <>
      <FormItem name="outputs" type="object" vertical>
        <Field<IJsonSchema> name="outputs">
          {({ field }) => (
            <JsonSchemaEditor
              readonly={readonly}
              value={field.value}
              onChange={(value) => field.onChange(value)}
            />
          )}
        </Field>
      </FormItem>
    </>
  );
}
