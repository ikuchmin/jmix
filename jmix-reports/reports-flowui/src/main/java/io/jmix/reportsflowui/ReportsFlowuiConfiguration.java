/*
 * Copyright 2022 Haulmont.
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

package io.jmix.reportsflowui;

import io.jmix.core.Messages;
import io.jmix.core.Metadata;
import io.jmix.core.annotation.JmixModule;
import io.jmix.flowui.FlowuiConfiguration;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.model.DataComponents;
import io.jmix.reports.ReportsConfiguration;
import io.jmix.reports.util.DataSetFactory;
import io.jmix.security.constraint.PolicyStore;
import io.jmix.security.constraint.SecureOperations;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@ConfigurationPropertiesScan
@JmixModule(dependsOn = {FlowuiConfiguration.class, ReportsConfiguration.class})
@PropertySource(name = "io.jmix.reportsflowui", value = "classpath:/io/jmix/reportsflowui/module.properties")
public class ReportsFlowuiConfiguration {

    @Bean("reportflowui_DataSetFactory")
    public DataSetFactory dataSetFactory() {
        return new DataSetFactory();
    }

    @Bean("report_CrossTabOrientationDataGridDecorator")
    public CrossTabDataGridDecorator crossTabDataGridDecorator(DataSetFactory dataSetFactory, UiComponents uiComponents,
                                                               SecureOperations secureOperations,
                                                               PolicyStore policyStore, Metadata metadata,
                                                               DataComponents dataComponents, Messages messages) {
        return new CrossTabDataGridDecorator(dataSetFactory, uiComponents, secureOperations, policyStore, metadata,
                dataComponents, messages);
    }
}
