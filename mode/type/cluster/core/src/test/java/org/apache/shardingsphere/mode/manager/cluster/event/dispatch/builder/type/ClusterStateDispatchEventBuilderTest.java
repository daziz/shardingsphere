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

import org.apache.shardingsphere.mode.state.ClusterState;
import org.apache.shardingsphere.mode.event.DataChangedEvent;
import org.apache.shardingsphere.mode.event.DataChangedEvent.Type;
import org.apache.shardingsphere.mode.manager.cluster.event.dispatch.event.DispatchEvent;
import org.apache.shardingsphere.mode.manager.cluster.event.dispatch.event.state.cluster.ClusterStateEvent;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClusterStateDispatchEventBuilderTest {
    
    private final ClusterStateDispatchEventBuilder builder = new ClusterStateDispatchEventBuilder();
    
    @Test
    void assertGetSubscribedKey() {
        assertThat(builder.getSubscribedKey(), is("/states/cluster_state"));
    }
    
    @Test
    void assertBuildEventWithValidClusterState() {
        Optional<DispatchEvent> actual = builder.build(new DataChangedEvent("/states/cluster_state", ClusterState.READ_ONLY.name(), Type.UPDATED));
        assertTrue(actual.isPresent());
        assertThat(((ClusterStateEvent) actual.get()).getClusterState(), is(ClusterState.READ_ONLY));
    }
    
    @Test
    void assertBuildEventWithInvalidClusterState() {
        Optional<DispatchEvent> actual = builder.build(new DataChangedEvent("/states/cluster_state", "INVALID", Type.UPDATED));
        assertTrue(actual.isPresent());
        assertThat(((ClusterStateEvent) actual.get()).getClusterState(), is(ClusterState.OK));
    }
}
