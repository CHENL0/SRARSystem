MyApp
    .controller('mainController',['$scope', function ($scope) {
        //get username from localStorage
        $scope.name = localStorage.getItem("data");

        $scope. prefixName = $scope.name.split("_")[0];


    }
    ]);

