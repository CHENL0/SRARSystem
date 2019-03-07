MyApp
    .service('auditService',['$http', '$q',function ($http, $q) {
        var token = "Bearer "+localStorage.getItem("token");
        return {
            getApplyInfoListDataForUser :function () {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'GET',
                    url: 'http://localhost:8080/apply/getAllApplyInfoForUser',
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

            getApplyInfoListDataForPf :function () {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'GET',
                    url: 'http://localhost:8080/apply/getAllApplyInfoForPf',
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


            getOnePfPjInfoListData : function(pfName) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/pf/getPjInfoListByPfName',
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
            },

            download : function (applyId) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/apply/downloadApplyFile',
                    data:{
                        applyId : applyId
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



            // getOneApplyInfoData : function (applyId) {
            //     var deferred = $q.defer();
            //     // 向后台发送处理数据
            //     var promise = $http({
            //         method: 'POST',
            //         url: 'http://localhost:8080/apply/getOnePjInfoData',
            //         data:{
            //             applyId : applyId
            //         },
            //         headers: { 'Content-Type': 'application/x-www-form-urlencoded' ,
            //             'Authorization' : token},
            //         transformRequest: function(obj) {
            //             var str = [];
            //             for (var s in obj) {
            //                 str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
            //             }
            //             return str.join("&");
            //         }
            //     });
            //     promise.then(function successCallback(response) {
            //         deferred.resolve(response.data);
            //     },function errorCallback(response) {
            //         // 请求失败执行代码
            //         deferred.reject(response);
            //     });
            //     return deferred.promise;
            // },

            changeApplyType : function (applyId,applyType) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/apply/changeApplyType',
                    data:{
                        applyId : applyId,
                        applyType : applyType
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
        },
            createPfInfo: function (applyUser,selectedType) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/apply/createPfInfo',
                    data:{
                        applyUser : applyUser,
                        selectedType : selectedType
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



            getPjType: function () {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'GET',
                    url: 'http://localhost:8080/pj/getPjInfoList',
                    data:{
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

            submitApplyFile : function (file,applyType) {
                var form = new FormData();
                form.append('file', file);
                form.append('applyType', applyType);

                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/apply/submitApplyFile',
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
            }

        };}]);

