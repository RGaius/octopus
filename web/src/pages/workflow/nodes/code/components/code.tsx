/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { Field } from "@flowgram.ai/free-layout-editor";
import {
  TypeScriptCodeEditor,
  PythonCodeEditor,
} from "@flowgram.ai/form-materials";
import { Divider, Select } from "antd";

import { useIsSidebar, useNodeRenderContext } from "../../../hooks";
import "./style.css";

export function Code() {
  const isSidebar = useIsSidebar();
  const { readonly } = useNodeRenderContext();

  if (!isSidebar) {
    return null;
  }
  
  return (
    <>
      <Divider size="small" style={{ margin: 0 }} />
      <div className="relative rounded-lg border bg-components-input-bg-hover">
        <div>
          <Field<string> name="script.language">
            <Select
              variant="filled"
              style={{ width: 120 }}
              defaultValue={"python3"}
              options={[
                {
                  label: "JavaScript",
                  value: "javascript",
                },
                {
                  label: "Python3",
                  value: "python3",
                },
                {
                  label: "Groovy",
                  value: "groovy",
                },
              ]}
            />
          </Field>
        </div>
        <div className="relative">
          <Field<string> name="script.language">
            {({ field: languageField }) => (
              <Field<string> name="script.content">
                {({ field }) => {
                  const EditorComponent = languageField.value === 'javascript' 
                    ? TypeScriptCodeEditor 
                    : PythonCodeEditor;
                  
                  return (
                    <EditorComponent
                      value={field.value}
                      onChange={(value) => field.onChange(value)}
                      readonly={readonly}
                    />
                  );
                }}
              </Field>
            )}
          </Field>
        </div>
      </div>
    </>
  );
}