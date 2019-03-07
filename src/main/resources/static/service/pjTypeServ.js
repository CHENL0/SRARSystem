MyApp
    .service('pjTypeService',['$http', '$q',function ($http, $q) {
        var token = "Bearer "+localStorage.getItem("token");
        return {
            getPjTypeInfoByPjTypeId : function(pjTypeId) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/pj/getPjTypeInfoForModal',
                    data: {
                        pjTypeId : pjTypeId
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
                })
                promise.then(function successCallback(response) {
                    deferred.resolve(response.data);
                },function errorCallback(response) {
                    // 请求失败执行代码
                    deferred.reject(response);
                });
                return deferred.promise;
            },

            deletePjTypeInfo : function (pjTypeId){
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/pj/deletePjTypeInfo',
                    data: {
                        pjTypeId : pjTypeId
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

            getPjTypeNumberByPjTypeList : function(pjTypeList) {
                var fd = new FormData();
                var pjTypeListJson = angular.toJson(pjTypeList);//把对象(集合)转换为json串
                fd.append("pjTypeList",pjTypeListJson);
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/pj/getPjTypeNumberForShow',
                    data: fd ,
                    headers: { 'Content-Type': undefined  ,
                        'Authorization' : token},
                    transformRequest: angular.identity
                })
                promise.then(function successCallback(response) {
                    deferred.resolve(response.data);
                },function errorCallback(response) {
                    // 请求失败执行代码
                    deferred.reject(response);
                });
                return deferred.promise;
            },

            submitPjTypeData : function (pjTypeInfoData) {
                var pjTypeInfoData =JSON.stringify(pjTypeInfoData);
                var form = new FormData();
                form.append('pjTypeInfoData', pjTypeInfoData);
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/pj/addPjTypeInfoData',
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

            setPjTypeInfoData : function () {
                return {
                    "pjTypeId" : "",
                    "pjTypeDescription": "",
                    "pjType": ""
                }
            }
        };}]);

