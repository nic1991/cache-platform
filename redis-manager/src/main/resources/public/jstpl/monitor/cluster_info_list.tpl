<ul>
    {{foreach from=$res item=cluster}}
        <li class="cluster-info-detail"  data-cluster-address="{{$cluster.address}}" data-cluster-id="{{$cluster.id}}" >
            <div>
                <div class="master-name">
                    <span><b>{{$cluster.clusterName}}</b></span>
                </div>
                <div id="cluster-info-{{$cluster.id}}"></div>
                <div class="detail">
                    <!-- 后期更改带参数 -->
                    <a href="/monitor/manager?clusterId={{$cluster.id}}"><button class="btn btn-primary btn-sm" type="button">Detail</button></a>
                </div>
            </div>
        </li>
    {{/foreach}}
</ul>