MyApp
    .controller('applyController',['$scope', '$interval','applyService','commonService', function ($scope,$interval,applyService,commonService) {
        $scope.pageClass = 'apply';
        //get username from localStorage
        $scope.name = localStorage.getItem("data");
        commonService.validateLogin($scope.name);
        $scope.applyInfoData = applyService.getApplyInfoData();
        $scope.applyInfoData.applyUser = $scope.name;
        $scope.isOverCount = false;

        $scope.getApplyFileData = function(){
            applyService.getApplyFile().then(
                function (value) {
                    $scope.applyFile = value.applyInfo;
                }
            )
        };
        $scope.getApplyFileData();

        $scope.validateUserForApply = function(){
            applyService.getUserInfoForApply($scope.name).then(
                function (value) {
                    if(value.responseType === 'SUCCESS'){
                        $scope.isOverCount = false;
                    }else if(value.responseType === 'ERROR'){
                        $scope.isOverCount = true;
                    }
                }
            )
        };$scope.validateUserForApply();

        $scope.validateFiles = function () {
            var file = document.getElementById("fileUpload").files[0];
            $scope.file = file;
            if(!file){
                return;
            }
            var suffix = file.name.split(".")[1];
            var fileSize = file.size;
            if(suffix === "zip" || suffix === "rar"){
                $scope.fileFromatError = false;
                if(fileSize < 40000000){
                    $scope.fileSizeError = false;
                }else {
                    $scope.file = '';
                    $scope.fileSizeError = true;
                }
            }else {
                $scope.file = '';
                $scope.fileFromatError = true;
            }
            $scope.$apply();
        };
        $scope.validateAllDataForApply = function () {
            if(!$scope.file){
                alert("file message have not uploaded");
                return false;
            }
            return true;
        };

        $scope.submit = function () {
            if($scope.validateAllDataForApply()){
                alert("ok ,the message have finished");
                applyService.submitDataForApply($scope.file,$scope.applyInfoData).then(
                    function (response) {
                        if(response.responseType === "SUCCESS"){
                            alert("Congratulations, and your information submitted to success");
                            window.location.href = "index.html#/";
                        }else {
                            alert("sorry,your information submitted to error")
                        }
                    }
                )
            }
        };

        $scope.download = function (fileName) {
            applyService.download().then(
                function (response) {
                    var link = document.createElement('a');
                    var blob = new Blob([response], {type: 'application/zip'});
                    link.setAttribute('href', window.URL.createObjectURL(blob));
                    link.setAttribute('download', fileName );
                    link.style.visibility = 'hidden';
                    document.body.appendChild(link);
                    link.click();
                    document.body.removeChild(link);
                }
            )
        };

    }
    ]);

