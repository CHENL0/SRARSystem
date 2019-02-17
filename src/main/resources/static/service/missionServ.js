MyApp
    .service('missionService',['$http', '$q',function ($http, $q) {
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
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
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
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded'},
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
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
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
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
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
                  taskName : '',
                  deadline : '',
                  taskDescription : ''
              }
            },

            submitTaskInfoData :function (taskInfo) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/task/submitTaskInfoData',
                    data: {
                        taskInfo:taskInfo
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
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
                    {code : 1, status : "Review"},
                    {code : 2, status : "Pass"},
                    {code : 3, status : "Reject"},
                    {code : 4, status : "Finished"},
                    {code : 5, status : "Break"},
                ];
            },

            changeProjectStatus : function (pjId,pjStatus) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/pf/changeProjectStatus',
                    data:{
                        pjId : pjId,
                        pjStatus : pjStatus
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded'},
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

