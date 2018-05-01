/******************************** com.newegg.ec.cache.app.controller.ClusterController ********************************/
/**
 * @type GET 
 * @param  int
 */
function  getCluster(id,callback){
   async_get("/cluster/getCluster?id="+id+"",callback);
}
/**
 * @type GET 
 * @param  String
 */
function  listCluster(group,callback){
   async_get("/cluster/listCluster?group="+group+"",callback);
}
/**
 * @type POST 
 * @param  Cluster{id=0, clusterName='null', userGroup='null', address='null', sslUsername='null', sslPassword='null'}
 */
function  addCluster(cluster,callback){
   async_post("/cluster/addCluster",cluster,callback);
}
/**
 * @type GET 
 * @param  int
 */
function  removeCluster(id,callback){
   async_get("/cluster/removeCluster?id="+id+"",callback);
}
/**
 * @type GET 
 * @param  String 
 * @param  int
 */
function  removeCluster(ip,port,callback){
   async_get("/cluster/getClusterInfo?ip="+ip+"&port="+port+"",callback);
}
/******************************** com.newegg.ec.cache.app.controller.UserController ********************************/
/**
 * @type GET
 */
function  list(callback){
   async_get("/user/listUser",callback);
}
/**
 * @type GET 
 * @param  int
 */
function  getUser(id,callback){
   async_get("/user/getUser?id="+id+"",callback);
}
/**
 * @type GET 
 * @param  int
 */
function  removeUser(id,callback){
   async_get("/user/removeUser?id="+id+"",callback);
}
/**
 * @type POST 
 * @param  User{id=0, username='null', password='null', userGroup='null'} 
 * @param  Cluster{id=0, clusterName='null', userGroup='null', address='null', sslUsername='null', sslPassword='null'}
 */
function  addUser(user,cluster,callback){
   async_post("/user/addUser",user,cluster,callback);
}
/******************************** com.newegg.ec.cache.app.controller.MonitorController ********************************/
/**
 * @type GET 
 * @param  String 
 * @param  int 
 * @param  int 
 * @param  String 
 * @param  String 
 * @param  String
 */
function  monitorGetGroupNodeInfo(tableName,startTime,endTime,host,type,date,callback){
   async_get("/monitor/getGroupNodeInfo?tableName="+tableName+"&startTime="+startTime+"&endTime="+endTime+"&host="+host+"&type="+type+"&date="+date+"",callback);
}
/**
 * @type GET 
 * @param  String 
 * @param  int 
 * @param  int 
 * @param  String 
 * @param  int
 */
function  monitorGetMaxField(tableName,startTime,endTime,key,limit,callback){
   async_get("/monitor/getMaxField?tableName="+tableName+"&startTime="+startTime+"&endTime="+endTime+"&key="+key+"&limit="+limit+"",callback);
}
/**
 * @type GET 
 * @param  String 
 * @param  int 
 * @param  int 
 * @param  String 
 * @param  int
 */
function  monitorGetMinField(tableName,startTime,endTime,key,limit,callback){
   async_get("/monitor/getMinField?tableName="+tableName+"&startTime="+startTime+"&endTime="+endTime+"&key="+key+"&limit="+limit+"",callback);
}
/**
 * @type GET 
 * @param  String 
 * @param  int 
 * @param  int 
 * @param  String 
 * @param  String
 */
function  monitorGetAvgField(tableName,startTime,endTime,host,key,callback){
   async_get("/monitor/getAvgField?tableName="+tableName+"&startTime="+startTime+"&endTime="+endTime+"&host="+host+"&key="+key+"",callback);
}
/**
 * @type GET 
 * @param  String 
 * @param  int 
 * @param  int 
 * @param  String
 */
function  monitorGetAllField(tableName,startTime,endTime,key,callback){
   async_get("/monitor/getAllField?tableName="+tableName+"&startTime="+startTime+"&endTime="+endTime+"&key="+key+"",callback);
}
/**
 * @type GET 
 * @param  String 
 * @param  int 
 * @param  int 
 * @param  String
 */
function  monitorGetLastNodeInfo(tableName,startTime,endTime,host,callback){
   async_get("/monitor/getLastNodeInfo?tableName="+tableName+"&startTime="+startTime+"&endTime="+endTime+"&host="+host+"",callback);
}
/**
 * @type POST 
 * @param  String
 */
function  monitorSlowLogs(jsonBody,callback){
   async_post("/monitor/slowLogs",jsonBody,callback);
}
