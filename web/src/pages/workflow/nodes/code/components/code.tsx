/**
 * Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
 * SPDX-License-Identifier: MIT
 */

import { Field } from '@flowgram.ai/free-layout-editor';
import { TypeScriptCodeEditor } from '@flowgram.ai/form-materials';
import { Divider } from 'antd';

import { useIsSidebar, useNodeRenderContext } from '../../../hooks';

export function Code() {
  const isSidebar = useIsSidebar();
  const { readonly } = useNodeRenderContext();

  if (!isSidebar) {
    return null;
  }

  return (
    <>
      <Divider size='small' style={{margin: 0}}/>
      <Field<string> name="script.content">
        {({ field }) => (
          <TypeScriptCodeEditor
            value={field.value}
            onChange={(value) => field.onChange(value)}
            readonly={readonly}
          />
        )}
      </Field>
    </>
  );
}
