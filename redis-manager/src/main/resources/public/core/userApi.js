/******************************** com.newegg.ec.cache.app.controller.UserController ********************************/
/**
 * @type GET
 */
function  list(callback,errorCall){
   get("/user/listUser",callback,errorCall);
}
/**
 * @type POST 
 * @param  User{id=0, username='null', password='null', userGroup='null'} 
 * @param  Cluster{id=0, clusterName='null', userGroup='null', address='null', sslUsername='null', sslPassword='null'}
 */
function  addUser(user,cluster,callback,errorCall){
   post("/user/addUser",user,cluster,callback,errorCall);
}
/**
 * @type GET 
 * @param  int
 */
function  removeUser(id,callback,errorCall){
   get("/user/removeUser?id="+id+"",callback,errorCall);
}
/**
 * @type GET 
 * @param  int
 */
function  getUser(id,callback,errorCall){
   get("/user/getUser?id="+id+"",callback,errorCall);
}
/******************************** com.newegg.ec.cache.app.controller.ClusterController ********************************/
/**
 * @type GET 
 * @param  int
 */
function  getCluster(id,callback,errorCall){
   get("/cluster/getCluster?id="+id+"",callback,errorCall);
}
/**
 * @type GET 
 * @param  String
 */
function  listCluster(group,callback,errorCall){
   get("/cluster/listCluster?group="+group+"",callback,errorCall);
}
/**
 * @type POST 
 * @param  Cluster{id=0, clusterName='null', userGroup='null', address='null', sslUsername='null', sslPassword='null'}
 */
function  addCluster(cluster,callback,errorCall){
   post("/cluster/addCluster",cluster,callback,errorCall);
}
/**
 * @type GET 
 * @param  int
 */
function  removeCluster(id,callback,errorCall){
   get("/cluster/removeCluster?id="+id+"",callback,errorCall);
}
/**
 * @type GET 
 * @param  String 
 * @param  int
 */
function  removeCluster(ip,port,callback,errorCall){
   get("/cluster/getClusterInfo?ip="+ip+"&port="+port+"",callback,errorCall);
}
