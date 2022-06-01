/*
 * Copyright 2019 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.jmix.ui.component;

import io.jmix.ui.meta.CanvasBehaviour;
import io.jmix.ui.meta.ContainerType;
import io.jmix.ui.meta.StudioComponent;

/**
 * A flow layout arranges components in a directional flow, much like lines of text in a paragraph.
 */
@StudioComponent(
        caption = "FlowBox",
        category = "Containers",
        xmlElement = "flowBox",
        icon = "io/jmix/ui/icon/container/flowBox.svg",
        canvasBehaviour = CanvasBehaviour.CONTAINER,
        containerType = ContainerType.FLOW,
        documentationURL = "https://docs.jmix.io/jmix/%VERSION%/ui/vcl/containers/box-layout.html#flowbox"
)
public interface FlowBoxLayout extends OrderedContainer, Component.BelongToFrame, HasMargin,
        HasSpacing, Component.HasCaption, Component.HasIcon, HasContextHelp, HasHtmlSanitizer,
        LayoutClickNotifier, ShortcutNotifier, HasHtmlCaption, HasHtmlDescription, HasRequiredIndicator {

    String NAME = "flowBox";
}