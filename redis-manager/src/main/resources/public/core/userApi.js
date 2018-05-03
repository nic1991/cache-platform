<<<<<<< HEAD
/******************************** com.newegg.ec.cache.app.controller.ClusterController ********************************/
/**
 * @type GET 
 * @param  String 
 * @param  int
 */
function  getClusterInfo(ip,port,callback){
   ajax.async_get("/cluster/getClusterInfo?ip="+ip+"&port="+port+"",callback);
}
/**
 * @type GET 
 * @param  String
 */
function  removeCluster(clusterId,callback){
   ajax.async_get("/cluster/removeCluster?clusterId="+clusterId+"",callback);
}
/**
 * @type GET 
 * @param  String
 */
function  listCluster(group,callback){
   ajax.async_get("/cluster/listCluster?group="+group+"",callback);
}
/**
 * @type POST 
 * @param  Cluster{id=0, clusterName='null', userGroup='null', address='null', sslUsername='null', sslPassword='null', clusterType='null'}
 */
function  addCluster(cluster,callback){
   ajax.async_post("/cluster/addCluster",cluster,callback);
}
/**
 * @type GET 
 * @param  String 
 * @param  int
 */
function  nodeList(ip,port,callback){
   ajax.async_get("/cluster/nodeList?ip="+ip+"&port="+port+"",callback);
}
/**
 * @type GET 
 * @param  int
 */
function  getCluster(id,callback){
   ajax.async_get("/cluster/getCluster?id="+id+"",callback);
}
/**
 * @type GET 
 * @param  String
 */
function  getClusterInfoByAddress(address,callback){
   ajax.async_get("/cluster/getClusterInfoByAddress?address="+address+"",callback);
}
/******************************** com.newegg.ec.cache.app.controller.UserController ********************************/
/**
 * @type GET
 */
function  list(callback){
   ajax.async_get("/user/listUser",callback);
}
/**
 * @type POST 
 * @param  User{id=0, username='null', password='null', userGroup='null'} 
 * @param  Cluster{id=0, clusterName='null', userGroup='null', address='null', sslUsername='null', sslPassword='null', clusterType='null'}
 */
function  addUser(user,cluster,callback){
   ajax.async_post("/user/addUser",user,cluster,callback);
}
/**
 * @type GET 
 * @param  int
 */
function  listGroup(id,callback){
   ajax.async_get("/user/listGroup?id="+id+"",callback);
}
/**
 * @type GET 
 * @param  int
 */
function  removeUser(id,callback){
   ajax.async_get("/user/removeUser?id="+id+"",callback);
}
/**
 * @type GET 
 * @param  int
 */
function  getUser(id,callback){
   ajax.async_get("/user/getUser?id="+id+"",callback);
}
=======
>>>>>>> 340515fca0b5ce7e0b88814e04898823df8645a2
/******************************** com.newegg.ec.cache.app.controller.MonitorController ********************************/
/**
 * @type GET 
 * @param  int 
 * @param  int 
 * @param  int 
 * @param  String 
 * @param  int
 */
function  monitorGetMaxField(clusterId,startTime,endTime,key,limit,callback){
   ajax.async_get("/monitor/getMaxField?clusterId="+clusterId+"&startTime="+startTime+"&endTime="+endTime+"&key="+key+"&limit="+limit+"",callback);
}
/**
 * @type GET 
 * @param  int 
 * @param  int 
 * @param  int 
<<<<<<< HEAD
 * @param  String 
 * @param  int
 */
function  monitorGetMinField(clusterId,startTime,endTime,key,limit,callback){
   ajax.async_get("/monitor/getMinField?clusterId="+clusterId+"&startTime="+startTime+"&endTime="+endTime+"&key="+key+"&limit="+limit+"",callback);
}
/**
 * @type POST 
 * @param  SlowLogParam{hostList=null, logLimit=0}
 */
