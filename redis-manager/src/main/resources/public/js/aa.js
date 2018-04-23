/******************************** com.newegg.ec.cache.controller.UserController ********************************/
/**
 * @type GET
 */
function  list(callback,errorCall){
   get("/user/listUser",callback,errorCall);
}
/**
 * @type GET 
 * @param  int
 */
function  getUser(id,callback,errorCall){
   get("/user/getUser?id="+id+"",callback,errorCall);
}
/**
 * @type GET 
 * @param  int
 */
function  removeUser(id,callback,errorCall){
   get("/user/removeUser?id="+id+"",callback,errorCall);
}
/**
 * @type POST 
 * @param  User{id=0, username='null', password='null', userGroup='null'} 
 * @param  Cluster{id=0, userGroup='null', ip='null', port=0, sslUsername='null', sslPassword='null'}
 */
function  addUser(user,cluster,callback,errorCall){
   post("/user/addUser",user,cluster,callback,errorCall);
}
/******************************** com.newegg.ec.cache.controller.ClusterController ********************************/
/**
 * @type GET
 */
function  list(callback,errorCall){
   get("/cluster/listCluster",callback,errorCall);
}
/**
 * @type POST 
 * @param  Cluster{id=0, userGroup='null', ip='null', port=0, sslUsername='null', sslPassword='null'}
 */
function  getCluster(cluster,callback,errorCall){
   post("/cluster/addCluster",cluster,callback,errorCall);
}
/**
 * @type GET 
 * @param  int
 */
function  getCluster(id,callback,errorCall){
   get("/cluster/getCluster?id="+id+"",callback,errorCall);
}
/**
 * @type GET 
 * @param  int
 */
function  removeCluster(id,callback,errorCall){
   get("/cluster/removeCluster?id="+id+"",callback,errorCall);
}
/******************************** com.newegg.ec.cache.controller.CheckControl ********************************/
/**
 * @type POST 
 * @param  String
 */
function  checkClusterName(jsonBody,callback,errorCall){
   post("/check/check_cluster_name",jsonBody,callback,errorCall);
}
/**
 * @type POST 
 * @param  String
 */
function  checkBatchDockerNode(jsonBody,callback,errorCall){
   post("/check/check_batch_docker_node",jsonBody,callback,errorCall);
}
/**
 * @type POST 
 * @param  String
 */
function  checkBatchMachineNode(jsonBody,callback,errorCall){
   post("/check/check_batch_machine_node",jsonBody,callback,errorCall);
}
/**
 * @type POST 
 * @param  String
 */
function  checkDir(jsonBody,callback,errorCall){
   post("/check/check_dir",jsonBody,callback,errorCall);
}
/**
 * @type POST 
 * @param  String
 */
function  checkBatchDirAndWget(jsonBody,callback,errorCall){
   post("/check/check_batch_dir_and_wget",jsonBody,callback,errorCall);
}
/**
 * @type POST 
 * @param  String
 */
function  checkIpPassword(jsonBody,callback,errorCall){
   post("/check/check_ip_password",jsonBody,callback,errorCall);
}
/**
 * @type GET 
 * @param  String 
 * @param  String
 */
function  checkIpPassword2(ip,port,callback,errorCall){
   get("/check/check_ip_password2?ip="+ip+"&port="+port+"",callback,errorCall);
}
/**
 * @type POST 
 * @param  String 
 * @param  String
 */
function  checkPort(pass,jsonBody,callback,errorCall){
   post("/check/check_port",pass,jsonBody,callback,errorCall);
}
/**
 * @type POST 
 * @param  String
 */
function  checkHost(jsonBody,callback,errorCall){
   post("/check/check_ip",jsonBody,callback,errorCall);
}
/******************************** com.newegg.ec.cache.controller.MonitorController ********************************/
/**
 * @type GET
 */
function  create(callback,errorCall){
   get("/monitor/create",callback,errorCall);
}
/**
 * @type POST 
 * @param  String
 */
function  slowLogs(jsonBody,callback,errorCall){
   post("/monitor/slowLogs",jsonBody,callback,errorCall);
}
