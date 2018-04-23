/******************************** com.newegg.ec.cache.controller.ClusterController ********************************/
/**
 * @type GET
 */
function  list(callback,errorCall){
   get("/cluster/listCluster",callback,errorCall);
}
/**
 * @type GET 
 * @param  int
 */
function  removeCluster(id,callback,errorCall){
   get("/cluster/removeCluster?id="+id+"",callback,errorCall);
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
/******************************** com.newegg.ec.cache.controller.UserController ********************************/
/**
 * @type GET
 */
function  list(callback,errorCall){
   get("/user/listUser",callback,errorCall);
}
/**
 * @type POST 
 * @param  User{id=0, username='null', password='null', userGroup='null'} 
 * @param  Cluster{id=0, userGroup='null', ip='null', port=0, sslUsername='null', sslPassword='null'}
 */
function  addUser(user,cluster,callback,errorCall){
   post("/user/addUser",user,cluster,callback,errorCall);
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
