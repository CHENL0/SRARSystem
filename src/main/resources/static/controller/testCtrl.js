angular.module('myApp', [])
    .service('myService', ['$http', function ($http) {
        return {
            getCurrencyData: function () {
                var list = {
                    currency: ["hkd", "rmb", "ryuan"]
                };
                return list;
            },
            getMoneyData: function () {
                var list = {
                    currency: ["hkd", "rmb", "ryuan"],
                    money: ["200", "100", "300"]
                };
                return list;
            },
            getAccount: function (list) {
                var currency = list.currency;
                var money = list.money;
                var map = [];
                for (var i = 0; i < currency.length; i++) {
                    map.push(currency[i] + money[i]);
                }
                return map;
            },
            getChangeMoney: function (i) {
                var list = {
                    currency: ["hkd", "rmb", "ryuan"],
                    money: ["200", "100", "300"]
                };
                var currency = list.currency;
                var money = list.money;
                return currency[i] + money[i];
            }
            // getCurrency:function (list) {
            //      var currency= list.currency;
            //     for(var i =0;i<currency.length;i++){
            //         map.push(currency[i]);
            //     }
            // }
        }
    }])
    .controller('firstController', ['$scope', 'myService', '$http', function ($scope, myService, $http) {
        $scope.name = myService.getCurrencyData().currency;
        var b = myService.getMoneyData();
        $scope.name2 = myService.getAccount(b);

        $scope.buy1Change = function (buy1) {
            var currencyMap = myService.getMoneyData().currency
            for (var i = 0; i < currencyMap.length; i++) {
                if (currencyMap[i] == buy1) {
                    $scope.moneyAccount2 = myService.getChangeMoney(i);
                }
            }
        };
        $scope.professionChange2 = function (buy2) {
            if (buy2.indexOf("hkd") >= 0) {
                $scope.buy1 = "hkd";
            } else if (buy2.indexOf("rmb") >= 0) {
                $scope.buy1 = "rmb";
            } else if (buy2.indexOf("ryuan") >= 0) {
                $scope.buy1 = "ryuan";
            }
        };
        $scope.professionChange1 = function (buy2) {
            if (buy2.indexOf("hkd") >= 0) {
                $scope.sell1 = "hkd";
            } else if (buy2.indexOf("rmb") >= 0) {
                $scope.sell1 = "rmb";
            } else if (buy2.indexOf("ryuan") >= 0) {
                $scope.sell1 = "ryuan";
            }
        };

        $scope.sell1Change = function (sell1) {
            var currencyMap = myService.getMoneyData().currency
            for (var i = 0; i < currencyMap.length; i++) {
                if (currencyMap[i] == sell1) {
                    $scope.moneyAccount1 = myService.getChangeMoney(i);
                }
            }
        };

        // $scope.name2 = b
    }])
