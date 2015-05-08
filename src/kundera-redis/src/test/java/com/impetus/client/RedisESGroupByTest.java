/*******************************************************************************
 * * Copyright 2015 Impetus Infotech.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 ******************************************************************************/
package com.impetus.client;

import javax.persistence.Persistence;
import javax.persistence.Query;

import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.impetus.kundera.client.query.AggregationsBaseTest;
import com.impetus.kundera.client.query.GroupByBaseTest;

/**
 * The Class RedisESGroupByTest.
 * 
 * @author karthikp.manchala
 */
public class RedisESGroupByTest extends GroupByBaseTest
{

    /** The Constant REDIS_PU. */
    private static final String REDIS_PU = "redisElasticSearch_pu";

    /** The node. */
    private static Node node = null;

    /**
     * Sets the up before class.
     * 
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        if (!checkIfServerRunning())
        {
            ImmutableSettings.Builder builder = ImmutableSettings.settingsBuilder();
            builder.put("path.data", "target/data");
            node = new NodeBuilder().settings(builder).node();
        }

        emf = Persistence.createEntityManagerFactory(REDIS_PU);
        em = emf.createEntityManager();
        init();
    }

    /**
     * Test aggregation.
     */
    @Test
    public void test()
    {
        testAggregation();
    }

    /**
     * Tear down after class.
     * 
     * @throws Exception
     *             the exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
        em.createQuery("Delete from Person p").executeUpdate();
        waitThread();

        em.close();
        emf.close();

        if (node != null)
            node.close();
    }
}