function  monitorSlowLogs(logParam,callback){
   ajax.async_post("/monitor/slowLogs",logParam,callback);
=======
 * @param  String
 */
function  monitorGetLastNodeInfo(clusterId,startTime,endTime,host,callback){
   ajax.async_get("/monitor/getLastNodeInfo?clusterId="+clusterId+"&startTime="+startTime+"&endTime="+endTime+"&host="+host+"",callback);
>>>>>>> 340515fca0b5ce7e0b88814e04898823df8645a2
}
/**
 * @type GET 
 * @param  int 
 * @param  int 
 * @param  int 
 * @param  String 
 * @param  String
 */
function  monitorGetAvgField(clusterId,startTime,endTime,host,key,callback){
   ajax.async_get("/monitor/getAvgField?clusterId="+clusterId+"&startTime="+startTime+"&endTime="+endTime+"&host="+host+"&key="+key+"",callback);
}
/**
 * @type GET 
 * @param  int 
 * @param  int 
 * @param  int 
 * @param  String 
 * @param  String 
 * @param  String
 */
function  monitorGetGroupNodeInfo(clusterId,startTime,endTime,host,type,date,callback){
   ajax.async_get("/monitor/getGroupNodeInfo?clusterId="+clusterId+"&startTime="+startTime+"&endTime="+endTime+"&host="+host+"&type="+type+"&date="+date+"",callback);
}
/**
 * @type GET 
 * @param  int 
 * @param  int 
 * @param  int 
 * @param  String
 */
function  monitorGetAllField(clusterId,startTime,endTime,key,callback){
   ajax.async_get("/monitor/getAllField?clusterId="+clusterId+"&startTime="+startTime+"&endTime="+endTime+"&key="+key+"",callback);
}
<<<<<<< HEAD
=======
/**
 * @type GET 
 * @param  int 
 * @param  int 
 * @param  int 
 * @param  String 
 * @param  String
 */
function  monitorGetAvgField(clusterId,startTime,endTime,host,key,callback){
   ajax.async_get("/monitor/getAvgField?clusterId="+clusterId+"&startTime="+startTime+"&endTime="+endTime+"&host="+host+"&key="+key+"",callback);
}
/******************************** com.newegg.ec.cache.app.controller.ClusterController ********************************/
/**
 * @type GET 
 * @param  int
 */
function  getCluster(id,callback){
   ajax.async_get("/cluster/getCluster?id="+id+"",callback);
}
/**
 * @type GET 
 * @param  String
 */
function  getMapInfo(host,callback){
   ajax.async_get("/cluster/getMapInfo?host="+host+"",callback);
}
/**
 * @type GET 
 * @param  String 
 * @param  int
 */
function  getClusterInfo(ip,port,callback){
   ajax.async_get("/cluster/getClusterInfo?ip="+ip+"&port="+port+"",callback);
}
/**
 * @type GET 
 * @param  String 
 * @param  int
 */
function  nodeList(ip,port,callback){
   ajax.async_get("/cluster/nodeList?ip="+ip+"&port="+port+"",callback);
}
/**
 * @type GET 
 * @param  String
 */
function  removeCluster(clusterId,callback){
   ajax.async_get("/cluster/removeCluster?clusterId="+clusterId+"",callback);
}
/**
 * @type GET 
 * @param  String
 */
function  listCluster(group,callback){
   ajax.async_get("/cluster/listCluster?group="+group+"",callback);
}
/**
 * @type GET 
 * @param  int
 */
function  getClusterHost(id,callback){
   ajax.async_get("/cluster/getClusterHost?id="+id+"",callback);
}
/**
 * @type POST 
 * @param  Cluster{id=0, clusterName='null', userGroup='null', address='null', sslUsername='null', sslPassword='null', clusterType='null'}
 */
function  addCluster(cluster,callback){
   ajax.async_post("/cluster/addCluster",cluster,callback);
}
/**
 * @type GET 
 * @param  String
 */
function  getClusterInfoByAddress(address,callback){
   ajax.async_get("/cluster/getClusterInfoByAddress?address="+address+"",callback);
}
/******************************** com.newegg.ec.cache.app.controller.UserController ********************************/
/**
 * @type GET
 */
function  list(callback){
   ajax.async_get("/user/listUser",callback);
}
/**
 * @type GET 
 * @param  int
 */
function  removeUser(id,callback){
   ajax.async_get("/user/removeUser?id="+id+"",callback);
}
/**
 * @type GET 
 * @param  int
 */
function  getUser(id,callback){
   ajax.async_get("/user/getUser?id="+id+"",callback);
}
/**
 * @type GET 
 * @param  int
 */
function  listGroup(id,callback){
   ajax.async_get("/user/listGroup?id="+id+"",callback);
}
/**
 * @type POST 
 * @param  User{id=0, username='null', password='null', userGroup='null'} 
 * @param  Cluster{id=0, clusterName='null', userGroup='null', address='null', sslUsername='null', sslPassword='null', clusterType='null'}
 */
function  addUser(user,cluster,callback){
   ajax.async_post("/user/addUser",user,cluster,callback);
}
>>>>>>> 340515fca0b5ce7e0b88814e04898823df8645a2
