MyApp
    .service('missionService',['$http', '$q',function ($http, $q) {
        var token = "Bearer "+localStorage.getItem("token");
        return {
            getTaskInfoListData :function (pfName) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/task/getTaskInfoListData',
                    data: {
                        pfName:pfName
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization' : token },
                    transformRequest: function(obj) {
                        var str = [];
                        for (var s in obj) {
                            str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                        }
                        return str.join("&");
                    }
                });
                promise.then(function successCallback(response) {
                    deferred.resolve(response.data);
                },function errorCallback(response) {
                    // 请求失败执行代码
                    deferred.reject(response);
                });
                return deferred.promise;
            },

            downloadTask : function (taskId) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/task/downloadTask',
                    data:{
                        taskId : taskId
                    },
                    // responseType: "arraybuffer",
                    responseType: "blob",
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization' : token},
                    transformRequest: function(obj) {
                        var str = [];
                        for (var s in obj) {
                            str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                        }
                        return str.join("&");
                    }
                });
                promise.then(function successCallback(response) {
                    deferred.resolve(response.data);
                },function errorCallback(response) {
                    // 请求失败执行代码
                    deferred.reject(response);
                });
                return deferred.promise;
            },

            getUserInfoData : function(username) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/user/getUserInfo',
                    data:{
                        username : username
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization' : token },
                    transformRequest: function(obj) {
                        var str = [];
                        for (var s in obj) {
                            str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                        }
                        return str.join("&");
                    }
                });
                promise.then(function successCallback(response) {
                    deferred.resolve(response.data);
                },function errorCallback(response) {
                    // 请求失败执行代码
                    deferred.reject(response);
                });
                return deferred.promise;
            },

            getPjTypeListData: function () {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'Get',
                    url: 'http://localhost:8080/pj/getPjInfoList',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization' : token },
                    transformRequest: function(obj) {
                        var str = [];
                        for (var s in obj) {
                            str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                        }
                        return str.join("&");
                    }
                });
                promise.then(function successCallback(response) {
                    deferred.resolve(response.data);
                },function errorCallback(response) {
                    // 请求失败执行代码
                    deferred.reject(response);
                });
                return deferred.promise;
            },



            initTaskData : function (){
              return{
                  "pjId" : '',
                  "userName" : '',
                  "pfName" : '',
                  "pjTitle" :'',
                  "taskName" : '',
                  "deadline" : '',
                  "taskDescription" : ''
              }
            },

            getUserInfoData : function(username) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/user/getUserInfo',
                    data:{
                        username : username
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization' : token },
                    transformRequest: function(obj) {
                        var str = [];
                        for (var s in obj) {
                            str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                        }
                        return str.join("&");
                    }
                });
                promise.then(function successCallback(response) {
                    deferred.resolve(response.data);
                },function errorCallback(response) {
                    // 请求失败执行代码
                    deferred.reject(response);
                });
                return deferred.promise;
            },

            getPjInfoDataForPjTitle : function(pjId) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/pj/getOnePjInfoData',
                    data:{
                        pjId : pjId
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' ,
                        'Authorization' : token},
                    transformRequest: function(obj) {
                        var str = [];
                        for (var s in obj) {
                            str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                        }
                        return str.join("&");
                    }
                });
                promise.then(function successCallback(response) {
                    deferred.resolve(response.data);
                },function errorCallback(response) {
                    // 请求失败执行代码
                    deferred.reject(response);
                });
                return deferred.promise;
            },

            submitTaskInfoData :function (taskInfo) {
                var taskInfoData =JSON.stringify(taskInfo);
                var form = new FormData();
                form.append('taskInfo', taskInfoData);
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/task/submitTaskInfoData',
                    data: form,
                    headers: {'Content-Type': undefined,
                        'Authorization' : token},
                    transformRequest: angular.identity
                });
                promise.then(function successCallback(response) {
                    deferred.resolve(response.data);
                },function errorCallback(response) {
                    // 请求失败执行代码
                    deferred.reject(response);
                });
                return deferred.promise;
            },

            getUserNameListByPfName :function (pfName) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/task/getUserNameList',
                    data: {
                        pfName : pfName
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization' : token },
                    transformRequest: function(obj) {
                        var str = [];
                        for (var s in obj) {
                            str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                        }
                        return str.join("&");
                    }
                });
                promise.then(function successCallback(response) {
                    deferred.resolve(response.data);
                },function errorCallback(response) {
                    // 请求失败执行代码
                    deferred.reject(response);
                });
                return deferred.promise;
            },



            getStatusData : function () {
                return [
                    {code : 1, status : "Unfinished"},
                    {code : 2, status : "Finished"},
                    {code : 3, status : "Timeout"}
                ];
            },

            changeTaskStatus : function (taskId,taskStatus) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/task/changeTaskStatus',
                    data:{
                        taskId : taskId,
                        taskStatus : taskStatus
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization' : token},
                    transformRequest: function(obj) {
                        var str = [];
                        for (var s in obj) {
                            str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                        }
                        return str.join("&");
                    }
                });
                promise.then(function successCallback(response) {
                    deferred.resolve(response.data);
                },function errorCallback(response) {
                    // 请求失败执行代码
                    deferred.reject(response);
                });
                return deferred.promise;
            },

            getPjTitleForSelecting : function ( pjReviewer,  pjUser) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/task/getPjTitleForSelecting',
                    data:{
                        pjReviewer : pjReviewer,
                        pjUser : pjUser
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization' : token},
                    transformRequest: function(obj) {
                        var str = [];
                        for (var s in obj) {
                            str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                        }
                        return str.join("&");
                    }
                });
                promise.then(function successCallback(response) {
                    deferred.resolve(response.data);
                },function errorCallback(response) {
                    // 请求失败执行代码
                    deferred.reject(response);
                });
                return deferred.promise;
            },

            deleteTaskInfoForAudit : function ( taskId) {
            var deferred = $q.defer();
            // 向后台发送处理数据
            var promise = $http({
                method: 'POST',
                url: 'http://localhost:8080/task/deleteTaskInfoForAudit',
                data:{
                    taskId : taskId
                },
                headers: { 'Content-Type': 'application/x-www-form-urlencoded',
                    'Authorization' : token},
                transformRequest: function(obj) {
                    var str = [];
                    for (var s in obj) {
                        str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                    }
                    return str.join("&");
                }
            });
            promise.then(function successCallback(response) {
                deferred.resolve(response.data);
            },function errorCallback(response) {
                // 请求失败执行代码
                deferred.reject(response);
            });
            return deferred.promise;
        },


            compare : function(property){
            return function(obj1,obj2){
                var value1 = obj1[property];
                var value2 = obj2[property];
                return value1 - value2;     // 升序
            }
        }

        };}]);

