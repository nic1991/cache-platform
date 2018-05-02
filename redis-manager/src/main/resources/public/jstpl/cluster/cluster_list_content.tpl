<ul>
    {{if $code == 0 }}
        {{foreach from=$res item=cluster}}
            <li data="ClusterName1">
                    <div class="cluster-item">
                        <div class="delete-container">
                            <i class="material-icons">close</i>
                        </div>
                        <a href="/pages/redisMonitorDetail" class="to-detail" title="see cluster detail">
                            <div class="one-data">
                                <span><b>{{$cluster.clusterName}}</b></span>
                            </div>
                            <div class="one-data" id="clustre-state-{{$cluster.id}}">
                                {{cluster_state address=$cluster.address id=$cluster.id}}
                            </div>
                            <div class="one-data">
                                <span>Group:</span>
                                <span>{{$cluster.userGroup}}</span>
                            </div>
                            <div class="one-data">
                                <span>Address:</span>
                                <span>{{$cluster.address}}</span>
                            </div>
                        </a>
                        <div class="operation-btn-swapper">
                            <!-- 后期需要带参 -->
                            <a href="/pages/managerCluster" class="operation-btn">
                                <span data-cluster-id="{{$cluster.id}}">Manager</span>
                            </a>
                        </div>
                    </div>
                </li>
        {{/foreach}}
    {{/if}}
    <li class="add-li" title="import cluster">
        <div class="add-btn">
            <i class="glyphicon glyphicon-plus"></i>
        </div>
    </li>
</ul>