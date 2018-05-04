<div class="col-md-6">
    <!-- SmartWizard html -->
    <div id="smartwizard" style="background:#fff">
        <ul style="background-color:#fff">
            <li><a href="#step-1">Step 1<br /><small>Create Cluster</small></a></li>
            <li><a href="#step-2">Step 2<br /><small>Install Node</small></a></li>
            <li><a href="#step-3">Step 3<br /><small>Select Master</small></a></li>
            <li style="display:none"><a href="#step-4">Step 4<br /><small>Finish</small></a></li>
        </ul>

        <div>
            <div id="step-1" class="">
                <div class="create-center-content">
                    <h2>Step 1 Create Cluster</h2>
                    <table class="table table-bordered" id="create-create-cluster">
                      <tbody>
                        <tr class="input-format">
                            <td class="container-table-tag">Cluster Name</td>
                            <td>
                                <input name="clusterId" type="text" class="form-control" data-ajax-type="get"  placeholder="cluster-name"  data-require="1" data-len="3-20" data-len-msg="length range5-20" data-format="[a-zA-Z0-9_]+" data-format-msg="only support letter and number or _"  data-server-check="/check/checkClusterName"  >
                                <div id="clusterId_tip"></div>
                            </td>
                        </tr>
                      </tbody>
                    </table>
                    <div>
                        <br>
                        <p>Select image for redis install</p>
                        <div class="form-inline" style="height:55px">
                              <input name="docker_image" type="text" class="form-control" value="humpback-hub.newegg.org/shec/redis3.0.6:v3" style="width:99%" >
                        </div>
                        <br>
                        <span style="color:#5d5c5c;"> <span class="glyphicon glyphicon-info-sign"></span> If without image you should push of redis image</span>
                    </div>
                    <div class="row">
                        <br>
                        <button class="btn btn-default second-selected" id="start-install-node" >start install node</button>
                    </div>
                    <br>
                </div>
            </div>
            <div id="step-2" class="">
                <div class="create-center-content">
                    <h2>Step 2 Install Node</h2>
                    <div>
                        <table class="table table-bordered" id="docker-container-form">
                          <tbody>
                            <tr class="input-format">
                              <td class="container-table-tag">Team</td>
                                    <td class="input-format selecte-button">
                                        <select name="team" id="team"  class="form-control table-select">

                                        </select>
                                    </td>
                              <td class="container-table-tag">Container Name <a tabindex="0" data-placement="right" class="detail-button glyphicon glyphicon-question-sign" role="button" data-toggle="popover" data-trigger="focus" data-content="container name is unique"></a></td>
                              <td>
                                clusterId
                              </td>
                            </tr>
                            <tr class="pointer batch-ip-list" >
                              <td class="container-table-tag" >Batch IP List <a tabindex="0" data-placement="right" class="detail-button glyphicon glyphicon-question-sign" role="button" data-toggle="popover" data-trigger="focus" data-content="batch add container ip or port list <br> example:<br>127.0.0.1 8008<br>127.0.0.2 8009"></a></td>
                              <td colspan="3">
                                <div class="node-value text-value ip-list-value">
                                    <textarea class="host-list" name="hostlist" data-relation="clusterId" data-require="1" data-server-check="/check/batchHostAccess"  placeholder="example 127.0.0.1:8008" ></textarea>
                                    <div id="hostlist_tip"></div>
                                </div>
                              </td>
                            </tr>
                          </tbody>
                        </table>
                        <div class="row">
                            <button class="btn btn-default second-selected" id="install-all-node">Install all node</button>
                            <br><br>
                        </div>
                    </div>
                </div>
            </div>
            <div id="step-3" class="">
                <div class="create-center-content hidden" id="step-3-fail">
                    <h2 style="color:#ec5f5f">Fail all node install</h2>
                    <br>
                    <a style="font-size:18px" href="/node/manager"><span class="glyphicon glyphicon-hand-right"></span> You can go to [ Create Panel ] to retry</a>
                    <br><br>
                </div>

                <div class="create-center-content" id="step-3-content">
                    <h2>Step 3 Select Master</h2>
                    <table class="table table-bordered">
                      <thead>
                        <tr>
                          <th>IP</th>
                          <th>Port</th>
                          <th>Operate</th>
                        </tr>
                      </thead>
                      <tbody id="select-master-list">

                      </tbody>
                    </table>
                    <div class="row">
                        <button class="btn btn-default second-selected" id="set-master-finish">Finish and go to set slave</button>
                    </div>
                    <br>
                </div>
            </div>
            <div id="step-4" class="">
                <div class="create-center-content">
                    <h2>Finish all operate</h2>
                    <br>
                    <a id="create_cluter_step_result_url" style="font-size:18px" href=""><span class="glyphicon glyphicon-hand-right"></span> You can go to [ Manager Panel ] to check</a>
                    <br><br>
                </div>
            </div>
        </div>
    </div>
</div><!-- .col-md-6 -->
<div class="col-md-6">
    <div class="panel panel-default">
      <div id="redis-config-tpl">
        {{include file="node/config/redis_3_config.tpl"}}
      </div>
    </div>
</div>