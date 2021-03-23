/*
 * Copyright 2021 Haulmont.
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

package io.jmix.dashboardsui.role;

import io.jmix.dashboards.role.DashboardsViewRole;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(code = DashboardsViewUiRole.CODE, name = "Dashboards: see embedded dashboards in UI")
public interface DashboardsViewUiRole extends DashboardsViewRole {

    String CODE = "dashboards-view-ui";

    @ScreenPolicy(screenIds = {"dshbrd_DashboardView.screen"})
    void dashboardsViewUi();
}
