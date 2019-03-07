MyApp
    .service('userProfileService',['$http', '$q',function ($http, $q) {
        var token = "Bearer "+localStorage.getItem("token");
        return {
            getUserInfoData : function(username) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/user/getUserInfo',
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

            getPfInfoData : function(pfName) {
                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/pf/getPfInfoData',
                    data:{
                        pfName : pfName
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

            // getOneUserPjInfoListData : function(username) {
            //     var deferred = $q.defer();
            //     // 向后台发送处理数据
            //     var promise = $http({
            //         method: 'POST',
            //         url: 'http://localhost:8080/pj/getPjInfoListByUsername',
            //         data:{
            //             username : username
            //         },
            //         headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
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

            submitData : function (file,userProfileData) {
                var user =JSON.stringify(userProfileData);
                var form = new FormData();
                form.append('file', file);
                form.append('userProfileData', user);

                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/user/userProfileSubmit',
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

            submitPfData : function (file,pfProfileData) {
                var pf =JSON.stringify(pfProfileData);
                var form = new FormData();
                form.append('file', file);
                form.append('pfProfileData', pf);

                var deferred = $q.defer();
                // 向后台发送处理数据
                var promise = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/pf/pfProfileSubmit',
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

            getUserProfileData : function () {
                return {
                    userName : '',
                    userNickname: '',
                    userGender: '',
                    userIntroduce: '',
                    userAddress: ''
                };
            },

            getPfProfileData : function () {
                return{
                    pfName :'',
                    pfNickname : '',
                    pfGender : '',
                    pfIntroduce : '',
                    pfAddress : ''
                }
            }


        };}]);

