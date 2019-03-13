MyApp
    .service('applyService',['$http', '$q',function ($http, $q) {
        var token = "Bearer "+localStorage.getItem("token");
        return {
            submitDataForApply : function (file,applyData) {
                var applyData =JSON.stringify(applyData);
                var form = new FormData();
                form.append('file', file);
                form.append('applyInfoData', applyData);

                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/apply/submitApplyInfoData',
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

            download : function () {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/apply/downloadApplyFile',
                    data:{
                        'applyId':''
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

            getApplyInfoData : function () {
                return {
                    "applyUser":""
                };
            },

            getApplyFile : function () {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/apply/getApplyFile',
                    data:{},
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

            getUserInfoForApply : function (userName) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/apply/ifApplyForUser',
                    data:{
                        userName : userName
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

            getPfInfoForApply : function (pfName) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/apply/ifApplyForPf',
                    data:{
                        pfName : pfName
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
            }

        };}]);

