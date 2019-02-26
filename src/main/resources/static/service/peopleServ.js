MyApp
    .service('peopleService',['$http', '$q',function ($http, $q) {
        return {

            getAllPfInfo : function() {
            var deferred = $q.defer();
            // 向后台发送处理数据
            var promise = $http({
                method: 'GET',
                url: 'http://localhost:8080/pf/getAllPfInfoForAdmin',
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

            getAllUserInfo : function() {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'GET',
                    url: 'http://localhost:8080/user/getAllUserInfoForAdmin',
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

            updateDelFlagForUser : function(userId,delFlag) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/user/updateDelFlagForAdmin',
                    data:{
                        userId :userId,
                        delFlag: delFlag
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
            updateDelFlagForPf : function(pfId,delFlag) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/pf/updateDelFlagForAdmin',
                    data:{
                        pfId :pfId,
                        delFlag: delFlag
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
            }

        };}]);

