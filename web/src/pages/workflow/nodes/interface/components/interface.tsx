/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { Field } from "@flowgram.ai/free-layout-editor";
import { Select } from "antd";

import { useIsSidebar, useNodeRenderContext } from "../../../hooks";
import { FormItem } from "../../../form-components";

export function Interface() {
  const isSidebar = useIsSidebar();

  const { readonly } = useNodeRenderContext();
  if (!isSidebar) {
    return null;
  }
  return (
    <div>
      <FormItem name="Interface" required vertical type="string">
        <Field<string> name="interface" defaultValue="GET">
          {({ field }) => (
            <Select
              value={field.value}
              onChange={(value) => {
                field.onChange(value as string);
              }}
              disabled={readonly}
              style={{ width: 120 }}
              options={[
                { label: "GET", value: "GET" },
                { label: "POST", value: "POST" },
              ]}
            />
          )}
        </Field>
      </FormItem>
    </div>
  );
}
