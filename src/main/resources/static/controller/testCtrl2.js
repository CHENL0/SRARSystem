angular.module('myApp',[])

    .controller('secondController',function ($scope) {
        console.log($scope);
        // $scope.name = $scope.$$prevSibling.name;
    })