/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.mode.manager.cluster.event.dispatch.builder.type;

import org.apache.shardingsphere.metadata.persist.node.GlobalNode;
import org.apache.shardingsphere.mode.event.DataChangedEvent;
import org.apache.shardingsphere.mode.event.DataChangedEvent.Type;
import org.apache.shardingsphere.mode.manager.cluster.event.dispatch.event.DispatchEvent;
import org.apache.shardingsphere.mode.manager.cluster.event.dispatch.event.config.AlterGlobalRuleConfigurationEvent;
import org.apache.shardingsphere.mode.manager.cluster.event.dispatch.builder.DispatchEventBuilder;
import org.apache.shardingsphere.mode.path.GlobalNodePath;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

/**
 * Global rule dispatch event builder.
 */
public final class GlobalRuleDispatchEventBuilder implements DispatchEventBuilder<DispatchEvent> {
    
    @Override
    public String getSubscribedKey() {
        return GlobalNode.getGlobalRuleRootNode();
    }
    
    @Override
    public Collection<Type> getSubscribedTypes() {
        return Arrays.asList(Type.ADDED, Type.UPDATED);
    }
    
    @Override
    public Optional<DispatchEvent> build(final DataChangedEvent event) {
        if (GlobalNodePath.isRuleActiveVersionPath(event.getKey())) {
            return GlobalNodePath.getRuleName(event.getKey()).map(optional -> new AlterGlobalRuleConfigurationEvent(optional, event.getKey(), event.getValue()));
        }
        return Optional.empty();
    }
}
