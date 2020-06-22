/*
 * Copyright 2020 Haulmont.
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

package test_support.app.entity.nullable_id;

import io.jmix.core.entity.Versioned;
import io.jmix.core.metamodel.annotation.InstanceName;

import javax.persistence.*;

@Table(name = "TEST_NBAR")
@Entity(name = "test_NBar")
public class NBar implements io.jmix.core.Entity, Versioned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    protected Long id;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;

    @Column(name = "NAME")
    @InstanceName
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOO_ID")
    private NFoo foo;

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NFoo getFoo() {
        return foo;
    }

    public void setFoo(NFoo foo) {
        this.foo = foo;
    }
}