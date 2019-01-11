PjDetailApp
    .service('pjDetailService',['$http', '$q',function ($http, $q) {
        return {
            getPjInfoListData : function() {
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


        };}]);

