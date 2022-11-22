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

package io.jmix.flowui.component.grid;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSelectionModel;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.shared.Registration;
import io.jmix.core.common.util.Preconditions;
import io.jmix.core.metamodel.model.MetaProperty;
import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.flowui.component.ListDataComponent;
import io.jmix.flowui.component.LookupComponent.MultiSelectLookupComponent;
import io.jmix.flowui.component.delegate.TreeGridDelegate;
import io.jmix.flowui.component.grid.editor.DataGridEditor;
import io.jmix.flowui.component.grid.editor.DataGridEditorImpl;
import io.jmix.flowui.data.DataUnit;
import io.jmix.flowui.data.grid.TreeDataGridItems;
import io.jmix.flowui.kit.component.grid.GridActionsSupport;
import io.jmix.flowui.kit.component.grid.JmixTreeGrid;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;

public class TreeDataGrid<E> extends JmixTreeGrid<E> implements ListDataComponent<E>, MultiSelectLookupComponent<E>,
        EnhancedDataGrid<E>, ApplicationContextAware, InitializingBean {

    protected ApplicationContext applicationContext;

    protected TreeGridDelegate<E, TreeDataGridItems<E>> gridDelegate;

    protected boolean editorCreated = false;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        initComponent();
    }

    protected void initComponent() {
        gridDelegate = createDelegate();
    }

    @SuppressWarnings("unchecked")
    protected TreeGridDelegate<E, TreeDataGridItems<E>> createDelegate() {
        return applicationContext.getBean(TreeGridDelegate.class, this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setDataProvider(HierarchicalDataProvider<E, ?> dataProvider) {
        if (dataProvider instanceof TreeDataGridItems) {
            gridDelegate.setItems((TreeDataGridItems<E>) dataProvider);
        }

        super.setDataProvider(dataProvider);
    }

    @Nullable
    @Override
    public E getSingleSelectedItem() {
        return gridDelegate.getSingleSelectedItem();
    }

    @Override
    public Set<E> getSelectedItems() {
        return gridDelegate.getSelectedItems();
    }

    @Override
    public void select(E item) {
        gridDelegate.select(item);
    }

    @Override
    public void select(Collection<E> items) {
        gridDelegate.select(items);
    }

    @Override
    public void deselect(E item) {
        gridDelegate.deselect(item);
    }

    @Override
    public void deselectAll() {
        gridDelegate.deselectAll();
    }

    @Nullable
    @Override
    public DataUnit getItems() {
        return gridDelegate.getItems();
    }

    @Override
    public boolean isMultiSelect() {
        return gridDelegate.isMultiSelect();
    }

    @Override
    public Registration addSelectionListener(SelectionListener<Grid<E>, E> listener) {
        return gridDelegate.addSelectionListener(listener);
    }

    @Override
    public void enableMultiSelect() {
        gridDelegate.enableMultiSelect();
    }

    @Override
    public GridSelectionModel<E> setSelectionMode(SelectionMode selectionMode) {
        GridSelectionModel<E> selectionModel = super.setSelectionMode(selectionMode);

        gridDelegate.onSelectionModelChange(selectionModel);

        return selectionModel;
    }

    /**
     * Adds column by the meta property path.
     *
     * @param metaPropertyPath meta property path to add column
     * @return added column
     */
    @Override
    public Column<E> addColumn(MetaPropertyPath metaPropertyPath) {
        Preconditions.checkNotNullArgument(metaPropertyPath);

        MetaProperty metaProperty = metaPropertyPath.getMetaProperty();
        return addColumn(metaProperty.getName(), metaPropertyPath);
    }

    /**
     * Adds column by the meta property path and specified key. The key is used to identify the column, see
     * {@link #getColumnByKey(String)}.
     *
     * @param key              column key
     * @param metaPropertyPath meta property path to add column
     * @return added column
     */
    @Override
    public Column<E> addColumn(String key, MetaPropertyPath metaPropertyPath) {
        Preconditions.checkNotNullArgument(metaPropertyPath);
        Preconditions.checkNotNullArgument(key);

        return gridDelegate.addColumn(key, metaPropertyPath);
    }

    @Override
    protected void onDataProviderChange() {
        super.onDataProviderChange();

        if (editorCreated) {
            DataGridEditor<E> editor = getEditor();
            if (editor instanceof DataGridDataProviderChangeObserver) {
                ((DataGridDataProviderChangeObserver) editor).dataProviderChanged();
            }
        }
    }

    @Override
    public DataGridEditor<E> getEditor() {
        return ((DataGridEditor<E>) super.getEditor());
    }

    @Override
    protected DataGridEditor<E> createEditor() {
        editorCreated = true;
        return new DataGridEditorImpl<>(this, applicationContext);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected GridActionsSupport<JmixTreeGrid<E>, E> createActionsSupport() {
        return new DataGridActionsSupport(this);
    }
}
