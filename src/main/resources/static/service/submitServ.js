MyApp
    .service('submitService',['$http', '$q',function ($http, $q) {
        var token = "Bearer "+localStorage.getItem("token");
        return {
            getUserInfoData : function(requestData) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/user/getUserInfo',
                    data: {
                        username : requestData
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' ,
                        'Authorization' : token
                    },
                    transformRequest: function(obj) {
                        var str = [];
                        for (var s in obj) {
                            str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                        }
                        return str.join("&");
                    }
                })
                promise.then(function successCallback(response) {
                    deferred.resolve(response.data);
                },function errorCallback(response) {
                    // 请求失败执行代码
                    deferred.reject(response);
                });
                return deferred.promise;
            },

            getPfInfoListData :function () {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'GET',
                    url: 'http://localhost:8080/user/getAllPFInfoList',
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
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' ,
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

            submitDataForPj : function (file,pjInfoData) {
                var pjInfoData =JSON.stringify(pjInfoData);
                var form = new FormData();
                form.append('file', file);
                form.append('projectInfoData', pjInfoData);

                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/pj/commitPjInfoData',
                    data: form ,
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

            submitDataForTask : function (file,taskInfoData) {
                var taskInfoData =JSON.stringify(taskInfoData);
                var form = new FormData();
                form.append('file', file);
                form.append('taskInfoData', taskInfoData);

                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/user/commitTaskInfoData',
                    data: form ,
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

            validatePf : function (pfName, userName){
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/pf/validatePfInfo',
                    data: {
                        userName : userName,
                        pfName : pfName
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded',
                        'Authorization' : token  },
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

            setPjInfoData : function () {
                return {
                    "pjTitle" : "",
                    "pjDescription": "",
                    "pjReviewer": "",
                    "pjUser": "",
                    "pjType": ""
                }
            },
            setTaskInfoData : function () {
                return {
                    "taskMessage": "",
                    "taskName": ""
                }
            },

            getOneUserPjInfoListData : function(username) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/pj/getPjInfoListByUsername',
                    data:{
                        username : username
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

        };}]);

