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

package io.jmix.flowui.sys;

import com.vaadin.flow.spring.VaadinServletContextInitializer;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

public class JmixVaadinServletContextInitializer extends VaadinServletContextInitializer {

    protected ApplicationContext applicationContext;

    public JmixVaadinServletContextInitializer(ApplicationContext applicationContext) {
        super(applicationContext);
        this.applicationContext = applicationContext;
    }

    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {
        String readClassPathFromTempJar = applicationContext.getEnvironment()
                .getProperty("io.jmix.flowui.read-class-path-from-temp-jar", "true");
        if (Boolean.parseBoolean(readClassPathFromTempJar)
                && JmixTempJarManifestUtils.isGradleTempJarClassPathUsed()) {
            String newClassPath = JmixTempJarManifestUtils.getClassPath();
            String previousClassPath = System.getProperty("java.class.path");

            // Add listener that will set class path from temp jar
            // before listeners with scanning will be added
            servletContext.addListener(new SetupTempClassPathContextListener(newClassPath));

            super.onStartup(servletContext);

            // Add listener that restores previous class path with which application run
            // after other listeners are added.
            servletContext.addListener(new RestoreClassPathContextListener(previousClassPath));
            return;
        }

        super.onStartup(servletContext);
    }

    /**
     * Listener sets provided class path to "java.class.path" property.
     */
    protected class SetupTempClassPathContextListener implements ServletContextListener {

        protected String newClassPath;

        public SetupTempClassPathContextListener(String newClassPath) {
            this.newClassPath = newClassPath;
        }

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            ServletContextListener.super.contextInitialized(sce);

            System.setProperty("java.class.path", newClassPath);
        }
    }

    /**
     * Listener restores saved class path to "java.class.path" property.
     */
    protected class RestoreClassPathContextListener implements ServletContextListener {

        protected String classPath;

        public RestoreClassPathContextListener(String classPath) {
            this.classPath = classPath;
        }

        @Override
        public void contextInitialized(ServletContextEvent sce) {
            ServletContextListener.super.contextInitialized(sce);

            System.setProperty("java.class.path", classPath);
        }
    }
}
