IndexApp
    .controller('ModalInstanceCtrl',function ($scope, $uibModalInstance, items) {
        //get username from localStorage
        $scope.items = items;
        $scope.selected = {
            item: $scope.items
        };
        $scope.ok = function () {
            $uibModalInstance.close($scope.selected);
        };
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        // ModalInstanceCtrl.$inject = ['$scope','$modalInstance', 'items'];

    }
    );

