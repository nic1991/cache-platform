package com.newegg.ec.cache.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by gl49 on 2018/4/20.
 */
@Component
public class RedisNodeUtil {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createNodeInfoTableTemplete (String table) {
        String createSql = "create table if not exists node_info_" + table + "(" +
                "id int auto_increment primary key," +
                "connected_clients BIGINT," +
                "blocked_clients BIGINT," +
                "used_memory BIGINT," +
                "used_memory_rss BIGINT," +
                "used_memory_peak BIGINT," +
                "mem_fragmentation_ratio float," +
                "aof_enabled BIGINT," +
                "total_connections_received BIGINT," +
                "total_commands_processed BIGINT," +
                "instantaneous_ops_per_sec BIGINT," +
                "total_net_input_bytes BIGINT," +
                "total_net_output_bytes BIGINT," +
                "instantaneous_input_kbps float," +
                "instantaneous_output_kbps float," +
                "rejected_connections BIGINT," +
                "sync_full BIGINT," +
                "sync_partial_ok BIGINT," +
                "sync_partial_err BIGINT," +
                "expired_keys BIGINT," +
                "evicted_keys BIGINT," +
                "keyspace_hits BIGINT," +
                "keyspace_misses BIGINT," +
                "pubsub_channels BIGINT," +
                "pubsub_patterns BIGINT," +
                "latest_fork_usec BIGINT," +
                "migrate_cached_sockets BIGINT," +
                "used_cpu_sys float," +
                "used_cpu_user float," +
                "used_cpu_sys_children float," +
                "used_cpu_user_children float," +
                "total_keys BIGINT," +
                "expires BIGINT," +
                "avg_ttl BIGINT," +
                "response_time BIGINT," +
                "clusterid varchar(50)," +
                "nodeid varchar(255)," +
                "host varchar(30)," +
                "ip varchar(25)," +
                "port int," +
                "day int," +
                "hour int," +
                "minute int," +
                "add_time int)";
        jdbcTemplate.execute(createSql);
    }
}
